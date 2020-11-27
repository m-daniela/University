
class Configuration:
    def __init__(self, state, i, alpha, beta):
        self.state = state
        self.i = i
        self.working_stack = alpha
        self.input_stack = beta

    def __str__(self):
        working = ""
        for item in self.working_stack:
            working += f"{str(item)}, "
        return f"\nState: {self.state}\n" \
               f"I: {self.i + 1}\n" \
               f"Working stack: {working}\n" \
               f"Input stack: {self.input_stack}\n"


class WorkingElement:
    def __init__(self, type, element, index):
        self.type = type
        self.element = element
        self.index = index

    def __str__(self):
        if self.type == "Terminal":
            return self.element
        return f"{self.element}{self.index + 1}"

class RDParser:
    def __init__(self, grammar, configuration):
        self.grammar = grammar
        self.configuration = configuration
        self.input_seq = None

    def drp(self, input_seq):
        self.input_seq = input_seq
        n = len(self.input_seq)
        print(f"Initial: {self.configuration}")
        # while s != f and s != e
        while self.configuration.state != "f" and self.configuration.state != "e":
            # if s == q
            if self.configuration.state == "q":
                # if input empty and i = n + 1
                if len(self.configuration.input_stack) == 0 and self.configuration.i == n:
                    # s = f
                    self.success()
                else:
                    # if top(input) is a NON terminal
                    top = self.configuration.input_stack[0]
                    if top in self.grammar.non_term:
                        self.expand()
                    else:
                        # if top input = current symbol in w and current position is lower than the
                        if n > self.configuration.i and top == self.input_seq[self.configuration.i]:
                            self.advance()
                        else:
                            self.momentary_insuccess()

            else:
                if self.configuration.state == "b":
                    top = self.configuration.working_stack[0]
                    if top.type == "Terminal":
                        self.go_back()
                    else:
                        self.another_try()

        if self.configuration.state == "e":
            print("Error")
        else:
            print("Sequence accepted")
            return self.get_production_string()

    def get_production_string(self):
        prods = []
        for item in self.configuration.working_stack:
            if item.type == "NonTerminal":
                prods = [item] + prods
        return prods

    def expand(self):

        # pop(input)
        a = self.configuration.input_stack.pop(0)
        # get productions
        y = self.grammar.get_productions_for_non_terminal(a)[0]

        self.configuration.working_stack = [WorkingElement("NonTerminal", a, 0)] + self.configuration.working_stack

        # add first production to the working stack
        for i in range(len(y) - 1, -1, -1):
            self.configuration.input_stack = [y[i]] + self.configuration.input_stack
        print(f"Expand {self.configuration}")


    def advance(self):
        # i = i + 1
        self.configuration.i += 1
        # pop(input, a)
        terminal = self.configuration.input_stack.pop(0)
        working_element = WorkingElement("Terminal", terminal, -1)
        # push(working, a)
        self.configuration.working_stack = [working_element] + self.configuration.working_stack
        print(f"Advance {self.configuration}")

    def momentary_insuccess(self):
        self.configuration.state = "b"
        print(f"Momentary insuccess {self.configuration}")

    def go_back(self):
        self.configuration.i -= 1

        self.configuration.input_stack = [self.configuration.working_stack.pop(0).element] + self.configuration.input_stack
        print(f"Back {self.configuration}")

    def another_try(self):
        we = self.configuration.working_stack[0]
        element = we.element
        index = we.index

        if index != len(self.grammar.get_productions_for_non_terminal(element)) - 1:
            # set state
            self.configuration.state = "q"
            working_element = WorkingElement("NonTerminal", element, index + 1)

            # pop(working, Aj)
            self.configuration.working_stack.pop(0)

            # push(working, Aj+1)
            self.configuration.working_stack = [working_element] + self.configuration.working_stack

            # pop(input, Gamma j)
            m = len(self.grammar.get_productions_for_non_terminal(element)[index])
            for _ in range(m):
                self.configuration.input_stack.pop(0)

            # push(input, Gamma j+1)
            y = self.grammar.get_productions_for_non_terminal(element)[index + 1]
            for i in range(len(y) - 1, -1, -1):
                self.configuration.input_stack = [y[i]] + self.configuration.input_stack
        else:
            if self.configuration.i == 1 and element == self.grammar.get_starting():
                self.configuration.state = "e"
            else:
                self.configuration.working_stack.pop(0)
                # pop(input, Gamma j)
                m = len(self.grammar.get_productions_for_non_terminal(element)[index])
                for _ in range(m):
                    self.configuration.input_stack.pop(0)
                self.configuration.input_stack = [element] + self.configuration.input_stack

        print(f"Another try {self.configuration}")

    def success(self):
        self.configuration.state = "f"
        print(f"Success {self.configuration}")

