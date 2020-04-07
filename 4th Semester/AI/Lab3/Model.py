# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:12:47 2020

@author: mosda
"""
import random
import itertools
from datetime import datetime

class Individual:
    def __init__(self, size):
        self.size = size
        self.board = []
        self.set_board()


    def get_size(self):
        return self.size

    def set_board(self):
        self.board = []
        for x in range(self.size):
            self.board.append([])

    def get_board(self):
        return self.board

    def add_to_board(self, individual):
        for i in range(len(self.board)):
            if len(self.board[i]) == 0:
                self.board[i] = individual
                break
        return self.board

    def place(self, individual, pos):
        '''
        Place the individual on the board
        :param individual:
        :param pos:
        :return:
        '''
        self.board[pos] = individual

    def individual(self):
        '''
        Get a random individual
        :return: a list of random numbers in the range 0, size - 1
        '''
        # random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))

        perm1 = random.sample(range(1, self.size + 1), self.size)
        perm2 = random.sample(range(1, self.size + 1), self.size)
        return [perm1, perm2]

    def get_all(self, individual):
        '''
        Get all permutations except the given one
        :param individual:
        :return:
        '''
        return list(itertools.permutations(individual))

    def population(self, count):
        '''
        Get a population of count members
        :param count: how many individuals (permutations)
        :return: list of count individuals
        '''
        return [self.individual() for x in range(count)]

    def fitness(self, individual):
        '''
        If the pairs are repeating in the matrix, increase fitness by 1
        The lower the fitness, the better the candidate
        :param individual: the individual to check
        :param current: the current state of the board
        :return: fitness lvl
        '''
        fit = 0
        for x in range(len(self.board)):
            try:
                for a, b in zip(self.board[x][0], individual[0]):
                    if a == b:
                        fit += 1
                for c, d in zip(self.board[x][1], individual[1]):
                    if c == d:
                        fit += 1
            except IndexError:
                return fit
        return fit

    def crossover(self, parent1, parent2, crossover_prob):
        '''
        Order crossover (with 2 cuts)
        Place the sequence from parent1 between the cuts in position
        Complete the new individual until the end with the rest of
        parent 2
        Complete the rest of the new individual with the numbers that
        are not already existing in the new individual
        :param parent1: individual to make the crossover
        :param parent2: individual to make the crossover
        :return: the new individual
        '''

        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))
        if crossover_prob > random.random():


            pos1 = random.randint(0, len(parent1[0]))
            pos2 = random.randint(pos1, len(parent1[0]))

            if pos1 == pos2:
                return parent2
            else:
                new_born1 = self.result_crossover(parent1[0], parent2[0], pos1, pos2)
                new_born2 = self.result_crossover(parent1[1], parent2[1], pos1, pos2)
                return [new_born1, new_born2]

        else:
            return parent1

    def result_crossover(self, parent1, parent2, pos1, pos2):
        '''
        Crossover on one half of the given parents
        :param parent1:
        :param parent2:
        :param pos1:
        :param pos2:
        :return:
        '''
        subs1 = parent1[pos1:pos2]
        subs2 = parent2[pos1:pos2]
        end = parent2[pos2:]
        start = parent2[:pos1]
        aux = []

        for x in subs2:
            if x not in subs1:
                aux.append(x)

        subs2 = aux

        for y in end:
            if y in subs1:
                end[end.index(y)] = subs2.pop(0)

        for z in start:
            if z in subs1 and z not in end:
                start[start.index(z)] = subs2.pop(0)

        new_born = start + subs1 + end

        return new_born

    def mutate(self, individual, mutation_prob):
        '''
        Perform a mutation on the given individual
        :param individual: the individual to mutate
        :return: the new individual
        '''
        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))

        if mutation_prob > random.random():
            random.shuffle(individual[0])
            random.shuffle(individual[1])
        return individual


    # def __str__(self):
    #     s = ''
    #     for line in self.board:
    #         for element in line:
    #             s = s + str(element) + ' '
    #         s = s + '\n'
    #     return s

