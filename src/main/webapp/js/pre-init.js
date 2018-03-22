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
	  //hide login button
	  $('li#g-signin2, li#g-signin2-mobile').hide();
	  //show logout button
	  $('li#signout-li, li#signout-li-mobile').show();
  } else {
    //isAuthorized = false;
	//show login button
	  $('li#g-signin2, li#g-signin2-mobile').show();
	//hide logout button
	  $('li#signout-li, li#signout-li-mobile').hide();
	  
	  console.log('User signed out.');
      
    	var xhr = new XMLHttpRequest();
		xhr.open('POST', 'https://creativetfinder.appspot.com/auth');
		//xhr.open('POST', 'http://localhost:8080/auth');
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhr.responseType = 'json';
		
		xhr.onload = function() {
			
		  console.log('Action: ' + xhr.response.result[0]);
		  
		  //$("#username-li").text("");
		  //$("#useremail-li").text("");
		  //$("#userimage-img").attr("src", "");
		  //$("#userimage-img-mobile").attr("src", "");
		};
		xhr.send(null);
		
		//TODO полное отсоединение от профиля
  }
}