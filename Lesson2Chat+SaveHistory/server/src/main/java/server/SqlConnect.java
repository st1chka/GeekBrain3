package server;

import java.io.File;
import java.sql.*;



public class SqlConnect {
    private static Connection connection;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psGetNickName;
    private static PreparedStatement psUpdateNickname;
    private static Statement stmt;



    public static boolean connect(){

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:SQLChat.db");
            allRequests();

            System.out.println("Есть подключение к БД");
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка поключения к БД");
            e.printStackTrace();
            return false;
        }


    }




    public static void allRequests() throws SQLException {
//        psUpdateNickname = connection.prepareStatement("update localChat set nickname = ? where = ?;");
        psGetNickName = connection.prepareStatement("SELECT nickname FROM localChat WHERE login = ? AND password = ?;");
        psRegistration = connection.prepareStatement("insert into localChat (nickname, login, password ) values (?,?,?) ;");
    }

    public static boolean getRegistrationNewUsers(String nickname, String login, String password){

        try {
            psRegistration.setString(2,nickname);
            psRegistration.setString(3,login);
            psRegistration.setString(1,password);
            psRegistration.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            psGetNickName.setString(1, login);
            psGetNickName.setString(2, password);
            ResultSet rs = psGetNickName.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }



    public static void disconnect() {

        try {
            connection.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
