package jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteApp1 {
	
	public static void main(String[] args) throws Exception {
		
		String sql = """
				delete from sample_books
				where book_no = ?
				""";

		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "zxcv1234");
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, 10);
		int rowCount = pstmt.executeUpdate();
		System.out.println(rowCount + "개의 행이 삭제되었습니다.");
		
		pstmt.close();
		con.close();
		
	}

}
