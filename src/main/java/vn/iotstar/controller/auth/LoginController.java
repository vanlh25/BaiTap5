package vn.iotstar.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private static final String COOKIE_REMEMBER = "userName";
    private static final String LOGIN_PAGE = "auth/login";  // /WEB-INF/views/auth/login.jsp
    private static final String WAITING_URL = "/auth/waiting";

    @Autowired
    private UserService userService;

    // GET: /auth/login
    @GetMapping("/login")
    public String loginPage(@CookieValue(value = COOKIE_REMEMBER, defaultValue = "") String rememberUsername,
                            HttpSession session,
                            HttpServletResponse response) {

        // Nếu đã login, redirect waiting
        if (session.getAttribute("account") != null) {
            return "redirect:" + WAITING_URL;
        }

        // Nếu có cookie remember me
        if (!rememberUsername.isEmpty()) {
            Optional<User> userOpt = userService.findByName(rememberUsername);
            if (userOpt.isPresent()) {
                User userEntity = userOpt.get();
                if (userEntity.getAdmin() != null && userEntity.getAdmin()) {
                    // Admin auto login
                    UserModel userModel = new UserModel();
                    BeanUtils.copyProperties(userEntity, userModel);
                    session.setAttribute("account", userModel);
                    return "redirect:" + WAITING_URL;
                }
            }
            // Không phải admin → xóa cookie
            Cookie cookie = new Cookie(COOKIE_REMEMBER, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return LOGIN_PAGE;
    }

    // POST: /auth/login
    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam(required = false) String remember,
                        HttpSession session,
                        HttpServletResponse response,
                        Model model) {

        if (userName.isEmpty() || password.isEmpty()) {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            return LOGIN_PAGE;
        }

        Optional<User> userOpt = userService.login(userName, password);
        if (userOpt.isPresent()) {
            User userEntity = userOpt.get();
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(userEntity, userModel);
            session.setAttribute("account", userModel);

            // Chỉ lưu cookie remember me nếu là admin
            if ("on".equals(remember) && Boolean.TRUE.equals(userEntity.getAdmin())) {
                Cookie cookie = new Cookie(COOKIE_REMEMBER, userName);
                cookie.setMaxAge(30 * 60); // 30 phút
                cookie.setPath("/");       // toàn app
                response.addCookie(cookie);
            }

            return "redirect:" + WAITING_URL;
        } else {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            return LOGIN_PAGE;
        }
    }
}
