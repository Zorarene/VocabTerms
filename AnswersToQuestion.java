import java.util.*;
public class AnswersToQuestion {

    private Set<String> answerChoices;
    private String correctAnswer;

    public AnswersToQuestion (){
        answerChoices = new HashSet<>();
        correctAnswer = "";
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void add(String s) {
        answerChoices.add(s);
    }

    public void addCorrect(String s) {
        correctAnswer = s;
    }

    public int size() {
        return answerChoices.size();
    }

public String toString(){
        StringBuilder sb = new StringBuilder();
        int n = 1;
    for (String answerChoice : answerChoices) {
        sb.append( n+") "+answerChoice+ "\n");
        n++;
    }
    // to print the correct answer
    //sb.append(correctAnswer + "\n");
    return sb.toString();
}


    public String[] toArray(String[] strings) {
        return (String[]) Arrays.stream(strings).toArray();
    }
}
