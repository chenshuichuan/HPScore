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
    private String proName;//作品name
    private double maxScore;
    private double minScore;
    private double average;
    private double pScore1;
    private double pScore2;
    private double pScore3;
    private double pScore4;
    private double pScore5;
    private double pScore6;
    private double pScore7;
    private double pScore8;
    private double pScore9;
    private double pScore10;
    private double pScore11;
    private String model;

    public RelativeScore() {
    }

    public RelativeScore(String proId, String proName, String model) {
        this.proId = proId;
        this.proName = proName;
        this.model = model;
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

    public double getpScore1() {
        return pScore1;
    }

    public void setpScore1(double pScore1) {
        this.pScore1 = pScore1;
    }

    public double getpScore2() {
        return pScore2;
    }

    public void setpScore2(double pScore2) {
        this.pScore2 = pScore2;
    }

    public double getpScore3() {
        return pScore3;
    }

    public void setpScore3(double pScore3) {
        this.pScore3 = pScore3;
    }

    public double getpScore4() {
        return pScore4;
    }

    public void setpScore4(double pScore4) {
        this.pScore4 = pScore4;
    }

    public double getpScore5() {
        return pScore5;
    }

    public void setpScore5(double pScore5) {
        this.pScore5 = pScore5;
    }

    public double getpScore6() {
        return pScore6;
    }

    public void setpScore6(double pScore6) {
        this.pScore6 = pScore6;
    }

    public double getpScore7() {
        return pScore7;
    }

    public void setpScore7(double pScore7) {
        this.pScore7 = pScore7;
    }

    public double getpScore8() {
        return pScore8;
    }

    public void setpScore8(double pScore8) {
        this.pScore8 = pScore8;
    }

    public double getpScore9() {
        return pScore9;
    }

    public void setpScore9(double pScore9) {
        this.pScore9 = pScore9;
    }

    public double getpScore10() {
        return pScore10;
    }

    public void setpScore10(double pScore10) {
        this.pScore10 = pScore10;
    }

    public double getpScore11() {
        return pScore11;
    }

    public void setpScore11(double pScore11) {
        this.pScore11 = pScore11;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
