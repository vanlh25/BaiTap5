package vn.iotstar.controller.auth;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/auth")
public class ResetPasswordController {

    private static final String RESET_PASSWORD_PAGE = "auth/reset-password"; // -> /templates/auth/reset-password.jsp
    private static final String LOGIN_PAGE = "auth/login"; // -> /templates/auth/login.html
    private static final String FORGOT_PASSWORD_URL = "/auth/forgot-password";

    @Autowired
    private UserService userService;

    // GET: /auth/reset-password
    @GetMapping("/reset-password")
    public String showResetPasswordPage(HttpSession session) {
        String email = (String) session.getAttribute("resetEmail");

        if (email == null) {
            // Nếu không có email trong session, quay lại trang forgot-password
            return "redirect:" + FORGOT_PASSWORD_URL;
        }

        return RESET_PASSWORD_PAGE;
    }

    // POST: /auth/reset-password
    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String password,
                                      @RequestParam String confirmPassword,
                                      HttpSession session,
                                      Model model) {

        String email = (String) session.getAttribute("resetEmail");

        if (email == null) {
            return "redirect:" + LOGIN_PAGE;
        }

        String alertMsg = null;

        // Kiểm tra mật khẩu
        if (!password.equals(confirmPassword)) {
            alertMsg = "Passwords không trùng khớp!";
        } else if (password.length() < 6) {
            alertMsg = "Password phải có ít nhất 6 kí tự!";
        } else {
            boolean success = userService.editPassword(email, password);
            if (success) {
                alertMsg = "Reset password successfully!";
                session.removeAttribute("resetEmail"); // Xóa session sau khi reset
                model.addAttribute("alert", alertMsg);
                return LOGIN_PAGE; // forward tới login
            } else {
                alertMsg = "System error!";
            }
        }

        // Nếu thất bại -> quay lại reset password page
        model.addAttribute("alert", alertMsg);
        return RESET_PASSWORD_PAGE;
    }
}
