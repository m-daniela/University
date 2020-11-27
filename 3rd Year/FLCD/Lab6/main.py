from Lab5.Grammar import Grammar
from Lab5.RDParser import WorkingElement
from Lab5.ParserOutput import ParseTree

if __name__ == "__main__":
    grammar = Grammar()
    grammar.read_file("g3.txt")

    state = "q"
    i = 0
    alpha = []
    beta = ["S"]
    #
    # config = Configuration(state, i, alpha, beta)
    # parser = Parser(grammar, config)
    #
    # parser.expand()
    # parser.advance()
    # print(config)
    # parser.expand()
    # parser.another_try()
    # print(config)


    s1 = WorkingElement("NonTerminal", "S", 1)
    s2 = WorkingElement("NonTerminal", "S", 2)
    s3 = WorkingElement("NonTerminal", "S", 3)
    s32 = WorkingElement("NonTerminal", "S", 3)
    l = [s1, s2, s3, s32]
    yes = ParseTree(grammar, s1, l)
    yes.create_table()


    # console = Console(grammar)
    # console.run()