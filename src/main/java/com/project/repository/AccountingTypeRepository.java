package com.project.repository;

import com.project.entity.characteristic.AccountingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingTypeRepository extends JpaRepository<AccountingType, Long> {

}
