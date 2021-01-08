

**Commands**

```bash
flex spec.lxi
bison -d lang.y
gcc lex.yy.c lang.tab.c -o result
result < input.txt
```