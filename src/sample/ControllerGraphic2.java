package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import com.sun.speech.freetts.*;
import model.Dictionary;

import java.io.IOException;

public class ControllerGraphic2 {

    @FXML
    private WebView webView;//nghĩa của từ hiển thị dưới dạng web.
    private String target;//từ đó.

    /**
     * Thiết lập từ và nghĩa cho scen2.
     * @param target từ.
     * @param explain nghĩa của từ.
     */
    @FXML
    public void setExplain( String target, String explain) {
        webView.getEngine().loadContent(explain);
        this.target = target;
    }

    /**
     * Phát âm từ.
     * @param text Từ cần phát âm.
     */
    public void speakText(String text)
    {
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
        voice.deallocate();
    }

    /**
     * Phát âm từ qua button.
     * @param event click.
     */
    public void buttonVoice(MouseEvent event)
    {
        String text = target;
        speakText(text);
    }

    /**
     * Thay đổi scen2 sang scen1.
     * @param event sự kiện click chuột.
     * @throws IOException excep.
     */
    public void changeScreen2(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample1.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
