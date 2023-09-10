package com.protein.models;

public class AminoAcid {
    String name;
    String shortName;
    String oneLetterCode;
    String formula;
    double monoIsotopicMass;
    double averageMass;

    public AminoAcid() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOneLetterCode() {
        return oneLetterCode;
    }

    public void setOneLetterCode(String oneLetterCode) {
        this.oneLetterCode = oneLetterCode;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double getMonoIsotopicMass() {
        return monoIsotopicMass;
    }

    public void setMonoIsotopicMass(double monoIsotopicMass) {
        this.monoIsotopicMass = monoIsotopicMass;
    }

    public double getAverageMass() {
        return averageMass;
    }

    public void setAverageMass(double averageMass) {
        this.averageMass = averageMass;
    }
}
