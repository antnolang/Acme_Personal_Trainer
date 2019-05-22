<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="professionalRecord.date.format2" var="dateFormat"/>

<ul>
	<li>
		<strong><spring:message code="professionalRecord.company"/></strong>
		<jstl:out value="${professionalRecord.company}"/>
	</li>
	<li>
		<strong><spring:message code="professionalRecord.startDate"/></strong>
		<fmt:formatDate value="${professionalRecord.startDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="professionalRecord.endDate"/></strong>
		<fmt:formatDate value="${professionalRecord.endDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="professionalRecord.role"/></strong>
		<jstl:out value="${professionalRecord.role}"/>
	</li>
	<li>
		<strong><spring:message code="professionalRecord.attachment"/></strong>
		<a href="${professionalRecord.attachment}">
			<jstl:out value="${professionalRecord.attachment}"/>
		</a>
	</li>
	<li>
		<strong><spring:message code="professionalRecord.comments"/></strong>
		<jstl:out value="${professionalRecord.comments}"/>
	</li>
</ul>

<a href="professionalRecord/backCurriculum.do?professionalRecordId=${professionalRecord.id}">
	<spring:message code="professionalRecord.return"/>
</a>