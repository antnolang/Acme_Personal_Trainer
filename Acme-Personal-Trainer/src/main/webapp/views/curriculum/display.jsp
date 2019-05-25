<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:if test="${isAuditable}">
	<h2>
		<a href="audit/auditor/create.do?curriculumId=${curriculum.id}"><spring:message code="curriculum.create.audit"/></a>
	</h2>
</jstl:if>


<fieldset>
	<legend><spring:message code="curriculum.fieldset.general"/></legend>
	<p>
		<strong><spring:message code="curriculum.ticker"/></strong>
		<jstl:out value="${curriculum.ticker}"/>
	</p>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.personalRecord"/></legend>
	
	<ul>
		<li>
			<strong><spring:message code="curriculum.personalRecord.fullName"/></strong>
			<jstl:out value="${curriculum.personalRecord.fullName}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalRecord.photo"/></strong>
			<img src="${curriculum.personalRecord.photo}" alt="Personal Record Photo" height="200px" width="200px"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalRecord.email"/></strong>
			<jstl:out value="${curriculum.personalRecord.email}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalRecord.phoneNumber"/></strong>
			<jstl:out value="${curriculum.personalRecord.phoneNumber}"/>
		</li>
		<li>
			<strong><spring:message code="curriculum.personalRecord.linkedInProfile"/></strong>
			<a href="${curriculum.personalRecord.linkedInProfile}">
				<jstl:out value="${curriculum.personalRecord.linkedInProfile}"/>
			</a>
		</li>
	</ul>
	
	<jstl:if test="${isOwner}">
		<a href="personalRecord/trainer/edit.do?personalRecordId=${curriculum.personalRecord.id}">
			<spring:message code="curriculum.edit"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.endorserRecord"/></legend>
	
	<display:table name="${curriculum.endorserRecords}" id="endRecord" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="endorserRecord/display.do?endorserRecordId=${endRecord.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner}">
			<display:column>
				<a href="endorserRecord/trainer/edit.do?endorserRecordId=${endRecord.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="fullname" titleKey="curriculum.endorserRecord.fullname" sortable="true"/>
		<display:column property="linkedInProfile" titleKey="curriculum.endorserRecord.linkedInProfile" autolink="true"/>
	</display:table>
	
	<jstl:if test="${isOwner}">
		<a href="endorserRecord/trainer/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.endorserRecord"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.educationRecord"/></legend>
	
	<display:table name="${curriculum.educationRecords}" id="edRecord" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="educationRecord/display.do?educationRecordId=${edRecord.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner}">
			<display:column>
				<a href="educationRecord/trainer/edit.do?educationRecordId=${edRecord.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="diplomaTitle" titleKey="curriculum.educationRecord.diplomaTitle" sortable="true"/>
		<display:column property="institution" titleKey="curriculum.educationRecord.institution" sortable="true"/>
	</display:table>
	
	<jstl:if test="${isOwner}">
		<a href="educationRecord/trainer/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.educationRecord"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.professionalRecord"/></legend>
	
	<display:table name="${curriculum.professionalRecords}" id="profRecord" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="professionalRecord/display.do?professionalRecordId=${profRecord.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner}">
			<display:column>
				<a href="professionalRecord/trainer/edit.do?professionalRecordId=${profRecord.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="company" titleKey="curriculum.professionalRecord.company" sortable="true"/>
		<display:column property="role" titleKey="curriculum.professionalRecord.role" sortable="true"/>
	</display:table>
	
	<jstl:if test="${isOwner}">
		<a href="professionalRecord/trainer/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.professionalRecord"/>
		</a>
	</jstl:if>
</fieldset>

<fieldset>
	<legend><spring:message code="curriculum.fieldset.miscellaneousRecord"/></legend>
	
	<display:table name="${curriculum.miscellaneousRecords}" id="miscRecord" requestURI="${requestURI}" class="displaytag" pagesize="5">
		<display:column>
			<a href="miscellaneousRecord/display.do?miscellaneousRecordId=${miscRecord.id}">
				<spring:message code="curriculum.display"/>
			</a>
		</display:column>
		
		<jstl:if test="${isOwner}">
			<display:column>
				<a href="miscellaneousRecord/trainer/edit.do?miscellaneousRecordId=${miscRecord.id}">
					<spring:message code="curriculum.edit"/>
				</a>
			</display:column>
		</jstl:if>
		
		<display:column property="title" titleKey="curriculum.miscellaneousRecord.title" sortable="true"/>
	</display:table>
	
	<jstl:if test="${isOwner}">
		<a href="miscellaneousRecord/trainer/create.do?curriculumId=${curriculum.id}">
			<spring:message code="curriculum.create.miscellaneousRecord"/>
		</a>
	</jstl:if>
</fieldset>