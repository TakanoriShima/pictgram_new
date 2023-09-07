package com.example.pictgram.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {

	private MessageSource messageSource;

	private SessionsController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/login")
	public String index() {
		return "sessions/new";
	}

	@GetMapping("/login-failure")
	//    public String loginFailure(Model model) {
	public String loginFailure(Model model, Locale locale) {
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-danger");
		//		model.addAttribute("message", "Emailまたはパスワードに誤りがあります。");
		model.addAttribute("message", messageSource.getMessage("sessions.loginFailure.flash", new String[] {}, locale));

		return "sessions/new";
	}

	@GetMapping("/logout-complete")
	//	public String logoutComplete(Model model) {
	public String logoutComplete(Model model, Locale locale) {
		System.out.println("ログアウトだよ");
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-info");
//		model.addAttribute("message", "ログアウトしました。");
		model.addAttribute("message", messageSource.getMessage("sessions.logoutComplete.flash", new String[] {}, locale));

		return "layouts/complete";
	}
}