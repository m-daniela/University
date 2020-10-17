# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:27 2020

@author: mosda
"""


from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QMainWindow, QLineEdit
from Controller import Controller, Testing
from Model import Population, Neighbourhood


# GUI
class Window(QMainWindow):

    def __init__(self):
        super().__init__()
        self.setGeometry(500, 500, 800, 350)
        self.organize()

        self.show()

    def organize(self):
        self.input_size = QLineEdit(self)
        self.input_size.setPlaceholderText('Size of particle')
        self.input_size.setGeometry(QtCore.QRect(10, 10, 200, 30))


        self.input_pop = QLineEdit(self)
        self.input_pop.setPlaceholderText('Population')
        self.input_pop.setGeometry(QtCore.QRect(10, 50, 200, 30))


        self.input_iter = QLineEdit(self)
        self.input_iter.setPlaceholderText('Iterations')
        self.input_iter.setGeometry(QtCore.QRect(10, 90, 200, 30))


        self.pso_button = QtWidgets.QPushButton(self)
        self.pso_button.setGeometry(QtCore.QRect(10, 130, 200, 30))
        self.pso_button.setObjectName("pso_button")
        self.pso_button.setText('PSO')

        self.pso_text = QtWidgets.QTextEdit(self)
        self.pso_text.setGeometry(QtCore.QRect(240, 10, 440, 310))
        self.pso_text.setObjectName("pso_text")


        self.test_button = QtWidgets.QPushButton(self)
        self.test_button.setGeometry(QtCore.QRect(10, 210, 200, 30))
        self.test_button.setObjectName("test_button")
        self.test_button.setText('Test')

        self.pso_button.clicked.connect(self.startPSO)
        self.test_button.clicked.connect(self.tests)


    def startPSO(self):
        self.p = Population(int(self.input_pop.text()))
        self.pop = self.p.get_population(int(self.input_size.text()))
        self.n = Neighbourhood(self.pop)
        self.n.split_pop()
        self.ctrl = Controller(self.pop, int(self.input_iter.text()))

        self.ctrl.sig_pso.connect(self.writePSO)

        self.ctrl.start()


    def writePSO(self, val):
        self.pso_text.append(val)


    def tests(self):
        t = Testing()
        t.show()




