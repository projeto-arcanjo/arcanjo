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
				
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="box box-primary">
							<div class="box-header"><h3 class="box-title">Counter</h3></div>
							<div class="box-body no-padding">
					              <ul class="nav nav-pills nav-stacked">
					                <li><a href="#"><i class="fa fa-bolt"></i> Interactions <span id="totalInteractionsCouter" class="label label-primary pull-right">0</span></a></li>
					                <li><a href="#"><i class="fa fa-map-pin"></i> Attributes <span id="totalAttributesCounter" class="label label-primary pull-right">0</span></a></li>
					                <li><a href="#"><i class="fa fa-circle-o"></i> Empty <span class="label label-primary pull-right">0</span></a></li>
					                <li><a href="#"><i class="fa fa-circle-o"></i> Empty <span class="label label-primary pull-right">0</span></a></li>
					                <li><a href="#"><i class="fa fa-circle-o"></i> Empty <span class="label label-primary pull-right">0</span></a></li>
					              </ul>							
							</div>
						</div>
					</div>
				
				
					<div class="col-md-9 col-sm-12 col-xs-12">
					
						<div class="box box-primary">
							<div class="box-header"><h3 class="box-title">Interactions</h3></div>
							<div class="box-body table-responsive">
								<table id="interactionsTable" class="tableModule">
									<thead>
									<tr class="fieldsModuleHeader">
										<th style="width:25%;border: 1px solid">Handle</th>
										<th style="width:25%;border: 1px solid">Name</th>
										<th style="width:50%;border: 1px solid">Parameters</th>
									</tr>
									<thead>
									<tbody></tbody>	
								</table>			
							</div>
						</div>	
						<div class="box box-primary">
							<div class="box-header"><h3 class="box-title">Attributes</h3></div>
							<div class="box-body table-responsive">
								<table id="attributesTable" class="tableModule">
									<thead>
									<tr class="fieldsModuleHeader">
										<th style="width:25%;border: 1px solid">Handle</th>
										<th style="width:25%;border: 1px solid">Name</th>
										<th style="width:25%;border: 1px solid">Data Type</th>
										<th style="width:25%;border: 1px solid">Value</th>
									</tr>
									<thead>
									<tbody></tbody>	
								</table>			
							</div>
						</div>					
										
					</div>
					
				</div>

				
			</section>
			
			
		</div>
		<jsp:include page="footer.jsp" />

	</div>
	<jsp:include page="requiredscripts.jsp" />
</body>


<script src="/resources/sockjs.min.js" type="text/javascript"></script>
<script src="/resources/stomp.min.js" type="text/javascript"></script>
<script src="/resources/script.js"></script>
<script src="/resources/toast.js"></script>
<script src="/resources/dashboard.js"></script>
</html>

