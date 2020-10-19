package view;

import controller.DataController;
import model.Dictionary;
import model.Word;

public class View {
    public static void main(String[] args) {
        String fileName = "history.txt";
        Dictionary dictionary = new Dictionary();
        DataController dataController = new DataController();
        Word word = new Word(1, "hello", "xin chào");
        dictionary.addWord(word);
        dataController.dictionaryExportToFile(dictionary, fileName);
        //dataController.showAllWord(dictionary);
    }
}
