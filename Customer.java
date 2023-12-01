package dbTwitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
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

    // MYSQL 연결
    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost/db_final";
            String user = "root";
            String pass = "12345";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("The Connection Successful");
            return con;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
