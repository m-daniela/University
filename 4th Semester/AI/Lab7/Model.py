# -*- coding: utf-8 -*-
"""
Created on Wed Apr 15 15:29:59 2020

@author: mosda
"""
import random
class DataHandler:
    def __init__(self, filename='data.txt'):
        self.filename = filename

    def get_data(self):
        # get random data from the database
        # split it in training data, testing data
        data = []
        with open(self.filename, 'r') as f:
            lines = f.readlines()

        for line in lines:
            if line != '' and line != '\n' and line[0] != 'B':
                values = line.strip().split(' ')
                data.append([])
                for v in values:
                    data[-1].append(float(v))
        p = random.random()
        random.shuffle(data)

        return data[:int(len(data) * p)], data[int(len(data) * p + 1):]

