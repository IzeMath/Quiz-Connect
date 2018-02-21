<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="langues.QC" />

<html lang="${language}">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>QuizzProject</title>
<meta name="description" content="Jeux Interractif" />
<meta name="keywords"
	content="quiz, quizz, mobile quiz, blind test, blind-test" />

<!-- Bootstrap core CSS -->
	<link href="./Ressource/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="./Ressource/font-awesome/css/font-awesome.min.css" />

	<!-- core CSS -->
	<link rel="stylesheet" type="text/css" href="./Ressource/css/MainTheme.css" />
	<link rel="stylesheet" type="text/css" href="./Ressource/css/component.css" />
	<script src="./Ressource/js/snap.svg-min.js"></script>


<!--[if IE]>
	  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

</head>
<body>
			<!-- Sidebar -->
			<div id="sidebar-wrapper2">
				<form>
					<select  id="language" name="language" onchange="submit()">
						<option value="en" ${language == 'en' ? 'selected' : ''}>En</option>
						<option value="fr" ${language == 'fr' ? 'selected' : ''}>Fr</option>
					</select>
				</form>
			</div> <!-- /sidebarWrapper -->

			<nav class="navbar navbar-light navbar-expand-md navigation-clean">
						<div class="container">
							<a class="navbar-brand" href="#">QuizConnect</a>
							<button id="toggler" class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1" aria-expanded="false" aria-label="Toggle navigation" aria-controls="navbarResponsive" style="background-color:#17a2b8; color: #ffffff" >
								<span class="sr-only">Toggle navigation</span>
								<i class="fa fa-bars" aria-hidden="true"></i>
							</button>

							<div class="collapse navbar-collapse" id="navcol-1">
								<ul class="nav navbar-nav ml-auto">
									<c:choose>
								<c:when test="${empty user}">
									<li class="nav-item"><a class="nav-link"
										href="#EnregistrementModal" data-toggle="modal"><fmt:message
												key="navbar.enregistrement" /></a></li>
									<li class="nav-item"><a class="nav-link"
										href="#ConnexionModal" data-toggle="modal"><fmt:message key="navbar.connexion" /></a></li>
								</c:when>
								<c:otherwise>
									<li class="nav-item"><a class="nav-link"
										href="LogoutServlet"><c:out value="${user.getEmail() }" /></a>
									</li>

								</c:otherwise>
							</c:choose>
								</ul>
							</div>
						</div>
					</nav>
			<div class="container" >
				<div class="container ServerList serverStyle">
					<div class="btnServerList btn btn-info ribbon">
						<i class="fa fa-refresh"></i> <fmt:message key="mainpage.listserver" />
					</div>
					<div class="card">
						<div class="card-body">
							<button type="button" class="btn btn-info btn-circle" data-target="#CreateRoom" data-toggle="modal" >
									<i class="fa fa-plus" ></i>
								</button>
							<form class="navbar-search pull-right" action="" method="post"
								role="search">
								<div class="input-group">
									<fmt:message key="mainpage.search" var="placeholdersearch" />
									<input type="text" class="form-control" id="SearchBar"
										placeholder="${placeholdersearch}">
									<div class="input-group-btn">
										<button onclick="SearchBtRoom()" type="button" class="btn btn-search btn-info">
											<span class="fa fa-search"></span>
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="ServerListStyle ">
						<div class="ListServerContainer">
							<c:choose>
								<c:when test="${empty server}">
									<div class="container SlotserverStyle">
										<div class="row"padding-bottom: 1%; padding-top: 1%;">
											<p style="margin: 0 auto;" class="Servtitle">
												<b><fmt:message key="mainpage.aucun" /></b> <fmt:message key="mainpage.noServ" />
											</p>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<c:forEach items="${server}" var="serv">
										<div class="container SlotserverStyle">
											<div class="row"
												style="margin-left: 7%; padding-bottom: 1%; padding-top: 1%;">
												<c:choose>
													<c:when test="${serv.isPrivate()}">
															<button id="${serv.getId()}" type="button"
															class="btn btn-info btn-circle btn-lg "
															style="margin-top: 5px;" href="#ConnexionModalRoom" data-toggle="modal" onclick="setID(${serv.getId()})">
															<i class="fa fa-play"></i>
															</button>
													</c:when>
												<c:otherwise>
												<button id="${serv.getId()}" type="button"
													class="btn btn-info btn-circle btn-lg "
													style="margin-top: 5px;" onclick="joinRoom(${serv.getId()})">
													<i class="fa fa-play"></i>
												</button>
												</c:otherwise>
												</c:choose>
												
											
												<div class="col">
													<p class="Servtitle">
														<b><c:out value="${serv.getName()}" /></b>
													</p>
													<p class="Servdescript">
														<fmt:message key="mainpage.nbjoueur" />: <c:out value="${serv.getNbPlayers()}" />
													</p>
												</div>
												<p class="Servtype">
													<c:choose>
													<c:when test="${serv.isPrivate()}">
														<i class="fa fa-lock">&nbsp</i><fmt:message key="mainpage.prive" />
													</c:when>
												<c:otherwise>
													<fmt:message key="mainpage.public" />
												</c:otherwise>
												</c:choose>
												</p>
											</div>
										</div>
										<hr class="divider-vertical" />
									</c:forEach>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</div>

				<div id="ConnexionModalRoom" class="modal fade">
					<div class="modal-dialog modal-login">
						<div class="modal-content">
							<div class="modal-header">
								<div class="avatar">
									<img src="./Ressource/img/avatar.png" alt="Avatar">
								</div>
								<h4 class="modal-title"><fmt:message key="joinroom.welcome" /></h4>
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
							</div>
							<div class="modal-body">
									<div class="form-group">
									<fmt:message key="joinroom.password" var="joinroompassword" />
										<input type="password" class="form-control" id="Wsjoinpass"
											placeholder="${joinroompassword}" required="required">
									</div>
	
									<div class="form-group">
										<button type="submit" 
										class="btn btn-primary btn-lg btn-block login-btn" onclick="joinRoomPrivate()"><fmt:message key="navbar.connexion" /></button>
									</div>
							</div>
							<div class="modal-footer">
								<a href="#"><fmt:message key="joinroom.footer" /></a>
							</div>
						</div>
					</div>
				</div>

				<div id="ConnexionModal" class="modal fade">
					<div class="modal-dialog modal-login">
						<div class="modal-content">
							<div class="modal-header">
								<div class="avatar">
									<img src="./Ressource/img/avatar.png" alt="Avatar">
								</div>
								<h4 class="modal-title"><fmt:message key="navbar.welcome" /></h4>
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
							</div>
							<div class="modal-body">
								<form action="LoginServlet" method="post">
									<div class="form-group">
									<fmt:message key="navbar.email" var="navbarmail" />
										<input type="text" class="form-control" name="email"
											placeholder="${navbarmail}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="navbar.password" var="navbarpass" />
										<input type="password" class="form-control" name="password"
											placeholder="${navbarpass}" required="required">
									</div>
									<div class="form-group">
										<button type="submit"
											class="btn btn-primary btn-lg btn-block login-btn"><fmt:message key="navbar.connexion" /></button>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<a href="#"><fmt:message key="navbar.footermdpoublie" /></a>
							</div>
						</div>
					</div>
				</div>

				<div id="CreateRoom" class="modal fade">
					<div class="modal-dialog modal-login">
						<div class="modal-content">
							<div class="modal-header">
								<div class="avatar">
									<img src="./Ressource/img/play.png" alt="Avatar">
								</div>
								<h4 class="modal-title"><fmt:message key="addroom.title" /></h4>
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
							</div>
							<div class="modal-body">
									<div class="form-group">
									<fmt:message key="addroom.name" var="addroomname"  />
										<input type="text" class="form-control" id="rname"
											placeholder="${addroomname}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="addroom.password" var="addroompass"  />
										<input type="password" class="form-control" id="rpass"
											placeholder="${addroompass}">
									</div>
									<div class="form-group">
										<button type="submit"
											class="btn btn-primary btn-lg btn-block login-btn" onclick="createRoom()"><fmt:message key="addroom.create" /></button>
									</div>
							</div>
						</div>
					</div>
				</div>

				<div id="EnregistrementModal" class="modal fade">
					<div class="modal-dialog modal-login">
						<div class="modal-content">
							<div class="modal-header">
								<div class="avatar">
									<img src="./Ressource/img/avatar.png" alt="Avatar">
								</div>
								<h4 class="modal-title"><fmt:message key="navbar.welcome" /></h4>
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
							</div>
							<div class="modal-body">
								<form action="RegisterServlet" method="post">
									<div class="form-group">
									<fmt:message key="navbar.nom" var="navbarnom" />
										<input type="text" class="form-control" name="name"
											placeholder="${navbarnom}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="navbar.prenom" var="navbarprenom" />
										<input type="text" class="form-control" name="firstname"
											placeholder="${navbarprenom}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="navbar.pseudo" var="navbarpseudo" />
										<input type="text" class="form-control" name="username"
											placeholder="${navbarpseudo}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="navbar.email" var="navbaremail" />
										<input type="email" class="form-control" name="email"
											placeholder="${navbaremail}" required="required">
									</div>
									<div class="form-group">
									<fmt:message key="navbar.password" var="navbarnpass" />
										<input type="password" class="form-control" name="password"
											placeholder="${navbarpass}" required="required">
									</div>

									<div class="form-group">
										<button type="submit"
											class="btn btn-primary btn-lg btn-block login-btn"><fmt:message key="navbar.enregistrement" /></button>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<p><fmt:message key="navbar.footerinfo" /></p>
							</div>
						</div>
					</div>
				</div>
			</div>

	<!-- Boostrap core JS -->
	<script type="text/javascript" src="./Ressource/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="./Ressource/bootstrap/js/bootstrap.bundle.min.js"></script>


	<script src="./Ressource/js/Detect.js"></script>
	<script src="./Ressource/js/Ws_init.js"></script>

</body>
</html>