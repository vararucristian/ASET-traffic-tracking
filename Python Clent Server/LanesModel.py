import sqlite3
from Lane import Lane


class LanesModel:
    instance = None

    def __init__(self):
        self.lanes_dex = dict()
        self.db = sqlite3.connect("lanes.db")

    @staticmethod
    def get_instance():
        LanesModel.instance = None
        if LanesModel.instance is None:
            LanesModel.instance = LanesModel()
        return LanesModel.instance

    def get_lanes(self, intersection_id):
        pass


