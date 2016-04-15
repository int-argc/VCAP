<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="util.SetOperations"%>
<%@page import="java.util.*"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='VCAP.css'>
        <title>PUBLIC EYE VIEWING</title>

    </head>
    <body>
        <h1>CURRENT VOTE RESULT:</h1>
        <div align="center">
            <%
				int max = 0, current = 0;
				SetOperations pres = new SetOperations("president");
				out.println("<h3>President</h3>");
				Set<String> pres_entries = pres.sortDesc();
				for (String entry : pres_entries){
					int score = pres.getScore(entry);
					out.println("<p>Candidate: "+entry+" | Current Votes: " + score + "</p>");
				}
			
				SetOperations vpres = new SetOperations("vice_president");
				Set<String> vpres_entries = vpres.sortDesc();
				out.println("<h3>Vice President</h3>");
				for (String entry : vpres_entries){
					int score = vpres.getScore(entry);
					out.println("<p>Candidate: "+entry+" | Current Votes: " + score + "</p>");
				}
			
			%>
			<a href="adminpanel.jsp"> Go Back </a>
			
        </div>
    </body>
</html>
