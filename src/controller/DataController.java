package controller;

import model.Dictionary;
import model.Word;
import java.io.*;
import java.sql.*;

public class DataController {

    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private String drive = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/dictionaries";
    private String user="root";
    private String password="quyen808590";
    private Connection conn;
    private PreparedStatement statement;

    /**
     * Đọc dữ liệu từ file ra diction.
     * @param dictionary diction.
     * @param fileName tên file.
     * @return diction.
     */
    public Dictionary insertFromFile(Dictionary dictionary, String fileName) {
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            dictionary = (Dictionary)ois.readObject();

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Xuất từ điển ra file.
     * @param dictionary Từ điển.
     * @param fileName tên file.
     */
    public void dictionaryExportToFile(Dictionary dictionary, String fileName) {
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(dictionary);

            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load dữ liệu từ mysql ra diction.
     * @param dictionary diction.
     * @return diction.
     */
    public Dictionary loadDataFromDictionary(Dictionary dictionary) {
        try {
            Class.forName(drive);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from tbl_edict";
            statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("idx");
                String target = resultSet.getString("word");
                String explain = resultSet.getString("detail");
                Word word = new Word(id, target, explain);
                dictionary.addWord(word);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    /**
     * Thêm dữ liệu vào mysql.
     * @param word từ cần thêm.
     */
    public void addWordToData(Word word) {
        try {
            String sql = "insert into tbl_edict"
                    + "(idx, word, detail)"
                    + "values (?,?,?)";
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.prepareStatement(sql);
            statement.setInt(1, word.getId());
            statement.setString(2, word.getWordTarget());
            statement.setString(3, word.getWordExplain());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Xóa dữ liệu trong mysql.
     * @param x vị trí idx cần xóa.
     */
    public void deleteWordInData(int x) {
        try {
            String sql = "delete from tbl_edict where idx =?";
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.prepareStatement(sql);
            statement.setInt(1, x);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Thay đổi dữ liệu trong mysql.
     * @param word từ cần thay đổi.
     */
    public void repairWordToData(Word word) {
        try {
            String sql = "update tbl_edict set word = ?, detail = ? where idx = ?";
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.prepareStatement(sql);
            statement.setString(1, word.getWordTarget());
            statement.setString(2, word.getWordExplain());
            statement.setInt(3, word.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
