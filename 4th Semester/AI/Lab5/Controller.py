# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:38 2020

@author: mosda
"""
import random
from datetime import datetime
from Model import Ant

import matplotlib.pyplot as plt
import numpy as np

class Controller:

    def __init__(self, goal, nr_ants, paths, iterations, ro=0.8, alpha=1, beta=1):
        self.goal = goal # how many permutations are needed
        self.nr_ants = nr_ants
        self.ants = []
        self.paths = paths
        self.iterations = iterations
        self.ro = ro
        self.alpha = alpha
        self.beta = beta
        self.nodes = self.paths.get_list_nodes()
        self.costs = self.paths.get_paths()
        self.pheromones = self.paths.get_initial_pheromone_matrix()

    def iteration(self):
        for ant in self.ants:
            steps = 0
            while steps < self.goal:
                steps += 1
                ant.next_move(self.costs, self.pheromones, self.alpha, self.beta)
        self.compute_pheromone_matrix()
        self.evaporate()

    def aco(self):
        for i in range(self.iterations):
            random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))
            self.ants = [Ant((random.randint(0, self.paths.get_size() - 1), random.randint(0, self.paths.get_size() - 1))) for x in range(self.nr_ants)]
            self.iteration()
            # print(self.to_str(self.pheromones))
        self.best_path()

    def compute_pheromone_matrix(self):
        for ant in self.ants:
            path = ant.get_solution()
            for place in path:
                if self.pheromones[place[0]][place[1]] is not None:
                    self.pheromones[place[0]][place[1]] += ant.leave_pheromone()


    def evaporate(self):
        phero_local = []
        for i in range(len(self.pheromones)):
            phero_local.append([])
            for j in range(len(self.pheromones)):
                if self.pheromones[i][j] is not None:
                    phero_local[-1].append(float("{:.6f}".format((1 - self.ro) * self.pheromones[i][j])))
                else:
                    phero_local[-1].append(None)
        self.pheromones = phero_local
        return self.pheromones

    def best_path(self):
        best = []
        fitness = []
        for i in range(len(self.pheromones)):
            new_list = self.pheromones[i][:self.pheromones[i].index(None)] + self.pheromones[i][self.pheromones[i].index(None) + 1:]
            x = max(list(new_list), key=float)
            j = self.pheromones[i].index(x)
            best.append((i, j))
            fitness.append(self.costs[i][j])
        
        print(fitness)
        #self.save_to_file('aco.txt', fitness)

    def save_to_file(self, filename, fittest):
        with open(filename, 'a') as f:
            for nr in fittest:
                f.write(f'{nr},')

    def to_str(self, population):
        s = ''
        for x in population:
            s += str(x) + '\n'
        s += '\n'
        return s


class Testing:
    '''
    Validation function
    '''
    def show(self, filename='aco.txt'):
        fitness = []
        with open(filename, 'r') as f:
            lines = f.readline().split(',')
            lines = lines[:-1]
            fitness = [int(x) for x in lines]

        arr = np.array(fitness)
        m = np.mean(arr, axis=0)
        std = np.std(arr, axis=0)
        means = []
        dev = []
        for i in range(100):
            means.append(m)
            dev.append(std)

        plt.axis([0, 100, 0, 10])
        plt.plot(fitness, '#f90202', linestyle='-')
        plt.plot(means, '#05f4fc', linestyle='-')
        plt.plot(dev, '#000000', linestyle='-')
        plt.show()
                 
                

