# -*- coding: utf-8 -*-
"""
Created on Thu Mar  5 14:36:06 2020

@author: mosda
"""

from Problem import Problem
from Controller import Controller
from State import State

class UI:
    def __init__(self, ctrl, board):
        self.ctrl = ctrl
        self.board = board

    def commands(self):
        while True:
            print('''
                0. Exit
                1. DFS
                2. Greedy
            ''')
    
            cmd = input('Enter your choice: ')
    
            if cmd == '0':
                return 0
            elif cmd == '1':
                return self.ctrl.dfs(self.board)
            elif cmd == '2':
                return self.ctrl.greedy(self.board)
            else:
                print('Invalid command')
    def main_menu(self):
        result = self.commands()
        if result == 0:
            return
        elif result == None:
            print('There is no solution for this matrix')
        else:
            print(result)



def get_initial(n):
    board = []
    for i in range(n):
        board.append([])
        for j in range(n):
            board[i].append(0)

    return board


if __name__ == '__main__':

    n = int(input('Enter the size of the board: '))

    b = get_initial(n)
    s = State(b)
    p = Problem(n)
    c = Controller(p, n)
    ui = UI(c, s)
    ui.main_menu()

