
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
        return f"State: {self.state}\n" \
               f"I: {self.i}\n" \
               f"Working stack: {working}\n" \
               f"Input stack: {self.input_stack}"


class WorkingElement:
    def __init__(self, type, element, index):
        self.type = type
        self.element = element
        self.index = index

    def __str__(self):
        if self.type == "Terminal":
            return self.element
        return f"{self.element}{self.index}"

class RDParser:
    def __init__(self, grammar, configuration):
        self.grammar = grammar
        self.configuration = configuration

    def drp(self):
        n = len(self.configuration.input_stack)
        while self.configuration.state != "f" and self.configuration.state != "e":
            if self.configuration.state == "q":
                if len(self.configuration.input_stack) == 0 and self.configuration.i == n + 1:
                    self.success()
                else:
                    top = self.configuration.input_stack[0]
                    if top in self.grammar.non_term:
                        self.expand()
                    else:
                        if top in self.grammar.terminals:
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


    def expand(self):

        a = self.configuration.input_stack.pop(0)
        y = self.grammar.get_productions_for_non_terminal(a)[0]

        self.configuration.working_stack = [WorkingElement("NonTerminal", a, 1)] + self.configuration.working_stack
        for i in range(len(y) - 1, -1, -1):
            self.configuration.input_stack = [y[i]] + self.configuration.input_stack
        print(f"Expand {self.configuration.input_stack}")


    def advance(self):
        terminal = self.configuration.input_stack.pop(0)
        working_element = WorkingElement("Terminal", terminal, -1)
        self.configuration.i += 1
        self.configuration.working_stack = [working_element] + self.configuration.working_stack

    def momentary_insuccess(self):
        self.configuration.state = "b"

    def go_back(self):
        self.configuration.i -= 1
        self.configuration.input_stack = [self.configuration.working_stack.pop(0).element] + self.configuration.input_stack

    def another_try(self):
        # TODO: index out of range
        element = self.configuration.working_stack[0].element
        index = self.configuration.working_stack[0].index
        if index < len(self.grammar.get_productions_for_non_terminal(element)):
            self.configuration.state = "q"
            working_element = WorkingElement("NonTerminal", element, index + 1)
            self.configuration.working_stack = [working_element] + self.configuration.working_stack
            y = self.grammar.get_productions_for_non_terminal(element)[index + 1]
            for i in range(len(y) - 1, -1, -1):
                self.configuration.input_stack = [y[i]] + self.configuration.input_stack
        elif index == 1 and element is self.grammar.get_starting():
            self.configuration.state = "e"
        else:
            self.configuration.input_stack = [self.configuration.working_stack.pop(0).element] + self.configuration.input_stack


    def success(self):
        self.configuration.state = "f"

