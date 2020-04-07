# -*- coding: utf-8 -*-
"""
Created on Sun Mar  8 12:11:02 2020

@author: mosda
"""

class Board:
    # state
    def __init__(self, size):
        self.size = size
        self.board = []
        self.placed = 0
        self.last = []
        
    def draw_board(self):
        # creates the empty board
        # free positions are marked with 0
        
        for i in range(self.size):
            self.board.append([])
            for j in range(self.size):
                self.board[i].append(0)
                
    def get_final(self):
        return self.placed
    
    def get_last(self):
        return self.last
                
    def next_config(self, x, y):
        # place 1 on the given position if possible
        # in: x, y positions on the board
        # out: the new board
        
        if x >= 0 and x < self.size and y >= 0 and y < self.size:
            if self.check_diag(x, y) and self.check_hor(x) and self.check_vert(y):
                self.board[x][y] = 1
                self.placed = self.placed + 1
                self.last = (x, y)
            else:
                print('Too many 1\'s')
        else:
            print('wrong positions')
        return self.board
                
    def check_hor(self, x):
        return not(1 in self.board[x])
            
    def check_vert(self, y):
        for line in self.board:
            if line[y] == 1:
                return False
        return True
    
    def isGood(self, x, y):
        return self.check_diag(x, y) and self.check_hor(x) and self.check_vert(y)
    
    def check_diag(self, x, y):
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
    
    def remove(self, x, y):
        self.board[x][y] = 0
        return self.board
        
    def get_board(self):
        return self.board
        
    def __str__(self):
        s = ''
        for i in self.board:
            for j in i:
                s = s + str(j) + ' ' 
            s = s + '\n'
        return s

