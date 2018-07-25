package hpscore.domain;

/**
 * Created by ricardo on 2018/6/6.
 */
/**
 *@Author: Ricardo
 *@Description: 评委打分表
 *@Date: 17:31 2018/6/11
 *@param:
 **/
public class PingweiScore {

    //对应Score表的记录
    private int id;
    //评委序号
    private String pid;
    //评委name
    private String pName;
    //作品id或name
    private String proId;
    //作品id或name
    private String proName;
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
    private String model;

    //作品编号
    private String bianHao;

    public String getBianHao() {
        return bianHao;
    }
    public void setBianHao(String bianHao) {
        this.bianHao = bianHao;
    }

    public PingweiScore(int id, String pid, String proId,
                        int option1, int option2, int option3, int option4, int option5, int option6,
                        int totalScore, String model) {
        this.id = id;
        this.pid = pid;
        this.proId = proId;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.option6 = option6;
        this.totalScore = totalScore;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public PingweiScore() {

    }
}
