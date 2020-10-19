package controller;

import model.Dictionary;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import com.darkprograms.speech.translator.GoogleTranslate;

public class ControllerUtility {

    /**
     * Tra cứu từ điển.
     * @param dictionary Từ điển.
     * @param word Từ cần tra.
     */
    public String dictionaryLookUp(Dictionary dictionary, String word) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.getWord(i).getWordTarget().equals(word)) {
                return dictionary.getWord(i).getWordExplain();
            }
        }
        return null;
    }

    /**
     * Tìm kiếm từ điển.
     * @param dictionary Từ điển.
     * @param key Từ khóa.
     */
    public ArrayList<String> dictionarySearcher(Dictionary dictionary, String key) {
        ArrayList<String> result = new ArrayList<>();
        //String pattern = ".*" + key.toLowerCase() + ".*";
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.getWord(i).getWordTarget().startsWith(key)) {
                result.add(dictionary.getWord(i).getWordTarget());
            }
        }
        return result;
    }

    /**
     * Dịch từ.
     * @param languagetarget Ngôn ngữ phát hiện.
     * @param target Từ cần dịch.
     * @return từ đã được dịch.
     */
    public  String googleTranslate(String languagetarget,String target) {
        String target_translated="";
        try {
            target_translated= GoogleTranslate.translate(languagetarget,target);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No internet connection!");
        }
        return target_translated;
    }
}
