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
					<div id="toolBarsContainer"	style="float:left;width: auto;margin-top: 7px;margin-left: 25px;">
						<div id="toolBarStandard" class="btn-group"
							style="float: left; opacity: 0.6;">
							
							<button title="Virtual" id="toolGuia" type="button" class="btn btn-primary btn-flat"> 
								<i class="fa fa-fighter-jet"></i>
							</button>
							
							<button title="Construtiva" id="toolEdgvBook" style="margin-left:10px;"
								type="button" class="btn btn-primary btn-flat">
								<i class="fa fa-flag-checkered"></i>
							</button>
							
						</div>
											
					</div>
					
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<!-- NOTIFICATIONS -->
							<jsp:include page="notifications.jsp" />
							<!-- USER DROP DOWN -->
							<jsp:include page="userdropdown.jsp" />
						</ul>
					</div>					
					
				</div>
					
					
			</nav>
		</header>

		<div class="content-wrapper">
			<div style="width:100%; height:100%" id="cesiumContainer">
			
			
						<div id="bottonBar"
							style="width: 100%; position: absolute; z-index: 9999; font-family: Consolas; font-size: 11px; bottom: 55px; height: 15px; color: black;">

							<div class="layerCounter" id="layerLoadingPanelGau" style="width: 150px; display:none;float: left; margin-left: 20px;margin-right:5px;padding-top:3px">
								<div class='progress progress-sm active'>
									<div class='progress-bar progress-bar-red progress-bar-striped' role='progressbar' aria-valuenow='100' aria-valuemin='0' aria-valuemax='100' style='width: 100%'></div>
								</div>
							</div>
							<div class="layerCounter" id="layerLoadingPanelCnt" style="width: 50px; float: left;display:none">
								<div id="lyrCount" style="width: 50px; float: left">0</div>
							</div>


							<div id="latLonPanel"
								style="width: 210px; float: right; margin-right: 20px;">
								<div id="mapLat"
									style="width: 100px; text-align: right; float: left"></div>
								<div id="mapLon"
									style="width: 100px; text-align: right; float: left"></div>
							</div>
							
						</div>			
			
			
			
			
			</div>
		</div>

		<jsp:include page="footer.jsp" />

	</div>
	<jsp:include page="requiredscripts.jsp" />
</body>


<script src="/resources/Cesium/Cesium.js" type="text/javascript"></script>
<script src="/resources/sockjs.min.js" type="text/javascript"></script>
<script src="/resources/stomp.min.js" type="text/javascript"></script>
<script src="/resources/convertions.js" type="text/javascript"></script>
<script src="/resources/globe.js" type="text/javascript"></script>
<script src="/resources/toast.js" type="text/javascript"></script>
<script src="/resources/aircrafts.js" type="text/javascript"></script>
<script src="/resources/test/teste.js" type="text/javascript"></script>

<script>
$( document ).ready(function() {

	// Conecta o WebSockets
	connect();
	// Inicia o Cesium
	startMap();
	// Ajusta o viewport do cesium
	applyMargins();

	// Leva o mapa para a area inicial
	goToOperationArea( homeLocation );


	// Carrega um teste com dummies
	loadTest();   
});
</script>


</html>

