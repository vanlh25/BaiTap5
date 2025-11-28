package vn.iotstar.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class LoginController {

    public static final String COOKIE_REMEMBER = "userName";

    @Autowired
    private UserService userService;

    // ---------------- GET LOGIN ----------------
    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap model) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            return "redirect:/admin/dashboard";
        }

        // Kiểm tra cookie Remember Me
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    String userName = cookie.getValue();
                    Optional<User> optUser = userService.findByUsername(userName);
                    if (optUser.isPresent()) {
                        UserModel userModel = new UserModel();
                        BeanUtils.copyProperties(optUser.get(), userModel); // copy entity -> model
                        request.getSession(true).setAttribute("account", userModel);
                        return "redirect:/admin/dashboard";
                    }
                }
            }
        }

        model.addAttribute("userModel", new UserModel()); // Bind form login
        return "/views/login";
    }

    // ---------------- POST LOGIN ----------------
    @PostMapping("/login")
    public String login(@ModelAttribute("userModel") UserModel userModel,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap model) {

        String userName = userModel.getUserName();
        String password = userModel.getPassword();
        boolean rememberMe = userModel.isRememberMe();

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            return "/views/login";
        }

        Optional<User> optUser = userService.login(userName, password);
        if (optUser.isPresent()) {
            User userEntity = optUser.get();
            // Map entity -> model
            UserModel user = new UserModel();
            BeanUtils.copyProperties(userEntity, user);

            HttpSession session = request.getSession(true);
            session.setAttribute("account", user);

            if (rememberMe) {
                Cookie cookie = new Cookie(COOKIE_REMEMBER, user.getUserName());
                cookie.setMaxAge(30 * 60); // 30 phút
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }

            if (userService.checkRoleAdmin(user.getUserName())) {
                return "redirect:/admin/dashboard";
            } else {
                model.addAttribute("alert", "Bạn không có quyền truy cập!");
                return "/views/login";
            }
        } else {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            return "/views/login";
        }
    }
}
