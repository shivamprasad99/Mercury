import java.io.*;
import java.util.*;

class instructions{
    static PC pc_object;
    instructions(PC pc_object_value){
        pc_object = pc_object_value;
    }

    public static int add(int ra, int rb, int pc_value){
        return ra+rb;
    }
    public static int and(int ra, int rb, int pc_value){
        return ra + rb;
    }
    public static int jalr(int ra, int offset, int pc_value){
        pc_object.set_muxInc(offset);
        pc_object.set_muxPc(ra);
        pc_value = pc_object.adder();
        int pc_temp = pc_object.get_pc_temp();
        return pc_temp;
    }
    public static int addi(int ra, int rb){
        return ra+rb;
    }
}