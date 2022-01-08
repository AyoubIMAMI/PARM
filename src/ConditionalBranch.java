public class ConditionalBranch {
    private final String symbol;
    private final String labelName;
    private String code = "notFound";

    ConditionalBranch(String providedString) {
        symbol = providedString.substring(1, 3);
        labelName = providedString.split(" ")[1].replaceAll("[.:,]", "");
    }

    String getSymbol() {
        return symbol;
    }

    void setCode(String code){
        this.code = code;
    }

    @Override
    public String toString() {
        return code + labelName;
    }
}
