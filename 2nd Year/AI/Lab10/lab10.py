# -*- coding: utf-8 -*-
"""
Created on Thu May 21 14:37:13 2020

@author: mosda
"""
from Model import *
from Controller import Controller
from View import View


def prepare_data():
    data = DataHandler()
    temperature, capacity, power, rules = data.get_data()
    inp = data.get_input()

    fuzzy_temp = FuzzyVariable('temperature', temperature)
    for line in temperature:
        fuzzy_temp.add_region(line[:-1], line[-1])

    fuzzy_cap = FuzzyVariable('capacity', capacity)
    for line in capacity:
        fuzzy_cap.add_region(line[:-1], line[-1])

    fuzzy_sys = FuzzySystem(rules, {'temperature': fuzzy_temp, 'capacity': fuzzy_cap})

    return fuzzy_sys, inp, power


if __name__ == '__main__':
    fs, inp, power = prepare_data()
    ctrl = Controller(fs, power)
    ui = View(ctrl, inp)
    ui.run()