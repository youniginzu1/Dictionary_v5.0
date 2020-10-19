package model;

import java.io.Serializable;

public class Word implements Serializable {
    private int id;//id của từ trong mysql.
    private String wordTarget; // Từ mới.
    private String wordExplain; // Giải nghĩa.

    /**
     * Khởi tạo không tham số.
     */
    public Word() {
    }

    /**
     * Khởi tạo có tham số.
     * @param wordTarget Từ mới.
     * @param wordExplain Giải nghĩa.
     */
    public Word(int id, String wordTarget, String wordExplain) {
        this.id = id;
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    /**
     * Lấy từ tiếng Anh.
     * @return Trả về từ tiếng Anh.
     */
    public String getWordTarget() {
        return wordTarget;
    }

    /**
     * Khởi tạo từ tiếng Anh.
     * @param wordTarget Từ tiếng Anh.
     */
    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    /**
     * Lấy nghĩa tiếng Việt.
     * @return Trả về nghĩa tiếng Việt.
     */
    public String getWordExplain() {
        return wordExplain;
    }

    /**
     * Thiết lập nghĩa tiếng Việt.
     * @param wordExplain Nghĩa tiếng Việt.
     */
    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    /**
     * Lấy id của từ.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Thiết lập id của từ.
     * @param id id.
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return wordTarget + " - " + wordExplain;
    }
}
