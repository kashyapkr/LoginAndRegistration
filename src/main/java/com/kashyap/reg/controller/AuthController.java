package com.kashyap.reg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kashyap.reg.dto.UserDto;
import com.kashyap.reg.entity.User;
import com.kashyap.reg.service.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthController {
	public AuthController(UserService service) {
		super();
		this.service = service;
	}

	private UserService service;
	
	@GetMapping("/index")
	public String home() {
		return "index";
	}
	
//	handler method to handle login request
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		UserDto userdto = new UserDto();
		model.addAttribute("user", userdto);
		return "register";
	}
	
	@PostMapping("/register/save")
	public String saveRegister(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,Model model) {
		User existingUser  = service.findUserByEmail(userDto.getEmail());
		
		if(existingUser!=null && existingUser.getEmail()!=null && !existingUser.getEmail().isBlank()) {
			bindingResult.rejectValue("email", null, "A user with this account already exists");
		}
		if(bindingResult.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/register";		}
		service.saveUser(userDto);
		return "redirect:/register?success";
		
	}
	
	@GetMapping("/users")
	public String users(Model model) {
		List<UserDto> users = service.findAll();
		model.addAttribute("users",users);
		return "users";
		
		
	}

}
