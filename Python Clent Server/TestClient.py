import unittest
from CameraClient import CameraClient


class TestEmployee(unittest.TestCase):
    def setUp(self):
        self.video_path = "traffic.mp4"
        self.address = '127.0.0.1'
        self.port = 8000

    def test_client_connection(self):
        camera_client = CameraClient(self.video_path)
        connection_result = camera_client.connect_server(self.address, self.port)
        self.assertEqual(connection_result, 0)
        camera_client.end_connection()

    def test_client_data_transfer(self):
        camera_client = CameraClient(self.video_path)
        connection_result = camera_client.connect_server(self.address, self.port)
        self.assertEqual(connection_result, 0)
        video_data_result = camera_client.send_video_data()
        self.assertEqual(video_data_result, 0)
        camera_client.end_connection()

    def test_client_video_transfer(self):
        camera_client = CameraClient(self.video_path)
        connection_result = camera_client.connect_server(self.address, self.port)
        self.assertEqual(connection_result, 0)
        video_data_result = camera_client.send_video_data()
        self.assertEqual(video_data_result, 0)
        send_frame_result = 0
        while camera_client.video_file.current_frame < camera_client.video_file.video_length:
            send_frame_result = camera_client.send_video_frame()
        self.assertEqual(send_frame_result, 0)
        camera_client.end_connection()


if __name__ == '__main__':
    unittest.main()

