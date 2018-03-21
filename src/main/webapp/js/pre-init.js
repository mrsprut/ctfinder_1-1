var GoogleAuth; // Google Auth object

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
}