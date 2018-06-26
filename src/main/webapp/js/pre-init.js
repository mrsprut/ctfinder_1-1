var GoogleAuth; // Google Auth object

function preloaderOn() {
	
	$("#cover").css("display", "block");
	$(".preloader-wrapper").css("display", "block");
}

function preloaderOff() {
	
	$("#cover").css("display", "none");
	$(".preloader-wrapper").css("display", "none");
}

function fixedEncodeURI (str) {
	
    return encodeURI(str).replace(/%5B/g, '[').replace(/%5D/g, ']');
}

function init() {
	
	preloaderOn();
	gapi.load('auth2', function() {

		GoogleAuth = gapi.auth2.getAuthInstance();
		GoogleAuth.isSignedIn.listen(checkSigninStatus);
		var isSignedIn = GoogleAuth.isSignedIn.get();
		//console.log('isSignedIn: ' + isSignedIn);
		checkSigninStatus(isSignedIn);
	});
}

/*document.getElementById("g-signin2")
	.addEventListener(
			"click"
			, function(){
				
				preloaderOn();
		});*/
/*$('div#g-signin2').click(function(){
	
	preloaderOn();
});*/

function checkSigninStatus(isSignedIn) {

	if (isSignedIn) {

		//hide login button
		$('div#g-signin2').hide();
		//show logout button
		$('#signout').show();
	} else {

		$('div#g-signin2').show();
		//hide logout button
		$('#signout').hide();

		//console.log('User signed out.');

		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'https://creativetfinder.appspot.com/auth');
		//xhr.open('POST', 'http://localhost:8080/auth');
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xhr.responseType = 'json';

		xhr.onload = function() {

			//console.log('Action: ' + xhr.response.result[0]);

			$("#username").text("");
			$("#useremail").text("");
			$("#userpicture").attr("src", "img/hourglass.jpeg");
			
			//GoogleAuth.disconnect();
		};
		xhr.send(null);

		//TODO полное отсоединение от профиля
	}
	preloaderOff();
}