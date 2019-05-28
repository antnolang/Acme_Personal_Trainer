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

<jstl:if test="${requestURI != 'endorsement/list.do'}">
<h3> <spring:message code="endorsement.sent" /> </h3>
<display:table name="sentEndorsements" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">
	<display:column>
		<a href="endorsement/customer,trainer/display.do?endorsementId=${row.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="endorsement/customer,trainer/edit.do?endorsementId=${row.id}">
			<spring:message code="endorsement.edit" />
		</a>
	</display:column>
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<display:column property="writtenMoment" titleKey="endorsement.writtenMoment" sortable="true" format="${w_format}" />
	
	<display:column property="mark" titleKey="endorsement.mark" />
	
	<display:column property="comments" titleKey="endorsement.comments" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column property="trainer.fullname" titleKey="endorsement.trainer" />
	</security:authorize>
	
	
	<security:authorize access="hasRole('TRAINER')">
		<display:column property="customer.fullname" titleKey="endorsement.customer" />
	</security:authorize>
	
</display:table>
</jstl:if>
<br />
<jstl:if test="${!haveActorAttended && haveActorAttended!= null}">
	<p style="color:blue;"><spring:message code="endorsement.info"/></p>
</jstl:if>
<jstl:if test="${haveActorAttended}">
<a href="endorsement/customer,trainer/create.do">
	<spring:message code="endorsement.create" />
</a>
</jstl:if>

<h3> <spring:message code="endorsement.received" /> </h3>
<display:table name="receivedEndorsements" id="row1" requestURI="${requestURI}" class="displaytag" pagesize="5">
	<display:column>
		<a href="endorsement/customer,trainer/display.do?endorsementId=${row1.id}">
			<spring:message code="endorsement.display" />
		</a>
	</display:column>
	
	<spring:message code="endorsement.formatMoment" var="w_format"  />
	<display:column property="writtenMoment" titleKey="endorsement.writtenMoment" sortable="true" format="${w_format}" />
	
	<display:column property="mark" titleKey="endorsement.mark" />
	
	<display:column property="comments" titleKey="endorsement.comments" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column property="trainer.fullname" titleKey="endorsement.trainer" />
	</security:authorize>
	
	
	<security:authorize access="hasRole('TRAINER')">
		<display:column property="customer.fullname" titleKey="endorsement.customer" />
	</security:authorize>
</display:table>
