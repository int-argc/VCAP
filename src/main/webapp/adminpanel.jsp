<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='VCAP.css'>
        <title>ADMINISTRATOR ONLY</title>

    </head>
    <body>
        <h1>ADMINISTRATOR DASH-BORED</h1>
        <div align="center">
            <form action="UploadFile" method="POST" enctype="multipart/form-data">
                <input type="file" name="file"/> <br />
                <input type="submit" class="btn" value="Upload"/>
            </form>
        </div>
    </body>
</html>
