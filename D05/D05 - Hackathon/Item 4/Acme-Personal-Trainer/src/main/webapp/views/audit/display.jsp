<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<spring:message code="audit.moment.format2" var="formatMoment"/>


<ul>
	<li>
		<strong><spring:message code="audit.curriculum"/> </strong>
		<a href="curriculum/display.do?curriculumId=${audit.curriculum.id}"><jstl:out value="${audit.curriculum.ticker}"/></a>
	</li>
	<li>
		<strong><spring:message code="audit.title"/> </strong>
		<jstl:out value="${audit.title}"/>
	</li>
	<li>
		<strong><spring:message code="audit.description"/> </strong>
		<jstl:out value="${audit.description}"/>
	</li>
	<li>
		<strong><spring:message code="audit.moment"/> </strong>
		<fmt:formatDate value="${audit.moment}" pattern="${formatMoment}"/>
	</li>
	<li>
		<jstl:if test="${not empty attachments}">
			<strong><spring:message code="audit.attachments"/></strong>
			<br>
			<ul>
				<jstl:forEach var="attachment" items="${attachments}">
					<li> <a href="${attachment}"><jstl:out value="${attachment}"/></a> </li>
				</jstl:forEach>
			</ul>
		</jstl:if>
	</li>
</ul>

<a href="audit/auditor/list.do"><spring:message code="audit.return.list"/></a>