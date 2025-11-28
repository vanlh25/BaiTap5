package vn.iotstar.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {
        // Kiểm tra session có account không
        Object account = session.getAttribute("account");
        if (account == null) {
            // Chưa đăng nhập -> redirect về login
            return "redirect:/auth/login";
        }

        // Có thể truyền thông tin user ra view nếu cần
        model.addAttribute("account", account);

        // Trả về view dashboard.jsp (trong thư mục /templates/views nếu dùng Thymeleaf)
        return "views/dashboard";
    }
}
