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

    private String BianHao;//作品编号
    private String proName;//作品name

    private int maxScore;
    private int minScore;
    private double average;

    private int[] pScores;//评委打分

    private String model;

    public InnovationScore(int NumberOfPingwei) {
        pScores = new int [NumberOfPingwei];
    }

    public InnovationScore(String proId, String proName, String model,int NumberOfPingwei) {
        this.proId = proId;
        this.proName = proName;
        this.model = model;
        this.pScores = new int [NumberOfPingwei];
    }
    public int[] getpScores() {
        return pScores;
    }
    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        BianHao = bianHao;
    }

    public void setpScores(int[] pScores) {
        this.pScores = pScores;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
