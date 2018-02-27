var wsocket = null;
var serviceLocation = "ws://" + window.location.href.split("/")[2]
		+ "/Quiz-Connect/Quizcon";
var timer;
var timeLeft;

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
		logMonitor();
	} else {// erreur mess null
		backToIndexPage();
	}
}

function logMonitor() {
	if (wsocket == null) {
		openWS();
	}
	wsocket.onopen = function() {
		wsocket.send(localStorage.getItem("mess"));
	}
}

function getMaxQuestion() {
	var difficulties = JSON.stringify(getCheckedBoxes("ch_difficulties"));
	var themes = JSON.stringify(getCheckedBoxes("ch_themes"));
	var langs = JSON.stringify(getCheckedBoxes("ch_langs"));
	// console.log(themes);
	if (difficulties != "null" && langs != "null" && themes != "null") {
		$.get("InfoQuestServlet", {
			difficulties : difficulties,
			themes : themes,
			langs : langs
		}, function(responseText) {
			$("#maxqst").text(responseText);
			$("#SendPara").prop('disabled', false);
		});
	}
}

function onErrorReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	console.log(msg);
}

function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	console.log(msg);

	// Permet d'afficher la question
	if (msg.type == "question") {
		$('#question').empty();
		$('#explications').empty();
		$('#question').append("<p>" + msg.question + "</p>");

		timeLeft = 12;
		$("#countdowntimer").text(timeLeft);
		timer = setInterval(function() {
			timeLeft--;
			$("#countdowntimer").text(timeLeft);

		}, 1000);

	}

	// Affiche les explications
	if (msg.type == "explications") {
		clearInterval(timer);
		$("#countdowntimer").empty();
		$('#explications').text(msg.explications);
	}

	// Affiche la liste des joueurs avec leurs score
	if (msg.type == "score") {
		$("#scoreList").empty();
		var sortedScore = [];
		for ( var key in msg.scores) {
			sortedScore.push([ key, msg.scores[key] ]);
		}
		sortedScore.sort(function(a, b) {
			return b[1] - a[1];
		});
		for (var i = 0; i < sortedScore.length; i++) {
			$("#scoreList").append(
					"<tr><td>" + sortedScore[i][0] + "</td><td>"
							+ sortedScore[i][1] + "</td></tr>");
		}
	}

	// Affiche id room en haut a droite
	if (msg.type == "infoMonitor") {
		var res = msg.roomId.substring(0, 3) + " ";
		var res2 = msg.roomId.substring(3, 6);
		$("#idroom").text(res + res2);
	}

}

function sendParameters() {
	var mess = {
		type : "startGame",
		nbQ : 0,
		difficulties : [],
		themes : [],
		langs : []
	};

	mess.nbQ = $('#nbQ').val();
	mess.difficulties = getCheckedBoxes("ch_difficulties");
	mess.themes = getCheckedBoxes("ch_themes");
	mess.langs = getCheckedBoxes("ch_langs");

	console.log(mess);
	$('#explications').empty();

	wsocket.send(JSON.stringify(mess));

	$("#SendPara").prop('disabled', true);

}

function getCheckedBoxes(chkboxName) {
	var checkboxes = document.getElementsByName(chkboxName);
	var checkboxesChecked = [];
	for (var i = 0; i < checkboxes.length; i++) {

		if (checkboxes[i].checked) {
			checkboxesChecked.push(checkboxes[i].value);
		}
	}
	return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}
function backToIndexPage() {
	closeWS();
	window.location.href = "/Quiz-Connect/GameServlet2";
}

conection();
