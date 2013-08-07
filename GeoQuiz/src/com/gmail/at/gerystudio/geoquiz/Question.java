package com.gmail.at.gerystudio.geoquiz;

/**
 * Created with IntelliJ IDEA.
 * User: ankyhe
 * Date: 13-8-7
 * Time: PM10:14
 * To change this template use File | Settings | File Templates.
 */
public class Question {

    public String questionStr;

    public boolean trueOrFalse;

    public Question(String aQuestionStr, boolean aTrueOrFalse) {
        questionStr = aQuestionStr;
        trueOrFalse = aTrueOrFalse;
    }

}
