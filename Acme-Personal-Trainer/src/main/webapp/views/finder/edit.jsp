<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="finder/customer/edit.do" modelAttribute="finder">
	<form:hidden path="id"/>
	
	<acme:textbox path="keyword" code="finder.keyword"/>
	
	<div>
		<form:label path="category">
			<spring:message code="finder.category" />
		</form:label>		
		<form:select path="category" multiple="false" size="1">
			<jstl:forEach var="categoryId" items="${categories.keySet()}">
				<form:option value="" label="----" />
				<form:option label="${categories.get(categoryId)}" value="${categoryId}"/>
			</jstl:forEach>
		</form:select>
		<form:errors path="category" cssClass="error" />
	</div>
	
	<acme:textbox path="startDate" code="finder.startDate" placeholder="dd/mm/yyyy"/>
	<acme:textbox path="endDate" code="finder.endDate" placeholder="dd/mm/yyyy"/>
	<acme:textbox path="startPrice" code="finder.startPrice"/>
	<acme:textbox path="endPrice" code="finder.endPrice"/>
	
	<!-- Buttons -->
	<div>
		<acme:submit name="save" code="finder.save"/>
		&nbsp;
		<acme:cancel code="finder.clear" url="finder/customer/clear.do"/>
		&nbsp;
		<acme:cancel code="finder.cancel" url="finder/customer/display.do"/>
	</div>
</form:form>