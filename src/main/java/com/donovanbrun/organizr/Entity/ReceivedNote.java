package com.donovanbrun.organizr.Entity;

// Used to transform json structure received form saving a note into this class
public class ReceivedNote {

    private String username;
    private String name;
    private String content;

    public ReceivedNote(String username, String name, String content) {
        this.username = username;
        this.name = name;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
