<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p> <strong> <spring:message code="article.publishedMoment" />:  </strong>  <jstl:out value="${article.publishedMoment}" /></p>

<p> <strong> <spring:message code="article.title" />:  </strong>  <jstl:out value="${article.title}" /></p>

<p> <strong> <spring:message code="article.description" />:  </strong>  <jstl:out value="${article.description}" /></p>

<p> <strong> <spring:message code="article.tags" />:  </strong>  <jstl:out value="${article.tags}" /></p>
	
<br />

<!-- Links -->
<jstl:if test="${article.isFinalMode}">
<a href="comment/customer,nutritionist/list.do?articleId=${article.id}">
		<spring:message	code="article.comments" />			
	</a>
</jstl:if>
<br />

<security:authorize access="hasRole('NUTRITIONIST')">
	<a href="article/nutritionist/list.do">
		<spring:message	code="article.back" />			
	</a>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="article/customer/allArticlesList.do">
		<spring:message	code="article.back" />			
	</a>
</security:authorize>
