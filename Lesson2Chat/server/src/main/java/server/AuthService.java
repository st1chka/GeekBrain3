package server;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);

//    boolean updateNickname(String newNickName, String oldNickName);

    boolean registration(String nickname,String login, String password );

//    boolean swapNick(String nickname, String s);
}
