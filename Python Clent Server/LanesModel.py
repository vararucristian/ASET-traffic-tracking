import sqlite3


class LanesModel:
    instance = None

    def __init__(self):
        if LanesModel.instance is not None:
            raise Exception("This class is a singleton!")
        else:
            LanesModel.instance = self
        self.lanes_dict = dict()
        self.areas_dict = dict()
        self.conn = sqlite3.connect("lanes.db")
        self.create_tables()
        self.set_lanes_dict()
        self.set_areas_dict()

    @staticmethod
    def get_instance():
        if LanesModel.instance is None:
            LanesModel()
        return LanesModel.instance

    def create_tables(self):
        cursor = self.conn.cursor()
        cursor.execute('CREATE TABLE IF NOT EXISTS lanes_points( '
                       'id INTEGER PRIMARY KEY AUTOINCREMENT, '
                       'intersection_name TEXT, '
                       'lane_name TEXT, '
                       'x INTEGER, '
                       'y INTEGER )')
        cursor.execute('CREATE TABLE IF NOT EXISTS areas_points( '
                       'id INTEGER PRIMARY KEY AUTOINCREMENT, '
                       'intersection_name TEXT, '
                       'x INTEGER, '
                       'y INTEGER )')
        cursor.close()

    def get_lanes(self, intersection_name):
        lanes = None
        if intersection_name in self.lanes_dict.keys():
            lanes = self.lanes_dict[intersection_name]
        return lanes

    def get_area(self, intersection_name):
        area_points = None
        if intersection_name in self.areas_dict.keys():
            area_points = self.areas_dict[intersection_name]
        return area_points

    def set_lanes_dict(self):
        cursor = self.conn.cursor()
        cursor.execute('SELECT * FROM lanes_points '                       
                       'ORDER BY id')
        rows = cursor.fetchall()

        for row in rows:
            intersection_name = row[1]
            lane_name = row[2]
            x = row[3]
            y = row[4]

            if intersection_name not in self.lanes_dict.keys():
                self.lanes_dict[intersection_name] = dict()
                self.lanes_dict[intersection_name][lane_name] = list()
                self.lanes_dict[intersection_name][lane_name].append((x, y))
            elif lane_name not in self.lanes_dict[intersection_name].keys():
                self.lanes_dict[intersection_name][lane_name] = list()
                self.lanes_dict[intersection_name][lane_name].append((x, y))
            else:
                self.lanes_dict[intersection_name][lane_name].append((x, y))
        cursor.close()

    def set_areas_dict(self):
        cursor = self.conn.cursor()
        cursor.execute('SELECT * FROM areas_points '                       
                       'ORDER BY id')
        rows = cursor.fetchall()

        for row in rows:
            intersection_name = row[1]
            x = row[2]
            y = row[3]

            if intersection_name not in self.areas_dict.keys():
                self.areas_dict[intersection_name] = list()
                self.areas_dict[intersection_name].append((x, y))
            else:
                self.areas_dict[intersection_name].append((x, y))
        cursor.close()

    def set_lane(self, intersection_name, lane_name, lane_points):
        self.remove_lane(intersection_name, lane_name)
        cursor = self.conn.cursor()
        for points_tuple in lane_points:
            cursor.execute('INSERT INTO lanes_points '
                           '(intersection_name, lane_name, x, y) '
                           'VALUES( ?, ?, ?, ? )',
                           (intersection_name, lane_name, points_tuple[0], points_tuple[1]))
        self.conn.commit()
        cursor.close()

    def remove_lane(self, intersection_name, lane_name):
        cursor = self.conn.cursor()
        cursor.execute('DELETE FROM lanes_points WHERE '
                       'intersection_name = ? AND lane_name = ?',
                       (intersection_name, lane_name))
        cursor.close()

    def set_area(self, intersection_name, area_points):
        self.remove_area(intersection_name)
        cursor = self.conn.cursor()
        for points_tuple in area_points:
            cursor.execute('INSERT INTO areas_points '
                           '(intersection_name, x, y) '
                           'VALUES( ?, ?, ? )',
                           (intersection_name, points_tuple[0], points_tuple[1]))
        self.conn.commit()
        cursor.close()

    def remove_area(self, intersection_name):
        cursor = self.conn.cursor()
        cursor.execute('DELETE FROM areas_points WHERE '
                       'intersection_name = ?',
                       (intersection_name, ))
        cursor.close()

    def print_lanes(self):
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM lanes_points")
        rows = cursor.fetchall()
        for row in rows:
            print(row)
        cursor.close()

    def print_areas(self):
        cursor = self.conn.cursor()
        cursor.execute("SELECT * FROM areas_points")
        rows = cursor.fetchall()
        for row in rows:
            print(row)
        cursor.close()


if __name__ == '__main__':
    lanes_model = LanesModel.get_instance()
    lanes_model.print_lanes()
    lanes_model.print_areas()
    print(lanes_model.areas_dict)
