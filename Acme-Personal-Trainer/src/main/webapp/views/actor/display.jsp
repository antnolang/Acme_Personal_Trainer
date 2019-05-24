<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<fieldset>
	<legend><spring:message code="actor.legend"/></legend>
	
	
	<p> <strong> <spring:message code="actor.fullname" />  </strong>  <jstl:out value="${actor.fullname}" /></p>

	<p> <strong> <spring:message code="actor.email" />  </strong>  <jstl:out value="${actor.email}" /></p>


	<jstl:if test="${!empty actor.photo }">
		<p>
			<strong> <spring:message code="actor.photo" /> 
			</strong> <img alt="Photo" src="<jstl:out value="${actor.photo}" />"
				height="200px" width="200px">
		</p>

	</jstl:if>

	<jstl:if test="${!empty actor.phoneNumber }">
		<p>
			<strong> <spring:message code="actor.phoneNumber" /> 
			</strong>
			<jstl:out value="${actor.phoneNumber}" />
		</p>
	</jstl:if>

	<jstl:if test="${!empty actor.address }">
		<p>
			<strong> <spring:message code="actor.address" /> 
			</strong>
			<jstl:out value="${actor.address}" />
		</p>
	</jstl:if>

	<security:authorize access="hasRole('ADMIN')">

		<jstl:if test="${isAuthorized == false }">
			<p>
				<strong> <spring:message code="actor.isSuspicious" /> 
				</strong>
				<jstl:if test="${actor.isSuspicious != null }">
					<jstl:if test="${actor.isSuspicious == true }">
						<spring:message code="actor.yes" />
					</jstl:if>
					<jstl:if test="${actor.isSuspicious == false }">
						<spring:message code="actor.no" />
					</jstl:if>
				</jstl:if>
				<jstl:if test="${actor.isSuspicious == null }">
					<jstl:out value="N/A" />
				</jstl:if>
			</p>
		</jstl:if>

		<jstl:if
			test="${actor.isSuspicious == true}">
			<jstl:if test="${ actor.userAccount.isBanned == false}">
				<a href="actor/administrator/ban.do?actorId=${actor.id}"><spring:message
						code="actor.ban" /></a>
			</jstl:if>
		</jstl:if>

		<jstl:if test="${actor.userAccount.isBanned}">
			<a href="actor/administrator/unBan.do?actorId=${actor.id}"><spring:message
					code="actor.unban" /></a>
		</jstl:if>

	</security:authorize>

	<jstl:if test="${isAuthorized == true}">
		<a
			href="actor/administrator,auditor,customer,nutritionist,trainer/edit.do?actorId=${actor.id}"><spring:message
				code="actor.edit" /></a>
	</jstl:if>
	<br>
	<br>
	<jstl:if test="${isAuthorized == true}">
		<a href="exportData/administrator,auditor,customer,nutritionist,trainer/export.do"><spring:message code="actor.exportData" /> </a>
	</jstl:if>
	
</fieldset>

<jstl:if test="${actor.userAccount.authorities=='[CUSTOMER]'}">
	<fieldset>
		<legend>
			<spring:message code="actor.customer.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.customer.isPremium" />
			</strong>
			<jstl:if test="${actor.isPremium == true }">
				<spring:message code="actor.yes" />
			</jstl:if>
			<jstl:if test="${actor.isPremium == false }">
				<spring:message code="actor.no" />
			</jstl:if>
			
		</p>

	</fieldset>
</jstl:if>

<jstl:if test="${actor.userAccount.authorities=='[TRAINER]'}">
	<fieldset>
		<legend>
			<spring:message code="actor.trainer.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.trainer.mark" />
			</strong>
			<jstl:if test="${actor.mark != null }">
					<jstl:out value="${actor.mark}" />
			</jstl:if>
			<jstl:if test="${actor.mark == null }">
				<jstl:out value="N/A" />
			</jstl:if>
		</p>
		
		
		<security:authorize access="hasRole('ADMIN')">
		<p>
			<strong> <spring:message code="actor.trainer.score" />
			</strong>
			<jstl:if test="${actor.score != null }">
					<jstl:out value="${actor.score}" />
			</jstl:if>
			<jstl:if test="${actor.score == null }">
				<jstl:out value="N/A" />
			</jstl:if>
		</p>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
		<p>
			<strong> <spring:message code="actor.trainer.workingOuts" />
			</strong> <a href="workingOut/customer/list.do?trainerId=${actor.id}"><spring:message
					code="table.workingOuts" /></a>
		</p>
		</security:authorize>
		
		<jstl:if test="${trainerAttended}">
		<p>
			<strong> <spring:message code="actor.trainer.endorsements" />
			</strong> <a href="endorsement/list.do?trainerId=${actor.id}"><spring:message
					code="table.endorsements" /></a>
		</p>
		</jstl:if>
	</fieldset>
</jstl:if>

<security:authorize access="hasAnyRole('NUTRITIONIST')">
<jstl:if test="${actor.userAccount.authorities=='[NUTRITIONIST]' && (isAuthorized == true) }">
	<fieldset>
		<legend>
			<spring:message code="actor.nutritionist.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.nutritionist.articles" />
			</strong> <a href="article/nutritionist/list.do"><spring:message
					code="table.articles" /></a>
		</p>

	</fieldset>
</jstl:if>
</security:authorize>

<fieldset>
	<legend><spring:message code="userAccount.legend"/></legend>
	<p> <strong> <spring:message code="actor.username" />: </strong>  <jstl:out value="${actor.userAccount.username}" /></p>
	
	<p> <strong> <spring:message code="actor.authority" />: </strong>  <jstl:out value="${actor.userAccount.authorities}" /></p>

</fieldset>

<fieldset>
	<legend><spring:message code="other.legend"/></legend>
	<p> <strong> <spring:message code="actor.socialProfiles" />: </strong>  <a href="socialProfile/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a></p>

</fieldset>

