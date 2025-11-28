package vn.iotstar.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.iotstar.model.UserModel;

@Controller
public class AdminHomeController {

    private static final String LOGIN_PAGE = "/auth/login";

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        // Kiểm tra session
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) {
            return "redirect:" + LOGIN_PAGE;
        }

        model.addAttribute("fullname", user.getFullName());
        return "admin/home"; // -> /WEB-INF/views/admin/home.jsp
    }
}
