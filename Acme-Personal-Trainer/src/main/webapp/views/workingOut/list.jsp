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


<!------------ FINDER ------------>
<spring:message code="workingOut.formatDate1" var="dateFormat"/>
<jstl:if test="${finder ne null}">
	<fieldset>
		<legend><spring:message code="workingOut.finder.legend"/></legend>
		
		<p style="color:blue;">
			<spring:message code="workingOut.finder.warning"/><jstl:out value="${numberOfResults}"/>
		</p>
		
		<ul>
			<li>
				<strong><spring:message code="finder.keyword"/></strong>
				<jstl:out value="${finder.keyword}"/>
			</li>
			<li>
				<strong><spring:message code="finder.category"/></strong>
				<jstl:out value="${finder.category}"/>
			</li>
			<li>
				<strong><spring:message code="finder.startDate"/></strong>
				<fmt:formatDate value="${finder.startDate}" pattern="${dateFormat}"/>
			</li>
			<li>
				<strong><spring:message code="finder.endDate"/></strong>
				<fmt:formatDate value="${finder.startDate}" pattern="${dateFormat}"/>
			</li>
			<li>
				<strong><spring:message code="finder.startPrice"/></strong>
				<jstl:out value="${finder.startPrice}"/>
			</li>
			<li>
				<strong><spring:message code="finder.endPrice"/></strong>
				<jstl:out value="${finder.endPrice}"/>
			</li>
			
		</ul>
		
		<div>
			<a href="finder/customer/edit.do"><spring:message code="finder.edit"/></a>
			&nbsp;
			<a href="finder/customer/clear.do"><spring:message code="finder.clear"/></a>
		</div>
	</fieldset>
	
	<jstl:set var="workingOuts" value="${finder.workingOuts}"/>
</jstl:if>


<!------------ WORKING OUT LIST ------------>
<display:table name="${workingOuts}" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">	
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
		<jstl:if test="${principal == row.trainer && !row.isFinalMode}">
			<a href="workingOut/trainer/makeFinal.do?workingOutId=${row.id}"><spring:message code="workingOut.makeFinal"/></a>
		</jstl:if>
	</display:column>
	
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column property="trainer.name" titleKey="workingOut.trainerName" />
	</security:authorize>
	
	<display:column property="ticker" titleKey="workingOut.ticker" />
	
	<spring:message code="workingOut.formatMoment" var="formatMoment" />
	<display:column property="startMoment" titleKey="workingOut.startMoment" sortable="true" format="${formatMoment}"/>
			
	<display:column property="endMoment" titleKey="workingOut.endMoment" sortable="true" format="${formatMoment}"/>

</display:table>


<security:authorize access="hasRole('TRAINER')">
 			<a href="workingOut/trainer/create.do"><spring:message code="workingOut.create"/></a>
 	</security:authorize>