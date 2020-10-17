# -*- coding: utf-8 -*-
"""
Created on Thu Mar  5 14:36:00 2020

@author: mosda
"""



class Controller:
    def __init__(self, problem, size):
        self.problem = problem
        self.size = size

    def dfs(self, root):
        s = [root]
        visited = []

        while len(s) != 0:
            current = s.pop()

            if current.isSolution(self.size):
                # print(current)
                return current
            visited.append(current)
            lvls = self.problem.expand(current)
            lvls.reverse()
            s2 = []
            for lvl in lvls:
                if lvl not in visited:
                    values = lvl.get_values()
                    x, y = values[-1]
                    lvl.remove_value(x, y)
                    if lvl.isGood(x, y):
                        s2.append(lvl)
                    lvl.add_value(x, y)
            s = s + s2

    def greedy(self, root):
        visited = []
        to_visit = [root]

        while len(to_visit) != 0:
            node = to_visit.pop()
            visited.append(node)
            v = node.get_values()
            # print(node)
            if node.isSolution(self.size):
                return node
            if node.isSolution(len(v)):

                aux = []
                lvls = self.problem.expand(node)
                lvls.reverse()

                for lvl in lvls:
                    if lvl not in visited:
                        aux.append(lvl)

                priority = []
                for x in aux:
                    priority.append([x, self.problem.heuristics(x)])
                # print(priority)
                priority.sort(key=lambda x:x[1])

                aux2 = []
                for y in priority:
                    aux2.append(y[0])

                to_visit = aux2[:] + to_visit
