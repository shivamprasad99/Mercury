/*
    to use this class
    first set_pc, set_muxPc, set_muxInc
    then use adder which will return the next pc
*/
// import class containg RA and RB;

class PC {
    public static int pc, pc_temp;  // default value of PC set to 0
    public static int muxPc, muxInc;  // default value of MuxINC is 4

    PC(){
        pc = 0x0;
        muxInc = 4;
    }
    // default value is 0x0 to change it this is the function
    public static void set_pc(int pc_value){
        pc = pc_value;
    }

    // RA or returned value of adder
    public static void set_muxPc(int Mux_PC_value){
        muxPc = Mux_PC_value;
    }

    // with how to increment, default 4 | offset in some instructions
    public static void set_muxInc(int Mux_INC_value){
        muxInc = Mux_INC_value;
    }

    public static int get_pc_temp(){
        return pc_temp;
    }

    public static int adder(){
        pc_temp = pc;
        pc = muxPc;
        pc = pc+muxInc;
        return pc;
    }
}
