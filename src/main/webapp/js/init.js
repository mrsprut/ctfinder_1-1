(function($){
  $(function(){

    $('.button-collapse').sideNav();
    //$('.parallax').parallax();
  }); // end of document ready
})(jQuery); // end of jQuery name space

/*Google profile*/

function onSignIn(googleUser) {
	
	var id_token = googleUser.getAuthResponse().id_token;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'https://creativetfinder.appspot.com/auth');
	//xhr.open('POST', 'http://localhost:8080/auth');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
	  console.log('Signed in as: ' + xhr.responseText);
	  //$('g-signin2, g-signin2-mobile').html('Exit(' + xhr.responseText + ')');
	};
	xhr.send('idtoken=' + id_token);
}