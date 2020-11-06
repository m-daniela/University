

class FiniteAutomata:
    def __init__(self):
        self.states = []
        self.alphabet = []
        self.initial = None
        self.final = []
        self.transitions = {}

    def read_file(self, filename="fa.in"):
        with open(filename) as f:
            lines = f.readlines()

        self.states = (lines[0].strip().split("=")[1:])[0].split(" ")
        self.alphabet = (lines[1].strip().split("=")[1:])[0].split(" ")
        self.initial = (lines[2].strip().split("=")[1:])[0].split(" ")[0]
        self.final = (lines[3].strip().split("=")[1:])[0].split(" ")
        local_transitions = (lines[4].strip().split("=")[1:])[0].split(";")

        self.set_transitions(local_transitions)

    def set_transitions(self, local_transitions):
        for transition in local_transitions:
            lhs, rhs = transition.split("-")
            lhs_pair = tuple(lhs.split(" "))
            rhs_pair = rhs.split(" ")
            if lhs_pair in self.transitions.keys():
                self.transitions[lhs_pair] += rhs_pair
            else:
                self.transitions[lhs_pair] = rhs_pair

    def get_states(self):
        return self.states

    def get_alphabet(self):
        return self.alphabet

    def get_initial(self):
        return self.initial

    def get_final(self):
        return self.final

    def get_transitions(self):
        return self.transitions

    def __str__(self):
        str = ""
        str += f"Q = {', '.join(self.states)}\n" \
               f"S = {', '.join(self.alphabet)}" \
               f"q0 = {self.initial}" \
               f"F = {', '.join(self.final)}" \
               f"D = {self.transitions}"

class Console:
    def __init__(self, finite_automata):
        self.commands = {"0": lambda: exit(0),
                         "1": lambda: finite_automata.get_states(),
                         "2": lambda: finite_automata.get_alphabet(),
                         "3": lambda: finite_automata.get_initial(),
                         "4": lambda: finite_automata.get_final(),
                         "5": lambda: finite_automata.get_transitions(),
                         }

        self.menu = """
        0. Exit
        1. Get states
        2. Get alphabet
        3. Get initial state
        4. Get final states
        5. Get transitions
        """

    def run(self):
        while True:
            print(self.menu)
            try:
                choice = input("Your command: ")
                print(self.commands[choice]())
            except KeyError:
                print("Wrong command")


if __name__ == "__main__":
    finite_automata = FiniteAutomata()
    finite_automata.read_file()
    console = Console(finite_automata)
    console.run()