import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InstructionLibrary {
    private final ArrayList<InstructionPattern> patterns = new ArrayList<>();

    InstructionLibrary(){
        // Register
        final InstructionPattern addRegister = new InstructionPattern("add",  Arrays.asList("rd", "rn", "rm"),  "0001100"); patterns.add(addRegister);
        final InstructionPattern subRegister = new InstructionPattern("sub",  Arrays.asList("rd", "rn", "rm"), "0001101"); patterns.add(subRegister);
        final InstructionPattern andRegister = new InstructionPattern("and",  Arrays.asList("rdn", "rm"), "0100000000"); patterns.add(andRegister);
        final InstructionPattern eorRegister = new InstructionPattern("eor",  Arrays.asList("rdn", "rm"), "0100000001"); patterns.add(eorRegister);
        final InstructionPattern lslRegister = new InstructionPattern("lsl",  Arrays.asList("rdn", "rm"), "0100000010"); patterns.add(lslRegister);
        final InstructionPattern lsrRegister = new InstructionPattern("lsr",  Arrays.asList("rdn", "rm"), "0100000010"); patterns.add(lsrRegister);
        final InstructionPattern asrRegister = new InstructionPattern("asr",  Arrays.asList("rdn", "rm"), "0100000100"); patterns.add(asrRegister);
        final InstructionPattern adcRegister = new InstructionPattern("adc",  Arrays.asList("rdn", "rm"), "0100000101"); patterns.add(adcRegister);
        final InstructionPattern sbcRegister = new InstructionPattern("sbc",  Arrays.asList("rdn", "rm"), "0100000110"); patterns.add(sbcRegister);
        final InstructionPattern rorRegister = new InstructionPattern("ror",  Arrays.asList("rdn", "rm"), "0100000111"); patterns.add(rorRegister);
        final InstructionPattern tstRegister = new InstructionPattern("tst",  Arrays.asList("rn", "rm"), "0100001000"); patterns.add(tstRegister);
        final InstructionPattern cmpRegister = new InstructionPattern("cmp",  Arrays.asList("rn", "rm"), "0100001010"); patterns.add(cmpRegister);
        final InstructionPattern cmnRegister = new InstructionPattern("cmn",  Arrays.asList("rn", "rm"), "0100001011"); patterns.add(cmnRegister);
        final InstructionPattern orrRegister = new InstructionPattern("orr",  Arrays.asList("rdn", "rm"), "0100001100"); patterns.add(orrRegister);
        final InstructionPattern bicRegister = new InstructionPattern("bic",  Arrays.asList("rdn", "rm"), "0100001110"); patterns.add(bicRegister);
        final InstructionPattern mvnRegister = new InstructionPattern("mvn",  Arrays.asList("rd", "rm"), "0100001111"); patterns.add(mvnRegister);
        final InstructionPattern mulRegister = new InstructionPattern("mul",  Arrays.asList("rdm", "rn"), "0100001101"); patterns.add(mulRegister);

        // Immediate
        final InstructionPattern lslImmediate = new InstructionPattern("lsl",  Arrays.asList("rd", "rm", "#imm5"), "00000"); patterns.add(lslImmediate);
        final InstructionPattern lsrImmediate = new InstructionPattern("lsr",  Arrays.asList("rd", "rm", "#imm5"), "00001"); patterns.add(lsrImmediate);
        final InstructionPattern asrImmediate = new InstructionPattern("asr",  Arrays.asList("rd", "rm", "#imm5"), "00010"); patterns.add(asrImmediate);
        final InstructionPattern addImmediate = new InstructionPattern("add",  Arrays.asList("rd", "rn", "#imm3"), "0001110"); patterns.add(addImmediate);
        final InstructionPattern addImmediate2 = new InstructionPattern("add",  Arrays.asList("rdn", "#imm8"), "00110"); patterns.add(addImmediate2);
        final InstructionPattern subImmediate = new InstructionPattern("sub",  Arrays.asList("rd", "rn", "#imm3"), "0001111"); patterns.add(subImmediate);
        final InstructionPattern subImmediate2 = new InstructionPattern("sub",  Arrays.asList("rdn", "#imm8"), "00111"); patterns.add(subImmediate2);
        final InstructionPattern movImmediate = new InstructionPattern("mov",  Arrays.asList("rd", "#imm8"), "00100", true); patterns.add(movImmediate);
        final InstructionPattern cmpImmediate = new InstructionPattern("cmp",  Arrays.asList("rd", "#imm8"), "00101"); patterns.add(cmpImmediate);
        final InstructionPattern rsbImmediate = new InstructionPattern("rsb",  Arrays.asList("rn", "rd"), "0100001001"); patterns.add(rsbImmediate);
        final InstructionPattern rsbImmediate2 = new InstructionPattern("rsb",  Arrays.asList("rd", "rn", "#0"), "0100001001", 3); patterns.add(rsbImmediate2);

        // SP plus immediate
        final InstructionPattern addSpImmediate = new InstructionPattern("add", Arrays.asList("sp", "#imm7"), "101100000"); patterns.add(addSpImmediate);
        final InstructionPattern subSpImmediate = new InstructionPattern("sub", Arrays.asList("sp", "#imm7"), "101100001"); patterns.add(subSpImmediate);
        final InstructionPattern strImmediate = new InstructionPattern("str",  Arrays.asList("rt", "sp", "#imm8"), "10010", true); patterns.add(strImmediate);
        final InstructionPattern strImmediate2 = new InstructionPattern("str",  Arrays.asList("rt", "sp"), "10010", true); patterns.add(strImmediate2);
        final InstructionPattern ldrImmediate = new InstructionPattern("ldr",  Arrays.asList("rt", "sp", "#imm8"), "10011", true); patterns.add(ldrImmediate);
        final InstructionPattern ldrImmediate2 = new InstructionPattern("ldr",  Arrays.asList("rt", "sp"), "10011", true); patterns.add(ldrImmediate2);
    }

    void assignOpcode(Instruction instruction){
        ArrayList<InstructionPattern> possiblePatterns = patterns.stream()
                .filter(instructionPattern -> instruction.getInstructionName().contains(instructionPattern.getInstructionName()))
                .filter(instructionPattern -> instructionPattern.getNbRegisters() == instruction.getNbRegisters())
                .filter(instructionPattern -> instructionPattern.hasImm() == instruction.hasImm())
                .filter(instructionPattern -> instructionPattern.hasSp() == instruction.hasSp())
                .filter(instructionPattern -> instructionPattern.nbVariables() == instruction.nbVariables())
                .collect(Collectors.toCollection(ArrayList::new));

        if (possiblePatterns.isEmpty())
            instruction.setOpcode(instruction.getInstructionName());
        else {
            instruction.setOpcode(possiblePatterns.get(0).getOpcode());
            instruction.setImmSize(possiblePatterns.get(0).getImmSize());
            instruction.setVariablesAreInRightOrder(possiblePatterns.get(0).getVariablesAreInRightOrder());
            instruction.setIgnoringParameterN(possiblePatterns.get(0).getIgnoringParameterN());
        }
    }
}
