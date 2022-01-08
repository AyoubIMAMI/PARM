import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<String> instructions;

        // Asking user for file selection.
        Scanner myObj = new Scanner(System.in);
        System.out.println("========== Assembly to hexadecimal  ==========");
        System.out.println("Note : Files will be read and write in asm folder");
        System.out.println("Select the .s file to compile: ");
        System.out.print(">_ ");
        String filePath = myObj.nextLine();
        System.out.println("========== Converting ... ==========");
        InputReader inputReader = new InputReader("asm/" + filePath + ".s");
        instructions = inputReader.readFile();

        // Setting up the library of every possible instruction.
        Instruction instruction;
        InstructionLibrary instructionLib = new InstructionLibrary();

        // Setting up the Branch Manager.
        BranchManager branchManager = new BranchManager();

        // Converting all instruction in hex
        ArrayList<String> binaryInstructions = new ArrayList<>();
        int instructionNumber = 1;
        for (String instructionString : instructions){
            instruction = new Instruction(instructionString, instructionNumber, branchManager);

            instructionLib.assignOpcode(instruction);
            if (instruction.toString() != null)
                binaryInstructions.add(instruction.toString());
            instructionNumber++;
        }

        String output = "v2.0 raw\n" + branchManager.convertExistingBranchToBinary(binaryInstructions);
        WriteFile writeFile = new WriteFile("asm/" + filePath + ".bin");
        System.out.println(output);
        writeFile.writeFile(output);
        System.out.println("========== File saved in asm/" + filePath + ".bin ==========");
    }
}
