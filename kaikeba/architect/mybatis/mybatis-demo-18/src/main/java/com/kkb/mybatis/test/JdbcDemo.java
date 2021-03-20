package com.kkb.mybatis.test;

import org.junit.Test;

import java.sql.*;

/**
 * 原生的JDBC代码存在几个问题：
 * 1.存在硬编码问题（获取连接、执行statement）
 * 2.连接频繁创建和销毁会浪费资源
 */
public class JdbcDemo {

	@Test
	public void test() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			// 1、加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 2、通过驱动管理类获取数据库链接connection = DriverManager
			connection = DriverManager.getConnection("jdbc:mysql://39.105.204.66:3306/kkb?characterEncoding=utf-8",
					"root", "root");

			// 3、定义sql语句 ?表示占位符
			String sql = "select * from user where username = ?";

			// 4、获取预处理 statement
			preparedStatement = connection.prepareStatement(sql);

			// 5、设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
			preparedStatement.setString(1, "千年老亚瑟");

			// 6、向数据库发出 sql 执行查询，查询出结果集
			rs = preparedStatement.executeQuery();

			// 7、遍历查询结果集
			while (rs.next()) {
				System.out.println(rs.getString("id") + " " + rs.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 8、释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
