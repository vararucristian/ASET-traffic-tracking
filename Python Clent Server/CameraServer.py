import base64
import json, cv2
import pickle
import socket
from concurrent.futures.thread import ThreadPoolExecutor
from VehicleDetector import VehicleDetector


class CameraServer:
    def __init__(self, server_ip, port):
        self.server_ip = server_ip
        self.port = port
        self.clients_size = 50
        self.video_detector = VehicleDetector()
        self.buffer_size = 40960000

    def start_server(self):
        server_socket = socket.socket()
        server_socket.bind((self.server_ip, self.port))

        executor = ThreadPoolExecutor(self.clients_size)
        server_socket.listen(self.clients_size)
        print('Listenning connections')
        while True:
            conn, address = server_socket.accept()
            executor.submit(self.handle_client_connection, conn, address)

    def handle_client_connection(self, conn, address):
        print("Received connection from: " + str(address))

        # in order to test the video frames transfer from client to server
        # for now the data is written in a local video file
        data = dict(json.loads(conn.recv(1024).decode()))
        video_output = cv2.VideoWriter(str(address) + '.mp4',
                                       cv2.VideoWriter_fourcc(*'mp4v'),
                                       data['video_fps'],
                                       (data['video_width'], data['video_height']))
        video_name = data['video_name']
        resp = 'Received init json'
        conn.send(resp.encode())
        while True:
            data = dict(json.loads(conn.recv(self.buffer_size).decode()))
            if not data:
                break
            frame_encode = base64.b64decode(data['frame_encode'])
            frame = pickle.loads(frame_encode)

            json_answer = self.video_detector.detect_objects(frame, video_name)
            self.post_result(json_answer)

            video_output.write(frame)
            resp = 'Received_frame ' + str(data['frame_count'])
            conn.send(resp.encode())
        video_output.release()
        conn.close()

    def post_result(self, json_result):
        # to do
        print(json_result)


if __name__ == '__main__':
    camera_server = CameraServer('127.0.0.1', 8000)
    camera_server.start_server()
