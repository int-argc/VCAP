<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.SetOperations"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Text-to-Speech and Object Storage services</title>

</head>
<body>

	<div>

		<h1>Voter's Page</h1>

		<%
			SetOperations posSet = new SetOperations("position");
			Set<String> entries = posSet.sortDesc();
		    for(String entry : entries) {
				out.println("<h3> " + entry + "</h3>");
				SetOperations memset = new SetOperations(entry);
				Set<String> entries2 = memset.sortDesc();
				for (String entry2: entries2){
					out.println("<p>"+entry2+"</p>");
				}
			}

		%>
		<a href="index.jsp"> Go Back </a>

	</div>



</body>
</html>
