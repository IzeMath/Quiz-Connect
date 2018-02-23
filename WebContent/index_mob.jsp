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
	<meta name="keywords" content="quiz, quizz, mobile quiz, blind test, blind-test" />
	
	<!-- Bootstrap core CSS -->
	<link href="Ressource/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="Ressource/font-awesome/css/font-awesome.min.css" />


	<!-- core CSS -->
	<link rel="stylesheet" type="text/css" href="./Ressource/css/MainTheme.css" />
	<link rel="stylesheet" type="text/css" href="./Ressource/css/component.css" />
	<script src="./Ressource/js/snap.svg-min.js"></script>
		
		<!--[if IE]>
	  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
			<![endif]-->
			
			<script>
				var isConnect = false;
			</script>

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
									<script>
										isConnect = false;
									</script>
								</c:when>
								<c:otherwise>
									<li class="nav-item"><a class="nav-link" href="LogoutServlet" id="userEmail"><c:out value="${user.getEmail() }" /></a>
									</li>
									<script>
										isConnect = true;
									</script>
								</c:otherwise>
							</c:choose>
								</ul>
							</div>
						</div>
					</nav>

				<div class="centering_child" >
						<div class="input-group Container_Mob">
						<fmt:message key="indexmob.name" var="nameForPlay" />
								<input type="text" id="NameOfMobile" name="NameOfMobile" class="form-control" placeholder="${nameForPlay}" />
					</div>
					<div class="input-group Container_Mob">
					<fmt:message key="indexmob.id" var="idForPlay" />
						<input type="number" id="forbtnGoMob" name="IDROOM" class="form-control" placeholder="${idForPlay}" required />	
					</div>
						<div class="input-group Container_Mob">
						<fmt:message key="indexmob.password" var="passForPlay" />
								<input type="text" id="MdpOfMobile" name="MDPROOM" class="form-control" placeholder="${passForPlay}" />
								<span class="input-group-btn">
								<button type="button" class="btn btn-info" onclick="joinRoomMob()"> >
								<span>GO</span>
								</button>
								</span>
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
		<!-- Boostrap core JS -->
		<script type="text/javascript"src="./Ressource/jquery/jquery.min.js"></script>
		<script type="text/javascript"src="./Ressource/bootstrap/js/bootstrap.bundle.min.js"></script>
		<script type="text/javascript" src="./Ressource/js/Detect_mob.js"></script>
		<script type="text/javascript" src="./Ressource/js/Ws_init.js"></script>


	</body>
</html>