package com.simalee.nocheats.module.assistant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yang on 2017/7/7.
 */

public class Game {
    private ArrayList<Situation> situationArrayList;

    public Game(ArrayList<Situation> situationArrayList){
        this.situationArrayList = situationArrayList;
    }

    public int getNumOfSituation(){
        return situationArrayList.size();
    }

    public ArrayList<Situation> getSituationArrayList() {
        return situationArrayList;
    }


}
