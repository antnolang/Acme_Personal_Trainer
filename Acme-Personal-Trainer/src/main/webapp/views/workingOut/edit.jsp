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

<form:form action="workingOut/trainer/edit.do" modelAttribute="workingOut">
	<form:hidden path="id" />
	
	<acme:textarea code="workingOut.description" path="description" />
	
	<acme:textbox code="workingOut.price" path="price"/>
	
	<acme:selectMandatory items="${categories}" multiple="true" 
		 itemLabel="name" code="workingOut.categories" path="categories"/>
	
	<acme:submit name="save" code="workingOut.save"/>	
	<jstl:if test="${workingOut.id != 0}">
		<acme:submit name="delete" code="workingOut.delete"/>	
	</jstl:if>
	<acme:cancel url="workingOut/trainer/list.do" code="workingOut.cancel"/>
	<br />
</form:form>