package com.project.mapper;

import com.project.dto.ProductDTO;
import com.project.entity.Product;
import com.project.repository.AccountingTypeRepository;
import com.project.repository.CategoryRepository;
import com.project.repository.FormRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  @Autowired
  protected CategoryRepository categoryRepository;

  @Autowired
  protected FormRepository formRepository;

  @Autowired
  protected AccountingTypeRepository accountingTypeRepository;


  @Mapping(target = "category", expression = "java(categoryRepository.findById(productDTO.getCategoryId()).get())")
  @Mapping(target = "form", expression = "java(formRepository.findById(productDTO.getFormId()).get())")
  @Mapping(target = "accountingType", expression = "java(accountingTypeRepository.findById(productDTO.getAccountingTypeId()).get())")
  public abstract Product mapDTOtoProduct(ProductDTO productDTO);
}
