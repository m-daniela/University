format long

x=[1 1.5 2 3 4];
f=[0 0.17609 0.30103 0.47712 0.60206];
xx=[2.5, 3.25];
pol = NewtonPol_stud(x, f, xx)
# 0.3968

i = 10:35
yi = i./10
pol2 = NewtonPol_stud(x, f, yi)

er = max(abs(log10(yi) - pol2))