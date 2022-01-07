import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<String> instructions;

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("========== Assembly to hexadecimal  ==========");
        System.out.println("Note : Files will be read and write in asm folder");
        System.out.println("Select the .s file to compile: ");
        System.out.print(">_ ");
        String filePath = myObj.nextLine();
        System.out.println("========== Converting ... ==========");

        InputReader inputReader = new InputReader("asm/" + filePath + ".s");
        instructions = inputReader.readFile();

        Instruction instruction;
        InstructionLibrary instructionLib = new InstructionLibrary();

        StringBuilder output = new StringBuilder("v2.0 raw\n");

        for (String instructionString : instructions){
            instruction = new Instruction(instructionString);
            instructionLib.assignOpcode(instruction);
            output.append(instruction).append(" ");
        }

        WriteFile writeFile = new WriteFile("asm/" + filePath + ".bin");
        System.out.println(output);
        writeFile.writeFile(output.toString());
        System.out.println("========== File saved in asm/" + filePath + ".bin ==========");
    }
}
