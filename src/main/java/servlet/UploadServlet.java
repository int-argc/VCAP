// UploadServlet.java: for uploading json formatted candidate list

package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.json.*;

import util.SetOperations;
import util.TextToStorage;

@WebServlet(urlPatterns = {"/UploadFile"})

public class UploadServlet extends HttpServlet {

    private static final int POS_SCORE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			out.println("Wrong page");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jsonStr = "";

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField())  {
                    // item is the file (and not a field), read it in and add to List
                    Scanner scanner = new Scanner(new InputStreamReader(item.getInputStream(), "UTF-8"));

                    //converting uploaded file to json string
                    while (scanner.hasNextLine())  {
                        String line = scanner.nextLine().trim();
                        if (line.length() > 0)  {
                            jsonStr += line;
                        }
                    }
                    scanner.close();

                    // convert candidate names to audio files
                    TextToStorage textStore = new TextToStorage();
                    ArrayList<String> positions = new ArrayList<String>();
                    SetOperations positionSet = new SetOperations("position");
                    positionSet.deleteSet();

                    // parse jsonstring
                    JSONObject obj = new JSONObject(jsonStr);
                    int count = obj.getInt("pos_count");
                    JSONArray arr = obj.getJSONArray("position");
                    for (int i = 0; i < count; i++) {
                        JSONObject pos = arr.getJSONObject(i);
                        String posname = pos.getString("name");
                        int cand_cnt = pos.getInt("num_candidates");
                        positionSet.add(POS_SCORE - i, posname);
                        // store an audio clip for this position
                        textStore.putTextToStorage(TextToStorage.beginStr + posname, posname);

                        SetOperations so = new SetOperations(posname);
                        so.deleteSet();
                        JSONArray candidates = pos.getJSONArray("candidates");
                        for (int j = 0; j < cand_cnt; j++) {
                            String name = candidates.getString(j);
                            // store audio clip of candidate name and store to db
                            textStore.putTextToStorage(name, name);
                            so.add(0, name);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace(System.err);
		}

        // candidates are added to db, redirect to index
        response.sendRedirect("success.jsp");

    }
}
