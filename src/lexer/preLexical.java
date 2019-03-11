package lexer;
import grammar.*;

import java.io.*;
import java.util.*;


public class preLexical {

    private static Map<String,Integer> labelMap =new HashMap<String, Integer>();


    public static void main(String argv[]){
        try {
            if ( argv.length != 1 )
                throw new Error( "Usage: java Main filename" );
            File file = new File(argv[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            Yylex lexer = new Yylex(br);
            int nToken=lexer.yylex();

            while(nToken>=0) {
                if (nToken == 16) {
                    String removeColon =new String(lexer.yytext());
                    removeColon=removeColon.substring(0,removeColon.length()-1);
                    labelMap.put(removeColon, lexer.yylineno());
                }
                nToken = lexer.yylex();
            }
            br= new BufferedReader( new FileReader(file));
            lexer=new Yylex(br);
            nToken=lexer.yylex();
            BufferedWriter wr = new BufferedWriter((new FileWriter(new File("src/lexer/modified.asm"))));

            while (nToken>=0){           /* System.out.println(lexer.yytext())*/;

                if(nToken==14){
                    int temp=labelMap.get(lexer.yytext())-lexer.yylineno();
                    System.out.println(temp);
                    wr.write(String.valueOf(temp));
                }
                else if(nToken==15){
                     String x=lexer.yytext();

                     StringBuffer y=new StringBuffer(x.charAt(0));
                     int i;
                     System.out.println(x);

                     for(i=1;i<x.length();i++){
                         if(x.charAt(i)=='(')
                             break;
                         y.append(x.charAt(i));
                     }
                     i++;

                    StringBuffer z= new StringBuffer(x.charAt(i));
                    for(;i<x.length();i++){
                        if(x.charAt(i)==')') break;
                        else z.append(x.charAt(i));
                    }
                    System.out.println(z);

                    wr.write(z.append(" ").append(y).toString());
                }
                else if(nToken==18){
                    wr.newLine();
                }
                else if(nToken==19){
                    wr.write(" ");
                }
                else if(nToken==16){
                    //ignore
                }
                else if(nToken==11){
                    String temp=lexer.yytext();
                    temp=temp.substring(2);
                    Integer z= Integer.parseInt(temp,16);
                    wr.write(String.valueOf(z));
                }
                else {
                    wr.write(lexer.yytext());
                }
                nToken=lexer.yylex();
            }                wr.close();


        }
        catch ( Exception exception ) {
            System.out.println( "Exception in Main "+ exception.toString() );
            exception.printStackTrace();
        }
    }

}
