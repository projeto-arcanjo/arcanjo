<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html manifest="">
   <jsp:include page="head.jsp" />
   <body class="hold-transition" style="margin:0px; padding:0px">
   
      <div  class="page-wrap">
      
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
			    
			    <div style="position:absolute; width:100%; height:80%" id="particles-js"></div>
			         
		         <div class="container navless-container" style="padding: 65px 15px;max-width: 960px;">
					      
		            <section class="content">
		               <div class="row">
		                  <div class="col-sm-7 brand-holder">
		                     <h1>
		                        <img style="width:50px;" src="/resources/img/arcanjo.png"> Plataforma Arcanjo
		                     </h1>
		                     <h3>Plataforma de Simulação</h3>
		                     <p class="text-justify">
								Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque eu lacinia diam, sit amet elementum risus. 
								Maecenas varius iaculis lacus, vitae vulputate ipsum. Sed eget justo id erat dapibus lacinia. Nullam a leo fringilla, 
								molestie enim sit amet, ultricies velit. You shall use 'admin@admin' as username, you shall use 'admin' as password. 
								Etiam condimentum ex ut finibus feugiat. Curabitur sit amet nulla ut nisi porta faucibus tincidunt sed arcu. 
								In diam odio, vestibulum a auctor sit amet, tristique vitae felis. Nulla accumsan vitae purus vitae viverra.
		                     </p>
		                  </div>
		                  <div class="col-sm-5">
		                     <div class="box box-primary">
                                 <form action="login" role="form" method="post">
	                              <div class="box-body">
                                    <div class="form-group">
                                       <label for="user_login">Usuário</label>
                                       <input placeholder="Username" class="form-control" autocomplete="off" autofocus="autofocus" autocapitalize="off" autocorrect="off" required="required" name="username" id="user_login" type="text">
                                    </div>
                                    <div class="form-group">
                                       <label for="user_password">Senha</label>
                                       <input autocomplete="off" placeholder="Password" class="form-control" required="required" name="password" id="user_password" type="password">
                                    </div>
                                    
                                    <div class="box-footer">
                                       <input name="commit" value="Login" class="btn btn-block btn-success btn-sm" type="submit">
                                       <input type="hidden" class="form-control" name="error" value="">
                                    </div>
                                    
                                    <div style="float:left"><a href="#">[ Esqueci a Senha ]</a></div>
                                    <div style="float:right"><a href="#">[ Registrar ]</a></div>
                                    
                                   </div> 
                                 </form>
		                     </div>
		                  </div>
		               </div>
		            </section>
            
            
            	</div>
            
            
         </div>
         
         
         <hr class="footer-fixed">
         <div class="container footer-container">
   <div class="pull-right hidden-xs">
     Powered By <a target="_BLANK" href="http://timpokorny.github.io/public/about/index.html">Calytrix's PorticoRTI</a>
   </div>
   <!-- Default to the left -->
   <strong>Plataforma de Simulação Arcanjo</strong> 
         </div>
         
         
      </div>
      <jsp:include page="requiredscripts.jsp" />
      
      <script>
	    particlesJS.load('particles-js', '/resources/particles/particles-config.json', function() {
    		 //
    	});
      </script>
      
      
   </body>
   
   
   
</html>

