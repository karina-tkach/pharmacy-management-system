package com.project.config;

import com.project.entity.characteristic.AccountingType;
import com.project.entity.characteristic.Category;
import com.project.entity.characteristic.Form;
import com.project.entity.user.Admin;
import com.project.repository.AccountingTypeRepository;
import com.project.repository.CategoryRepository;
import com.project.repository.FormRepository;
import com.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final FormRepository formRepository;
    private final AccountingTypeRepository accountingTypeRepository;
    private final UserService userService;


    @Override
    public void run(String... args) throws Exception {
        userService.addUser(new Admin("Karina", "Tkach", "User1234", "user@gmail.com"));
        if (categoryRepository.findAll().isEmpty()) {
            List<Category> categories = new ArrayList<>();

            categories.add(new Category("0001", "Hormones"));
            categories.add(new Category("0002", "Insulins"));
            categories.add(new Category("0003", "Vaccine"));

            categories.add(new Category("0004", "Vitamins"));
            categories.add(new Category("0004-0001", "B-complex vitamins"));

            categories.add(new Category("0005", "Antidepressants"));
            categories.add(new Category("0006", "Sedatives"));
            categories.add(new Category("0007", "Hypnotics"));

            categories.add(new Category("0008", "Gastrointestinal"));
            categories.add(new Category("0009", "Ophthalmic"));
            categories.add(new Category("0010", "Respiratory"));
            categories.add(new Category("0011", "Cardio-vascular"));

            categories.add(new Category("0012", "Antibiotics"));
            categories.add(new Category("0013", "Antiseptics"));
            categories.add(new Category("0014", "Antiviral"));

            categories.add(new Category("0015", "Anti-inflammatory"));
            categories.add(new Category("0016", "Painkillers"));
            categories.add(new Category("0017", "Other"));

            categoryRepository.saveAll(categories);
        }


        if (formRepository.findAll().isEmpty()) {
            List<Form> forms = new ArrayList<>();

            forms.add(new Form("Pill"));
            forms.add(new Form("Capsule"));
            forms.add(new Form("Granules"));
            forms.add(new Form("Powder"));
            forms.add(new Form("Ointment"));
            forms.add(new Form("Cream"));
            forms.add(new Form("Gel"));
            forms.add(new Form("Paste"));
            forms.add(new Form("Patch"));
            forms.add(new Form("Solution"));
            forms.add(new Form("Drops"));
            forms.add(new Form("Tincture"));
            forms.add(new Form("Syrup"));
            forms.add(new Form("Aerosols and sprays"));
            forms.add(new Form("Dose forms for injection"));
            forms.add(new Form("Other"));

            formRepository.saveAll(forms);
        }


        if (accountingTypeRepository.findAll().isEmpty()) {
            List<AccountingType> accountingTypes = new ArrayList<>();

            accountingTypes.add(new AccountingType("No type"));
            accountingTypes.add(new AccountingType("List A (poisonous and narcotic substances)"));
            accountingTypes.add(new AccountingType("List B (potent substances)"));

            accountingTypeRepository.saveAll(accountingTypes);
        }
    }
}