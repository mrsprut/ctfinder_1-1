var GoogleAuth; // Google Auth object
var authorized = false;
var localized = false;

var noItemsImage = '<img class="responsive-img" src="../img/flat-search-find.png">';

//Набор ссылок на функции локализации страниц
var pageLocaleHandlers = {};
//Флаги выполнения начальной локализации страниц
var pageLocalization = {
		'home':false
		, 'find':false
		, 'offer':false
		, 'about':false
};

//Добавляем к стандартному типу Дата функцию коррекции даты по часовому поясу
Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});

//Форматирование даты, в которой год, месяц и день изначально переставлены местами
function formatDate(date){

    //Разрезаем строку даты на массив из трех елементов
    var pieces = date.split('-');
    //Меняем элементы местами
    pieces.reverse();
    //Склеиваем строку даты из массива
    var reversed = pieces.join('-');
    return reversed;
}

/*function sleep(ms) {
	return new Promise(resolve => setTimeout(resolve, ms));
}*/

function preloaderOn(nocover) {
	
	if(nocover == undefined){
		$("#cover").css("display", "block");
	}
	$(".preloader-wrapper").css("display", "block");
}

function preloaderOff() {
	
	$("#cover").css("display", "none");
	$(".preloader-wrapper").css("display", "none");
}

function fixedEncodeURI (str) {
	
    return encodeURI(str).replace(/%5B/g, '[').replace(/%5D/g, ']');
}

function dictionaryResponseToArray(dict){
	
	return dict.reduce(function(acc, cur, i) {
		  		acc[cur.key] = cur.word;
		  		return acc;
			}, {});
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

function checkSigninStatus(isSignedIn) {

	preloaderOn();
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
		xhr.open('POST', 'https://creativetfinder-dev.appspot.com/auth');
		//xhr.open('POST', 'http://localhost:8080/auth');
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xhr.responseType = 'json';

		xhr.onload = function() {

			//console.log('Action: ' + xhr.response.result[0]);
			preloaderOn();
			$("#username").text("");
			$("#useremail").text("");
			$("#userpicture").attr("src", "img/hourglass.jpeg");
			
			//preloaderOff();
			//GoogleAuth.disconnect();
		};
		xhr.send(null);
		//preloaderOff();
		//TODO полное отсоединение от профиля
	}
	//preloaderOff();
}