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

<form:form action="endorsement/customer,trainer/edit.do" modelAttribute="endorsement">
	<form:hidden path="id" />
	<form:hidden path="version"/>
	
	<acme:textbox code="endorsement.mark" path="mark"/>
	<acme:textarea code="endorsement.comments" path="comments" />
	
	<jstl:if test="${!isUpdating}">
		<security:authorize access="hasRole('CUSTOMER')">
			<acme:selectMandatory path="trainer" code="endorsement.trainer" items="${trainers}" itemLabel="fullname" multiple="false" />
		</security:authorize>
		
		<security:authorize access="hasRole('TRAINER')">
			<acme:selectMandatory path="customer" code="endorsement.customer" items="${customers}" itemLabel="fullname" multiple="false" />
		</security:authorize>
	</jstl:if>
	
	<jstl:if test="${isUpdating}">
		<security:authorize access="hasRole('CUSTOMER')">
			<p>
				<strong> <spring:message code="endorsement.trainer" />:
				</strong>
				<jstl:out value="${trainerFullname}" />
			</p>
		</security:authorize>
		
		<security:authorize access="hasRole('TRAINER')">
			<p>
				<strong> <spring:message code="endorsement.customer" />:
				</strong>
				<jstl:out value="${customerFullname}" />
			</p>
		</security:authorize>
	</jstl:if>
	
	<!-- Buttons -->
	<acme:submit name="save" code="endorsement.save" />
	<input type="submit" name="delete" value="<spring:message code="endorsement.delete"/>" onclick="return confirm('<spring:message code="endorsement.confirm.delete"/>')"/>
	<acme:cancel url="endorsement/customer,trainer/list.do" code="endorsement.cancel" />
</form:form>