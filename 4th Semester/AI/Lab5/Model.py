# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:12:47 2020

@author: mosda
"""
import random
from datetime import datetime

class Place:
    def __init__(self, size):
        self.size = size
        self.body = self.set_body()

    def get_size(self):
        return self.size

    def set_body(self):
        '''
        Get a random double permutation of size self.size
        :return: a list of random numbers in the range 0, size - 1
        '''
        # random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))

        perm1 = random.sample(range(1, self.size + 1), self.size)
        perm2 = random.sample(range(1, self.size + 1), self.size)
        return [perm1, perm2]

    def get_body(self):
        return self.body


    def fitness(self, individual):
        '''
        If the pairs are repeating in the matrix, increase fitness by 1
        The lower the fitness, the better the candidate. Will be the cost
        between the current vertex and the given one
        :param individual: the individual to check
        :return: fitness lvl
        '''
        fit = 0

        try:
            for a, b in zip(self.body[0], individual.get_body()[0]):
                if a == b:
                    fit += 1
            for c, d in zip(self.body[1], individual.get_body()[1]):
                if c == d:
                    fit += 1
        except IndexError:
            return fit
        return fit

    def __str__(self):
        s = ''
        for e1, e2 in zip(self.body[0], self.body[1]):
            s = s + f'{e1}, {e2} | '
        return s

class Paths:
    def __init__(self, size, nodes):
        self.size = size
        self.nodes = nodes
        self.list_of_nodes = self.get_nodes()
        self.list_of_paths = self.compute_paths()
    
    def get_size(self):
        return self.nodes

    def get_list_nodes(self):
        return self.list_of_nodes

    def get_paths(self):
        return self.list_of_paths

    def get_nodes(self):
        nodes_local = []
        for i in range(self.nodes):
            nodes_local.append(Place(self.size))
        return nodes_local

    def compute_paths(self):
        paths_local = []
        for node1 in self.list_of_nodes:
            paths_local.append([])
            for node2 in self.list_of_nodes:
                if node1 != node2:
                    paths_local[-1].append(node1.fitness(node2))
                else:
                    paths_local[-1].append(None)
        return paths_local

    def get_initial_pheromone_matrix(self, initial=1):
        phero_local = []
        for node1 in self.list_of_nodes:
            phero_local.append([])
            for node2 in self.list_of_nodes:
                if node1 != node2:
                    phero_local[-1].append(initial)
                else:
                    phero_local[-1].append(None)
        return phero_local

    def __str__(self):
        s = ''
        for i in range(len(self.list_of_nodes)):
            for j in range(len(self.list_of_nodes)):
                s += f'{self.list_of_nodes[i]}-> {self.list_of_nodes[j]}: {self.list_of_paths[i][j]}\n'
            s += '\n'
        return s

# p = Paths(5, 3)
# print(p.get_paths())
# print(p.get_initial_pheromone_matrix())

class Ant:
    def __init__(self, first_position):
        self.solution = [first_position]
        self.distance = 0

    def leave_pheromone(self):
        return float("{:.6f}".format(1 ** (-self.distance)))

    def set_path(self, path):
        self.solution.append(path)

    def get_solution(self):
        '''

        :return: list of coordinates in the cost matrix as tuples
        '''
        return self.solution

    def add_to_distance(self, distance):
        '''

        :param distance: the new cost
        :return: distance travelled
        '''
        self.distance += distance

    def get_distance(self):
        return self.distance

    def next_move(self, paths, pheromones, alpha, beta):
        # get current position
        current_position = self.solution[-1]
        # get possible next paths and the lvl of pheromone on each corresponding edge
        possible_paths = paths[current_position[0]]
        phero = pheromones[current_position[0]]
        # probabilities for choosing each path
        probabilities = []
        sum_paths = 0
        for path, ph in zip(possible_paths, phero):
            if path is not None and ph is not None:
                try:
                    sum_paths += (ph ** alpha) * ((1 / path)**beta)
                except ZeroDivisionError:
                    pass
        for path, ph in zip(possible_paths, phero):
            if path is not None and ph is not None:
                try:
                    probabilities.append(((ph ** alpha) * ((1 / path)**beta))/sum_paths)
                except ZeroDivisionError:
                    pass
        roulette = []

        for p in range(len(probabilities)):
            roulette.append(sum(probabilities[p:]))
        roulette2 = roulette + [0]
        r = random.random()

        for i in range(len(roulette2) - 1):
            if r < roulette2[i] and r >= roulette2[i + 1] and possible_paths[i] is not None:
                self.add_to_distance(possible_paths[i])
                self.set_path((current_position[0], i))
                break





