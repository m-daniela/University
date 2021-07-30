format long;

A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10];
b = [32; 23; 33; 31];

[n, m] = size(A);

# a.

cond_nr = cond(A)
x = gauss(n, A, b)

# b.

b2 = [32.1; 22.9; 33.1; 30.9];

x2 = gauss(n, A, b2)

input_rel_b = norm(b - b2)/ norm(b)
output_rel_b = norm(x - x2)/ norm(x)
err_b = input_rel_b / output_rel_b

# c.

A2 = [10 7 8.1 7.2; 7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98];

x3 = gauss(n, A2, b)

input_rel_c = norm(A - A2)/ norm(A)
output_rel_c = norm(x - x3)/ norm(x)
err_c = input_rel_c / output_rel_c