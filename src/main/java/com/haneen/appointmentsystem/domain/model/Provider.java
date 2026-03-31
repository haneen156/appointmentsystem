package com.haneen.appointmentsystem.domain.model;

public class Provider {

    private Long id;
    private String name;
    private String specialization;

    public Provider( String name, String specialization) {
        validateString(name,"Name");
        validateString(specialization,"Specialization");
        this.name = name;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
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
    public void changeSpecialization(String newSpecialization){
        if(newSpecialization == null || newSpecialization.isBlank()) {
            throw new IllegalArgumentException("Specialization can't be empty or null");
        }
        this.specialization = newSpecialization;
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
    }
}
