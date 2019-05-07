//Получение ключа текущего раздела сайта
var getCurrentPageName = function(){
	
	var hash = location.hash || "#home";
    //if(hash.indexOf("info") === -1){
	var re = /#([-0-9A-Za-z]+)(\:(.+))?/;
    var match = re.exec(hash);
    if(match != null){
    	hash = match[1];
    } else {
    	hash = "home";
    }
    //}
    return hash;
};

(function($){
  $(function(){

	  	$('.tooltipped').tooltip();
    
		$('.dropdown-tyaa').click(function(){
			
		    $(this).find('.dropdown-content-tyaa').stop().slideToggle(400);
		});
		
		var localizeIndex = function (){
			
			preloaderOn();
			$.ajax({
				type: 'POST',
				url: '/language?action=dictionary',
				dataType: 'json',
				cache: false
			}).done(function(responseText, textStatus, jqXHR) {
				
				//Получаем словарь
				var dict = dictionaryResponseToArray(responseText.result);
				//Готовим шаблон пунктов главного меню при помощи библиотеки Hogan
			    var desktopNavTemplate = Hogan.compile(
		    		'<div class="nav-wrapper container">'
						+'<a id="logo-container" href="#" class="brand-logo">CTFinder</a>'
						+'<a href="#" data-target="nav-mobile" class="sidenav-trigger button-collapse"><i class="material-icons">menu</i></a>'
						+'<ul class="right hide-on-med-and-down">'
							+'<li><a href="#home">{{index_nav_home}}</a></li>'
							+'<li><a href="#find">{{index_nav_find}}</a></li>'
							+'<li><a href="#create">{{index_nav_offer}}</a></li>'
							+'<li><a href="#about">{{index_nav_about}}</a></li>'
						+'</ul>'
					+'</div>'
			    );
			    //Заполняем шаблон пунктов главного меню данными и помещаем на веб-страницу
				$('nav').html(desktopNavTemplate.render(dict));
				
				//Пункты мобильного главного меню
				var mobileNavTemplate = Hogan.compile(
					'<li><a href="#home">{{index_nav_home}}</a></li>'
					+'<li><a href="#find">{{index_nav_find}}</a></li>'
					+'<li><a href="#create">{{index_nav_offer}}</a></li>'
					+'<li><a href="#about">{{index_nav_about}}</a></li>'
			    );
				$('#nav-mobile').html(mobileNavTemplate.render(dict));
				
				//Пункты главного меню в подвале
				var footerNavTemplate = Hogan.compile(
					'<h5 class="white-text">{{index_footer_sections_title}}</h5>'
					+'<ul>'
						+'<li><a class="white-text" href="#home">{{index_nav_home}}</a></li>'
						+'<li><a class="white-text" href="#find">{{index_nav_find}}</a></li>'
						+'<li><a class="white-text" href="#create">{{index_nav_offer}}</a></li>'
						+'<li><a class="white-text" href="#about">{{index_nav_about}}</a></li>'
					+'</ul>'
			    );
				$('#footer-navbar').html(footerNavTemplate.render(dict));
				
				//О нас в подвале
				var footerAboutUsTemplate = Hogan.compile(
						'<h5 class="white-text">{{index_about_us_title}}</h5>'
						+'<p class="grey-text text-lighten-4">{{index_about_us_text}}</p>'
				    );
				$('#footer-about-us').html(footerAboutUsTemplate.render(dict));
				
				//Контакты в подвале
				var footerConnectTemplate = Hogan.compile(
					'<h5 class="white-text">{{index_footer_connect_title}}</h5>'
					+'<ul>'
						+'<li><a class="white-text" href="mailto:tyaamariupol@gmail.com">E-mail</a></li>'
						+'<li><a class="white-text" href="https://vk.com/creativetfinder">VK group</a></li>'
						+'<li><a class="white-text" href="https://www.facebook.com/groups/510052379461608/">FB group</a></li>'
						+'<li class="disabled"><a class="white-text" href="#!">YouTube</a></li>'
					+'</ul>'
			    );
				$('#footer-connect').html(footerConnectTemplate.render(dict));
				
				//Восстанавливаем класс active для пункта главного меню текущего раздела сайта
				var currentPageName = getCurrentPageName();
				//console.log('nav ul > li > a[href=\\#' + currentPageName + '], ul.sidenav a[href=\\#' + currentPageName + ']');
				$('nav ul > li > a[href=\\#' + currentPageName + '], ul.sidenav a[href=\\#' + currentPageName + ']').addClass("active");
				//console.log($('nav ul > li > a[href=\\#' + currentPageName + '], ul.sidenav a[href=\\#' + currentPageName + ']'));
				//
				$('.sidenav').sidenav();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				  
				alert("Ошибка: " + jqXHR);
			}).always(function() {
			    
				localized = true;
				preloaderOff();
			});
		}
		
		var getLanguages = function (){
			
			localized = false;
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
				Object.keys(pageLocaleHandlers).forEach(function (key){
					if(!pageLocalization[key]){
						pageLocaleHandlers[key].call();
					} else {
						//pageLocalization[key] = false;
					}
				});
			}).fail(function(jqXHR, textStatus, errorThrown) {
				  
				alert("Ошибка: " + jqXHR);
			}).always(function() {
			    
				//preloaderOff();
			});
		}
		
		getLanguages();
		//localizeIndex();
  }); // end of document ready
})(jQuery); // end of jQuery name space

function onSignIn(googleUser) {
	
	var id_token = googleUser.getAuthResponse().id_token;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'https://creativetfinder-dev.appspot.com/auth');
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
	  
	  authorized = true;
	  
	  preloaderOff();
	};
	xhr.send('idtoken=' + id_token);
}

$('#signout-li').click(signOut);

function signOut() {
	
	$(document.body).find("section#find, section#create").html("");
    var auth2 = gapi.auth2.getAuthInstance();
    
    auth2.signOut().then(function () {
    	
    	authorized = false;
    	preloaderOff();
    });
}

$('div#g-signin2').click(function(){
	
	preloaderOn();
});
