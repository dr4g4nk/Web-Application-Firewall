/**
 * 
 */
var sum=0;

function add(value){
	sum+=parseFloat(value);
}

function total(){
	alert("Ukupno za placanje "+ sum+" KM");
}

function logout(){
	location.replace(location.href.split("ShopApp/")[0]+"ShopApp/logout");
}