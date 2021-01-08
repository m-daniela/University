
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     NUMBER = 258,
     STRING = 259,
     CHAR = 260,
     BOOL = 261,
     VOID = 262,
     CONST = 263,
     WHILE = 264,
     FOR = 265,
     IF = 266,
     ELSE = 267,
     ELSEIF = 268,
     FUNCTION = 269,
     READ = 270,
     WRITE = 271,
     STOP = 272,
     IN = 273,
     TRUE = 274,
     FALSE = 275,
     CONSTANT = 276,
     IDENTIFIER = 277,
     EQ = 278,
     PLUS = 279,
     MINUS = 280,
     MULTIPLY = 281,
     DIVIDE = 282,
     EQQ = 283,
     LT = 284,
     GT = 285,
     LE = 286,
     GE = 287,
     DIFF = 288,
     AND = 289,
     OR = 290,
     MOD = 291,
     NOT = 292,
     OPEN_PAR = 293,
     CLOSE_PAR = 294,
     OPEN_SQ = 295,
     CLOSE_SQ = 296,
     OPEN_CURLY = 297,
     CLOSE_CURLY = 298,
     COMMA = 299,
     SEMICOL = 300
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


