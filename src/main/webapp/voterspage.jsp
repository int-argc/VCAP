<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="util.SetOperations"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>voters page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<link rel='stylesheet' type= 'text/css' href='VCAP.css'>
    </head>
    <body>
		<h1>Voter's Page</h1>

		<%
			SetOperations posSet = new SetOperations("position");
			Set<String> entries = posSet.sortDesc();
			for(String entry : entries) {
				out.println("<div class=\"position\">" + entry + "</div>");
				SetOperations candSet = new SetOperations(entry);
				Set<String> candidates = candSet.sortDesc();
				for(String cand : candidates) {
					out.println("<div class=\"candidate\">" + cand + "</div>");
				}
			}
		%>


        <script>

            $(document).ready(function() {
				var CODE_RIGHT = 39;
	            var CODE_ENTER = 13;
				var currSel = "";
                $(document.body).keydown(function(event) {
                    var code = event.keyCode;
                    if (code == CODE_RIGHT) {
                        var currSel = $("div.candidate.selected");
						var next = currSel.next(".candidate");
						if(next.length != 0) {
							next.addClass("selected");
							currSel.removeClass("selected");
						} else {
							currSel.removeClass("selected");
							currSel.siblings("div.candidate").first().addClass("selected");
						}
                    } else if(code == CODE_ENTER) {
						var currSel = $("div.candidate.selected");
                    	alert("vote for " + currSel.text());
					}
                });

				$(window).load(function() {
					$("div.candidate").first().addClass("selected");
				});
            });
        </script>
    </body>
</html>
