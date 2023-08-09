package org.example;

import java.sql.*;

public class SelectAllTest {
    public static void main(String[] args){
        // DB연결을 위한 인터페이스.
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // DBMS 접속, jdbc URL은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환한다.
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/examplesdb?useUnicode=true&serverTimezone=Asia/Seoul",
                            "root",
                            "Rdbckr69173+");

            if(conn != null){
                System.out.println("DBMS 연결 성공!!");
                System.out.println(conn.getClass().getName()); // getClass().getName() 자바 리플렉션.
            }

            // SQL을 작성하고, SQL을 실행
            // insert into role(role_id, name) values(값, '값');
            // conn아 지금 연결된 DBMS에 이 SQL을 준히배줘. 그런데 물음표 부분은 남겨놔.
            // 준비한 것을 참조하도록 PreparedStatement를 반환
            ps = conn.prepareStatement("select * from role");

            // select문이 실행되면 실행된 결과는 DBMS에 있다.
            // 실행된 결과는 ResultSet이 참조한다.
            rs = ps.executeQuery(); // select문 실행

            // next()는 데이터를 한 줄 (record)을 읽어오면 true를 반환.
            // 더이상 읽어올게 없을때까지 반복한다.
            while (rs.next()){
                int roleId = rs.getInt("role_id");
                String name = rs.getString("name");
                System.out.println(roleId + ", " + name);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }finally {
            try {
                // rs 자원 해제
                if(rs != null){
                    rs.close();
                }
                // ps 자원 해제
                if(ps != null){
                    ps.close();
                }

                // 연결 끊기
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        }
    }
}
