<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<p> <strong> <spring:message code="comment.text" />  </strong>  <jstl:out value="${comment.text}" /></p>

<spring:message code="comment.formatMoment1" var="formatMoment"/>

<p> <strong><spring:message code="comment.publicationMoment"/>:</strong>
		<fmt:formatDate value="${comment.publicationMoment}" pattern="${formatMoment}"/></p>

<br />

<!-- Links -->


<a href="comment/customer,nutritionist/list.do?articleId=${comment.article.id}"> <spring:message
		code="comment.return" />
</a>
