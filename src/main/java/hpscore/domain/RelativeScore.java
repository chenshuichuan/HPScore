package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="relative_score")
public class RelativeScore {

    @Id
    @GeneratedValue
    private int id;
    private String proId;//作品code
    private String bianHao;//作品编号
    private String proName;//作品name
    private double maxScore;
    private double minScore;
    private double average;
    //评委们的相对分
    private double[]pScore;
    private String model;

    public RelativeScore() {
    }

    public RelativeScore(String proId, String proName, String model) {
        this.proId = proId;
        this.proName = proName;
        this.model = model;
    }
    public double[] getpScore() {
        return pScore;
    }
    public void setpScore(double[] pScore) {
        this.pScore = pScore;
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
