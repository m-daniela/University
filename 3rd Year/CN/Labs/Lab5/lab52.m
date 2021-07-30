
x = [1 2]
y = [0 0.6931]
yy = [1 0.5]
xx = [1.5]

ans = HermitePol(x, y, yy, xx)

err = abs(log(xx) - ans)