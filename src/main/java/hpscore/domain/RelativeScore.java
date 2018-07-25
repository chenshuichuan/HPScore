package hpscore.domain;

/**
 * Created by ricardo on 2018/6/6.
 */

public class RelativeScore {

    private int id;
    private String proId;//作品code
    private String bianHao;//作品编号
    private String proName;//作品name
    private double maxScore;
    private double average;
    private double minScore;
    private double[] pAverage;//6个单项的平均分
    //评委们的相对分
    private double[]pScores;
    private String model;
    private int ranking;
    public int getRanking() {
        return ranking;
    }
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    public RelativeScore(int NumberOfPingwei) {
        this.pScores = new double[NumberOfPingwei];
        this.pAverage = new double[6];
    }

    public RelativeScore(String proId, String proName, String model,int NumberOfPingwei) {
        this.proId = proId;
        this.proName = proName;
        this.model = model;
        this.pScores = new double[NumberOfPingwei];
        this.pAverage = new double[6];
    }

    public double[] getpAverage() {
        return pAverage;
    }

    public void setpAverage(double[] pAverage) {
        this.pAverage = pAverage;
    }

    public String getBianHao() {
        return bianHao;
    }

    public void setBianHao(String bianHao) {
        this.bianHao = bianHao;
    }
    public double[] getpScores() {
        return pScores;
    }
    public void setpScores(double[] pScores) {
        this.pScores = pScores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
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

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
