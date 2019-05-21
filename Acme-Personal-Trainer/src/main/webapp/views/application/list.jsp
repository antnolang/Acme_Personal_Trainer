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

<fieldset>
	<legend>
		<spring:message code="application.pending" />
	</legend>

	<display:table name="pendingApplications" id="row1"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column style="background-color:grey">
			<a
				href="application/customer,trainer/display.do?applicationId=${row1.id}"><spring:message
					code="application.display" /></a>
		</display:column>

		<security:authorize access="hasRole('TRAINER')">
			<display:column style="background-color:grey">
				<a href="application/trainer/reject.do?applicationId=${row1.id}"><spring:message
						code="application.reject" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<display:column style="background-color:grey">
				<a href="application/trainer/accept.do?applicationId=${row1.id}"><spring:message
						code="application.accept" /></a>
			</display:column>
		</security:authorize>
		
		<display:column style="background-color:grey" 
			property="workingOut.ticker" titleKey="application.workingOut" />
			
		<display:column style="background-color:grey" 
			property="registeredMoment" titleKey="application.registeredMoment" />

		<security:authorize access="hasRole('TRAINER')">
			<display:column style="background-color:grey" 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column style="background-color:grey" 
				property="workingOut.trainer.fullname" titleKey="application.trainer" />
		</security:authorize>

	</display:table>
</fieldset>

<fieldset>
	<legend>
		<spring:message code="application.rejected" />
	</legend>

	<display:table name="rejectedApplications" id="row2"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column style="background-color:orange" >
			<a
				href="application/customer,trainer/display.do?applicationId=${row2.id}"><spring:message
					code="application.display" /></a>
		</display:column>

				<display:column style="background-color:orange" 
			property="workingOut.ticker" titleKey="application.workingOut" />
			
		<display:column style="background-color:orange" 
			property="registeredMoment" titleKey="application.registeredMoment" />

		<security:authorize access="hasRole('TRAINER')">
			<display:column style="background-color:orange" 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column style="background-color:orange" 
				property="workingOut.trainer.fullname" titleKey="application.trainer" />
		</security:authorize>

	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="application.accepted" />
	</legend>

	<display:table name="acceptedApplications" id="row3"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column style="background-color:green">
			<a
				href="application/customer,trainer/display.do?applicationId=${row3.id}"><spring:message
					code="application.display" /></a>
		</display:column>

				<display:column style="background-color:green" 
			property="workingOut.ticker" titleKey="application.workingOut" />
			
		<display:column style="background-color:green" 
			property="registeredMoment" titleKey="application.registeredMoment" />

		<security:authorize access="hasRole('TRAINER')">
			<display:column style="background-color:green" 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column style="background-color:green" 
				property="workingOut.trainer.fullname" titleKey="application.trainer" />
		</security:authorize>

	</display:table>
</fieldset>

<br>
<br>
<input type="button" name="return"
	value="<spring:message code="application.return" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />