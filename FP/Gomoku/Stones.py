
class PlaceException(Exception):
    pass


class Stones:
    def __init__(self):
        self.coords = {}
        self.win = False

    def add_stone(self, x, y, color):
        '''
        Add stone coordinates and color to the dictionary
        :param x:
        :param y:
        :param color:
        :return:
        '''
        if (x, y) in self.coords.keys():
            raise PlaceException('Wrong move')
        self.coords[(x, y)] = color

    def get_places(self):
        return self.coords

    def get_winner(self):
        '''
        Checks each coordinate
        :return: color of the winner
        '''
        for coordinates, color in self.coords.items():
            if self.winner_h(coordinates, color) or self.winner_v(coordinates, color) \
                    or self.winner_d(coordinates, color) or self.winner_d_back(coordinates, color):
                return color

    def winner_h(self, coordinates, color):
        stone_count = 0
        x = coordinates[0]
        y = coordinates[1]

        while stone_count < 5:
            x = x + 1
            if (x, y) in list(self.coords.keys()) and self.coords[(x, y)] == color:
                stone_count += 1
            else:
                break

        return stone_count == 4

    def winner_v(self, coordinates, color):
        stone_count = 0
        x = coordinates[0]
        y = coordinates[1]

        while stone_count < 5:
            y = y + 1
            if (x, y) in list(self.coords.keys()) and self.coords[(x, y)] == color:
                stone_count += 1
            else:
                break

        return stone_count == 4

    def winner_d(self, coordinates, color):
        stone_count = 0
        x = coordinates[0]
        y = coordinates[1]

        while stone_count < 5:
            x = x + 1
            y = y + 1
            if (x, y) in list(self.coords.keys()) and self.coords[(x, y)] == color:
                stone_count += 1
            else:
                break

        return stone_count == 4

    def winner_d_back(self, coordinates, color):
        stone_count = 0
        x = coordinates[0]
        y = coordinates[1]

        while stone_count < 5:
            x = x - 1
            y = y + 1
            if (x, y) in list(self.coords.keys()) and self.coords[(x, y)] == color:
                stone_count += 1
            else:
                break

        return stone_count == 4

