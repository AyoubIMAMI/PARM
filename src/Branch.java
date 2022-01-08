public class Branch {
    private String labelName;
    private int nCible;

    Branch(String labelName, int nSource) {
        this.labelName = labelName;
        this.nCible = nSource;
    }

    public String getLabelName() {
        return labelName;
    }

    public int getNCible() {
        return nCible;
    }

    @Override
    public String toString() {
        return "Label Name: " + labelName + ", nCible: " + nCible;
    }
}
