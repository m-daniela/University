# -*- coding: utf-8 -*-
"""
Created on Fri May 22 12:31:19 2020

@author: mosda
"""

def trapezoid(a, b, c, d):
    if a == b and c == d:
        return lambda x: x
    elif a == b:
        return lambda x: max(0., min(1., (d - x) / (d - c)))
    elif c == d:
        return lambda x: max(0., min((x - a) / (b - a), 1.))
    else:
        return lambda x: max(0., min((x - a) / (b - a), 1., (d - x) / (d - c)))

def triangle(a, b, c):
    return trapezoid(a, b, b, c)

class DataHandler:
    def __init__(self, filename='problem.in'):
        self.filename = filename

    def get_data(self):
        with open(self.filename, 'r') as f:
            lines = f.readlines()
        groups = [[]]
        for line in lines:
            if line == '\n':
                groups.append([])
            else:
                groups[-1].append(line.strip().split(','))

        for i in range(len(groups) - 1):
            for j in range(len(groups[i])):
                groups[i][j] = [float(y) for y in groups[i][j][:-1]] + [groups[i][j][-1]]
        return groups[0], groups[1], groups[2], groups[3]

    def get_input(self, filename='input.in'):
        with open(filename, 'r') as f:
            lines = f.readlines()
        inp = [[]]
        for line in lines:
            if line == '\n':
                inp.append([])
            else:
                name, value = line.strip().split(',')
                inp[-1].append(name)
                inp[-1].append(float(value))
        return inp


class FuzzyVariable:
    def __init__(self, name, values):
        self.name = name
        self.regions = {}
        self.values = [values[0][0], values[-1][-2]]

    def get_values(self):
        return self.values

    def get_regions(self):
        return self.regions

    def add_region(self, values, label):
        if len(values) == 3:
            self.regions[label] = triangle(values[0], values[1], values[2])
        if len(values) == 4:
            self.regions[label] = trapezoid(values[0], values[1], values[2], values[3])

    def fuzzy(self, value):
        results = {}
        for key in list(self.regions.keys()):
            results[key] = self.regions[key](value)
        return results

    def defuzzify(self, fuzzy_results):
        sum1, sum2 = 0, 0
        for i in range(int(self.values[0]), int(self.values[1]) + 1):
            max1 = max([i * fuzzy_results[x] * self.fuzzy(i)[x] for x in list(self.regions.keys())])
            max2 = max([fuzzy_results[x] * self.fuzzy(i)[x] for x in list(self.regions.keys())])
            sum1 += max1
            sum2 += max2
        return sum1 / sum2

class FuzzySystem:
    def __init__(self, rules, variables):
        self.rules = rules
        self.variables = variables


    def evaluate(self, values):
        results_temp = self.variables[values[0]].fuzzy(values[1])
        results_cap = self.variables[values[2]].fuzzy(values[3])
        fuzzy_values = {}
        for i in range(len(self.rules)):
            x = results_temp[self.rules[i][0]]
            y = results_cap[self.rules[i][1]]
            fuzzy_values[(self.rules[i][0], self.rules[i][1])] = (self.rules[i][2], min(x, y))
        return fuzzy_values

    def aggregate(self, fuzzy_values, power):
        agg = {}
        for value in list(fuzzy_values.values()):
            try:
                agg[value[0]] = max(value[1], agg[value[0]])
            except KeyError:
                agg[value[0]] = value[1]

        return agg, self.aggregateVariable(agg, power)

    def aggregateVariable(self, agg, output):
        new_values = []
        for line in output:
            values = line[:-1]
            prob = agg[line[-1]]
            aggv = [values[0]]
            for x in range(1, len(values)):
                aggv.append(aggv[-1] + prob * abs(values[0] - values[x]))
            aggv.append(values[x])
            aggv.append(line[-1])
            new_values.append(aggv)

        afv = FuzzyVariable('aggregate', new_values)
        for line in new_values:
            afv.add_region(line[:-1], line[-1])

        return afv