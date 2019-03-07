import java.io.*;

public class lexer{
    public static void main( String[] argv ) {
        try {
            if ( argv.length != 1 )
                throw new Error( "Usage: java Main filename" );
            File file = new File(argv[0]);
            BufferedReader in = new BufferedReader(new FileReader(file));
            Yylex lexer = new Yylex(in);
            System.out.println(lexer.yylex());
        }
        catch ( Exception exception ) {
            System.out.println( "Exception in Main "+ exception.toString() );
            exception.printStackTrace();
        }
    }
}