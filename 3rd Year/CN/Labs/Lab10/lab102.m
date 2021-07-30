
warning("off");

n = [10:15]; 

for i = 1:length(n)
  H = hilb(n(i)); 
  i
  c2 = cond(H)
  c1 = cond(H, 1)
  cinf = cond(H, inf)
   
endfor