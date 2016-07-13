var mules = [];

// load the users json
$.getJSON("db/users.json", function (userdata) {
	// load the mules json
	$.getJSON( "db/mules.json", function( data ) {
	  $.each( data, function( key, val ) {
	  	mules[val.username] = val;
	  	$.extend(mules[val.username],userdata[val.username]);
	  });
	});
});



// click handler for Mule select button
$(".btn").click(function(event) {
  key = event.target.id;
  chrome.tabs.getSelected(null, function(tab) {
    chrome.tabs.sendMessage(tab.id, mules[key]);
   });
});