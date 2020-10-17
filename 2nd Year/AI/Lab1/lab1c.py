# -*- coding: utf-8 -*-
"""
Created on Thu Feb 27 15:37:14 2020

@author: mosda
"""

import numpy as np

# sudoku

class Sudoku:
    
    def __init__(self, filename, size):
        self.filename = filename
        self.size = size
        self.table = []
        self.numbers = 0
        
        
    def read_table(self):
        # reads the table from a file
        self.table = []
        self.numbers = 0
        with open(self.filename, 'r') as f:
            for index, line in enumerate(f.readlines()):
                
                line = line.strip()
                self.table.append([])
                for jndex, position in enumerate(line):
                    if position != 'x':
                        self.table[index].append(int(position))
                    else: 
                        self.table[index].append(0)
                        self.numbers += 1
       
        
    
    def check_horizontal(self, number, position):
        # check if the number is on the line given by the position
        return number in self.table[position]
    
    def check_vertical(self, number, position):
        # check if the number is on the column given by the position
        for line in self.table:
            if number == line[position]:
                return True
        return False
    
    def solve(self):
        
        sample = np.random.randint(1, self.size + 1, self.numbers)
        print('Your random choice ' + str(sample))
        for x in sample:
            if self.check_sample(x) == False:
                print('Your solution is not correct')
                return False
            else:
                print('Your solution is correct')
                return True
                        
        
    def check_sample(self, number):
        # if the number fits, put it in the first position
        # else, stop execution
        for index, line in enumerate(self.table):
            for jndex, position in enumerate(line):
                if position == 0:
                    if not(self.check_horizontal(number, index)) and not(self.check_vertical(number, jndex)):
                        self.table[index][jndex] = number
                    else:
                        return False
        return True
    
    def print_table(self):
        s = ''
        for line in self.table:
            for c in line:
                s = s + str(c)
            s = s + '\n'
                
        print(s)
            
        

class Crypto:
    def __init__(self, filename):
        self.filename = filename
        self.words = []
        self.letters = {}
        
        
        
    def read_file(self):
        # read file, each line is a word
        lines = []
        with open(self.filename, 'r') as f:
            lines = f.readlines()
            
        for line in lines:
            self.words.append(line.strip())
        
        
    
    def get_letters(self):
        # get a set of the letters from the given words
        s = ''
        for word in self.words:
            s = s + word
        self.string_of_words = set(s)
        
        self.get_sample()
        
    def get_sample(self):
        # assign each number from a random sequence to each letter
        # the numbers cannot repeat themselves and the first letters
        # are not 0
        
        #sample = np.random.choice(16, size=len(letters), replace=False)
        sample = np.random.choice(16, size=len(self.string_of_words), replace=False)
        #print(len(letters))
        
        for let, number in zip(list(self.string_of_words), sample):
            self.letters[let] = hex(number).split('x')[-1]
        
        #print(self.letters)
        self.check_first_letter()
        
        
    def check_first_letter(self):
        # if 0 was assigned to one of the first letters, take another sample
        for word in self.words:
            if self.letters[word[0]] == '0':
                self.get_sample()
        
        
    
    def to_number(self, number):
        # transform the word into a hex number (string)
        num = ''
        for c in number:
            num = num + self.letters[c]
        return num
        
    def check(self):
        # checks if the sum of the first n - 1 numbers is equal 
        # to the last number
        local = []
        for word in self.words:
            local.append(self.to_number(word))
            
        *numbers, result = local
        
        s = 0
        for n in numbers:
            s = s + int(n, 16)
        
        return s == int(result, 16)
    
    
    def solve(self):
        self.get_letters()
        
        for word in self.words:
            print(word + ' = ' + str(self.to_number(word)))
        
        if self.check():
            print('Correct solution')
            return True
        else:
            print('Incorrect solution')
            return False
            
        

class Geometric:
    def __init__(self, filename):
        self.filename = filename
        self.forms = []
        self.no_of_forms = 0
        self.lines = 5
        self.columns = 6
        self.board = self.generate_board()
        
        
    def generate_board(self):
        # get the empty board
        board = []
        for i in range(self.lines):
            board.append([])
            for j in range(self.columns):
                board[i].append(0)
        
        return board
        
    def read_file(self):
        # read figures from file
        with open(self.filename, 'r') as f:
            read_lines = f.readlines()
        
        self.no_of_forms, *lines = read_lines
        
        local = []
        
        for line in lines:
            if line != '\n':
                local.append(line.strip().split(','))
            else:
                self.forms.append(local)
                local = []
        
    def get_sample(self):
        
        # for each form, get a random position and place it on the board
        
        self.board = self.generate_board()
        for i in range(int(self.no_of_forms)):
            if not(self.place_piece(np.random.randint(0, self.lines), np.random.randint(0, self.columns), i)):
                return False
        return True
                
    
    def place_piece(self, x, y, piece):
        # place the piece on the board if possible
        # x, y: coordinates (int)
        # piece: position of piece in piece array (int)
        
        local_piece = self.forms[piece]
        h = len(local_piece)
        w = len(local_piece[0])
        
        self.print_piece(x, y, piece + 1, local_piece)
        
        if x + h <= self.lines and y + w <= self.columns:
            for i in range(x, x + h):
                for j in range(y, y + w):
                    if self.board[i][j] == 0 or local_piece[i - x][j - y] == 0:
                        self.board[i][j] = local_piece[i - x][j - y]
                    else:
                        print('Pieces overlap on ' + str(i) + str(j) + '\n')
                        return False
                        
        else:
            print('Piece falls out of the world\n')
            return False
        return True
        
    def print_piece(self, x, y, number, piece):
        s = 'Piece ' + str(number) + '\nPosition ' + str(x) + str(y) + '\n'
        for line in piece:
            for elem in line:
                s = s + str(elem) + ' '
            s = s + '\n'
        print(s)
        
        
    def print_board(self):
        s = ''
        for line in self.board:
            for elem in line:
                s = s + str(elem) + ' '
            s = s + '\n'
        print(s)
        

def sudoku(trials):
    
    example = input('Which example to run: ')
    
    if example == '1': 
        s = Sudoku('sudo4.txt', 4)
    else:
        s = Sudoku('sudo9.txt', 9)
    
    counter = 0
    
    for i in range(trials):
        print('Try ' + str(i + 1))
        s.read_table()
        if s.solve():
            return
            #counter += 1
        #s.print_table()
    print('Success: ' + str(counter))
    
    

def crypto(trials):
    
    example = input('Which example to run: ')
    
    if example == '1': 
        c = Crypto('crypto1.txt')
    elif example == '2': 
        c = Crypto('crypto2.txt')
    elif example == '3': 
        c = Crypto('crypto3.txt') 
    else: 
        c = Crypto('crypto4.txt')
    
    c.read_file()
    
    counter = 0
    for i in range(trials):
        print('Try ' + str(i + 1))
        if c.solve():
            counter += 1
    print('Success: ' + str(counter))
    

def geometry(trials):
    g = Geometric('forms.txt')
    
    g.read_file()
    counter = 0
    
    for i in range(trials):
        print('Try ' + str(i + 1))
        if g.get_sample():
            counter += 1
        g.print_board()
    print('Success: ' + str(counter))

def console():
    #while True:
    print('''
          0. Exit\n
          1. Sudoku\n
          2. Cryptarithmetic game\n
          3. Geometric forms puzzle''')
    
    c = input('Your choice: ')
    
    if c == '0':
        return
    
    trials = int(input('How many trials: '))
    
    if c == '1':
        sudoku(trials)
    elif c == '2':
        crypto(trials)
    elif c == '3':
        geometry(trials)
    else:
        print('Not valid')
    
console()