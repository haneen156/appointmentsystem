package com.haneen.appointmentsystem.domain.model;

public class User {

    private Long id;
    private String name;
    private String email;

    public User(String name, String email) {
        validateString(name,"Name");
        validateString(email,"Email");
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void rename(String newName){
        if(newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Name can't be empty or null");
        }
        this.name = newName;
    }
    public void changeEmail(String newEmail){
        if(newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email can't be empty or null");
        }
        this.email = newEmail;
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
    }
}
