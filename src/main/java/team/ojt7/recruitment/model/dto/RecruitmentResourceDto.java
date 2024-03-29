package team.ojt7.recruitment.model.dto;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import team.ojt7.recruitment.model.entity.DirectRecruitmentResource;
import team.ojt7.recruitment.model.entity.ExternalRecruitmentResource;
import team.ojt7.recruitment.model.entity.RecruitmentResource;

public class RecruitmentResourceDto {

	protected Long id;
	
	@NotBlank(message = "{notBlank.recruitmentResource.code}")
	protected String code;
	
	@NotBlank(message = "{notBlank.recruitmentResource.name}")
	protected String name;
	
	protected boolean isDeleted;
	
	public static RecruitmentResourceDto of(RecruitmentResource rr) {
		
		if (rr == null) {
			return null;
		}
		
		if (rr instanceof ExternalRecruitmentResource err) {
			ExternalRecruitmentResourceDto dto = new ExternalRecruitmentResourceDto();
			dto.setId(err.getId());
			dto.setCode(err.getCode());
			dto.setName(err.getName());
			dto.setPic(err.getPic());
			dto.setContactPerson(err.getContactPerson());
			dto.setEmail(err.getEmail());
			dto.setPhone(err.getPhone());
			dto.setType(err.getType());
			dto.setDeleted(err.isDeleted());
			dto.setWebsiteLink(err.getWebsiteLink());
			dto.setAddress(err.getAddress());
			return dto;
		}else if (rr instanceof DirectRecruitmentResource drr) {
			DirectRecruitmentResourceDto dto = new DirectRecruitmentResourceDto();
			dto.setId(drr.getId());
			dto.setCode(drr.getCode());
			dto.setName(drr.getName());
			dto.setDeleted(drr.isDeleted());
			return dto;
		} else {
			RecruitmentResourceDto dto = new RecruitmentResourceDto();
			dto.setId(rr.getId());
			dto.setCode(rr.getCode());
			dto.setName(rr.getName());
			dto.setDeleted(rr.isDeleted());
			return dto;
		}
	}
	
	public static List<RecruitmentResourceDto> ofList(List<? extends RecruitmentResource> rrs) {
		return rrs.stream().map(r -> of(r)).toList();
	}
	
	public static RecruitmentResource parse(RecruitmentResourceDto dto) {
		if (dto == null) {
			return null;
		}
		
		if (dto instanceof ExternalRecruitmentResourceDto errDto) {
			ExternalRecruitmentResource err = new ExternalRecruitmentResource();
			err.setId(errDto.getId());
			err.setCode(errDto.getCode());
			err.setName(errDto.getName());
			err.setPic(errDto.getPic());
			err.setContactPerson(errDto.getContactPerson());
			err.setEmail(errDto.getEmail());
			err.setPhone(errDto.getPhone());
			err.setType(errDto.getType());
			err.setDeleted(errDto.isDeleted);
			err.setWebsiteLink(errDto.getWebsiteLink());
			err.setAddress(errDto.getAddress());
			return err;
		} else if (dto instanceof DirectRecruitmentResourceDto drrDto) {
			DirectRecruitmentResource drr = new DirectRecruitmentResource();
			drr.setId(drrDto.getId());
			drr.setCode(drrDto.getCode());
			drr.setName(drrDto.getName());
			drr.setDeleted(drrDto.isDeleted);
			return drr;
		} else {
			RecruitmentResource rr = new RecruitmentResource();
			rr.setId(dto.getId());
			rr.setCode(dto.getCode());
			rr.setName(dto.getName());
			rr.setDeleted(dto.isDeleted);
			return rr;
		}
	}
	
	public static List<RecruitmentResource> parseList(List<RecruitmentResourceDto> dtos) {
		return dtos.stream().map(d -> parse(d)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, id, isDeleted, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecruitmentResourceDto other = (RecruitmentResourceDto) obj;
		return Objects.equals(code, other.code) && Objects.equals(id, other.id) && isDeleted == other.isDeleted
				&& Objects.equals(name, other.name);
	}

}
