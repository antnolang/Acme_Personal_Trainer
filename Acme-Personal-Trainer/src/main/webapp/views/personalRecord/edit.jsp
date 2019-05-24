<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="personalRecord/trainer/edit.do" modelAttribute="personalRecord">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="personalRecord.fieldset"/></legend>
		
		<acme:textbox code="personalRecord.fullname" path="fullName"/>
		<acme:textbox code="personalRecord.photo" path="photo"/>
		<acme:textbox code="personalRecord.email" path="email"/>
		<acme:textbox code="personalRecord.phoneNumber" path="phoneNumber" placeholder="+34 (111) 654654654" id="phoneNumber"/>
		<acme:textbox code="personalRecord.linkedInProfile" path="linkedInProfile" placeholder="https://www.linkedin.com/..."/>
	</fieldset>
	
	<!-- Buttons -->
	<spring:message code="personalRecord.confirm.phone" var="confirmTelephone"/>
	<acme:submit name="save" code="personalRecord.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	&nbsp;
	<acme:cancel code="personalRecord.cancel" url="curriculum/trainer/display.do"/>
</form:form>