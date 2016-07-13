var baseurl = "https://baldaron-4adb5.firebaseio.com/";
var mules = {};

// load the users json
$.getJSON(baseurl+"users.json", function (userdata) {
	// load the mules json
	$.getJSON(baseurl+"mules.json", function( data ) {
	  $.each( data, function( key, val ) {
	  	mules[val.username] = val;
	  	$.extend(mules[val.username],userdata[val.username]);
	  	// populate the mules list

		mulesArr = {"mules" : _.toArray(mules)};
		var output = Mustache.render("{{#mules}} <tr><td><img src={{pic_url}} class='img-responsive'  style='max-height: 80px;'></td><td>{{firstname}}</td><td>{{lastname}}</td><td><button type='button' id='{{username}}' class='btn btn-lg btn-success'>Select</button></td></tr>{{/mules}}", mulesArr);
		$("#content").html(output);	

		// click handler for Mule select button
		$(".btn").click(function(event) {
		  key = event.target.id;
		  chrome.tabs.getSelected(null, function(tab) {
		    chrome.tabs.sendMessage(tab.id, mules[key]);
		   });
		});
	  });
	});
});


