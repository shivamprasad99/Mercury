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

public class assignment1{

	public static class stageTwo{
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
		public ArrayList decoder(){
			ArrayList<Integer> retVal = new ArrayList<>();
			File file;
			String inst = "";
			try{
				file = new File("test.txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String str;
				while((str = br.readLine()) != null){
					String[] tempStr = str.split(" ");
					////////////////////////////////////
					System.out.println(tempStr[1]);
					////////////////////////////////////
					String opcode = tempStr[1].substring(25);
					String func7 = tempStr[1].substring(0, 7);
					String func3 = tempStr[1].substring(11, 14);
					///////////////////////////////////
					System.out.println(opcode);
					System.out.println(func3);
					System.out.println(func7);
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
								break;
						}else if(insData1.opcode.equals(tempData.opcode) && insData1.func3.equals(tempData.func3) && insData1.func7.equals(tempData.func7)){
							  // getType();
								inst = inst + entry.getKey();
								instruction = instruction + inst.substring(1);
								instructionType = instructionType + getType(inst);
								break;
						}else if(insData2.opcode.equals(tempData.opcode) && insData2.func3.equals(tempData.func3) && insData2.func7.equals(tempData.func7)){
								// getType();
								inst = inst + entry.getKey();
								instruction = instruction + inst.substring(1);
								instructionType = instructionType + getType(inst);
								break;
						}
					}
					///////////////////////////////////
					System.out.println(tempStr[1]);
					System.out.println(instruction);
					System.out.println(instructionType);
					///////////////////////////////////
					if(instructionType.equals("R")){
						retVal.add(getRs1(tempStr[1]));
						retVal.add(getRs2(tempStr[1]));
						retVal.add(getRd(tempStr[1]));
						return retVal;
					}
					if(instructionType.equals("I")){
						retVal.add(getRs1(tempStr[1]));
						retVal.add(getRd(tempStr[1]));
						retVal.add(getIm(instructionType, tempStr[1]));
						return retVal;
					}
					if(instructionType.equals("U") || instructionType.equals("UJ")){
						retVal.add(getIm(instructionType, tempStr[1]));
						retVal.add(getRd(tempStr[1]));
						return retVal;
					}
					if(instructionType.equals("S") || instructionType.equals("SB")){
						retVal.add(getRs1(tempStr[1]));
						retVal.add(getRs2(tempStr[1]));
						retVal.add(getIm(instructionType, tempStr[1]));
						return retVal;
					}
					// System.out.println(tempS);
				}
			}catch(Exception e){
				System.out.println("File not found: "+e);
			}
			return null;
		}
	}

	public static void main(String []args){
		stageTwo obj = new stageTwo();
		ArrayList<Integer> dec = new ArrayList<>();
		obj.initialize();
		dec = obj.decoder();
		System.out.println(dec);
	}
}
