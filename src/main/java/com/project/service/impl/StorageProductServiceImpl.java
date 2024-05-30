package com.project.service.impl;

import com.project.dto.InflowRequest;
import com.project.dto.OutflowRequest;
import com.project.entity.StorageProduct;
import com.project.repository.InflowRecordRepository;
import com.project.repository.SoldRecordRepository;
import com.project.repository.StorageProductRepository;
import com.project.repository.WrittenOffRecordRepository;
import com.project.entity.record.InflowRecord;
import com.project.entity.record.SoldRecord;
import com.project.entity.record.WrittenOffRecord;
import com.project.mapper.StorageProductMapper;
import com.project.service.StorageProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class StorageProductServiceImpl implements StorageProductService {
    private final StorageProductRepository storageProductRepository;
    private final InflowRecordRepository inflowRecordRepository;
    private final SoldRecordRepository soldRecordRepository;
    private final WrittenOffRecordRepository writtenOffRecordRepository;

    private final StorageProductMapper storageProductMapper;


    @Override
    public Long getRemainingQuantityOfStorageProduct(Long storageProductId) {
        try {
            return storageProductRepository.findById(storageProductId).get().getQuantity();
        } catch (DataAccessException ex) {
            return 0L;
        }
    }

    @Override
    public List<StorageProduct> getAllStorageProducts() {
        try {
            return storageProductRepository.findAll();
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<InflowRecord> getAllInflowRecords() {
        try {
            return inflowRecordRepository.findAll();
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<SoldRecord> getAllSoldRecords() {
        try {
            return soldRecordRepository.findAll();
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<WrittenOffRecord> getAllWrittenOffRecords() {
        try {
            return writtenOffRecordRepository.findAll();
        } catch (DataAccessException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public Boolean addNewStorageProduct(InflowRequest inflowRequest) {
        try {
            StorageProduct storageProduct = storageProductMapper.mapInflowRequestToProduct(inflowRequest);
            if (storageProduct.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException();
            }
            storageProduct = storageProductRepository.save(storageProduct);

            InflowRecord inflowRecord = new InflowRecord();
            inflowRecord.setStorageProduct(storageProduct);
            inflowRecord.setQuantity(inflowRequest.getQuantity());
            inflowRecord.setWrittenAt(LocalDateTime.now());
            inflowRecord.setPriceBought(inflowRequest.getPriceBought());

            inflowRecordRepository.save(inflowRecord);

            return true;
        } catch (IllegalArgumentException | DataAccessException ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean sellStorageProduct(Long storageProductId, OutflowRequest sellRequest) {
        try {
            StorageProduct storageProduct = storageProductRepository.findById(storageProductId).get();
            storageProduct.setQuantity(storageProduct.getQuantity() - sellRequest.getQuantity());
            storageProductRepository.save(storageProduct);

            SoldRecord soldRecord = new SoldRecord();
            soldRecord.setStorageProduct(storageProduct);
            soldRecord.setQuantity(sellRequest.getQuantity());
            soldRecord.setPriceSold(sellRequest.getPriceSold());
            soldRecord.setSoldAt(LocalDateTime.now());

            soldRecordRepository.save(soldRecord);

            return true;
        } catch (DataAccessException ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean writeOffStorageProduct(Long storageProductId, OutflowRequest writeOffRequest) {
        try {
            StorageProduct storageProduct = storageProductRepository.findById(storageProductId).get();
            storageProduct.setQuantity(storageProduct.getQuantity() - writeOffRequest.getQuantity());
            storageProductRepository.save(storageProduct);

            WrittenOffRecord writtenOffRecord = new WrittenOffRecord();
            writtenOffRecord.setStorageProduct(storageProduct);
            writtenOffRecord.setQuantity(writeOffRequest.getQuantity());
            writtenOffRecord.setWrittenOffAt(LocalDateTime.now());

            writtenOffRecordRepository.save(writtenOffRecord);

            return true;
        } catch (DataAccessException ex) {
            return false;
        }
    }
}
