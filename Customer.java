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

    // 사용을 위해서 해당 프로젝트에 mysqldbconnector 생성 필요
    // SQL 문법에 맞추어 입력되는 값들을 DB로 전송한다

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
	                     "`follower_id` int NOT NULL," +
	                     "`following_id` int NOT NULL," +
	                     "PRIMARY KEY (`follow_id`)," +
	                     "INDEX idx_follower_id (`follower_id`)," +
	                     "INDEX idx_following_id (`following_id`)," +
	                     "FOREIGN KEY (`follower_id`) REFERENCES `customer` (`user_id`)" +
	                     "  ON DELETE NO ACTION ON UPDATE NO ACTION," +
	                     "FOREIGN KEY (`following_id`) REFERENCES `customer` (`user_id`)" +
	                     "  ON DELETE NO ACTION ON UPDATE NO ACTION);")) {

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
								"`location` VARCHAR(30) NULL," +
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
