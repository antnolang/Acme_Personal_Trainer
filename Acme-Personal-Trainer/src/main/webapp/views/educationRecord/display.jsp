<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="educationRecord.date.format2" var="dateFormat"/>

<ul>
	<li>
		<strong><spring:message code="educationRecord.diplomaTitle"/></strong>
		<jstl:out value="${educationRecord.diplomaTitle}"/>
	</li>
	<li>
		<strong><spring:message code="educationRecord.institution"/></strong>
		<jstl:out value="${educationRecord.institution}"/>
	</li>
	<li>
		<strong><spring:message code="educationRecord.startDate"/></strong>
		<fmt:formatDate value="${educationRecord.startDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="educationRecord.endDate"/></strong>
		<fmt:formatDate value="${educationRecord.endDate}" pattern="${dateFormat}"/>
	</li>
	<li>
		<strong><spring:message code="educationRecord.attachment"/></strong>
		<a href="${educationRecord.attachment}">
			<jstl:out value="${educationRecord.attachment}"/>
		</a>
	</li>
	<li>
		<strong><spring:message code="educationRecord.comments"/></strong>
		<jstl:out value="${educationRecord.comments}"/>
	</li>
</ul>

<a href="educationRecord/backCurriculum.do?educationRecordId=${educationRecord.id}">
	<spring:message code="educationRecord.return"/>
</a>