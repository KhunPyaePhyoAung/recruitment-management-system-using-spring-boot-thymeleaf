package team.ojt7.recruitment.model.dto;

import java.util.HashMap;
import java.util.Map;

public class HomeDto {

	private long jobOpeningsCount;
	private long jobExpiringsCount;
	private long notStartedInterviewsCount;
	private long newApplicantsCount;
	private Map<Integer, Integer> newInterviewsCountByDate;
	private Map<Integer, Integer> pastInterviewsCountByDate;
	private Map<RecruitmentResourceDto, Long> topRecruitmentResources = new HashMap<>();

	public long getJobOpeningsCount() {
		return jobOpeningsCount;
	}

	public void setJobOpeningsCount(long jobOpeningsCount) {
		this.jobOpeningsCount = jobOpeningsCount;
	}

	public long getJobExpiringsCount() {
		return jobExpiringsCount;
	}

	public void setJobExpiringsCount(long jobExpiringsCount) {
		this.jobExpiringsCount = jobExpiringsCount;
	}

	public long getNotStartedInterviewsCount() {
		return notStartedInterviewsCount;
	}

	public void setNotStartedInterviewsCount(long notStartedInterviewsCount) {
		this.notStartedInterviewsCount = notStartedInterviewsCount;
	}

	public long getNewApplicantsCount() {
		return newApplicantsCount;
	}

	public void setNewApplicantsCount(long newApplicantsCount) {
		this.newApplicantsCount = newApplicantsCount;
	}

	public Map<Integer, Integer> getNewInterviewsCountByDate() {
		return newInterviewsCountByDate;
	}

	public void setNewInterviewsCountByDate(Map<Integer, Integer> newInterviewsCountByDate) {
		this.newInterviewsCountByDate = newInterviewsCountByDate;
	}

	public Map<Integer, Integer> getPastInterviewsCountByDate() {
		return pastInterviewsCountByDate;
	}

	public void setPastInterviewsCountByDate(Map<Integer, Integer> pastInterviewCountByDate) {
		this.pastInterviewsCountByDate = pastInterviewCountByDate;
	}

	public Map<RecruitmentResourceDto, Long> getTopRecruitmentResources() {
		return topRecruitmentResources;
	}

	public void setTopRecruitmentResources(Map<RecruitmentResourceDto, Long> topRecruitmentResources) {
		this.topRecruitmentResources = topRecruitmentResources;
	}

}
