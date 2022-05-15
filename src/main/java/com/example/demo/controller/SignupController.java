package com.example.demo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Message;
import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.service.MessageService;
import com.example.demo.domain.service.SignupService;
import com.example.demo.exception.BusinessException;
import com.example.demo.form.SignupForm;
import com.example.demo.form.validator.SignupFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;
    private final MessageService messageService;
    private final SignupFormValidator validator;

    @InitBinder("signupForm")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/signup")
    public String getSignup(SignupForm form) {
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String signupConfirm(@Validated SignupForm form, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return getSignup(form);
        }
        signupService.createTmpUserAndSendMail(form);

        redirectAttributes.addFlashAttribute("mailAddress", form.getMailAddress());
        return "redirect:/signup/confirm";
    }

    @GetMapping("/signup/confirm")
    public String createTmpUserComplete() {
        return "signup/createTmpUserComplete";
    }

    @GetMapping("/signup/certificate")
    public String signupComplete(@RequestParam String token, RedirectAttributes redirectAttributes) {
        TmpUser tmpUser = null;
        try {
            tmpUser = signupService.findTmpUserByToken(token);
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }

        signupService.createUser(tmpUser.getMailAddress(), token);

        // ログアウト処理
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityContextHolder.clearContext();
        }

        // リダイレクトしログイン画面に遷移する。
        redirectAttributes.addFlashAttribute("msg", messageService.getMessage(Message.S001_SIGNUP_COMPLETE));
        return "redirect:/login";
    }
}
