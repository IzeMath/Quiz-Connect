var id = "";

$.get("GetUserIdServlet", {}, function(responseText) {
	if (responseText.userId != "") {
		id = responseText.userId;
	}
});

function joinRoomMob() {
	if (id == "") {
		joinRoomInv();
	} else {
		joinRoomUser();
	}
}

function joinRoomInv() {
	var mess = {
		type : "playerInv",
		playerName : "",
		roomId : "",
		password : ""
	};
	mess.roomId = $("#forbtnGoMob").val();
	mess.password = $("#MdpOfMobile").val();

	if ($("#NameOfMobile").val() != "") {
		mess.playerName = $("#NameOfMobile").val();
	} else {
		mess.playerName = nameAlea();
	}

	$.get("CheckRoomServlet", {
		roomId : mess.roomId,
		password : mess.password
	}, function(responseText) {
		if (responseText.canChangePage) {
			localStorage.setItem("mess", JSON.stringify(mess));
			goToRoomPage();
		}
	});
}

function joinRoomUser() {
	var mess = {
		type : "playerConnected",
		roomId : "",
		password : "",
		userId : id
	};
	mess.roomId = $("#forbtnGoMob").val();
	mess.password = $("#MdpOfMobile").val();

	$.get("CheckRoomServlet", {
		roomId : mess.roomId,
		password : mess.password
	}, function(responseText) {
		if (responseText.canChangePage) {
			localStorage.setItem("mess", JSON.stringify(mess));
			goToRoomPage();
		}
	});
}

function goToRoomPage() {
	window.location.href = "/Quiz-Connect/GameServletmob";
}

function nameAlea() {
	var nb = Math.floor(Math.random() * 15000);
	var newName = "Guest" + nb;
	return newName;
}