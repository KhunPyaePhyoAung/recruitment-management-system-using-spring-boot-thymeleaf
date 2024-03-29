$(document).ready(function () {
	
	$(".select-search").each(function(i, t) {
		dselect(t, {
			search: true
		});
	});
	

	var form=document.getElementById("interviewForm");
	console.log(form);
	form.addEventListener("submit",function(event){
		validated(event)
	});

	$(".validated-input").on("input", function(event) {
		let labels = $(event.target.parentElement).find(".validated-label");
		labels.each((i, l) => {
			l.parentNode.removeChild(l);
		});
		if ($(this).val()) {
			validatedEach(event, $(this).attr("id"));
		}
	});

	$(".validated-input").on("change", function(event) {
		let labels = $(event.target.parentElement).find(".validated-label");
		labels.each((i, l) => {
			l.parentNode.removeChild(l);
		});
		console.log($(this).attr("id"));
		if ($(this).val()) {
			validatedEach(event, $(this).attr("id"));
		}
	});
});

var constraints={
	code:{
		presence: {message:"^Enter code"},
		format:{
			 pattern : /^\S*$/,
             message : "^Code cannot contain space"
		},
		length: {
			minimum: 6,
			maximum: 30,
			message: "^Code must be between 6 and 30 characters"
		}
	},
	interviewName:{
		presence: {message : "^Select interview name"},
	},
	applicant:{
		presence: {message : "^Select interview name"},

	},
	localDate:{
		presence : {message : "^Select date"}
	},
	localTime:{
		presence : {message : "^Select time"}
	}
}

const validated = (event) => {
	let form=document.getElementById("interviewForm");
	event.preventDefault();
	let values=validate.collectFormValues(form);
	let validation=validate(values,constraints);
	
	let codeError=document.createElement("label");
	codeError.id="codeError";
	codeError.classList.add("text-danger", "validated-label");
	
	let interviewNameError=document.createElement("label");
	interviewNameError.id="interviewNameError";
	interviewNameError.classList.add("text-danger", "validated-label");
	
	let applicantError=document.createElement("label");
	applicantError.id="applicantError";
	applicantError.classList.add("text-danger", "validated-label");
	
	let dateError=document.createElement("label");
	dateError.id="dateError";
	dateError.classList.add("text-danger", "validated-label");
	
	let timeError=document.createElement("label");
	timeError.id="timeError";
	timeError.classList.add("text-danger", "validated-label");
	if(validation){
		console.log(validation);
	 checkValidation(codeError, "codeError", "code", validation.code);
	checkValidation(interviewNameError, "interviewNameError","interviewNameHelp", validation.interviewName);
	checkValidation(applicantError, "applicantError","applicantHelp", validation.applicant);
	checkValidation(dateError, "dateError", "date", validation.localDate);
	checkValidation(timeError, "timeError", "time", validation.localTime);
	}
	else{
		form.submit();
	}
}

const validatedEach = (event, inputId) => {
	let form=document.getElementById("interviewForm");
	event.preventDefault();
	let values=validate.collectFormValues(form);
	let validation=validate(values,constraints);
	
	let codeError=document.createElement("label");
	codeError.id="codeError";
	codeError.classList.add("text-danger", "validated-label");
	
	let interviewNameError=document.createElement("label");
	interviewNameError.id="interviewNameError";
	interviewNameError.classList.add("text-danger", "validated-label");
	
	let applicantError=document.createElement("label");
	applicantError.id="applicantError";
	applicantError.classList.add("text-danger", "validated-label");
	
	let dateError=document.createElement("label");
	dateError.id="dateError";
	dateError.classList.add("text-danger", "validated-label");
	
	let timeError=document.createElement("label");
	timeError.id="timeError";
	timeError.classList.add("text-danger", "validated-label");
	if(validation){
		if (inputId == "code") {
			checkValidation(codeError, "codeError", "code", validation.code);
		} else if (inputId == "interviewName") {
			checkValidation(interviewNameError, "interviewNameError","interviewNameHelp", validation.interviewName);
		} else if (inputId == "applicant") {
			checkValidation(applicantError, "applicantError","applicantHelp", validation.applicant);
		} else if (inputId == "date") {
			checkValidation(dateError, "dateError", "date", validation.localDate);
		} else if (inputId == "time") {
			checkValidation(timeError, "timeError", "time", validation.localTime);
		}
	}
}

const setMinInterviewDate = () => {
    let createdDateTime = document.getElementById("createdDateTime").value;
    let minDate = new Date().toISOString().split("T")[0];
	let minTime =  new Date().getHours() + ":" + new Date().getMinutes();
	console.log(minTime);
    if (createdDateTime) {
        minDate = createdDateTime.split("T")[0];
		let time = createdDateTime.split("T")[1];
		minTime = time.split(":")[0] + ":" + time.split(":")[1];
    }
	console.log("Hello");
	console.log(minTime);
    let interviewDateInput = document.getElementById("date");
    interviewDateInput.min = minDate;
	let interviewTimeInput = document.getElementById("time");
    interviewTimeInput.min = minTime;

	$("#date").on('change', function(e) {
		if ($(this).val() == new Date().toISOString().split("T")[0]) {
			console.log("Heyy");
			let minTime =  new Date().getHours() + ":" + new Date().getMinutes();
			interviewTimeInput.min = minTime;
			if ($("#time").val() < minTime) {
				$("#time").val(minTime);
			  }
		} else {
			interviewTimeInput.min = "";
		}
	});

	$("#time").on("change", function(e) {
		if ($(this).val() < $(this).attr("min")) {
		  $(this).val($(this).attr("min"));
		} else if ($(this).val() > $(this).attr("max")) {
		  $(this).val($(this).attr("min"));
		}
	  });
}