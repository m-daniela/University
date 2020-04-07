# -*- coding: utf-8 -*-
"""
Created on Thu Feb 27 14:42:18 2020

@author: mosda
"""

import matplotlib.pyplot as plt
import numpy as np

def binomial():
    sample = np.random.binomial(100, 0.2, 1000)
    
    plt.plot(sample, 'ro')
    plt.xlabel('Binomial')
    plt.axis([0, 1000, 0, 100])
    plt.show()
    
    # using histograms
    plt.hist(sample, 50, density=True)
    plt.xlabel('Binomial')
    plt.show()


def normal():
    sample = np.random.normal(0, 0.1, 100)
    
    plt.plot(sample, 'ro')
    plt.xlabel('Normal')
    plt.axis([0, 100, 0, 20])
    plt.show()
    
    # using histograms
    plt.hist(sample, 50, density=True)
    plt.xlabel('Normal')
    plt.show()


    
def choice():
    #print('1. Binomial\n2. Normal\n')
    while True:
        c = input('0. Exit\n1. Binomial\n2. Normal\n')
        if c == '1':
            binomial()
        elif c == '2':
            normal()
        elif c == '0':
            return
        else:
            print('Not valid')
        
    
choice()
