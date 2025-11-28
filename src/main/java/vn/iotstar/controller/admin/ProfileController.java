package vn.iotstar.controller.admin;

import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Value("${file.upload-dir}")
    private String uploadDir; // đọc từ file config

    @GetMapping("/profile")
    public String profile(HttpSession session, ModelMap model,
                          @RequestParam(value = "message", required = false) String message) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:/auth/login";

        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "/views/user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") UserModel userModel,
                                @RequestParam("images") MultipartFile file,
                                HttpSession session) throws IOException {

        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:/auth/login";

        user.setFullname(userModel.getFullname());
        user.setPhone(userModel.getPhone());

        String finalFileName = user.getImages(); // giữ ảnh cũ nếu không upload

        if (file != null && !file.isEmpty()) {
            String submittedFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
            String ext = submittedFileName.contains(".") ?
                         submittedFileName.substring(submittedFileName.lastIndexOf(".")) : "";
            finalFileName = System.currentTimeMillis() + ext;

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) uploadDirectory.mkdirs();

            file.transferTo(new File(uploadDir + "/" + finalFileName));
        }

        user.setImages(finalFileName);
        User entity = new User();
        BeanUtils.copyProperties(user, entity); // copy dữ liệu từ model sang entity
        userService.save(entity);
        session.setAttribute("account", user);

        return "redirect:/admin/dashboard";
    }
}
