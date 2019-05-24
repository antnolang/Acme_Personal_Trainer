<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${rolActor=='trainer'}">
	<strong><spring:message code="application.customer" />:</strong>
	<jstl:out value="${application.customer.fullname}" />
	<br />
</jstl:if>

<strong><spring:message code="application.registeredMoment" />:</strong>
<spring:message code="application.formatMoment1" var="formatApplicationMoment"/>
		<fmt:formatDate value="${application.registeredMoment}" pattern="${formatApplicationMoment}"/>
<br />

<strong><spring:message code="application.status" />:</strong>
<jstl:out value="${application.status}" />
<br />

<strong><spring:message code="application.creditCard" />:</strong>
<a
	href="creditCard/customer/display.do?creditCardId=${application.creditCard.id}"><spring:message
		code="application.creditCard" /></a>
<br />		

<strong><spring:message code="application.workingOut" />:</strong>
<a href="workingOut/customer,trainer/display.do?workingOutId=${application.workingOut.id}"><jstl:out value="${application.workingOut.ticker}"/></a>
	<br/>
	
	<strong><spring:message code="application.comments" />:</strong>
<jstl:out value="${application.comments}" />
<br />

<br />
<!-- Links -->
<jstl:if test="${rolActor=='customer'}">
<a href="application/customer/list.do"> <spring:message
		code="application.return" />
</a>
</jstl:if>

<jstl:if test="${rolActor=='trainer'}">
<a href="application/trainer/list.do?workingOutId=${application.workingOut.id}"> <spring:message
		code="application.return" />
</a>
</jstl:if>
