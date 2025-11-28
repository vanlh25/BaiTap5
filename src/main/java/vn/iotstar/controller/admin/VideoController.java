package vn.iotstar.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Video;
import vn.iotstar.model.UserModel;
import vn.iotstar.model.VideoModel;
import vn.iotstar.service.VideoService;

@Controller
@RequestMapping("/admin/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String LOGIN_PAGE = "/auth/login";

    // ===== LIST VIDEO WITH SEARCH =====
    @GetMapping("/list")
    public String listVideo(@RequestParam(value = "keyword", required = false) String keyword,
                            HttpSession session, Model model) {

        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        List<Video> videos;
        if (keyword != null && !keyword.isEmpty()) {
            videos = videoService.search(keyword);
        } else {
            videos = videoService.findAll();
        }

        model.addAttribute("videos", videos);
        model.addAttribute("keyword", keyword);
        return "admin/video/list-video";
    }

    // ===== ADD VIDEO FORM =====
    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        model.addAttribute("video", new VideoModel());
        return "admin/video/add-video";
    }

    // ===== SAVE VIDEO =====
    @PostMapping("/add")
    public String saveVideo(@ModelAttribute VideoModel videoModel) throws IOException {

        String finalFileName = videoModel.getPoster();
        MultipartFile file = videoModel.getPosterFile();

        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = originalFileName.lastIndexOf(".");
            if (dot != -1) ext = originalFileName.substring(dot);

            finalFileName = System.currentTimeMillis() + ext;

            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            File destFile = new File(uploadFolder, finalFileName);
            file.transferTo(destFile);
        }

        Video video = new Video();
        video.setTitle(videoModel.getTitle());
        video.setDescription(videoModel.getDescription());
        video.setViews(videoModel.getViews());
        video.setActive(videoModel.getActive() == 1);
        video.setPoster(finalFileName);
        // categoryId cần lấy Category entity nếu có, bỏ qua nếu không
        // video.setCategory(...);

        videoService.save(video);
        return "redirect:/admin/video/list";
    }

    // ===== EDIT VIDEO FORM =====
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int videoId, HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("account");
        if (user == null) return "redirect:" + LOGIN_PAGE;

        Video video = videoService.findById(videoId).orElse(null);
        if (video == null) return "redirect:/admin/video/list";

        VideoModel videoModel = new VideoModel();
        videoModel.setVideoId(video.getVideoId());
        videoModel.setTitle(video.getTitle());
        videoModel.setDescription(video.getDescription());
        videoModel.setViews(video.getViews());
        videoModel.setActive(video.getActive() ? 1 : 0);
        videoModel.setPoster(video.getPoster());

        model.addAttribute("video", videoModel);
        return "admin/video/edit-video";
    }

    // ===== UPDATE VIDEO =====
    @PostMapping("/edit")
    public String updateVideo(@ModelAttribute VideoModel videoModel) throws IOException {
        Video video = videoService.findById(videoModel.getVideoId()).orElse(null);
        if (video == null) return "redirect:/admin/video/list";

        video.setTitle(videoModel.getTitle());
        video.setDescription(videoModel.getDescription());
        video.setViews(videoModel.getViews());
        video.setActive(videoModel.getActive() == 1);

        MultipartFile file = videoModel.getPosterFile();
        String finalFileName = video.getPoster();

        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = originalFileName.lastIndexOf(".");
            if (dot != -1) ext = originalFileName.substring(dot);

            finalFileName = System.currentTimeMillis() + ext;

            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            File destFile = new File(uploadFolder, finalFileName);
            file.transferTo(destFile);
        }

        video.setPoster(finalFileName);
        videoService.save(video);

        return "redirect:/admin/video/list";
    }

    // ===== DELETE VIDEO =====
    @GetMapping("/delete/{id}")
    public String deleteVideo(@PathVariable("id") int videoId) {
        videoService.deleteById(videoId);
        return "redirect:/admin/video/list";
    }
}
