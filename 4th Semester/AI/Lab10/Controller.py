# -*- coding: utf-8 -*-
"""
Created on Fri May 22 12:31:31 2020

@author: mosda
"""

class Controller:
    def __init__(self, fuzzy_system, output):
        self.fuzzy_system = fuzzy_system
        self.output = output

    def evaluate(self, values):
        fuzzy_values = self.fuzzy_system.evaluate(values)
        aggregate_values, aggregate_variable = self.fuzzy_system.aggregate(fuzzy_values, self.output)
        result = aggregate_variable.defuzzify(aggregate_values)

        s = f'Input: {values}\n' \
            f'Values for the aggregated regions: {aggregate_values}\n' \
            f'Result: {result}\n\n'
        self.save_output(s)
        return s

    def save_output(self, s, filename='output.out'):
        with open(filename, 'a') as f:
            f.write(s)
