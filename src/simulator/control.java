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
        int pc_value = 0;
        int line_no = 0;
        BufferedReader file_reader;
        List<Integer> opcode_func3_func7_rs1_rs2_rd_immidiate_n;
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                /*
                    here write code to send to decoder
                    opcode_func3_func7_rs1_rs2_rd_immidiate = decoder(line);  in place of System.out.println(line)

                    I have a line no but what to do with it
                */
                line = convert_machine_code_line_to_instruction(line, line_no);
                System.out.println(line);
            }
        }
        catch(IOException e){
            System.out.println("You either deleted or shifted the file containing machine code");
        }


        int which_instruction = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(7);
        PC pc_object = new PC();
        register_file register_file_object = new register_file();
        instructions instruction_object = new instructions(pc_object);  

        ra = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(3));
        rb = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(4));
        
        
        if(which_instruction == 1){
            muxB = rb;                          // just to get some feel
            ry = instruction_object.add(ra, muxB, pc_value);    
        }           
        else if(which_instruction == 2){
            muxB = rb;
            ry = instruction_object.and(ra, muxB, pc_value);
        }
        else if(which_instruction == 12){
            muxB = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(6);
            ry = instruction_object.jalr(ra, muxB, pc_value);
        }   
    }
}



