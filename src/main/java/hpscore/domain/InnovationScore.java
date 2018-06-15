package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
/**
 *@Author: Ricardo
 *@Description: 用于创新性、实用性分数的计算
 *@Date: 21:17 2018/6/14
 *@param: 
 **/
public class InnovationScore {

    private String proId;//作品code
    private String proName;//作品name
    private int maxScore;
    private int minScore;
    private double average;

    private int pScore1;
    private int pScore2;
    private int pScore3;
    private int pScore4;
    private int pScore5;
    private int pScore6;
    private int pScore7;
    private int pScore8;
    private int pScore9;
    private int pScore10;
    private int pScore11;
    private String model;

    public InnovationScore() {
    }

    public InnovationScore(String proId, String proName, String model) {
        this.proId = proId;
        this.proName = proName;
        this.model = model;
    }

    public double getAverage() {
        return average;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getpScore1() {
        return pScore1;
    }

    public void setpScore1(int pScore1) {
        this.pScore1 = pScore1;
    }

    public int getpScore2() {
        return pScore2;
    }

    public void setpScore2(int pScore2) {
        this.pScore2 = pScore2;
    }

    public int getpScore3() {
        return pScore3;
    }

    public void setpScore3(int pScore3) {
        this.pScore3 = pScore3;
    }

    public int getpScore4() {
        return pScore4;
    }

    public void setpScore4(int pScore4) {
        this.pScore4 = pScore4;
    }

    public int getpScore5() {
        return pScore5;
    }

    public void setpScore5(int pScore5) {
        this.pScore5 = pScore5;
    }

    public int getpScore6() {
        return pScore6;
    }

    public void setpScore6(int pScore6) {
        this.pScore6 = pScore6;
    }

    public int getpScore7() {
        return pScore7;
    }

    public void setpScore7(int pScore7) {
        this.pScore7 = pScore7;
    }

    public int getpScore8() {
        return pScore8;
    }

    public void setpScore8(int pScore8) {
        this.pScore8 = pScore8;
    }

    public int getpScore9() {
        return pScore9;
    }

    public void setpScore9(int pScore9) {
        this.pScore9 = pScore9;
    }

    public int getpScore10() {
        return pScore10;
    }

    public void setpScore10(int pScore10) {
        this.pScore10 = pScore10;
    }

    public int getpScore11() {
        return pScore11;
    }

    public void setpScore11(int pScore11) {
        this.pScore11 = pScore11;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
