from flcd.CustomException import CustomException
from flcd.Scanner import Scanner
from flcd.SymTable import SymTable

if __name__ == "__main__":
    try:
        symtab = SymTable(50)
        scanner = Scanner(symtab)
        scanner.read_file("files/p1.txt")
        scanner.scan()
        scanner.write_file()
    except CustomException as e:
        print(e)
