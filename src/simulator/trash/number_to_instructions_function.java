import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;

class number_to_instrucions_function{
    
    /*
        b_select, y_select, pc_select, inc_select, ma_select
    */
    LinkedHashMap<String, Interger> instruction_to_integer = new LinkedHashMap<String, Interger>();
    
    number_to_instrucions_function(){
        instruction_to_integer.put(1, "add 00100");
        instruction_to_integer.put(2, "and 00100");
        instruction_to_integer.put(3, "or 00100");
        instruction_to_integer.put(4, "sll");
        instruction_to_integer.put(5, "slt");
        instruction_to_integer.put(6,"sltu");
        instruction_to_integer.put(7,"sra");
        instruction_to_integer.put(8,"sub");
        instruction_to_integer.put(9,"xor");
        instruction_to_integer.put(10,"addi");
        instruction_to_integer.put(11,"andi");
        instruction_to_integer.put(12,"jalr");
        instruction_to_integer.put(13,"lb");
        instruction_to_integer.put(14,"lw");
        instruction_to_integer.put(15,"lh");
        instruction_to_integer.put(16,"lhu");
        instruction_to_integer.put(17,"lbu");
        instruction_to_integer.put(18,"ori");
        instruction_to_integer.put(19,"slli");
        instruction_to_integer.put(20,"slti");
        instruction_to_integer.put(21,"sltiu");
        instruction_to_integer.put(22,"srai");
        instruction_to_integer.put(23,"srli");
        instruction_to_integer.put(24,"xori");
        instruction_to_integer.put(25,"auipc");
        instruction_to_integer.put(26,"lui");
        instruction_to_integer.put(27,"sb");
        instruction_to_integer.put(28,"sh");
        instruction_to_integer.put(29,"sw");
        instruction_to_integer.put(30,"beq");
        instruction_to_integer.put(31,"bge");
        instruction_to_integer.put(32,"bgeu");
        instruction_to_integer.put(33,"blt");
        instruction_to_integer.put(34,"bne");
        instruction_to_integer.put(35,"bltu");
        instruction_to_integer.put(36,"jal");
    }


    static String get_control_unit_values(int which_instrucion){
        return instruction_to_integer.get(which_instrucion);
    }
    

    
}