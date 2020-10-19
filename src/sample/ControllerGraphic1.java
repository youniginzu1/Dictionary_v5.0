package sample;

import controller.ControllerUtility;
import controller.DataController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dictionary;
import model.Word;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerGraphic1 implements Initializable {

    ObservableList listS = FXCollections.observableArrayList();//danh sách khi tìm kiếm
    ObservableList listD = FXCollections.observableArrayList();//danh sách dữ liệu của diction

    private String fileNameH = "history.txt";//tên file lịch sử tìm kiếm

    private Dictionary dictionary = new Dictionary();//dữ liệu từ điển
    private Dictionary history = new Dictionary();//dữ liệu lịch sử

    private DataController dataController = new DataController();//điều khiển dữ liệu data
    private ControllerUtility controllerUtility = new ControllerUtility();//điểu khiển tiện ích
    @FXML
    private ListView<String> moveListR;//danh sách dữ liệu hiển thị ở phần thêm sửa xóa
    @FXML
    private ListView<String> moveListG;//danh sách dữ liệu hiển thị ở thanh tìm kiếm google
    @FXML
    private TextField searchG;//thanh tìm kiếm google
    @FXML
    private TextField searchR;//thanh tìm kiếm ở phần thêm sủa xóa
    @FXML
    private TextField targetR;//từ thêm vào ở phần thêm sửa xóa
    @FXML
    private TextArea explainR;//nghĩa của từ thêm vào ở phần thêm sửa xóa
    @FXML
    private TextField targetTranslate;//từ ở phần translate
    @FXML
    private TextArea explainTranslate;//nghĩa ở phần translate


    /**
     * Hàm tự khởi động khi ctrinh chạy.
     * @param url url.
     * @param resourceBundle resoure.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    /**
     * Hàm load dữ liệu cho chương trình
     */
    @FXML
    private void loadData() {

        listS.removeAll(listS);
        listD.removeAll(listD);
        dictionary = dataController.loadDataFromDictionary(dictionary);
        history = dataController.insertFromFile(history, fileNameH);
        for (int i = 0; i < dictionary.size(); i++) {
            listD.add(dictionary.getWord(i).getWordTarget());
        }
        for (int i = 0; i < history.size(); i++) {
            listS.add(history.getWord(i).getWordTarget());
        }
        moveListR.setItems(listD);
    }

    /**
     * Hàm tìm kiếm từ ở thanh google bằng enter
     * @param event sự kiện enter.
     * @throws IOException excep.
     */
    @FXML
    private void lookWord1(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            String str = searchG.getText();
            boolean kt = true;
            String result = controllerUtility.dictionaryLookUp(dictionary, str);
            if (result == null) {
                kt = false;
                result = "Dữ liệu từ vựng chưa có, xin vui lòng tìm từ khác!";
                str = "";
            }
            if (kt && !checkList(history, str)) {
                Word word = new Word(history.size() + 1, str, result);
                history.addWord(word);
                dataController.dictionaryExportToFile(history, fileNameH);
            }
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("sample2.fxml"));
            Parent tableView = loader.load();
            Scene scene = new Scene(tableView);
            ControllerGraphic2 controller = loader.getController();
            controller.setExplain(str, result);
            window.setScene(scene);
        }
    }

    /**
     * Hàm tìm kiếm ở thanh google bằng button
     * @param event button.
     * @throws IOException excep.
     */
    @FXML
    private void lookWord2(ActionEvent event) throws IOException {
        String str = searchG.getText();
        boolean kt = true;
        String result = controllerUtility.dictionaryLookUp(dictionary, str);
        if (result == null) {
            kt = false;
            result = "Dữ liệu từ vựng chưa có, xin vui lòng tìm từ khác!";
            str = "";
        }
        if (kt && !checkList(history, str)) {
            Word word = new Word(history.size() + 1, str, result);
            history.addWord(word);
            dataController.dictionaryExportToFile(history, fileNameH);
        }
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample2.fxml"));
        Parent tableView = loader.load();
        Scene scene = new Scene(tableView);
        ControllerGraphic2 controller = loader.getController();
        controller.setExplain(str, result);
        window.setScene(scene);
    }

    /**
     * Hàm hiện các từ gợi ý cho các từ được tìm kiếm.
     * @param event sự kiện nhập từ vào thanh google.
     */
    @FXML
    private void searchWord(KeyEvent event) {
        String str = searchG.getText().trim();
        listS.removeAll(listS);
        if (!str.isEmpty()) {
            ArrayList<String> result = controllerUtility.dictionarySearcher(dictionary, str);
            for (int i = 0; i < result.size(); i++) {
                listS.add(result.get(i));
            }
            moveListG.setItems(listS);
        }
    }

    /**
     * Hàm hiện các từ gợi ý cho các từ được tìm kiếm.
     * @param event sự kiện nhập từ vào thanh sửa xóa.
     */
    @FXML
    private void searchWord2(KeyEvent event) {
        String str = searchR.getText().trim();
        listS.removeAll(listS);
        if (!str.isEmpty()) {
            ArrayList<String> result = controllerUtility.dictionarySearcher(dictionary, str);
            for (int i = 0; i < result.size(); i++) {
                listS.add(result.get(i));
            }
            moveListR.setItems(listS);
        } else {
            moveListR.setItems(listD);
        }
    }

    /**
     * Hàm in ra lịch sử tìm kiếm.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void printHistory(MouseEvent event) {
        String str = searchG.getText().trim();
        listS.removeAll(listS);
        if (str.isEmpty()) {
            for (int i = 0; i < history.size(); i++) {
               listS.add(history.getWord(i).getWordTarget());
            }
        }
        moveListG.setItems(listS);
    }

    /**
     * Ần lịch sử tìm kiếm.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void hideHistory(MouseEvent event) {
        listS.removeAll(listS);
        moveListG.setItems(listS);
    }

    /**
     * Thêm từ vào danh sách qua sự kiện.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void addWordToList(MouseEvent event) {
        if (checkList(dictionary,targetR.getText())) {
            windowMessage("Từ này đã có trong từ điển. Vui lòng nhập từ khác!");
        } else {
            Word word = new Word(dictionary.getWord(dictionary.size() - 1)
                    .getId() + 1, targetR.getText(), explainR.getText());
            dictionary.addWord(word);
            dataController.addWordToData(word);
            listD.add(word.getWordTarget());
            windowMessage("Bạn đã thêm từ *" + word.getWordTarget() + " vào từ điển!");
        }
        targetR.setText("");
        explainR.setText("");
        moveListR.setItems(listD);
        searchR.setText("");
    }

    /**
     * Xóa từ trong danh sách qua sự kiện.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void deleteWordInList(MouseEvent event) {
        String targetInList = moveListR.getSelectionModel().getSelectedItem();
        int index = 0;
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.getWord(i).getWordTarget().equals(targetInList)) {
                index = dictionary.getWord(i).getId();
                dictionary.removeWord(i);
                listD.remove(i);
                windowMessage("Bạn đã xóa từ *" + targetInList + " trong từ điển!");
                break;
            }
        }
        dataController.deleteWordInData(index);
        targetR.setText("");
        explainR.setText("");
        moveListR.setItems(listD);
        listS.removeAll(listS);
        searchR.setText("");
    }

    /**
     * Xóa từ trong danh sách qua sự kiện.
     * @param event sự kiện nhấn nút delete.
     */
    @FXML
    private void deleteWordKey(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            int index = 0;
            String targetInList = moveListR.getSelectionModel().getSelectedItem();
            for (int i = 0; i < dictionary.size(); i++) {
                if (dictionary.getWord(i).getWordTarget().equals(targetInList)) {
                    index = dictionary.getWord(i).getId();
                    dictionary.removeWord(i);
                    listD.remove(i);
                    windowMessage("Bạn đã xóa từ *" + targetInList + " trong từ điển!");
                    break;
                }
            }
            dataController.deleteWordInData(index);
            targetR.setText("");
            explainR.setText("");
            moveListR.setItems(listD);
            listS.removeAll(listS);
            searchR.setText("");
        }
    }

    /**
     * In ra chi tiết một từ trong danh sách.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void showWordToRepair(MouseEvent event) {
        String targetInList = moveListR.getSelectionModel().getSelectedItem();
        targetR.setText(targetInList);
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.getWord(i).getWordTarget().equals(targetInList)) {
                explainR.setText(dictionary.getWord(i).getWordExplain());
                break;
            }
        }
    }

    /**
     * Thay đổi dữ liệu của một từ trong danh sách.
     * @param event sự kiện click chuột.
     */
    @FXML
    private void repairWordInList(MouseEvent event) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.getWord(i).getWordTarget().equals(targetR.getText())) {
                dictionary.getWord(i).setWordExplain(explainR.getText());
                windowMessage("Bạn đã sửa thành công từ *" + targetR.getText());
                dataController.repairWordToData(dictionary.getWord(i));
                targetR.setText("");
                explainR.setText("");
                searchR.setText("");
                moveListR.setItems(listD);
                listS.removeAll(listS);
                return;
            }
        }
        windowMessage("Không có từ này trong từ điển. Vui lòng nhập lại");
    }

    /**
     * Tìm kiếm từ thông qua lịch sử.
     * @param event sự kiện click đúp.
     * @throws IOException excep.
     */
    public void lookByHistory(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                String str = moveListG.getSelectionModel().getSelectedItem();
                if (str == null) return;
                boolean kt = true;
                String result = controllerUtility.dictionaryLookUp(dictionary, str);
                if (result == null) {
                    kt = false;
                    result = "Dữ liệu từ vựng chưa có, xin vui lòng tìm từ khác!";
                    str = "";
                }
                if (kt && !checkList(history, str)) {
                    Word word = new Word(history.size() + 1, str, result);
                    history.addWord(word);
                    dataController.dictionaryExportToFile(history, fileNameH);
                }
                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("sample2.fxml"));
                Parent tableView = loader.load();
                Scene scene = new Scene(tableView);
                ControllerGraphic2 controller = loader.getController();
                controller.setExplain(str, result);
                window.setScene(scene);
            }
        }
    }

    /**
     * Kiểm tra một từ có trong danh sách ko?
     * @param dict danh sách.
     * @param target từ cần tìm.
     * @return TorF.
     */
    public boolean checkList(Dictionary dict, String target) {
        for (int i = 0; i < dict.size(); i++) {
            if (dict.getWord(i).getWordTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dịch từ translate.
     * @param event click chuột.
     */
    public void GGTranslate(MouseEvent event) {
        try {
            String translate = controllerUtility.googleTranslate("vi", targetTranslate.getText().toLowerCase());
            explainTranslate.setText(translate);
        } catch (NullPointerException e) {
            System.out.println("Done");
        }
    }

    /**
     * Dịch từ translate.
     * @param event phím enter.
     */
    public void enterToTranslate(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                String translate = controllerUtility.googleTranslate("vi", targetTranslate.getText().toLowerCase());
                explainTranslate.setText(translate);
            }
        } catch (NullPointerException e) {
            System.out.println("Done");
        }
    }

    /**
     * Phát âm từ tiếng anh.
     * @param event click chuột.
     */
    public void speak(MouseEvent event) {
        ControllerGraphic2 controllerGraphic2 = new ControllerGraphic2();
        controllerGraphic2.speakText(targetTranslate.getText());
    }

    /**
     * Thông báo ra thông tin cho người dùng.
     * @param message thông điệp
     */
    public void windowMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
