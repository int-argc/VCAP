// UploadServlet.java: for uploading json formatted candidate list

package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;

@WebServlet(urlPatterns = {"/UploadFile"})

public class UploadServlet extends HttpServlet {
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

                    // parse jsonstring
                    JSONObject obj = new JSONObject(jsonStr);
                    int count = obj.getInt("pos_count");
                    JSONArray arr = obj.getJSONArray("position");
                    for (int i = 0; i < count; i++) {
                        JSONObject pos = arr.getJSONObject(0);
                        String posname = pos.getString("name");
                        int cand_cnt = pos.getString("num_candidates");
                        System.out.println("posname = " + posname)
                        JSONArray candidates = pos.getJsonArray("candidates");
                        for (int i; i < cand_cnt; i++) {
                            String name = candidates.getString(i);
                            System.out.println("naem = " + name);
                        }
                    }


                }
            }
        }
        catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace(System.err);
		}
    }
}
