import java.io.*;
import java.util.*;

class register_file{
    LinkedHashMap<Interger, Interger> register_address_to_value = new LinkedHashMap<Interger, Interger>();
    for(int i = 0; i < 32; i++){
        register_address_to_value.put(i, Integer.MAX_VALUE);
    }

    void store_in_register(int index, int value){
        register_address_to_value.put(index, value);
    }

    int load_from_register(int index){
        return register_address_to_value.get(index);
    }
}