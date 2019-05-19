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

<display:table name="workingOuts" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">	
		<display:column>
		<a href="workingOut/customer,trainer/display.do?workingOutId=${row.id}"><spring:message code="workingOut.display"/></a>
	</display:column>	
	
	<security:authorize access="hasRole('TRAINER')">
	<display:column>
		<jstl:if test="${principal == row.trainer && !row.isFinalMode}">
			<a href="workingOut/trainer/edit.do?workingOutId=${row.id}"><spring:message code="workingOut.edit"/></a>
		</jstl:if>
	</display:column>
	
	<display:column>	
		<jstl:if test="${principal == row.company && !row.isFinalMode}">
			<a href="workingOut/trainer/makeFinal.do?workingOutId=${row.id}"><spring:message code="workingOut.makeFinal"/></a>
		</jstl:if>
	</display:column>
	
	</security:authorize>
	
	<display:column property="trainer.name" titleKey="workingOut.trainerName" />
	
	<display:column property="ticker" titleKey="workingOut.ticker" />
	
	<spring:message code="workingOut.moment" var="formatMoment" />
	<display:column property="startMoment" titleKey="workingOut.startMoment" sortable="true" format="${formatMoment}"/>
			
	<display:column property="endMoment" titleKey="workingOut.endMoment" sortable="true" format="${formatMoment}"/>

</display:table>


<security:authorize access="hasRole('TRAINER')">
		<jstl:if test="${principal == owner}">
 			<a href="workingOut/trainer/create.do"><spring:message code="workingOut.create"/></a>
 		</jstl:if>
 	</security:authorize>