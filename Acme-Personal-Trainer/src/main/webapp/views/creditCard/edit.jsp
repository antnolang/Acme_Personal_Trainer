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

<form:form action="creditCard/administrator,auditor,company,provider,rookie/edit.do" modelAttribute="creditCardForm">

	<fieldset>
		<legend><spring:message code="creditCard.legend"/></legend>
		
		<acme:textbox code="creditCard.holder.requested" path="holder"/>
		<br>
		
		<acme:textbox code="creditCard.make.requested" path="make"/>
		<br>
		
		<acme:textbox code="creditCard.number.requested" path="number"/>
		<br>
		
		<acme:textbox code="creditCard.expirationMonth.requested" path="expirationMonth"/>
		<br>
		
		<acme:textbox code="creditCard.expirationYear.requested" path="expirationYear"/>
		<br>
		
		<acme:textbox code="creditCard.cvvCode.requested" path="cvvCode"/>
		<br>
	
	</fieldset>
	
 
 
 	<acme:submit name="save" code="creditCard.save"/>
	
	<acme:cancel url="actor/display.do" code="creditCard.cancel"/>
 
	
	
</form:form>