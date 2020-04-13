# -*- coding: utf-8 -*-
"""
Created on Fri Apr 10 11:00:50 2020

@author: mosda
"""

from Controller import Controller, Testing
from Model import DataHandler
from View import View

if __name__ == '__main__':
    d = DataHandler('DECISION/balance-scale.data')
    c = Controller(d)
    t = Testing()
    v = View(c, t)
    v.run()