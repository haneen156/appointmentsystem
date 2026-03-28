package com.haneen.appointmentsystem.domain.model;

public class Provider {

    private Long id;
    private String name;
    private String specialization;

    public Provider( String name, String specialization) {
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
}
