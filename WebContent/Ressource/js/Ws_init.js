var idjoinroom = 0;

function SearchBtRoom() {
	var idToSearch = document.getElementById("SearchBar").value;
	var evt = document.createEvent("MouseEvents");
	evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false,
			false, false, false, 0, null);
	document.getElementById(idToSearch).dispatchEvent(evt);
}

function createRoom() {
	var mess = {
		type : "monitorCreate",
		roomName : "",
		password : ""
	};
	mess.roomName = $("#rname").val();
	mess.password = $("#rpass").val();
	localStorage.setItem("mess", JSON.stringify(mess));
	goToRoomPage();

}

function canJoin(idroom, password) {
	$.get("CheckRoomServlet", {
		roomId : idroom,
		password : password
	}, function(responseText) {
		return responseText.canChangePage;
	});
}

function joinRoom(idroom) {
	var mess = {
		type : "monitorJoin",
		roomId : idroom,
		password : ""
	};
	
	$.get("CheckRoomServlet", {
		roomId : mess.roomId,
		password : mess.password
	}, function(responseText) {
		if(responseText.canChangePage){
			localStorage.setItem("mess", JSON.stringify(mess));
			goToRoomPage();
		}
	});
}

function joinRoomPrivate() {
	var mess = {
		type : "monitorJoin",
		roomId : idjoinroom,
		password : ""
	};
	mess.password = $("#Wsjoinpass").val();
	
	$.get("CheckRoomServlet", {
		roomId : mess.roomId,
		password : mess.password
	}, function(responseText) {
		if(responseText.canChangePage){
			localStorage.setItem("mess", JSON.stringify(mess));
			goToRoomPage();
		}
	});
}

function goToRoomPage() {
	window.location.href = "/Quiz-Connect/GameServlet";
}

function setID(idroom) {
	idjoinroom = idroom;
}