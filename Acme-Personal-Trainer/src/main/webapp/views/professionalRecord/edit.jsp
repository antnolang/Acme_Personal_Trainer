<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="professionalRecord/trainer/edit.do" modelAttribute="professionalRecord">
	<input type="hidden" name="curriculumId" value="${curriculumId}"/>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="professionalRecord.fieldset"/></legend>
		
		<acme:textbox code="professionalRecord.company" path="company"/>
		<acme:textbox code="professionalRecord.startDate" path="startDate" placeholder="dd/mm/yyyy"/>
		<acme:textbox code="professionalRecord.endDate" path="endDate" placeholder="dd/mm/yyyy"/>
		<acme:textbox code="professionalRecord.role" path="role"/>
		<acme:textbox code="professionalRecord.attachment" path="attachment"/>
		<acme:textarea code="professionalRecord.comments" path="comments"/>
	</fieldset>
	
	<!-- Buttons -->
	<acme:submit name="save" code="professionalRecord.save"/>
	<jstl:if test="${professionalRecord.id != 0}">
		&nbsp;
		<acme:submit name="delete" code="professionalRecord.delete"/>
	</jstl:if>
	&nbsp;
	<acme:cancel code="professionalRecord.cancel" url="curriculum/trainer/display.do"/>
</form:form>