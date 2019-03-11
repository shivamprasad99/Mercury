package com.company;
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


    public static void increment_decrement(int which_memory_element, int how_much){
        checker();
        which_memory_element = which_memory_element + how_much;
    }

    public static String load_in_memory(int address){
        checker();
        return memory_linked_hash_map.get(address);
    }

    public static void store_in_memory(int which_memory_element, String value_to_be_stored){
        checker();
        memory_linked_hash_map.put(which_memory_element, value_to_be_stored);
    }

}
