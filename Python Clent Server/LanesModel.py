import sqlite3
from Lane import Lane


class LanesModel:
    instance = None

    def __init__(self):
        if LanesModel.instance is not None:
            raise Exception("This class is a singleton!")
        else:
            LanesModel.instance = self
        self.lanes_dex = dict()
        self.db = sqlite3.connect("lanes.db")

    @staticmethod
    def get_instance():
        if LanesModel.instance is None:
            LanesModel()
        return LanesModel.instance

    def get_lanes(self, intersection_id):
        pass


