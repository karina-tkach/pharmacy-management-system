package com.project.entity.user;

import jakarta.persistence.Entity;

@Entity
public class Pharmacist extends User {
    public Pharmacist() {
        super();
    }

    public Pharmacist(String firstName, String lastName, String password, String email) {
        super(firstName, lastName, password, email, Role.PHARMACIST);
    }

    @Override
    public String getInfo() {
        return String.format("First Name: %s | Last Name:%s | Email:%s", this.getFirstName(),
                this.getLastName(), this.getEmail());
    }
}
