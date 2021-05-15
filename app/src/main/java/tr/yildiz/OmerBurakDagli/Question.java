package tr.yildiz.OmerBurakDagli;

import java.util.ArrayList;

public class Question {
    private String question;
    private String answer;
    private ArrayList<String> choices;
    public static ArrayList<Question> questions = new ArrayList<Question>();

    public Question(String question, String answer, ArrayList<String> choices) {
        this.question = question;
        this.answer = answer;
        this.choices = choices;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public static ArrayList<Question> getQuestions() {
        return questions;
    }

    public static void setQuestions(ArrayList<Question> questions) {
        Question.questions = questions;
    }
}
