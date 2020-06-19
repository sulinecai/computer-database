function validateForm() {
	var result = true;
	var introduced = document.getElementById("introduced").value;
	var discontinued = document.getElementById("discontinued").value;
	
	var dateMin = new Date("1970-01-01");
	var dateMax = new Date("2038-01-19");

	if (introduced != "") {
		introduced = new Date(introduced);
		if (isADate(introduced)){
			if (introduced < dateMin || introduced > dateMax){
				result = false;
				alert("The introduced date must be between 01/01/1970 and 01/19/2038.");
			}
			else if (discontinued != "") {
				discontinued = new Date(discontinued);
				if (isADate(discontinued)){
					if (discontinued < dateMin || discontinued > dateMax){
						result = false;
						alert("The discontinued date must be between 01/01/1970 and 01/19/2038.");
					} else if (discontinued<introduced){
						result = false;
						alert("The discontinued date must be before introduced date.");
					}
				} else {
					result = false;
					alert("The discontinued date format is invalid");
				}
			}
		} else {
			result = false;
			alert("The introduced date format is invalid");
		}
	} else if (discontinued != "") {
		result = false;
		alert("Impossible to set a discontinued date if the introduced date is not defined.");
	}
	return result;
}

function isADate(date) {
	return date instanceof Date && !isNaN(date.getTime())
}