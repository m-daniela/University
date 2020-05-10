# -*- coding: utf-8 -*-
"""
Created on Wed Apr 15 15:30:23 2020

@author: mosda
"""

class View:
    def __init__(self, ctrl):
        self.ctrl = ctrl
        self.cmd = '''
            0. Exit
            1. Least Squares Method
            2. Test
        '''

    def run(self):
        while True:
            print(self.cmd)
            x = input('Your choice: ')
            if x == '0':
                return
            elif x == '1':
                print(self.ctrl.least_squares())
                # return
            elif x == '2':
                # print(f'Success: {self.ctrl.test_results()}')
                self.ctrl.test_results()
                return
            else:
                print('Wrong input')

