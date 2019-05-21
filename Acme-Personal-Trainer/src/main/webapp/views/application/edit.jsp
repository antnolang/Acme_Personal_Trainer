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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="application/customer/edit.do"
	modelAttribute="application">
	<form:hidden path="id" />
	<form:hidden path="workingOut" />

		<acme:selectMandatory items="${creditCards}" multiple="false" 
		 itemLabel="holderName" code="application.creditCard" path="creditCard"/>

		<br />
		
		<acme:textarea code="application.comments" path="comments" />
		<br />
		
		<div>
			<acme:submit name="save" code="application.save" />
			&nbsp;
			<acme:cancel code="application.cancel" url="application/customer/list.do" />
		</div>
		
</form:form>