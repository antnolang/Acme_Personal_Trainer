<%--
 * select.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="map" required="true" type="java.util.Map"%>
<%@ attribute name="itemsSelected" required="true" type="java.util.Collection"%>
<%@ attribute name="multiple" required="false" %>
<%@ attribute name="code" required="true" %>

<%-- <%@ attribute name="items" required="true" type="java.util.Collection" %> --%>
<%-- <%@ attribute name="itemLabel" required="true" %> --%>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<jstl:if test="${multiple == null}">
	<jstl:set var="multiple" value="false" />
</jstl:if>

<%-- Definition --%>


<div>
	<form:label path="${path}">
			<spring:message code="${code}" />:
	</form:label>
	<form:select path="${path}" multiple="${multiple}">
		<jstl:forEach var="elementId" items="${map.keySet()}">
			<jstl:set var="isSelected" value="${false}"/>
			<jstl:forEach var="item" items="${itemsSelected}">
				<jstl:if test="${item.id == elementId}">
					<jstl:set var="isSelected" value="${true}"/>
				</jstl:if>
			</jstl:forEach>
			<jstl:choose>
				<jstl:when test="${isSelected}">
					<form:option label="${map.get(elementId)}" value="${elementId}" selected="true"/>
				</jstl:when>
				<jstl:otherwise>
					<form:option label="${map.get(elementId)}" value="${elementId}"/>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="${path}"/>
	<br />
</div>