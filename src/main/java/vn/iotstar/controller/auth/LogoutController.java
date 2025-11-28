package vn.iotstar.controller.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    private static final String LOGIN_PAGE = "/auth/login";
    private static final String COOKIE_REMEMBER = "userName";

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // Xoá session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Xoá cookie remember me
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // chắc chắn path đúng
                    response.addCookie(cookie);
                }
            }
        }

        return "redirect:" + LOGIN_PAGE;
    }
}
