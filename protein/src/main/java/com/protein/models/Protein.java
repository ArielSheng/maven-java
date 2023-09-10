package com.protein.models;

import java.util.HashMap;

public class Protein {
    String name;   //蛋白质名称
    String sequence;    //蛋白质序列
    String[] peptides;  //胰蛋白酶酶切肽段
    HashMap<String, Integer> peptideType; //key肽段：value肽段类型（0-特征肽段；1-单次肽段；其他-非特征非单次肽段）

    public Protein() {

    }

   public HashMap<String, Integer> getPeptideType() {
        return peptideType;
    }

    public void setPeptideType(HashMap<String, Integer> peptideType) {
        this.peptideType = peptideType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String[] getPeptides() {
        return peptides;
    }

    public void setPeptides(String[] peptides) {
        this.peptides = peptides;
    }
}
