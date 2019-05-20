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

<form:form action="creditCard/customer/edit.do" modelAttribute="creditCard">


		
		<acme:textbox code="creditCard.holder" path="holderName"/>
		<br>
		
		<acme:selectPrime items="${makes}" code="creditcard.make" path="creditCard.brandName"/>
		<br>
		
		<acme:textbox code="creditCard.number" path="number"/>
		<br>
		
		<acme:textbox code="creditCard.expirationMonth" path="expirationMonth"/>
		<br>
		
		<acme:textbox code="creditCard.expirationYear" path="expirationYear"/>
		<br>
		
		<acme:textbox code="creditCard.cvvCode" path="cvvCode"/>
		<br>

 
 
 	<acme:submit name="save" code="creditCard.save"/>
	
	<acme:cancel url="creditCard/customer/list.do" code="creditCard.cancel"/>
 
	
	
</form:form>