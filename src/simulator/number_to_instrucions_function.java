import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;

class number_to_instrucions_function{
    /*
        b_select, y_select, pc_select, inc_select, ma_select
    */
    
    static LinkedHashMap<Integer, String> integer_to_inst = new LinkedHashMap<Integer, String>();
    static void set_number_to_instrucions_function(){
        integer_to_inst.put(1, "add 00100");
        integer_to_inst.put(2, "and 00100");
        integer_to_inst.put(3, "or 00100");
        integer_to_inst.put(4, "sll ");
        integer_to_inst.put(5, "slt");
        integer_to_inst.put(6, "sltu");
        integer_to_inst.put(7, "sra");
        integer_to_inst.put(8, "sub");
        integer_to_inst.put(9, "xor");
        integer_to_inst.put(10,"addi 10100");
        integer_to_inst.put(11,"andi");
        integer_to_inst.put(12,"jalr");
        integer_to_inst.put(13,"lb");
        integer_to_inst.put(14,"lw 11100");
        integer_to_inst.put(15,"lh");
        integer_to_inst.put(16,"lhu");
        integer_to_inst.put(17,"lbu");
        integer_to_inst.put(18,"ori");
        integer_to_inst.put(19,"slli");
        integer_to_inst.put(20,"slti");
        integer_to_inst.put(21,"sltiu");
        integer_to_inst.put(22,"srai");
        integer_to_inst.put(23,"srli");
        integer_to_inst.put(24,"xori 10100");
        integer_to_inst.put(25,"auipc");
        integer_to_inst.put(26,"lui");
        integer_to_inst.put(27,"sb");
        integer_to_inst.put(28,"sh");
        integer_to_inst.put(29,"sw 11100");
        integer_to_inst.put(30,"beq");
        integer_to_inst.put(31,"bge");
        integer_to_inst.put(32,"bgeu");
        integer_to_inst.put(33,"blt");
        integer_to_inst.put(34,"bne");
        integer_to_inst.put(35,"bltu");
        integer_to_inst.put(36,"jal");
    }
    


    static String get_control_unit_values(int which_instrucion){
        return integer_to_inst.get(which_instrucion);
    }
    

    
}