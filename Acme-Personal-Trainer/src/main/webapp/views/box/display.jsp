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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<p>
	<strong ><spring:message code="box.name"/>: </strong>
	<jstl:out value="${box.name}"/>
</p>

<jstl:if test="${box.parent != null}">
	<p>
		<strong> <spring:message code="box.parent" />: </strong>
		<a href="box/administrator,auditor,customer,nutritionist,trainer/display.do?boxId=${box.parent.id}">
			<jstl:out value="${box.parent.name}"/>
		</a>
	</p>
</jstl:if>

<jstl:if test="${not empty childBoxes}">
	<p> <strong> <spring:message code="box.child" />: </strong> </p>
	<display:table name="childBoxes" id="row" requestURI="box/administrator,auditor,customer,nutritionist,trainer/display.do" pagesize="5" class="displaytag">
		<display:column>
			<jstl:if test="${!row.isSystemBox}">
				<a href="box/administrator,auditor,customer,nutritionist,trainer/edit.do?boxId=${row.id}">
					<spring:message code="box.edit" />
				</a>
			</jstl:if>
		</display:column>
		
		<display:column>
			<a href="box/administrator,auditor,customer,nutritionist,trainer/display.do?boxId=${row.id}">
				<spring:message code="box.display" />
			</a>
		</display:column>
		
		<display:column property="name" titleKey="box.name" />
	</display:table>
</jstl:if>

<jstl:if test="${not empty messages}">
	<p> <strong> <spring:message code="box.messages" />: </strong> </p>
	<display:table name="messages" id="fila" requestURI="box/administrator,auditor,customer,nutritionist,trainer/display.do" pagesize="5" class="displaytag">
		<display:column>
			<a href="message/administrator,auditor,customer,nutritionist,trainer/display.do?messageId=${fila.id}&boxId=${box.id}">
				<spring:message code="box.display" />
			</a>
		</display:column>
		<display:column>
			<a href="message/administrator,auditor,customer,nutritionist,trainer/move.do?messageId=${fila.id}&boxId=${box.id}">
				<spring:message code="box.move" />
			</a>
		</display:column>
		<display:column>
			<a href="message/administrator,auditor,customer,nutritionist,trainer/delete.do?messageId=${fila.id}&boxId=${box.id}" onclick="return confirm('<spring:message code="message.confirm.delete"/>')">
				<spring:message code="box.delete" />
			</a>
		</display:column>
		
		<spring:message code="message.date.format" var="dateFormat"/>
		<display:column property="sentMoment" titleKey="message.sendMoment" format="${dateFormat}"/>
		
		<display:column property="subject" titleKey="message.subject"/>
		
		<display:column property="priority" titleKey="message.priority"/>
		
		<display:column property="sender.userAccount.username" titleKey="message.sender"/>
	</display:table>
	<br/>
</jstl:if>

<!-- LINKS -->

<a href="box/administrator,auditor,customer,nutritionist,trainer/list.do">
	<spring:message code="box.return" />
</a>


