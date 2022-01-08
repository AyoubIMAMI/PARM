import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstructionPattern {
    private final String instructionName; // Eg. in ADDS <Rd>, <Rn>, <Rm> : "ADDS" is an instruction.
    private final ArrayList<String> variables = new ArrayList<>(); // Eg. in ADDS <Rd>, <Rn>, <Rm> : "Rd, Rn, Rm" are variables.
    private final boolean variablesAreInRightOrder;
    private final int ignoringParameterN;

    private final String opcode; // Eg. in ADDS <Rd>, <Rn>, <Rm> : "0001100" is the opcode.

    InstructionPattern(String instructionName,  List<String> variables, String opcode){
        this.instructionName = instructionName;
        this.variables.addAll(variables);
        this.opcode = opcode;
        this.variablesAreInRightOrder = false;
        this.ignoringParameterN = -1;
    }

    InstructionPattern(String instructionName,  List<String> variables, String opcode, boolean variablesAreInRightOrder){
        this.instructionName = instructionName;
        this.variables.addAll(variables);
        this.opcode = opcode;
        this.variablesAreInRightOrder = variablesAreInRightOrder;
        this.ignoringParameterN = -1;
    }

    InstructionPattern(String instructionName,  List<String> variables, String opcode, int ignoringParameterN){
        this.instructionName = instructionName;
        this.variables.addAll(variables);
        this.opcode = opcode;
        this.variablesAreInRightOrder = false;
        this.ignoringParameterN = ignoringParameterN;
    }

    boolean hasSp(){
        for (String variable : variables)
            if (variable.equals("sp")) return true;

        return false;
    }

    boolean getVariablesAreInRightOrder(){
        return variablesAreInRightOrder;
    }

    String getInstructionName(){
        return instructionName;
    }

    String getOpcode(){
        return opcode;
    }

    int getNbRegisters(){
        int count = 0;

        for (String variable : variables)
            if (variable.startsWith("r")) count++;

        return count;
    }

    boolean hasImm(){
        for (String variable : variables)
            if (variable.startsWith("#")) return true;
        return false;
    }

    int getImmSize(){
        for (String variable : variables)
            if (variable.startsWith("#imm")) return Integer.parseInt(variable.replace("#imm", ""));
        return 0;
    }

    int getIgnoringParameterN(){
        return ignoringParameterN;
    }

    int nbVariables() {
        return variables.size();
    }
}
