import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;

public class control{ 
    static String IR;
    static int ra, rb, rd, rm, pc_value, immidiate;
    static String ry, rz;
    static int muxB, muxY;
    static register_file register_file_object;
    static instructions instruction_object;
    static memory memory_object;
    PC pc_object;




    control(){
        pc_value = 0;
        IR = "";
        register_file register_file_object = new register_file();
        instructions instruction_object = new instructions(pc_object);  
        memory memory_object = new memory();
        PC pc_object = new PC();
    }



    // seperate line_no and machine code.
    static String convert_machine_code_line_to_instruction(String line, int line_no){
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


    static String convert_int_to_string(int value){
        String s = "";
        while(value!=0){
            int temp = value % 2;
            value /= 2; 
            s = (char)(temp+48) + s;
        }
        return s;
    }


    static void fetch(){
        
        for(int i = 0; i < 4; i++){
            int val = memory_object.loadByte(pc_value);
            IR = IR + convert_int_to_string(val);
            pc_value++; 
        }

    }





    static int decoder(){
        List<Integer> opcode_func3_func7_rs1_rs2_rd_immidiate_n = new ArrayList<Integer>();

        /*
            opcode_func3_func7_rs1_rs2_rd_immidiate_n = decode();    
        */ 
        
        int which_instruction = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(7);
        ra = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(3));
        rb = register_file_object.load_from_register(opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(4));
        rd = opcode_func3_func7_rs1_rs2_rd_immidiate_n.get(5);
        rm = rb;
        immidiate = opcode_func3_func7_rs1_rs2_rd_immidiate_n(6);
        
        /* Control Unit to update all the values of mux */
        
        return which_instruction;
    }


    /*
        kept ry and rz as string and if function return intger convert it to String.
    */
    static void ALU(int which_instruction){
        if(which_instruction == 1){
            int temp;        
            temp = instruction_object.add(ra, muxB);    
            rz = Integer.toString(temp);
        }           
        else if(which_instruction == 2){
            int temp;
            temp = instruction_object.and(ra, muxB);
            rz = Integer.toString(temp);
        }
        else if(which_instruction == 12){
            int ry;
            ry = instruction_object.jalr(ra, muxB, pc_value);
        }   
        else if(which_instruction == 10){
            int temp;
            temp = instruction_object.addi(ra, rb);
            rz = Integer.toString(temp);
        }
    }



    static void memory_read_write(){
        // get value of muxY
        if(muxY == 0)
            ry = rz;
        else if(muxY == 1){
            int address = Integer.parseInt(ry);
            // rz = memory_object.load_from_memory(address);
            // rm to memory to store
        }
        else if(muxY == 2){

        }
    }


    static void storing_in_memory(String line_input){
        char[] line = line_input.toCharArray();
        for(int i = 0; i < 4; i++){
            int out = 0;
            for(int j = 8*i + 7; j >= 8*i; j--){
                int current_no = 0;
                if(line[j] == '0')
                    current_no = 0;
                else
                    current_no = 1;
                out += current_no * Math.pow(2, (8*i+7) - j);
            }
            memory_object.storeByte(out);
        }
    }



    public static void main(String args[]){
        int address_c, address_b, address_a, rz, rm;
        int line_no = 0;
        BufferedReader file_reader;
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                line = convert_machine_code_line_to_instruction(line, line_no);
                storing_in_memory(line);
            }
        }
        catch(IOException e){
            System.out.println("You either deleted or shifted the file containing machine code");
        }
        System.out.println("Added to memory");
        
        
        while(pc_value <= memory_object.code_start){
            
            fetch();
            
            int which_instruction = decoder();
            
            ALU(which_instruction);
            
            memory_read_write();
        }
    }
}