public class BranchPattern {
    private final String symbol;
    private final String code;

    BranchPattern(String symbol, String code){
        this.symbol = symbol;
        this.code = code;
    }

    String getCode() {
        return code;
    }

    String getSymbol() {
        return symbol;
    }
}
