

class SymTable:
    def __init__(self, size):
        self.__size = size
        self.__table = [[] for _ in range(size)]

    def hashfun(self, element):
        char_sum = 0
        for char in element:
            char_sum += ord(char)
        return char_sum % self.__size

    def insert(self, element):
        position = self.hashfun(element)
        if element not in self.__table[position]:
            self.__table[position].append(element)

    def lookup(self, position_table, position_list):
        try:
            return self.__table[position_table][position_list]
        except Exception:
            return None

    def get_position(self, element):
        table_position = self.hashfun(element)
        elements_on_pos = self.__table[table_position]

        if len(elements_on_pos) != 0:
            for i, e in enumerate(elements_on_pos):
                if e == element:
                    return table_position, i
        else:
            return -1, -1

    def __str__(self):
        table = ""
        for i, elements in enumerate(self.__table):
            if len(elements) != 0:
                table += f'{i}: {elements}\n'
        return table


def testing():
    print("Testing")
    symtab = SymTable(50)
    symtab.insert("a")
    # "a" will not appear twice
    symtab.insert("a")
    symtab.insert("b")
    symtab.insert("aux")

    symtab.insert("0")
    symtab.insert("23")
    symtab.insert('"abcd"')
    symtab.insert("'@'")

    print(str(symtab))

    print(symtab.get_position("aux"))
    print(symtab.get_position('"abcd"'))
    print(symtab.get_position("0"))
    print(symtab.get_position("fff"))
    assert symtab.get_position("aux") == (34, 0)
    assert symtab.get_position('"abcd"') == (12, 0)
    assert symtab.get_position("0") == (48, 1)
    assert symtab.get_position("fff") == (-1, -1)

    print(symtab.lookup(48, 0))
    print(symtab.lookup(30, 1))
    assert symtab.lookup(48, 0) == "b"
    assert symtab.lookup(30, 1) is None


# if __name__ == "__main__":
#     testing()