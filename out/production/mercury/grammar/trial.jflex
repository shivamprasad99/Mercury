package grammar;
import java.io.*;
import java.lang.*;
<<<<<<< HEAD
=======
import java_cup.runtime.*;
>>>>>>> ec3e56e681ff6105b3c30370eff659add64575ed
%%

%{
void printf(String s){
    System.out.print(s);
}

public int yylineno(){
    return yyline+1;
}
%}

rType =                                          add|and|or|sll|slt|sltu|sra|srl|sub|xor
iType =                                          addi|andi|jalr|lb|lw|lh|lwu|ori|slli|srli|xori|lbu|lhu|slti|sltiu|srai
uType =                                          auipc|lui
sType =                                          sb|sh|sw
sbType =                                         beq|bge|bgeu|blt|bne|bltu
ujType  =                                        jal
pseudo =                                         li|la
%standalone
%public

%%

<<<<<<< HEAD
[\t\, ]+                                        {/* ignore whitespace */;return 19;}
"#".*[^\n]                                      {/*printf("Comment ");*/return 0;}
:                                               {/*printf("Colon ");*/return 1;}
[\n]                                            {yyline++;/*printf("\n");*/return 18;}  /*new change*/
=======
[\t\, ]+                                        {/* ignore whitespace */;} 
"#".*[^\n]                                      {/*printf("Comment ");*/return 0;}
:                                               {/*printf("Colon ");*/return 1;}
[\n]                                            {yyline++;/*printf("\n");*/}
>>>>>>> ec3e56e681ff6105b3c30370eff659add64575ed
".data"|".text"                                 {/*printf("Directive ");*/return 2;}
{rType}                                         {/*printf("Instruction ");*/return 3;}
{iType}                                         {/*printf("Instruction ");*/return 4;}
{uType}                                         {/*printf("Instruction ");*/return 5;}
{sType}                                         {/*printf("Instruction ");*/return 6;}
{sbType}                                        {/*printf("Instruction ");*/return 7;}
{ujType}                                        {/*printf("Instruction ");*/return 8;}
{pseudo}                                        {/*printf("Pseudo ");*/return 9;}
.word|.byte                                     {/*printf("Datatype ");*/return 10;}
"0x"[0-9a-f]+                                   {/*printf("Hex ");*/return 11;}
"x"[0-9]+                                       {/*printf("Register ");*/return 12;}
[-]?[1-9]+                                      {/*printf("Number ");*/return 13;}
<<<<<<< HEAD
[a-zA-Z]+(:)                                    {/*printf("LabelSource");*/return 16;}        /* new_change */
[a-zA-Z]+                                       {/*printf("Label ");*/return 14;}
[0-9]+\([x][0-9]*\)                                {/*printf("Memory ");*/return 15;}

.                                               {/*printf("Unexpected ");*/return 17;}  /* new_change */
=======
[a-zA-Z]+                                       {/*printf("Label ");*/return 14;}
[0]\([x][0-9]*\)                                {/*printf("Memory ");*/return 15;}
.                                               {/*printf("Unexpected ");*/return 16;}
>>>>>>> ec3e56e681ff6105b3c30370eff659add64575ed
