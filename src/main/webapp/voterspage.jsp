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
					out.println("<div class=\"candidate " + entry + "\">" + cand + "</div>");
				}
			}
		%>
		
		<br>
		President: <input type="text" id="presvote" name="presvote" />
		Vice President: <input type="text" id="vpvote" name="vpvote" />
		
        <script>

            $(document).ready(function() {
				var CODE_RIGHT = 39;
	            var CODE_ENTER = 13;
				var currSel = "";
				var POS_CODE = 10;	// initially president
				
                $(document.body).keydown(function(event) {
                    var code = event.keyCode;
                    if (code == CODE_RIGHT) {
                    	var cname = "";
                    	if(POS_CODE == 10) {
                    		cname = "div.candidate.president";
                    	} else if(POS_CODE == 9) {
                    		cname = "div.candidate.vice_president";
                    	}
                        var currSel = $(cname + ".selected");
						var next = currSel.next(".candidate");
						if(next.length != 0) {
							next.addClass("selected");
							currSel.removeClass("selected");
						} else {
							currSel.removeClass("selected");
							currSel.siblings(cname).first().addClass("selected");
						}
                    } else if(code == CODE_ENTER) {
                    	if(POS_CODE == 10) {	// president
		                	var currSel = $("div.candidate.president.selected");
		                	var candName = currSel.text();
		                	document.getElementById("presvote").value = candName;
		                	
		                	// audio: you voted
		                	// audio: name of candidate
		                	
		                	// audio: starting vp vote
                    		$("div.candidate.vice_president").first().addClass("selected");
                    	} else if(POS_CODE == 9) {	// vp
                    		var currSel = $("div.candidate.vice_president.selected");
		                	var candName = currSel.text();
		                	document.getElementById("vpvote").value = candName;
		                	
		                	
		                	// audio: you voted
		                	// audio: name of candidate
		                	// audio: finish na!
		                	// kaw na dito aidz...
                    	}
                    	POS_CODE = POS_CODE - 1;
					}
                });

				$(window).load(function() {
					// play audio to say voting will start
					// play audio of selected name
					$("div.candidate.president").first().addClass("selected");
				});
            });
        </script>
    </body>
</html>
