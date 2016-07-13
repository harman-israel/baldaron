
chrome.extension.onMessage.addListener(function(request, sender, sendResponse) {
	// Fill the form according to the mule selected
	$("#shipping-email input").val(request.email);
	$("#shipping-firstNameField input").val(request.firstname);
	$("#shipping-lastNameField input").val(request.lastname);
	$("#shipping-address1Field input").val(request.address1);
	$("#shipping-address2Field input").val(request.address2);
	$("#shipping-cityField input").val(request.city);
	$("#shipping-postalCodeField input").val(request.zip);
	$("#shipping-phoneField input").val(request.phone);
	$("#shipping-stateField select").find('option[value="'+request.state+'"]').attr("selected",true);
});