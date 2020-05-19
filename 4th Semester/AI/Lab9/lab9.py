# -*- coding: utf-8 -*-
"""
Created on Fri May 15 18:31:08 2020

@author: mosda
"""

import numpy as np
from keras.datasets import mnist
from keras.models import Sequential, load_model
from keras.layers.convolutional import Conv2D
from keras.layers import Dense, Dropout
from keras.layers.convolutional import MaxPooling2D
from keras.layers import Flatten
from keras.utils import np_utils

class CNN:
    def __init__(self, X_train, X_test, Y_train, Y_test, classes):
        self.X_train = X_train
        self.X_test = X_test
        self.classes = classes
        self.Y_train = Y_train
        self.Y_test = Y_test

    def prepare_data(self):
        # normalize the data

        # shape into the input vector
        # 60000 training units, 10000 testing units, images of 28 x 28 pixels
        self.X_train = self.X_train.reshape(-1, 28, 28, 1)
        self.X_test = self.X_test.reshape(-1, 28, 28, 1)
        self.X_train = self.X_train.astype('float32')
        self.X_test = self.X_test.astype('float32')

        # make the pixel values range between [0, 1] from [0, 255]
        self.X_train /= 255
        self.X_test /= 255

        # get the numerical value of the entry in the data set
        # in this case, the 1 in the output array will be the label of the
        # image from the input
        self.Y_train = np_utils.to_categorical(self.Y_train, self.classes)
        self.Y_test = np_utils.to_categorical(self.Y_test, self.classes)

    def build_model(self):
        img_rows, img_cols = 28, 28
        # add the layers to the model
        # 2 hidden layers of 128 neurons each
        # 1 output layer with 10 neurons (1 for each digit)
        self.model = Sequential()
        self.model.add(Conv2D(128, kernel_size=(3, 3),
                         activation='relu',
                         input_shape=(img_rows, img_cols, 1)))
        self.model.add(Conv2D(128, kernel_size=(3, 3), activation='relu'))
        self.model.add(MaxPooling2D(pool_size=(2, 2)))
        self.model.add(Dropout(0.25))
        self.model.add(Flatten())
        self.model.add(Dense(128, activation='relu'))
        self.model.add(Dropout(0.5))
        self.model.add(Dense(self.classes, activation='softmax'))

        self.model.compile(loss='categorical_crossentropy',
                           metrics=['accuracy'],
                           optimizer='adam')

    def train_model(self, epochs):
        self.history = self.model.fit(self.X_train,
                                      self.Y_train,
                                      batch_size=128,
                                      epochs=epochs,
                                      verbose=2,
                                      validation_data=(self.X_test, self.Y_test))

    def results(self):
        self.model.save('results.h5')

        mnist_model = load_model('results.h5')
        loss_and_metrics = mnist_model.evaluate(self.X_test, self.Y_test, verbose=2)

        print("Loss", loss_and_metrics[0])
        print("Accuracy", loss_and_metrics[1])

if __name__ == '__main__':

    classes = 10
    epochs = 5
    (X_train, y_train), (X_test, y_test) = mnist.load_data()
    cnn = CNN(X_train, X_test, y_train, y_test, classes)
    cnn.prepare_data()
    cnn.build_model()
    cnn.train_model(epochs)
    cnn.results()
