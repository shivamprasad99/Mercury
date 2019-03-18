class instruction_function_to_number{
    public static void main(String args[]){
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
    }
}