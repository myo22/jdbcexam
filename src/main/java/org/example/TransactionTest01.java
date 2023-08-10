package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionTest01 {
    public static void main(String[] args){
        // DB연결을 위한 인터페이스.
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // DBMS 접속, jdbc URL은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환한다.
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/examplesdb?useUnicode=true&serverTimezone=Asia/Seoul",
                            "root",
                            "Rdbckr69173+");
            // 자동 커밋을 false로 설정.
            conn.setAutoCommit(false);


            if(conn != null){
                System.out.println("DBMS 연결 성공!!");
                System.out.println(conn.getClass().getName()); // getClass().getName() 자바 리플렉션.
            }

            // SQL을 작성하고, SQL을 실행
            // insert into role(role_id, name) values(값, '값');
            // conn아 지금 연결된 DBMS에 이 SQL을 준히배줘. 그런데 물음표 부분은 남겨놔.
            // 준비한 것을 참조하도록 PreparedStatement를 반환
            ps = conn.prepareStatement("delete from role where role_id = ?");

            // 물음표에 값을 채워줘. -> 바인딩. -> 바인딩까지 하면 SQL을 실행할 준비.
            ps.setInt(1, 3); // 1번째 물음포에 정수 값을 설정.

            // SQL실행. executeUpdate(); - insert, update, delete할 때 사용.
            int updateCount =  ps.executeUpdate();
            System.out.println("수정된 건수: " + updateCount);

            System.out.println("10초간 쉰다.");
            Thread.sleep(10000); // 10초간 멈춘다.
            System.out.println("10초를 다 쉬었다..");

            conn.commit();
        } catch (Exception ex) {
            try {
                System.out.println("ROLLBACK합니다.");
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }finally {
            try {
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
