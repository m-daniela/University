
nr = ""

for x in range(10):
    nr += f"p {x}~r;"
print(nr)

numbers = ""
for x in range(10):
    numbers += f"{x} "
print(numbers)

alphabet = ""
for y in range(ord("a"), ord("z") + 1):
    alphabet += f"{chr(y)} "

for z in range(ord("A"), ord("Z") + 1):
    alphabet += f"{chr(z)} "

final_alphabet = alphabet + numbers
print(final_alphabet)

ident = ""
for x in range(ord("a"), ord("z") + 1):
    ident += f"p1 {chr(x)}~r1;"

for z in range(ord("A"), ord("Z") + 1):
    ident += f"p1 {chr(z)}~r1;"
print(ident)

for x in final_alphabet.split(" "):
    ident += f"r1 {x}~r1;"
print(ident)

yes = ""
long_boy = "' 0 1 2 3 4 5 6 7 8 9 + - a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9 ! @ # $ % ^ & *".split(" ")
for x in long_boy:
    yes += f"q1 {x}~r1;"

print(yes)


