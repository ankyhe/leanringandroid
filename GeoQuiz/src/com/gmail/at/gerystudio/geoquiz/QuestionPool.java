package com.gmail.at.gerystudio.geoquiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-7
 * Time: PM10:15
 * To change this template use File | Settings | File Templates.
 */
public class QuestionPool {
    /* Singleton */
    private static QuestionPool instance = null;
    private QuestionPool() {
        questions = new ArrayList<Question>();
        initQuestions();
    }
    public static QuestionPool getInstance() {
        if (instance == null) {
            synchronized (QuestionPool.class) {
                if (instance == null) {
                    instance = new QuestionPool();
                }
            }
        }
        return instance;
    }
    /* END Singleton */


    private List<Question> questions;
    private int idx;


    public Question next() {
        if (idx == questions.size() - 1) {
            idx = 0;
        } else {
            ++idx;
        }
        return questions.get(idx);
    }

    public Question previous() {
        if (idx == 0) {
            idx = questions.size() - 1;
        } else {
            --idx;
        }
        return questions.get(idx);
    }

    public boolean isCorrect(boolean trueOrFalse) {
        return questions.get(idx).trueOrFalse == trueOrFalse;
    }

    private void initQuestions() {
        Question q1 = new Question("The Pacific Ocean is larger than the Atlantic Ocean.", true);
        Question q2 = new Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false);
        Question q3 = new Question("The source of the Nile River is in Egypt.", false);
        Question q4 = new Question("The Amazon River is the longest river in the Americas.", true);
        Question q5 = new Question("Lake Baikal is the world\\'s oldest and deepest freshwater lake.", true);
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        idx = questions.size() - 1;
    }

}
