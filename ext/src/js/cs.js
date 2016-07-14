//pushover data
var tokenKey = "akfh5nf698mm2b5qysay8vxx5bb88b";
var userKey = "u9utyrddiwb6iyqebqp8bdv8aqd3cc";
var postUrl = "https://api.pushover.net/1/messages.json";

var parcelsRef = new Firebase('https://baldaron-4adb5.firebaseio.com/parcels');





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
		// fill db
		var baseurl = "https://baldaron-4adb5.firebaseio.com/";
		var parcelData = {};
		parcelData["description"] = $(".product-name a").attr("title");
		parcelData["leecher"] = "sagiben";
		parcelData["mule"] = request.muleid;
		parcelData["mule_usernam"] = request.username;
		parcelData["purchase_date"] = new Date();
		parcelData["status"] = "Pending";
		parcelData["total_price"] = $(".regular-price-container span").text();
		parcelData["url"] = $(".product-image img").attr("src");
		parcelId = Math.floor(Math.random() * 99) + 1 ; 
		parcelStr = String.valueOf(parcelId);
		parcel = {};
		parcel[parcelId] = parcelData;
	    parcelsRef.update(parcel);

		// send notification
		message = request.firstname + ", someone requests a delivery";
		dataToSend = {"token":tokenKey,"user":userKey,"message":message};
		$.post(postUrl,dataToSend);
	});
	
});