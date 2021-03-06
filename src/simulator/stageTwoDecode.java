import java.io.*;
import java.util.*;
	class Data{
		Data(){}
    	Data(String op,String f7,String f3){
        	opcode = op;
        	func7 = f7;
        	func3 = f3;
    	}
    	String opcode;
    	String func3;
    	String func7;
	}

public class stageTwoDecode{
		private static Map<String,Data> ins = new HashMap<String,Data>();
		String instructionType="";
		String instruction="";
		public static String getType(String inst){
			String instr = inst.substring(0, 1);
			/*
			System.out.println();
			System.out.println(instr);
			System.out.println();
			*/
			if(instr.equals("I")){ return "I"; }
			if(instr.equals("R")){ return "R"; }
			if(instr.equals("U")){ return "U"; }
			if(instr.equals("J")){ return "UJ"; }
			if(instr.equals("B")){ return "SB"; }
			if(instr.equals("S")){ return "S"; }
			return "";
		}

		public static int getRs1(String inst){
			String strRs1 = inst.substring(7, 12);
			int foo = Integer.parseInt(strRs1, 2);
			return foo;
		}
		public static int getRs2(String inst){
			String strRs1 = inst.substring(12, 17);
			int foo = Integer.parseInt(strRs1, 2);
			return foo;
		}
		public static int getRd(String inst){
			String strRs1 = inst.substring(20, 25);
			int foo = Integer.parseInt(strRs1, 2);
			return foo;
		}
		public static int getIm(String insType, String inst){
			if(insType == "I"){
					String strRs1 = inst.substring(0, 11);
					int foo = Integer.parseInt(strRs1, 2);
					return foo;
			}else if(insType == "U" || insType == "J"){
				String strRs1 = inst.substring(0, 20);
				int foo = Integer.parseInt(strRs1, 2);
				return foo;
			}else if(insType == "S" || insType == "B"){
				String strRs1 = inst.substring(0, 7)+inst.substring(17, 22);
				int foo = Integer.parseInt(strRs1, 2);
				return foo;
			}
			return -1; // wrong input
		}

    	private static void initialize(){
		        ins.put("Radd",new Data("0110011","0000000","000"));
		        ins.put("Rand",new Data("0110011","0000000","111"));
		        ins.put("Ror",new Data("0110011","0000000","110"));
		        ins.put("Rsll",new Data("0110011","0000000","001"));
		        ins.put("Rslt",new Data("0110011","0000000","010"));
		        ins.put("Rsltu",new Data("0110011","0000000","011"));
		        ins.put("Rsra",new Data("0110011","0100000","101"));
		        ins.put("Rsrl",new Data("0110011","0000000","101"));
		        ins.put("Rsub",new Data("0110011","0100000","000"));
		        ins.put("Rxor",new Data("0110011","0000000","100"));
		        ins.put("Iaddi",new Data("0010011","","000"));
		        ins.put("Iandi",new Data("0010011","","111"));
		        ins.put("Ijalr",new Data("1100111","","000"));
		        ins.put("Ilb",new Data("0000011","","000"));
		        ins.put("Ilw",new Data("0010011","","010"));
		        ins.put("Ilh",new Data("0010011","","001"));
		        ins.put("Ilhu",new Data("0010011","","101"));
		        ins.put("Ilbu",new Data("0010011","","100"));
		        //  ins.put("lwu",new Data(("0010011","","000"));
		        ins.put("Iori",new Data("0010011","","110"));
		        ins.put("Islli",new Data("0010011","","001"));
		        ins.put("Islti",new Data("0010011","","010"));
		        ins.put("Isltiu",new Data("0010011","","011"));
		        ins.put("Israi",new Data("0010011","","101"));
		        ins.put("Isrli",new Data("0010011","","101"));
		        ins.put("Ixori",new Data("0010011","","100"));
		//        ins.put("la",new Data(("0010011","","000"));
		        ins.put("Uauipc",new Data("0010111","",""));
		        ins.put("Ului",new Data("0110111","",""));
		        ins.put("Ssb",new Data("0100011","","000"));
		        ins.put("Ssh",new Data("0100011","","001"));
		        ins.put("Ssw",new Data("0100011","","010"));
		        ins.put("Bbeq",new Data("1100011","","000"));
		        ins.put("Bbge",new Data("1100011","","101"));
		        ins.put("Bbgeu",new Data("1100011","","111"));
		        ins.put("Bblt",new Data("1100011","","100"));
		        ins.put("Bbne",new Data("1100011","","001"));
		        ins.put("Bbltu",new Data("1100011","","110"));
		        ins.put("Jjal",new Data("1101111","",""));
			}

			private int getInstructionNumber(String instruction){
				LinkedHashMap<String, Interger> instruction_to_integer = new LinkedHashMap<String, Interger>();
        			instruction_to_integer.put('add', 1);
				instruction_to_integer.put('and', 2);
				instruction_to_integer.put('or', 3);
				instruction_to_integer.put('sll', 4);
				instruction_to_integer.put('slt', 5);
				instruction_to_integer.put('sltu', 6);
				instruction_to_integer.put('sra', 7);
				instruction_to_integer.put('sub', 8);
				instruction_to_integer.put('xor', 9);
				instruction_to_integer.put('addi', 10);
				instruction_to_integer.put('andi', 11);
				instruction_to_integer.put('jalr', 12);
				instruction_to_integer.put('lb', 13);
				instruction_to_integer.put('lw', 14);
				instruction_to_integer.put('lh', 15);
				instruction_to_integer.put('lhu', 16);
				instruction_to_integer.put('lbu', 17);
				instruction_to_integer.put('ori', 18);
				instruction_to_integer.put('slli', 19);
				instruction_to_integer.put('slti', 20);
				instruction_to_integer.put('sltiu', 21);
				instruction_to_integer.put('srai', 22);
				instruction_to_integer.put('srli', 23);
				instruction_to_integer.put('xori', 24);
				instruction_to_integer.put('auipc', 25);
				instruction_to_integer.put('lui', 26);
				instruction_to_integer.put('sb', 27);
				instruction_to_integer.put('sh', 28);
				instruction_to_integer.put('sw', 29);
				instruction_to_integer.put('beq', 30);
				instruction_to_integer.put('bge', 31);
				instruction_to_integer.put('bgeu', 32);
				instruction_to_integer.put('blt', 33);
				instruction_to_integer.put('bne', 34);
				instruction_to_integer.put('bltu', 35);
				instruction_to_integer.put('jal', 36);

				return instruction_to_integer.get(instruction);

			}

		public ArrayList decoder(String input){
			ArrayList<Integer> retVal = new ArrayList<>();
			String inst = "";
			try{
					initialize();
					String tempStr = input; /*Input*/
					////////////////////////////////////
					// System.out.println(tempStr);
					////////////////////////////////////
					String opcode = tempStr.substring(25); /*getting opcode*/
					String func7 = tempStr.substring(0, 7);/*getting function 7*/
					String func3 = tempStr.substring(11, 14);/*getting function 3*/
					///////////////////////////////////
					// System.out.println(opcode);
					// System.out.println(func3);
					// System.out.println(func7);
					///////////////////////////////////
					Data insData = new Data(opcode, "", "");
					Data insData1 = new Data(opcode, "", func3);
					Data insData2 = new Data(opcode, func7, func3);
					for(Map.Entry<String, Data> entry : ins.entrySet()){
						Data tempData = entry.getValue();
						// System.out.println(insData2);
						if(insData.opcode.equals(tempData.opcode) && insData.func3.equals(tempData.func3) && insData.func7.equals(tempData.func7)){
								inst = inst + entry.getKey();
								instruction = instruction + inst.substring(1);
								instructionType = instructionType + getType(inst);
						}else if(insData1.opcode.equals(tempData.opcode) && insData1.func3.equals(tempData.func3) && insData1.func7.equals(tempData.func7)){
								inst = inst + entry.getKey();
								instruction = instruction + inst.substring(1);
								instructionType = instructionType + getType(inst);
						}else if(insData2.opcode.equals(tempData.opcode) && insData2.func3.equals(tempData.func3) && insData2.func7.equals(tempData.func7)){
								inst = inst + entry.getKey();
								instruction = instruction + inst.substring(1);
								instructionType = instructionType + getType(inst);
						}
					}
					///////////////////////////////////
					// System.out.println(tempStr);
					// System.out.println(instruction);
					// System.out.println(instructionType);
					///////////////////////////////////
					retVal.add(opcode);
					retVal.add(func3);
					retVal.add(func7);
					int instructionNumber = getInstructionNumber(instruction);
					if(instructionType.equals("R")){
						retVal.add(getRs1(tempStr));
						retVal.add(getRs2(tempStr));
						retVal.add(getRd(tempStr));
						retVal.add(-1);
						retVal.add(instructionNumber);
						return retVal;
					}
					if(instructionType.equals("I")){
						retVal.add(getRs1(tempStr));
						retVal.add(-1);
						retVal.add(getRd(tempStr));
						retVal.add(getIm(instructionType, tempStr));
						retVal.add(instructionNumber);
						return retVal;
					}
					if(instructionType.equals("U") || instructionType.equals("UJ")){
						retVal.add(-1);
						retVal.add(-1);
						retVal.add(getRd(tempStr));
						retVal.add(getIm(instructionType, tempStr));
						retVal.add(instructionNumber);
						return retVal;
					}
					if(instructionType.equals("S") || instructionType.equals("SB")){
						retVal.add(getRs1(tempStr));
						retVal.add(getRs2(tempStr));
						retVal.add(-1);
						retVal.add(getIm(instructionType, tempStr));
						retVal.add(instructionNumber);
						return retVal;
					}
			}catch(Exception e){
				System.out.println("File not found: "+e);
			}
			return null;
		}
}
