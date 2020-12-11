from flcd.CustomException import CustomException
from flcd.Lab5.Grammar import Grammar
from flcd.Lab5.ParserOutput import ParseTree
from flcd.Lab5.RDParser import Configuration, RDParser
from flcd.Scanner import Scanner
from flcd.SymTable import SymTable


class Console:
    def __init__(self, grammar, parser):
        self.commands = {"0": lambda: exit(0),
                         "1": lambda: grammar.get_non_term(),
                         "2": lambda: grammar.get_terminals(),
                         "3": lambda: grammar.get_starting(),
                         "4": lambda: grammar.get_productions(),
                         "5": lambda x: grammar.get_productions_for_non_terminal(x),
                         "6": lambda x: parser.drp(x),
                         }

        self.menu = """
        0. Exit
        1. Get non terminals
        2. Get terminals
        3. Get starting symbol
        4. Get productions
        5. Get productions for a non-terminal
        6. Check if sequence is accepted and get output
        """

    def run(self):
        while True:
            print(self.menu)
            try:
                choice = input("Your command: ")
                if choice == "5":
                    nt = input("Give non-terminal: ")
                    print(self.commands[choice](nt))
                elif choice == "6":
                    try:
                        prod_string = parser.drp()
                        parse_table = ParseTree(grammar, prod_string[0], prod_string)
                        parse_table.create_table()
                    except Exception as e:
                        print(e)
                else:
                    print(self.commands[choice]())
            except KeyError:
                print("Wrong command")


if __name__ == "__main__":
    print("""
    1. Sequence
    2. Program
    """)
    try:
        choice = input("Choice: ")

        grammar = Grammar()

        if choice == "1":
            filename = "./Lab5/files/seq.in"
            grammar.read_file("./Lab5/files/g1.in")
        elif choice == "2":
            symtab = SymTable(50)
            scanner = Scanner(symtab)
            scanner.read_file("files/p1.txt")
            scanner.scan()
            scanner.write_file()
            filename = "files/pif.out"
            grammar.read_file("./Lab5/files/g2m.txt")
        else:
            raise CustomException("Wrong command")

        # initial configuration for Parser
        state = "q"
        i = 0
        alpha = []
        beta = [grammar.get_starting()]

        config = Configuration(state, i, alpha, beta)

        parser = RDParser(grammar, config, filename)

        console = Console(grammar, parser)
        console.run()

    except CustomException as e:
        print(e)
