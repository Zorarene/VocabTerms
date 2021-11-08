import java.util.*;
import java.io.*;

public class VocabTermsDriver {
    public static void main(String[] args){
        // Official map with the organised data. The chapter number is the key for the outer map, the term is the
        // key for nested map and the definition act as the value.
        HashMap<Integer,HashMap<String,String>> organised_definitions = readFileToMap();

        int chapter_number = getChapterChoice();

        askFourQuestions(organised_definitions,chapter_number);
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

    public static int getChapterChoice(){
        int official_chapter_choice = 0;
        boolean again = true;
        do {
            Scanner inscan = new Scanner(System.in);
            System.out.println("Which chapter do you want to study from?");
            try {
                int user_chapter_choice = inscan.nextInt();
                if (user_chapter_choice > 15 || user_chapter_choice < 1){
                    System.out.println("Please enter a chapter number from 1 - 15.");
                }else{
                    official_chapter_choice = user_chapter_choice;
                    again = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a chapter number from 1 - 15.");
            }
        }while(again);

        return official_chapter_choice;
    }

    public static List<String> questionOutput(HashMap<Integer,HashMap<String,String>> organised_definitions, int chapter_num, List<Integer> questions_asked){
        List<String> answer_chapter = new ArrayList<>();
        String correct_answer = "";
        // This line goes into the specified chapter and puts the terms of the nested map of that chapter into a list.
        // chapter 1 => "tuple" = "definition", "structure" = "definition", "code" = "definition"
        //                                  goes to
        // nested_map_keys = {"tuple","structure","code"}
        List<String> nested_map_keys = new ArrayList<>(organised_definitions.get(chapter_num).keySet());

        int random_int;
        do {
            Random random = new Random();
            random_int = random.nextInt(nested_map_keys.size());
        }while(questions_asked.contains(random_int));

        String definition_asked = organised_definitions.get(chapter_num).get(nested_map_keys.get(random_int));
        System.out.printf("Which of the following mean '%s'\n",definition_asked );
        System.out.printf("Chapter: %d \n", chapter_num);


        for(Map.Entry<String,String> entry: organised_definitions.get(chapter_num).entrySet()){
            if (entry.getValue().equals(definition_asked)){
                correct_answer = entry.getKey();
            }
        }
        answer_chapter.add(correct_answer);
        answer_chapter.add(String.valueOf(random_int));

        return answer_chapter;

    }

    public static HashMap<Integer,String> outputAnswerChoices(List<String> correct_answer,int chapter_num ,HashMap<Integer,HashMap<String,String>> organised_definitions){
        List<String> nested_map_keys = new ArrayList<>(organised_definitions.get(chapter_num).keySet());
        HashMap<Integer,String> answer_choices = new HashMap<>();
        Set<String> choices = new HashSet<>();
        choices.add(correct_answer.get(0));
        Random rand = new Random();

        while(choices.size() != 4){
            int rand_ndx = rand.nextInt(nested_map_keys.size());
            choices.add(nested_map_keys.get(rand_ndx));
        }

        int num = 1;
        for(String ans : choices){
            answer_choices.put(num,ans);
            num++;
        }
        for(Map.Entry<Integer,String> pair : answer_choices.entrySet()){
            System.out.println(pair.getKey() +") "+ pair.getValue());
        }


        return answer_choices;


    }

    public static int gradeTheAnswer(HashMap<Integer,String> answer_choices, List<String> correct_answer){
        boolean again = true;
        do {
            Scanner keyb = new Scanner(System.in);
            System.out.println("Your Answer: ");
            try {
                int user_answer = keyb.nextInt();
                if (answer_choices.containsKey(user_answer)) {
                    if (answer_choices.get(user_answer).equals(correct_answer.get(0))) {
                        System.out.println("Correct");
                        return 1;
                    } else {
                        System.out.println("Wrong");
                        System.out.println("Correct answer is: "+correct_answer.get(0));
                    }
                    again = false;
                } else {
                    System.out.println("Please enter an available choice.");

                }
            }catch(InputMismatchException e){
                System.out.println("Numbers only please");
            }
        } while(again);

        return 0;
    }

    public static void askFourQuestions(HashMap<Integer,HashMap<String,String>> organised_definitions, int chapter_number){
        int score = 0;
        List<Integer> questions_asked = new ArrayList<>();
        for (int i = 1; i != 5 ; i++){
            List<String> answer_chapter = questionOutput(organised_definitions, chapter_number, questions_asked);
            questions_asked.add(Integer.parseInt(answer_chapter.get(1)));

            HashMap<Integer,String> answer_choices = outputAnswerChoices(answer_chapter,chapter_number,organised_definitions);
            int point = gradeTheAnswer(answer_choices, answer_chapter);
            score+= point;
        }

        System.out.printf("You got %d out of 4 correct.\n", score);
    }
}
