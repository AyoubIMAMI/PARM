import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Instruction {
    private final String providedString; // The original assembler line.
    private final int instructionNumber; // The line number where the instruction is.
    private final BranchManager branchManager;

    private final String instructionName; // Eg. in ADDS <Rd>, <Rn>, <Rm> : "ADDS" is an instruction.
    private ArrayList<String> variables = new ArrayList<>(); // Eg. in ADDS <Rd>, <Rn>, <Rm> : "Rd, Rn, Rm" are variables.

    private String opcode = "notFound"; // Eg. in ADDS <Rd>, <Rn>, <Rm> : "0001100" is the opcode.
    private int immSize = 0;
    private boolean variablesAreInRightOrder;
    private int ignoringParameterN = -1;

    Instruction(String instructionString, int instructionNumber, BranchManager branchManager){
        this.providedString = instructionString;
        this.instructionNumber = instructionNumber;
        this.branchManager = branchManager ;

        String[] instructionElements = instructionString.split(" ");
        instructionName = instructionElements[0];

        variables.addAll(Arrays.asList(instructionElements).subList(1, instructionElements.length));
        variables.removeIf(variable -> variable.equals(""));
    }

    void setIgnoringParameterN(int index){
        this.ignoringParameterN = index;
    }

    void setVariablesAreInRightOrder(boolean variablesAreInRightOrder){
        this.variablesAreInRightOrder = variablesAreInRightOrder;
    }

    void setImmSize(int immSize) {
        this.immSize = immSize;
    }

    boolean hasImm(){
        for (String variable : variables)
            if (variable.startsWith("#")) return true;
        return false;
    }

    void setOpcode(String opcode){
        this.opcode = opcode;
    }

    int getNbRegisters(){
        int count = 0;
        List<String> refNames =  Arrays.asList("r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7");

        for (String variable : variables)
            if (refNames.contains(variable)) count++;

        return count;
    }

    String getInstructionName(){
        return instructionName;
    }

    boolean hasSp(){
        for (String variable : variables)
            if (variable.equals("sp")) return true;

        return false;
    }

    int nbVariables(){
        return variables.size();
    }

    private String getRegisterBinary(String register) {
        return switch (register) {
            case "r0" -> "000";
            case "r1" -> "001";
            case "r2" -> "010";
            case "r3" -> "011";
            case "r4" -> "100";
            case "r5" -> "101";
            case "r6" -> "110";
            case "r7" -> "111";
            default -> register;
        };
    }

    private void convertExistingOffsetToBinary(){
        if (hasSp()){
            ArrayList<String> newVariables = new ArrayList<>();

            for(String variable : variables){
                StringBuilder newVariable = new StringBuilder(variable);

                if (variable.startsWith("#")) {
                    newVariable = new StringBuilder();

                    String decOffset = variable.replace("#", "");
                    int offset = Integer.parseInt(decOffset) / 4;
                    String binOffset = Integer.toBinaryString(offset);
                    newVariable.append("0".repeat(Math.max(0, immSize - binOffset.length()))).append(binOffset);

                }

                if (!newVariable.toString().equals("sp"))
                    newVariables.add(newVariable.toString());
            }
            variables = newVariables;
        }
    }

    private void convertExistingRegisterToBinary() {
        for (int i = 0; i < variables.size(); i++){
            String variable = variables.get(i);
            if (variable.startsWith("r"))
                variables.set(i, getRegisterBinary(variable));
        }
    }

    private void convertExistingImmToBinary(){
        for (int i = 0; i < variables.size(); i++){
            String variable = variables.get(i);

            if (variable.startsWith("#")) {
                String imm = variable.replace("#", "");
                int immValue = Integer.parseInt(imm);
                String immBin = Integer.toBinaryString(immValue);
                variable = "0".repeat(Math.max(0, immSize - immBin.length())) + immBin;
            }
            variables.set(i, variable);
        }
    }

    @Override
    public String toString() {
        // If it's a Branch or a Label
        if ((providedString.startsWith("b") || providedString.startsWith(".")) && !providedString.startsWith("bic")){
            return updatingBranchManager();
        }

        // If it's not a Branch or a Label
        // Converting all parameters to their binary value
        applyAllBinaryConversions();

        // Finding and applying the binary opcode at the beginning of the string
        StringBuilder processingOutput = new StringBuilder(opcode);

        // Adding binary parameter in the correct order
        if (!variablesAreInRightOrder){
            for(int i = variables.size() - 1; i >= 0; i--){
                if (i != ignoringParameterN - 1)
                    processingOutput.append(" ").append(variables.get(i));
            }
        } else {
            for(int i = 0; i < variables.size(); i++){
                if (i != ignoringParameterN - 1)
                    processingOutput.append(" ").append(variables.get(i));
            }
        }

        // Removing space from the binary value
        String output = String.valueOf(processingOutput).replace(" ", "");

        // Making sure the string is 16 digits long
        output += "0".repeat(Math.max(0, 16 - output.length()));

        // If the instruction is correctly formatted, returning the hex value
        if(output.replace(" ", "").matches("[0-1]+")){
            int decimal = Integer.parseInt(output.replace(" ", ""),2);
            return Integer.toString(decimal,16);
        }

        return output;
    }

    private void applyAllBinaryConversions(){
        convertExistingRegisterToBinary();
        convertExistingOffsetToBinary();
        convertExistingImmToBinary();
    }

    private String updatingBranchManager() {
        if (providedString.startsWith(".")){
            branchManager.newLabel(providedString.replaceAll("[.:]", ""), instructionNumber);
            return null;
        }
        return providedString;
    }
}
