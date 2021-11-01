function getUsers(){
	var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
  	if ((request.readyState == 4) && (request.status == 200)){
			var users = JSON.parse(request.responseText);
			var innerHTML = "<table><tr><th>Korisnicko ime</th><th>Uloga</th>"+
							    "<th>Obrisi</th></tr>";
			for(var user of users){
				innerHTML += "<tr>";
				innerHTML += "<td>"+user.username+"</td>";
				innerHTML += "<td>"+(user.role == 0 ? "Kupac" : user.role == 1 ? "Admin artikal" : "Admin")+"</td>";
				innerHTML += "<td><button onclick=\"javascript:deleteUser(\'"+user.username+"\')\">Obrisi</button></td>";
				innerHTML += "</tr>";
			}
			innerHTML += "</table>";
			document.getElementById("table").innerHTML = innerHTML;
		}
  };
  request.open("GET", "./users", true);
  request.send();
}

function deleteUser(username){
	
	var request = new XMLHttpRequest();
	 request.onreadystatechange = function() {
		  	if ((request.readyState == 4) && (request.status == 200)){
		  		getUsers();
		  	}
	 };
	 request.open("DELETE", "./users?username="+username, true);
	 request.send();
}

function logout(){
	location.replace(location.href.split("UserApp/")[0]+"UserApp/logout");
}