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

<display:table name="creditCards" id="row" requestURI="${requestURI}" class="displaytag" pagesize="5">	
	<display:column>
		<a href="creditCard/customer/display.do?creditCardId=${row.id}"><spring:message code="creditCard.display"/></a>
	</display:column>	
	
	<display:column>
			<a href="creditCard/customer/delete.do?creditCardId=${row.id}"><spring:message code="creditCard.delete"/></a>
	</display:column>
	
	<display:column property="holderName" titleKey="creditCard.holder" />

	<display:column property="make" titleKey="creditCard.make" />
	
	
</display:table>


<security:authorize access="hasRole('TRAINER')">
 			<a href="workingOut/trainer/create.do"><spring:message code="workingOut.create"/></a>
 	</security:authorize>