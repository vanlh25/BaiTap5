package vn.iotstar.controller.admin;

import vn.iotstar.model.VideoModel;
import vn.iotstar.entity.Video;
import vn.iotstar.service.VideoService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    // ------------------- LIST -------------------
    @GetMapping("")
    public String list(ModelMap model) {
        List<Video> list = videoService.findAll(); // lấy entity trực tiếp
        model.addAttribute("videos", list);
        return "admin/videos/list";
    }

    // ------------------- ADD -------------------
    @GetMapping("/add")
    public String add(ModelMap model) {
        VideoModel videoModel = new VideoModel();
        videoModel.setIsEdit(false);
        model.addAttribute("video", videoModel);
        return "admin/videos/addOrEdit";
    }

    // ------------------- EDIT -------------------
    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") int id) {
        Optional<Video> optVideo = videoService.findById(id); // trả về Optional<Video>

        if (optVideo.isPresent()) {
            Video video = optVideo.get();
            VideoModel videoModel = new VideoModel();
            BeanUtils.copyProperties(video, videoModel);
            videoModel.setIsEdit(true);
            model.addAttribute("video", videoModel);
            return new ModelAndView("admin/videos/addOrEdit", model);
        }

        model.addAttribute("message", "Video does not exist!");
        return new ModelAndView("redirect:/admin/videos", model);
    }


    // ------------------- SAVE OR UPDATE -------------------
    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @ModelAttribute("video") VideoModel videoModel) {

        Video entity = new Video();
        BeanUtils.copyProperties(videoModel, entity); // map model → entity
        videoService.save(entity); // service làm việc với entity

        String message = videoModel.getIsEdit() ? "Video updated successfully!" : "Video saved successfully!";
        model.addAttribute("message", message);

        return new ModelAndView("redirect:/admin/videos");
    }

    // ------------------- DELETE -------------------
    @GetMapping("/delete/{id}")
    public ModelAndView delete(ModelMap model, @PathVariable("id") int id) {
        videoService.delete(id);
        model.addAttribute("message", "Video deleted successfully!");
        return new ModelAndView("redirect:/admin/videos", model);
    }
}
