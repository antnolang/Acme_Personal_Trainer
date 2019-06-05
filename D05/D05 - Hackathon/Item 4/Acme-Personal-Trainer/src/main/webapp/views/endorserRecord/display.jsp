<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<ul>
	<li>
		<strong><spring:message code="endorserRecord.fullname"/></strong>
		<jstl:out value="${endorserRecord.fullname}"/>
	</li>
	<li>
		<strong><spring:message code="endorserRecord.email"/></strong>
		<jstl:out value="${endorserRecord.email}"/>
	</li>
	<li>
		<strong><spring:message code="endorserRecord.phoneNumber"/></strong>
		<jstl:out value="${endorserRecord.phoneNumber}"/>
	</li>
	<li>
		<strong><spring:message code="endorserRecord.linkedInProfile"/></strong>
		<jstl:out value="${endorserRecord.linkedInProfile}"/>
	</li>
</ul>

<a href="endorserRecord/backCurriculum.do?endorserRecordId=${endorserRecord.id}">
	<spring:message code="endorserRecord.return"/>
</a>