import datetime
from math import sin, cos
import random
from time import sleep

DEPTH_MAX = 10
terminals = ['-180', '-165', '-150', '-135', '-120', '-105', '-90', '-75',
             '-60', '-45', '-30', '-15', '0', '15', '30', '45', '60', '75',
             '90', '105', '120', '135', '150', '165', '180']
noTerminals = len(terminals)
functions = ['+', '-', '*', 'sin', 'cos']
binary_functions = ['+', '-', '*']
unary_functions = ['sin', 'cos']
noFunctions = 5
constants = [random.random() for _ in range(10)]
classes = {'Move-Forward': 0,
           'Slight-Left-Turn': 1,
           'Slight-Right-Turn': 2,
           'Sharp-Right-Turn': 3
           }


# model

class DataHandler:
    def __init__(self, filename='sensor_readings_24.data'):
        self.filename = filename

    def get_data(self):
        with open(self.filename, 'r') as f:
            lines = f.readlines()
        groups = []
        for line in lines:
            groups.append([])
            trimmed = line.strip().split(',')
            for element in trimmed[:-1]:
                groups[-1].append(float(element))
            groups[-1].append(trimmed[-1])
        return groups[:int(len(groups)*0.6)], groups[int(len(groups)*0.6) + 1:]

    def get_input_data(self):
        with open('input.in', 'r') as f:
            line = f.readline()
        values = line.strip().split(',')
        return int(values[0]), float(values[1]), float(values[2]), float(values[3])

class Chromosome:
    def __init__(self, depth=DEPTH_MAX):
        self.max_depth = depth
        self.representation = [0 for _ in range(2 ** (self.max_depth + 1) - 1)]
        self.fitness = 0
        self.size = 0
        self.accuracy = 0
        self.grow_expression()
        self.trim_representation()

    def grow_expression(self, pos=0, depth=0):
        # random initialization
        if pos == 0 or depth < self.max_depth:
            if pos != 0 and random.random() < 0.5:
                self.representation[pos] = random.randint(1, noTerminals)
                self.size = pos + 1
                return pos + 1
            else:
                # function node - negative index
                function_index = random.randint(1, noFunctions - 1)
                self.representation[pos] = -function_index
                # check if the given function is unary or binary
                if functions[function_index] in unary_functions:
                    final_child = self.grow_expression(pos + 1, depth + 1)
                    return final_child
                else:
                    final_first_child = self.grow_expression(pos + 1, depth + 1)
                    final_second_child = self.grow_expression(final_first_child, depth + 1)
                return final_second_child
        else:
            # terminal node - positive index
            self.representation[pos] = random.randint(1, noTerminals)
            self.size = pos + 1
            return pos + 1

    def trim_representation(self):
        # getting rid of all the zeros
        self.representation = [i for i in self.representation if i != 0]

    def evaluate_expression(self, new_pos, current_data):
        pos = min(len(self.representation) - 1, new_pos)
        try:
            if self.representation[pos] > 0:
                # get the terminal node
                return current_data[self.representation[pos] - 1], pos
            elif self.representation[pos] < 0:
                # get the function
                function = functions[-self.representation[pos] - 1]
                if function == '+':
                    aux_first = self.evaluate_expression(pos + 1, current_data)
                    aux_second = self.evaluate_expression(aux_first[1] + 1, current_data)
                    return aux_first[0] + aux_second[0], aux_second[1]
                elif function == '-':
                    aux_first = self.evaluate_expression(pos + 1, current_data)
                    aux_second = self.evaluate_expression(aux_first[1] + 1, current_data)
                    return aux_first[0] - aux_second[0], aux_second[1]
                elif function == '*':
                    aux_first = self.evaluate_expression(pos + 1, current_data)
                    aux_second = self.evaluate_expression(aux_first[1] + 1, current_data)
                    return aux_first[0] * aux_second[0], aux_second[1]
                elif function == 'sin':
                    aux_child = self.evaluate_expression(pos + 1, current_data)
                    return sin(aux_child[0]), aux_child[1]
                elif function == 'cos':
                    aux_child = self.evaluate_expression(pos + 1, current_data)
                    return cos(aux_child[0]), aux_child[1]
        except IndexError:
            return current_data[-1], len(current_data) - 1

    def compute_fitness(self, data):
        error = 0.0
        guessed = 0
        dataset_length = len(data)
        for i in range(dataset_length - 1):
            value = self.evaluate_expression(0, data[i][:-1])[0]
            label = data[i][-1]
            expected = classes[label]
            error += abs(expected - value)
            # print(f'fitness in compute fitness {value}, {label}, {expected}')
            if abs(expected - value) < 3.0:
                guessed += 1
        self.accuracy = guessed / dataset_length
        self.fitness = error

    def traverse(self, pos):
        try:
            if self.representation[pos] > 0:
                return pos + 1
            else:
                return self.traverse(self.traverse(pos + 1))
        except IndexError:
            return pos

# controller

class Controller:
    def __init__(self, train_data, test_data, individuals, crossover_prob, mutation_prob, epsilon):
        self.train_data = train_data
        self.test_data = test_data
        self.individuals = individuals
        self.population = [Chromosome() for _ in range(self.individuals)]
        self.crossover_prob = crossover_prob
        self.mutation_prob = mutation_prob
        self.epsilon = epsilon
        self.accuracy = 0.0

    def crossover(self, parent1, parent2):
        child = Chromosome()
        while True:
            start_parent1 = random.randint(0, parent1.size - 1)
            end_parent1 = parent1.traverse(start_parent1)
            start_parent2 = random.randint(0, parent2.size - 1)
            end_parent2 = parent2.traverse(start_parent2)
            # print(f'child {len(child.representation)}')
            # print(end_parent1 + (end_parent2 - start_parent2 - 1) + (parent1.size - end_parent1 - 1))
            # print(len(parent1.representation) + len(parent2.representation))
            if len(child.representation) <= end_parent1 + (end_parent2 - start_parent2 - 1) + \
                    (parent1.size - end_parent1 - 1):
                break
            elif len(child.representation) >= len(parent1.representation) + len(parent2.representation) - 2:
                return child
        i = -1
        try:
            for i in range(start_parent1):
                child.representation[i] = parent1.representation[i]
            for j in range(start_parent2, end_parent2):
                i = i + 1
                child.representation[i] = parent2.representation[j]
            for j in range(end_parent1, parent1.size):
                i = i + 1
                child.representation[i] = parent1.representation[j]
            child.size = i + 1
        except IndexError:
            pass
        return child

    def mutation(self, chromosome):
        child = Chromosome()
        pos = random.randint(0, chromosome.size - 1)
        child.representation = chromosome.representation[:]
        child.size = chromosome.size
        node = child.representation[pos]
        if node > 0:
            child.representation[pos] = random.randint(1, noTerminals)
        else:
            child.representation[pos] = -random.randint(1, noFunctions)
        return child

    def selection(self):
        i = 0
        while self.accuracy < self.epsilon:
            # print(f'iteration {i}')
            i = i + 1
            for j in range(len(self.population) // 2):
                # get some children
                parent_index1 = random.randint(0, self.individuals - 1)
                parent_index2 = random.randint(0, self.individuals - 1)
                if parent_index1 != parent_index2:
                    parent1 = self.population[parent_index1]
                    parent2 = self.population[parent_index2]
                    # print('cross ' + str(random.random()))
                    if self.crossover_prob > random.random():
                        child = self.crossover(parent1, parent2)
                        # print('mutate')
                        if self.mutation_prob > random.random():
                            child = self.mutation(child)
                        self.population.append(child)
            for individual in self.population:
                try:
                    individual.compute_fitness(self.train_data)
                except RecursionError:
                    continue

            best = sorted(self.population, key=lambda x: x.fitness)
            self.accuracy = best[-1].accuracy
            self.population = best[:self.individuals]
            self.write_output(i, best[-1].accuracy, best[-1].fitness)
            print(f'Iteration: {i}\nAccuracy: {best[-1].accuracy}\nFitness: {best[-1].fitness}')

    def write_output(self, iteration, accuracy, fitness):
        s = f'Iteration: {iteration}\nAccuracy: {accuracy}\nFitness: {fitness}'
        with open('output.out', 'a') as f:
            f.write(s)

# view

class UI:
    def __init__(self, controller):
        self.ctrl = controller

    def run(self):
        self.ctrl.selection()


if __name__ == '__main__':
    d = DataHandler()
    train_data, test_data = d.get_data()
    population_size, crossover, mutation, epsilon = d.get_input_data()
    # population_size, crossover, mutation, epsilon = 50, 0.5, 0.2, 0.7
    ctrl = Controller(train_data, test_data, population_size, crossover, mutation, epsilon)
    ui = UI(ctrl)
    random.seed(datetime.time())
    ui.run()
