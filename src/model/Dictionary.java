package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictionary implements Serializable {
    private ArrayList<Word> words = new ArrayList<>(); // Mảng lưu trữ các từ.

    /**
     * Hàm khởi tạo không tham số.
     */
    public Dictionary() {
    }

    /**
     * Thêm từ vựng vào trong từ điển.
     * @param word Từ vựng.
     */
    public void addWord(Word word) {
        words.add(word);
    }

    /**
     * Kích thước của từ điển.
     * @return Kích thức.
     */
    public int size() {
        return words.size();
    }

    /**
     * Lấy một từ theo vị trí.
     * @param index vị trí.
     * @return trả về từ.
     */
    public Word getWord(int index) {
        return words.get(index);
    }

    /**
     * Xóa một từ ở vị trí index.
     * @param index vị trí cần xóa.
     */
    public void removeWord(int index) {
        words.remove(index);
    }

}
