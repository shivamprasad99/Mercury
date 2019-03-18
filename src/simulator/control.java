import java.io.*;
import java.util.*;

public class control{ 
    /*
        Not including the part where we get value from lexer
        reading as <line_no><space><instruction>
    */

    private static String convert_machine_code_line_to_instruction(String line, int line_no){
        char[] char_line = line.toCharArray();  // convert String to char_array
        line_no = (int)(char_line[0]) - 48;     // getting line_no
        int i = 1;
        while(char_line[i] == ' '){             // ignoring the spaces
            i++;
        } 
        int j = 0;
        char[] instruction_for_decoder = new char[32];
        int length_line = line.length();
        length_line -= i;                       // length of line to be read(if correct then 32 bit)
        while(j < length_line){
            instruction_for_decoder[j] = char_line[i];
            i++;j++;
        }
        line = new String(instruction_for_decoder);     // convert charArray to String
        return line;
    }


    public static void main(String args[]){
        int muxB, ra, rb, address_c, address_b, address_a, rz, ry, rm;
        int line_no = 0;
        BufferedReader file_reader;
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                /*
                    here write code to send to decoder
                    decoder(line);  in place of System.out.println(line)

                    I have a line no but what to do with it
                */
                line = convert_machine_code_line_to_instruction(line, line_no);
                System.out.println(line);
            }
        }
        catch(IOException e){
            System.out.println("You either deleted or shifted the file containing machine code");
        }
    }
}



