package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/profile") // Chỉ profile thôi
public class ProfileController {

	@Autowired
	private UserService userService;

	@Value("${file.upload-dir}")
	private String uploadDir; // đọc từ application.properties

	private static final String LOGIN_PAGE = "/auth/login";

	// GET: hiển thị profile
	@GetMapping
	public String showProfile(HttpSession session, org.springframework.ui.Model model) {
		UserModel userModel = (UserModel) session.getAttribute("account");
		if (userModel == null) {
			return "redirect:" + LOGIN_PAGE;
		}

		model.addAttribute("user", userModel);
		return "admin/profile"; // view profile.jsp
	}

	// POST: lưu profile
	@PostMapping
	public String saveProfile(@RequestParam String fullName, @RequestParam String phone,
			@RequestParam(value = "images", required = false) MultipartFile imageFile,
			@RequestParam(value = "oldImage", required = false) String oldImage, HttpSession session)
			throws IOException {

		UserModel userModel = (UserModel) session.getAttribute("account");
		if (userModel == null) {
			return "redirect:" + LOGIN_PAGE;
		}

		// Cập nhật thông tin
		userModel.setFullName(fullName);
		userModel.setPhone(phone);

		String finalFileName = oldImage;

		if (imageFile != null && !imageFile.isEmpty()) {
			String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
			String ext = "";
			int dot = originalFilename.lastIndexOf(".");
			if (dot != -1)
				ext = originalFilename.substring(dot);

			finalFileName = System.currentTimeMillis() + ext;

			File uploadFolder = new File(uploadDir);
			if (!uploadFolder.exists())
				uploadFolder.mkdirs();

			File destFile = new File(uploadFolder, finalFileName);
			imageFile.transferTo(destFile);
		}

		userModel.setImages(finalFileName);

		// Chuyển UserModel -> User entity và lưu
		User userEntity = userService.findByName(userModel.getUserName()).orElse(new User());

		userEntity.setUserName(userModel.getUserName());
		userEntity.setFullName(userModel.getFullName());
		userEntity.setPhone(userModel.getPhone());
		userEntity.setImages(userModel.getImages());
		userEntity.setAdmin(true); // luôn là admin
		userEntity.setActive(true);

		//Giữ nguyên mật khẩu cũ
		userEntity.setPassword(userEntity.getPassword());

		userService.save(userEntity);

		// Cập nhật lại session
		session.setAttribute("account", userModel);

		// Quay lại chính trang profile
		return "redirect:/profile";
	}
}
