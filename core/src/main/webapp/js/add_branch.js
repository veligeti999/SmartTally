function init() {
    $.ajax({
        type: "GET",
        url: "/new-tally/rest/merchants",
        dataType: 'json',
        async: false,
        success: function(result) {
            document.getElementById("merchant-name").innerHTML = result.response_data.name;
            document.getElementById("owner-name").innerHTML = result.response_data.ownerName;
        },
         error: function(error) {
          timeoutSession(error);
        }
    });
}
init();

var addBranchLoader = document.getElementById('addBranchLoader');
var addBranchSubmit = document.getElementById('addBranchSubmit');
addBranchLoader.style.display = 'none';

function cancelAdd(){
    window.location.href = "branches.html";
}

$(function() {
    // Initialize form validation on the registration form.
    // It has the name attribute "registration"
    $("form[name='registerBranch']").validate({
        // Specify validation rules
        rules: {
            // The key name on the left side is the name attribute
            // of an input field. Validation rules are defined
            // on the right side
            name: "required",
            managerName: "required",
            email: {
                required: true,
                // Specify that email should be validated
                // by the built-in "email" rule
                email: true
            },
            password: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            },
            phone: {
                required: true,
                digits: true,
                minlength: 10,
                maxlength: 11
            },
            address: "required",
            city: "required",
            state: "required",
            country: "required",
            zip: "required"
        },
        // Specify validation error messages
        /*messages: {
          name: "Please enter your Merchant Name",
          ownerName: "Please enter your Owner Name",
          password: {
            required: "Please provide a password",
            minlength: "Your password must be at least 5 characters long"
          },
          email: "Please enter a valid email address"
        },*/
        // Make sure the form is submitted to the destination defined
        // in the "action" attribute of the form when valid
        submitHandler: function(form) {
            addBranchSubmit.setAttribute('disabled', true);
            addBranchLoader.style.display = 'block';
            var postJson = {};
            postJson.name = document.getElementById('name').value;
            postJson.password = document.getElementById('password').value;
            postJson.manager_name = document.getElementById('manager-name').value;
            postJson.phone = document.getElementById('phone').value;
            postJson.email = document.getElementById('email').value;
            var address = {};
            address.address = document.getElementById('address').value;
            address.city = document.getElementById('city').value;
            address.state = document.getElementById('state').value;
            address.country = document.getElementById('country').value;
            address.zip = document.getElementById('zip').value;
            postJson.address = address;
            $.ajax({
                type: "POST",
                url: "/new-tally/rest/merchants/branch",
                dataType: 'json',
                async: false,
                data: JSON.stringify(postJson),
                headers: {
                    "Content-Type": "application/json"
                },
                success: function(result) {
                    if(result.response_code == 0) {
                        toastr.success(result.response_message, "SUCCESS");
                        setTimeout(function() {
                            window.location.href = "branches.html";
                        }, 1000);
                    } else {
                        $('#addBranchSubmit').removeAttr('disabled');
                        addBranchLoader.style.display = 'none';
                        toastr.error(result.response_message, "ERROR");
                    }
                },
                error: function(error) {
                    $('#addBranchSubmit').removeAttr('disabled');
                    addBranchLoader.style.display = 'none';
                    timeoutSession(error);
                }
            });
        }
    });
});
