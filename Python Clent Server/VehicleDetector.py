import base64

import numpy as np
import os
import tensorflow as tf
import cv2
import json

from object_detection.utils import label_map_util
from object_detection.utils import visualization_utils as vis_util
from shapely.geometry import Point
from shapely.geometry.polygon import Polygon


class VehicleDetector:
    def __init__(self):

        self.graph_path = 'Tensorflow data/frozen_inference_graph.pb'
        self.detection_graph = tf.Graph()
        self.detected_objects = ['car', 'truck', 'motorcycle']

        with self.detection_graph.as_default():
            od_graph_def = tf.compat.v1.GraphDef()
            with tf.compat.v2.io.gfile.GFile(self.graph_path, 'rb') as fid:
                serialized_graph = fid.read()
                od_graph_def.ParseFromString(serialized_graph)
                tf.import_graph_def(od_graph_def, name='')

        self.PATH_TO_LABELS = os.path.join('Tensorflow data',
                                           'mscoco_label_map.pbtxt')

        self.NUM_CLASSES = 90

        self.label_map = label_map_util.load_labelmap(self.PATH_TO_LABELS)
        self.categories = label_map_util.convert_label_map_to_categories(self.label_map,
                                                                         max_num_classes=self.NUM_CLASSES,
                                                                         use_display_name=True)
        self.category_index = label_map_util.create_category_index(self.categories)
        self.session = tf.compat.v1.Session(graph=self.detection_graph)

    def detect_objects(self, frame, intersection_name, lanes_dict, area_points):

        image = self.crop_frame(frame, area_points)

        image_np_expanded = np.expand_dims(image, axis=0)
        image_tensor = self.detection_graph.get_tensor_by_name('image_tensor:0')
        boxes = self.detection_graph.get_tensor_by_name('detection_boxes:0')
        scores = self.detection_graph.get_tensor_by_name('detection_scores:0')
        classes = self.detection_graph.get_tensor_by_name('detection_classes:0')
        num_detections = self.detection_graph.get_tensor_by_name('num_detections:0')

        (boxes, scores, classes, num_detections) = self.session.run(
            [boxes, scores, classes, num_detections],
            feed_dict={image_tensor: image_np_expanded})
        vis_util.visualize_boxes_and_labels_on_image_array(
            frame,
            np.squeeze(boxes),
            np.squeeze(classes).astype(np.int32),
            np.squeeze(scores),
            self.category_index,
            use_normalized_coordinates=True,
            line_thickness=4,
            min_score_thresh=.5)

        json_answer = self.create_json_answer(intersection_name,
                                              frame,
                                              np.squeeze(boxes),
                                              np.squeeze(classes).astype(np.int32),
                                              np.squeeze(scores),
                                              num_detections,
                                              lanes_dict)

        return json_answer

    def create_json_answer(self, intersection_name, image, boxes, classes, scores, num_detections, lanes_dict,
                           min_score=0.5):
        dict_answer = dict()
        dict_answer['intersection_name'] = intersection_name
        dict_answer['vehicles_count'] = 0
        dict_answer['lanes'] = dict()
        if lanes_dict is not None:
            self.draw_lanes(image, lanes_dict)
            for lane_key in lanes_dict.keys():
                dict_answer['lanes'][lane_key] = dict()
                dict_answer['lanes'][lane_key]['vehicle_count'] = 0

        for i in range(0, len(boxes)):
            if scores[i] > min_score:
                ymin, xmin, ymax, xmax = boxes[i]
                im_height, im_width, _ = image.shape
                left = int(xmin * im_width)
                right = int(xmax * im_width)
                top = int(ymin * im_height)
                bottom = int(ymax * im_height)
                class_name = self.category_index[classes[classes[i]]]['name']
                image = cv2.rectangle(image, (left, top), (right, bottom), (255, 0, 0), 1)
                #
                if class_name in self.detected_objects:
                    answer_entry = dict()
                    answer_entry['score'] = str(scores[i])
                    answer_entry['left'] = left
                    answer_entry['top'] = top
                    answer_entry['right'] = right
                    answer_entry['bottom'] = bottom
                    dict_answer['vehicles_count'] += 1
                    self.check_lanes_entry(answer_entry, dict_answer, lanes_dict)


        retval, buffer = cv2.imencode('.png', image)
        image_text = base64.b64encode(buffer).decode('ascii')
        dict_answer['image'] = image_text
        json_answer = json.dumps(dict_answer)
        cv2.imshow(intersection_name, image)
        cv2.waitKey(1)
        return json_answer

    @staticmethod
    def draw_lanes(image, lanes_dict):
        for lane_points in lanes_dict.values():
            array = np.array(lane_points, dtype=np.int32)
            cv2.polylines(image, [array], True, (255, 0, 0), 2)

    def check_lanes_entry(self, entry_dict, answer_dict, lanes_dict):
        if lanes_dict is not None:
            for lane_item in lanes_dict.items():
                if self.check_lane_entry(entry_dict, lane_item[1]):
                    answer_dict['lanes'][lane_item[0]]['vehicle_count'] += 1

    @staticmethod
    def check_lane_entry(entry_dict, lane_points):
        polygon = Polygon(lane_points)

        entry_points = list()
        entry_points.append((entry_dict['left'], entry_dict['top']))
        entry_points.append((entry_dict['left'], int((entry_dict['top'] + entry_dict['bottom']) * 0.5)))

        entry_points.append((entry_dict['left'], entry_dict['bottom']))
        entry_points.append((int((entry_dict['left'] + entry_dict['right']) * 0.5), entry_dict['bottom']))

        entry_points.append((entry_dict['right'], entry_dict['bottom']))
        entry_points.append((entry_dict['right'], int((entry_dict['bottom'] + entry_dict['top']) * 0.5)))

        entry_points.append((entry_dict['right'], entry_dict['top']))
        entry_points.append((int((entry_dict['right'] + entry_dict['left']) * 0.5), entry_dict['top']))

        entry_point_found = False
        for entry_point in entry_points:
            if polygon.contains(Point(entry_point[0], entry_point[1])):
                entry_point_found = True
                break
        return entry_point_found

    @staticmethod
    def crop_frame(frame, area_points):
        frame = frame.copy()
        res = frame
        if area_points is not None:
            mask = np.zeros(frame.shape[0:2], dtype=np.uint8)
            points = np.array(area_points)
            cv2.drawContours(mask, [points], -1, (255, 255, 255), -1, cv2.LINE_AA)

            res = cv2.bitwise_and(frame, frame, mask=mask)
            cv2.imshow("Samed Size Black Image", res)
        return res


if __name__ == '__main__':
    video_detector = VehicleDetector()
