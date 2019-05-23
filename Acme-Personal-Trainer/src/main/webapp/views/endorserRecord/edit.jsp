<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="endorserRecord/trainer/edit.do" modelAttribute="endorserRecord">
	<input type="hidden" name="curriculumId" value="${curriculumId}"/>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="endorserRecord.fieldset"/></legend>
		
		<acme:textbox code="endorserRecord.fullname" path="fullname"/>
		<acme:textbox code="endorserRecord.phoneNumber" path="phoneNumber" placeholder="+34 (111) 654654654" id="phoneNumber"/>
		<acme:textbox code="endorserRecord.email" path="email"/>
		<acme:textbox code="endorserRecord.linkedInProfile" path="linkedInProfile" placeholder="https://www.linkedin.com/..."/>
	</fieldset>
	
	<!-- Buttons -->
	<spring:message code="endorserRecord.confirm.phone" var="confirmTelephone"/>
	<acme:submit name="save" code="endorserRecord.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
	<jstl:if test="${endorserRecord.id != 0}">
		&nbsp;
		<acme:submit name="delete" code="endorserRecord.delete"/>
	</jstl:if>
	&nbsp;
	<acme:cancel code="endorserRecord.cancel" url="curriculum/trainer/display.do"/>
</form:form>