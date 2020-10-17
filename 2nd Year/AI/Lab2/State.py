class State:
    # is the board
    # receives a board
    def __init__(self, board):
        self.board = board
        self.size = len(self.board)
        self.values = []
        self.set_values()

    def get_board(self):
        return self.board

    def get_size(self):
        return self.size

    def get_values(self):
        return self.values

    def set_values(self):
        # gets the values in the given board
        for i in range(len(self.board)):
            for j in range(len(self.board[0])):
                if self.board[i][j] == 1:
                    self.values.append((i, j))

    def add_value(self, x, y):
        # adds a 1 on the board and on the values list
        # in: x, y (int)
        # out: board as matrix
        if x >= 0 and x < len(self.board) and y >= 0 and y < len(self.board):
            self.board[x][y] = 1
            self.values.insert(0, (x, y))
        return self.board

    def remove_value(self, x, y):
        # removes the 1 from the table and the values list
        # in: x, y (int)
        self.board[x][y] = 0
        try:
            self.values.remove((x, y))
        except Exception:
            pass

    def check_hor(self, x):
        # checks if the lines are safe
        # in: x (int)
        # out: bool
        return not (1 in self.board[x])

    def check_vert(self, y):
        # checks if the columns are safe
        # in: y (int)
        # out: bool
        for line in self.board:
            if line[y] == 1:
                return False
        return True

    def check_diag(self, x, y):
        # saddest function on Earth
        # checks if the diagonals are safe
        # in: x, y (int)
        # out: bool
        x1 = x
        y1 = y

        while x != self.size and y != self.size:
            if self.board[x][y] == 1:
                return False
            x = x + 1
            y = y + 1

        x = x1
        y = y1
        while x >= 0 and y >= 0:
            if self.board[x][y] == 1:
                return False
            x = x - 1
            y = y - 1

        x = x1
        y = y1
        while x < self.size and y >= 0:
            if self.board[x][y] == 1:
                return False
            x = x + 1
            y = y - 1

        x = x1
        y = y1
        while x >= 0 and y < self.size:
            if self.board[x][y] == 1:
                return False
            x = x - 1
            y = y + 1

        return True


    def isGood(self, x, y):
        # checks if the given position is good
        # returns True if so, False otherwise
        # in: x, y (int)
        # out: bool
        return self.check_diag(x, y) and self.check_hor(x) and self.check_vert(y)


    def isSolution(self, size):
        # checks if the board is a solution 
        # or a candidate to the solution
        # in: size (int) 
        # out: if the goal of placing 'size' 1s is met
        count = 0
        for value in self.values:
            self.remove_value(value[0], value[1])
            if self.isGood(value[0], value[1]):
                count += 1
            self.add_value(value[0], value[1])

        return size == count

    def __str__(self):
            s = ''
            for i in self.board:
                for j in i:
                    s = s + str(j) + ' '
                s = s + '\n'
            return s