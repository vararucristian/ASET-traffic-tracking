from VideoFile import VideoFile


class CameraClient():
    def __init__(self, video_path):
        self.video_file = VideoFile(video_path)

    def connect_and_send_video(self, ip_address, port):
        pass

    def create_json(self, img):
        pass

    def send_json(self, img):
        pass

