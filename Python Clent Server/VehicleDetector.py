import base64
import numpy as np
import tensorflow as tf
import cv2
import json
from object_detection.utils import label_map_util


class VehicleDetector:
    def __init__(self):
        self.graph_path = 'Tensorflow data/frozen_inference_graph.pb'
        self.PATH_TO_LABELS = 'Tensorflow data\\mscoco_label_map.pbtxt'
        self.detected_objects = ['car', 'truck', 'motorcycle']
        self.detection_graph = tf.Graph()

        with self.detection_graph.as_default():
            od_graph_def = tf.compat.v1.GraphDef()
            with tf.compat.v2.io.gfile.GFile(self.graph_path, 'rb') as fid:
                serialized_graph = fid.read()
                od_graph_def.ParseFromString(serialized_graph)
                tf.import_graph_def(od_graph_def, name='')

        self.NUM_CLASSES = 90
        self.label_map = label_map_util.load_labelmap(self.PATH_TO_LABELS)
        self.categories = label_map_util.convert_label_map_to_categories(self.label_map,
                                                                         max_num_classes=self.NUM_CLASSES,
                                                                         use_display_name=True)
        self.category_index = label_map_util.create_category_index(self.categories)
        self.session = tf.compat.v1.Session(graph=self.detection_graph)

    def detect_objects(self, frame, intersection_name, area_points, min_score=0.5):
        image_cropped = self.crop_frame(frame, area_points)

        image_np_expanded = np.expand_dims(image_cropped, axis=0)
        image_tensor = self.detection_graph.get_tensor_by_name('image_tensor:0')
        boxes = self.detection_graph.get_tensor_by_name('detection_boxes:0')
        scores = self.detection_graph.get_tensor_by_name('detection_scores:0')
        classes = self.detection_graph.get_tensor_by_name('detection_classes:0')
        num_detections = self.detection_graph.get_tensor_by_name('num_detections:0')

        (boxes, scores, classes, num_detections) = self.session.run(
            [boxes, scores, classes, num_detections],
            feed_dict={image_tensor: image_np_expanded})
        boxes = np.squeeze(boxes)
        scores = np.squeeze(scores)
        classes = np.squeeze(classes).astype(np.int32)

        self.draw_area(frame, area_points)
        json_response = self.create_json_answer(intersection_name,
                                                frame,
                                                boxes,
                                                classes,
                                                scores,
                                                min_score)
        return json_response

    def create_json_answer(self, intersection_name, image, boxes, classes, scores, min_score=0.5):
        dict_response = dict()
        dict_response['streetId'] = int(intersection_name)
        dict_response['nrCars'] = 0

        for i in range(0, len(boxes)):
            if scores[i] > min_score:
                class_name = self.category_index[classes[classes[i]]]['name']
                if class_name in self.detected_objects:
                    dict_response['nrCars'] += 1
                    left, right, top, bottom = self.box_coordinates(image, boxes[i])
                    image = self.draw_box(image, left, right, top, bottom)

        self.show_image(intersection_name, image)
        self.add_image_to_response(dict_response, image)
        json_response = json.dumps(dict_response)
        return json_response

    @staticmethod
    def draw_area(image, area_points):
        array = np.array(area_points, dtype=np.int32)
        cv2.polylines(image, [array], True, (0, 255, 0), 2)

    @staticmethod
    def crop_frame(frame, area_points):
        frame = frame.copy()
        res = frame
        if area_points is not None:
            mask = np.zeros(frame.shape[0:2], dtype=np.uint8)
            points = np.array(area_points)
            cv2.drawContours(mask, [points], -1, (255, 255, 255), -1, cv2.LINE_AA)
            res = cv2.bitwise_and(frame, frame, mask=mask)
        return res

    @staticmethod
    def box_coordinates(image, box):
        ymin, xmin, ymax, xmax = box
        im_height, im_width, _ = image.shape
        left = int(xmin * im_width)
        right = int(xmax * im_width)
        top = int(ymin * im_height)
        bottom = int(ymax * im_height)
        return left, right, top, bottom

    @staticmethod
    def draw_box(image, left, right, top, bottom):
        image = cv2.rectangle(image, (left, top), (right, bottom), (255, 0, 0), 4)
        return image

    @staticmethod
    def add_image_to_response(dict_response, image):
        _, buffer = cv2.imencode('.png', image)
        image_text = base64.b64encode(buffer).decode('ascii')
        dict_response['image'] = image_text

    @staticmethod
    def show_image(intersection_name, image):
        cv2.imshow(intersection_name, image)
        cv2.waitKey(1)


if __name__ == '__main__':
    video_detector = VehicleDetector()
