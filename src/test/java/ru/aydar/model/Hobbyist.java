package ru.aydar.model;

import java.util.List;

public class Hobbyist {
    private String firstName;
    private String surname;
    private int age;
    private List<String> hobby;

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobby() {
        return hobby;
    }
}
