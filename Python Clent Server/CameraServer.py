import socket
from concurrent.futures.thread import ThreadPoolExecutor
from VehicleDetector import VehicleDetector


class CameraServer:
    def __init__(self, server_ip, port):
        self.server_ip = server_ip
        self.port = port
        self.clients_size = 50
        self.video_detector = VehicleDetector()

    def start_server(self):
        server_socket = socket.socket()
        server_socket.bind((self.server_ip, self.port))

        executor = ThreadPoolExecutor(self.clients_size)
        server_socket.listen(self.clients_size)
        while True:
            conn, address = server_socket.accept()
            executor.submit(self.handle_client_connection, conn, address)

    def handle_client_connection(self, conn, address):
        conn.close()

    def post_result(self, result):
        pass

