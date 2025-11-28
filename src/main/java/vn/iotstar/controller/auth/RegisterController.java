package vn.iotstar.controller.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/auth")
public class RegisterController {

    private static final String COOKIE_REMEMBER = "userName";
    private static final String REGISTER_PAGE = "auth/register"; // /templates/auth/register.html
    private static final String LOGIN_URL = "/auth/login";
    private static final String WAITING_URL = "/auth/waiting";

    @Autowired
    private UserService userService;

    // GET: /auth/register
    @GetMapping("/register")
    public String registerPage(HttpSession session,
                               @CookieValue(value = COOKIE_REMEMBER, defaultValue = "") String rememberUsername) {
        // Nếu đã login
        if (session.getAttribute("account") != null) {
            return "redirect:" + WAITING_URL;
        }

        // Nếu có cookie remember me
        if (!rememberUsername.isEmpty()) {
            return "redirect:" + WAITING_URL;
        }

        return REGISTER_PAGE;
    }

    // POST: /auth/register
    @PostMapping("/register")
    public String register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String fullName,
                           @RequestParam String phone,
                           Model model,
                           HttpSession session,
                           HttpServletResponse response) {

        String alertMsg = "";

        // Kiểm tra trùng lặp
        if (userService.existsByEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            model.addAttribute("alert", alertMsg);
            return REGISTER_PAGE;
        }

        if (userService.existsByUserName(userName)) {
            alertMsg = "Tài khoản đã tồn tại!";
            model.addAttribute("alert", alertMsg);
            return REGISTER_PAGE;
        }

        if (userService.existsByPhone(phone)) {
            alertMsg = "Số điện thoại đã tồn tại!";
            model.addAttribute("alert", alertMsg);
            return REGISTER_PAGE;
        }

        // Thực hiện đăng ký
        boolean isSuccess = userService.register(email, userName, fullName, password, phone);
        if (isSuccess) {
            // Sau đăng ký chuyển về login
            return "redirect:" + LOGIN_URL;
        } else {
            alertMsg = "System error!";
            model.addAttribute("alert", alertMsg);
            return REGISTER_PAGE;
        }
    }
}
