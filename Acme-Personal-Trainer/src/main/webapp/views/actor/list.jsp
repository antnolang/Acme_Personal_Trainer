<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="actors" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">	
	<display:column>
		<jstl:if test="${row.userAccount.authorities != '[ADMIN]' }">
		<a href="actor/display.do?actorId=${row.id}"><spring:message code="actor.table.display.profile"/></a>
		</jstl:if>
	</display:column>		

	<display:column property="fullname" titleKey="table.fullname" />
	
	<display:column property="email" titleKey="table.email" />
	
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	
	<display:column property="address" titleKey="table.address"/>	
	
	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${row.isSuspicious == null}">
			<display:column value="" titleKey="table.isSuspicious" />
		</jstl:if>
		<jstl:if test="${row.isSuspicious != null && row.isSuspicious}">
			<spring:message code="actor.yes" var="b" />
			<display:column value="${b}" titleKey="table.isSuspicious" />
		</jstl:if>
		<jstl:if test="${row.isSuspicious != null && !row.isSuspicious}">
			<spring:message code="actor.no" var="b" />
			<display:column value="${b}" titleKey="table.isSuspicious" />
		</jstl:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<form:form action="actor/administrator/suspiciousProcess.do">
		<acme:submit name="suspicious" code="actor.launch.suspicious" />
	</form:form>
	
</security:authorize>
 	
