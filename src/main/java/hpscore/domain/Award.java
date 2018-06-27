package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="award")
public class Award {

    @Id
    @GeneratedValue
    private int id;
    private int worksId;
    private String model;
    private double score;
    private String scoreType;//目前有"创新分","实用分"两项值
    private int ranking;

    public Award() {
    }

    public Award(int worksId, String model, double score, String scoreType) {
        this.worksId = worksId;
        this.model = model;
        this.score = score;
        this.scoreType = scoreType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorksId() {
        return worksId;
    }

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
