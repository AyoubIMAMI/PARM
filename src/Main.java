import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<String> instructions;

        InputReader inputReader = new InputReader("asm/test.s");
        instructions = inputReader.readFile();

        Instruction instruction;
        InstructionLibrary instructionLib = new InstructionLibrary();

        for (String instructionString : instructions){
            instruction = new Instruction(instructionString);
            instructionLib.assignOpcode(instruction);
            System.out.println(instruction);
        }
    }
}
