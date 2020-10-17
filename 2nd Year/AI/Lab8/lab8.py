# -*- coding: utf-8 -*-
"""
Created on Wed Apr 29 18:53:49 2020

@author: mosda
"""

import numpy as np
import random
import matplotlib as mpl

# functions

def linear(x):
    return x

def linear_deriv(x):
    return np.ones(x.shape)

# get data

class DataHandler:
    def __init__(self, filename='data.txt'):
        self.filename = filename

    def get_data(self):
        dataset = []
        data = []
        output = []
        with open(self.filename, 'r') as f:
            lines = f.readlines()

        for line in lines:
            if line != '' and line != '\n' and line[0] != 'B':
                values = line.strip().split(' ')
                dataset.append([])
                for v in range(len(values)):
                    dataset[-1].append(float(values[v]))

        dataset = self.normalize(dataset)

        for i in range(len(dataset)):
            data.append([])
            for j in range(len(dataset[i]) - 1):
                data[-1].append(dataset[i][j])
            output.append([dataset[i][-1]])

        # p = random.random()
        # print(p)
        p = 0.75
        return data[:int(len(data) * p)], output[:int(len(data) * p)], data[int(len(data) * p + 1):],  output[int(len(data) * p + 1):]

    def normalize(self, dataset):
        # normalize data set: keep between [0, 1]
        for i in range(len(dataset)):
            xmin = min(dataset[i])
            xmax = max(dataset[i])
            for j in range(len(dataset[i])):
                x = dataset[i][j]
                dataset[i][j] = (x - xmin)/(xmax - xmin)
        return dataset

class NeuralNetwork:

    def __init__(self, training, training_output, hidden_neurons):
        self.training = training
        self.training_output = training_output
        self.hidden_neurons = hidden_neurons
        self.weights1 = np.random.rand(self.training.shape[1], hidden_neurons)
        self.weights2 = np.random.rand(hidden_neurons, 1)
        self.output = np.zeros(self.training_output.shape)
        self.loss = []

    def feedforward(self, x):

        self.output_hidden = linear(np.dot(x, self.weights1))
        self.output = linear(np.dot(self.output_hidden, self.weights2))

        return self.output

    def backpropagation(self, learn_rate):

        delta_r = (self.training_output - self.output)/self.training.shape[0]
        hidden = learn_rate * np.dot(self.output_hidden.T, delta_r)
        self.weights2 += hidden
        delta_h = np.dot(delta_r, self.weights2.T)
        hidden2 = learn_rate * np.dot(self.training.T, delta_h)

        self.weights1 += hidden2

        self.loss.append(sum((self.training_output - self.output) ** 2)/self.training.shape[0])

    def test(self, test_in, test_out):
        result = self.feedforward(test_in)
        print(f'Average error: {sum((test_out - result)**2) / test_out.shape[0]}')


if __name__ == '__main__':
    data = DataHandler()
    training_in, training_out, test_in, test_out = data.get_data()
    train_in = np.array(training_in)
    train_out = np.array(training_out)
    test_in = np.array(test_in)
    test_out = np.array(test_out)

    n = NeuralNetwork(train_in, train_out, 5)

    iter = []
    for i in range(10000):
        n.feedforward(train_in)
        n.backpropagation(0.1)
        iter.append(i)

    n.test(test_in, test_out)

    mpl.pyplot.plot(iter, n.loss, label='Loss value vs iteration')
    mpl.pyplot.xlabel('Iterations')
    mpl.pyplot.ylabel('Loss function')
    mpl.pyplot.legend()
    mpl.pyplot.show()
