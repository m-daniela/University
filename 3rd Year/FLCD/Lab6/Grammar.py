
class Grammar:
    def __init__(self):
        self.non_term = []
        self.terminals = []
        self.starting = None
        self.productions = {}

    def read_file(self, filename="g1.in"):
        with open(filename) as f:
            lines = f.readlines()

        self.non_term = (lines[0].strip().split("=", 1)[1:])[0].split(" ")
        self.terminals = (lines[1].strip().split("=", 1)[1:])[0].split(" ")
        self.terminals.append(" ")
        self.starting = (lines[2].strip().split("=", 1)[1:])[0].split(" ")[0]
        local_productions = (lines[3].strip().split("=", 1)[1:])[0].split(";")

        self.set_productions(local_productions)

    def set_productions(self, local_productions):
        for transition in local_productions:
            lhs, rhs = transition.split("->")
            lhs_pair = tuple(lhs.split(","))[0]
            rhs_pair = rhs.split("|")
            rhs_pair_newsplit = []
            for p in rhs_pair:
                pp = p.split(",")
                rhs_pair_newsplit.append(pp)
            if lhs_pair in self.productions.keys():
                self.productions[lhs_pair] += rhs_pair_newsplit
            else:
                self.productions[lhs_pair] = rhs_pair_newsplit

    def get_non_term(self):
        return self.non_term

    def get_terminals(self):
        return self.terminals

    def get_starting(self):
        return self.starting

    def get_productions(self):
        return self.productions

    def get_productions_for_non_terminal(self, non_terminal):
        try:
            return self.productions[non_terminal]
        except KeyError:
            return []

class Console:
    def __init__(self, finite_automata):
        self.commands = {"0": lambda: exit(0),
                         "1": lambda: finite_automata.get_non_term(),
                         "2": lambda: finite_automata.get_terminals(),
                         "3": lambda: finite_automata.get_starting(),
                         "4": lambda: finite_automata.get_productions(),
                         "5": lambda x: finite_automata.get_productions_for_non_terminal(x),
                         }

        self.menu = """
        0. Exit
        1. Get non terminals
        2. Get terminals
        3. Get starting symbol
        4. Get productions
        5. Get productions for a non-terminal
        """

    def run(self):
        while True:
            print(self.menu)
            try:
                choice = input("Your command: ")
                if choice == "5":
                    nt = input("Give non-terminal ")
                    print(self.commands[choice](nt))
                else:
                    print(self.commands[choice]())
            except KeyError:
                print("Wrong command")


if __name__ == "__main__":
    grammar = Grammar()
    grammar.read_file("g2.txt")
    console = Console(grammar)
    console.run()