<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<link href="./Ressource/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="./Ressource/font-awesome/css/font-awesome.min.css" />

	<!-- core CSS -->
	<link rel="stylesheet" type="text/css" href="./Ressource/css/MainTheme.css" />
	<link rel="stylesheet" type="text/css" href="./Ressource/css/component.css" />
	<script src="./Ressource/js/snap.svg-min.js"></script>



	</head>
	<body>
		<div id="pagewrap" class="pagewrap">
		
			<nav class="navbar navbar-light navbar-expand-md navigation-clean">
				<div class="container">
					<a class="navbar-brand" href="/Quiz-Connect/GameServlet2">QuizConnect</a>
					<button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1" style="background-color:#17a2b8; color: #ffffff" >
						<span class="sr-only">Toggle navigation</span>
						<i class="fa fa-bars" aria-hidden="true"></i>
					</button>
					<div class="collapse navbar-collapse" id="navcol-1">
						<ul class="nav navbar-nav ml-auto">
							<li class="nav-item">
								<span class="nav-link" ><b>ID <fmt:message key="playroom.lobby" />:</b><span id="idroom" >000000</span></span>
							</li>
						</ul>
					</div>
				</div>
			</nav>

					<div id="wrapper">
						<!-- GAME PAGE -->
						<div id="page-content-wrapper ">
							<div class="container-fluid ">
								<div class="row">
									<div class="centering" >
										<div class="centering_child" id="reponses"><p class="regle" > <fmt:message key="playroom.attente" /></p></div>
									</div>

								</div>
							</div>
						</div>

				</div><!-- /page-2 -->
		</div><!-- /PageWrap-->

		<!-- Boostrap core JS -->
		<script type="text/javascript"src="Ressource/jquery/jquery.min.js"></script>
		<script type="text/javascript"src="Ressource/bootstrap/js/bootstrap.bundle.min.js"></script>

		<script src="./Ressource/js/Ws_engine.js"></script>

	</body>
</html>