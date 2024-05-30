package com.project.service;

import com.project.dto.InflowRequest;
import com.project.dto.OutflowRequest;
import com.project.entity.StorageProduct;
import com.project.entity.record.InflowRecord;
import com.project.entity.record.SoldRecord;
import com.project.entity.record.WrittenOffRecord;

import java.util.List;

public interface StorageProductService {

  Long getRemainingQuantityOfStorageProduct(Long storageProductId);

  List<StorageProduct> getAllStorageProducts();

  List<InflowRecord> getAllInflowRecords();

  List<SoldRecord> getAllSoldRecords();

  List<WrittenOffRecord> getAllWrittenOffRecords();

  Boolean addNewStorageProduct(InflowRequest inflowRequest);

  Boolean sellStorageProduct(Long storageProductId, OutflowRequest sellRequest);

  Boolean writeOffStorageProduct(Long storageProductId, OutflowRequest writeOffRequest);
}
