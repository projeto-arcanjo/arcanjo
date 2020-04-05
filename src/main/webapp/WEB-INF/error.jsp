<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html manifest="">

	<head>
		<meta charset="utf-8">
		<title>Guardião OAuth Server</title>
	  
		  <!-- Bootstrap 3.3.7 -->  
		  <link rel="stylesheet" href="${midasLocation}/bower_components/bootstrap/dist/css/bootstrap.min.css">
		  <!-- Font Awesome -->
		  <link rel="stylesheet" href="${midasLocation}/bower_components/font-awesome/css/font-awesome.min.css">
		  <!-- Ionicons -->
		  <link rel="stylesheet" href="${midasLocation}/bower_components/Ionicons/css/ionicons.min.css">
		  <!-- Theme style -->
		  <link rel="stylesheet" href="${midasLocation}/dist/css/AdminLTE.min.css">
		  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
		        page. However, you can choose any other skin. Make sure you
		        apply the skin class to the body tag so the changes take effect. -->
		  <link rel="stylesheet" href="${midasLocation}/dist/css/skins/skin-blue.min.css">
		  
	</head>

   <body class="hold-transition" style="margin:0px; padding:0px">
   
      <div class="page-wrap">
      
         <header class="navbar fixed-top navbar-empty">
            <div class="container">
               <div class="mx-auto">
                  <div style="margin:0 auto; width:50px;height:50px">
                     <!-- <img style="width:40px;height:40px" src="/resources/img/logo.png"> -->
                  </div>
               </div>
            </div>
         </header>
         
			<div style="min-height: calc(100vh - 150px);">
			         
		         <div class="container navless-container" style="padding: 65px 15px;max-width: 960px;">
		         
		            <section class="content">
		                     <h1 class="text-red">
		                        <img style="width:40px;height:40px" src="/resources/img/hades-logo.png"> ${status} | Erro ao processar esta requisição
		                     </h1>
		                     <h3>O sistema encontrou um erro ao processar sua requisição.</h3>
		                     <p class="text-justify">
		                        ${error} <br>
		                        ${message}  
		                     </p>
		            </section>
            
            
            	</div>
            
            
         </div>
         <hr class="footer-fixed">
         <div class="container footer-container">
            <div class="footer-links">
               Sistema Single Sign-On Cerberus 
            </div>
         </div>
      </div>


	<!-- jQuery 3 -->
	<script src="${midasLocation}/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="${midasLocation}/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${midasLocation}/dist/js/adminlte.min.js"></script>

   </body>
</html>

