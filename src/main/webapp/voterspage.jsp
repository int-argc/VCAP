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

		<h2>test:</h2>
		<%
			SetOperations posSet = new SetOperations("position");
			Set<String> entries = posSet.sortDesc();
		    for(String entry : entries) {
		        int score = posSet.getScore(entry);
				out.println("entry = " + entry);
				out.println("score = " + score);
			}

			SetOperations presSet = new SetOperations("president");
			Set<String> entries2 = presSet.sortDesc();
		    for(String entry : entries2) {
		        int score = presSet.getScore(entry);
				out.println("entry = " + entry);
				out.println("score = " + score);
			}
		%>

	</div>



</body>
</html>
