<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="audit/auditor/edit.do" modelAttribute="audit">
	<form:hidden path="id"/>
	
	<p>
		<strong><spring:message code="audit.curriculum"/> </strong>
		<a href="curriculum/display.do?curriculumId=${audit.curriculum.id}"><jstl:out value="${audit.curriculum.ticker}"/></a>
	</p>
	<acme:textbox code="audit.title" path="title"/>
	<acme:textbox code="audit.description" path="description"/>
	
	<p style="color:blue;"><spring:message code="audit.info.attachments"/></p>
	<acme:textarea code="audit.attachments" path="attachments"/>
	
	
	<!-- Buttons -->
	<acme:submit name="save" code="audit.save"/>
	<jstl:choose>
		<jstl:when test="${audit.id != 0}">
			&nbsp;
			<acme:submit name="delete" code="audit.delete"/>
			&nbsp;
			<acme:cancel code="audit.cancel" url="audit/auditor/list.do"/>
		</jstl:when>
		<jstl:otherwise>
			&nbsp;
			<acme:cancel code="audit.cancel" url="curriculum/display.do?curriculumId=${audit.curriculum.id}"/>
		</jstl:otherwise>
	</jstl:choose>
</form:form>