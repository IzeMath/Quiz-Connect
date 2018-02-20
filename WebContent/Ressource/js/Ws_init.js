function SearchBtRoom()
{
	var idToSearch = document.getElementById("SearchBar").value;
	var evt = document.createEvent("MouseEvents");
	evt.initMouseEvent("click", true, true, window,0, 0, 0, 0, 0, false, false, false, false, 0, null);
	document.getElementById(idToSearch).dispatchEvent(evt);
}

function createRoom() {
	var mess = {
		type : "createRoom",
		roomName : "",
		password : ""
	};
	mess.roomName = $("#rname").val();
	mess.password = $("#rpass").val();
	localStorage.setItem("mess", JSON.stringify(mess));
	window.location.href = "/Quiz-Connect/GameServlet";
	
}

function joinRoom(idroom) {
	var mess = {
		type : "joinRoom",
		roomId : idroom,
		password : ""
	};
	localStorage.setItem("mess", JSON.stringify(mess));
	window.location.href = "/Quiz-Connect/GameServlet";
}

function joinRoomMob() {
	var mess = {
		type : "joinRoom",
		roomId : "",
		password : ""
	};
	mess.roomId = $("#forbtnGoMob").val();
	localStorage.setItem("mess", JSON.stringify(mess));
	window.location.href = "/Quiz-Connect/GameServletmob";
}

function setID(idroom)
{
	idjoinroom = idroom;
}

function joinRoomPrivate() {
	var mess = {
		type : "joinRoom",
		roomId : idjoinroom,
		password : ""
	};
	mess.password = $("#Wsjoinpass").val();
	localStorage.setItem("mess", JSON.stringify(mess));
	window.location.href = "/Quiz-Connect/GameServlet";
}
