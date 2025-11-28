package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home") // URL /home
    public String home() {
        // Trả về tên JSP (không cần .jsp)
        return "home"; // Spring sẽ tìm /WEB-INF/views/home.jsp
    }
}
