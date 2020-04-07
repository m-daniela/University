# -*- coding: utf-8 -*-
"""
Created on Thu Mar  5 14:39:42 2020

@author: mosda
"""

from State import State

class Problem:
    def __init__(self, size):
        self.size = size
        self.initial = None
        self.final = None

    def set_initial(self, initial):
        self.initial = initial

    def get_final(self):
        return self.final


    def copy(self, board):
        # copy the board
        # more complexity, but whatever
        # in: board
        # out: copy of board
        sadness = []
        for i in range(len(board)):
            sadness.append([])
            for j in range(len(board[0])):
                sadness[i].append(board[i][j])
        return sadness

    def expand(self, state):
        # expand the given state by 1 level
        # in: state (State)
        # out: list of states
        values = state.get_values()
        try:
            x, y = values[-1]
        except Exception:
            x = 0
            y = -1
        states = []
        for i in range(x, self.size):
            for j in range(self.size):
                if i == x and j <= y:
                    pass
                else:
                    # if state.isGood(i, j):
                    board = state.add_value(i, j)
                    # print(board)
                    b2 = self.copy(board)
                    s = State(b2)

                    states.append(s)
                    state.remove_value(i, j)

        return states

    def heuristics(self, state):
        # in: state (State)
        # out: path to the next step (int)
        result = 0
        v = len(state.get_values())
        if state.isSolution(v) == False:
            return 500
        for line in state.get_board():
            for col in line:
                if col == 0:
                    result = result + 1
                    # print(result)
        return result
