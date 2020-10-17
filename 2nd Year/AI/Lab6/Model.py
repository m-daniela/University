# -*- coding: utf-8 -*-
"""
Created on Fri Apr 10 11:01:46 2020

@author: mosda
"""

import random

class DataHandler:
    def __init__(self, filename='DECISION/balance-scale.data'):
        self.filename = filename


    def read_file(self):
        # parse the file
        data = []
        with open(self.filename, 'r') as f:
            lines = f.readlines()

        for line in lines:
            data.append(line.strip().split(','))
        return data

    def split(self, dataset):
        # split dataset in classes and classes + attributes
        cls = []
        attr = []
        for data in dataset:
            cls.append(data[0])
            attr.append([data[0]])
            for nr in data[1:5]:
                attr[-1].append(int(nr))
        return cls, attr

    def split_dataset(self, dataset):
        # split dataset in training data and testing data
        p = random.random()
        random.shuffle(dataset)
        result, data = self.split(dataset)
        train = data[:int((len(data) + 1) * p)]
        train_result = result[:int((len(result) + 1) * p)]
        test = data[int(len(data) * p + 1):]
        test_result = result[int(len(result) * p + 1):]
        return train, train_result, test, test_result

    def get_data(self):
        return self.split_dataset(self.read_file())


class Node:
    def __init__(self, label, values):
        self.label = label
        self.values = values
        self.edges = []
        self.nodes = []

    def get_label(self):
        return self.label

    def set_label(self, label):
        self.label = label

    def get_edges(self):
        return self.edges

    def add_edge(self, edge):
        self.edges.append(edge)

    def get_values(self):
        return self.values

    def set_values(self, values):
        self.values = values

    def add_node(self, node):
        self.nodes.append(node)

    def get_nodes(self):
        return self.nodes

    def __str__(self):
        s = f'{self.label}\n{self.values}\n------\n'
        for edge, node in zip(self.edges, self.nodes):
            s+= f'Edge: {edge}\nNode: {node}'
        return s

