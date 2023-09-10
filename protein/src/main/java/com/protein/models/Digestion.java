package com.protein.models;

public class Digestion {
    String proteinName;     //蛋白质名称
    int peptideCount;       //肽段数量

    public Digestion() {
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public int getPeptideCount() {
        return peptideCount;
    }

    public void setPeptideCount(int peptideCount) {
        this.peptideCount = peptideCount;
    }
}
