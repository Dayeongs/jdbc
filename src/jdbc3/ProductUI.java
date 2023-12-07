package jdbc3;

import java.sql.SQLException;
import java.util.List;

public class ProductUI {

	private ProductDao productDao = new ProductDao();
	private Scanner scanner = new Scanner(System.in);
	
	public void showMenu() {
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("1.전체조회 2.상세조회 3.검색(가격) 4.신규등록 5.삭제 6.가격수정 7.재고수정 0.종료");
		System.out.println("----------------------------------------------------------------------------------");
		
		System.out.print("### 메뉴번호: ");
		int menuNo = scanner.nextInt();
		
		try {
			switch(menuNo)  {
			case 1: 전체조회(); break;
			case 2: 상세조회(); break;
			case 3: 검색(); break;
			case 4: 신규등록(); break;
			case 5: 삭제(); break;
			case 6: 가격수정(); break;
			case 7: 재고수정(); break;
			case 0: 종료(); break;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println();
		System.out.println();
		
		showMenu();
	}
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 상품 조회 >>");
		
		List<Product> products = productDao.getAllProducts();
		if(products.isEmpty()) {
			System.out.println("### 상품정보가 존재하지 않습니다.");
			return;
		}
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호\t이름\t제조사\t가격\t할인가격\t수량\t품절여부\t등록일");
		System.out.println("----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getName() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDiscountPrice() + "\t");
			System.out.print(product.getStock() + "\t");
			System.out.print(product.getSoldOut() + "\t");
			System.out.println(product.getCreateDate() + "\t");
		}
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 상세조회() throws SQLException {
		System.out.println("<<제품 정보 상세 조회>>");
		
		System.out.println("### 제품번호를 입력해서 제품 상세정보를 확인하세요.");
		System.out.print("### 제품번호: ");
		int prodNo = scanner.nextInt();
		
		Product product = productDao.getProductByNo(prodNo);
		if (product == null) {
			System.out.println("### 책번호: ["+prodNo+"] 책정보가 존재하지 않습니다.");
			return;
		}
		System.out.println("--------------------------------------------------");
		System.out.println("번호: " + product.getNo());
		System.out.println("이름: " + product.getName());
		System.out.println("제조사: " + product.getMaker());
		System.out.println("가격: " + product.getPrice());
		System.out.println("할인가격: " + product.getDiscountPrice());
		System.out.println("수량: " + product.getStock());
		System.out.println("최초등록일: " + product.getCreateDate());
		System.out.println("--------------------------------------------------");
		
	}
	
	private void 검색() throws SQLException {
		System.out.println("<< 도서 정보 검색 >>");
		
		System.out.println("### 가격범위를 입력해서 도서 정보를 확인하세요.");
		System.out.print("### 최저가격: ");
		int minPrice = scanner.nextInt();
		System.out.print("### 최고가격: ");
		int maxPrice = scanner.nextInt();
		
		List<Product> products = productDao.getproductByPrice(minPrice, maxPrice);
		if(products.isEmpty()) {
			System.out.println("### 상품정보가 존재하지 않습니다.");
			return;
		}
		System.out.println("--------------------------------------------------");
		System.out.println("번호\t제조사\t가격\t이름");
		System.out.println("--------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.println(product.getName() + "\t");
		}
		System.out.println("--------------------------------------------------");
	}
	
	private void 신규등록() throws SQLException {
		System.out.println("<< 신규 제품 등록 >>");
		System.out.println("### 신규 제품 정보를 입력하세요.");
		
		System.out.print("### 번호: ");
		int no = scanner.nextInt();
		System.out.print("### 이름: ");
		String name = scanner.nextString();
		System.out.print("### 제조사: ");
		String maker = scanner.nextString();
		System.out.print("### 가격: ");
		int price = scanner.nextInt();
		System.out.print("### 할인가격: ");
		int discountPrice = scanner.nextInt();
		System.out.print("### 수량: ");
		int stock = scanner.nextInt();
		
		Product product = new Product();
		product.setNo(no);
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDiscountPrice(discountPrice);
		product.setStock(stock);
		
		productDao.insertProduct(product);
		System.out.println("### 신규 제품을 등록하였습니다.");
	}
	
	private void 삭제() throws SQLException {
		System.out.println("<< 제품 정보 삭제 >>");
		System.out.println("### 제품번호를 입력받아서 제품정보를 삭제합니다.");
		
		System.out.print("### 제품번호 입력: ");
		int prodNo = scanner.nextInt();
		
		productDao.deleteProductByNo(prodNo);
		System.out.println("제품번호: ["+prodNo+"]번의 제품정보가 삭제되었습니다.");
	}
	
	private void 가격수정() throws SQLException {
		System.out.println("<< 제품 가격 수정 >>");
		System.out.println("### 제품번호를 입력받아서 제품가격, 제품할인가격을 수정합니다.");
		
		System.out.print("### 제품번호 입력: ");
		int prodNo = scanner.nextInt();
		System.out.print("### 제품가격 입력: ");
		int prodPrice = scanner.nextInt();
		System.out.print("### 제품할인가격 입력: ");
		int discountPrice = scanner.nextInt();
		
		productDao.updatePrice(prodNo, prodPrice, discountPrice);
		System.out.println("["+prodNo+"]번의 가격이 수정되었습니다.");
	}
	
	private void 재고수정() throws SQLException {
		System.out.println("<< 제품 재고 수정 >>");
		System.out.println("### 제품번호를 입력받아서 제품수량을 수정합니다.");
		
		System.out.print("### 제품번호 입력:");
		int prodNo = scanner.nextInt();
		System.out.print("### 제품수량 입력: ");
		int stock = scanner.nextInt();
		
		productDao.updateStock(prodNo, stock);
		System.out.println("["+prodNo+"]번의 수량이 수정되었습니다.");
	}
	
	private void 종료() {
		System.out.println("### 프로그램을 종료합니다.");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new ProductUI().showMenu();
	}
	
}
