
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${articles}" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">

	<display:column>
		<a href="article/display.do?articleId=${row.id}"><spring:message code="article.table.display"/></a>
	</display:column>	
	
	<security:authorize access="hasRole('NUTRITIONIST')">
	<display:column>
		<jstl:if test="${principal == row.nutritionist}">
			<a href="article/nutritionist/edit.do?articleId=${row.id}"><spring:message code="article.edit"/></a>
		</jstl:if>
	</display:column>	
	
	<display:column>	
		<jstl:if test="${principal == row.nutritionist && !row.isFinalMode}">
			<a href="article/nutritionist/makeFinal.do?articleId=${row.id}"><spring:message code="article.makeFinal"/></a>
		</jstl:if>
	</display:column>
	</security:authorize>
	
	<display:column property="title" titleKey="article.title" />
	
	<security:authorize access="hasRole('CUSTOMER')">
	<display:column titleKey="article.nutritionist">
		<a href="actor/display.do?actorId=${row.nutritionist.id}"><jstl:out value="${row.nutritionist.name}"/></a>
	</display:column>
	</security:authorize>

</display:table>

	<security:authorize access="hasRole('NUTRITIONIST')">
 			<a href="article/nutritionist/create.do"><spring:message code="article.create"/></a>
 	</security:authorize>


