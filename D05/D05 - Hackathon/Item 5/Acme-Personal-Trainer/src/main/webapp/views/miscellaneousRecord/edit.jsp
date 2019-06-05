<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="miscellaneousRecord/trainer/edit.do" modelAttribute="miscellaneousRecord">
	<input type="hidden" name="curriculumId" value="${curriculumId}"/>
	<form:hidden path="id" />
	<form:hidden path="version" />

	<fieldset>
		<legend><spring:message code="miscellaneousRecord.fieldset"/></legend>
		
		<acme:textbox code="miscellaneousRecord.title" path="title"/>
		<acme:textbox code="miscellaneousRecord.attachment" path="attachment"/>
		<acme:textarea code="miscellaneousRecord.comments" path="comments"/>
	</fieldset>
	
	<!-- Buttons -->
	<acme:submit name="save" code="miscellaneousRecord.save"/>
	<jstl:if test="${miscellaneousRecord.id != 0}">
		&nbsp;
		<acme:submit name="delete" code="miscellaneousRecord.delete"/>
	</jstl:if>
	&nbsp;
	<acme:cancel code="miscellaneousRecord.cancel" url="curriculum/trainer/display.do"/>
</form:form>