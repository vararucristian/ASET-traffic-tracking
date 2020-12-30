import json, cv2, base64
from socket import socket
import pickle
import urllib
from urllib import request
import numpy as np
from aop import aspectize, before, after
import threading


@aspectize
class WebCameraClient:
    def __init__(self, frame_address, intersection_name):
        self.frame_address = frame_address
        self.intersection_name = intersection_name
        self.connection = None
        self.stop = False
        self.is_last_frame = False
        self.input_thread = threading.Thread(target=self.input)
        self.input_thread.start()

    def input(self):
        while True:
            command = input()
            if command == 'q':
                self.is_last_frame = True
                break

    def connect_server(self, ip_address, port):
        result = 0
        try:
            self.connection = socket()
            self.connection.connect((ip_address, port))
        except Exception as error:
            print('Error while connection client:', error)
            self.connection = None
            result = -1
        return result

    def end_connection(self):
        if self.connection is not None:
            self.connection.close()
            self.connection = None

    def send_video_data(self):
        result = 0
        if self.connection is None:
            print('Connection is None')
            result = -1
        else:
            try:
                data_dict = dict()
                data_dict['intersection_name'] = self.intersection_name
                data_json = json.dumps(data_dict)
                self.send_json_to_server(data_json)
            except Exception as error:
                print('Error while sending frame to server:', error)
                result = -1
        return result

    def send_video_frame(self):
        result = 0
        if self.connection is None:
            print('Connection is None')
            result = -1
        else:
            try:
                resp = urllib.request.urlopen(self.frame_address)
                if resp is not None:
                    frame = np.asarray(bytearray(resp.read()), dtype="uint8")
                    frame = cv2.imdecode(frame, cv2.IMREAD_COLOR)
                    json_frame = self.create_frame_json(frame)
                    self.connection.send(str(len(json_frame)).encode())
                    data = self.connection.recv(1024).decode()
                    print('Response from server:', data)
                    self.send_json_to_server(json_frame)
                    if self.is_last_frame is True:
                        self.stop = True
            except Exception as error:
                print('Error while sending frame to server:', error)
                result = -1
        return result

    def send_video(self):
        if self.connection is None:
            print('Connection is None')
        else:
            video_data_result = camera_client.send_video_data()
            if video_data_result == 0:
                while not self.stop:
                    send_frame_result = camera_client.send_video_frame()
                    if send_frame_result != 0:
                        break
            self.end_connection()

    def create_frame_json(self, frame):
        frame_dict = dict()
        frame_data = pickle.dumps(frame)
        frame_encode = base64.b64encode(frame_data)
        frame_dict['frame_encode'] = frame_encode.decode('ascii')
        frame_dict['is_last_frame'] = self.is_last_frame
        frame_json = json.dumps(frame_dict)
        return frame_json

    def send_json_to_server(self, json_frame):
        if self.connection is None:
            print('Connection is None')
        else:
            self.connection.send(json_frame.encode())


@before(WebCameraClient.send_json_to_server)
def before_send_json(self, *args):
    print('Sending json for frame')


@after(WebCameraClient.send_json_to_server)
def after_send_json(res, self, *args):
    data = self.connection.recv(1024).decode()
    print('Response from server:', data)


if __name__ == '__main__':
    camera_client = WebCameraClient("https://live.freecam.ro/live/iasi-hotel-unirea?d=1608366225652", 'hotel unirea')
    camera_client.connect_server('127.0.0.1', 8000)
    camera_client.send_video()

