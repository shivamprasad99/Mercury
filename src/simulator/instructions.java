import java.io.*;
import java.util.*;

class instructions{
    static PC pc_object;
    instructions(PC pc_object_value){
        pc_object = pc_object_value;
    }

    public static int add(int ra, int rb){
        return ra+rb;
    }
    public static int and(int ra, int rb){
        return ra + rb;
    }
    public static int addi(int ra, int rb){
        return ra+rb;
    }
    public static int or_(int ra, int rb){
        return ra|rb;
    }
    public static int sll(int ra, int rb){
        return ra<<rb;
    }
    public static int slt(int ra, int rb){
        if(ra<rb)
            return 1;
        return 0;
    }
    public static int sltu(int ra, int rb){
        if(ra-rb<0) // of course you're smart DB, but not sure about this one
            return 1;
        return 0;
    }
    public static int sra(int ra, int rb){
        return ra>>rb;
    }
    public static int sub(int ra, int rb){
        return ra-rb;
    }
    public static int xor(int ra, int rb){
        return ra^rb;
    }
    public static int srl(int ra, int rb){
        return ra>>>rb;
    }
    public static int wide_immediate_addition(int ra, int rb) {
        return ra+(rb<<12);
    }
    public static int lw(int ra, int rb){
        return ra+rb;
    }
    public static int sw(int ra, int rb){
        return ra+rb;
    }
}