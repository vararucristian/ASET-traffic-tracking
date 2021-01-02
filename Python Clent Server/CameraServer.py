import base64
import json, cv2
import pickle
import socket
from concurrent.futures.thread import ThreadPoolExecutor

import requests

from VehicleDetector import VehicleDetector
from LanesModel import LanesModel


class CameraServer:
    def __init__(self, server_ip, port):
        self.server_ip = server_ip
        self.port = port
        self.clients_size = 50
        self.lanes_model = LanesModel.get_instance()
        self.video_detector = VehicleDetector()
        self.buffer_size = 4096000

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
        print("Received connection from: " + str(address))

        data = dict(json.loads(conn.recv(1024).decode()))
        intersection_name = data['intersection_name']
        lanes_dict = self.lanes_model.get_lanes(intersection_name)
        area_points = self.lanes_model.get_area(intersection_name)

        resp = 'Received init json'
        conn.send(resp.encode())
        while True:
            self.buffer_size = int(conn.recv(1024).decode())
            resp = 'Received_size'
            conn.send(resp.encode())

            data = dict(json.loads(conn.recv(self.buffer_size).decode()))
            if not data or len(data) == 0:
                break
            frame_encode = base64.b64decode(data['frame_encode'])
            is_last_frame = data['is_last_frame']
            frame = pickle.loads(frame_encode)

            json_answer = self.video_detector.detect_objects(frame, intersection_name, lanes_dict, area_points)
            self.post_result(json_answer)

            resp = 'Received_frame '
            conn.send(resp.encode())
            if is_last_frame:
                break

        cv2.destroyWindow(intersection_name)
        cv2.destroyWindow(intersection_name + ' area')
        conn.close()
        print("Ended connection from: " + str(address))

    def post_result(self, json_result):
        # print(json_result)
        # exit()
        r = requests.post('http://localhost:8082/addTraffic',
                          data=json_result,
                          headers={'Content-Type': 'application/json',
                                   'Access-Control-Allow-Origin': '*'
    })
        print(r.json())


if __name__ == '__main__':
    camera_server = CameraServer('127.0.0.1', 8000)
    camera_server.start_server()
