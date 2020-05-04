<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
       <!-- User Account Menu -->
       <li class="dropdown user user-menu">
         <!-- Menu Toggle Button -->
         <a href="#" class="dropdown-toggle" data-toggle="dropdown">
           <!-- The user image in the navbar-->
           <img src="/resources/img/nophoto.png" class="user-image" alt="User Image">
           <!-- hidden-xs hides the username on small devices so only the image appears. -->
           <span class="hidden-xs">${user.fullName}</span>
         </a>
         <ul class="dropdown-menu">
           <!-- The user image in the menu -->
           <li class="user-header">
             <img style="width:100px;height:100px" src="/resources/img/nophoto.png" class="img-circle" alt="User Image">
             <p>
	<small>${user.fullName}</small>
	<security:authorize access="hasRole('ROLE_ADMIN')">
	           <small>ADMIN</small>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_USER')">
	           <small>USER</small>
	</security:authorize>	                  
             </p>
           </li>
           <li class="user-footer">
             <div class="pull-left">
               <a href="#" class="btn btn-default btn-flat">Perfil</a>
             </div>
             <div class="pull-right">
               <a href="/logout" class="btn btn-default btn-flat">Sair</a>
             </div>
           </li>
         </ul>
       </li>
