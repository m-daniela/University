from Lab5.RDParser import Configuration, RDParser, WorkingElement
from Lab5.ParserOutput import ParseTree


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



# if __name__ == "__main__":
#     grammar = Grammar()
#     grammar.read_file("g3.txt")
#
#     s = WorkingElement("NonTerminal", "S", 1)
#
#     state = "q"
#     i = 0
#     alpha = []
#     beta = ["S"]
#     config = Configuration(state, i, alpha, beta)
#     w = ["a", "a", "c", "b", "c"]
#     parser = RDParser(grammar, config, w)
#     prod_string = parser.drp()
#     for item in prod_string:
#         print(item)
#
#     yes = ParseTree(grammar, prod_string[0], prod_string)
#     yes.create_table()

    # parse table

    # s1 = WorkingElement("NonTerminal", "S", 1)
    # s2 = WorkingElement("NonTerminal", "S", 2)
    # s3 = WorkingElement("NonTerminal", "S", 3)
    # s32 = WorkingElement("NonTerminal", "S", 3)
    # l = [s1, s2, s3, s32]
    #
    # yes = ParseTree(grammar, s1, l)
    # yes.create_table()





    # console = Console(grammar)
    # console.run()