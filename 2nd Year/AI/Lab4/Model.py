# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:12:47 2020

@author: mosda
"""
import random
from datetime import datetime

class Particle:
    def __init__(self, size, velocity=0):
        self.size = size
        self.velocity = velocity
        self.position = 0
        self.body = self.set_body()
        self.prev_pos = 0
        self.neighbours = []

    def get_size(self):
        return self.size

    def get_velocity(self):
        return self.velocity

    def set_velocity(self, velocity):
        self.velocity = velocity

    def get_position(self):
        return self.position

    def set_position(self, position):
        self.position = position

    def place(self, particle, prob):
        '''
        Swap positions with the given particle
        :param particle:
        :param prob:
        :return: the other particle, with the new position
        '''
        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))
        if prob > random.random():
            self.prev_pos = self.position
            self.position = particle.get_position()
            particle.set_position(self.prev_pos)
            return particle


    def set_body(self):
        '''
        Get a random individual
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
        The lower the fitness, the better the candidate
        :param individual: the individual to check
        :param current: the current state of the board
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

    def set_neighbours(self, neighbours):
        self.neighbours = neighbours

    def get_neighbours(self):
        return self.neighbours

    def __str__(self):
        s = ''
        for e1, e2 in zip(self.body[0], self.body[1]):
            s = s + f'{e1}, {e2} | '
        s = s + f', velocity: {self.velocity}, position: {self.position}'
        return s

class Population:
    def __init__(self, particles):
        '''

        :param particles: how many particles in the population
        '''
        self.particles = particles
        self.pop = []


    def get_population(self, size):
        '''

        :param size: size of each particle
        :return:
        '''
        for x in range(self.particles):
            self.pop.append(Particle(size))
        return self.pop

    def __str__(self):
        s = ''
        for particle in self.pop:
            s += particle + '\n'
        s += '\n'
        return s

class Neighbourhood:
    def __init__(self, population):
        '''

        :param n: nr of neighbourhoods
        :param population:
        '''
        self.pop = population
        self.nghs = []

    def split_pop(self):
        random.seed(int(datetime.now().strftime('%M:%S.%f')[-3:]))
        for place, particle in enumerate(self.pop):
            nr = random.randint(2, len(self.pop) - 1)
            if place == len(self.pop):
                rest = self.pop[:self.pop.index(particle)]
            else:
                rest = self.pop[:self.pop.index(particle)] + self.pop[self.pop.index(particle) + 1:]
            new_neighbours = random.sample(rest, nr)

            for ngh, place in zip(new_neighbours, range(nr)):
                ngh.set_position(place)

            self.nghs.append(new_neighbours)
        self.set_ngh()
        return self.nghs


    def set_ngh(self):
        for ngh in self.nghs:
            for particle in ngh:
                try:
                    if ngh.index(particle) > -1:
                        particle.set_neighbours(ngh)
                except ValueError:
                    pass



