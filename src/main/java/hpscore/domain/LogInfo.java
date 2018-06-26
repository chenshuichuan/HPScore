package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="log_info")
public class LogInfo {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String ip;
    private String time;
    private String action;
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public LogInfo() {
    }
    public LogInfo(String name, String ip, String time, String action,String model) {
        this.name = name;
        this.ip = ip;
        this.time = time;
        this.action = action;
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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


}
