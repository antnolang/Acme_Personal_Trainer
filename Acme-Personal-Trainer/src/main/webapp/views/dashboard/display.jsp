<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<p> <strong> <spring:message code="dashboard.11.4.1" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberWorkingOutPerTrainer[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberWorkingOutPerTrainer[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberWorkingOutPerTrainer[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberWorkingOutPerTrainer[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.11.4.2" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerWorkingOut[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerWorkingOut[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerWorkingOut[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerWorkingOut[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.11.4.3" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataPricePerWorkingOut[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataPricePerWorkingOut[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataPricePerWorkingOut[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataPricePerWorkingOut[3]}" /> </td>
	</tr>
</table>

<p>
	<strong> <spring:message code="dashboard.11.4.4" /> </strong>:
	<jstl:out value="${findRatioPendingApplications}" />
</p>

<p>
	<strong> <spring:message code="dashboard.11.4.5" /> </strong>:
	<jstl:out value="${findRatioAcceptedApplications}" />
</p>

<p>
	<strong> <spring:message code="dashboard.11.4.6" /> </strong>:
	<jstl:out value="${findRatioRejectedApplications}" />
</p>

<p><strong> <spring:message code="dashboard.11.4.7" />: </strong></p>
<display:table name="findTrainersWithPublishedWorkingOutMoreThanAverageOrderByName" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
</display:table>

<p><strong> <spring:message code="dashboard.11.4.8" />: </strong></p>
<display:table name="findUsualCustomers" id="row1" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
</display:table>

<p> <strong> <spring:message code="dashboard.37.5.1" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberEndorsementPerTrainer[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberEndorsementPerTrainer[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberEndorsementPerTrainer[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberEndorsementPerTrainer[3]}" /> </td>
	</tr>
</table>

<p>
	<strong> <spring:message code="dashboard.37.5.2" /> </strong>:
	<jstl:out value="${ratioTrainerWithEndorsement}" />
</p>

<p><strong> <spring:message code="dashboard.37.5.3" />: </strong></p>
<display:table name="findCustomerWriteMostEndorsement" id="row3" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
</display:table>

<p>
	<strong> <spring:message code="dashboard.37.5.4" /> </strong>:
	<jstl:out value="${findRatioEmptyVsNonEmpty}" />
</p>