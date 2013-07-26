<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<!DOCTYPE html>
<html>
	<head>
    	<title>Build Time</title>
    	<link rel="stylesheet" type="text/css" href="css/main.css"/>
    	<meta charset="utf-8"> 
  	</head>
  	<body>
  		<div class="container">
			<div style="width: 100%;">
				<div class="line"></div>
				<div class="topLine">
					<div style="float: left;">
						<img src="images/task.png" style="width: 85px;"/>
					</div>
					<div style="float: left;" class="headline">Build Information 1.0</div>
				</div>
			</div>

		<div style="clear: both;"></div>  
		<p class="bold">You have a total number of ${fn:length(builds)} build informations. Total Elapsed Time: <span class="red">${totalElapsedTime}</span>.</p>

		<table>
			<tr>
				<th class="medium-field text-center">Project Name</th>
				<th class="large-field text-center">Description (groupID - artifactID - version)</th>
				<th class="url-field text-center">Goals</th>
				<th class="url-field text-center">System</th>
				<th class="small-field text-center">Created</th>
				<th class="elapsed-field text-center">Elapsed Time</th>
				<th class="small-field text-center">Options</th>
			</tr>
			<c:forEach var="build" items="${builds}">
				<tr>
					<td class="text-center">${build.projectName}</td>
					<td class="text-center">${build.groupID} - ${build.artifactID} - ${build.version}</td>
					<td class="text-center">${build.formattedGoals}</td>
					<td class="text-center">${build.buildUser} - ${build.operatingSystem} - ${build.osArchitecture}</td>
					<td class="text-center"><fmt:formatDate value="${build.buildDate}" pattern="dd/MM, HH:mm:ss" /></td>
					<td class="text-center">${build.formattedElapsedTime}</td>
					<td class="text-center">
						<a class="done" href="/remove?id=${build.id}">Remove</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<hr/>
		</div>
	</body>
</html> 