<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<p>
	<spring:message code="endorsement.formatMoment2" var="w_format" />
	<strong> <spring:message code="endorsement.writtenMoment" />: </strong>
	<fmt:formatDate value="${endorsement.writtenMoment}" pattern="${w_format}" />
</p>


<p>
	<strong> <spring:message code="endorsement.comments" />: </strong>
	<jstl:out value="${endorsement.comments}" />
</p>


<p>
	<strong> <spring:message code="endorsement.mark" />: </strong>
	<jstl:out value="${endorsement.mark}" />
</p>


<security:authorize access="hasRole('CUSTOMER')">
<p>
	<strong> <spring:message code="endorsement.trainer" />: </strong>
	<a href="actor/display.do?actorId=${endorsement.trainer.id}"><jstl:out value="${endorsement.trainer.fullname}"/></a>
</p>
</security:authorize>
	
	
<security:authorize access="hasRole('TRAINER')">
<p>
	<strong> <spring:message code="endorsement.customer" />: </strong>
	<a href="actor/display.do?actorId=${endorsement.customer.id}"><jstl:out value="${endorsement.customer.fullname}"/></a>
</p>
</security:authorize>

<p>
	<a href="endorsement/customer,trainer/list.do">
		 <spring:message code="endorsement.return" />
	</a>
</p>	   

