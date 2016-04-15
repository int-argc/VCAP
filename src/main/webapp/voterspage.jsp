
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="connectors.TexttoSpeechConnector"%>
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
		
		
			TexttoSpeechConnector t2s = new TexttoSpeechConnector();
		
			SetOperations posSet = new SetOperations("position");
			Set<String> entries = posSet.sortDesc();
			for(String entry : entries) {
				out.println("<div class=\"position\">" + entry + "</div>");
				SetOperations candSet = new SetOperations(entry);
				Set<String> candidates = candSet.sortDesc();
				for(String cand : candidates) {
					String url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=" + cand;
					
					
					String audiotag = "<audio id =\"" + cand + "\"  src = \"" + url + "\"controls>";
					out.println("<div class=\"candidate " + entry + "\">" + cand + "</div>");
					out.println(audiotag + "</audio>");
					//out.println("</div>");
				}
			}
			
			String url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=You Voted For";
			String audiotaghidden = "<audio id = \"you_vote\" src = \"" + url + "\"controls>";
			out.println("<br/>"+audiotaghidden+"</audio>" +"<br/>");
			
			url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=Voting will now start.";
			String audiotaghidden2 = "<audio id = \"start\" src = \"" + url + "\"controls>";
			out.println(audiotaghidden2+"</audio>"+"<br/>");
			
			url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=For the presidential candidates.";
			String audiotaghidden3 = "<audio id = \"pres_start\" src = \"" + url + "\"controls>";
			out.println(audiotaghidden3+"</audio>"+"<br/>");
			
			url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=For the vice presidential candidates.";
			String audiotaghidden4 = "<audio id = \"vpres_start\" src = \"" + url + "\"controls>";
			out.println(audiotaghidden4+"</audio>"+"<br/>");
			
			url = "https://" + t2s.getUsername() + ":" + t2s.getPassword() + "@stream.watsonplatform.net/text-to-speech/api/v1/synthesize?text=Voting will end now. Thank you!";
			String audiotaghidden5 = "<audio id = \"finish\" src = \"" + url + "\"controls>";
			out.println(audiotaghidden5+"</audio>"+"<br/>");
			
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
						var next = currSel.nextAll(cname + ".candidate").first();
						if(next.length != 0) {
							next.addClass("selected");
							currSel.removeClass("selected");
							next.next("audio").trigger("play");
						} else {
							currSel.removeClass("selected");
							currSel.siblings(cname).first().addClass("selected");
							currSel.siblings(cname).first().next("audio").trigger("play");
						}
                    } else if(code == CODE_ENTER) {
                    	//alert(POS_CODE);
                    	if(POS_CODE == 10) {	// president
		                	var currSel = $("div.candidate.president.selected");
		                	var candName = currSel.text();
		                	document.getElementById("presvote").value = candName;
		                	
		                	// audio: you voted
		                	var you_vote = document.getElementById("you_vote");
							you_vote.load();
							you_vote.play();
							
							$('#you_vote').on('ended', function(){
									currSel.next("audio").trigger("play");
									currSel.next("audio").on("ended",function(){
									      	
									// audio: starting vp vote
									var start_vpres = document.getElementById("vpres_start");
									start_vpres.load();
									start_vpres.play();
									
									$("#vpres_start").on('ended', function(){
										var currSel = $("div.candidate.vice_president.selected");
										currSel.next("audio").trigger("play");
									});
								});
							});
							
                    		$("div.candidate.vice_president").first().addClass("selected");
                    	} else if(POS_CODE == 9) {	// vp
                    		var currSel = $("div.candidate.vice_president.selected");
		                	var candName = currSel.text();
		                	document.getElementById("vpvote").value = candName;
		                	
		                	
		                	// audio: you voted
							var you_vote = document.getElementById("you_vote");
							you_vote.load();
							you_vote.play();
							
							
		                	// audio: name of candidate
							$('#you_vote').on('ended', function(){
									currSel.next("audio").trigger("play");
									currSel.next("audio").on("ended",function(){
									      	
									// audio: finish na!
									var finish_vote = document.getElementById("finish");
									finish_vote.load();
									finish_vote.play();
									
									$('#finish').on('ended', function(){
										window.location.href = "NextServlet?presvote=" + document.getElementById("presvote").value + "&vpvote=" + document.getElementById("vpvote").value;
									});
								});
							});

		                	// kaw na dito aidz...
                    	}
                    	POS_CODE = POS_CODE - 1;
						
					}

                });
				$(window).load(function() {
					// play audio to say voting will start
					var start = document.getElementById("start");
					start.load();
					start.play();
					
					$('#start').on('ended', function(){
						var pres_start = document.getElementById("pres_start");
						pres_start.load();
						pres_start.play();	
						$("#pres_start").on('ended', function(){
							var currSel = $("div.candidate.president.selected");
							currSel.next("audio").trigger("play");
						});
					});

					// play audio of selected name
					$("div.candidate.president").first().addClass("selected");
					
					
				});
            });
        </script>
    </body>
</html>
