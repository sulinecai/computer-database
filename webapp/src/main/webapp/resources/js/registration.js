jQuery('.registrationForm').validate({
    rules: {
        username: {
            required: true,
        },
        password: {
            required: true,
            minlength: 5
        },
        confirm: {
            required: true,
            minlength: 5,
            equalTo: "#password"
        }
    }
}); 