<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="educationRecord/trainer/edit.do" modelAttribute="educationRecord">
	<input type="hidden" name="curriculumId" value="${curriculumId}"/>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="educationRecord.fieldset"/></legend>
		
		<acme:textbox code="educationRecord.diplomaTitle" path="diplomaTitle"/>
		<acme:textbox code="educationRecord.institution" path="institution"/>
		<acme:textbox code="educationRecord.startDate" path="startDate" placeholder="dd/mm/yyyy"/>
		<acme:textbox code="educationRecord.endDate" path="endDate" placeholder="dd/mm/yyyy"/>
		<acme:textbox code="educationRecord.attachment" path="attachment"/>
		<acme:textarea code="educationRecord.comments" path="comments"/>
	</fieldset>
	
	<!-- Buttons -->
	<acme:submit name="save" code="educationRecord.save"/>
	<jstl:if test="${educationRecord.id != 0}">
		&nbsp;
		<acme:submit name="delete" code="educationRecord.delete"/>
	</jstl:if>
	&nbsp;
	<acme:cancel code="educationRecord.cancel" url="curriculum/trainer/display.do"/>
</form:form>