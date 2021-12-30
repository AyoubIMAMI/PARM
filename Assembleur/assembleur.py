#########################
# Registry Dictionary
#########################
registryDic = {
    "r0":"000",
    "r1":"001",
    "r2":"010",
    "r3":"011",
    "r4":"100",
    "r5":"101",
    "r6":"110",
    "r7":"111"
}

def getInstructionList(instructionString):
    return instructionString.replace(',', '').split()

##########
# SUB
##########
def subsToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with subs remplaced with its binary value.
        Return the same instruction (string) if the instruction isn't a subs instruction
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    if len(instructionList) != 4:
        return instructionString
    
    if instructionList[0] != "sub":
        return instructionString

    # sub (register)
    if instructionList[1] in registryKeyList and instructionList[2] in registryKeyList  and instructionList[3] in registryKeyList:
        rd = instructionList[1]
        rn = instructionList[2]
        rm = instructionList[3]
        return "0001101 " + rm + " " + rn + " " + rd
    
    # 9.1.4.2 SUB (SP minus immediate) : Subtract Immediate from SP (p. 402)
    if instructionList[1] == "sp" and instructionList[2] == "sp" and  instructionList[3].startswith('#'):
        imm7 = format(int(int(instructionList[3].replace('#', '')) / int(4)), 'b')
        
        if len(imm7) > 7:
            raise MemoryError("imm7 is too big (> 7 bits)")
        else:
            imm7Size = len(imm7)
            for i in range(0, 7 - imm7Size):
                imm7 = '0' + imm7
        print(imm7)
        return "101100001 " + imm7



##########
# ADD
##########
def addsToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with adds remplaced with its binary value.
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    if len(instructionList) != 4:
        return instructionString
    
    if instructionList[0] != "add":
        return instructionString

    # add (register)
    if instructionList[1] in registryKeyList and instructionList[2] in registryKeyList  and instructionList[3] in registryKeyList:
        rd = instructionList[1]
        rn = instructionList[2]
        rm = instructionList[3]
        return "0001100 " + rm + " " + rn + " " + rd

    # add (imm3)
    if instructionList[1] in registryKeyList and instructionList[2] in registryKeyList and instructionList[3].startswith('#'):
        rd = instructionList[1]
        rn = instructionList[2]
        imm3 = format(int(instructionList[3].replace('#', '')), 'b')

        if len(imm3) > 3:
            raise MemoryError("imm3 is too big (> 3 bits)")
        else:
            imm3Size = len(imm3)
            for i in range(0, 3 - imm3Size):
                imm3 = '0' + imm3

        return "0001110 " + imm3 + " " + rn + " " + rd

    return ""


def registryToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with registry remplaced with their binary values.
    """
    newInstruction = ""
    instructionList = getInstructionList(instructionString)
    
    for element in instructionList:
        if element in registryDic.keys():
            newInstruction += " " + registryDic[element]
        elif newInstruction == "":
            newInstruction = element
        else:
            newInstruction += " " + element
    return newInstruction

def instructionToHexadecimal(binaryInstruction):
    return format(int(binaryInstruction.replace(' ', ''), 2), 'x').upper()








####### Testing #######
instructions = [
    "add r1, r1, r2",
    "add r1, r1, #5",
    "sub r1, r1, r2",
    "sub sp, sp, #12"]


for instruction in instructions: 
    instruction = addsToBinary(instruction)
    instruction = subsToBinary(instruction)
    instruction = registryToBinary(instruction)
    instruction = instructionToHexadecimal(instruction)
    print(instruction)
