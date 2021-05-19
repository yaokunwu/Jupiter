package rpc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Searchitem
 */
@WebServlet("/search")
public class Searchitem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Searchitem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String username = "";
			if (request.getParameter("username") != null) {
				username = request.getParameter("username");
			}
			
			JSONArray array = new JSONArray();
			JSONObject obj = new JSONObject();
			try {
				array.put(new JSONObject().put("username", username));
				//array.put(new JSONObject().put("password", username));
				obj.put("usere", username);
				obj.put("pass", username);
				array.put(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			RpcHelper.writeJsonArray(response, array);

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
