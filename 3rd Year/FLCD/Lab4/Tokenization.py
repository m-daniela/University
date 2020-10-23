
import re


class Tokenization:
    def __init__(self, pif):
        self.__pif = pif
        self.__lines = None
        self.__reserved_words = []
        self.__separators = []
        self.__simple_operators = []
        self.__compound_operators = []

    def read_file(self, filename):

        with open(filename, "r") as f:
            self.__lines = f.readlines()

    def get_reserved(self, filename="token.in"):
        operators = []

        with open(filename, "r") as f:
            lines = f.readlines()

        for i in range(5, len(lines)):
            element = lines[i].strip().split(" ")[0]
            if i < 19:
                operators.append(element)
            elif i < 26:
                self.__separators.append(element)
            elif i > 27:
                self.__reserved_words.append(element)

        for operator in operators:
            if len(operator) > 1:
                self.__compound_operators.append(operator)
            else:
                self.__simple_operators.append(operator)

        # print(self.__separators)
        # print(self.__reserved_words)
        # print(self.__simple_operators)
        # print(self.__compound_operators)

    def split_by_operator(self, token):
        # print(token)
        reg = "(==)|(<=)|(>=)|(!=)|(&&)|(||)"
        if re.search(reg, token):
            split_by = re.search(reg, token).group()
            print(split_by)

    def split_by_separator(self, line_tokens, list_tokens):
        for token in line_tokens:
            token.strip()
            if re.search("[()\[\]{},;]", token):
                split_by = re.search("[()\[\]{},;\s]", token).group()
                list_tokens.append(split_by)
                new_line_list = token.split(split_by)
                self.split_by_separator(new_line_list[1:], list_tokens)
            else:
                list_tokens.append(token)
        print(list_tokens)
        # # print("".join(self.__separators))
        # if re.search("[()\[\]{},;]", token):
        #     split_by = re.search("[()\[\]{},;]", token).group()
        #     token_list.append(split_by)
        #     print(token.split(split_by)


    def scan(self):
        for line in self.__lines:
            line_tokens = line.strip().split(" ")
            self.split_by_separator(line_tokens, [])
            for token in line_tokens:
                if token in self.__reserved_words or token in self.__separators \
                        or token in self.__compound_operators or token in self.__simple_operators:
                    pass
                    # print(token, -1)
                elif token:
                    # check if identifier or constant
                    pass
                else:
                    print("Lexical error")
