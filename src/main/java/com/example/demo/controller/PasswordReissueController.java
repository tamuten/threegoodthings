package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Message;
import com.example.demo.domain.model.PasswordReissueInfo;
import com.example.demo.domain.service.MessageService;
import com.example.demo.domain.service.PasswordReissueService;
import com.example.demo.exception.BusinessException;
import com.example.demo.form.PasswordReissueForm;
import com.example.demo.form.PasswordResetForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reissue")
@RequiredArgsConstructor
public class PasswordReissueController {
    private final PasswordReissueService passwordReissueService;
    private final MessageService messageService;

    @GetMapping(value = "create", params = "form")
    public String showCreateReissueInfoForm(PasswordReissueForm form) {
        return "passwordreissue/createReissueInfoForm";
    }

    @PostMapping("/create")
    public String createReissueInfo(@Validated PasswordReissueForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showCreateReissueInfoForm(form);
        }

        try {
            passwordReissueService.createAndSendReissueInfo(form.getMailAddress());
        } catch (BusinessException e) {
            model.addAttribute("err", e.getMessage());
            return showCreateReissueInfoForm(form);
        }

        return "redirect:/reissue/create?complete";
    }

    @GetMapping(value = "create", params = "complete")
    public String createReissueInfoComplete() {
        return "passwordreissue/createReissueInfoComplete";
    }

    @GetMapping(value = "/resetpassword", params = "form")
    public String showPasswordResetForm(PasswordResetForm form, @RequestParam String token,
            RedirectAttributes redirectAttributes) {
        PasswordReissueInfo info = null;
        try {
            info = passwordReissueService.findByToken(token);
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("err", e.getMessage());
            return "redirect:/login";
        }

        form.setMailAddress(info.getMailAddress());
        form.setToken(token);
        return "passwordreissue/passwordResetForm";
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@Validated PasswordResetForm form, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "passwordreissue/passwordResetForm";
        }
        passwordReissueService.resetPassword(form.getMailAddress(), form.getToken(), form.getNewPassword());

        redirectAttributes.addFlashAttribute("msg", messageService.getMessage(Message.S004_UPDATE_PASSWORD_COMPLETE));
        return "redirect:/login";
    }

}
