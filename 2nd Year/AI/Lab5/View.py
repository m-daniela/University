# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:27 2020

@author: mosda
"""
from Controller import Testing

class UI:
    def __init__(self, ctrl, test):
        self.ctrl = ctrl
        self.test = test
        print('''
            0. Exit
            1. ACO
            2. Test
        ''')

    def run(self):
        
        while True:
            x = input('Your choice: ')
            if x == '1':
                self.ctrl.aco()
            elif x == '2':
                self.test.show()
            elif x == '0':
                return
            else:
                print('Wrong input')