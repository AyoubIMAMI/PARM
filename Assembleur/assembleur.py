import os

#########################
# File reading
#########################
def getFileInstruction(path):
    instructions = []
    file = open(path, "r")

    for line in file:
        line = line.replace('\n', '')
        if line != "":
            instructions.append(line)
    
    file.close()
    return instructions

#########################
# File writing
#########################
def writeBin(path, bin):
    if(os.path.isfile('path')):
        file = open(path, "x")
    else:
        file = open(path, "w")
    
    file.write("v2.0 raw\n")
    file.close()
    file = open(path, "a")
    file.write(bin)
    file.close()


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

############################################################
# SUB
############################################################
def subsToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with subs remplaced with its binary value.
        Return the same instruction (string) if the instruction isn't a subs instruction
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    if len(instructionList) < 3:
        return instructionString
    
    if not "sub" in instructionList[0]:
        return instructionString

    # 9.1.4.2 SUB (SP minus immediate) : Subtract Immediate from SP (p. 402)
    # Checking for:  SP, #<offset>
    if instructionList[1] == "sp" and  instructionList[2].startswith('#'):
        imm7 = format(int(int(instructionList[2].replace('#', '')) / int(4)), 'b')
        
        if len(imm7) > 7:
            raise MemoryError("imm7 is too big (> 7 bits)")
        else:
            imm7Size = len(imm7)
            for i in range(0, 7 - imm7Size):
                imm7 = '0' + imm7
        return "101100001 " + imm7 

    if len(instructionList) != 4:
        return instructionString 

    # sub (register)
    if instructionList[1] in registryKeyList and instructionList[2] in registryKeyList  and instructionList[3] in registryKeyList:
        rd = instructionList[1]
        rn = instructionList[2]
        rm = instructionList[3]
        return "0001101 " + rm + " " + rn + " " + rd
    
    # 9.1.4.2 SUB (SP minus immediate) : Subtract Immediate from SP (p. 402)
    # Checking for SP, SP, #<offset>
    if instructionList[1] == "sp" and instructionList[2] == "sp" and  instructionList[3].startswith('#'):
        imm7 = format(int(int(instructionList[3].replace('#', '')) / int(4)), 'b')
        
        if len(imm7) > 7:
            raise MemoryError("imm7 is too big (> 7 bits)")
        else:
            imm7Size = len(imm7)
            for i in range(0, 7 - imm7Size):
                imm7 = '0' + imm7
        return "101100001 " + imm7
    
    return instructionString


############################################################
# ADD
############################################################
def addsToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with adds remplaced with its binary value.
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    if len(instructionList) < 3:
        return instructionString
    
    if not "add" in instructionList[0]:
        return instructionString

    # 9.1.4.1 ADD (SP plus immediate) : Add Immediate to SP (p. 194)
    # Checking for:  SP, #<offset>
    if instructionList[1] == "sp" and  instructionList[2].startswith('#'):
        imm7 = format(int(int(instructionList[2].replace('#', '')) / int(4)), 'b')
        
        if len(imm7) > 7:
            raise MemoryError("imm7 is too big (> 7 bits)")
        else:
            imm7Size = len(imm7)
            for i in range(0, 7 - imm7Size):
                imm7 = '0' + imm7
        return "101100000 " + imm7 

    if len(instructionList) != 4:
        return instructionString
    
    if not "add" in instructionList[0]:
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

    # 9.1.4.2 9.1.4.1 ADD (SP plus immediate) : Add Immediate to SP (p. 194)
    # Checking for SP, SP, #<offset>
    if instructionList[1] == "sp" and instructionList[2] == "sp" and  instructionList[3].startswith('#'):
        imm7 = format(int(int(instructionList[3].replace('#', '')) / int(4)), 'b')
        
        if len(imm7) > 7:
            raise MemoryError("imm7 is too big (> 7 bits)")
        else:
            imm7Size = len(imm7)
            for i in range(0, 7 - imm7Size):
                imm7 = '0' + imm7
        return "101100000 " + imm7
    
    return instructionString

    return instructionString

############################################################
# MOV
############################################################
def movToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with mov remplaced with its binary value.
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    if len(instructionList) != 3:
        return instructionString
    
    if not "mov" in instructionList[0]:
        return instructionString
    
    if instructionList[1] in registryKeyList and instructionList[2].startswith('#'):
        rd = instructionList[1]
        imm8 = format(int(instructionList[2].replace('#', '')), 'b')
        imm8Size = len(imm8)
        
        if imm8Size > 8:
            raise MemoryError("imm8 is too big (> 8 bits)")
        else:
            for i in range(0, 8 - imm8Size):
                imm8 = '0' + imm8

        return "00100 " + rd + " " + imm8
    
    return instructionString

############################################################
# STR
############################################################
def strToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with str remplaced with its binary value.
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    # 9.1.3.1 STR (immediate) : Store Register (p. 386)
    if len(instructionList) < 3:
        return instructionString
    
    if instructionList[0] != "str":
        return instructionString
    
    # str reg, [sp] 
    if instructionList[1] in registryKeyList and instructionList[2] == "[sp]":
        rt = instructionList[1]
        return "10010 " + rt + " 00000000"

    # str reg, [sp, #offset] 
    if len(instructionList) != 4:
        return instructionString

    if instructionList[1] in registryKeyList and instructionList[2] == "[sp" and instructionList[3].startswith("#") and instructionList[3].endswith("]"):
        
        rt = instructionList[1]
        imm8 = format(int(int(instructionList[3].replace('#', '').replace(']', '')) / int(4)), 'b')
        imm8Size = len(imm8)
        
        if imm8Size > 8:
            raise MemoryError("imm8 is too big (> 8 bits)")
        else:
            for i in range(0, 8 - imm8Size):
                imm8 = '0' + imm8

        return "10010 " + rt + " " + imm8
    
    return instructionString


############################################################
# LDR
############################################################
def ldrToBinary(instructionString):
    """
        For a given instruction (string) will return the same instruction with ldr remplaced with its binary value.
    """
    instructionList = getInstructionList(instructionString)
    registryKeyList = registryDic.keys()

    # 9.1.3.2 LDR (immediate) : Load Register (p. 246)
    if len(instructionList) != 4:
        return instructionString
    
    if instructionList[0] != "ldr":
        return instructionString
    

    if instructionList[1] in registryKeyList and instructionList[2] == "[sp" and instructionList[3].startswith("#") and instructionList[3].endswith("]"):
        
        rt = instructionList[1]
        imm8 = format(int(int(instructionList[3].replace('#', '').replace(']', '')) / int(4)), 'b')
        imm8Size = len(imm8)
        
        if imm8Size > 8:
            raise MemoryError("imm8 is too big (> 8 bits)")
        else:
            for i in range(0, 8 - imm8Size):
                imm8 = '0' + imm8

        return "10011 " + rt + " " + imm8
    
    return instructionString

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
    return format(int(binaryInstruction.replace(' ', ''), 2), 'x')



####### Processing #######
path = input("Enter the Assembler Source file (.s): ")
binPath = path.replace(".s", ".bin")
assembler = getFileInstruction(path)
bin = ""

for instruction in assembler: 
    instruction = addsToBinary(instruction)
    instruction = subsToBinary(instruction)
    instruction = movToBinary(instruction)
    instruction = strToBinary(instruction)
    instruction = ldrToBinary(instruction)

    instruction = registryToBinary(instruction)
    instruction = instructionToHexadecimal(instruction)
    bin += instruction + " "

print(bin)
writeBin(binPath, bin)