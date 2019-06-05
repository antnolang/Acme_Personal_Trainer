<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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


	<p>
		<strong><spring:message code="creditCard.holder"/> :</strong>
		<jstl:out value="${creditCard.holderName}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.make"/>: </strong>
		<jstl:out value="${creditCard.brandName}"/>
	</p>
	
	<p>
		<jstl:set var="length" value="${fn:length(creditCard.number)}"/>
		<strong><spring:message code="creditCard.number"/> :</strong>
		<jstl:out value="****${fn:substring(creditCard.number, length - 4, length)}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.expirationMonth"/> :</strong>
		<jstl:out value="${creditCard.expirationMonth}"/>
	</p>
	
	<p>
		<strong><spring:message code="creditCard.expirationYear"/>: </strong>
		<jstl:out value="${creditCard.expirationYear}"/>
	</p>
	
	<a href="creditCard/customer/list.do">
		<spring:message	code="creditCard.back" />			
	</a>

	