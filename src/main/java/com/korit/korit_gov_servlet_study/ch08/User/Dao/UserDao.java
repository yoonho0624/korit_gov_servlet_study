package com.korit.korit_gov_servlet_study.ch08.User.Dao;

import com.korit.korit_gov_servlet_study.ch08.User.Entity.User;
import com.korit.korit_gov_servlet_study.ch08.User.Util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static UserDao instance;
    private UserDao() {}
    public static UserDao getInstance() {
        if (instance == null) instance = new UserDao();
        return instance;
    }
    // user추가
//    public User addUser(User user) {
//        String sql = "insert into user_tb(user_id, username, password, age, create_dt) values (0, ?, ?, ?, now())";
//        try(Connection con = ConnectionFactory.getConnection();
//            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            ps.setInt(3, user.getAge());
//            if (ps.execute()) {
//                try(ResultSet rs = ps.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        Long userId = rs.getLong("user_id");
//                        user.setUserId(userId);
//                    }
//                }
//                return user;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    // UserDao.java
    public User addUser(User user) {
        // 1. SQL 수정: user_id와 그에 해당하는 값 (0)을 구문에서 완전히 제거해야 합니다.
        String sql = "insert into user_tb(username, password, age, create_dt) values (?, ?, ?, now())";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAge());

            // 2. execute() 대신 executeUpdate() 사용 (이 부분은 이미 올바르게 수정되었습니다.)
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 3. 생성된 키를 가져와서 User.java의 타입(Integer)에 맞게 설정합니다.
                        // *안전을 위해 Long을 쓰고 싶다면 User.java의 userId도 Long으로 바꿔야 합니다.*
                        int userId = rs.getInt(1);
                        user.setUserId(userId);
                    }
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // username으로 user찾기
    public Optional<User> findByUsername(String username) {
        String sql = "select user_id, username, password, age, create_dt from user_tb where username = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toUser(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public Optional<List<User>> findByKeyword(String keyword) {
        String sql = "select user_id, username, password, age, create_dt from user_tb where username like ?";
        List<User> userList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userList.add(toUser(rs));
                }
            }
            return Optional.of(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public Optional<List<User>> getUserAll() {
        String sql = "select user_id, username, password, age, create_dt from user_tb";
        List<User> userList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userList.add(toUser(rs));
                }
            }
            return Optional.of(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public User toUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getInt("user_id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .age(resultSet.getInt("age"))
                .createDt(resultSet.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
}
