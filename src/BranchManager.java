import java.util.ArrayList;

public class BranchManager {
    ArrayList<Branch> branches = new ArrayList<>();

    void newLabel(String labelName, int nSource){
        Branch newBranch = new Branch(labelName, nSource - branches.size());
        branches.add(newBranch);
    }

    String convertExistingBranchToBinary(ArrayList<String> binaryInstructions){
        StringBuilder output = new StringBuilder();
        BranchLibrary branchLibrary = new BranchLibrary();
        int nSource = 1;
        for (String instruction : binaryInstructions) {
            StringBuilder newInstruction = new StringBuilder();
            // Unconditional Branch
            if (instruction.startsWith("b .")){
                Branch label = findLabelWithName(instruction.substring(1).replaceAll("[ .:]", ""));

                assert label != null;
                newInstruction.append("11100").append(valeurSigneeEnComplementA2(label.getNCible() - nSource - 3, 11));
            }

            // Conditional Branch
            else if (instruction.startsWith("b") && instruction.split(" ").length == 2) {
                ConditionalBranch branch = new ConditionalBranch(instruction);
                branchLibrary.assignCode(branch);
                newInstruction.append(branch);
            }

            // Not a Branch
            else {
                newInstruction.append(instruction);
            }

            if(newInstruction.toString().matches("[0-1]+")){
                int decimal = Integer.parseInt(newInstruction.toString(),2);
                newInstruction = new StringBuilder(Integer.toString(decimal, 16));
            }

            output.append(newInstruction).append(" ");

            nSource++;
        }
        return output.toString();
    }

    private Branch findLabelWithName(String labelName) {
        for (Branch branch : branches){
            if (branch.getLabelName().equals(labelName))
                return branch;
        }
        return null;
    }

    private String valeurSigneeEnComplementA2(int n, int size) {
        if (n > 0){
            String binary = Integer.toBinaryString(n);
            String fullBinary = "0".repeat(Math.max(0, size - binary.length())) + binary.toString();
            System.out.println(fullBinary);
            return fullBinary;
        }

        String binary = Integer.toBinaryString(-n);
        String fullBinary = "0".repeat(Math.max(0, size - binary.length())) + binary.toString();

        String complementA1 = fullBinary.replace("0", "_").replace("1", "0").replace("_", "1");
        int complementA2Decimal = Integer.parseInt(complementA1, 2) + 1;

        String complementA2binary = Integer.toBinaryString(complementA2Decimal);
        String fullComplementA2binary = "0".repeat(Math.max(0, size - complementA2binary.length())) + complementA2binary;
        if (fullComplementA2binary.length() > size)
            return fullComplementA2binary.substring(1);
        else
            return fullComplementA2binary;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Branch branch : branches)
            output.append(branch.toString()).append("\n");
        return output.toString();
    }
}
