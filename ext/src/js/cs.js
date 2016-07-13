//pushover data
var tokenKey = "akfh5nf698mm2b5qysay8vxx5bb88b";
var userKey = "u9utyrddiwb6iyqebqp8bdv8aqd3cc";
var postUrl = "https://api.pushover.net/1/messages.json";



chrome.extension.onMessage.addListener(function(request, sender, sendResponse) {
	// Fill the form according to the mule selected
	$("#shipping-email input").val(request.email).select();
	$("#shipping-firstNameField input").val(request.firstname).select()	;
	$("#shipping-lastNameField input").val(request.lastname);
	$("#shipping-address1Field input").val(request.address1);
	$("#shipping-address2Field input").val(request.address2);
	$("#shipping-cityField input").val(request.city);
	$("#shipping-postalCodeField input").val(request.zip);
	$("#shipping-phoneField input").val(request.mulephone);
	$("#shipping-stateField select").find('option[value="'+request.state+'"]').attr("selected",true);

	$(".continue-button").click(function(evt) {
		message = request.firstname + ", someone requests a delivery";
		dataToSend = {"token":tokenKey,"user":userKey,"message":message};
		$.post(postUrl,dataToSend);
	});
	
});