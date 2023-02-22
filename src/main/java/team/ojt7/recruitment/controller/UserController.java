package team.ojt7.recruitment.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.AdminChangePasswordFormDto;
import team.ojt7.recruitment.model.dto.UserChangePasswordFormDto;
import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.service.UserService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;
import team.ojt7.recruitment.model.validator.AdminChangePasswordFormValidator;
import team.ojt7.recruitment.model.validator.DefaultValidationGroupOrder;
import team.ojt7.recruitment.model.validator.UserChangePasswordFormValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminChangePasswordFormValidator adminChangePasswordFormValidator;
	
	@Autowired
	private UserChangePasswordFormValidator userChangePasswordFormValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		var target = binder.getTarget();
		if (target != null) {
			if (adminChangePasswordFormValidator.supports(target.getClass())) {
				binder.addValidators(adminChangePasswordFormValidator);
			}
			
			if (userChangePasswordFormValidator.supports(target.getClass())) {
				binder.addValidators(userChangePasswordFormValidator);
			}
			
		}
	}

	@RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
	public String addNewUser(ModelMap model) {
		model.addAttribute("user", userService.generateNewWithCode());		
		return "adduser";
	}

	@RequestMapping(value = "/admin/user/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam("id")Long id, ModelMap model) {
		model.put("user", userService.findById(id).get());
		return "edituser";

	}

	@RequestMapping(value="/admin/user/save",method=RequestMethod.POST)
	public String saveUser(
			@Validated
			@ModelAttribute("user")
			UserDto dto,
			BindingResult bs,
			ModelMap model) {
		
		if(!dto.getPassword().equals(dto.getConfirmPassword())) {
			bs.rejectValue("confirmPassword", "notSame", "Passwords are not the same");
		}
		
		if (!bs.hasErrors()) {
			try {
				User user = UserDto.parse(dto);
				userService.save(user);
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			return dto.getId() == null ? "adduser" : "edituser";
		}
		
		return "redirect:/admin/user/search";
	}

	@RequestMapping(value = "admin/user/detail", method = RequestMethod.GET)
	public String showUserDetail(@RequestParam("id") Long id,ModelMap model) {
		Optional<UserDto> user=userService.findById(id);
		if (user.isPresent()) {
			
			model.addAttribute("list",user.get());
			
		}
		
		return "userdetail";

	}

	@RequestMapping(value = "/admin/user/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("id")Long id) {
		userService.deleteById(id);
		return "redirect:/admin/user/search";
	}

	@RequestMapping(value = "/admin/user/search", method = RequestMethod.GET)
	public String searchUsers(
			@RequestParam(required=false)
			String keyword,
			ModelMap model) {
		List<UserDto> list=userService.search(keyword,null);
		model.addAttribute("list",list);
		return "users";

	}
	
	@GetMapping("/admin/user/password/change")
	public String showAdminChangePasswordPage(
			@RequestParam Long id,
			ModelMap model) {
		AdminChangePasswordFormDto form = new AdminChangePasswordFormDto();
		form.setUserId(id);
		model.put("passwordForm", form);
		return "change-user-password";
	}
	
	@GetMapping("/user/password/change")
	public String showUserChangePasswordPage(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		UserChangePasswordFormDto form = new UserChangePasswordFormDto();
		form.setUserId(loginUser.getId());
		model.put("passwordForm", form);
		return "change-password";
	}
	
	@PostMapping("/admin/user/password/save")
	public String savePassword(
			@Validated
			@ModelAttribute("passwordForm")
			AdminChangePasswordFormDto passwordForm,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			return "change-user-password";
		}
		
		userService.changePassword(passwordForm.getUserId(), passwordForm.getPassword());
		return "redirect:/admin/user/search";
	}
	
	@PostMapping("/user/password/save")
	public String savePassword(
			@Validated
			@ModelAttribute("passwordForm")
			UserChangePasswordFormDto passwordForm,
			BindingResult bindingResult
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				userService.changePassword(passwordForm.getUserId(), passwordForm.getOldPassword(), passwordForm.getNewPassword());
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "change-password";
		}
		
		return "redirect:/user/profile";
	}
	
	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String showUserprofile(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		model.put("user", UserDto.of(loginUser));
		return "userprofile";

	}
	
	@PostMapping("/user/profile/save")
	public String saveProfile(
			@Validated
			@ModelAttribute("user")
			UserDto user,
			BindingResult bindingResult
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				userService.save(UserDto.parse(user));
			}  catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bindingResult.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "editprofile";
		}
		
		return "redirect:/user/profile";
	}
	
	@RequestMapping(value = "/user/profile/edit", method = RequestMethod.GET)
	public String Editprofile(ModelMap model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		model.put("user", UserDto.of(loginUser));
		return "editprofile";

	}

}