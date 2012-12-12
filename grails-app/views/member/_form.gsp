<%@ page import="th.co.osdev.Member" %>



<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'secretId', 'error')} required">
	<label for="secretId">
		<g:message code="member.secretId.label" default="Secret Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="secretId" type="number" value="${memberInstance.secretId}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: memberInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="member.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${memberInstance?.name}"/>
</div>

