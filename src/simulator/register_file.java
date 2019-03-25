import java.io.*;
import java.util.*;

class register_file{
    LinkedHashMap<Integer, Integer> register_address_to_value = new LinkedHashMap<Integer, Integer>();
    register_file(){
        register_address_to_value.put(0, 0);
        for(int i = 1; i < 32; i++){
            register_address_to_value.put(i, Integer.MAX_VALUE);
        }
    }
    void store_in_register(int index, int value){
        if(index < 0 || index > 31){
            return;
        } 
        register_address_to_value.put(index, value);
    }

    int load_from_register(int index){
        if(index < 0 || index > 31){
            return 0;
        } 
        return register_address_to_value.get(index);
    }

    void printRegisterFile(){
        for(int i = 0; i < 32; i++){
            System.out.println("x"+i+" "+register_address_to_value.get(i));
        }
    }
}