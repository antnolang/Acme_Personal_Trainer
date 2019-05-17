<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Personal Trainer Co., Inc.</b>
<br>
<a href="welcome/terms.do"><spring:message code="master.page.terminos"/></a>
<br>
<a href="welcome/dataProcesses.do"><spring:message code="master.page.dataProcesses"/></a>
