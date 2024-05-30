package com.project.entity.characteristic;

import com.project.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "forms")
public class Form extends Characteristic {
    public Form(String name) {
        super.setName(name);
    }

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<Product> products;

}
