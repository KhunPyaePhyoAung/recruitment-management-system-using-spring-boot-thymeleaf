$(document).ready(function () {

    let alert = document.getElementById("alert");
    if(alert) {
        let modelElement=document.getElementById("alert");
        modelElement.style.display = "block";
        window.onclick = function(event) {
            if (event.target == modelElement) {
                modelElement.style.display = "none";
            }
        }
        window.setTimeout(closeAlert, 5000);
    }

    pareDateFromTo("dateFrom", "dateTo");

    var form = document.getElementById("changeStatusForm");
    form.addEventListener("submit", function(event) {
        validated(event);
        $(this).attr("id")
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

});

var constraints = {
    remark: {
        length: {
            maximum: 255,
            message: "^Remark must be maximum 255 characters"
        }
    }
};

const validated = (event) => {
    let form = document.getElementById("changeStatusForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let remarkError = document.createElement("label");
    remarkError.id = "remarkError";
    remarkError.classList.add("text-danger", "validated-label");

    if (validation) {
        checkValidation(remarkError, "remarkError", "remark", validation.remark);
    } else {
        form.submit();
    }
}

const validatedEach = (event, inputId) => {
    let form = document.getElementById("changeStatusForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let remarkError = document.createElement("label");
    remarkError.id = "remarkError";
    remarkError.classList.add("text-danger", "validated-label");

    if (validation) {
        if (inputId == "remark") {
            checkValidation(remarkError, "remarkError", "remark", validation.remark);
        }
    }
}

function showChangeStatusDialog(id) {

    $.ajax({
        type: "GET",
        url: "/applicant/requirePositionDetail/status/change/api?id=" + id,
        dataType: "json",
        success: function (data) {
            console.log(data);
            
            let form = $("#changeStatusForm");
            let applicantIdInput = form.find("input[name='applicantId']");
            applicantIdInput.val(data.applicantId);
            

            let statusSelect = form.find("select[name='status']");
            statusSelect.val(data.status);
            statusSelect.prop("disabled", !data.updatable);

            let remarkTextarea = form.find("textarea[name='remark']");
            remarkTextarea.val("");
            remarkTextarea.prop("disabled", !data.updatable);

            let modelElement=document.getElementById("changeStatusModal");
            modelElement.style.display = "block";
            window.onclick = function(event) {
                if (event.target == modelElement) {
                    modelElement.style.display = "none";
                }
            }

            let selectedStatus = data.status;
            let btnSubmit = $('#btnStatusSubmit');
            btnSubmit.prop("disabled", !data.updatable);
            btnSubmit.addClass("disabled");
            $('#status').on('change', function(event) {
                let changeValue = this.value;
                if (changeValue == selectedStatus) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }
            });
        },
        error: function (e) {
            console.log('error');
            console.log(e);
        }
    });
}