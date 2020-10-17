package web.utils;

import web.domain.Picture;
import web.domain.User;
import web.exceptions.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/lab8";
    private final String user = "root";
    private final String password = "pass";

    public DatabaseConnection() {
        connect();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch(Exception ex) {
            System.out.println("Connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) throws SQLException, CustomException {
        String cmd = "select * from users where username = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new User(resultSet.getInt("id_user"), resultSet.getString("username"), resultSet.getString("password"));
        }
        else{
            throw new CustomException("Invalid username or password.");
        }
    }

    public User signUp(String username, String password, String email) throws SQLException, CustomException {
        String cmd = "insert into users(username, email, password) values(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);
        preparedStatement.executeUpdate();

        return authenticate(username, password);

    }

    public void addPicture(String path, int userId) throws SQLException {
        String cmd = "insert into pictures(path, id_user) values(?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setString(1, path);
        preparedStatement.setInt(2, userId);

        preparedStatement.executeUpdate();

    }

    public List<Picture> getGallery(int count){
//        String cmd = "select * from pictures limit ?;";
        String cmd = "select * from pictures;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
//            preparedStatement.setInt(1, count);
            return getAll(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Picture> getPictures(int userid){
        String cmd = "select * from pictures where id_user = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setInt(1, userid);
            return getAll(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public List<Picture> getTop(int top){
        String cmd = "select * from pictures order by score desc limit ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setInt(1, top);
            return getAll(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<Picture> getAll(PreparedStatement preparedStatement) throws SQLException {
        List<Picture> result = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id_picture");
            String path = resultSet.getString("path");
            int score = resultSet.getInt("score");
            int user_id = resultSet.getInt("id_user");

            Picture picture = new Picture(id, path, user_id, score);
            result.add(picture);
        }
        return result;
    }

    public String getUser(int id_picture) throws SQLException {
        String cmd = "select u.username from users u inner join pictures p on u.id_user = p.id_user where id_picture = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setInt(1, id_picture);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }

    public void registerVote(int vote, int id_picture, int id_user) throws SQLException, CustomException {
        String cmd = "update pictures set score = ? where id_picture = ? and id_user <> ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        int old_vote = getScore(id_picture);
        preparedStatement.setInt(1, old_vote + vote);
        preparedStatement.setInt(2, id_picture);
        preparedStatement.setInt(3, id_user);

        preparedStatement.executeUpdate();
    }

    public void vote(int vote, int id_picture, int id_user) throws SQLException, CustomException {

        if (getPicture(id_picture).getUserId() != id_user && !userVoted(id_user, id_picture)){

            registerVote(vote, id_picture, id_user);
            String cmd = "insert into scoreboard(id_user, id_picture, score) values(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_picture);
            preparedStatement.setInt(3, vote);

            preparedStatement.executeUpdate();
        }
        else throw new CustomException("Vote already registered");
    }

    private boolean userVoted(int id_user, int id_picture) throws SQLException {
        String cmd = "select * from scoreboard where id_user = ? and id_picture = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setInt(1, id_user);
        preparedStatement.setInt(2, id_picture);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private int getScore(int id_picture) throws SQLException, CustomException {
        return getPicture(id_picture).getScore();
//        String cmd = "select score from pictures where id_picture = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
//        preparedStatement.setInt(1, id_picture);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        if (resultSet.next()) {
//            return resultSet.getInt("score");
//        }
//        return 0;
    }

    public Picture getPicture(int id_picture) throws SQLException, CustomException {
        String cmd = "select * from pictures where id_picture = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setInt(1, id_picture);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Picture(id_picture, resultSet.getString("path"), resultSet.getInt("id_user"), resultSet.getInt("score"));
        }
        else{
            throw new CustomException("Picture not found");
        }
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
