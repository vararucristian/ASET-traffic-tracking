import base64
import cv2
import json
import pickle
import socket
import requests
from concurrent.futures.thread import ThreadPoolExecutor

from requests.adapters import HTTPAdapter

from LanesModel import LanesModel
from VehicleDetector import VehicleDetector


class CameraServer:
    def __init__(self, server_ip, port):
        self.server_ip = server_ip
        self.port = port
        self.clients_size = 50
        self.lanes_model = LanesModel.get_instance()
        self.video_detector = VehicleDetector()

    def start_server(self):
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_socket.bind((self.server_ip, self.port))

        executor = ThreadPoolExecutor(self.clients_size)
        server_socket.listen(self.clients_size)
        print('Listenning connections')
        while True:
            conn, address = server_socket.accept()
            executor.submit(self.handle_client_connection, conn, address)

    def handle_client_connection(self, conn, address):
        address_str = str(address)
        print("Received connection from: " + address_str)
        connection_alive, intersection_name, min_score = self.receive_init_json(conn, address_str)
        area_points = self.lanes_model.get_area(intersection_name)

        connection_alive = self.send_response(conn, address_str, 'Received init json')
        while connection_alive:
            try:
                buffer_size = self.read_json_size(conn)
                self.send_response(conn, address_str, 'Received json size ' + str(buffer_size))

                frame, is_last_frame = self.read_json_data(conn, buffer_size)
                json_result = self.video_detector.detect_objects(frame, intersection_name, area_points, min_score)

                self.post_result(json_result)
                self.send_response(conn, address_str, 'Received frame json')

                if is_last_frame:
                    connection_alive = False
            except Exception as error:
                print('Exception in connection:', str(address), ' error:', error)
                connection_alive = False
        cv2.destroyWindow(intersection_name)
        conn.close()
        print("Ended connection from: " + str(address))

    @staticmethod
    def receive_init_json(conn, address):
        print(type(conn))
        connection_alive = True
        intersection_name = ''
        min_score = 0.5
        try:
            data = dict(json.loads(conn.recv(1024).decode()))
            intersection_name = data['intersection_name']
            min_score = data['min_score']
            print('Received init json from address:', address)
        except Exception as error:
            print('Exception while reading init json from address:', address, 'error:', error)
            connection_alive = False
        return connection_alive, intersection_name, min_score

    @staticmethod
    def read_json_size(conn):
        buffer_size = int(conn.recv(1024).decode())
        return buffer_size

    @staticmethod
    def read_json_data(conn, buffer_size):
        data = dict(json.loads(conn.recv(buffer_size).decode()))
        frame_encode = base64.b64decode(data['frame_encode'])
        frame = pickle.loads(frame_encode)
        is_last_frame = data['is_last_frame']
        return frame, is_last_frame

    @staticmethod
    def send_response(conn, address, response):
        connection_alive = True
        try:
            conn.send(response.encode())
        except Exception as error:
            print('Exception while sending response to address:', address, 'error:', error)
            connection_alive = False
        return connection_alive

    @staticmethod
    def post_result(json_result):
        try:
            requests.post('http://localhost:8082/addTraffic',
                          data=json_result,
                          headers={'Content-Type': 'application/json',
                                   'Access-Control-Allow-Origin': '*'})
        except Exception as error:
            print('Exception while posting json result, error:', error)


if __name__ == '__main__':
    camera_server = CameraServer('127.0.0.1', 8000)
    camera_server.start_server()
