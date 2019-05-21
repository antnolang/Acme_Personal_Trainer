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

<form:form action="session/trainer/edit.do" modelAttribute="session">
	<form:hidden path="id" />
	<input type="hidden" name="workingOutId" value="${workingOutId}"/>
	
	<acme:textbox code="session.title" path="title"/>
	
	<acme:textarea code="session.description" path="description" />
	
	<acme:textbox code="session.address" path="address"/>
	
	<acme:textbox code="session.starMoment" placeholder="dd/MM/yyyy HH:mm" path="startMoment"/>
	
	<acme:textbox code="session.endMoment" placeholder="dd/MM/yyyy HH:mm" path="endMoment"/>	
	
	<acme:submit name="save" code="session.save"/>	
	<jstl:if test="${session.id != 0}">
		<acme:submit name="delete" code="session.delete"/>	
	</jstl:if>
	<acme:cancel url="workingOut/trainer/list.do" code="session.cancel"/>
	<br />
</form:form>