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

<spring:message code="confirm.telephone" var="confirmTelephone"/>
<form:form action="actor/administrator,auditor,customer,nutritionist,trainer/edit.do" modelAttribute="registrationForm" onsubmit="javascript:calcMD5();">
	<jstl:choose>
		<jstl:when test="${rol == 'Customer'}">
			<h2>
				<spring:message code="header.customer" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Trainer'}">
			<h2>
				<spring:message code="header.trainer" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Administrator'}">
			<h2>
				<spring:message code="header.administrator" />
			</h2>
		</jstl:when>
		<jstl:when test="${rol == 'Auditor'}">
			<h2><spring:message code="header.auditor"/></h2>
		</jstl:when>
		<jstl:when test="${rol == 'Nutritionist'}">
			<h2><spring:message code="header.nutritionist"/></h2>
		</jstl:when>

	</jstl:choose>

	<form:hidden path="id"/>
	
	<fieldset>
		<legend><spring:message code="actor.legend"/></legend>
	
		<acme:textbox code="actor.name" path="name"/>
		
		<br />
		
		<acme:textbox code="actor.middleName" path="middleName"/>
		
		<br />
		
		
		<acme:textbox code="actor.surname" path="surname"/>
		
	
		<br />
		
		
		<acme:textbox code="actor.photo" path="photo"/>
		
		<br />
		
		<acme:textbox code="actor.email" path="email" placeholder="mail@email.com"/>
		
		<br />
		
		<acme:textbox code="actor.phoneNumber" path="phoneNumber" placeholder="+34 (111) 654654654" id="phoneNumber"/>
		
		<br />
		
		<acme:textbox code="actor.address" path="address"/>
		
		<br /> 
		
		
	</fieldset>
 
	<fieldset>
		<legend><spring:message code="userAccount.legend"/></legend>
	
		<acme:textbox path="userAccount.username" code="userAccount.username" />
		<br>

		<acme:password path="userAccount.password" code="userAccount.password" id="passwordId" />
		<br>

		<acme:password path="userAccount.confirmPassword" code="userAccount.confirmPassword" id="confirmPasswordId"/>
		<br>
		
	</fieldset>
	
 
 
 	<jstl:choose>
		<jstl:when test="${rol == 'Administrator'}">
			<acme:submit name="saveAdmin" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteAdmin" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Customer'}">
			<acme:submit name="saveCustomer" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteCustomer" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Trainer'}">
			<acme:submit name="saveTrainer" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteTrainer" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Auditor'}">
			<acme:submit name="saveAuditor" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteAuditor" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
		<jstl:when test="${rol == 'Nutritionist'}">
			<acme:submit name="saveNutritionist" code="actor.save" onclick="javascript: return checkTelephone('${confirmTelephone}');"/>
			<input type="submit" name="deleteNutritionist" value="<spring:message code="actor.delete"/>" onclick="return confirm('<spring:message code="actor.confirm.delete"/>')"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:cancel url="actor/display.do" code="actor.cancel"/>
 
	
	<hr>
	
</form:form>