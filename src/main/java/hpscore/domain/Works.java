package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="works")
public class Works {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    //序号（从1~24的那个，对应proId）
    private String code;
    private String model;
    private double finalScore;
    private String school;
    private String teachers;
    private String students;
    private int ranking;
    //分赛区名称
    private String partName;
    //作品编号
    private String bianHao;
    private int year;



    public String getBianHao() {
        return bianHao;
    }

    public void setBianHao(String bianHao) {
        this.bianHao = bianHao;
    }
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
    public Works() {
    }
    public Works(Works works) {
        this.id=works.id;
        this.name=works.name;
        this.code=works.code;
        this.bianHao=works.bianHao;
        this.finalScore=works.finalScore;
        this.model=works.model;
        this.school=works.school;
        this.teachers=works.teachers;
        this.students=works.students;
        this.ranking=works.ranking;
        this.partName=works.partName;
        this.year=works.year;
    }
    public Works(String name, String code, String model) {
        this.name = name;
        this.code = code;
        this.model = model;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
