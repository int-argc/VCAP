x<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel='stylesheet' type= 'text/css' href='VCAP.css'>
	<title>ADMINISTRATOR ONLY</title>
	
</head>

<body>
	<div align = "center"> 
	
		<form action ="TexttoSpeechServlet" method="post">
			<h1> ADMINISTRATOR </h1>
			
			<input type="text" name="candidate" placeholder='ENTER NEW CANDIDATE'  required/> <br/> 
			<input type="text" name="filename" placeholder='ENTER FILENAME'  required/> <br/>
			<input type="submit" name = "Convert" value = "Convert to Audio" />  <br/>
		</form>	
		<form action = "Logout" method="post">
			<input type="submit" name = "Logout" value = "Logout" /> <br/>
		</form>
		
	</div>
</body>
</html>
