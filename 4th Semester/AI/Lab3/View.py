# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:13:27 2020

@author: mosda
"""


from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QMainWindow, QLineEdit
from Controller import Controller, Testing
from Model import Individual


class View:
    def __init__(self, controller):
        self.controller = controller

    def commands(self):
        print('''
            0. Exit
            1. EA
            2. HC
            3. Tests
        ''')


        while True:
            cmd = input('Enter a command: ')

            if cmd == '0':
                return
            elif cmd == '1':
                print(self.controller.ea())
            elif cmd == '2':
                print(self.controller.hc())
            else:
                print('Wrong input')

    def run(self):
        self.commands()


    def start_ea(self):
        return self.controller.ea()

    def start_hc(self):
        return self.controller.hc()


class Window(QMainWindow):

    def __init__(self):
        super().__init__()
        self.setGeometry(500, 500, 1000, 350)
        self.organize()


        self.show()

    def organize(self):
        self.input_ind = QLineEdit(self)
        self.input_ind.setPlaceholderText('Individual')
        self.input_ind.setGeometry(QtCore.QRect(10, 10, 200, 30))


        self.input_pop = QLineEdit(self)
        self.input_pop.setPlaceholderText('Population')
        self.input_pop.setGeometry(QtCore.QRect(10, 50, 200, 30))


        self.input_iter = QLineEdit(self)
        self.input_iter.setPlaceholderText('Iterations')
        self.input_iter.setGeometry(QtCore.QRect(10, 90, 200, 30))


        self.input_cross = QLineEdit(self)
        self.input_cross.setDisabled(True)
        self.input_cross.setPlaceholderText('Crossover Probability')
        self.input_cross.setGeometry(QtCore.QRect(10, 130, 200, 30))

        self.input_mut = QLineEdit(self)
        self.input_mut.setDisabled(True)
        self.input_mut.setPlaceholderText('Mutation Probability')
        self.input_mut.setGeometry(QtCore.QRect(10, 170, 200, 30))

        self.ea_button = QtWidgets.QPushButton(self)
        self.ea_button.setGeometry(QtCore.QRect(10, 210, 200, 30)) # 360, 10
        self.ea_button.setObjectName("ea_button")
        self.ea_button.setText('EA')

        self.ea_text = QtWidgets.QTextEdit(self)
        self.ea_text.setGeometry(QtCore.QRect(240, 10, 340, 310))
        self.ea_text.setObjectName("ea_text")

        self.hc_button = QtWidgets.QPushButton(self)
        self.hc_button.setGeometry(QtCore.QRect(10, 250, 200, 30)) # 740, 10
        self.hc_button.setObjectName("hc_button")
        self.hc_button.setText('HC')

        self.hc_text = QtWidgets.QTextEdit(self)
        self.hc_text.setGeometry(QtCore.QRect(610, 10, 350, 310))
        self.hc_text.setObjectName("hc_text")

        self.test_button = QtWidgets.QPushButton(self)
        self.test_button.setGeometry(QtCore.QRect(10, 290, 200, 30))  # 740, 10
        self.test_button.setObjectName("test_button")
        self.test_button.setText('Test')

        self.ea_button.clicked.connect(self.startEA)
        self.hc_button.clicked.connect(self.startHC)
        self.test_button.clicked.connect(self.tests)


    def startEA(self):
        yes = Individual(int(self.input_ind.text()))
        self.ctrl = Controller(yes, int(self.input_pop.text()), int(self.input_iter.text()))

        self.ctrl.sig_ea.connect(self.writeEA)

        self.ctrl.fittest_ea = True
        # self.ctrl.fittest_hc = False
        self.ctrl.start()


    def writeEA(self, val):
        self.ea_text.append(val)

    def startHC(self):

        ind = Individual(int(self.input_ind.text()))
        self.ctrl = Controller(ind, int(self.input_pop.text()), int(self.input_iter.text()))

        self.ctrl.sig_hc.connect(self.writeHC)
        # self.ctrl.fittest_ea = False
        self.ctrl.fittest_hc = True
        self.ctrl.start()

    def writeHC(self, val):
        self.hc_text.append(val)

    def tests(self):
        t = Testing()
        t.show()




