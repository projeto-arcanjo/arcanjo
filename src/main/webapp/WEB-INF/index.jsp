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
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<nav class="navbar navbar-static-top" role="navigation">
				<jsp:include page="pagelogo.jsp" />

				<a href="#" class="sidebar-toggle" data-toggle="push-menu"
					role="button"> <span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a>

				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- NOTIFICATIONS -->
						<jsp:include page="notifications.jsp" />
						<!-- USER DROP DOWN -->
						<jsp:include page="userdropdown.jsp" />
					</ul>
				</div>
			</nav>
		</header>

		<jsp:include page="mainsidebar.jsp" />

		<div class="content-wrapper">

			<section class="content container-fluid">
			
				 
				<div class="row">
		              <div class="col-md-12 col-sm-12 col-xs-12">
			              <a class="btn btn-flat">
			                <i class="fa fa-play"></i>
			              </a>		              
			              <a class="btn btn-flat">
			                <i class="fa fa-stop"></i> 
			              </a>		              
			              <a onclick="refreshData();" href="#" class="btn btn-flat">
			                <i class="fa fa-repeat"></i>
			              </a>		              
		              </div>
				</div>
 				
 				
				<div class="row">
					<!--  https://ionicons.com/v2/cheatsheet.html  -->


					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="small-box bg-light-blue">
							<div class="inner">
								<h3 id="totalModules">0</h3>
								<p>Modules</p>
							</div>
							<div class="icon">
								<i class="ion ion-cube"></i>
							</div>
							<a onclick="showModules();" href="#" class="small-box-footer">More <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
					
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="small-box bg-light-blue">
							<div class="inner">
								<h3 id="totalClasses">0</h3>
								<p>Classes</p>
							</div>
							<div class="icon">
								<i class="ion ion-ios-box"></i>
							</div>
							<a onclick="showClasses();" href="#" class="small-box-footer">More <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="small-box bg-light-blue">
							<div class="inner">
								<h3 id="totalObjects">0</h3>
								<p>Instances</p>
							</div>
							<div class="icon">
								<i class="ion ion-android-car"></i>
							</div>
							<a onclick="showInstances();" href="#"	class="small-box-footer">More <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="small-box bg-light-blue">
							<div class="inner">
								<h3 id="totalInteractions">0</h3>
								<p>Interactions</p>
							</div>
							<div class="icon">
								<i class="ion ion-arrow-graph-up-right"></i>
							</div>
							<a onclick="showInteractions();" href="#" class="small-box-footer">More <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>



				</div>
				
				<div id="mainContentData" class="row"></div>

				<div id="mainContentDataLine2" class="row"></div>
				
			</section>
			
			
		</div>
		<jsp:include page="footer.jsp" />

	</div>
	<jsp:include page="requiredscripts.jsp" />
</body>


<script src="/resources/sockjs.min.js" type="text/javascript"></script>
<script src="/resources/stomp.min.js" type="text/javascript"></script>
<script src="/resources/script.js"></script>
<script src="/resources/modulemanager.js"></script>
<script src="/resources/interactionmanager.js"></script>
<script src="/resources/classmanager.js"></script>
<script src="/resources/instancemanager.js"></script>
<script src="/resources/toast.js"></script>
<script src="/resources/treemaker.js"></script>
<script src="/resources/interactiontreemaker.js"></script>
</html>

