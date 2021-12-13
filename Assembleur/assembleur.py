
"dictionnaire d'instruction connu"
dic_assembleur={
    "R0":"000",
    "R1":"001",
    "R2":"010",
    "R3":"011",
    "R4":"100",
    "R5":"101",
    "R6":"110",
    "R7":"111",
    "ADD":"000000",
    "SUB":"000010",
    "AND":"000100",
    "OR":"000110",
    "XOR":"001000",
    "SL":"001010",
    "SR":"001100",
    "MULT":"001110",
    "LD":"010000",
    "STR":"010010",
    "JMP":"110000",
    "JEQU":"110010",
    "JNEQU":"110100",
    "CALL":"110110",
    "RET":"111000",
    "ADDi":"000001",
    "SUBi":"000011",
    "ANDi":"000101",
    "ORi":"000111",
    "XORi":"001001",
    "SLi":"001011",
    "SRi":"001101",
    "MULTi":"001111"
}

### fonctions terminées ###

"""fonction qui convertit un entier sur 16 bits"""
def convert(const):
    const  = int(const)
    if(const > 65535):
        res = "0"*16
    else:
        res = format(const,"016b")
    return str(res)

#print(type(convert("10")))

"""fonction qui traduit l'instruction usuelle"""
def trad(mot):
    return dic_assembleur[mot]


"""inverse la chaine passée en paramètre"""
def reverter(bitword):
    return bitword[::-1]
""""""

"""fonction qui avec un fichier en paramétre 
lit la premiére ligne et la traduit en binaire"""
def lecture_ligne(nom_file, num_li): 
    fichier_courant = open(nom_file,"r",encoding="UTF-8")
    ligne = fichier_courant.readline()
    tab_instruction = lecture_mot_ligne(ligne)
    res=""
    im = False
    print(tab_instruction)
    for i in range(0,len(tab_instruction)):
        if(tab_instruction[i] in dic_assembleur):
            res= res +dic_assembleur[tab_instruction[i]]     
        else: 
            res = res +convert(tab_instruction[i]) 
    fichier_courant.close()
    
    return res

"""fonction qui récupére les mot par ligne"""
def lecture_mot_ligne(ligne):
    res = ligne.split()
    return res

"""locate permet d'ajouter les labels en leur attribuant leur numéro de ligne sur 16 bits
De plus on ajoute le label avec : et sans pour le reconnaitre a son initialistion et son appel """
def locate():
    fichier_lu = open("entree.txt","r",encoding="UTF-8")
    lignes = fichier_lu.readlines()
    nb_lignes = len(lignes)
    fichier_lu.close()
    for i in range(nb_lignes):
        wrd_line=lecture_mot_ligne(lignes[i])
        nb_wrd_line = len(wrd_line)
        for j in range(nb_wrd_line):
            if(":" in wrd_line[j]):
                dic_assembleur[wrd_line[j]]=str(convert(i))
                dic_assembleur[wrd_line[j][:-1]] = str(convert(i))     
    return dic_assembleur

###########################


### fonction à finir ###


def rep_label(mot): 
    if(mot in dic_assembleur):
        return True
    
print(rep_label("000"))

"""doit pouvoir lire un fichier en entier"""
def lecture_fichier(): 
    fichier_lu = open("entree.txt","r",encoding="UTF-8")
    #on lit toutes les lignes du fichier et on récupére 
    # leur nombres pour savoir combien de fois on doit appeler lectures lignes
    lignes = fichier_lu.readlines()
    fichier_lu.close()
    nb_lignes = len(lignes)
    fichier_ecr = open("sortie.txt","w")
    dic_assembleur = locate()
    for i in range(nb_lignes):
        #print(lignes[i])
        wrd_line=lecture_mot_ligne(lignes[i])
        nb_wrd_line = len(wrd_line)
       
        temp=""
        print(i)
        for j in range(0,nb_wrd_line):
            if(wrd_line[j] in dic_assembleur):
                temp= temp +dic_assembleur[wrd_line[j]]     
            else: 
                temp = temp +convert(wrd_line[j]) 
        #Donnez l'hexadecimal et le reverse pour implémenter dans la RAM 
        temp = str(hex(int(temp)))
        fichier_ecr.write(temp)
        fichier_ecr.write("\n")
    fichier_ecr.close()

    return nb_lignes

"enlever le dernier caractére"
def test(mot):
    return print(mot[:-1])

def inn(mot):
    return print(':' in mot)



lecture_fichier()
"""
aka = []
lecture_fichier()
aka.append(test("entree.txt")[0])
temp = lecture_mot_ligne(aka[0])
print(temp)

print(test("entree.txt")[0])
print(aka)
"""


# test
#print(test("entree.txt"))
#print(":" in lecture_mot_ligne("W: SUBi R0 R1 R3")[0])
#print(lecture_ligne("entree.txt"))
#print(reverter(dic_assembleur[lecture_mot_ligne(lecture_ligne(test))[0]]))
