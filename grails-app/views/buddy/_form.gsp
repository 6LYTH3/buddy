<%@ page import="th.co.osdev.Buddy" %>



<div class="fieldcontain ${hasErrors(bean: buddyInstance, field: 'buddyId', 'error')} required">
	<label for="buddyId">
		<g:message code="buddy.buddyId.label" default="Buddy Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="buddyId" type="number" value="${buddyInstance.buddyId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: buddyInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="buddy.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${buddyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: buddyInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="buddy.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" required="" value="${buddyInstance?.email}"/>
</div>

