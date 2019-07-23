package com.company;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        JSONObject json = new JSONObject();

        try {
            json = JsonHelper.readJsonFromUrl("https://opentdb.com/api.php?amount=10");
            //System.out.println(json.getJSONArray("results"));

            Iterator<Object> questions = (json.getJSONArray("results")).iterator();

            System.out.println("Welcome to the quiz show!\nTry and answer 10 questions!");
            int score = 0;
            while (questions.hasNext()) {
                JSONObject question = (JSONObject) questions.next();
                List<Object> wrong = question.getJSONArray("incorrect_answers").toList();
                int right = new Random().nextInt(wrong.size() + 1);
                int size = wrong.size();
                String correct = StringEscapeUtils.unescapeHtml4(String.valueOf(question.get("correct_answer")));
                String choices = "";
                for (int i = 0; i <= size; i++) {
                    if (i ==  right) {
                        choices += (i + 1) + ". " + question.get("correct_answer") + "\n";
                    } else {
                        choices += (i + 1) + ". " + wrong.remove(new Random().nextInt(wrong.size())) + "\n";
                    }
                }
                right++;
                System.out.println(StringEscapeUtils.unescapeHtml4(String.valueOf(question.get("question"))));
                System.out.println(StringEscapeUtils.unescapeHtml4(choices));
                int choice = read.nextInt();

                if (choice == right) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect! The answer is: " + correct);
                }
            }

            System.out.println("Finish!\nYour final score is " + score + "/10!" );
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
