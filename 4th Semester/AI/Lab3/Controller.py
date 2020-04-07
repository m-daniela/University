# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:38 2020

@author: mosda
"""
import random
from datetime import datetime

from PyQt5.QtCore import QThread, pyqtSignal
import matplotlib.pyplot as plt
import numpy as np


class Controller(QThread):

    sig_ea = pyqtSignal(str)
    sig_hc = pyqtSignal(str)

    def __init__(self, problem, individuals, iterations, crossover_prob=0.8, mutation_prob=0.1):
        super().__init__()
        self.problem = problem
        self.individuals = individuals
        self.pop = self.get_initial_population(individuals)
        self.iterations = iterations
        self.crossover_prob = crossover_prob
        self.mutation_prob = mutation_prob
        self.fittest_ea = False
        self.fittest_hc = False

    def iteration(self):

        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))

        ind1 = random.randint(0, len(self.pop) - 1)
        ind2 = random.randint(0, len(self.pop) - 1)

        if ind1 != ind2:
            cross = self.problem.crossover(self.pop[ind1], self.pop[ind2], self.crossover_prob)
            mutant = self.problem.mutate(cross, self.mutation_prob)

            f1 = self.problem.fitness(self.pop[ind1])
            f2 = self.problem.fitness(self.pop[ind2])
            f3 = self.problem.fitness(mutant)

            if f1 >= f2 and f1 >= f3:
                self.pop[ind1] = mutant
            if f1 < f2 and f2 > f3:
                self.pop[ind2] = mutant

        return self.pop

    def ea(self):
        '''
        Evolutionary algorithm. Emits signals of the best of the iteration
        and its fitness
        :return:
        '''
        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))

        self.get_initial_population(self.individuals)
        local_board = []


        for x in range(self.problem.get_size()):
            fittest, howfit = self.get_fittest()
            self.sig_ea.emit(f'{fittest} with fitness: {howfit}')
            local_board.append(fittest)
        self.problem.set_board()
        self.sig_ea.emit(self.to_str(local_board))


    def get_fittest(self):

        for i in range(self.iterations):
            self.iteration()
        fitboys = [(self.problem.fitness(x), x) for x in self.pop]
        fitboys.sort()
        howfit, fittest = fitboys[0]
        # self.save_to_file('ea.txt', fitboys[0])

        self.problem.add_to_board(fittest)

        return (fittest, howfit)

    def hc(self):
        '''
        Hill climbing algorithm. Emits signals of the permutation that was chosen and its fitness
        :return:
        '''
        count = 0
        size = self.problem.get_size()
        pop = self.problem.population(size)
        for x in pop:
            self.problem.add_to_board(x)

        while count < self.iterations:
            count += 1
            # random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))
            pos = random.randint(0, size - 1)
            old_perm = pop[pos]
            old_fitness = self.problem.fitness(old_perm)
            perms = self.problem.get_all(old_perm)
            randperm = perms[random.randint(0, len(perms) - 1)]
            self.problem.place(randperm, pos)
            new_fitness = self.problem.fitness(randperm)

            self.sig_hc.emit(f'{randperm} with fitness: {new_fitness}')

            if old_fitness < new_fitness:
                self.problem.place(old_perm, pos)
        self.sig_hc.emit(self.to_str(self.problem.get_board()))
        return self.to_str(self.problem.get_board())



    def save_to_file(self, filename, fittest):
        with open(filename, 'a') as f:
            f.write(f'{fittest[0]},{fittest[1]}\n')


    def get_initial_population(self, individuals):
        return self.problem.population(individuals)

    def to_str(self, board):
        s = ''
        for line in board:
            for e1, e2 in zip(line[0], line[1]):
                s = s + f'{e1}, {e2} | '
            s = s + '\n'
        return s

    def run(self):
        if self.fittest_ea == True:
            self.ea()
        if self.fittest_hc == True:
            self.hc()

class Testing:
    '''
    Validation function
    '''
    def show(self, filename='ea.txt'):
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

#t = Testing()
#t.show()













