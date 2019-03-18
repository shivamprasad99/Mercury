/*
    to use this class
    first set_pc, set_muxPc, set_muxInc
    then use adder which will return the next pc
*/
// import class containg RA and RB;

public class PC {
    int pc = 0x0, pc_temp;  // default value of PC set to 0
    int muxPc, muxInc = 4;  // default value of MuxINC is 4

    // default value is 0x0
    void set_pc(int pc_value){
        pc = pc_value;
    }

    // RA or
    void set_muxPc(int Mux_PC_value){
        muxPc = Mux_PC_value;
    }

    // default value is 4
    void set_muxInc(int Mux_INC_value){
        muxInc = Mux_INC_value;
    }

    int adder(){
        pc_temp = pc;
        pc = pc+muxInc;
        return pc;
    }
}
