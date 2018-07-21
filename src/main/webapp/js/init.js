(function($){
  $(function(){

    //$('.sidenav').sidenav();
    
		$('.dropdown-tyaa').click(function(){
			
		    $(this).find('.dropdown-content-tyaa').stop().slideToggle(400);
		});
		
		var localizeIndex = function (){
			
			preloaderOn("nocover");
			$.ajax({
				type: 'POST',
				url: '/language?action=dictionary',
				dataType: 'json',
				cache: false
			}).done(function(responseText, textStatus, jqXHR) {
				
				//console.log(responseText.result);
				var dict = dictionaryResponseToArray(responseText.result);
				console.log(dict);
				//Готовим шаблон при помощи библиотеки Hogan
			    var desktopNavTemplate = Hogan.compile(
		    		'<div class="nav-wrapper container">'
						+'<a id="logo-container" href="#" class="brand-logo">CTFinder</a>'
						+'<a href="#" data-target="nav-mobile" class="sidenav-trigger button-collapse"><i class="material-icons">menu</i></a>'
						+'<ul class="right hide-on-med-and-down">'
							+'<li><a href="#home" class="active">{{index_nav_home}}</a></li>'
							+'<li><a href="#find">{{index_nav_find}}</a></li>'
							+'<li><a href="#create" class="">{{index_nav_offer}}</a></li>'
							+'<li><a href="#about">{{index_nav_about}}</a></li>'
						+'</ul>'
					+'</div>'
			    );
			    //console.log(template);
			    //Заполняем шаблон данными и помещаем на веб-страницу
				$('nav').html(desktopNavTemplate.render(dict));
				
				var mobileNavTemplate = Hogan.compile(
					'<li><a href="#home" class="active">{{index_nav_home}}</a></li>'
					+'<li><a href="#find">{{index_nav_find}}</a></li>'
					+'<li><a href="#create" class="">{{index_nav_offer}}</a></li>'
					+'<li><a href="#about">{{index_nav_about}}</a></li>'
			    );
			    //console.log(template);
			    //Заполняем шаблон данными и помещаем на веб-страницу
				$('#nav-mobile').html(mobileNavTemplate.render(dict));
				
				$('.sidenav').sidenav();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				  
				alert("Ошибка: " + jqXHR);
			}).always(function() {
			    
				preloaderOff();
			});
		}
		
		var getLanguages = function (){
			
			$.ajax({
				type: 'POST',
				url: '/language?action=get-all',
				dataType: 'json',
				cache: false
			}).done(function(responseText, textStatus, jqXHR) {
				  
				//console.log(responseText);
				//Готовим шаблон списка при помощи библиотеки Hogan
			    var template = Hogan.compile(
			        '{{#result}}'
			        +'<li>'
						+'<a data-lang="{{code}}" class="btn-floating {{#active}}active{{/active}}">{{code}}</a>'
					+'</li>'
					+'{{/result}}'
			    );
			    //Заполняем шаблон данными и помещаем на веб-страницу
				$('#lang-selector > ul').html(template.render(responseText));
				$('.fixed-action-btn').floatingActionButton();
				
				$('#lang-selector > ul > li > a.btn-floating').unbind("click");
				$('#lang-selector > ul > li > a.btn-floating').click(function(ev){
					
					ev.preventDefault();
					preloaderOn("nocover");
	    			var langCode = $(this).data('lang');
	    			var setLangUrlString =
			        	"/language?action=set"
			        			+"&language=" + langCode;
	    			$.ajax({
			            url: setLangUrlString,
			            type: "POST",
			            cache : false
			        }).done(function(data) {
			        	
			        	if(data.result[0] == "set"){
			        		
			        		getLanguages();
			        	} else {
			        		alert('Ошибка выбора языка');
			        	}
			        }).fail(function(jqXHR, textStatus, errorThrown) {
						  
						alert("Ошибка: " + jqXHR);
					}).always(function() {
					    
						preloaderOff();
					});
				});
				
				localizeIndex();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				  
				alert("Ошибка: " + jqXHR);
			}).always(function() {
			    
				//preloaderOff();
			});
		}
		
		getLanguages();
		localizeIndex();
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
	  //console.log('Signed in as: ' + xhr.response.result[0].name);
	  //console.log('Signed in as: ' + xhr.response.result[0].email);
	  //console.log('Signed in as: ' + xhr.response.result[0].pictureUrl);
	  //
	  $("#username").text(xhr.response.result[0].name);
	  $("#useremail").text(xhr.response.result[0].email);
	  $("#userpicture").attr("src", xhr.response.result[0].pictureUrl);
	  
	  preloaderOff();
	};
	xhr.send('idtoken=' + id_token);
}

$('#signout-li').click(signOut);

function signOut() {
	
	preloaderOn();
	$(document.body).find("section#find, section#create").html("");
    var auth2 = gapi.auth2.getAuthInstance();
    
    auth2.signOut().then(function () {
    	
    	//$(document.body).find("section#find, section#create").html("");
    	preloaderOff();
    	
    	/*var xhr = new XMLHttpRequest();
    	xhr.open('POST', 'https://creativetfinder.appspot.com/auth');
    	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    	xhr.responseType = 'json';
    	xhr.onload = function() {
    	  //
    		$(document.body).find("section#find, section#create").html("");
    		preloaderOff();
    	};
    	xhr.send(null);*/
    });
}

$('div#g-signin2').click(function(){
	
	preloaderOn();
});