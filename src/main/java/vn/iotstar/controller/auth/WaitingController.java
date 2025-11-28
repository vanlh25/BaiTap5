package vn.iotstar.controller.auth;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.iotstar.model.UserModel;

@Controller
public class WaitingController {

    @GetMapping("/auth/waiting")
    public String waiting(HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");

        if (user == null || !user.isAdmin()) {
            // Không phải admin → logout + thông báo
            session.invalidate();
            model.addAttribute("alert", "Bạn không có quyền truy cập. Vui lòng đăng nhập lại.");
            return "auth/login"; // forward tới login.jsp với alert
        }

        // Admin → /admin/home
        return "redirect:/admin/home";
    }

}
