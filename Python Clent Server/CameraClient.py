import json, cv2, base64
from socket import socket
import pickle
from VideoFile import VideoFile
from aop import aspectize, before, after


@aspectize
class CameraClient:
    def __init__(self, video_path, intersection_name):
        self.video_path = video_path
        self.video_file = VideoFile(video_path)
        self.intersection_name = intersection_name
        self.connection = None

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
        elif self.video_file.current_frame >= self.video_file.video_length:
            print('There aren\'t any frames left to send')
        else:
            try:
                data_dict = dict()
                data_dict['video_name'] = self.video_path
                data_dict['intersection_name'] = self.intersection_name
                data_dict['video_fps'] = self.video_file.video_fps
                data_dict['video_width'] = self.video_file.video_width
                data_dict['video_height'] = self.video_file.video_height
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
        elif self.video_file.current_frame >= self.video_file.video_length:
            print('There aren\'t any frames left to send')
        else:
            try:
                frame, is_last_frame = self.video_file.read_next_frame()
                if frame is not None:
                    json_frame = self.create_frame_json(frame, is_last_frame)
                    self.send_json_to_server(json_frame)
            except Exception as error:
                print('Error while sending frame to server:', error)
                result = -1
        return result

    def send_video(self):
        if self.connection is None:
            print('Connection is None')
        elif self.video_file.current_frame >= self.video_file.video_length:
            print('There aren\'t any frames left to send')
        else:
            video_data_result = camera_client.send_video_data()
            if video_data_result == 0:
                while camera_client.video_file.current_frame < camera_client.video_file.video_length:
                    send_frame_result = camera_client.send_video_frame()
                    if send_frame_result != 0:
                        break
            self.end_connection()

    def create_frame_json(self, frame, is_last_frame):
        frame_dict = dict()
        frame_dict['frame_count'] = self.video_file.current_frame

        frame_data = pickle.dumps(frame)
        frame_dict['frame_encode'] = base64.b64encode(frame_data).decode('ascii')
        frame_dict['is_last_frame'] = is_last_frame
        frame_json = json.dumps(frame_dict)
        return frame_json

    def send_json_to_server(self, json_frame):
        if self.connection is None:
            print('Connection is None')
        else:
            self.connection.send(json_frame.encode())


@before(CameraClient.send_json_to_server)
def before_send_json(self, *args):
    print('Sending json for frame:', self.video_file.current_frame, '/', self.video_file.video_length)


@after(CameraClient.send_json_to_server)
def after_send_json(res, self, *args):
    data = self.connection.recv(1024).decode()
    print('Response from server:', data)


if __name__ == '__main__':
    camera_client = CameraClient("sub-1504619634606.mp4", 'sub-1504619634606')
    camera_client.connect_server('127.0.0.1', 8000)
    camera_client.send_video()

