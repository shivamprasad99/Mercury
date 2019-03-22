import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;

public class control{ 
    static String IR = "";
    static int ra, rb, rd, rm, pc_value = 0, immidiate;
    static boolean condition_signal_beq = false; // control unit must set these accordingly
    static boolean condition_signal_bge = false; // control unit must set these accordingly
    static boolean condition_signal_bgeu = false; // control unit must set these accordingly
    static boolean condition_signal_blt = false; // control unit must set these accordingly
    static boolean condition_signal_bne = false; // control unit must set these accordingly
    static boolean condition_signal_bltu = false; // control unit must set these accordingly
    static int b_select, y_select, pc_select, inc_select, ma_select;
    static int ry, rz;
    static int muxB, muxY;
    static register_file register_file_object = new register_file();
    static instructions instruction_object;
    static memory memory_object = new memory();
    static number_to_instrucions_function control_and_name_of_instruction = new number_to_instrucions_function();;
    static PC pc_object = new PC();
    static int line_no;
    static stageTwoDecode decoder_object = new stageTwoDecode(); 


    // control(){
    //     pc_value = 0;
    //     IR = "";
    //     register_file register_file_object = new register_file();
    //     memory memory_object = new memory();
    //     PC pc_object = new PC();
    //     instructions instruction_object = new instructions(pc_object);  
    //     number_to_instrucions_function control_and_name_of_instruction = new number_to_instrucions_function();
    //     control_and_name_of_instruction.set_number_to_instrucions_function();
    //     stageTwoDecode decoder_object = new stageTwoDecode();
    // }



    // seperate line_no and machine code.
    static String convert_machine_code_line_to_instruction(String line){
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




    static void fetch(){
        IR = "";
        for(int i = 0; i < 4; i++){
            int val = memory_object.loadByte(pc_value);
            String currentBinary = Integer.toBinaryString(256 + val);
            String s = currentBinary.substring(currentBinary.length() - 8);
            IR = IR + s;
            pc_value++; 
        }
       
    }

    static void change_conditional_signal(int which_instruction){
        switch(which_instruction){
            case 30: condition_signal_beq = true;
                break;
            case 31: condition_signal_bge = true;
                break;
            case 32: condition_signal_bgeu = true;
                break;
            case 33: condition_signal_blt = true;
                break;
            case 34: condition_signal_bne = true;
                break;
            case 35: condition_signal_bltu = true;
        }
    }



    static int decoder(){
        int which_instruction=0;
        try{
            // System.out.println(IR);
            ArrayList<Integer> rs1_rs2_rd_immidiate_n =  decoder_object.decode(IR);   
            which_instruction = rs1_rs2_rd_immidiate_n.get(4);
            ra = register_file_object.load_from_register(rs1_rs2_rd_immidiate_n.get(0));
            // System.out.println(rs1_rs2_rd_immidiate_n.get(1));
            rb = register_file_object.load_from_register(rs1_rs2_rd_immidiate_n.get(1));
            rd = rs1_rs2_rd_immidiate_n.get(2);
            rm = rb;
            immidiate = rs1_rs2_rd_immidiate_n.get(3);
            String s = control_and_name_of_instruction.get_control_unit_values(which_instruction);
            
            String[] c = s.split(" ");
            char[] control_unit = c[1].toCharArray();
            b_select = control_unit[0];
            y_select = control_unit[1];
            pc_select = control_unit[2];
            inc_select = control_unit[3];
            ma_select = control_unit[4];
        }catch(Exception e){
            // System.out.print("haha");
        }

        return which_instruction;
    }


    /*
        kept ry and rz as string and if function return intger convert it to String.
    */
    static void ALU(int which_instruction){
        switch(b_select) {
            case 0: muxB = rb; break;
            case 1: muxB = immidiate; break;
        }
        if(which_instruction == 1 || which_instruction == 10 || (which_instruction >= 13 && which_instruction <= 17) || (which_instruction >= 27 && which_instruction <= 29)){
            rz = instruction_object.add(ra, muxB);    
        }           
        else if(which_instruction == 2 || which_instruction == 11){
            rz = instruction_object.and(ra, muxB);
            
        }
        else if(which_instruction == 3 || which_instruction == 18){
            rz = instruction_object.or_(ra, muxB);
            
        }
        else if(which_instruction == 4 || which_instruction == 19){
            rz = instruction_object.sll(ra, muxB);
            
        }
        else if(which_instruction == 5 || which_instruction == 20){
            rz = instruction_object.slt(ra, muxB);
            
        }
        else if(which_instruction == 6 || which_instruction == 21){
            rz = instruction_object.sltu(ra, muxB);
            
        }
        else if(which_instruction == 7 || which_instruction == 22){
            rz = instruction_object.sra(ra, muxB);
            
        }
        else if(which_instruction == 8){
            rz = instruction_object.sub(ra, muxB);
            
        }
        else if(which_instruction == 9 || which_instruction == 24){
            rz = instruction_object.xor(ra, muxB);
            
        }
        else if(which_instruction == 12){
            int ry;
            ry = instruction_object.jalr(ra, muxB, pc_value);
        }
        // where is srl in which_instruction
        else if(which_instruction == 23){
            int temp;
            temp = instruction_object.srl(ra, muxB);
            
        }
        else if(which_instruction == 25 || which_instruction == 26){
            int temp;
            // give pc to ra and immediate value to muxB
            // ALU will do the 12 bit shifting for you
            temp = instruction_object.wide_immediate_addition(ra, muxB);
            
        }
        else if(which_instruction == 30){
            change_conditional_signal(which_instruction);
            if(condition_signal_beq==true){
                pc_value  = pc_value + muxB;
            }
        }
    }

    static void memory_read_write(){
        // get value of muxY
        if(muxY == 0)
            ry = rz;
        else if(muxY == 1){
            // rz = memory_object.load_from_memory(address);
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
            System.out.print(out+" ");
            memory_object.storeByte(out);
        }
        System.out.println();
    }



    public static void main(String args[]){
        instruction_object = new instructions(pc_object);
        int address_c, address_b, address_a, rz, rm;
        BufferedReader file_reader;
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                line = convert_machine_code_line_to_instruction(line);
                System.out.print(line+" ");
                storing_in_memory(line);
            }
        }
        catch(IOException e){
            System.out.println("You either deleted or shifted the file containing machine code");
        }
        System.out.println("Added to memory");
        
        
        while(pc_value < memory_object.code_start){
            // System.out.println(pc_value + " " + memory_object.code_start);
            fetch();
            
            int which_instruction = decoder();
            
            ALU(which_instruction);
            
            memory_read_write();
        }
    }
}
