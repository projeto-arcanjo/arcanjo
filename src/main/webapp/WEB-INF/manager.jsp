<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html manifest="">
<!-- PAGE HEAD -->
<jsp:include page="head.jsp" />
<style type="text/css">
/* show the move cursor as the user moves the mouse over the panel header.*/
#draggablePanelList {
	cursor: move;
}
</style>
<body class="skin-blue layout-top-nav">
	<div class="wrapper">

		<header class="main-header">
			<nav class="navbar navbar-static-top" role="navigation">
				<div class="container-fluid">

					<div class="navbar-header">
						<img src="/resources/img/logo-md-2.png"
							style="height: 45px; float: left; margin-top: 3px;"> <img
							src="/resources/img/logo-md-1.png"
							style="height: 43px; float: left; margin-right: 15px; margin-top: 7px;">
						<a style="font-size: 25px;" href="#" class="navbar-brand"><b>Arcanjo</b>|RTI</a>
					</div>


					<!--  BARRA DE BOTÕES DE FERRAMENTAS -->
					<div id="toolBarsContainer"
						style="float: left; width: auto; margin-top: 7px; margin-left: 25px;">
						<div id="toolBarStandard" class="btn-group"
							style="float: left; opacity: 0.6;">

							<button title="Início" id="toolGuia" type="button"
								class="btn btn-primary btn-flat">
								<i class="fa fa-home"></i>
							</button>

						</div>

					</div>

					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<!-- NOTIFICATIONS -->
							<!-- 
							<jsp:include page="notifications.jsp" />
							 -->
							<!-- USER DROP DOWN -->
							<jsp:include page="userdropdown.jsp" />
						</ul>
					</div>

				</div>
			</nav>
		</header>

		<div class="content-wrapper">

			<section class="content-header">
				<h1>
					Gerenciador | <small>Classes </small>
				</h1>
				<ol id="classBreadCrumb" class="breadcrumb" style="float:left"></ol>
			</section>


			<section class="content container-fluid">


				<div class="row">

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="box box-primary">
							<div class="box-header">
								<h3 class="box-title">
									<span id="currentRootClass">&nbsp;</span>
								</h3>
							</div>
							<div id="chihldrenClassContainer" class="box-body no-padding"
								style="height: 500px"></div>
						</div>
					</div>


					<div class="col-md-9 col-sm-12 col-xs-12">

						<div class="box box-primary">
							<div class="box-header">
								<h3 class="box-title">Detalhes</h3>
							</div>
							<div id="detailsCard" class="box-body table-responsive"></div>
						</div>

						<div class="box box-primary">
							<div class="box-header">
								<h3 class="box-title">Atributos</h3>
							</div>
							<div id="attributesCard" class="box-body table-responsive"></div>
						</div>

					</div>

				</div>


			</section>


		</div>
		<jsp:include page="footer.jsp" />

	</div>
	<jsp:include page="requiredscripts.jsp" />
</body>

<script src="/resources/script.js" type="text/javascript"></script>
<script src="/resources/manager.js" type="text/javascript"></script>

<script>
	$( document ).ready(function() {
		// Faxina de interface
		bindButtons();
	});
</script>

</html>
