<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Test Game</title>
</head>
<!--  <script src="Ressource/jquery/jquery.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<!--  <script>
	var wsocket = null;
	var serviceLocation = "ws://localhost:8080/QC-IHM/Quizcon";
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
				invitName : ""
			};
			mess.invitName = $("#invitName").val();
			console.log(mess);
			wsocket.send(JSON.stringify(mess));
			var testObject = { 'one': 1, 'two': 2, 'three': wsocket };
			localStorage.setItem('ws', JSON.stringify(testObject));
			var retrievedObject = localStorage.getItem('ws');

			console.log('retrievedObject: ', JSON.parse(retrievedObject));
			
		}
	}

	function logUser() {
		if (wsocket == null) {
			openWS();
		}
		wsocket.onopen = function() {
			var mess = {
				type : "logUser",
				userName : "",
				password : ""
			};
			mess.userName = $("#user").val();
			mess.password = $("#pass").val();
			wsocket.send(JSON.stringify(mess));
		}
	}

	function joinRoom() {
		var mess = {
			type : "joinRoom",
			roomId : 0,
			password : ""
		};
		mess.roomId = $("#rid").val();
		mess.password = $("#rpass").val();
		wsocket.send(JSON.stringify(mess));
	}

	function createRoom() {
		var mess = {
			type : "createRoom",
			roomName : "",
			password : ""
		};
		mess.roomName = $("#rname").val();
		mess.password = $("#rpass2").val();
		wsocket.send(JSON.stringify(mess));
	}

	function sendPara() {
		var difficulties = JSON.stringify(getCheckedBoxes("ch_difficulties"));
		var themes = JSON.stringify(getCheckedBoxes("ch_themes"));
		var langs = JSON.stringify(getCheckedBoxes("ch_langs"));
		//console.log(themes);
		if (difficulties != "null" && langs != "null" && themes != "null") {
			$.get("²", {
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
			for (var i = 0; i < msg.reponses.length; i++) {
				var rep = $('<input type="button" onclick="sendReponse(this)" value="'
						+ msg.reponses[i] + '">');
				rep.appendTo('#reponses');
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
						sortedScore[i][0] + " ====> " + sortedScore[i][1]
								+ "<br>");
			}
		}
		if (msg.type == "inform") {
			console.log(msg);
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
		$('#reponses').append("<p>" + explication + "</p>");

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
</script>-->

<script type="text/javascript" src="./Ressource/js/Ws_engine.js"></script>
<body>
	<fieldset>
		<legend>Connexion</legend>
		Connexion invité : <br> Nom : <input type="text" id="invitName">
		<br> <input type="button" onclick="logInv()"
			value="ouvrir session invité"><br> =============== OU
		=============== <br> Connexion utilisateur <br> Utilisateur:
		<input type="text" id="user"> <br> Mot de passe: <input
			type="password" id="pass"> <br> <input type="button"
			onclick="logUser()" value="ouvrir session">
	</fieldset>

	<fieldset>
		<legend>Connexion à une room</legend>

		Rejoindre une room : <input type="text" id="rid"> <br>
		Mot de passe (optinnel) : <input type="text" id="rpass"> <br>
		<input type="button" onclick="joinRoom()" value="Rejoindre"><br>
		=============== OU =============== <br>
		Créer une room :<br>
		Nom : <input type="text" id="rname"><br>
		Mot de passe (optinnel) : <input type="text" id="rpass2"><br>
		<input type="button" onclick="createRoom()" value="Créer et rejoindre"><br>

	</fieldset>
	<br>

	<fieldset>
		<legend>Nombres de questions</legend>
		<input type="text" id="nbQ"> max :
		<div id="maxqst"></div>
	</fieldset>

	<fieldset>
		<legend>Liste des difficultées</legend>
		<c:forEach var="itm" items="${difficulties}">
			<input onclick="getMaxQuestion()" type="checkbox" value="${ itm }"
				name="ch_difficulties">
			<c:out value="${ itm }"></c:out>
			<br>
		</c:forEach>
	</fieldset>
	<fieldset>
		<legend>Liste des themes</legend>
		<c:forEach var="itm" items="${themes}">
			<input onclick="getMaxQuestion()" type="checkbox" value="${ itm }"
				name="ch_themes">
			<c:out value="${ itm }"></c:out>
			<br>
		</c:forEach>
	</fieldset>
	<fieldset>
		<legend>Liste des langues</legend>
		<c:forEach var="itm" items="${langs}">
			<input onclick="getMaxQuestion()" type="checkbox" value="${ itm }"
				name="ch_langs">
			<c:out value="${ itm }"></c:out>
			<br>
		</c:forEach>
	</fieldset>
	<br>
	<input type="button" onclick="sendParameters()" value="send Para">
	<br>
	<hr>
	<input type="button" onclick="askQuestion()" value="Question svp">
	<br>

	<div id="question"></div>
	<div id="reponses"></div>
	<br>

	<fieldset>
		<legend>Liste des scores</legend>
		<div id="scoreList"></div>
	</fieldset>



</body>
</html>