package com.project.controller;

import com.project.entity.characteristic.Category;
import com.project.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PageController {
  private final CategoryService categoryService;
  private final FormService formService;
  private final AccountingTypeService accountingTypeService;
  private final SupplierService supplierService;
  private final ProductService productService;
  private final StorageProductService storageProductService;
  private final UserService userService;

  @GetMapping("/home")
  public String getHome(Model model) {
    model.addAttribute("admin", userService.getUserById(1L));
    return "home";
  }

  @GetMapping("/categories")
  public String getCategories(Model model) {
    model.addAttribute("categories", categoryService.getAllCategories());
    return "categories";
  }

  @GetMapping("/suppliers")
  public String getSuppliers(Model model) {
    model.addAttribute("suppliers", supplierService.getAllSuppliers());
    return "suppliers";
  }

  @GetMapping("/products")
  public String getProducts(Model model) {
    model.addAttribute("categories", categoryService.getAllCategoriesWithNbsp());
    model.addAttribute("forms", formService.getAllForms());
    model.addAttribute("accountingTypes", accountingTypeService.getAllAccountingTypes());
    model.addAttribute("products", productService.getAllProducts());
    return "products";
  }

  @GetMapping("/storage")
  public String getStorage(Model model) {
    model.addAttribute("products", productService.getAllProducts());
    model.addAttribute("suppliers", supplierService.getAllSuppliers());
    model.addAttribute("storageProducts", storageProductService.getAllStorageProducts());
    return "storage";
  }

  @GetMapping("/storage/inflow")
  public String getInflowRecords(Model model) {
    model.addAttribute("inflowRecords", storageProductService.getAllInflowRecords());
    return "inflow";
  }

  @GetMapping("/storage/sold")
  public String getSoldRecords(Model model) {
    model.addAttribute("soldRecords", storageProductService.getAllSoldRecords());
    return "sold";
  }

  @GetMapping("/storage/written-off")
  public String getWrittenOffRecords(Model model) {
    model.addAttribute("writtenOffRecords", storageProductService.getAllWrittenOffRecords());
    return "written-off";
  }

  @GetMapping("/statistics-product")
  public String getStatisticsProduct(Model model) {
    model.addAttribute("products", productService.getAllProducts());
    return "statistics-product";
  }

  @GetMapping("/statistics-category")
  public String getstatisticsGroup(Model model) {
    List<Category> categories = categoryService.getAllCategories();

    categories = categories.stream()
            .filter(category -> !category.getProducts().isEmpty())
            .collect(Collectors.toList());
    
    model.addAttribute("categories", categories);
    return "statistics-category";
  }
}
