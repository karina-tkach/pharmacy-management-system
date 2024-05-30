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
@Table(name = "accounting_types")
public class AccountingType extends Characteristic {
    public AccountingType(String name) {
        super.setName(name);
    }

    @OneToMany(mappedBy = "accountingType", cascade = CascadeType.ALL)
    private List<Product> products;
}
