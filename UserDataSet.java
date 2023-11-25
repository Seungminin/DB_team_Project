import java.util.ArrayList;

public class UserDataSet {
	private ArrayList<User> users; //ArrayList로 User를 저장 -> 우리는 서버에다가 
	//User 정보들을 서버에다가 저장을 할 것이다.
	
	public UserDataSet() {
		users = new ArrayList<User>();
	}
	
	// 회원 추가
	public void addUsers(User user) {
	        users.add(user);
	}
	// 아이디 중복 확인
    public boolean isIdOverlap(String id) {
    	return users.contains(new User(id));
    }
    // 회원 삭제
	public void withdraw(String id) {
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