public class ConditionalBranch {
    private final String symbol;
    private final String labelName;
    private String code = "notFound";
    private final int nSource;
    private final BranchManager branchManager;

    ConditionalBranch(String providedString, int nSource, BranchManager branchManager) {
        symbol = providedString.substring(1, 3);
        labelName = providedString.split(" ")[1].replaceAll("[.:,]", "");
        this.nSource = nSource;
        this.branchManager = branchManager;
    }

    String getSymbol() {
        return symbol;
    }

    void setCode(String code){
        this.code = code;
    }

    int getLabelNCible(String labelName){
        Branch label = branchManager.findLabelWithName(labelName);
        return label.getNCible();
    }

    @Override
    public String toString() {
        return "1101" + code + branchManager.valeurSigneeEnComplementA2(getLabelNCible(labelName) - nSource - 3, 8);
    }
}
