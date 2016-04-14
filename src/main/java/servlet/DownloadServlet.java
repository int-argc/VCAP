
package servlet;

import connectors.ObjectStorageConnector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.openstack4j.model.storage.object.SwiftObject;

@WebServlet(urlPatterns = {"/Download"})
public class DownloadServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		//response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter()
        try {
			ObjectStorageConnector connect = new ObjectStorageConnector();

            SwiftObject swiftObj;
            InputStream inStr = null;
            OutputStream output = null;

            List<? extends SwiftObject> objectList = connect.listAllObjects("exam_container");
            String fileName = request.getParameter("file").toString()+".wav";

            for (int i = 0; i < objectList.size(); i++) {
                if (fileName.equals(objectList.get(i).getName())) {
                    swiftObj = connect.getFile("exam_container", fileName);
                    response.setContentType("audio/wav");
                    response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
                    inStr = swiftObj.download().getInputStream();
                    output = response.getOutputStream();

					byte[] buf = new byte[2046];
					int len;
					while ((len = inStr.read(buf)) > 0) {
						output.write(buf, 0, len);
					}

					response.setContentType("audio/wav");
					response.setHeader("Content-disposition","attachment;filename=\""+fileName+"\"");

					OutputStream os =output;

					os.flush();
					os.close();
                    /*
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while((bytesRead = inStr.read(buffer)) != -1) {
                        outStr.write(buffer, 0, bytesRead);
                    }
                    inStr.close();
                    outStr.flush();
                    outStr.close();
                    */

                    /*IOUtils.copy(inStr, outStr);
                    inStr.close();
                    outStr.flush();
                    outStr.close();*/
                }
            }
        } catch(Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
