
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 20 16:14:04 2020

@author: mosda
"""
from View import Window
from PyQt5.QtWidgets import QApplication
import sys


if __name__ == "__main__":
    app = QApplication(sys.argv)
    win = Window()
    sys.exit(app.exec_())
