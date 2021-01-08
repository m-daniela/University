%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}


%token NUMBER
%token STRING
%token CHAR
%token BOOL
%token VOID
%token CONST

%token WHILE
%token FOR
%token IF
%token ELSE
%token ELSEIF
%token FUNCTION
%token READ
%token WRITE
%token STOP
%token IN

%token TRUE
%token FALSE

%token CONSTANT
%token IDENTIFIER

%token EQ
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVIDE

%token EQQ
%token LT
%token GT
%token LE
%token GE
%token DIFF
%token AND
%token OR

%token MOD
%token NOT

%token OPEN_PAR
%token CLOSE_PAR
%token OPEN_SQ
%token CLOSE_SQ
%token OPEN_CURLY
%token CLOSE_CURLY
%token COMMA
%token SEMICOL


%start program

%%

program:     functionDecl OPEN_CURLY statementList CLOSE_CURLY | functionDecl OPEN_CURLY declList SEMICOL statementList CLOSE_CURLY
functionDecl:     FUNCTION VOID IDENTIFIER OPEN_PAR CLOSE_PAR;
declList:     declaration | arrayDecl | declaration SEMICOL declList;
declaration:     type IDENTIFIER SEMICOL | constType IDENTIFIER SEMICOL
arrayDecl:     type OPEN_SQ CONSTANT CLOSE_SQ IDENTIFIER | constType OPEN_SQ CONSTANT CLOSE_SQ IDENTIFIER;
arrayAccess:     IDENTIFIER OPEN_SQ CONSTANT CLOSE_SQ | IDENTIFIER OPEN_SQ IDENTIFIER CLOSE_SQ;
type:     NUMBER | STRING | CHAR | BOOL;
constType:     CONST type;
statementList:     statement statementList | statement;
statement:     simpleStatement SEMICOL | structStatement;
simpleStatement:     assignmentStatement | IOStatement;
assignmentStatement:     type IDENTIFIER EQ IOStatement 
	| IDENTIFIER EQ IOStatement 
	| type IDENTIFIER EQ simpleExpression 
	| constType IDENTIFIER EQ simpleExpression 
	| IDENTIFIER EQ simpleExpression 
	| IDENTIFIER EQ operationalExpression 
	| type IDENTIFIER EQ operationalExpression 
	| constType IDENTIFIER EQ operationalExpression;
operationalExpression:     simpleExpression operation simpleExpression;
IOStatement:     READ OPEN_PAR CLOSE_PAR | WRITE OPEN_PAR CONSTANT CLOSE_PAR | WRITE OPEN_PAR IDENTIFIER CLOSE_PAR;
structStatement:     ifStatement | whileStatement | forStatement;
ifStatement:     IF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY
	|IF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY ELSEIF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY
	|IF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY ELSEIF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY ELSE OPEN_CURLY statementList CLOSE_CURLY 
	| IF OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY ELSE OPEN_CURLY statementList CLOSE_CURLY
whileStatement:     WHILE OPEN_PAR condition CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY
forStatement:     FOR OPEN_PAR forExpression CLOSE_PAR OPEN_CURLY statementList CLOSE_CURLY;
simpleExpression:     IDENTIFIER | CONSTANT | arrayAccess;
forExpression:     simpleExpression IN simpleExpression 
	| type simpleExpression IN simpleExpression 
	| assignmentStatement SEMICOL condition SEMICOL CONSTANT;
condition:     simpleExpression relation simpleExpression;
operation:     PLUS | MINUS | MULTIPLY | DIVIDE | MOD | NOT;
relation:     EQQ | LT | GT | LE | GE | DIFF | AND | OR;


%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}