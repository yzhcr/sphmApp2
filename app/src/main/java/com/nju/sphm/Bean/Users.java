package com.nju.sphm.Bean;

import java.io.Serializable;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class Users implements Serializable{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
