package team.ojt7.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team.ojt7.recruitment.model.dto.PositionDto;
import team.ojt7.recruitment.model.service.PositionService;



@Controller
public class PositionController {
	@Autowired
	private PositionService positionService;
	@RequestMapping(value = "/admin/position/add", method = RequestMethod.GET)
	public String addNewPosition(ModelMap model) {
		model.addAttribute("position",new PositionDto());		
		return "edit-position";
	}

	@GetMapping("/manager/position/search")
	public String searchPositions(@RequestParam(required = false) String keyword, ModelMap model) {
		List<PositionDto> list = positionService.search(keyword);
		model.addAttribute("list", list);
		return "positions";
	}

	@GetMapping("/admin/position/edit")
	public String editPosition(
			@RequestParam(required = false)
			Long id,
			ModelMap model
			) {
		PositionDto positionDto = positionService.findById(id).orElse(new PositionDto());
		model.put("position", positionDto);
		return "edit-position";
	}

	@PostMapping("/admin/position/save")
	public String savePosition(@ModelAttribute("position") PositionDto dto,BindingResult bs,ModelMap model) {
		if(bs.hasErrors()) {
			return "edit-position";
		}
		
		positionService.save(PositionDto.parse(dto));
		return "redirect:/manager/position/search";
	}

	@GetMapping("/admin/position/delete")
	public String deletePosition(@RequestParam("id") Long id) {
		
		positionService.deleteById(id);
		return "redirect:/manager/position/search";
	}


}