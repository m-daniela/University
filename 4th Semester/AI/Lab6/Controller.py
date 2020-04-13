# -*- coding: utf-8 -*-
"""
Created on Fri Apr 10 11:01:17 2020

@author: mosda
"""

import math
from Model import Node

class Controller:
    def __init__(self, data):
        self.data = data
        self.train = []
        self.train_result = []
        self.test = []
        self.test_result = []
        self.set_data()
        self.tree = None

    def set_data(self):
        self.train, self.train_result, self.test, self.test_result = self.data.get_data()

    def dt(self):
        self.tree = self.generate(self.train, [1, 2, 3, 4])
        return self.tree

    def generate(self, partition, attributes):
        '''

        :param partition: partition of the training data
        :param attributes: list of attributes (lw, ld, rw, rd)
        :return:
        '''
        node = Node(None, partition)
        # check if all elements in partition belong to a class
        check = self.check_class(partition)
        if check[0]:
            node.set_label(check[1])
            return node
        else:
            if len(attributes) == 0:
                node = partition[0][0]
                return node
            else:
                separation_attribute = self.attr_selection(partition, attributes)
                node.set_label(separation_attribute)
                values, instances = self.get_values(partition, separation_attribute)
                for v in values:
                    print(v, values)
                    partition2 = []
                    for i in range(len(partition)):
                        if partition[i][separation_attribute] == v:
                            partition2.append(partition[i])

                    if len(partition2) == 0:
                        node.add_edge(v)
                        node.add_node(Node(separation_attribute, partition))
                    else:
                        attributes2 = attributes[:attributes.index(separation_attribute)] + \
                                      attributes[attributes.index(separation_attribute) + 1:]
                        node.add_edge(v)
                        node.add_node(self.generate(partition2, attributes2))
                return node


    def entropy(self, partition):
        # get the entropy of the partition
        cls = []
        for i in range(len(partition)):
                cls.append(partition[i][0])

        values = ['L', 'R', 'B']
        final = 0.0
        total = len(partition)
        for v in values:
            a = cls.count(v)
            try:
                final += -(a/total)*math.log2(a/total)
            except Exception:
                final += 0
        return final, total

    def average(self, partition, attr):
        # entropy for each partition for the given attribute
        # return the average of the results
        entropy_values = []
        totals = []
        values, lines = self.get_values(partition, attr)

        for v in values:
            partition2 = []
            for i in range(len(partition)):

                if partition[i][attr] == v:
                    partition2.append(partition[i])
            e = self.entropy(partition2)
            entropy_values.append(e[0])
            totals.append(e[1])
        avg = 0
        for a, b in zip(totals, entropy_values):
            avg += (a/len(partition))*b
        return avg

    def info_gain(self, partition, attr):
        target_entropy = self.entropy(partition)[0]
        return float("{:.6f}".format(target_entropy - self.average(partition, attr)))

    def attr_selection(self, partition, attributes):
        # return the label of the selected attr
        gains = []
        for attr in attributes:
            gains.append((self.info_gain(partition, attr), attr))
        gains.sort(key=lambda x: x[0], reverse=True)
        return gains[0][1]

    def get_values(self, partition, attribute):
        # get the values of given attr from the partition
        values = []
        lines = []
        for instance in partition:
            if instance[attribute] not in values:
                values.append(instance[attribute])
            lines.append(instance)
        return values, lines

    def check_class(self, partition):
        # check if the partition belongs to a single class
        # if so, return true and the name of the class
        # else return false and None
        try:
            cls = partition[0][0]
            for instance in partition:
                if instance[0] != cls:
                    return False, None
            return True, cls
        except Exception:
            return False, None


    def test_the_tree(self):
        results = []
        for data in self.test:
            results.append(self.recursive_test(data, self.tree))
        return self.check_if_good(results)

    def recursive_test(self, data, node):
        label = node.get_label()
        if isinstance(label, int):
            edge = data[label]
            edges = node.get_edges()
            nodes = node.get_nodes()
            if edge in edges:
                new_node = nodes[edges.index(edge)]
                return self.recursive_test(data, new_node)
        elif isinstance(label, str):
            data.append(label)
            return data

    def check_if_good(self, result):
        # count how many instances where correctly classified
        good = 0
        for element in result:
            if element is not None and element[0] == element[-1]:
                good += 1
        try:
            return float("{:.6f}".format(good/len(result)))
        except ZeroDivisionError:
            return 0


class Testing:
    def show_tests(self, filename='test.txt'):
        with open(filename, 'r') as f:
            lines = f.readlines()
            for line in lines:
                result, runs = line.strip().split(',')
                print(f'Best in {runs} runs {result}')

