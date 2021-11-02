import java.util.*;
import java.io.*;


public class VocabTermsDriver {


    public static void main(String[] args) {
        // Official map with the organised data. The chapter number is the key for the outer map, the term is the
        // key for nested map and the definition act as the value.
        HashMap<Integer, HashMap<String, String>> organised_definitions = readFileToMap();
        // System.out.println("amount of chapters read from file" + organised_definitions.size());

        String question = makeQuestion(organised_definitions);
        System.out.println("Which is the best definition for "+ question);
        List<String> answers = makeAnswers(organised_definitions,question);
        System.out.println(answers);

    }


    private static HashMap<Integer, HashMap<String, String>> readFileToMap() {
        // Chapter #, Term, definition of term.
        HashMap<Integer, HashMap<String, String>> organised_definitions = new HashMap<>();
        try {
            Scanner definitions = new Scanner(new File("pythonGlossary.csv"));
            // Skipping over headers.
            String headers = definitions.nextLine();

            // Looping through the file
            while (definitions.hasNext()) {
                // Removing ',' between the chapter #, term and definition.
                String[] entry = definitions.nextLine().split(",");

                VocabTerm word = new VocabTerm(entry);

                // Entering it into the map in the right places.
                organised_definitions.put(word.getChapter(), organised_definitions.getOrDefault(word.getChapter(), new HashMap<>()));
                organised_definitions.get(word.getChapter()).put(word.getTerm(), word.getDefinition());


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return organised_definitions;
    }

    public static String makeQuestion(HashMap<Integer, HashMap<String, String>> organised_definitions) {
        String question = "";
        //pick a random chapter and get the terms and def
        Random rand = new Random();
        int termChap = rand.nextInt(organised_definitions.size()) + 1;

        //getting a random term
        HashMap<String, String> ChapMap = (organised_definitions.get(termChap));
        //put all terms in a set
        Set<String> termList = ChapMap.keySet();
        //convert to a list
        String[] termArray = termList.toArray(new String[0]);
        //random select a term
        Random termInd = new Random(termList.size());
        int questInd = termInd.nextInt(termList.size() + 1);
        String key = termArray[questInd];
        question += key;
        return question;
    }

    public static List<String> makeAnswers(HashMap<Integer, HashMap<String, String>> organised_definitions,String key) {
        List<String> AnswerOption = new ArrayList<>();
        Random rand = new Random();
        int termChap = rand.nextInt(organised_definitions.size()) + 1;

        //how do I pass in variables used in make questions

        //get just terms and def
        HashMap<String, String> ChapMap = (organised_definitions.get(termChap));
        //put all terms in a set
        Set<String> termList = ChapMap.keySet();
        //convert to a list
        String[] termArray = termList.toArray(new String[0]);
        //random select a term
        Random termInd = new Random(termList.size());
        //index of the random term
        int questInd = termInd.nextInt(termList.size() + 1);
        //get another random term, but use an option choices
        int answerInd = termInd.nextInt(termList.size() + 1);
        //want to do that 3 times

        for (int i = 0; i < 2; i++) {

        String defKey = termArray[answerInd];
        String value = ChapMap.get(defKey);
        AnswerOption.add(value);
        }
//issue im having with it consistently printing 4 answer options
//







        return AnswerOption;
    }







}

