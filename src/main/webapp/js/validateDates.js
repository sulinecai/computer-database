function validateForm() {
	var result = true;
	var introduced = document.getElementById("introduced").value;
	var discontinued = document.getElementById("discontinued").value;

	if (introduced != "") {
		introduced = new Date(introduced);
		if (discontinued != "") {
			discontinued = new Date(discontinued);
			if (discontinued<introduced){
				result = false;
				alert("The discontinued date must be before introduced date.");
			}
		}
	} else if (discontinued != "") {
		result = false;
		alert("Impossible to set a discontinued date if the introduced date is not defined.");
	}
	return result;
}