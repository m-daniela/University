
from CustomException import CustomException
import re


class Scanner:
    def __init__(self, symbol_table):
        self.__symbol_table = symbol_table
        self.__lines = []
        self.__pif = []
        self.__reserved_words = []
        self.__separators = []
        self.__simple_operators = []
        self.__compound_operators = []
        self.import_reserved()

    def read_file(self, filename):
        with open(filename, "r") as f:
            self.__lines = f.readlines()

    # saves the reserved words, operators and separators
    # the elements of each category are grouped
    # after the file is parsed, the operators are split
    # into simple and compound operators, so they will be easier to find
    # in: name of the file
    def import_reserved(self, filename="files/token.in"):
        operators = []

        with open(filename, "r") as f:
            lines = f.readlines()

        for i in range(5, len(lines)):
            element = lines[i].strip().split(" ")[0]
            if i < 20:
                operators.append(element)
            elif i < 28:
                self.__separators.append(element)
            elif i > 28:
                self.__reserved_words.append(element)

        for operator in operators:
            if len(operator) > 1:
                self.__compound_operators.append(operator)
            else:
                self.__simple_operators.append(operator)

    # detects the tokens from the given program
    # the regex values are saved in the beginning and the splitter function is
    # called for each line, for each type of operators
    # in: line_tokens - a list with the tokens on one line from the input file, found by
    # splitting after space
    # out: a list with each identifier, constant, reserved word, operator and separator
    def detect_tokens(self, line_tokens):
        regex = ["[()\[\]{};,]", "(==|<=|>=|!=|&&|\|\|)", "[=+\-*/<>%!]"]
        split_by_separators = self.splitter(line_tokens, [], regex[0])
        split_by_compound_operators = self.splitter(split_by_separators, [], regex[1])
        split_by_simple_operators = self.splitter(split_by_compound_operators, [], regex[2])
        return split_by_simple_operators

    # recursive function which checks and re-splits the tokens if necessary
    # in:
    # - line_tokens: list of tokens to be checked
    # - list_tokens: the new list, after each run
    # - regex: the pattern to search for in the tokens
    # out: the list of final tokens split by the category of the given regex
    def splitter(self, line_tokens, list_tokens, regex):
        for token in line_tokens:
            try:
                token.strip()
                if re.search(regex, token):
                    split_by, position = re.search(regex, token).group(), re.search(regex, token).start()
                    new_line_list = token.split(split_by)

                    if (split_by is "-" or split_by is "+") and new_line_list[0] is "":
                        split_by = f"{split_by}{new_line_list[1]}"
                        if new_line_list[1] is "0":
                            raise CustomException(f"Lexical error: token {split_by}")
                        else:
                            list_tokens.append(split_by)
                    else:
                        if position != 0:
                            list_tokens.append(new_line_list[0])
                        list_tokens.append(split_by)
                        self.splitter(new_line_list[1:], list_tokens, regex)
                else:
                    list_tokens.append(token)
            except ValueError as e:
                pass
        return list_tokens

    # checks if the given token matches the identifier pattern
    # in: token
    # out: true if it matches, false otherwise
    def check_identifier(self, token):
        return re.match("^[a-zA-Z]+[a-zA-Z0-9_]*$", token) is not None

    # checks if the given token matches the constant pattern
    # in: token
    # out: true if it matches, false otherwise
    def check_constant(self, token):
        return re.match("^'.'$", token) is not None or re.match('^".*"$', token) is not None \
               or re.match("^0$|(^([+\-]){,1}[1-9]+[0-9]*)$", token) is not None

    # the scanner function, takes each line from the input file, splits
    # it and classifies the values returned by the detect_tokens function
    def scan(self):
        for line_nr, line in enumerate(self.__lines):
            try:
                line_tokens = line.strip().split(" ")
                parsed_line = self.detect_tokens(line_tokens)

                for token in parsed_line:
                    if token != "":
                        if token in self.__reserved_words or token in self.__separators \
                                or token in self.__compound_operators or token in self.__simple_operators:
                            self.__pif.append((token, -1))
                        else:
                            if self.check_identifier(token) or self.check_constant(token):
                                self.__symbol_table.insert(token)
                                self.__pif.append((token, self.__symbol_table.get_position(token)))
                            else:
                                raise CustomException(f"Lexical error: token {token}")
            except Exception as e:
                raise CustomException(f"{e} line {line_nr}")
        print("Lexically correct")

    # saves both the Program Internal Form and the Symbol Table to separate files
    def write_file(self):
        pif = ""
        for line in self.__pif:
            pif += f"{line[0]} -> {line[1]}\n"

        with open("files/pif.out", "w") as f:
            f.write(pif)

        with open("files/st.out", "w") as g:
            g.write(str(self.__symbol_table))


