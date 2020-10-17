# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:38 2020

@author: mosda
"""
import random
import math

from PyQt5.QtCore import QThread, pyqtSignal
import matplotlib.pyplot as plt
import numpy as np


class Controller(QThread):

    sig_pso = pyqtSignal(str)

    def __init__(self, population, iterations, w=1.0, c1=1.0, c2=2.5):
        super().__init__()
        self.population = population
        self.iterations = iterations
        self.w = w
        self.c1 = c1
        self.c2 = c2

    def iteration(self):
        best_ngh = []
        for particle in self.population:
            best_ngh.append(self.get_best_ngh(particle))

        for particle, neigh in zip(self.population, best_ngh):
            self.update_velocity(particle, neigh[1])

        for particle, neigh in zip(self.population, best_ngh):
            particle.place(neigh[1], self.get_prob(particle))

        return self.population


    def pso(self):
        for i in range(self.iterations):
            p = self.iteration()
            # for x in p:
            self.sig_pso.emit(self.to_str(p))


    def get_best_ngh(self, particle):
        ngh = particle.get_neighbours()
        best_ngh = [(particle.fitness(x), x) for x in ngh]
        best_ngh.sort(key=lambda x: x[0])
        # self.save_to_file('pso.txt', best_ngh[0])
        return best_ngh[0]

    def update_velocity(self, particle, best_ngh):
        velo = self.w * particle.get_velocity() + self.c1 * random.random() * (best_ngh.get_position() - particle.get_position()) + self.c2 * random.random() * self.manhattan(particle, best_ngh)
        particle.set_velocity(velo / 100)
        return particle

    def manhattan(self, particle1, particle2):
        s = 0
        for a, b in zip(particle1.get_body()[0], particle2.get_body()[0]):
            s += abs(a-b)
        for a, b in zip(particle1.get_body()[1], particle2.get_body()[1]):
            s += abs(a-b)
        return s

    def get_prob(self, particle):
        return math.pow(1, -(1 + math.exp(-particle.get_position())))


    def save_to_file(self, filename, fittest):
        with open(filename, 'a') as f:
            f.write(f'{fittest[0]},{fittest[1]}\n')

    def to_str(self, population):
        s = ''
        for x in population:
            s += str(x) + '\n'
        s += '\n\n'
        return s

    def run(self):
        self.pso()

class Testing:
    '''
    Validation function
    '''
    def show(self, filename='pso2.txt'):
        fitness = []
        with open(filename, 'r') as f:
            lines = f.readlines()
            for line in lines:
                nr = line.split(',')[0]
                fitness.append(int(nr))

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

