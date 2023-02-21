package team.ojt7.recruitment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.ApplicantDto;
import team.ojt7.recruitment.model.dto.ApplicantSearch;
import team.ojt7.recruitment.model.entity.Applicant;
import team.ojt7.recruitment.model.service.ApplicantService;

@Controller
public class ApplicantController {
	
	@Autowired
	ApplicantService applicantService;
	

	@RequestMapping(value = "/hr/applicant/edit", method = RequestMethod.GET)
	public String addNewApplicant(@RequestParam(required = false)
	Long id,
	ModelMap model) {
		ApplicantDto applicantDto = applicantService.findById(id).orElse(applicantService.generateNewWithCode());
		

		if (applicantDto.getId() != null) {
	
		}

		
		model.put("applicant", applicantDto);
		return "edit-applicant";
	}

	@RequestMapping(value="/hr/applicant/save",method=RequestMethod.POST)
	public String saveApplicant(
			@Validated
			@ModelAttribute("applicant")
			ApplicantDto applicantDto,
			BindingResult bindingResult,
			ModelMap model) {
		Applicant applicant = ApplicantDto.parse(applicantDto);
		applicantService.save(applicant);
		return "redirect:/manager/applicant/search";
		
	}

	@RequestMapping(value = "/hr/applicant/delete", method = RequestMethod.GET)
	public String deleteApplicant(@RequestParam("id")Long id) {
		applicantService.deleteById(id);
		return "redirect:/hr/applicant/search";
	}

	@RequestMapping(value = "/manager/applicant/search", method = RequestMethod.GET)
	public String searchApplicant(@ModelAttribute("applicantSearch")
	ApplicantSearch applicantSearch,
	ModelMap model) {
		Page<ApplicantDto> applicantPage = applicantService.search(applicantSearch);
		model.put("applicantSearch", applicantSearch);
		model.put("applicantPage", applicantPage);
		return "applicants";
	}

}