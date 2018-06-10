package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="score")
public class Score {

    @Id
    @GeneratedValue
    private int id;
    //评委id或name
    private String pid;
    //作品id或name
    private String proId;

    //6个评分项
    private int option1;
    private int option2;
    private int option3;
    private int option4;
    private int option5;
    private int option6;

    //原始总分
    private int totalScore;
    //最终相对分
    private double finalScore;
    private int editTimes;
    //第一个记录员
    private String editor1;
    //第二个记录员
    private String editor2;
    private String model;
    public Score() {
    }

    public Score(String pid, String proId,
                 int option1, int option2, int option3, int option4, int option5, int option6,
                  int editTimes, String model) {
        this.pid = pid;
        this.proId = proId;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.option6 = option6;
        this.totalScore = option1+option2+option3+option4+option5+option6;
        this.editTimes = editTimes;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public int getOption1() {
        return option1;
    }

    public void setOption1(int option1) {
        this.option1 = option1;
    }

    public int getOption2() {
        return option2;
    }

    public void setOption2(int option2) {
        this.option2 = option2;
    }

    public int getOption3() {
        return option3;
    }

    public void setOption3(int option3) {
        this.option3 = option3;
    }

    public int getOption4() {
        return option4;
    }

    public void setOption4(int option4) {
        this.option4 = option4;
    }

    public int getOption5() {
        return option5;
    }

    public void setOption5(int option5) {
        this.option5 = option5;
    }

    public int getOption6() {
        return option6;
    }

    public void setOption6(int option6) {
        this.option6 = option6;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public int getEditTimes() {
        return editTimes;
    }

    public void setEditTimes(int editTimes) {
        this.editTimes = editTimes;
    }

    public String getEditor1() {
        return editor1;
    }

    public void setEditor1(String editor1) {
        this.editor1 = editor1;
    }

    public String getEditor2() {
        return editor2;
    }

    public void setEditor2(String editor2) {
        this.editor2 = editor2;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
