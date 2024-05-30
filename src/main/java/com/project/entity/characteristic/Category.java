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
@Table(name = "categories")
public class Category extends Characteristic {
  public Category(String code, String name) {
    this.code = code;
    super.setName(name);
  }

  private String code;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<Product> products;
}
