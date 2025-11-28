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
public class ResetPasswordController {

    @Autowired
    private UserService userService; // Spring inject IUserService

    @GetMapping("/auth/reset-password")
    public String showResetPasswordPage(HttpSession session) {
        // Nếu không có email trong session → redirect về forgot-password
        if (session.getAttribute("resetEmail") == null) {
            return "redirect:/auth/forgot-password";
        }

        return "reset-password"; // forward đến /WEB-INF/views/reset-password.jsp
    }

    @PostMapping("/auth/reset-password")
    public String handleResetPassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model,
            HttpSession session) {

        String alertMsg = "";

        // Kiểm tra session hợp lệ
        String email = (String) session.getAttribute("resetEmail");
        if (email == null) {
            return "redirect:/auth/login";
        }

        // Kiểm tra mật khẩu hợp lệ
        if (!password.equals(confirmPassword)) {
            alertMsg = "Passwords do not match!";
        } else if (password.length() < 8) {
            alertMsg = "Password must be at least 8 characters!";
        } else {
            // Cập nhật mật khẩu trong DB
            boolean success = userService.editPassword(email, password);
            if (success) {
                alertMsg = "Reset password successfully!";
                session.removeAttribute("resetEmail"); // Xóa session reset email
                model.addAttribute("alert", alertMsg);
                return "login"; // forward tới login.jsp
            } else {
                alertMsg = "System error";
            }
        }

        // Nếu thất bại, quay lại trang reset-password
        model.addAttribute("alert", alertMsg);
        return "reset-password";
    }
}
