package com.project.entity.user;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String firstName, String lastName, String password, String email) {
        super(firstName, lastName, password, email, Role.ADMIN);
    }

    @Override
    public String getInfo() {
        return String.format("  Admin Info: <br>First Name: %s<br>Last Name:%s<br>Email:%s", this.getFirstName(),
                this.getLastName(), this.getEmail());
    }
}
