import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader {
    private final String path;

    InputReader(String path){
        this.path = path;
    }

    ArrayList<String> readFile(){
        ArrayList<String> instructions = new ArrayList<>();

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String instruction = myReader.nextLine()
                        .replaceAll("[,()\\[\\]]", " ");
                if (!instruction.startsWith("@"))
                    instructions.add(instruction);
            }
            myReader.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("File can not pe found!");
            e.printStackTrace();
        }

        return instructions;
    }
}