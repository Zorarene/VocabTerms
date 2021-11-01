import java.util.*;
import java.io.*;


public class VocabTermsDriver {


    private static Object Integer;

    public static void main(String[] args) {
        // Official map with the organised data. The chapter number is the key for the outer map, the term is the
        // key for nested map and the definition act as the value.
        HashMap<Integer, HashMap<String, String>> organised_definitions = readFileToMap();
        
    }


    private static HashMap<Integer,HashMap<String,String>> readFileToMap() {
        // Chapter #, Term, definition of term.
        HashMap<Integer,HashMap<String,String>> organised_definitions = new HashMap<>();
        try {
            Scanner definitions = new Scanner(new File("pythonGlossary.csv"));
            // Skipping over headers.
            String headers = definitions.nextLine();

            // Looping through the file
            while(definitions.hasNext()){
                // Removing ',' between the chapter #, term and definition.
                String[] entry = definitions.nextLine().split(",");

                VocabTerm word = new VocabTerm(entry);

                // Entering it into the map in the right places.
                organised_definitions.put(word.getChapter(),organised_definitions.getOrDefault(word.getChapter(),new HashMap<>()));
                organised_definitions.get(word.getChapter()).put(word.getTerm(), word.getDefinition() );


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return organised_definitions;
    }

    public static String makeQuestion(HashMap<Integer,HashMap<String,String>>VocabTerms){
        //pick a random chapter and get the terms and def
        Random rand = new Random(14);
        int termChap = rand.nextInt() +1;
        HashMap<String,String> TermandDef = VocabTerms.get(termChap);
        //getting a random term
        Object[] Terms  = TermandDef.keySet().toArray();
        Object key = Terms[rand.nextInt(Terms.length)];
        String output = key +" "+TermandDef.get(key);
        return output;



        //System.out.println("Which is the best definition for ");
    }
}

