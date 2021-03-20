package com.kkb.mybatis.homework;
import java.sql.*;

public class week1_jdbc {

    public static void main(String[] args) throws Exception{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs =null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC",
                    "root", "abcd@1234");

            String sql = "SELECT id, name, url FROM websites where name = ?";

            statement = con.prepareStatement(sql);

            statement.setString(1, "Ã‘±¶");

            rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8°¢ Õ∑≈◊ ‘¥
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
