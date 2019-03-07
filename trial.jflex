import java.io.*;
import java.lang.*;
import java_cup.runtime.*;
%%

%{
void printf(String s){
    //System.out.print();
}
%}

rType =                                          add|and|or|sll|slt|sltu|sra|srl|sub|xor
iType =                                          addi|andi|jalr|lb|lw|lh|lwu|ori|slli|srli|xori|la
uType =                                          auipc|lui
sType =                                          sb|sh|sw
sbType =                                         beq|bge|bgeu|blt|bne|bltu
ujType  =                                        jal
%standalone

%%

[\t\, ]+                                        {/* ignore whitespace */;} 
"#".*[^\n]                                      {printf("Comment ");}
:                                               {printf("Colon ");}
[\n]                                            {yyline++;printf("\n");}
".data"|".text"                                 {printf("Directive ");}
{rType}                                         {printf("Instruction ");}
{iType}                                         {printf("Instruction ");}
{uType}                                         {printf("Instruction ");}
{sType}                                         {printf("Instruction ");}
{sbType}                                        {printf("Instruction ");}
{ujType}                                        {printf("Instruction ");}
.word|.byte                                     {printf("Datatype ");}
"0x"[0-9a-f]+                                   {printf("Hex ");}
"x"[0-9]+                                       {printf("Register ");}
[-]?[1-9]+                                      {printf("Number ");}
[a-zA-Z]+                                       {printf("Label ");}
[0]\([x][0-9]*\)                                {printf("Memory ");}
.                                               {printf("Unexpected ");}