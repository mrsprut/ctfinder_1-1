// Single application framework
(function($,window){
	
  var pageHandlers = {};
  var currentPage;
  
  // show the "page" with optional parameter
  function show(pageName,param) {

    $(".preloader-wrapper").css("display", "block");
    //console.log($(".loader"));
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
      $.get(src, "html") // it has src and is empty - load it
          .done(function(html){ currentPage = pageName; $page.html(html); show(pageName,param); })
          .fail(function(){ $page.html("failed to get:" + src); });
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
        hash = match[1];
        var param = match[3];
        app(hash,param); // navigate to the page
    }
  }
  
  $(window).hashchange( onhashchange ); // attach hashchange handler
  
  window.app = app; // setup the app as global object
  
  $(function(){ $(window).hashchange() }); // initial state setup

})(jQuery,this);






