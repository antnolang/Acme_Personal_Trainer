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
	
	<jstl:if test="${requestURI == 'company/list.do' }">
		<display:column property="commercialName" titleKey="table.commercialName"/>
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${row.isSpammer == null}">
			<display:column value="" titleKey="table.isSpammer" />
		</jstl:if>
		<jstl:if test="${row.isSpammer != null && row.isSpammer}">
			<spring:message code="actor.yes" var="b" />
			<display:column value="${b}" titleKey="table.isSpammer" />
		</jstl:if>
		<jstl:if test="${row.isSpammer != null && !row.isSpammer}">
			<spring:message code="actor.no" var="b" />
			<display:column value="${b}" titleKey="table.isSpammer" />
		</jstl:if>
	</security:authorize>
	
	<display:column titleKey="table.auditScore">
		<jstl:if test="${row.userAccount.authorities == '[COMPANY]'}">
			<jstl:out value="${row.auditScore}" />
		</jstl:if>
	</display:column>
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<form:form action="actor/administrator/spammersProcess.do">
		<acme:submit name="spammers" code="actor.launch" />
	</form:form>
	
	<form:form action="actor/administrator/auditScoreProcess.do">
		<acme:submit name="audit_score" code="actor.process" />
	</form:form>
</security:authorize>
 	
