package com.example.android.reportcard;

import java.util.Arrays;

public class ReportCard {
    private String studentName;
    private int[] mathGrades;
    private int[] scienceGrades;
    private int[] historyGrades;
    private int mathMedia;
    private int scienceMedia;
    private int historyMedia;


    public ReportCard(String name) {
        studentName = name;
        mathGrades = new int[4];
        scienceGrades = new int[4];
        historyGrades = new int[4];
        mathMedia = 0;
        scienceMedia = 0;
        historyMedia = 0;
    }

    public void setMathGrade(int quarter, int grade) {
        //valida os dados de entrada se o trimestre informado for de 1~4.
        if (quarter > 0 && quarter < 5) {
            if (grade >= 0 && grade <= 10) {
                mathGrades[quarter - 1] = grade;
                mathMedia = calculateMedia(mathGrades);
            }
        }
    }

    public int getMathGrade(int quarter) {
        if(quarter>0 && quarter<5) {
            return mathGrades[quarter-1];
        } else{
            return 0;}}

    public void setScienceGrade(int quarter, int grade) {
        //valida os dados de entrada se o trimestre informado for de 1~4.
        if (quarter > 0 && quarter < 5) {
            if (grade >= 0 && grade <= 10) {
                scienceGrades[quarter - 1] = grade;
                scienceMedia = calculateMedia(scienceGrades);
            }
        }
    }

    public int getScienceGrade(int quarter) {
        if(quarter>0 && quarter<5) {
            return scienceGrades[quarter-1];
        } else {
            return 0;}}

    public void setHistoryGrade(int quarter, int grade) {
        //valida os dados de entrada se o trimestre informado for de 1~4.
        if (quarter > 0 && quarter < 5) {
            if (grade >= 0 && grade <= 10) {
                historyGrades[quarter - 1] = grade;
                historyMedia = calculateMedia(historyGrades);
            }
        }
    }

    public int getHistoryGrade(int quarter) {
        if(quarter>0 && quarter<5) {
            return historyGrades[quarter-1];
        } else {
            return 0;}}

    private int calculateMedia(int[] subject){
        int sum = 0;
        for (int i=0; i < subject.length;i++){
            sum += subject[i];
        }
        return sum / subject.length;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "studentName='" + studentName + '\'' +
                ", mathGrades=" + Arrays.toString(mathGrades) +
                ", scienceGrades=" + Arrays.toString(scienceGrades) +
                ", historyGrades=" + Arrays.toString(historyGrades) +
                ", mathMedia=" + mathMedia +
                ", scienceMedia=" + scienceMedia +
                ", historyMedia=" + historyMedia +
                '}';
    }
}
