package db_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
	private static String driver;
	private static String url;
	private static String user;
	private static String pass;
	private static Connection con = null;
	
	public static void main(String[] args) {
		  try (Connection con = getConnection()) {
		    createUserTable(); // Create the 'customer' table first
		    createFollowTable(); // Create the 'follow' table
		    createPostTable(); // Create the 'post' table
		    createCommentTable(); // Create the 'comment' table
		    
		    
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		}

    //Table 생성, DB생성.
	public static void createUserTable() {
        try (Connection con = getConnection();
             PreparedStatement create = con.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS `db_final`.`customer` (" +
                             "`user_id` int NOT NULL AUTO_INCREMENT," +
                             "`password` VARCHAR(255)," +
                             "`name` VARCHAR(255)," +
                             "`phone` VARCHAR(255)," +
                             "`Email` VARCHAR(255)," +
                             "`birth` VARCHAR(255)," +
                             "`website` VARCHAR(255)," +
                             "`gender` VARCHAR(255)," +
                             "`introduction` VARCHAR(255)," +
                             "PRIMARY KEY(`user_id`))"+
                             "Engine = InnoDB;")) {
            create.execute();
            System.out.println("User table successfully created");
        } catch (Exception e) {
            System.out.println("Error creating user table: " + e.getMessage());
        }
    }

	public static void createFollowTable() {
	    try (Connection con = getConnection();
	         PreparedStatement create = con.prepareStatement(
	             "CREATE TABLE IF NOT EXISTS `db_final`.`follow` (" +
	                     "`follow_id` int NOT NULL AUTO_INCREMENT," +
	                     "`following_id` int NOT NULL," +
	                     "PRIMARY KEY (`follow_id`, `following_id`)," +  // 변경: PRIMARY KEY에 following_id 추가
	                     "INDEX idx_following_id (`following_id`)," +
	                     "FOREIGN KEY (`following_id`) REFERENCES `customer` (`user_id`)" +
	                     "  ON DELETE CASCADE ON UPDATE CASCADE);")) {
	        create.execute();
	        System.out.println("Follow table successfully created");
	    } catch (Exception e) {
	        System.out.println("Error creating follow table: " + e.getMessage());
	    }
	}

	public static void createPostTable() {
		try (Connection con = getConnection();
				PreparedStatement create = con.prepareStatement(
						"CREATE TABLE IF NOT EXISTS `db_final`.`post` (" +
								"`post_id` VARCHAR(45) NOT NULL," +
								"`content` TEXT NULL," +
								"`location` VARCHAR(100) NULL," +
								"`user_id` int NOT NULL," + // 수정: user_id 데이터 타입 변경
								"PRIMARY KEY (`post_id`)," +
								"FOREIGN KEY (`user_id`)" +
								"  REFERENCES `db_final`.`customer` (`user_id`)" +
								"  ON DELETE CASCADE" +
								"  ON UPDATE CASCADE)" +
						"ENGINE = InnoDB;")) {
	           	create.execute();
	            System.out.println("Post table successfully created");
	        } catch (Exception e) {
	            System.out.println("Error creating post table: " + e.getMessage());
	        }
	    }

	public static void createCommentTable() {
		try (Connection con = getConnection();
				PreparedStatement create = con.prepareStatement(
						"CREATE TABLE IF NOT EXISTS `db_final`.`comment` (" +
								"`comment_id` VARCHAR(45) NOT NULL," +
								"`content` VARCHAR(200) NULL," +
							    "`parent_id` VARCHAR(45) NOT NULL," + // Change data type to VARCHAR(45)
								"`user_id` int NOT NULL," +
								"`post_id` VARCHAR(45) NOT NULL," +
								"`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
								"PRIMARY KEY (`comment_id`)," +
								"FOREIGN KEY (`parent_id`)" +
								"  REFERENCES `db_final`.`comment` (`comment_id`)" + // Change reference table and column
								"  ON DELETE CASCADE" +
								"  ON UPDATE CASCADE," +
								"FOREIGN KEY (`user_id`)" +
								"  REFERENCES `db_final`.`customer` (`user_id`)" +
								"  ON DELETE CASCADE" +
								"  ON UPDATE CASCADE," +
								"FOREIGN KEY (`post_id`)" +
								"  REFERENCES `db_final`.`post` (`post_id`)" +
								"  ON DELETE CASCADE" +
								"  ON UPDATE CASCADE)" +
						"ENGINE = InnoDB;")) {
	            create.execute();
	            System.out.println("Comment table successfully created");
	        } catch (Exception e) {
	            System.out.println("Error creating comment table: " + e.getMessage());
	        }
	    }
	public void post_Content(String content) {
	      Statement stmt = null;
	        try {
	           stmt = con.createStatement();
	            String sql = "SELECT content FROM post WHERE content = ?";
	            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	                pstmt.setString(1, content); 
	                ResultSet resultSet = pstmt.executeQuery();
	                
	                while (resultSet.next()) {
	                    String post_Id = resultSet.getString("post_id");
	                    String post_Content = resultSet.getString("content");
	                    System.out.println("Post ID: " + post_Id + ", Content: " + post_Content);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }

	 public void comment_Content(String content) {
	        try (Connection con = getConnection()) {
	            String sql = "SELECT content FROM comment WHERE content = ?";
	            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	                pstmt.setString(1, content); 
	                ResultSet resultSet = pstmt.executeQuery();

	                while (resultSet.next()) {
	                    String comment_Id = resultSet.getString("comment_id");
	                    String comment_Content = resultSet.getString("content");
	                    System.out.println("Comment ID: " + comment_Id + ", Content: " + comment_Content);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
	 
	public void delete(String id, int index) {
	    PreparedStatement pstmt = null;
	    ResultSet res = null;

	    try {
	        // 1. 팔로우하는 친구의 ID 조회
	        String selectSql = "SELECT following_id FROM follow WHERE follow_id = ?";
	        pstmt = con.prepareStatement(selectSql);
	        pstmt.setString(1, id);
	        res = pstmt.executeQuery();

	        int count = 0;
	        String[] friendIds = new String[2];
	        while (res.next() && count < 2) {
	            friendIds[count] = res.getString(1);
	            System.out.println(res.getString(1));
	            count++;
	        }

	        // 2. 친구 삭제
	        if (index >= 0 && index < count) {
	            String deleteSql = "DELETE FROM follow WHERE following_id = ? && follow_id = "+id;
	            pstmt = con.prepareStatement(deleteSql);
	            pstmt.setString(1, friendIds[index]);
	            int rowsAffected = pstmt.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Success delete follow");
	            } else {
	                System.out.println("Failed delete follow");
	            }
	        } else {
	            System.out.println("Fail because of syntax");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (res != null) res.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public void getId(String id, String[] friends) {
		 Statement stmt = null;
	     ResultSet res = null;
	        try {
	            String sql = "SELECT user_id FROM customer WHERE user_id IN (SELECT following_id FROM follow WHERE follow_id = " + id + ")";
	            stmt = con.createStatement();
	            res = stmt.executeQuery(sql);

	            int count = 0;
	            while (res.next() && count < 3) {
	                // 결과가 있으면 해당 결과의 "name" 컬럼 값을 객체에 저장
	            	if (count == 0) {
	                    friends[count] = res.getString(1);
	                } else if (count == 1) {
	                    friends[count]= res.getString(1);
	                }
	                count++;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (stmt != null) stmt.close();
	                if (res != null) res.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	}
	public void getName(String id, String[] names) {
        Statement stmt = null;
        ResultSet res = null;
        try {
            String sql = "SELECT name FROM customer WHERE user_id IN (SELECT following_id FROM follow WHERE follow_id = " + id + ")";
            stmt = con.createStatement();
            res = stmt.executeQuery(sql);

            int count = 0;
            while (res.next() && count < 3) {
                // 결과가 있으면 해당 결과의 "name" 컬럼 값을 객체에 저장
            	if (count == 0) {
                    names[count] = res.getString(1);
                } else if (count == 1) {
                    names[count]= res.getString(1);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (res != null) res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public void getfriends(String id, String[] toge_names) {
	    PreparedStatement pstmt = null;
	    ResultSet res = null;

	    try {
	        // 첫 번째 쿼리 실행
	        String sql = "SELECT follow_id FROM follow WHERE following_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        res = pstmt.executeQuery();

	        int count = 0;
	        String[] friendIds = new String[2];
	        while (res.next() && count < 2) {
	            friendIds[count] = res.getString(1);
	            count++;
	        }

	        // 두 번째 및 세 번째 쿼리 실행
	        for (int i = 0; i < friendIds.length; i++) {
	            String friendId = friendIds[i];
	            String sql1 = "SELECT COUNT(follow_id) AS overlapping_count " +
	                          "FROM follow " +
	                          "WHERE follow_id = ? AND following_id IN (SELECT following_id FROM follow WHERE follow_id = ?)";
	            pstmt = con.prepareStatement(sql1);
	            pstmt.setString(1, id);
	            pstmt.setString(2, friendId);
	            res = pstmt.executeQuery();
	            int overlappingCount = 0;
	            if (res.next()) {
	                overlappingCount = res.getInt("overlapping_count");
	            }
	            toge_names[i] = String.valueOf(overlappingCount);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (res != null) res.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void getpostContent(String content) {
		Statement stmt = null;
	    
	    try {
	    	stmt = con.createStatement();
	        String sql = "SELECT post_id, content FROM post WHERE content = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, content); 
	        ResultSet resultSet = pstmt.executeQuery();
	            
	        while (resultSet.next()) {
	        	String post_Id = resultSet.getString("post_id");
	            String post_Content = resultSet.getString("content");
	            System.out.println("Post ID: " + post_Id + ", Content: " + post_Content);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	try {
	    		if (stmt != null) stmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	}


	public void getcommentContent(String content) {
		Statement stmt = null;
		
	    try {
	    	stmt = con.createStatement();
	        String sql = "SELECT comment_id, content FROM comment WHERE content = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, content); 
	        ResultSet resultSet = pstmt.executeQuery();

	            while (resultSet.next()) {
	                String comment_Id = resultSet.getString("comment_id");
	                String comment_Content = resultSet.getString("content");
	                String comment_dateTime = resultSet.getString("created_at");
	                System.out.println("Comment ID: " + comment_Id + ", Content: " + comment_Content + ", DateTime: " + comment_dateTime);
	            }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if(stmt != null) stmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	//InformationForm_friends에서 친구의 id_name을 가져오는 것이다. 
	public String getfriendsId(String user_id) {
	    Statement stmt = null;
	    ResultSet res = null;
	    String result = null;

	    try {
	        String sql = "SELECT NAME FROM CUSTOMER WHERE USER_ID = " + user_id;
	        stmt = con.createStatement();
	        res = stmt.executeQuery(sql);

	        if (res.next()) {
	            result = res.getString(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (res != null) res.close(); // ResultSet도 닫아주어야 합니다.
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}


	public boolean isIdOverlap(int id) {
        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.createStatement();
            // 수정: SQL 쿼리에서 따옴표 제거하고 파라미터 바인딩 사용
            String sql = "SELECT user_id FROM customer WHERE user_id = ?";
            // 수정: PreparedStatement를 사용하여 파라미터 바인딩
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();
            return res.next(); // true if the result set is not empty, meaning ID already exists
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (res != null) res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	public boolean pw_check(String id, String pw) {
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT password FROM customer WHERE user_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String storedPw = rs.getString("password");
	            return storedPw.equals(pw);
	        } else {
	            // No such user found
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (rs != null) rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	public void withdraw(String id) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String sql = "DELETE FROM customer WHERE user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	public boolean contains(String id) {
	    Statement stmt = null;
	    ResultSet res = null;
	    try {
	        stmt = con.createStatement();
	        String sql = "SELECT user_id FROM customer WHERE user_id = ?";
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        res = pstmt.executeQuery();
	        return res.next(); // true if the result set is not empty, meaning ID exists
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (res != null) res.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
    // MYSQL 연결
    public static Connection getConnection() {
        try {
            driver = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://localhost/db_final";
            user = "root";
            pass = "12345";
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("The Connection Successful");
            return con;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}