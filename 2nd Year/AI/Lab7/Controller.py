# -*- coding: utf-8 -*-
"""
Created on Wed Apr 15 15:30:28 2020

@author: mosda
"""

import sympy

class Controller:
    def __init__(self, data_handler):
        self.data, self.test = data_handler.get_data()
        self.results = None

    def least_squares(self):
        # finds the coefficients from the given dataset
        sum_eq = []
        symbols = sympy.symbols('b0 b1 b2 b3 b4 b5')

        for values in self.data:
            equations = []
            for sym in symbols:
                f = sympy.diff((values[5] - (symbols[0] + symbols[1] * values[0] + symbols[2] * values[1] +
                                             symbols[3] * values[2] + symbols[4] * values[3] + symbols[5] *
                                             values[4])) ** 2, sym)
                equations.append(f)
            sum_eq.append(equations)

        equations2 = []
        for j in range(len(sum_eq[0])):
            s = 0
            for i in range(len(sum_eq)):
                s += sum_eq[i][j]
            equations2.append(s)
        self.results = list(sympy.linsolve(equations2, symbols))
        return self.results

    def test_results(self):
        # tests the coefficients
        # shows the numbers and the error
        b0, b1, b2, b3, b4, b5 = self.results[0]
        for v in self.test:
            eq = b0 + b1 * v[0] + b2 * v[1] + b3 * v[2] + b4 * v[3] + b5 * v[4]

            print(v[5], eq)
            try:
                print(f'Error: {"{:.13f}".format(abs(v[5] - eq))}')
            except Exception:
                print(0)
            print()
