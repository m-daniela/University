
# choose nodes close to 115
x = [64, 81, 100, 121, 144, 169]
y = sqrt(x)
a = 115

res = AitkenPol_stud(x, y, a)
er = abs(sqrt(a) - res)

##res = 10.7236
##err = 1.1613...e-04