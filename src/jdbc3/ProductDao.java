package jdbc3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDao {
	
	// 모든 상품정보를 반환하는 기능
	// 반환타입: List<Product>
	// 메소드이름: getAllProducts
	// 매개변수: 없음
	public List<Product> getAllProducts() throws SQLException {
		String sql = """
				select	
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
					prod_stock, prod_sold_out, prod_create_date, prod_update_date
				from 
					sample_products
				order by
					prod_no asc
			""";
		
		List<Product> productList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			Date createDate = rs.getDate("prod_create_date");
			Date updateDate = rs.getDate("prod_update_date");
			
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDiscountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
			product.setCreateDate(createDate);
			product.setUpdateDate(updateDate);
			
			productList.add(product);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	// 상품번호에 해당하는 상품정보를 반환하는 기능
	// 반환타입: Product
	// 메소드이름: getProductByNo
	// 매개변수: int ProdNo
	public Product getProductByNo(int prodNo) throws SQLException {
		String sql = """
				select
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
					prod_stock, prod_sold_out, prod_create_date, prod_update_date
				from
					sample_products
				where 
					prod_no = ?
			""";
		
		Product product = null;
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			product = new Product();
			product.setNo(rs.getInt("prod_no"));
			product.setName(rs.getString("prod_name"));
			product.setMaker(rs.getString("prod_maker"));
			product.setPrice(rs.getInt("prod_price"));
			product.setDiscountPrice(rs.getInt("prod_discount_price"));
			product.setStock(rs.getInt("prod_stock"));
			product.setSoldOut(rs.getString("prod_sold_out"));
			product.setCreateDate(rs.getDate("prod_create_date"));
			product.setUpdateDate(rs.getDate("prod_update_date"));
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return product;
	}
	

	// 최저가격, 최고가격을 전달받아서 해당 가격범위에 속하는 상품정보를 반환하는 기능
	// 반환타입: List<Product>
	// 메소드명: getProductByPrice
	// 매개변수: int minPrice, int maxPrice
	public List<Product> getproductByPrice(int minPrice, int maxPrice) throws SQLException {
		String sql = """
				select
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
					prod_stock, prod_sold_out, prod_create_date, prod_update_date
				from
					sample_products
				where 
					prod_price >= ? and prod_price <= ?
				order by prod_no asc
			""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, minPrice);
		pstmt.setInt(2, maxPrice);
		ResultSet rs = pstmt.executeQuery();
		
		List<Product> productList = new ArrayList<Product>();
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			Date createDate = rs.getDate("prod_create_date");
			Date updateDate = rs.getDate("prod_update_date");
			
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDiscountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
			product.setCreateDate(createDate);
			product.setUpdateDate(updateDate);
			
			productList.add(product);
		}
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	// 신규 상품정보를 저장하는 기능
	// 반환타입: void
	// 메소드명: insertProduct
	// 매개변수: Product product
	public void insertProduct(Product product) throws SQLException {
		String sql = """
				insert into sample_products
					(prod_no, prod_name, prod_maker, prod_price, 
					 prod_discount_price, prod_stock)
				values
					(?, ? ,? ,? ,?, ?)				
			""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, product.getNo());
		pstmt.setString(2, product.getName());
		pstmt.setString(3, product.getMaker());
		pstmt.setInt(4, product.getPrice());
		pstmt.setInt(5, product.getDiscountPrice());
		pstmt.setInt(6, product.getStock());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호를 전달받아서 해당 상품정보를 삭제하는 기능
	// 반환타입: void
	// 메소드명: deleteProductByNo
	// 매개변수: int ProdNo 
	public void deleteProductByNo(int prodNo) throws SQLException {
		String sql = """
				delete from sample_products
				where prod_no = ?			
			""";
		
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호, 가격, 할인가격을 전달받아서 해당 상품의 가격을 변경하는 기능
	// 반환타입: void
	// 메소드명: updatePrice
	// 매개변수: int prodNo, int prodPrice, int discountPrice
	public void updatePrice(int prodNo, int prodPrice, int discountPrice) throws SQLException {
		String sql = """
				update 
					sample_products
				set
					prod_price = ?,
					prod_discount_price = ?,
					prod_update_date = sysdate
				where 
					prod_no = ?
			""";
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, prodPrice);
		pstmt.setInt(2, discountPrice);
		pstmt.setInt(3, prodNo);

		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호, 수량을 전달받아서 해당상품의 수량을 변경하는 기능
	// 반환타입: void
	// 메소드명: updateStock
	// 매개변수: int prodNo, int stock
	public void updateStock(int prodNo, int stock) throws SQLException {

		String sql = """
				update
					sample_products
				set
					prod_stock = ?,
					prod_update_date = sysdate
				where
					prod_no = ?
			""";
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, stock);
		pstmt.setInt(2, prodNo);

		pstmt.executeUpdate();

		pstmt.close();
		connection.close();
		}
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch(ClassNotFoundException ex) {
			throw new SQLException(ex.getMessage(), ex);
		}
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "zxcv1234";
		
		return DriverManager.getConnection(url, user, password);
	}
}
