import math
import functools

# Solution for https://open.kattis.com/problems/wrapping

class Point:
    def __init__(self):
        self.x = 0.0
        self.y = 0.0


def rot_point(deg: float, x: float, y: float, center_x: float, center_y: float):
    point = Point()
    if deg == 0:
        point.x = x + center_x
        point.y = y + center_y
        return point

    rad = deg * (3.14159265358979323846 / 180)
    sin = math.sin(rad)
    cos = math.cos(rad)
    point.x = x * cos - y * sin
    point.y = x * sin + y * cos
    point.x += center_x
    point.y += center_y

    return point


def dot_product(p1: Point, p2: Point, p3: Point):
    return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)


def area(point_list: list):
    result = 0.0
    for i in range(1, len(point_list)):
        result += (point_list[i - 1].y + point_list[i].y) * (point_list[i].x - point_list[i - 1].x) / 2.0
    return math.fabs(result)


def point_compare(p1: Point, p2: Point):
    if p1.x == p2.x:
        return p2.y - p1.y
    return p2.x - p1.x


def andrew(point_list: list):
    # TODO: Presort
    point_list.sort(key=functools.cmp_to_key(point_compare))

    n = len(point_list)
    capacity = 0
    hull_list = [None] * 2 * n

    for i in range(n):
        while capacity >= 2 and dot_product(hull_list[capacity - 2], hull_list[capacity - 1], point_list[i]) <= 0:
            capacity -= 1
        hull_list[capacity] = point_list[i]
        capacity += 1

    t_cap = capacity + 1
    for i in range(n - 1, 0, -1):
        while capacity >= t_cap and dot_product(hull_list[capacity - 2], hull_list[capacity - 1],
                                                point_list[i - 1]) <= 0:
            capacity -= 1
        hull_list[capacity] = point_list[i - 1]
        capacity += 1

    hull_list = hull_list[0:capacity]
    return hull_list


if __name__ == "__main__":
    cases = eval(input())
    result_list = list()
    for i in range(cases):
        small_area = 0.0
        point_list = list()
        n = int(input())
        for i_n in range(n):
            in_read = input().split(" ")
            x = float(in_read[0])
            y = float(in_read[1])
            w = float(in_read[2])
            h = float(in_read[3])
            v = float(in_read[4])
            v *= -1
            small_area += w * h
            h /= 2.0
            w /= 2.0
            point_list.append(rot_point(v, w, h, x, y))
            point_list.append(rot_point(v, -w, h, x, y))
            point_list.append(rot_point(v, w, -h, x, y))
            point_list.append(rot_point(v, -w, -h, x, y))
        hull_points = andrew(point_list)
        big_area = area(hull_points)
        result_list.append((small_area * 100) / big_area)
    for result in result_list:
    print("%.1f %%" % result)