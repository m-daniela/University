
class Node:
    def __init__(self, working_element, left, right, father, father_index, index):
        self.element = working_element
        self.left = left
        self.right = right
        self.father = father
        self.father_index = father_index
        self.index = index

    def __str__(self):
        return f"{self.index} | {str(self.element)} | Left: {self.left} | Right: {self.right} | " \
               f"Father: {str(self.father)} {self.father_index}"

class ParseTree:
    def __init__(self, grammar, root, productions):
        self.grammar = grammar
        self.root = root
        self.productions = productions
        self.tree = []
        self.index = 1

    def create_table(self):
        # bfs
        element = self.root.element
        index = self.root.index
        # create the root node and add it to the tree
        root_node = Node(f"{element}{index}", -1, -1, None, -1, self.index)
        self.index += 1
        a_tree = [root_node]

        # recursively parse each level
        # in:
        # working_element - current non-terminal as working element,
        # containing the type, value and the index of the production
        # father_index - index of the father non-terminal
        # out:
        # set and print the table
        def recursive_create(working_element, father_index):
            element = working_element.element
            index = working_element.index
            # get the production for the non-terminal and the corresponding index
            production = self.grammar.get_productions_for_non_terminal(element)[index - 1]
            waiting = []
            line = []
            for i, p in enumerate(production):
                node = None
                # terminal node
                if p in self.grammar.terminals:
                    node = Node(f"{p}", -1, -1, f"{element}{index}", father_index, self.index)

                # non-terminal node, pop from the string of productions
                # append to the waiting list with the index of the node to continue to the next level of the tree
                elif p in self.grammar.non_term:
                    try:
                        we = self.productions.pop(0)
                        element = we.element
                        index = we.index
                        node = Node(f"{element}{index}", -1, -1, f"{element}{index}", father_index, self.index)
                        waiting.append((we, self.index))

                    except Exception as e:
                        print(e)

                # set the left and right siblings of the node if exist, otherwise keep -1
                # and increase the global index (for the table)
                line.append(node)
                if i == 0:
                    node.left = -1
                elif i == len(production):
                    node.right = -1
                else:
                    line[i - 1].right = node.index
                    node.left = line[i - 1].index
                a_tree.append(node)

                self.index += 1

            # take each non-terminal saved and continue recursively
            while len(waiting) > 0:
                top, new_father_index = waiting.pop(0)
                recursive_create(top, new_father_index)

        # start with the first production from the string of productions
        first = self.productions.pop(0)
        recursive_create(first, self.index - 1)
        self.tree = a_tree

        self.write_file()

    def write_file(self):
        table = ""
        for node in self.tree:
            table += f"{str(node)}\n"
        print(table)
        with open("out.txt", "w") as f:
            f.write(table)

    # def create_table1(self):
    #     a_tree = []
    #     element = self.root.element
    #     index = self.root.index
    #
    #     def recursive_create(working_element, previous, left, right):
    #         element = working_element.element
    #         index = working_element.index
    #         production = self.grammar.get_productions_for_non_terminal(element)[index - 1]
    #         chd = []
    #
    #         for i, p in enumerate(production):
    #             if p in self.grammar.terminals:
    #                 chd.append(p)
    #                 node = Node(f"{p}", production[:i], production[i+1:], f"{element}{index}", [])
    #                 a_tree.append(node)
    #             elif p in self.grammar.non_term:
    #                 try:
    #                     we = self.productions.pop(0)
    #                     chd.append(str(we))
    #                     recursive_create(we, working_element, production[:i], production[i+1:])
    #                 except Exception as e:
    #                     print(e)
    #
    #         node = Node(f"{element}{index}", [], [], str(previous), chd)
    #         a_tree.append(node)
    #
    #     current = self.productions.pop(0)
    #     recursive_create(current, None, [], [])
    #
    #     for node in a_tree:
    #         print(node)
    #         print()
    #         print()
    #
    #
    # def create_table2(self):
    #     ## saved in order - dfs
    #     a_tree = []
    #     element = self.root.element
    #     index = self.root.index
    #
    #     def recursive_create(working_element, previous, left, right):
    #         element = working_element.element
    #         index = working_element.index
    #         production = self.grammar.get_productions_for_non_terminal(element)[index - 1]
    #         chd = []
    #
    #         node = Node(f"{element}{index}", left, right, str(previous), production, self.index)
    #         self.index += 1
    #         a_tree.append(node)
    #         for i, p in enumerate(production):
    #             if p in self.grammar.terminals:
    #                 # chd.append(p)
    #                 node = Node(f"{p}", production[:i], production[i+1:], f"{element}{index}", [], self.index)
    #                 self.index += 1
    #                 a_tree.append(node)
    #             elif p in self.grammar.non_term:
    #                 try:
    #                     we = self.productions.pop(0)
    #                     # chd.append(str(we))
    #                     recursive_create(we, working_element, production[:i], production[i+1:])
    #                 except Exception as e:
    #                     print(e)
    #
    #     current = self.productions.pop(0)
    #     recursive_create(current, None, [], [])
    #
    #     for node in a_tree:
    #         print(node)
    #         print()
    #         print()
