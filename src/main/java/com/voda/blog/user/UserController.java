package com.voda.blog.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user_signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "user_signup";
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "비밀번호와 확인이 일치하지 않습니다");
            return "user_signup";
        }
        userService.create(userCreateForm.getUsername(), userCreateForm.getPassword1(), userCreateForm.getEmail(),
                userCreateForm.getNickname());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user_login";
    }
}
