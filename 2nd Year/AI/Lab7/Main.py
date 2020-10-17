# -*- coding: utf-8 -*-
"""
Created on Wed Apr 15 15:30:33 2020

@author: mosda
"""

from Model import DataHandler
from Controller import Controller
from View import View

if __name__ == '__main__':
    d = DataHandler('database.txt')
    ctrl = Controller(d)
    v = View(ctrl)
    v.run()