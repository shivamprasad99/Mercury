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
        int muxB, ra, rb, address_c, address_b, address_a, rz, rm;
        int pc_value = 0;
        int line_no = 0;
        
        PC pc_object = new PC();
        register_file register_file_object = new register_file();
        instructions instruction_object = new instructions(pc_object);  
        memory memory_object = new memory();
        
        BufferedReader file_reader;
        List<Integer> opcode_func3_func7_rs1_rs2_rd_immidiate_n = new ArrayList<Integer>();
        
        /*
            testing
        */

        /* stored in the memory */
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                line = convert_machine_code_line_to_instruction(line, line_no);
                memory_object.store_code_memory(line);
                memory_object.increment_code_start(4);
            }
        }
        catch(IOException e){
            System.out.println("You either deleted or shifted the file containing machine code");
        }
        System.out.println("Added to memory");
        while(pc_value <= memory_object.code_start){
            /*
                here write code to send to decoder
                opcode_func3_func7_rs1_rs2_rd_immidiate = decoder(line); 

                I have a line no but what to do with it???
            */
            memory_object.load_from_memory(pc_value);    
            
            /*
                right now it will show out of bound error
            */
            int which_instruction = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(7);
            
            ra = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(3));
            rb = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(4));
            
            
            if(which_instruction == 1){
                int ry;
                muxB = rb;                          // just to get some feel
                ry = instruction_object.add(ra, muxB, pc_value);    
                rz = ry;
            }           
            else if(which_instruction == 2){
                int ry;
                muxB = rb;
                ry = instruction_object.and(ra, muxB, pc_value);
                rz = ry;
            }
            else if(which_instruction == 12){
                int ry;
                muxB = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(6);
                ry = instruction_object.jalr(ra, muxB, pc_value);
            }   
            else if(which_instruction == 14){
                String ry;
                muxB = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(6);
                ry = instruction_object.lw(ra, muxB, pc_value, memory_object);
            }
            else if(which_instruction == 10){
                int ry;
                muxB = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(6);
                ry = instruction_object.addi(ra, rb, pc_value);
            }
        }
    }
}