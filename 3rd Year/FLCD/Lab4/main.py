from flcd.Tokenization import Tokenization

if __name__ == "__main__":
    tokens = Tokenization("pif.txt")
    tokens.read_file("p1.txt")
    tokens.get_reserved()
    tokens.scan()
