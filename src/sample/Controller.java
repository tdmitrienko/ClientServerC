package sample;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Socket socket;
    public TextField text;
    public TextField otKogo;
    public TextArea chat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connect();
            handle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect() throws IOException {
        socket=new Socket("localhost",8080);
    }

    private  void handle() {
        Thread thread= new Thread(() -> {
            while(true){
                try {
                    DataInputStream dis=new DataInputStream(socket.getInputStream());
                    if(dis.available()<=0){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;// если нет сообщения то спим 10 и продолжаем заново
                    }
                    else{
                        Paket paket=new Paket();
                        paket.read(dis);
                        chat.setText(chat.getText()+paket.getLoginOtpravitel()+" -> " + paket.getMail()+"\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private static void end() throws IOException {
        socket.close();
    }

    public void otpravit(ActionEvent event) throws IOException {
        if(!otKogo.getText().isEmpty() && !text.getText().isEmpty()) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Paket paket = new Paket(otKogo.getText(), text.getText());
            paket.write(dos);
            try {
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            text.setText("");
            otKogo.setDisable(true);
        }

    }
}
