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
	<form:hidden path="version" />
	
	<acme:textarea code="workingOut.description" path="description" />
	
	<acme:textbox code="workingOut.price" path="price"/>
	
	<form:label path="categories">
			<spring:message code="workingOut.categories"/>:
	</form:label>
	<form:select path="categories" multiple="true">
		<jstl:forEach var="categoryId" items="${categories.keySet()}">
			<form:option label="${categories.get(categoryId)}" value="${categoryId}"/>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="categories"/>
	<br />
	
	<acme:submit name="save" code="workingOut.save"/>	
	<jstl:if test="${workingOut.id != 0}">
		<acme:submit name="delete" code="workingOut.delete"/>	
	</jstl:if>
	<acme:cancel url="workingOut/trainer/list.do" code="workingOut.cancel"/>
	<br />
</form:form>