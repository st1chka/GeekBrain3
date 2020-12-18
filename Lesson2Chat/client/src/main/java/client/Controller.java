package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private HBox authPanel;
    @FXML
    private HBox msgPanel;
    @FXML
    private ListView<String> clientList;

    private Socket socket;
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8188;

    private DataInputStream in;
    private DataOutputStream out;

    private boolean authenticated;
    private String nickname;
    private Stage stage;
    private Stage regStage;
    private RegController regController;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        msgPanel.setManaged(authenticated);
        msgPanel.setVisible(authenticated);
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        clientList.setManaged(authenticated);
        clientList.setVisible(authenticated);
        if (!authenticated) {
            nickname = "";
        }
        setTitle(nickname);
        textArea.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createRegWindow();
        Platform.runLater(() -> {
            stage = (Stage) textField.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                System.out.println("bye");
                if (socket != null && !socket.isClosed()) {
                    try {
                        out.writeUTF("/end");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        setAuthenticated(false);
    }

    private void connect() {
        try {
            this.socket = new Socket("localhost", 8188);
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
            (new Thread(() -> {
                try {
                    while(true) {
                        String str = this.in.readUTF();
                        if (!str.startsWith("/")) {
                            this.textArea.appendText(str + "\n");
                        } else {
                            if (str.equals("/regok")) {
                                this.regController.addMessage("Регистрация прошла успешно");
                            }

                            if (str.equals("/regno")) {
                                this.regController.addMessage("Регистрация не получилась\nВозможно предложенные лоин или никнейм уже заняты");
                            }

                            if (!str.startsWith("/authok ")) {
                                if (!str.equals("/end")) {
                                    continue;
                                }

                                throw new RuntimeException("Сервер нас вырубил по таймауту");
                            }

                            this.nickname = str.split("\\s")[1];
                            this.setAuthenticated(true);

                            while(true) {
                                while(true) {
                                    str = this.in.readUTF();
                                    if (str.startsWith("/")) {
                                        if (str.startsWith("/clientlist ")) {
                                            String[] token = str.split("\\s");
                                            Platform.runLater(() -> {
                                                this.clientList.getItems().clear();

                                                for(int i = 1; i < token.length; ++i) {
                                                    this.clientList.getItems().add(token[i]);
                                                }

                                            });
                                        }

                                        if (str.equals("/end")) {
                                            return;
                                        }

                                        if (str.startsWith("/yournickis ")) {
                                            this.nickname = str.split(" ")[1];
                                            this.setTitle(this.nickname);
                                        }
                                    } else {
                                        this.textArea.appendText(str + "\n");
                                    }
                                }
                            }
                        }
                    }
                } catch (RuntimeException var13) {
                    System.out.println(var13.getMessage());
                } catch (IOException var14) {
                    var14.printStackTrace();
                } finally {
                    this.setAuthenticated(false);

                    try {
                        this.socket.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                }

            })).start();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    @FXML
    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        String msg = String.format("/auth %s %s", loginField.getText().trim(), passwordField.getText().trim());
        try {
            out.writeUTF(msg);
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String username) {
        String title = String.format("Чатек[ %s ]", username);
        if (username.equals("")) {
            title = "Чатек";
        }
        String chatTitle = title;
        Platform.runLater(() -> {
            stage.setTitle(chatTitle);
        });
    }

    @FXML
    public void clickClientlist(MouseEvent mouseEvent) {
        String msg = String.format("/w %s ", clientList.getSelectionModel().getSelectedItem());
        textField.setText(msg);
    }

    private void createRegWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reg.fxml"));
            Parent root = fxmlLoader.load();
            regStage = new Stage();
            regStage.setTitle("СпэйсЧат Регистрация");
            regStage.setScene(new Scene(root, 350, 300));
            regStage.initModality(Modality.APPLICATION_MODAL);

            regController = fxmlLoader.getController();
            regController.setController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void showRegWindow(ActionEvent actionEvent) {
        regStage.show();
    }

    public void tryToReg(String login, String password, String nickname) {
        String msg = String.format("/reg %s %s %s",  nickname , login,password);

        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
