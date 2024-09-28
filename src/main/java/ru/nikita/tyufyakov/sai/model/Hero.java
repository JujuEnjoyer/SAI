package ru.nikita.tyufyakov.sai.model;

public class Hero {
    private String name;
    private String attribute;
    private String role;

    public Hero(String name, String attribute, String role) {
        this.name = name;
        this.attribute = attribute;
        this.role = role;
    }
    public Hero(){};

    public String getName() {
        return name;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
