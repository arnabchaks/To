<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="WEB-INF/showErrorMessage.jsp" isELIgnored="false"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="default.css">
</head>

<body>
<%-- 	<p><%@include file="showImage.jsp"%></p> --%>
	<p>
		<a href="controller?action=goToMenu">[Return to Menu]</a>
	</p>
	


	<form:form action="controller" method="post" commandName="wallet">
		<input type="hidden" name="action" value="saveCar" />
		<%-- Set this value to id property of car attribute --%>
		<table>
			<!-- input fields -->
			<tr>
				<td>Mobile no.<font color="red"><sup>*</sup></font>
				</td>
				<td><form:input type="text" path="mobileno" value="${wallet.mobileno}" /></td>
				<%-- Set this value to make property of car attribute --%>
			</tr>
			<tr>
				<td>Name</td>
				<td><form:input type="text" path="name" value="${wallet.name}" /></td>
				<%-- Set this value to model property of car attribute --%>
			</tr>
			<tr>
				<td>Balance<font color="red"><sup>*</sup></font></td>
				<td><form:input type="text" path="balance" value="${wallet.balance}"/></td>
				<%-- Set this value to modelYear property of car attribute --%>
			</tr>

			<!-- Save/Reset buttons -->
			<tr>
				<td colspan="2"><input type="submit" name="save" value="Save" />
					&nbsp;&nbsp; <input type="reset" name="reset" value="Reset" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
