package vn.iotstar.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.entity.Category;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String LOGIN_PAGE = "/auth/login";

    // ===== LIST CATEGORY =====
    @GetMapping("/list")
    public String listCategory(HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/category/list-category";
    }

    // ===== CREATE CATEGORY FORM =====
    @GetMapping("/add")
    public String createForm(HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        model.addAttribute("category", new CategoryModel());
        return "admin/category/add-category";
    }

    // ===== SAVE NEW CATEGORY =====
    @PostMapping("/add")
    public String saveCategory(@ModelAttribute CategoryModel categoryModel, HttpSession session) throws IOException {

        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        String finalFileName = categoryModel.getImages();
        MultipartFile file = categoryModel.getImagesFile();

        // ===== Xử lý upload file =====
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = originalFileName.lastIndexOf(".");
            if (dot != -1) ext = originalFileName.substring(dot);

            finalFileName = System.currentTimeMillis() + ext;

            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            File destFile = new File(uploadFolder, finalFileName);
            file.transferTo(destFile);
        }

        // ===== Chuyển CategoryModel -> Category entity =====
        Category category = new Category();
        category.setCategoryName(categoryModel.getCategoryName());
        category.setCategoryCode(categoryModel.getCategoryCode());
        category.setImages(finalFileName);
        category.setStatus(categoryModel.getStatus());
        category.setUser(userService.findByName(user.getUserName()).orElse(null));

        categoryService.save(category);
        return "redirect:/admin/category/list";
    }

    // ===== EDIT CATEGORY FORM =====
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int categoryId, HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        Category category = categoryService.findById(categoryId).orElse(null);
        if (category == null) return "redirect:/admin/category/list";

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(category.getCategoryId());
        categoryModel.setCategoryName(category.getCategoryName());
        categoryModel.setCategoryCode(category.getCategoryCode());
        categoryModel.setImages(category.getImages());
        categoryModel.setStatus(category.getStatus());

        model.addAttribute("category", categoryModel);
        return "admin/category/edit-category";
    }

    // ===== UPDATE CATEGORY =====
    @PostMapping("/edit")
    public String updateCategory(@ModelAttribute CategoryModel categoryModel, HttpSession session) throws IOException {

        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        Category category = categoryService.findById(categoryModel.getCategoryId()).orElse(null);
        if (category == null) return "redirect:/admin/category/list";

        category.setCategoryName(categoryModel.getCategoryName());
        category.setCategoryCode(categoryModel.getCategoryCode());
        category.setStatus(categoryModel.getStatus());

        String finalFileName = category.getImages();
        MultipartFile file = categoryModel.getImagesFile();

        // ===== Xử lý upload file mới nếu có =====
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = originalFileName.lastIndexOf(".");
            if (dot != -1) ext = originalFileName.substring(dot);

            finalFileName = System.currentTimeMillis() + ext;

            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            File destFile = new File(uploadFolder, finalFileName);
            file.transferTo(destFile);
        }

        category.setImages(finalFileName);

        categoryService.save(category);
        return "redirect:/admin/category/list";
    }

    // ===== DELETE CATEGORY =====
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") int categoryId, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        categoryService.deleteById(categoryId);
        return "redirect:/admin/category/list";
    }

}
