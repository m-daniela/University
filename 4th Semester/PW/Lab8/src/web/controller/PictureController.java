package web.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import web.domain.Picture;
import web.exceptions.CustomException;
import web.utils.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class PictureController extends HttpServlet{

    public PictureController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        DatabaseConnection databaseConnection = new DatabaseConnection();

        if (action != null && action.equals("gallery")){
            // show all pictures from gallery
            resp.setContentType("application/json");
            int count = Integer.parseInt(req.getParameter("count"));
            List<Picture> pictures = databaseConnection.getGallery(count);
            PrintWriter out = new PrintWriter(resp.getOutputStream());
            out.println(jsonPictureList(pictures));
            out.flush();
        }
        else if (action != null && action.equals("vote")){
            // vote picture
            int vote = Integer.parseInt(req.getParameter("score"));
            int pictureId = Integer.parseInt(req.getParameter("id_picture"));
            int userId = Integer.parseInt(req.getParameter("userid"));
            resp.setContentType("application/text");
            PrintWriter out = new PrintWriter(resp.getOutputStream());
            try {
                databaseConnection.vote(vote, pictureId, userId);
                out.println("Vote registered");
                out.flush();
            } catch (SQLException | CustomException throwables) {
                out.println(throwables.getMessage());
                out.flush();
            }
        }
        databaseConnection.disconnect();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // add picture


        int userId = Integer.parseInt(req.getParameter("userid"));
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String action = req.getParameter("action");

        if (action != null && action.equals("yourpics")){
            // show user's gallery
            List<Picture> pictures = databaseConnection.getPictures(userId);
            PrintWriter out = new PrintWriter(resp.getOutputStream());
            pictures.forEach(p -> out.println("<img src = \"" + p.getPath() + "\" ><br>"));
            out.flush();
        }
        else if (action != null && action.equals("gallery")){
            // show all pictures from gallery
            int count = Integer.parseInt(req.getParameter("count"));
            List<Picture> pictures = databaseConnection.getGallery(count);
            PrintWriter out = new PrintWriter(resp.getOutputStream());
            out.println(jsonPictureList(pictures));
            out.flush();
        }
        else if (action != null && action.equals("top")){
            // show top pictures
            int top = Integer.parseInt(req.getParameter("value"));
            List<Picture> pictures = databaseConnection.getTop(top);
            PrintWriter out = new PrintWriter(resp.getOutputStream());
            pictures.forEach(p -> {
                try {
                    out.println("<p>Author: " + databaseConnection.getUser(p.getId()) + " / Score: " + p.getScore() + "</p>" +
                            "<img src = \"" + p.getPath() + "\" ><br>");
                } catch (SQLException throwables) {
                    out.println(throwables.getMessage());
                }
            });
            out.flush();
        }
        else if (action != null && action.equals("add")) {
            // upload pictures
            String file = req.getParameter("file");
            String url = req.getParameter("url");
            try {
                // from computer
                if (!file.isEmpty())
                    databaseConnection.addPicture(file, userId);
                // from URL
                else if (!url.isEmpty())
                    databaseConnection.addPicture(url, userId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/profile.jsp");
            requestDispatcher.forward(req, resp);
        }

        databaseConnection.disconnect();
    }

    private JSONArray jsonPictureList(List<Picture> pictures){
        JSONArray jsonPictures = new JSONArray();
        pictures.forEach(p->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_picture", p.getId());
            jsonObject.put("path", p.getPath());
            jsonObject.put("score", p.getScore());
            jsonObject.put("id_user", p.getUserId());
            jsonPictures.add(jsonObject);
        });
        return jsonPictures;
    }

}
