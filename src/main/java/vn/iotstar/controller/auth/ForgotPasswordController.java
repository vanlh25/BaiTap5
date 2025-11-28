package vn.iotstar.controller.auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iotstar.service.UserService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService; // Spring sẽ inject bean

    @GetMapping("/auth/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password"; // forward đến /WEB-INF/views/forgot-password.jsp
    }

    @PostMapping("/auth/forgot-password")
    public String handleForgotPassword(
            @RequestParam("email") String email,
            Model model,
            HttpSession session) {

        String alertMsg = "";

        // Kiểm tra email rỗng
        if (email == null || email.trim().isEmpty()) {
            alertMsg = "Email không được để trống.";
            model.addAttribute("alert", alertMsg);
            return "forgot-password"; // forward trở lại trang JSP
        }

        // Kiểm tra email tồn tại
        if (!userService.checkExistEmail(email)) {
            alertMsg = "Email không tồn tại trong hệ thống.";
            model.addAttribute("alert", alertMsg);
            return "forgot-password"; // forward trở lại
        }

        // Nếu email hợp lệ, lưu vào session để bước reset password
        session.setAttribute("resetEmail", email);

        // redirect sang trang reset password
        return "redirect:/auth/reset-password";
    }
}
