function getItems(){
	var request = new XMLHttpRequest();
  request.onreadystatechange = function() {
  	if ((request.readyState == 4) && (request.status == 200)){
			var items = JSON.parse(request.responseText);
			var innerHTML = "<table><tr><th>ID</th><th>Naziv</th>"+
							    "<th>Opis</th><th>Cijena</th><th>Obrisi</th></tr>";
			for(var item of items){
				innerHTML += "<tr>";
				innerHTML += "<td>"+item.idItem+"</td>";
				innerHTML += "<td>"+item.name+"</td>";
				innerHTML += "<td>"+item.description+"</td>";
				innerHTML += "<td>"+item.price+"</td>";
				innerHTML += "<td><button onclick=\"javascript:deleteItem("+item.idItem+")\">Obrisi</button></td>";
				innerHTML += "</tr>";
			}
			innerHTML += "</table>";
			document.getElementById("table").innerHTML = innerHTML;
		}
  };
  request.open("GET", "./items", true);
  request.send();
}

function logout(){
	location.replace(location.href.split("ItemApp/")[0]+"ItemApp/logout");
}

function deleteItem(id){
	var request = new XMLHttpRequest();
	 request.onreadystatechange = function() {
		  	if ((request.readyState == 4) && (request.status == 200)){
				getItems();
		  	}
	 };
	 request.open("DELETE", "./items?id="+id, true);
	 request.send();
}
