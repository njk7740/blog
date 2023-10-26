package com.voda.blog.post;

import com.voda.blog.user.SiteUser;
import com.voda.blog.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, Principal principal) {
        model.addAttribute("postList", postService.getList());
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "post_list";
    }

    @GetMapping("/create")
    public String create(PostForm postForm) {
        return "post_createForm";
    }

    @PostMapping("/create")
    public String create(@Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "post_createForm";
        postService.create(postForm.getSubject(), postForm.getContent());
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        return "post_detail";
    }
}
