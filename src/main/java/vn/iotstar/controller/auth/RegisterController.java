package vn.iotstar.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/auth")
public class RegisterController {

    public static final String COOKIE_REMEMBER = "userName";

    @Autowired
    private UserService userService;

    // ---------------- GET REGISTER ----------------
    @GetMapping("/register")
    public String register(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            return "redirect:/admin/dashboard";
        }

        // Kiểm tra cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    return "redirect:/admin/dashboard";
                }
            }
        }

        model.addAttribute("userModel", new UserModel());
        return "/views/register";
    }

    // ---------------- POST REGISTER ----------------
    @PostMapping("/register")
    public String register(@ModelAttribute("userModel") UserModel userModel,
                           ModelMap model,
                           HttpServletRequest request) {

        String userName = userModel.getUserName();
        String password = userModel.getPassword();
        String email = userModel.getEmail();
        String fullName = userModel.getFullname();
        String phone = userModel.getPhone();

        // Kiểm tra trùng lặp
        if (userService.checkExistEmail(email)) {
            model.addAttribute("alert", "Email đã tồn tại!");
            return "/views/register";
        }

        if (userService.checkExistUsername(userName)) {
            model.addAttribute("alert", "Tài khoản đã tồn tại!");
            return "/views/register";
        }

        if (userService.checkExistPhone(phone)) {
            model.addAttribute("alert", "Số điện thoại đã tồn tại!");
            return "/views/register";
        }

        boolean success = userService.register(email, userName, fullName, password, phone);
        if (success) {
            return "redirect:/auth/login";
        } else {
            model.addAttribute("alert", "System error!");
            return "/views/register";
        }
    }
}
