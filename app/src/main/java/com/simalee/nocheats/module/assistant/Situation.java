package com.simalee.nocheats.module.assistant;

import java.util.HashMap;

/**
 * Created by Yang on 2017/7/7.
 */

public class Situation{
    private String situation;
    private String choiceOne;
    private String choiceTwo;
    private String choiceThree;
    private String choiceFour;
    private Situation choice_one_situ;
    private Situation choice_two_situ;
    private Situation choice_three_situ;
    private Situation choice_four_situ;
    private HashMap<String,String> noNextSituMap;

    public Situation getChoice_one_situ() {
        return choice_one_situ;
    }

    public void setChoice_one_situ(Situation choice_one_situ) {
        this.choice_one_situ = choice_one_situ;
    }

    public Situation getChoice_two_situ() {
        return choice_two_situ;
    }

    public void setChoice_two_situ(Situation choice_two_situ) {
        this.choice_two_situ = choice_two_situ;
    }

    public Situation getChoice_three_situ() {
        return choice_three_situ;
    }

    public void setChoice_three_situ(Situation choice_three_situ) {
        this.choice_three_situ = choice_three_situ;
    }

    public Situation getChoice_four_situ() {
        return choice_four_situ;
    }

    public void setChoice_four_situ(Situation choice_four_situ) {
        this.choice_four_situ = choice_four_situ;
    }

    public HashMap<String, String> getNoNextSituMap() {
        return noNextSituMap;
    }

    public void setNoNextSituMap(HashMap<String, String> noNextSituMap) {
        this.noNextSituMap = noNextSituMap;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getChoiceOne() {
        return choiceOne;
    }

    public void setChoiceOne(String choiceOne) {
        this.choiceOne = choiceOne;
    }

    public String getChoiceTwo() {
        return choiceTwo;
    }

    public void setChoiceTwo(String choiceTwo) {
        this.choiceTwo = choiceTwo;
    }

    public String getChoiceThree() {
        return choiceThree;
    }

    public void setChoiceThree(String choiceThree) {
        this.choiceThree = choiceThree;
    }

    public String getChoiceFour() {
        return choiceFour;
    }

    public void setChoiceFour(String choiceFour) {
        this.choiceFour = choiceFour;
    }


    public boolean hasNextStep() {
        if (choice_one_situ != null && choice_two_situ != null && choice_three_situ != null && choice_four_situ != null) {
            return true;
        }
        return false;
    }

    /**
     * 判断该情境下该choice 是否还有下一个情景
     * @param a
     * @return
     */
    public boolean hasNextStepForTheChoice(String a){
        switch (a){
            case "A":
                if (choice_one_situ != null) {
                    return true;
                }
                break;
            case "B":
                if (choice_two_situ != null) {
                    return true;
                }
                break;
            case "C":
                if (choice_three_situ != null) {
                    return true;
                }
                break;
            case "D":
                if (choice_four_situ != null) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**获取下一步的situation
     * @param choice
     * @return
     */
    public Situation getNextSituationForTheChoice(String choice) {
        if (hasNextStepForTheChoice(choice)) {
            switch (choice) {
                case "A":
                    if (choice_one_situ != null) {
                        return choice_one_situ;
                    }
                    break;
                case "B":
                    if (choice_two_situ != null) {
                        return choice_two_situ;
                    }
                    break;
                case "C":
                    if (choice_three_situ != null) {
                        return choice_three_situ;
                    }
                    break;
                case "D":
                    if (choice_four_situ != null) {
                        return choice_four_situ;
                    }
                    break;
                default:
                    return null;
            }

        }
        return null;
    }

    public Situation(String s,String c1,String c2,String c3,String c4){
        this.situation = s;
        this.choiceOne = c1;
        this.choiceTwo = c2;
        this.choiceThree = c3;
        this.choiceFour = c4;
    }
}