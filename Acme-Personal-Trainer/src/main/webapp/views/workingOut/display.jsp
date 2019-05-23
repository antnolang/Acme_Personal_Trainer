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

<spring:message code="workingOut.formatMoment1" var="formatMoment"/>

<jstl:if test="${isApplied}">
		<h2>
			<a href="application/customer/create.do?workingOutId=${workingOut.id}"><spring:message code="workingOut.apply" /></a>
		</h2>
	</jstl:if>
<strong><spring:message code="workingOut.trainerName"/>:</strong>
		<a href="actor/display.do?actorId=${workingOut.trainer.id}"><jstl:out value="${workingOut.trainer.name}"/></a>
	<br/>
	
	<strong><spring:message code="workingOut.ticker"/>:</strong>
		<jstl:out value="${workingOut.ticker}"/>
	<br/>
	
	<strong><spring:message code="workingOut.publishedMoment"/>:</strong>
		<fmt:formatDate value="${workingOut.publishedMoment}" pattern="${formatMoment}"/>
		
	<br/>	
	<strong><spring:message code="workingOut.startMoment"/>:</strong>
		<fmt:formatDate value="${workingOut.startMoment}" pattern="${formatMoment}"/>
		<br/>
	<strong><spring:message code="workingOut.endMoment"/>:</strong>
		<fmt:formatDate value="${workingOut.endMoment}" pattern="${formatMoment}"/>
		
<br/>
	<strong><spring:message code="workingOut.description"/>:</strong>
		<jstl:out value="${workingOut.description}"/>
	<br/>
	
	<spring:message code="workingOut.vat" var="vatTag"/>
	<strong><spring:message code="workingOut.price"/>:</strong>
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${workingOut.price * (1 + VAT /100)}"/> &#8364; <jstl:out value="(${VAT}%  ${vatTag}  Inc.)"/>
	<br/>
	
	
	<security:authorize access="hasRole('TRAINER')">
	<jstl:if test="${principal == workingOut.trainer}">
		<strong><spring:message code="workingOut.finalMode"/>:</strong>
			<jstl:out value="${workingOut.isFinalMode}"/>
		<br/>
		
		<strong><spring:message code="workingOut.applications"/>:</strong>
		<a href="application/trainer/list.do?workingOutId=${workingOut.id}"><spring:message code="workingOut.applications"/></a>
	<br/>
	</jstl:if>
	</security:authorize>
	
	
	<br/>
	
<display:table name="categories" id="row1" requestURI="workingOut/customer,trainer/display.do" class="displaytag" pagesize="5">
		
	<display:column property="name" titleKey="workingOut.name" />
</display:table>
	
	
<display:table name="sessions" id="row2" requestURI="workingOut/customer,trainer/display.do" class="displaytag" pagesize="5">

	<security:authorize access="hasRole('TRAINER')">
	<display:column>
		<jstl:if test="${principal == workingOut.trainer && !workingOut.isFinalMode}">
		<a href="session/trainer/edit.do?sessionId=${row2.id}">
			<spring:message	code="workingOut.edit" />			
		</a>
	
		</jstl:if>
		</display:column>
	</security:authorize>	
	
	<display:column property="title" titleKey="workingOut.title" />
	
	<display:column property="description" titleKey="workingOut.description" />
	
	<display:column property="address" titleKey="workingOut.address" />
	
	<spring:message code="workingOut.formatMoment" var="formatMoment" />
	<display:column property="startMoment" titleKey="workingOut.startMoment" sortable="true" format="${formatMoment}"/>
	
	<spring:message code="workingOut.formatMoment" var="formatMoment" />
	<display:column property="endMoment" titleKey="workingOut.endMoment" sortable="true" format="${formatMoment}"/>
</display:table>

<security:authorize access="hasRole('TRAINER')">
	<jstl:if test="${principal == workingOut.trainer && !workingOut.isFinalMode}">
	<a href="session/trainer/create.do?workingOutId=${workingOut.id}">
		<spring:message	code="workingOut.create.session" />			
	</a>
	
	</jstl:if>
</security:authorize>	
	
	<!-- Links -->	
	
<security:authorize access="hasRole('TRAINER')">

	<a href="workingOut/trainer/list.do">
		<spring:message	code="workingOut.back" />			
	</a>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="workingOut/customer/listAvailable.do">
		<spring:message	code="workingOut.back" />			
	</a>
</security:authorize>
	
	