import java.io.*;

public class lexer{
    public static void main( String[] argv ) {
        System.out.println("yes");
        try {
            if ( argv.length != 1 )
                throw new Error( "Usage: java Main filename" );
            FileInputStream fileInputStream = new FileInputStream( argv[ 0 ] );
            // BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Yylex lexer = new Yylex(fileInputStream);
            // String str = in.readLine();
            // lexer.yylex();
            // while(str!=null){
            //     lexer.yylex();
            //     str = in.readLine();
            // }
        }
        catch ( Exception exception ) {
            System.out.println( "Exception in Main "+ exception.toString() );
            exception.printStackTrace();
        }
    }
}