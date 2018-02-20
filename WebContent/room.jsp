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
					<a class="navbar-brand" href="/Quiz-Connect/GameServlet2">QuizConnect</a>
					<button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1" style="background-color:#17a2b8; color: #ffffff" >
						<span class="sr-only">Toggle navigation</span>
						<i class="fa fa-bars" aria-hidden="true"></i>
					</button>
					<div class="collapse navbar-collapse" id="navcol-1">
						<ul class="nav navbar-nav ml-auto">
							<li class="nav-item">
								<span class="nav-link" ><b>ID Room:</b> <span id="idroom">922587</span></span>
							</li>
						</ul>
					</div>
				</div>
			</nav>

					<div id="wrapper">
						<div id="sidebar-wrapper">
							<ul class="sidebar-nav">
								<li>
									<h6 class="Tab_Title">Players / Score</h6>
									<div class="Tabscore_players">
										<table class="table" >
											<tbody id="scoreList">
											<tr>
												<td>&nbsp</td>
												<td>&nbsp</td>
											</tr>
											</tbody>
										</table>
									</div>
								</li>
								<li>
									<h6 class="Tab_Title">Parameters</h6>

									<div class="ParamStyle" >
										<div class="input-group">
											<span class="input-group-addon"><div class="ajust-input-group-addon"><div id="maxqst"></div></div></span>
											<input type="number" class="form-control" placeholder="Nb questions" id="nbQ" />
											<button class="btn btn-info" onclick="sendParameters()" id="SendPara" disabled>
											<span class="fa fa-arrow-right" ></span>
											</button>
										</div>
									</div>
									<h6 class="Tab_Title">Difficultes</h6>
									<div class="ParamStyle" >
										<div class="funkyradio">
										<c:forEach var="itm" items="${difficulties}">
											<div class="funkyradio-info">
												<input type="checkbox" name="ch_difficulties" id="${ itm }" onclick="getMaxQuestion()" value="${ itm }"/>
												<label for="${ itm }"><c:out value="${ itm }"></c:out></label>
											</div>
										</c:forEach>
										</div>
									</div>
									<h6 class="Tab_Title">Thematique</h6>
									<div class="ParamStyle" >
										<div class="funkyradio">
										
										<c:forEach var="itm" items="${themes}">
											<div class="funkyradio-info">
												<input type="checkbox" name="ch_themes" id="${ itm }" onclick="getMaxQuestion()" value="${ itm }" />
												<label for="${ itm }"><c:out value="${ itm }"></c:out></label>
											</div>
										</c:forEach>			
										</div>
									</div>

									<h6 class="Tab_Title">Langues</h6>
									<div class="ParamStyle" style="margin-bottom: 30%;" >
										<div class="funkyradio">
										<c:forEach var="itm" items="${langs}">
										
										<div class="funkyradio-info">
												<input type="checkbox" name="ch_langs" value="${ itm }" id="${ itm }" onclick="getMaxQuestion()"/>
												<label for="${ itm }"><c:out value="${ itm }"></c:out></label>
										</div>
										</c:forEach>
										</div>
									</div>
								</li>
							</ul>
						</div>

						<!-- GAME PAGE -->
						<div id="page-content-wrapper ">
							<div class="container-fluid ">
								<div class="row">
									<div class="col-lg-12 centering">
										<h1 id="question">Regles:</h1>
										<p class="regle" id="reponses" >Bienvenu dans le salon de jeu, pour vous permettre de repondre, il vous faut utilise votre smartphone a cette url, il vous faudra saisir l’id, qui se situe en haut a droite, de la room afin de la rejoindre.
											Vous aurez dix secondes pour repondre à chaque questions depuis votre smartphone.
											Have fun.
										</p>
										<h1 id="countdowntimer"></h1>
									</div>
								</div>
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