package com.project.controller;

import com.project.entity.user.Pharmacist;
import com.project.entity.user.Role;
import com.project.entity.user.User;
import com.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("admin/users")
@AllArgsConstructor
@SuppressWarnings("java:S1192")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String viewHomePageWithPagination(Model model) {
        List<User> users = userService.getAllUsers(Role.PHARMACIST);

        if (users.isEmpty()) {
            model.addAttribute("error", "Can't load users");
        } else {
            model.addAttribute("listUsers", users);
        }

        return "users";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new Pharmacist();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") Pharmacist user, Model model) {
        User addedUser = userService.addUser(user);
        if (addedUser == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid user parameters", "new_user");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added user", "new_user");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        if (user == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get user by this id", "update_user");
        }
        return "update_user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") Pharmacist user, Model model, Principal principal) {
        Long userId = user.getId();

        User updatedUser = userService.updateUserById(user, userId);

        if (updatedUser == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid user parameters", "update_user");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated user", "update_user");
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        if (Objects.equals(userService.getUserById(id).getEmail(), principal.getName())) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete executor", "update_user");
        }
        boolean result = userService.deleteUserById(id);
        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete user by this id", "update_user");
        }
        return "redirect:/admin/users";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
