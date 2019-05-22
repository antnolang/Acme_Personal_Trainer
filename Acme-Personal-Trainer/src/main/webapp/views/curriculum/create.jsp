<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<p style="color:blue;"><spring:message code="curriculum.info"/></p>


<form:form action="curriculum/trainer/edit.do" modelAttribute="curriculum">
	<form:hidden path="id" />

	<acme:textbox code="curriculum.personalRecord.fullName" path="personalRecord.fullName" readonly="true"/>
	<acme:textbox code="curriculum.personalRecord.photo" path="personalRecord.photo" placeholder="https://examplePhotoUrl.com"/>
	<acme:textbox code="curriculum.personalRecord.email" path="personalRecord.email"/>
	<acme:textbox code="curriculum.personalRecord.phoneNumber" path="personalRecord.phoneNumber" id="phoneNumber"/>
	<acme:textbox code="curriculum.personalRecord.linkedInProfile" path="personalRecord.linkedInProfile" placeholder="https://www.linkedin.com/..."/>
	
	<!-- Buttons -->
	<spring:message code="curriculum.confirm.phone" var="confirmTelephone"/>
	<acme:submit name="save" code="curriculum.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	&nbsp;
	<acme:cancel code="curriculum.cancel" url="welcome/index.do"/>
</form:form>