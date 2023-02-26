package team.ojt7.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.DepartmentDto;
import team.ojt7.recruitment.model.dto.DepartmentSearch;
import team.ojt7.recruitment.model.service.DepartmentService;
import team.ojt7.recruitment.model.service.exception.InvalidField;
import team.ojt7.recruitment.model.service.exception.InvalidFieldsException;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/department/search")
	public String searchDepartments(
			@ModelAttribute("departmentSearch")
			DepartmentSearch departmentSearch,
			ModelMap model) {
		Page<DepartmentDto> page = departmentService.search(departmentSearch);
		model.put("departmentPage", page);
		model.put("departmentSearch", departmentSearch);
		return "departments";
	}

	@GetMapping("/department/edit")
	public String editDepartment(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		DepartmentDto departmentDto = departmentService.findById(id).orElse(new DepartmentDto());
		model.put("department", departmentDto);
		String title = departmentDto.getId() == null ? "Add New Department" : "Edit Department";
		model.put("title", title);
		return "edit-department";
	}

	@PostMapping("/department/save")
	public String saveDepartment(@ModelAttribute("department") @Validated DepartmentDto dto,BindingResult bs,ModelMap model) {
		
		if (!bs.hasErrors()) {
			try {
				departmentService.save(DepartmentDto.parse(dto));
			} catch (InvalidFieldsException e) {
				for (InvalidField invalidField : e.getFields()) {
					bs.rejectValue(invalidField.getField(), invalidField.getCode(), invalidField.getMessage());
				}
			}
		}
		
		if(bs.hasErrors()) {
			String title = dto.getId() == null ? "Add New Department" : "Edit Department";
			model.put("title", title);
			return "edit-department";
		}
		
		return "redirect:/department/search";
	}

	@PostMapping("/department/delete")
	public String deleteDepartment(@RequestParam("id") Long id) {
		
		departmentService.deleteById(id);
		return "redirect:/department/search";
	}
}
