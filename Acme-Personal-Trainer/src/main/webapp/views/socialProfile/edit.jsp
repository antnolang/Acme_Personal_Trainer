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

<form:form action="socialProfile/administrator,auditor,company,provider,rookie/edit.do" modelAttribute="socialProfile" >
	<form:hidden path="id"/>
	<form:hidden path="version"/>
		
	<acme:textbox code="socialProfile.nick" path="nick" />
	<acme:textbox code="socialProfile.socialNetwork" path="socialNetwork" />
	<acme:textbox code="socialProfile.linkProfile" path="linkProfile" />
	
	<!-- Buttons -->
	<acme:submit name="save" code="socialProfile.save"/>
	<jstl:if test="${socialProfile.id != 0}">
		<acme:submit name="delete" code="socialProfile.delete" />
	</jstl:if>		
	<acme:cancel url="socialProfile/list.do?actorId=${actorId}" code="socialProfile.cancel"/>
</form:form>