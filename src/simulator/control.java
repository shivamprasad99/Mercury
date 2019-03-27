package  simulator;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;



public class control{ 
    static String IR = "";
    static int ra, rb, rd, rm, pc_value = 0, immediate, pc_temp;
    static boolean condition_signal_beq = false; // control unit must set these accordingly
    static boolean condition_signal_bge = false; // control unit must set these accordingly
    static boolean condition_signal_bgeu = false; // control unit must set these accordingly
    static boolean condition_signal_blt = false; // control unit must set these accordingly
    static boolean condition_signal_bne = false; // control unit must set these accordingly
    static boolean condition_signal_bltu = false; // control unit must set these accordingly
    static int ry, rz;
    static int muxA,muxB, muxY, muxPc, muxMa, muxInc;
    static int memoryData;
    static register_file register_file_object = new register_file();
    static instructions instruction_object;
    static memory memory_object = new memory();
    static number_to_instrucions_function control_and_name_of_instruction = new number_to_instrucions_function();;
    static PC pc_object = new PC();
    static int line_no;
    static stageTwoDecode decoder_object = new stageTwoDecode(); 
    static int which_instruction;
    static controlUnit controlUnitObject = new controlUnit();


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
    
    static String seperate_line_and_machineCode(String line){
        char[] char_line = line.toCharArray();  // convert String to char_array
        int i = 0;
        String s = "";
        while(char_line[i] != ' '){
            s += char_line[i];
            i++;    
        }
        line_no = Integer.parseInt(s);
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

    // static String convert_machine_code_line_to_instruction(String line){
    //     String[] s = line.split(" ");
    //     line_no = Integer.valueOf(s[0]);
    //     return s[1];
    // }

    static void setMuxValues(){
        change_conditional_signal(which_instruction);
        if(controlUnitObject.pcSelect == 1)
            pc_object.muxPc = pc_value;
        if(controlUnitObject.pcSelect == 0)
            pc_object.muxPc = ra;
        if(controlUnitObject.maSelect == 0)
            muxMa = rz;
        if(controlUnitObject.maSelect == 1)
            muxMa = pc_value;
        if(controlUnitObject.incSelect == 0)
            pc_object.muxInc = 4;
        if(controlUnitObject.incSelect == 1)
            pc_object.muxInc = immediate;
        if(controlUnitObject.bSelect == 0)
            muxB = rb;
        if(controlUnitObject.bSelect == 1)
            muxB = immediate;
        if(controlUnitObject.ySelect == 0)
            ry = rz;
        if(controlUnitObject.ySelect == 1)
            ry = memoryData;
        if(controlUnitObject.ySelect == 2)
            ry = pc_object.pc_temp;
        if(controlUnitObject.aSelect==0)
            muxA=ra;
        if(controlUnitObject.aSelect==1)
            muxA=pc_value;              //auipc
        if(controlUnitObject.aSelect==2)
            muxA=0;             //lui
    }


    static void stage1(){
        immediate = 0;
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



    static void decoder(){
        ArrayList<Integer> rs1_rs2_rd_immediate_n =  decoder_object.decode(IR);   
        System.out.println(rs1_rs2_rd_immediate_n);
        // System.out.println(rs1_rs2_rd_immediate_n.get(1));
        
        try{
            which_instruction = rs1_rs2_rd_immediate_n.get(4);
        }catch(Exception e){}

        try{
            ra = register_file_object.load_from_register(rs1_rs2_rd_immediate_n.get(0));
        }catch(Exception e){}

        try{
            rb = register_file_object.load_from_register(rs1_rs2_rd_immediate_n.get(1));
            rm = rb;
        }catch(Exception e){}
    
        try{
            rd = rs1_rs2_rd_immediate_n.get(2);
        }catch(Exception e){}
        
        try{
            immediate = rs1_rs2_rd_immediate_n.get(3);
        }catch(Exception e){}
    
        // System.out.println(ra + " "+rb+ " "+rd+ " "+immediate);
    }


    /*
        kept ry and rz as string and if function return intger convert it to String.
    */
    static void ALU(){
        if(which_instruction == 1 || which_instruction == 10 || (which_instruction >= 13 && which_instruction <= 17) || (which_instruction >= 27 && which_instruction <= 29)){
            rz = instruction_object.add(muxA, muxB);
        }           
        else if(which_instruction == 2 || which_instruction == 11){
            rz = instruction_object.and(muxA, muxB);
            
        }
        else if(which_instruction == 3 || which_instruction == 18){
            rz = instruction_object.or_(muxA, muxB);
            
        }
        else if(which_instruction == 4 || which_instruction == 19){
            rz = instruction_object.sll(muxA, muxB);
            
        }
        else if(which_instruction == 5 || which_instruction == 20){
            rz = instruction_object.slt(muxA, muxB);
            
        }
        else if(which_instruction == 6 || which_instruction == 21){
            rz = instruction_object.sltu(muxA, muxB);
            
        }
        else if(which_instruction == 7 || which_instruction == 22){
            rz = instruction_object.sra(muxA, muxB);
            
        }
        else if(which_instruction == 8){
            rz = instruction_object.sub(muxA, muxB);
        }
        else if(which_instruction == 9 || which_instruction == 24){
            rz = instruction_object.xor(muxA, muxB);
        }
        else if(which_instruction == 12){   // jalr
            pc_value = pc_object.adder(pc_value);
            
        }
        if(which_instruction == 36){   // jal
            pc_value = pc_object.adder(pc_value);
        }
        // where is srl in which_instruction
        else if(which_instruction == 23){
            int temp;
            temp = instruction_object.srl(muxA, muxB);
            
        }
        else if(which_instruction == 25 || which_instruction == 26){
            //  int temp;
            // temp=muxB<<20;
            //rz=muxA+temp;
            // give pc to ra and immediate value to muxB
            // ALU will do the 12 bit shifting for you
            rz = instruction_object.wide_immediate_addition(muxA, muxB);
        }
        else if(which_instruction == 30){
            if(condition_signal_beq==true){
                pc_value  = immediate;
            }
        }
        else if(which_instruction == 31){
            if(condition_signal_bge==true){
                pc_value  = immediate;
            }
        }
        else if(which_instruction == 32){
            if(condition_signal_bgeu==true){
                pc_value  = immediate;
            }
        }
        else if(which_instruction == 33){
            if(condition_signal_blt==true){
                pc_value  = immediate;
            }
        }
        else if(which_instruction == 34){
            if(condition_signal_bne==true){
                pc_value  = immediate;
            }
        }
        else if(which_instruction == 35){
            if(condition_signal_bltu==true){
                pc_value  = immediate;
            }
        }
        System.out.println("rz "+rz);
    }


    static void memory_read_write(){
        System.out.println("rm + muxMa "+rm+" "+muxMa);
        // using muxMa
        if(which_instruction == 13){
            memoryData = memory_object.loadByte(muxMa);
        }
        if(which_instruction == 14){
            System.out.println("inside");
            memoryData = memory_object.loadWord(muxMa);
            System.out.println("memoryData "+memoryData);
        }
        if(which_instruction == 27){
            memory_object.storeDataByte(rm, muxMa);
        }
        if(which_instruction == 29){
            memory_object.storeDataWord(rm, muxMa);
        }
    }


    static void writeBack(){
        if(controlUnitObject.rfWrite == 1)
            register_file_object.store_in_register(rd, ry);
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
        control_and_name_of_instruction.set_number_to_instrucions_function();
        BufferedReader file_reader;
        register_file_object.store_in_register(2,memory_object.stack_start);
        
        try{
            file_reader = new BufferedReader(new FileReader("./test"));    
            String line = "";
            
            while((line = file_reader.readLine()) !=null){
                if(line.length() == 0)
                    continue;
                line = seperate_line_and_machineCode(line);
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
            
            controlUnitObject.stage1();
            setMuxValues();
            stage1();
            // controlUnit.reset();
            controlUnitObject.stage2();
            setMuxValues();
            decoder();
            
            // controlUnit.reset();
            controlUnitObject.setInstruction(which_instruction);
            controlUnitObject.stage3();
            setMuxValues();https://advitiya.in/viewhttps://advitiya.in/view
            ALU();
            /// call setMuxInc()
            
            // controlUnit.reset();
            setMuxValues();         // for muxMa
            controlUnitObject.stage4();
            setMuxValues();
            memory_read_write();    // for ry

            // controlUnit.reset();
            controlUnitObject.stage5();
            setMuxValues();
            writeBack();

            register_file_object.printRegisterFile();
        }
    }
}
