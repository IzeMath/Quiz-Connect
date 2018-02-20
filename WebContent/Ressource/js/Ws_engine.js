var wsocket = null;
var serviceLocation = "ws://192.168.1.18:8080/QC-IHM/Quizcon";
var explication = "";

function openWS() {
	wsocket = new WebSocket(serviceLocation);
	wsocket.onmessage = onMessageReceived;
}

function closeWS() {
	wsocket.close();
}

function logInv() {
	if (wsocket == null) {
		openWS();
	}
	wsocket.onopen = function() {
		var mess = {
			type : "logInv",
			invitName : "moniteur",
			moniteurType : true
		};
		wsocket.send(JSON.stringify(mess));
	}
}

function joinRoom() {
	wsocket.send(localStorage.getItem("mess"));
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
		});
	}
}

function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data); // native API
	console.log(msg);
	if (msg.type == "question") {
		explication = msg.explication;
		$('#question').empty();
		$('#reponses').empty();
		$('#question').append("<p>" + msg.question + "</p>");
		if (navigator.userAgent
				.match(/(android|iphone|blackberry|symbian|symbianos|symbos|netfront|model-orange|javaplatform|iemobile|windows phone|samsung|htc|opera mobile|opera mobi|opera mini|presto|huawei|blazer|bolt|doris|fennec|gobrowser|iris|maemo browser|mib|cldc|minimo|semc-browser|skyfire|teashark|teleca|uzard|uzardweb|meego|nokia|bb10|playbook)/gi)) {
			for (var i = 0; i < msg.reponses.length; i++) {
				var rep = $('<input type="button" class="block" onclick="sendReponse(this)" value="'
						+ msg.reponses[i] + '">');
				rep.appendTo('#reponses');
			}
		}
	}
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

	if (msg.type == "inform") {
		if (msg.logInv && !msg.inRoom) {
			joinRoom();
		}
	}
}

function askQuestion() {
	var mess = {
		type : "question"
	};
	wsocket.send(JSON.stringify(mess));
}
function sendReponse(bt) {
	var mess = {
		type : "reponse",
		reponse : ""
	};
	mess.reponse = bt.value;
	console.log(mess);
	wsocket.send(JSON.stringify(mess));
	$('#reponses').empty();
	$('#reponses').append(explication);

}

function sendParameters() {
	var mess = {
		type : "init",
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
	wsocket.send(JSON.stringify(mess));
	console.log(mess);
	wsocket.send(JSON.stringify(mess));
	askQuestion();
	$("#SendPara").prop('disabled', true);

	timerWS($("#nbQ").val());

}

function timerWS(nbquestions) {

	var timeleft = 10;
	console.log(nbquestions);
	var countdownTimer = setInterval(
			function() {
				timeleft--;
				document.getElementById("countdowntimer").textContent = timeleft;
				if (timeleft <= 0) {
					clearInterval(countdownTimer);
					if (nbquestions != 1) {
						askQuestion();
						document.getElementById("countdowntimer").textContent = "10";
						timerWS(nbquestions - 1);

					} else {
						$('#reponses')
								.html(
										"Bienvenu dans le salon de jeu, pour vous permettre de repondre, il vous faut utilise votre smartphone a cette url, il vous faudra saisir l’id, qui se situe en haut a droite, de la room afin de la rejoindre. Vous aurez dix secondes pour repondre à chaque questions depuis votre smartphone. Have fun.");
						$('#question').html("Regle");
					}

				}
			}, 1000);

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

logInv();