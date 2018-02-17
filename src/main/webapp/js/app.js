// Single application framework
(function($,window){

  var pageHandlers = {};
  var currentPage;
  
  // show the "page" with optional parameter
  function show(pageName,param) {

    $(".loader").css("display", "block");
    //console.log($(".loader"));
    // invoke page handler
    var ph = pageHandlers[pageName]; 
    if( ph ) { 
      var $page = $(".sect#" + pageName);
      ph.call( $page.length ? $page[0] : null,param ); // call "page" handler
    }
    // activate the page  
    $("nav a.active").removeClass("active");
    $("nav a[href='#"+pageName+"']").addClass("active");
    //TODO hide only old section
    $(document.body).attr("page",pageName)
                    .find(".sect").fadeOut(1000).removeClass("active")
                    .filter(".sect#" + pageName).addClass("active").fadeIn(600);
  }  

  function app(pageName,param) {
  
    var $page = $(document.body).find(".sect#" + pageName);  
    
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
    var $page = $(document.body).find(".sect#" + currentPage);  
    pageHandlers[currentPage] = handler.call($page[0]);
  }
  
  function onhashchange() 
  {
    var hash = location.hash || "#home";
    
    var re = /#([-0-9A-Za-z]+)(\:(.+))?/;
    var match = re.exec(hash);
    hash = match[1];
    var param = match[3];
    app(hash,param); // navigate to the page
  }
  
  $(window).hashchange( onhashchange ); // attach hashchange handler
  
  window.app = app; // setup the app as global object
  
  $(function(){ $(window).hashchange() }); // initial state setup

})(jQuery,this);






