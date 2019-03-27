import java.io.*;
import java.util.*;


public class preLexical {

    private static Map<String,Integer> labelMap =new HashMap<String, Integer>();

    public static void preprocessor(String filename){
        try {
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));
            Yylex lexer = new Yylex(br);
            BufferedWriter wr = new BufferedWriter((new FileWriter(new File("src/lexer/preprocessed.asm"))));
            int nToken = lexer.yylex();
            while(nToken>=0){
                if(nToken == 18) {
                    wr.write("\n");
                    while (nToken == 18 || nToken == 0) {
                        nToken = lexer.yylex();
                    }
                }
                if(nToken == 16){
                    wr.write(lexer.yytext());
                    wr.write(" ");
                    nToken = lexer.yylex();
                    while(nToken == 18 || nToken == 0){
                        nToken = lexer.yylex();
                    }
                }
                if(nToken == 2 && lexer.yytext().equals(".data")){
                    System.out.println("Entered");
                    nToken = lexer.yylex();
                    while(nToken != 2 && nToken != -1){
                        nToken = lexer.yylex();
                    }
                }

                wr.write(lexer.yytext());
                wr.write(" ");
                nToken = lexer.yylex();
            }
            wr.close();
        }catch(Exception exception){
            System.out.println( "Exception in Main "+ exception.toString() );
            exception.printStackTrace();
        }
    }

    public static void main(String argv[]){
        try {
            if ( argv.length != 1 )
                throw new Error( "Usage: java Main filename" );
            preprocessor(argv[0]);
            File file = new File("src/lexer/preprocessed.asm");
            BufferedReader br = new BufferedReader(new FileReader(file));
            Yylex lexer = new Yylex(br);
            int nToken=lexer.yylex();
            int flag = 0;
            while(nToken>=0) {
                if (nToken == 16) {
                    String removeColon = lexer.yytext();
                    removeColon=removeColon.substring(0,removeColon.length()-1);
                    labelMap.put(removeColon, lexer.yylineno());
                    flag = 1;
                }
                nToken = lexer.yylex();
                if(flag==1 && nToken == 18){
//                    lexer.adjustlineno();
                }
                flag = 0;
            }
            br= new BufferedReader( new FileReader(file));
            lexer=new Yylex(br);
            nToken=lexer.yylex();
            BufferedWriter wr = new BufferedWriter((new FileWriter(new File("src/lexer/modified.asm"))));

            while (nToken>=0){           /* System.out.println(lexer.yytext())*/;

                if(nToken==14){
                    int temp = 0;
                    try {
                        temp = labelMap.get(lexer.yytext()) - lexer.yylineno();
                    }
                    catch ( Exception exception ) {
                        System.out.println( "Exception in Main "+ exception.toString() );
                        exception.printStackTrace();
                    }
//                    System.out.println(temp);
                    wr.write(String.valueOf(temp*4));
                    wr.write(" ");
                }
                else if(nToken==15){
                     String x=lexer.yytext();

                     StringBuffer y=new StringBuffer();
                     int i;
//                     System.out.println(x);

                     for(i=0;i<x.length();i++){
                         if(x.charAt(i)=='(')
                             break;
                         y.append(x.charAt(i));
                     }
                     i++;
//                    System.out.println(y);
                    StringBuffer z= new StringBuffer();
                    for(;i<x.length();i++){
                        if(x.charAt(i)==')') break;
                        else z.append(x.charAt(i));
                    }
//                    System.out.println(z);

                    wr.write(z.append(" ").append(y).toString());
                    wr.write(" ");
                }
                else if(nToken==0){
                    ;
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
                    wr.write(" ");
                }
                else {
                    wr.write(lexer.yytext());
                    wr.write(" ");
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