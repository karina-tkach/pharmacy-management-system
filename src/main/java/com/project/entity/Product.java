package com.project.entity;

import com.project.entity.characteristic.AccountingType;
import com.project.entity.characteristic.Category;
import com.project.entity.characteristic.Form;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "form_id")
  private Form form;

  @ManyToOne
  @JoinColumn(name = "accounting_type_id")
  private AccountingType accountingType;

  @Column(name = "expiration_days")
  private Long expirationDays;

  @Column(name = "is_prescriptive")
  private Boolean isPrescriptive;


  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<StorageProduct> storageProducts;
}
