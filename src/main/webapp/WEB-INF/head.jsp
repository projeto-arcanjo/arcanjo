<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Arcanjo</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="${midasLocation}/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
	        page. However, you can choose any other skin. Make sure you
	        apply the skin class to the body tag so the changes take effect. -->
<link rel="stylesheet"
	href="${midasLocation}/dist/css/skins/skin-blue.min.css">



<!-- daterange picker -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/bootstrap-daterangepicker/daterangepicker.css">
<!-- bootstrap datepicker -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="${midasLocation}/plugins/iCheck/all.css">
<!-- Bootstrap Color Picker -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
<!-- Bootstrap time Picker -->
<link rel="stylesheet"
	href="${midasLocation}/plugins/timepicker/bootstrap-timepicker.min.css">




<!-- Select2 -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/select2/dist/css/select2.min.css">

<link rel="shortcut icon" type="image/x-icon"
	href="/resources/img/arcanjo-tn.png" />

<!-- Pace style -->
<link rel="stylesheet" href="${midasLocation}/plugins/pace/pace.min.css">

<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet" href="${midasLocation}/plugins/iCheck/all.css">


<!-- DataTables AdminLTE 2.4.5 -->
<link rel="stylesheet"
	href="${midasLocation}/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">

<link rel="stylesheet" href="${midasLocation}/sweetalert2.min.css">

<!-- TREANT -->
<link rel="stylesheet" href="/resources/treant/Treant.css"
	type="text/css" />

<!-- MAGNO -->
<link rel="stylesheet" href="/resources/style.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	  <![endif]-->

<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

<script type="text/javascript">
	String.prototype.replaceAll = function(de, para){
		var str = this;
		var pos = str.indexOf(de);
		while (pos > -1){
			str = str.replace(de, para);
			pos = str.indexOf(de);
		}
		return (str);
	}
</script>


</head>
