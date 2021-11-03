import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;

import java.util.*;
import java.io.*;


public class VocabTermsDriver {




    public static void main(String[] args) {
        // Official map with the organised data. The chapter number is the key for the outer map, the term is the
        // key for nested map and the definition act as the value.
        HashMap<Integer, HashMap<String, String>> organised_definitions = readFileToMap();
        // System.out.println("amount of chapters read from file" + organised_definitions.size());
        int chap = getChap(organised_definitions);
        String question = makeQuestion(organised_definitions,chap);
        HashMap<String,String> chapMap = chapMap(organised_definitions,chap);
        System.out.println("Which is the best definition for "+ question + "?");
        System.out.println("Chapter: "+ chap);
        AnswersToQuestion answers = makeAnswers(organised_definitions,question,chap);
        System.out.println(answers);
        int answerVal = getAnswer(chapMap,answers,question);
        System.out.println("Ans Val: "+ answerVal);
      // System.out.println("size of answer's id: " +answers.size());

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

    public static int getChap(HashMap<Integer, HashMap<String, String>> organised_definitions){
        Random rand = new Random();
        int termChap = rand.nextInt(organised_definitions.size()-1) ;
        return termChap;
    }

    public static String makeQuestion(HashMap<Integer, HashMap<String, String>> organised_definitions,int chap) {
        String question = "";

        //getting a random term
        HashMap<String, String> ChapMap = (organised_definitions.get(chap));
        //put all terms in a set
        Set<String> termList = ChapMap.keySet();
        //convert to a list
        String[] termArray = termList.toArray(new String[0]);
        //random select a term
        Random termInd = new Random(termList.size());
        int questInd = termInd.nextInt(termList.size());
        String key = termArray[questInd];
        question += key;
        return question;
    }

    public static HashMap<String,String> chapMap(HashMap<Integer, HashMap<String, String>> organised_definitions, int chap){
        HashMap<String, String> ChapMap = (organised_definitions.get(chap));
        return ChapMap;

    }

    public static AnswersToQuestion makeAnswers(HashMap<Integer, HashMap<String, String>> organised_definitions,String key,int chap) {
        AnswersToQuestion options = new AnswersToQuestion();
        //System.out.println("key "+ key);
        Random rand = new Random();

        //get just terms and def
        HashMap<String, String> ChapMap = (organised_definitions.get(chap));
        //System.out.println(ChapMap);
        //add correct answer to AnswerOptions
        String output = ChapMap.get(key);
        //System.out.println(output);
        options.add(output);

        options.addCorrect(ChapMap.get(key));

        //put all terms in a set
        Set<String> termList = ChapMap.keySet();
        //convert to a list
        String[] termArray = termList.toArray(new String[0]);
        //random select a term
        Random termInd = new Random(termList.size());
        //get another random term, but use an option choices

        //want to do that 3 times

        while(options.size() !=4) {
                 int answerInd = termInd.nextInt(termList.size());
                String defKey = termArray[answerInd];
                String value = ChapMap.get(defKey);
                options.add(value);
    }
        return options;

    }

    public static int  getAnswer(HashMap<String,String>chapMap,AnswersToQuestion answers,String term){

       // get the correct definition from the chapmap
       String ansDef = chapMap.get(term);
       System.out.println(ansDef);
        //put in a list so it can be indexed
       List<AnswersToQuestion> answerOptions = new ArrayList<>();
        answerOptions.add(answers);
       // System.out.println(answerOptions);
        //see what index the def is in the List
        int ansVal = answerOptions.indexOf(ansDef) +1;




       return ansVal;
    }







}

