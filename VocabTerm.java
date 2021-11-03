public class VocabTerm {
    private int chapter;
    private String term;
    private String definition;


    public VocabTerm (){
        chapter = 0;
        term = "";
        definition = "";

    }

    public  VocabTerm (String[] entry){
        chapter = Integer.parseInt(entry[0]);
        term = entry[1];
        definition = entry[2];
    }


    public int getChapter() {
        return chapter;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }


}
