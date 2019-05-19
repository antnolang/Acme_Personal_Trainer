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



<security:authorize access="hasRole('CUSTOMER')">
	<fieldset>
		<legend>
			<spring:message code="application.pending" />
		</legend>

		<display:table name="pendingApplications" id="row"
			requestURI="${requestURI}" class="displaytag" pagesize="5">

			<display:column>
				<a
					href="application/customer,trainer/display.do?applicationId=${row.id}"><spring:message
						code="application.display" /></a>
			</display:column>

			<display:column property="problem.title"
				titleKey="application.problem" />

			<security:authorize access="hasRole('CUSTOMER')">
				<display:column property="workingOut.title"
					titleKey="application.workingOut" />
			</security:authorize>

		</display:table>
	</fieldset>
</security:authorize>

<fieldset>
	<legend>
		<spring:message code="application.submitted" />
	</legend>

	<display:table name="submittedApplications" id="row1"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column>
			<a
				href="application/customer,trainer/display.do?applicationId=${row1.id}"><spring:message
					code="application.display" /></a>
		</display:column>

		<security:authorize access="hasRole('TRAINER')">
			<display:column>
				<a href="application/trainer/reject.do?applicationId=${row1.id}"><spring:message
						code="application.reject" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<display:column>
				<a href="application/trainer/accept.do?applicationId=${row1.id}"><spring:message
						code="application.accept" /></a>
			</display:column>
		</security:authorize>

		<display:column
					property="problem.title" titleKey="application.problem" />

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column 
				property="workingOut.title" titleKey="application.workingOut" />
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<display:column 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>

	</display:table>
</fieldset>

<fieldset>
	<legend>
		<spring:message code="application.rejected" />
	</legend>

	<display:table name="rejectedApplications" id="row2"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column >
			<a
				href="application/customer,trainer/display.do?applicationId=${row2.id}"><spring:message
					code="application.display" /></a>
		</display:column>

		<display:column 
			property="problem.title" titleKey="application.problem" />

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column 
				property="workingOut.title" titleKey="application.workingOut" />
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<display:column 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>

	</display:table>
</fieldset>


<fieldset>
	<legend>
		<spring:message code="application.accepted" />
	</legend>

	<display:table name="acceptedApplications" id="row3"
		requestURI="${requestURI}" class="displaytag" pagesize="5">

		<display:column>
			<a
				href="application/customer,trainer/display.do?applicationId=${row3.id}"><spring:message
					code="application.display" /></a>
		</display:column>

		<display:column 
			property="problem.title" titleKey="application.problem" />

		<security:authorize access="hasRole('CUSTOMER')">
			<display:column 
				property="workingOut.title" titleKey="application.workingOut" />
		</security:authorize>

		<security:authorize access="hasRole('TRAINER')">
			<display:column 
				property="customer.fullname" titleKey="application.customer" />
		</security:authorize>

	</display:table>
</fieldset>

<br>
<br>
<input type="button" name="return"
	value="<spring:message code="application.return" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />