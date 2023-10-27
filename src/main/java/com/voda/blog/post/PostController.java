package com.voda.blog.post;

import com.voda.blog.user.SiteUser;
import com.voda.blog.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("paging", postService.getList(page));
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "post_list";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(PostForm postForm, Model model, Principal principal) {
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "post_createForm";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@Valid PostForm postForm, BindingResult bindingResult, Model model, Principal principal) {
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        if (bindingResult.hasErrors()) return "post_createForm";
        SiteUser user = userService.getByUsername(principal.getName());
        postService.create(postForm.getSubject(), postForm.getContent(), user);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, Principal principal) {
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Model model, Principal principal, PostForm postForm) {
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        Post post = postService.getById(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) throw new RuntimeException("권한이 없습니다");
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        return "post_createForm";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Model model, Principal principal, @Valid PostForm postForm,
                         BindingResult bindingResult) {
        if (principal != null) model.addAttribute("user", userService.getByUsername(principal.getName()));
        if (bindingResult.hasErrors()) return "post_createForm";
        Post post = postService.getById(id);
        postService.modify(post, postForm.getSubject(), postForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id, Principal principal) {
        Post post = postService.getById(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) throw new RuntimeException("권한이 없습니다");
        postService.delete(post);
        return "redirect:/";
    }
}
