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


<p> <strong> <spring:message code="dashboard.one" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberPositionsPerCompany[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.two" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerRookie[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerRookie[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerRookie[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberApplicationPerRookie[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.three" />: </strong></p>
<display:table name="findCompaniesOfferedMorePositions" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
	<display:column property="commercialName" titleKey="table.commercialName"/>
</display:table>

<p><strong> <spring:message code="dashboard.four" />: </strong></p>
<display:table name="findRookiesWithMoreApplications" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
</display:table>

<p> <strong> <spring:message code="dashboard.five" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataSalaryOffered[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.six" />: </strong></p>
<display:table name="dataPositionsBestWorstSalary" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="ticker" titleKey="position.ticker" />
	<display:column property="title" titleKey="position.title" />
	<spring:message code="position.formatDeadline" var="formatMomentDeadline" />
	<display:column property="deadline" titleKey="position.deadline" sortable="true" format="${formatMomentDeadline}"/>
	<display:column property="profile" titleKey="position.profile" />
	<display:column property="skills" titleKey="position.skills" />
	<display:column property="technologies" titleKey="position.technologies" />
	<display:column property="salary" titleKey="position.salary" />
</display:table>
	
<p> <strong> <spring:message code="dashboard.seven" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerRookie[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerRookie[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerRookie[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberCurriculumPerRookie[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.eight" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberResultsFinder[3]}" /> </td>
	</tr>
</table>

<p>
	<strong> <spring:message code="dashboard.nine" /> </strong>:
	<jstl:out value="${findRatioEmptyVsNonEmpty}" />
</p>

<p> <strong> <spring:message code="dashboard.ten" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScore[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScore[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScore[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScore[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.eleven" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScoreOfCompany[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScoreOfCompany[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScoreOfCompany[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${findDataNumberAuditScoreOfCompany[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.twelve" />: </strong></p>
<display:table name="findCompaniesHighestScore" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
	<display:column property="auditScore" titleKey="table.auditScore"/>
</display:table>

<p>
	<strong> <spring:message code="dashboard.thirteen" /> </strong>:
	<jstl:out value="${findAvgSalaryByHighestPosition}" />
</p>

<p> <strong> <spring:message code="dashboard.fourteen" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataItemsPerProvider[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataItemsPerProvider[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataItemsPerProvider[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataItemsPerProvider[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.fifteen" />: </strong></p>
<display:table name="topFiveProviders" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
	<display:column property="make" titleKey="table.make"/>
</display:table>

<p> <strong> <spring:message code="dashboard.sixteen" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerProvider[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerProvider[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerProvider[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerProvider[3]}" /> </td>
	</tr>
</table>

<p> <strong> <spring:message code="dashboard.seventeen" />: </strong> </p>
<table>
	<tr>
		<th> <spring:message code="dashboard.average" /> </th>
		<th> <spring:message code="dashboard.min" /> </th>
		<th> <spring:message code="dashboard.max" /> </th>
		<th> <spring:message code="dashboard.deviation" /> </th>
	</tr>
	<tr>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerPosition[0]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerPosition[1]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerPosition[2]}" /> </td>
		<td> <fmt:formatNumber type = "number" maxFractionDigits = "3" minFractionDigits="3" value = "${dataOfSponsorshipPerPosition[3]}" /> </td>
	</tr>
</table>

<p><strong> <spring:message code="dashboard.eighteen" />: </strong></p>
<display:table name="findProvidersWithMoreSponsorships" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	<display:column property="fullname" titleKey="table.fullname" />
	<display:column property="email" titleKey="table.email" />
	<display:column property="phoneNumber" titleKey="table.phoneNumber" />
	<display:column property="address" titleKey="table.address"/>
	<display:column property="make" titleKey="table.make"/>
</display:table>