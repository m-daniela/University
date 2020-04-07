
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:14:04 2020

@author: mosda
"""
from View import UI
from Controller import Controller, Testing
from Model import Paths


if __name__ == "__main__":
    size = int(input('Size of permutation: '))
    ants = int(input('Number of ants: '))
    nodes = int(input('Number of nodes: '))
    iterations = int(input('Iterations: '))
    p = Paths(size, nodes)
    test = Testing()
    ctrl = Controller(size, ants, p, iterations)
    ui = UI(ctrl, test)
    ui.run()
