package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

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
    private Date time;
    private String action;
    public LogInfo() {
    }

    public LogInfo(String name, String ip, Date time, String action) {
        this.name = name;
        this.ip = ip;
        this.time = time;
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
