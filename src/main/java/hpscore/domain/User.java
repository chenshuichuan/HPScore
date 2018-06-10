package hpscore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ricardo on 2018/6/6.
 */
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String password;
    private int role;
    public User() {
    }
    public User(String name, String password, int role) {
        this.name = name;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
