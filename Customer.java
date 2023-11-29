import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Customer {
    public static void main(String[] args) {
        createUserTable();
        createFollowTable(); // follow 테이블 생성 추가
        createPostTable(); // post 테이블 생성 추가
        createCommentTable(); // comment 테이블 생성 추가
    }

    // 사용을 위해서 해당 프로젝트에 mysqldbconnector 생성 필요
    // SQL 문법에 맞추어 입력되는 값들을 DB로 전송한다

    // Table 생성
    public static void createUserTable() {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS "
                            + "customer(id int NOT NULL AUTO_INCREMENT,"
                            + "name varChar(255),"
                            + "author varChar(255),"
                            + "booknumber varChar(255),"
                            + "publisher varChar(255),"
                            + "category varChar(255),"
                            + "introduction varChar(255),"
                            + "PRIMARY KEY(id))");
            create.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Table successfully created");
        }
    }

    // DB 만든 부분
    public static void createFollowTable() {
        try (Connection con = getConnection();
             PreparedStatement create = con.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS `modelDB`.`follow` ("
                             + "`follow_id` VARCHAR(45) NOT NULL,"
                             + "`follower_id` VARCHAR(45) NOT NULL,"
                             + "`following_id` VARCHAR(45) NOT NULL,"
                             + "PRIMARY KEY (`follow_id`),"
                             + "INDEX `fk_follow_user1_idx` (`follower_id` ASC) VISIBLE,"
                             + "INDEX `fk_follow_user2_idx` (`following_id` ASC) VISIBLE,"
                             + "CONSTRAINT `fk_follow_user1`"
                             + "  FOREIGN KEY (`follower_id`)"
                             + "  REFERENCES `modelDB`.`user` (`user_id`)"
                             + "  ON DELETE NO ACTION"
                             + "  ON UPDATE NO ACTION,"
                             + "CONSTRAINT `fk_follow_user2`"
                             + "  FOREIGN KEY (`following_id`)"
                             + "  REFERENCES `modelDB`.`user` (`user_id`)"
                             + "  ON DELETE NO ACTION"
                             + "  ON UPDATE NO ACTION)"
                             + "ENGINE = InnoDB;")) {
            create.execute();
            System.out.println("Follow table successfully created");
        } catch (Exception e) {
            System.out.println("Error creating follow table: " + e.getMessage());
        }
    }

    public static void createPostTable() {
        try (Connection con = getConnection();
             PreparedStatement create = con.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS `modelDB`.`post` ("
                             + "`post_id` VARCHAR(45) NOT NULL,"
                             + "`content` TEXT NULL,"
                             + "`location` VARCHAR(30) NULL,"
                             + "`user_id` VARCHAR(45) NOT NULL,"
                             + "PRIMARY KEY (`post_id`),"
                             + "INDEX `fk_post_user1_idx` (`user_id` ASC) VISIBLE,"
                             + "CONSTRAINT `fk_post_user1`"
                             + "  FOREIGN KEY (`user_id`)"
                             + "  REFERENCES `modelDB`.`user` (`user_id`)"
                             + "  ON DELETE NO ACTION"
                             + "  ON UPDATE NO ACTION)"
                             + "ENGINE = InnoDB;")) {
            create.execute();
            System.out.println("Post table successfully created");
        } catch (Exception e) {
            System.out.println("Error creating post table: " + e.getMessage());
        }
    }

    public static void createCommentTable() {
        try (Connection con = getConnection();
             PreparedStatement create = con.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS `modelDB`.`comment` ("
                             + "`comment_id` VARCHAR(45) NOT NULL,"
                             + "`content` VARCHAR(200) NULL,"
                             + "`parent_id` VARCHAR(45) NULL,"
                             + "`user_id` VARCHAR(45) NOT NULL,"
                             + "`post_id` VARCHAR(45) NOT NULL,"
                             + "PRIMARY KEY (`comment_id`),"
                             + "INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,"
                             + "INDEX `fk_comment_post1_idx` (`post_id` ASC) VISIBLE,"
                             + "CONSTRAINT `fk_comment_user1`"
                             + "  FOREIGN KEY (`user_id`)"
                             + "  REFERENCES `modelDB`.`user` (`user_id`)"
                             + "  ON DELETE NO ACTION"
                             + "  ON UPDATE NO ACTION,"
                             + "CONSTRAINT `fk_comment_post1`"
                             + "  FOREIGN KEY (`post_id`)"
                             + "  REFERENCES `modelDB`.`post` (`post_id`)"
                             + "  ON DELETE NO ACTION"
                             + "  ON UPDATE NO ACTION)"
                             + "ENGINE = InnoDB;")) {
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
