package server;

public class SQLRedactor implements AuthService {
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return SqlConnect.getNicknameByLoginAndPassword(login, password);
    }


    @Override
    public boolean registration( String nickname,String login, String password) {
        return SqlConnect.getRegistrationNewUsers(nickname,login,password);
    }

//    @Override
//    public boolean swapNick(String newNickName, String oldNickName) {
//        return SqlConnect.getUpdateNickName(newNickName,oldNickName);
//    }
}
