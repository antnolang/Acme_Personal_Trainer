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

	<p> <strong> <spring:message code="actor.VATnumber" />  </strong>  <jstl:out value="${actor.VATnumber}" />%</p>

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
				<strong> <spring:message code="actor.isSpammer" /> 
				</strong>
				<jstl:if test="${actor.isSpammer != null }">
					<jstl:out value="${actor.isSpammer}" />
				</jstl:if>
				<jstl:if test="${actor.isSpammer == null }">
					<jstl:out value="N/A" />
				</jstl:if>
			</p>
		</jstl:if>

		<jstl:if
			test="${actor.isSpammer == true}">
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
			href="actor/administrator,auditor,company,provider,rookie/edit.do?actorId=${actor.id}"><spring:message
				code="actor.edit" /></a>
	</jstl:if>
	
	<jstl:if test="${isAuthorized == true}">
		<a href="exportData/administrator,company,rookie/export.do"><spring:message code="actor.exportData" /> </a>
	</jstl:if>
	
</fieldset>

<jstl:if test="${actor.userAccount.authorities=='[COMPANY]'}">
	<fieldset>
		<legend>
			<spring:message code="actor.company.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.company.commercialName" />
			</strong>
			<jstl:out value="${actor.commercialName}" />
		</p>
		
		<p>
			<strong> <spring:message code="actor.company.auditScore" />
			</strong>
			<jstl:if test="${actor.auditScore == null }">
				<jstl:out value="N/A" />
			</jstl:if>
			<jstl:if test="${actor.auditScore != null }">
				<jstl:out value="${actor.auditScore}" />
			</jstl:if>
			
		</p>


		<p>
			<strong> <spring:message code="actor.company.positions" />
			</strong> <a href="position/list.do?companyId=${actor.id}"><spring:message
					code="table.positions" /></a>
		</p>



	</fieldset>
</jstl:if>

<jstl:if test="${actor.userAccount.authorities=='[PROVIDER]'}">
	<fieldset>
		<legend>
			<spring:message code="actor.provider.legend" />
		</legend>
		<p>
			<strong> <spring:message code="actor.provider.make" />
			</strong>
			<jstl:out value="${actor.make}" />
		</p>
		
		<p>
			<strong> <spring:message code="actor.provider.items" />
			</strong> <a href="item/list.do?providerId=${actor.id}"><spring:message
					code="provider.items" /></a>
		</p>
		
	</fieldset>
</jstl:if>

<jstl:if test="${isAuthorized == true}">
<fieldset>
	<legend><spring:message code="creditCard.legend"/></legend>
	
	<p>
		<strong><spring:message code="creditCard.holder"/> </strong>
		<jstl:out value="${actor.creditCard.holder}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.make"/> </strong>
		<jstl:out value="${actor.creditCard.make}"/>
	</p>
	
	<p>
		<jstl:set var="length" value="${fn:length(actor.creditCard.number)}"/>
		<strong><spring:message code="creditCard.number"/> </strong>
		<jstl:out value="****${fn:substring(actor.creditCard.number, length - 4, length)}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.expirationMonth"/> </strong>
		<jstl:out value="${actor.creditCard.expirationMonth}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.expirationYear"/> </strong>
		<jstl:out value="${actor.creditCard.expirationYear}"/>
	</p>

	<a href="creditCard/administrator,auditor,company,provider,rookie/edit.do?actorId=${actor.id}"><spring:message
				code="actor.creditCard.edit" /></a>

	</fieldset>
</jstl:if>

<fieldset>
	<legend><spring:message code="userAccount.legend"/></legend>
	<p> <strong> <spring:message code="actor.username" />: </strong>  <jstl:out value="${actor.userAccount.username}" /></p>
	
	<p> <strong> <spring:message code="actor.authority" />: </strong>  <jstl:out value="${actor.userAccount.authorities}" /></p>

</fieldset>

<fieldset>
	<legend><spring:message code="other.legend"/></legend>
	<p> <strong> <spring:message code="actor.socialProfiles" />: </strong>  <a href="socialProfile/list.do?actorId=${actor.id}"><spring:message code="actor.socialProfiles"/></a></p>

</fieldset>

