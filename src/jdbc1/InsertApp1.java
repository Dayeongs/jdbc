package jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertApp1 {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * 자바의 데이터베이스 엑세스 절차
		 * 1. 오라클 jdbc diver를 로딩해서 드라이버 레지스터리에 등록시킨다.
		 * 2. 드라이버 레지스터리에 등록된 오라클 jdbc driver를 이용해서
		 * 	  오라클 데이터베이스와 연결을 담당하는 Connection 객체를 획득한다.
		 * 3. Connection 객체의 메소드를 실행해서 SQL을 데이터베이스로 전송하고
		 * 	  실행시키는 PreparedStatement 객체를 획득한다.
		 * 4. PreparedStatement 객체의 executeUpdate() 메소드를 실행해서
		 * 	  SQL을 데이터베이스로 보내 실행시키고 결과값을 받는다.
		 * 5. 데이터베이스 엑세스 작업에 사용했던 모든 자원을 반납한다.
		 */
		
		// 1. jdbc driver를 드라이버 레지스터리에 등록시킨다.
		// Class.forName(클래스 전체경로)은 지정된 클래스를 메모리에 로딩한다. 
		// 정적메소드, 객체생성없이 클래스만 메모리에 로딩시킨다.
		Class.forName("oracle.jdbc.OracleDriver");
		
		// 2. Connection 객체를 획득한다.
		// jdbc:oracle:thin 이름으로 등록된 oracle jdbc드라이버
		// @localhost:1521:xe - 내 컴퓨터의 1521번 포트에서 실행되는 xe 데이터베이스
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "hr";
		String password = "zxcv1234";
		Connection connection = DriverManager.getConnection(url, username, password);
		
		// 3. PreparedStatement 객체를 획득한다.
		// ?는 테이블명에 사용할 수 없다.
		String sql = """
				INSERT INTO SAMPLE_BOOKS
				(BOOK_NO, BOOK_TITLE, BOOK_WRITER, BOOK_PRICE, BOOK_STOCK)
				VALUES
				(?,?,?,?,?)	
		""";
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, 10);
		pstmt.setString(2, "이것이 자바다");
		pstmt.setString(3, "신용권");
		pstmt.setInt(4, 35000);
		pstmt.setInt(5, 20);
		
		// 4. SQL을 서버로 전송하고 실행시킨다.
		int rowCount = pstmt.executeUpdate();
		System.out.println(rowCount + "개의 행이 추가되었습니다.");
		
		
		// 5. 사용했던 자원을 반납한다.
		pstmt.close();
		connection.close();
		
	}

}
