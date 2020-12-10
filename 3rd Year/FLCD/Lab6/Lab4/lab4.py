

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
            lhs, rhs = transition.split("~")
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

    def is_dfa(self):
        # see if the transition goes to more states
        for key in list(self.transitions.keys()):
            if len(self.transitions[key]) > 1:
                return False
        return True

    def is_accepted(self, sequence):
        try:
            # get first symbol and initial state
            start = (self.initial, sequence[0])
            # get the next state from the map of transitions
            next_state = self.transitions[start][0]
            # print(start, next_state)
            # check for sequence of 1 element
            if len(sequence) == 1 and next_state in self.final:
                return True
            # get the next state of each remaining symbol by
            # iterating through the rest of the sequence
            for x in range(1, len(sequence)):
                start = (next_state, sequence[x])
                next_state = self.transitions[start][0]
                # print(start, next_state)
                # if we get to the last symbol and the state is final, return true
                if x == len(sequence) - 1 and next_state in self.final:
                    return True

        except KeyError as e:
            pass
            # print(f"Not found: {e}")
        return False


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
                         "1": lambda: finite_automata.get_non_term(),
                         "2": lambda: finite_automata.get_terminals(),
                         "3": lambda: finite_automata.get_starting(),
                         "4": lambda: finite_automata.get_final(),
                         "5": lambda: finite_automata.get_productions(),
                         "6": lambda: finite_automata.is_dfa(),
                         "7": lambda x: finite_automata.is_accepted(x),
                         }

        self.menu = """
        0. Exit
        1. Get states
        2. Get alphabet
        3. Get initial state
        4. Get final states
        5. Get transitions
        6. Check if the finite automata is deterministic
        7. Check if given sequence is accepted
        """

    def run(self):
        while True:
            print(self.menu)
            try:
                choice = input("Your command: ")
                if choice == "7":
                    sequence = input("Give sequence ")
                    print(self.commands[choice](sequence))
                else:
                    print(self.commands[choice]())
            except KeyError:
                print("Wrong command")


def test_numbers():
    fa = FiniteAutomata()
    fa.read_file("constant_nr.in")
    assert fa.is_accepted("0") == True
    assert fa.is_accepted("5") == True
    assert fa.is_accepted("102") == True
    assert fa.is_accepted("+2") == True
    assert fa.is_accepted("-15") == True
    assert fa.is_accepted("034") == False
    assert fa.is_accepted("+0") == False
    assert fa.is_accepted("-0") == False
    assert fa.is_accepted("-06") == False

def test_identifier():
    fa = FiniteAutomata()
    fa.read_file("identifier.in")
    assert fa.is_accepted("a") == True
    assert fa.is_accepted("main") == True
    assert fa.is_accepted("Abc") == True
    assert fa.is_accepted("a43") == True
    assert fa.is_accepted("b1") == True
    assert fa.is_accepted("Z3r0") == True
    assert fa.is_accepted("u_4") == True
    assert fa.is_accepted("2a") == False
    assert fa.is_accepted("_43b") == False
    assert fa.is_accepted("_ct") == False
    assert fa.is_accepted("_") == False


def test_string():
    fa = FiniteAutomata()
    fa.read_file("constant_string.in")
    assert fa.is_accepted('"abcd"') == True
    assert fa.is_accepted('"5"') == True
    assert fa.is_accepted(''"a#"'') == True
    assert fa.is_accepted('"034') == False
    assert fa.is_accepted('ad0"') == False
    assert fa.is_accepted('"') == False

def test_char():
    fa = FiniteAutomata()
    fa.read_file("constant_char.in")
    assert fa.is_accepted("'a'") == True
    assert fa.is_accepted("'#'") == True
    assert fa.is_accepted("'5'") == True
    assert fa.is_accepted("'a") == False
    assert fa.is_accepted("a'") == False
    assert fa.is_accepted("'abc'") == False

if __name__ == "__main__":
    try:
        test_numbers()
        test_identifier()
        test_string()
        test_char()
    except Exception as e:
        print(e)
    finite_automata = FiniteAutomata()
    # finite_automata.read_file("identifier.in")
    # finite_automata.read_file("constant_char.in")
    finite_automata.read_file("fa.in")
    console = Console(finite_automata)
    console.run()