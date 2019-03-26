public class controlUnit{

    //each of these functions need to be run before any action for that stage.


    private int whichInstruction; //the instruction number

    protected int stageCounter;

        //control signals for data Path

    protected int bSelect, ySelect, cSelect, aluOp, condtSignals, rfWrite;

        //control signals for processor-memory interface and IR control signals

    protected int maSelect, memRead, memWrite, mfc, irEnable;

        //control signals for IAG

    protected int pcSelect, pcEnable, incSelect;


//    controlUnit(int whichInstruction){
//            stageCounter=0;
//            this.whichInstruction=whichInstruction;
//
//    }

    void stage1(){
        pcSelect=1;
        maSelect=1;     //address is to be sent from PC through muxMA
        incSelect=0;    //to add 4 to pc
    }

    void stage2(){
        pcSelect=0;
    }

    void setInstruction(int whichInstruction){      //to be called after decode and before execute
        this.whichInstruction=whichInstruction;
    }

    void stage3(){
        if(whichInstruction<10||(whichInstruction>29&&whichInstruction!=36))    //instruction requiring register B
            bSelect=0;      //0 for rb
        else bSelect=1;     //1 for immediate

        if(whichInstruction<12||(whichInstruction>17&&whichInstruction<24)) ySelect=0;      //0 for rz
        else if(whichInstruction==12||whichInstruction==36) ySelect =2; //2 for return address
        else ySelect=1; //1 for loading ry from memory , no effect for branching instructions

        if(whichInstruction==12) pcSelect=0; // for using return address

        if(whichInstruction==36||whichInstruction==12){         //mux inc to immediate for unconditional values
            incSelect=1;
        }

    }

    void setMuxInc(int flag){
        if(whichInstruction>=30&&whichInstruction<=35){     //mux inc to immediate after checking the condition
            if(flag==1) incSelect=1;
            else  incSelect=0;
        }
        
    }


    void stage4(){
        maSelect=0;     //address is to be sent from Rz through muxMa
    }

    void stage5(){
        if(whichInstruction<=26||whichInstruction==36)
            rfWrite=1;
        
    }




}

