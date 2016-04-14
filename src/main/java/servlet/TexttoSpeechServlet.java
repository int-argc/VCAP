
package servlet;

import connectors.TexttoSpeechConnector;
import connectors.ObjectStorageConnector;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import java.awt.Component;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import java.util.Random;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.util.ResponseUtil;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import javax.servlet.annotation.WebServlet;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet(name = "TexttoSpeechServlet", urlPatterns = {"/TexttoSpeechServlet"})

public class TexttoSpeechServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			out.println("Wrong page");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
		try{


			TexttoSpeechConnector textConnector = new TexttoSpeechConnector();

			ObjectStorageConnector objStorConnect = new ObjectStorageConnector();
			objStorConnect.createContainer("candidates");

			TextToSpeech service = new TextToSpeech();
			service.setUsernameAndPassword(textConnector.getUsername(),textConnector.getPassword());

			Payload audioData = null;

			String text = request.getParameter("candidate").toString();

			if ( text == null || text.length() == 0 ){
				out.println("Error occurred.");
				return;
			}

			Random rand = new Random();
			int random = rand.nextInt(500000);
			Integer objInt = random;
			//String fileName = objInt.hashCode()+"";
			String fileName = request.getParameter("filename").toString();

			InputStream speech = service.synthesize(text, "audio/wav");
			audioData = Payloads.create(speech);

			objStorConnect.uploadFile("candidates", fileName+".wav", audioData);

			//response.sendRedirect("convert.jsp?file="+fileName);
			response.sendRedirect("index.jsp");

			/* byte[] buf = new byte[2046];
			int len;
			while ((len = speech.read(buf)) > 0) {
				output.write(buf, 0, len);
			}

			response.setContentType("audio/wav");
			response.setHeader("Content-disposition","attachment;filename=output.wav");

			OutputStream os =output;

			os.flush();
			os.close();
			*/
		}
		catch( Exception e ){
			System.out.println(e);
		}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
