# -*- coding: utf-8 -*-
"""
Created on Fri Apr 10 11:01:37 2020

@author: mosda
"""

class View:
    def __init__(self, ctrl, testing):
        self.ctrl = ctrl
        self.testing = testing
        self.cmd = '''
            0. Exit
            1. Decision Tree
            2. Test tree
            3. Show tests
        '''
    def run(self):
        while True:
            print(self.cmd)
            x = input('Your choice: ')
            if x == '0':
                return
            elif x == '1':
                print(self.ctrl.dt())
            elif x == '2':
                print(self.ctrl.test_the_tree())
            elif x == '3':
                print(self.testing.show_tests())
            else:
                print('Wrong input')
