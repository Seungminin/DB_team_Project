package dbTwitter;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDataSet {
	
	private ArrayList<User> users; //ArrayList로 User를 저장 -> 우리는 서버에다가 
	//User 정보들을 서버에다가 저장을 할 것이다.
	private Customer cst = new Customer();
	private Connection con = cst.getConnection();
	public UserDataSet() {
		users = new ArrayList<User>();
		
	}
	
	// 회원 추가
	public boolean addUsers(User user){
		String sql = "INSERT INTO customer(user_id, password, name, phone, Email, birth, website, gender, introduction)"
				+ " values (?,?,?,?,?,?,?,?,?)";
		try {
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPw());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getphone());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getbirth());
			pstmt.setString(7, user.getwebsite());
			pstmt.setString(8, user.getGender());
			pstmt.setString(9, user.getintroduce());
			
			int r = pstmt.executeUpdate();
			users.add(user); 
			System.out.println("Insert user data "+ r + "rows");
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 아이디 중복 확인
    public boolean isIdOverlap(String id) {
    	return users.contains(new User(id));
    }
    // 회원 삭제
	public void withdraw(String id) {
		String sql = String.format("DELETE FROM customer where user_id = %s", id);
		
		users.remove(getUser(id));
    }
	// 유저 정보 가져오기
	public User getUser(String id) {
		return users.get(users.indexOf(new User(id)));
	}
	// 회원인지 아닌지 확인
	public boolean contains(User user) {
		
		return users.contains(user);
	}

}