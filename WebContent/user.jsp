<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>UserPage</title>
<link rel="stylesheet" href="Ressource/bootstrap/css/bootstrap.min.css">
</head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="Ressource/js/Chart.js"></script>
<script src="Ressource/js/User_chart.js"></script>

<body style="background-color: #17a2b8;">
	<div>
		<nav class="navbar navbar-light navbar-expand-md navigation-clean">
		<div class="container">
			<a class="navbar-brand" href="#">Company Name</a>
			<button class="navbar-toggler" data-toggle="collapse"
				data-target="#navcol-1">
				<span class="sr-only">Toggle navigation</span><span
					class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navcol-1">
				<ul class="nav navbar-nav ml-auto">
					<li class="nav-item" role="presentation"><a
						class="nav-link active" href="#">First Item</a></li>
					<li class="nav-item" role="presentation"><a class="nav-link"
						href="#">Second Item</a></li>
					<li class="nav-item" role="presentation"><a class="nav-link"
						href="#">Third Item</a></li>
					<li class="dropdown"><a
						class="dropdown-toggle nav-link dropdown-toggle"
						data-toggle="dropdown" aria-expanded="false" href="#">Dropdown</a>
						<div class="dropdown-menu" role="menu">
							<a class="dropdown-item" role="presentation" href="#">First
								Item</a><a class="dropdown-item" role="presentation" href="#">Second
								Item</a><a class="dropdown-item" role="presentation" href="#">Third
								Item</a>
						</div></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>
	<div class="container">
		<div class="row" style="margin-top: 25px; margin-bottom: 25px;">
			<div class="col-sm-12 col-md-4 col-lg-4 col-xl-4"
				style="margin-bottom: 10px;">
				<div class="card" style="height: 100%;">
					<div class="card-body">
						<div class="row" style="margin: 5px;">
							<div class="col">
								<h1 class="text-nowrap text-truncate">UserName</h1>
							</div>
						</div>
						<div class="row" style="margin: 5px;">
							<div class="col">
								<img src="profile-42914_640.png" width="200px" height="200px"
									class="img-fluid d-flex mx-auto"
									style="background-color: #2b7e84;" />
							</div>
						</div>
						<div class="row" style="margin: 5px;">
							<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
								<button class="btn btn-info" type="button" style="width: 100%;">Editer
									le profil</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-8 col-lg-8 col-xl-8">
				<div class="card" style="height: 100%;">
					<div class="card-body">
						<div class="row" style="margin: 5px;">
							<div class="col">
								<h1 class="text-nowrap text-truncate">UserName</h1>
							</div>
						</div>
						<div class="row" style="margin: 5px;">
							<div class="col-12">
								<div id="canvas-holder">
									<canvas id="chart-area"></canvas>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6">
				<div class="card" style="height: 100%;">
					<div class="card-body">
						<div class="row" style="margin: 5px;">
							<div class="col">
								<h1 class="text-nowrap text-truncate">UserName</h1>
							</div>
						</div>
						<div class="row" style="margin: 5px;">
							<div class="col">
								<img src="profile-42914_640.png" width="200px" height="200px"
									class="img-fluid d-flex mx-auto"
									style="background-color: #2b7e84;" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">Tableau peut etre</h4>
						<div class="table-responsive">
							<table class="table table-striped table-sm">
								<thead>
									<tr>
										<th>Column 1</th>
										<th>Column 2</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Cell 1</td>
										<td>Cell 2</td>
									</tr>
									<tr>
										<td>Cell 3</td>
										<td>Cell 4</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>