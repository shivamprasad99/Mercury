import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.Math;

public class memory {

    static LinkedHashMap<Integer, Byte> memory_linked_hash_map = new LinkedHashMap<Integer, Byte>();
    static int code_start = 0x0;
    static int data_start = 0xffffffff;
    static int stack_start= 0x7ffffffc;

    public static void checker(){
        if(code_start < 0x0 && code_start >= 0xffffffff){
            System.out.println("Out of bound");
            System.exit(0);
        }
        else if(data_start >= stack_start){
            System.out.println("Out of bound");
            System.exit(0);
        }
    }
    public static void increment_code_start(int how_much){
        checker();
        code_start = code_start + how_much;
    }
    public static void increment_data_start(int how_much){
        checker();
        data_start = data_start + how_much;
    }
    public static void increment_stack_start(int how_much){
        checker();
        stack_start = stack_start + how_much;
    }

    
    public static void storeByte(int value){
        checker();
        int a2 = 0xff;
        value = value & a2;
        byte b = (byte)(a2 & value);
        memory_linked_hash_map.put(code_start, b);    
        code_start += 1;
    }

    static byte[] toByte(int i){
        byte[] word = new  byte[4];
        word[3] = (byte)(i>>24);
        word[2] = (byte)(i>>16);
        word[1] = (byte)(i>>8);
        word[0] = (byte)(i>>0);
        return word;
    }
    
    static int toInt(String[] hex){
        int a = 0;
        int sum = 0;
        for(int t = 0; t < 4; t++){
            String[] arr = hex[t].split("");
            System.out.println(hex[t]+" ");
            for(int i = 0; i < hex[t].length(); i++){
                int temp = (int) Math.pow(16, i+sum);
                int val = Integer.parseInt(arr[  hex[t].length() - i - 1  ], 16);
                a+=temp*val;
                // System.out.println(temp+" "+val+" "+ temp*val);
            }
            sum+=2;
        }
        return a;
    }
    
    public static void storeWord(int value){
        byte[] bt = toByte(value);
        storeByte(bt[0]);
        code_start++;
        storeByte(bt[1]);
        code_start++;
        storeByte(bt[2]);
        code_start++;
        storeByte(bt[3]);
        code_start++;
    }


    public static int loadByte(int address){
        byte bt = memory_linked_hash_map.get(address);
        int output = bt & 0xff;
        String hex = Integer.toHexString(output);
        String[] arr = hex.split(""); 
        int a = 0;
        for(int i =0; i < hex.length(); i++){
            int temp = (int)Math.pow(16, i);
            int val = Integer.parseInt(arr[hex.length() - i - 1], 16);
            a += temp*val;
        }
        return a;    
    }

    public static int loadWord(int address){
        byte[] bt = new byte[4];
        bt[0] = memory_linked_hash_map.get(address);
        bt[1] = memory_linked_hash_map.get(address+1);
        bt[2] = memory_linked_hash_map.get(address+2);
        bt[3] = memory_linked_hash_map.get(address+3);
        int removing_negative[] = new int[4];
        for(int i = 0; i < 4; i++){
            removing_negative[i] = bt[i] & 0xff;
        }
        String[] hex = new String[4];
        hex[0] = Integer.toHexString(removing_negative[0]);
        hex[1] = Integer.toHexString(removing_negative[1]);
        hex[2] = Integer.toHexString(removing_negative[2]);
        hex[3] = Integer.toHexString(removing_negative[3]);
        int b = toInt(hex);
        return b;

    }


    // public static String load_string_from_memory(int address){
    //     checker();
    //     return memory_linked_hash_map.get(address);
    // }


    // public static void store_code_memory(String value_to_be_stored){
    //     checker();
    //     memory_linked_hash_map.put(code_start, value_to_be_stored);
    //     System.out.println(code_start + " " + value_to_be_stored);
    // }

    // public static void store_data_memory(String value_to_be_stored){
    //     checker();
    //     memory_linked_hash_map.put(data_start, value_to_be_stored);
    //     System.out.println(data_start + " " + value_to_be_stored);
    // }
    // public static void store_stack_memory(String value_to_be_stored){
    //     checker();
    //     memory_linked_hash_map.put(stack_start, value_to_be_stored);
    //     System.out.println(stack_start + " " + value_to_be_stored);
    // }

}
