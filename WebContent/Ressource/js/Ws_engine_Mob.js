var wsocket = null;
var serviceLocation = "ws://" + window.location.href.split("/")[2]
		+ "/Quiz-Connect/Quizcon";
var buttonSelected = null;
var canRep = false;

function openWS() {
	wsocket = new WebSocket(serviceLocation);
	wsocket.onmessage = onMessageReceived;
	wsocket.onerror = onErrorReceived;
}

function closeWS() {
	wsocket.close();
}

// Premiere fonction appellé
// Ici nous somme sur un moniteur
function conection() {
	var mess = localStorage.getItem("mess");
	if (mess != null) {
		logMobile();
		$("#idroom").html(JSON.parse(localStorage.getItem("mess")).roomId);
	} else {// erreur mess null
		backToIndexPage();
	}
}

function logMobile() {
	if (wsocket == null) {
		openWS();
	}
	wsocket.onopen = function() {
		wsocket.send(localStorage.getItem("mess"));
	}
}

function onErrorReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	console.log(msg);
}

function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	console.log(msg);

	if (msg.type == "listRep") {
		$("#reponses").empty();
		for (var i = 0; i < msg.listRep.length; i++) {
			var rep = $('<div class="row col-10 offset-1 my-1"><button id="'
					+ msg.listRep[i]
					+ '" type="button" onclick="sendReponse(this)" class="btn-block btn btn-secondary">'
					+ msg.listRep[i] + '</button></div>');

			rep.appendTo('#reponses');

		}
		canRep = true;
	}

	if (msg.type == "reponse") {
		canRep = false;
		document.getElementById(msg.reponse).className = "btn-block btn btn-success";
	}
}

function sendReponse(bt) {
	if (canRep) {
		var mess = {
			type : "playerReponse",
			reponse : ""
		};
		mess.reponse = bt.id;

		console.log(mess);

		wsocket.send(JSON.stringify(mess));

		if (buttonSelected != null) {
			buttonSelected.className = "btn-block btn btn-secondary";
		}
		bt.className = "btn-block btn btn-primary";

		buttonSelected = bt;
	}
}

function backToIndexPage() {
	closeWS();
	window.location.href = "/Quiz-Connect/GameServlet2";
}

conection();
