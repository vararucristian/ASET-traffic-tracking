import cv2
import numpy as np
from LanesModel import LanesModel
from shapely.geometry import Point
from shapely.geometry.polygon import Polygon


class LaneMarker:
    def __init__(self):
        self.points = []
        self.image = None
        self.intersection_name = ''
        self.lane_name = ''
        self.image_clone = None
        self.lanes_model = LanesModel()
        self.centre_point = (0, 0)

    def mark_lane(self, image_path, intersection_name, lane_name):
        self.intersection_name = intersection_name
        self.lane_name = lane_name
        self.image = cv2.imread(image_path)
        self.image_clone = self.image.copy()

        im_height, im_width, _ = self.image.shape
        self.centre_point = (im_width // 2, im_height // 2)
        print('centre:', self.centre_point)

        cv2.namedWindow(intersection_name)
        cv2.setMouseCallback(intersection_name, self.click_and_mark)

        while True:

            cv2.imshow(intersection_name, self.image)
            self.image = self.image_clone.copy()
            self.draw_polylines()
            key = cv2.waitKey(1)
            if key == ord("r"):
                self.image = self.image_clone.copy()
                self.points = []
            elif key == ord('s'):
                print('marking output:', self.points)
                self.save_lane_to_model()
            elif key == ord('a'):
                print('interest output:', self.points)
                self.save_area_to_model()
            elif key == ord('q'):
                break

    def click_and_mark(self, event, x, y, flags, param):

        if event == cv2.EVENT_LBUTTONDOWN:
            self.points.append([x, y])
            print(self.points)

    def draw_polylines(self):
        array = np.array(self.points, dtype=np.int32)
        cv2.polylines(self.image, [array], True, (255, 0, 0), 2)
        cv2.imshow(self.intersection_name, self.image)

    def save_lane_to_model(self):
        if len(self.points) >= 3:
            self.lanes_model.set_lane(self.intersection_name, self.lane_name, self.points)
        else:
            print('not enouth points, minimum 3 is required')

    def save_area_to_model(self):
        if len(self.points) >= 3:
            self.lanes_model.set_area(self.intersection_name, self.points)
        else:
            print('not enouth points, minimum 3 is required')

    def contains_centre(self):
        if len(self.points) >= 3:
            point = Point(self.centre_point[0], self.centre_point[1])
            polygon = Polygon(self.points)
            print('Contains:', polygon.contains(point))


if __name__ == '__main__':
    lane_marker = LaneMarker()
    lane_marker.mark_lane('images_helpers/0500.jpg', '1', 'lane')

