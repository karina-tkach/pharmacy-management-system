package com.project.entity;

import com.project.entity.record.InflowRecord;
import com.project.entity.record.SoldRecord;
import com.project.entity.record.WrittenOffRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "storage_products")
public class StorageProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

  private Long quantity;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;
  

  @OneToMany(mappedBy = "storageProduct", cascade = CascadeType.ALL)
  private List<InflowRecord> inflowRecords;

  @OneToMany(mappedBy = "storageProduct", cascade = CascadeType.ALL)
  private List<SoldRecord> soldRecords;

  @OneToMany(mappedBy = "storageProduct", cascade = CascadeType.ALL)
  private List<WrittenOffRecord> writtenOffRecords;
}
