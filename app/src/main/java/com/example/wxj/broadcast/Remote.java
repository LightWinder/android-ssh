package com.example.wxj.broadcast;

/**
 * Created by wxj on 2020/10/27.
 */

public class Remote {
    private String user ="admin";
    private String host ="192.168.123.1";
    private int port =22;
    private String password ="admin";
    private String identity ="~/.ssh/id_rsa";
    private String passphrase ="";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public Remote() {
    }

    public Remote(String user, String host, int port, String password, String identity, String passphrase) {
        this.user = user;
        this.host = host;
        this.port = port;
        this.password = password;
        this.identity = identity;
        this.passphrase = passphrase;
    }
}
