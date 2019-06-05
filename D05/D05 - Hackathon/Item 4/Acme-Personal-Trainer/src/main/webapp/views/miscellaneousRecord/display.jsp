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
		<strong><spring:message code="miscellaneousRecord.title"/></strong>
		<jstl:out value="${miscellaneousRecord.title}"/>
	</li>
	<li>
		<strong><spring:message code="miscellaneousRecord.attachment"/></strong>
		<a href="${miscellaneousRecord.attachment}">
			<jstl:out value="${miscellaneousRecord.attachment}"/>
		</a>
	</li>
	<li>
		<strong><spring:message code="miscellaneousRecord.comments"/></strong>
		<jstl:out value="${miscellaneousRecord.comments}"/>
	</li>
</ul>

<a href="miscellaneousRecord/backCurriculum.do?miscellaneousRecordId=${miscellaneousRecord.id}">
	<spring:message code="miscellaneousRecord.return"/>
</a>