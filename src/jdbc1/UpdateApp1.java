package jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateApp1 {
	
	public static void main(String[] args) throws Exception {
		// 문자열을 적을때에는 오타 주의!
		String sql = """
				update sample_books
				set
					book_title = ?,
					book_writer = ?,
					book_price = ?,
					book_stock = ?
				where
					book_no = ?
				""";
		
		// jdbc driver를 메모리에 로딩한다.
		Class.forName("oracle.jdbc.OracleDriver");
		// 데이터베이스와 연결을 담당하는 Connection객체를 획득한다.
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "zxcv1234");
		// SQL 전송을 담당하는 PreparedStatement객체를 획득한다.
		PreparedStatement pstmt = con.prepareStatement(sql);
		// SQL의 ?에 바인딩할 값을 순서대로 설정한다.
		pstmt.setString(1, "이것이 자바다");
		pstmt.setString(2, "신용권");
		pstmt.setInt(3, 31000);
		pstmt.setInt(4, 2);
		pstmt.setInt(5, 10);
		// SQL을 데이터베이스에 보내서 실행시키고 결과를 반환받는다.
		int rowCount = pstmt.executeUpdate();
		System.out.println(rowCount + "개의 행이 변경되었습니다.");
		
		// 사용했던 자원을 반납한다.
		pstmt.close();
		con.close();

	}

}
