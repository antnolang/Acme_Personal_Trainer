<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme Personal Trainer Co., Inc." height="200px" width="700px"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customisation/administrator/display.do"> <spring:message code="master.page.customisation" /> </a></li>
					<li><a href="actor/administrator/registerAdministrator.do"><spring:message code="master.page.administrator.create" /></a></li>
					<li><a href="actor/administrator/registerAuditor.do"><spring:message code="master.page.auditor.create" /></a></li>
					<li><a href="actor/administrator/registerNutritionist.do"><spring:message code="master.page.nutritionist.create" /></a></li>
					<li><a href="actor/administrator/list.do"><spring:message code="master.page.actor.list" /></a></li>

					<!-- Añadir enlaces -->
					<li><a href="/category/administrator/list.do"><spring:message code="master.page.category" /> </a></li>

				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/customer/list.do"><spring:message code="master.page.application.list" /></a></li>
				</ul>

			</li>
			<li><a class="fNiv"><spring:message	code="master.page.creditCard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="creditCard/customer/list.do"><spring:message code="master.page.creditCard.list" /></a></li>
				</ul>

			</li>
			<li><a class="fNiv"><spring:message	code="master.page.workingOut" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="workingOut/customer/list.do"><spring:message code="master.page.workingOut.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv" href="endorsement/customer,trainer/list.do"><spring:message code="master.page.endorsement.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('TRAINER')">
			<li><a class="fNiv"><spring:message	code="master.page.workingOut" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="workingOut/trainer/list.do"><spring:message code="master.page.workingOut.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv" href="endorsement/customer,trainer/list.do"><spring:message code="master.page.endorsement.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>	
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/registerCustomer.do"><spring:message code="master.page.customer.create" /></a></li>
					<li><a href="actor/registerTrainer.do"><spring:message code="master.page.trainer.create" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<!-- Añadir enlaces -->
					<li><a href="actor/display.do"><spring:message code="master.page.actor.display" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>