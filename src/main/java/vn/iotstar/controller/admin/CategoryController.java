package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.model.CategoryModel;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // ------------------- LIST -------------------
    @GetMapping("")
    public String list(ModelMap model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/list"; // view list.jsp
    }

    // ------------------- ADD -------------------
    @GetMapping("/add")
    public String add(ModelMap model) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIsEdit(false);
        model.addAttribute("category", categoryModel);
        return "admin/categories/addOrEdit"; // view addOrEdit.jsp
    }

    // ------------------- SAVE / UPDATE -------------------
    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(
            @Valid @ModelAttribute("category") CategoryModel categoryModel,
            BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            return new ModelAndView("admin/categories/addOrEdit");
        }

        Category entity = new Category();
        BeanUtils.copyProperties(categoryModel, entity);
        categoryService.save(entity);

        String message = categoryModel.getIsEdit() ? "Category updated successfully!" : "Category added successfully!";
        model.addAttribute("message", message);

        return new ModelAndView("redirect:/admin/categories");
    }

    // ------------------- EDIT -------------------
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id, ModelMap model) {
        Optional<Category> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isPresent()) {
            Category entity = categoryOpt.get();
            CategoryModel categoryModel = new CategoryModel();
            BeanUtils.copyProperties(entity, categoryModel);
            categoryModel.setIsEdit(true);
            model.addAttribute("category", categoryModel);
            return new ModelAndView("admin/categories/addOrEdit", model);
        } else {
            model.addAttribute("message", "Category not found!");
            return new ModelAndView("redirect:/admin/categories", model);
        }
    }

    // ------------------- DELETE -------------------
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, ModelMap model) {
        categoryService.deleteById(id);
        model.addAttribute("message", "Category deleted successfully!");
        return new ModelAndView("redirect:/admin/categories", model);
    }
}
