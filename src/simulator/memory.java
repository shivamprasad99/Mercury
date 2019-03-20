import java.io.*;
import java.util.*;

public class memory {

    static protected LinkedHashMap<Integer, String> memory_linked_hash_map = new LinkedHashMap<Integer, String>();
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

    

    public static String load_from_memory(int address){
        checker();
        return memory_linked_hash_map.get(address);
    }


    public static void store_code_memory(String value_to_be_stored){
        checker();
        memory_linked_hash_map.put(code_start, value_to_be_stored);
        System.out.println(code_start + " " + value_to_be_stored);
    }

    public static void store_data_memory(String value_to_be_stored){
        checker();
        memory_linked_hash_map.put(data_start, value_to_be_stored);
        System.out.println(data_start + " " + value_to_be_stored);
    }
    public static void store_stack_memory(String value_to_be_stored){
        checker();
        memory_linked_hash_map.put(stack_start, value_to_be_stored);
        System.out.println(stack_start + " " + value_to_be_stored);
    }
}
