# -*- coding: utf-8 -*-
"""
Created on Fri May 22 12:31:24 2020

@author: mosda
"""

class View:
    def __init__(self, controller, data):
        self.cmd = '0. Exit\n' \
                   '1. Example 1\n' \
                   '2. Example 2'
        self.controller = controller
        self.data = data

    def run(self):
        while True:
            print(self.cmd)
            x = input('Your choice: ')
            if x == '0':
                return
            elif x == '1':
                print(self.controller.evaluate(self.data[0]))
            elif x == '2':
                print(self.controller.evaluate(self.data[1]))
            else:
                print('Wrong input')
