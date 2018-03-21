(function($){
  $(function(){

    $('.button-collapse').sideNav();
    //$('.parallax').parallax();
  }); // end of document ready
})(jQuery); // end of jQuery name space

/*Google profile*/

/*var GoogleAuth; // Google Auth object

function init() {
  gapi.load('auth2', function() { 
	  
	  GoogleAuth = gapi.auth2.getAuthInstance();
	  GoogleAuth.isSignedIn.listen(updateSigninStatus);
  });
}

function updateSigninStatus(isSignedIn) {
	
  if (isSignedIn) {
    //isAuthorized = true;
    //if (currentApiRequest) {
      //sendAuthorizedApiRequest(currentApiRequest);
    //}
	  $('li#g-signin2, li#g-signin2-mobile').hide();
  } else {
    //isAuthorized = false;
	  $('li#g-signin2, li#g-signin2-mobile').show();
  }
}*/

function onSignIn(googleUser) {
	
	var id_token = googleUser.getAuthResponse().id_token;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'https://creativetfinder.appspot.com/auth');
	//xhr.open('POST', 'http://localhost:8080/auth');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.responseType = 'json';
	xhr.onload = function() {
	  console.log('Signed in as: ' + xhr.response);
	  //$('g-signin2, g-signin2-mobile').html('Exit(' + xhr.responseText + ')');
	  //console.log($('li#g-signin2, li#g-signin2-mobile'));
	  //$('li#g-signin2, li#g-signin2-mobile').hide();
	};
	xhr.send('idtoken=' + id_token);
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
      //$('li#g-signin2, li#g-signin2-mobile').show();
    });
  }
