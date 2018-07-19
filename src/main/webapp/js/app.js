// Single application framework
(function($,window){
	
  var pageHandlers = {};
  var currentPage;
  
  // show the "page" with optional parameter
  function show(pageName,param) {

	  //$(".preloader-wrapper").css("display", "block");
	  preloaderOn();
    
	    // invoke page handler
	    var ph = pageHandlers[pageName]; 
	    
	    // activate the page  
	    $("nav a.active").removeClass("active");
	    $("nav a[href='#"+pageName+"']").addClass("active");
	    //TODO hide only old section
	    $(document.body).attr("page",pageName)
	                    .find("section").fadeOut(1000).removeClass("active")
	                    .filter("section#" + pageName).fadeIn(1000).addClass("active");
	    //$(document.body).find("section#" + pageName).fadeIn(1000).addClass("active");
	    //console.log($(document.body).find("section#" + pageName));
	    
	    if( ph ) { 
	        var $page = $("section#" + pageName);
	        ph.call( $page.length ? $page[0] : null,param ); // call "page" handler
	    }
  }  

  function app(pageName,param) {
  
    var $page = $(document.body).find("section#" + pageName);  
    
    var src = $page.attr("src");
    if( src && $page.find(">:first-child").length == 0) {
    	
      $.ajax({
    		  url:src
    		  , type: 'GET'
    		  , crossDomain: true
      }) // it has src and is empty - load it
          .done(function(html, textStatus, xhr){
        	  
        	  
        	  
        	  if(html == "no_session"){
        		  
        		  setTimeout(preloaderOff, 500);
        		  //$('div#g-signin2').click();
        		  //$page.html("");
        		  window.location.href = "/#home";
        		  alert("Ошибка авторизации. Сначала войдите в аккаунт");
        	  } else {
        		  
        		  currentPage = pageName;
            	  $page.html(html);
            	  show(pageName,param);
        	  }
    	  })
          .fail(function(){ $page.html("failed to get:" + src); });
      
    	/*fetch(src, { redirect: "error" })
    	  .then(function(response) {
    		  
    		  	console.log(response.status);
    		  	return response.text().then(function(html) {
    		  		
    		  		if (response.status == 200) {
    					  // process the data
    						currentPage = pageName;
    						$page.html(html);
    						show(pageName,param);
					}
    		  	});
    	  })
    	  .catch(function(error) {
    	      // do some clean-up job
    		  //$page.html("Auth error");
    		  console.log("Auth error");
    	  });*/
      
    } else
      show(pageName,param);
  }

  // register page handler  
  app.handler = function(handler) {
    var $page = $(document.body).find("section#" + currentPage);  
    pageHandlers[currentPage] = handler.call($page[0]);
  }
  
  function onhashchange() 
  {
    var hash = location.hash || "#home";
    if(hash.indexOf("info") === -1){
    	var re = /#([-0-9A-Za-z]+)(\:(.+))?/;
        var match = re.exec(hash);
        if(match != null){
        	hash = match[1];
            var param = match[3];
            app(hash,param); // navigate to the page
        }
    }
  }
  
  $(window).hashchange( onhashchange ); // attach hashchange handler
  
  window.app = app; // setup the app as global object
  
  $(function(){ $(window).hashchange() }); // initial state setup

})(jQuery,this);






