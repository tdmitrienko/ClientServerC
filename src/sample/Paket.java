package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Paket {
    private String loginOtpravitel;
    private String mail;

    public Paket(String emailOtpravitel, String mail) {
        this.loginOtpravitel = emailOtpravitel;
        this.mail = mail;
    }

    public Paket() {

    }

    public String getLoginOtpravitel() {
        return loginOtpravitel;
    }

    public String getMail() {
        return mail;
    }

    public void write(DataOutputStream dos){
        try {
            dos.writeUTF(loginOtpravitel);
            dos.writeUTF(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(DataInputStream dis){
        try {
            loginOtpravitel=dis.readUTF();
            mail=dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
