#########################
# Registres
#########################
dic_registres = {
    "R0":"000",
    "R1":"001",
    "R2":"010",
    "R3":"011",
    "R4":"100",
    "R5":"101",
    "R6":"110",
    "R7":"111"
}

def registerToBinary(instruction):
    """ 
    For a given line of instruction, remplace each register name with its binary equivalent.
    (eg. registerToBinary("ADD r1 r2 r3") will return "ADD 001 010 011"
    """
    newInstruction = ""
    instructionElements = instruction.split()

    for element in instructionElements:
        element = element.upper()
        if element in dic_registres.keys():
            newInstruction += dic_registres.get(element)
        else:
            newInstruction += element
        newInstruction += " "
    return newInstruction

print(registerToBinary("ADD r1 r2 r3"))