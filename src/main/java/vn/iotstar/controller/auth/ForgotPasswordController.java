package vn.iotstar.controller.auth;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/auth")
public class ForgotPasswordController {

    private static final String FORGOT_PASSWORD_PAGE = "auth/forgot-password"; // -> /templates/auth/forgot-password.jsp
    private static final String RESET_PASSWORD_URL = "/auth/reset-password";

    @Autowired
    private UserService userService;

    // GET: /auth/forgot-password
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return FORGOT_PASSWORD_PAGE;
    }

    // POST: /auth/forgot-password
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email,
                                       HttpSession session,
                                       Model model) {

        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("alert", "Email không được để trống.");
            return FORGOT_PASSWORD_PAGE;
        }

        if (!userService.existsByEmail(email)) {
            model.addAttribute("alert", "Email không tồn tại trong hệ thống.");
            return FORGOT_PASSWORD_PAGE;
        }

        // Lưu email vào session để bước reset password
        session.setAttribute("resetEmail", email);

        return "redirect:" + RESET_PASSWORD_URL;
    }
}
