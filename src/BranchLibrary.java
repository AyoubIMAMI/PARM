import java.util.ArrayList;
import java.util.stream.Collectors;

public class BranchLibrary {
    private final ArrayList<BranchPattern> conditionalPatterns = new ArrayList<>();

    BranchLibrary(){
        final BranchPattern eqPattern = new BranchPattern("EQ", "0000"); conditionalPatterns.add(eqPattern);
        final BranchPattern nePattern = new BranchPattern("NE", "0001"); conditionalPatterns.add(nePattern);
        final BranchPattern csPattern = new BranchPattern("CS", "0010"); conditionalPatterns.add(csPattern);
        final BranchPattern hsPattern = new BranchPattern("HS", "0010"); conditionalPatterns.add(hsPattern);
        final BranchPattern ccPattern = new BranchPattern("CC", "0011"); conditionalPatterns.add(ccPattern);
        final BranchPattern loPattern = new BranchPattern("LO", "0011"); conditionalPatterns.add(loPattern);
        final BranchPattern miPattern = new BranchPattern("MI", "0100"); conditionalPatterns.add(miPattern);
        final BranchPattern plPattern = new BranchPattern("PL", "0101"); conditionalPatterns.add(plPattern);
        final BranchPattern vsPattern = new BranchPattern("VS", "0110"); conditionalPatterns.add(vsPattern);
        final BranchPattern vcPattern = new BranchPattern("VC", "0111"); conditionalPatterns.add(vcPattern);
        final BranchPattern hiPattern = new BranchPattern("HI", "1000"); conditionalPatterns.add(hiPattern);
        final BranchPattern lsPattern = new BranchPattern("LS", "1001"); conditionalPatterns.add(lsPattern);
        final BranchPattern gePattern = new BranchPattern("GE", "1010"); conditionalPatterns.add(gePattern);
        final BranchPattern ltPattern = new BranchPattern("LT", "1011"); conditionalPatterns.add(ltPattern);
        final BranchPattern gtPattern = new BranchPattern("GT", "1100"); conditionalPatterns.add(gtPattern);
        final BranchPattern lePattern = new BranchPattern("LE", "1101"); conditionalPatterns.add(lePattern);
        final BranchPattern alPattern = new BranchPattern("AL", "1110"); conditionalPatterns.add(alPattern);
    }

    void assignCode(ConditionalBranch branch){
        ArrayList<BranchPattern> possiblePatterns = conditionalPatterns.stream()
                .filter(branchPattern -> branchPattern.getCode().equals(branch.getSymbol()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (possiblePatterns.isEmpty())
            branch.setCode(branch.getSymbol());
        else {
            branch.setCode(possiblePatterns.get(0).getCode());
        }
    }
}
