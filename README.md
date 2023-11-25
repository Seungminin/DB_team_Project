# DB_team_Project
# DB_텀프로젝트

### 구상

Twitter라는 SNS 앱. 

필요한 requirements

(Followers-Following), Message among clients, 게시물 올리기 + 댓글?

Java Swing을 이용하여 로그인 (ID,Password), 없으면 회원가입 → DB에 저장.

하나의 Server Computers, 여러 Client접속

만약 Server DB에 ID,Password 입력해서 접속하는 Client? Server에 있는 DB중에서 그 Client에게 허용된 정보만 view를 만들어줘서 거기에만 접속할 수 있게 해준다. 없으면 view를 만들어 준다.

### Client 구별은 어떻게 할 것인가?

→ ID, Password를 통해 구별을 해준다.

일단 Socket Programming을 통해 서버와 client 연결 Thread이용 → 다중 접속 지원 가능.

ID, Password 입력 후 Server는 DB에서 맞는 tuple이 있으면 그 사용자에게 view를 제공

view(본인의 팔로잉, 팔로워, 다른 사용자들에 대한 팔로워, 팔로잉) 

ex)N명의 Client가 있으면 N개의 view가 존재, 가장 큰 틀인 DB 존재. 

[https://velog.io/@dongvelop/간단한-로그인-프로그램-구현하기](https://velog.io/@dongvelop/%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0) //Java 로그인

https://mangkyu.tistory.com/110 //정규화

---

## Normalization

- 1NF : attribute가 두 개 이상이면 안된다. → 새로운 것을 만든다.
- 2NF : 기본키의 부분집합이 결정자가 되어서는 안된다. → 분해, 완전함수종속
- 3NF : 2NF에서 만들어진 테이블에서 이행적 종속 관계를 없애는 것 → 분해 (DB를 업데이트 할 때 하나의 테이블 만 고치면 된다.)
- BCNF(3.5) : 모든 결정자가 후보키가 되어야 한다.

---

## ER diagram

![스크린샷 2023-11-21 192114.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/4b88b321-8197-44e7-98c5-3a97d8110204/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2023-11-21_192114.png)

1.User는 여러가지 attribute를 갖는다. User는 다른 User를 Following할 수 있다.  Entity : User,Following Relation : Follow (many-many관계이기 때문에 Follow는 2개의 primary_key를 가져온다. Follow(User_id,  Following_id, Follower_id, Follow_id ) Primary key를 가진다.

(Follow relation이나 Following relation에 있는 id들은 전부 User의 primary key를 referencing한 것이다. 

## 요구하는 기본 기능

User : DB에 있는 id,password가 있을 때 로그인 없으면 새로운 아이디를 만들어주기.
password 변환

Follow : 다른 사용자에게 승인 없이 Follow, 다른 사용자에 대한 모든 Follow, Following보기.

Article : 본인 유저 보드에 짧은 메시지 작성 가능
Following 하는 User board에 짧은 메시지 작성
최근 순으로 메시지들을 볼 수 있다.
유저는 모든 사람의 보드를 볼 수 있다.

## 추가적인 기능

- Following한 사람을 랜덤으로 추리고 Following한 사람의 Following 친구를 랜덤으로 추천
- 함께 아는 친구 (=나의 Following들은 내가 Follwing한 사람의 Following들)
- 검색할 때 친구의 아이디를 Index칸 별로 나타내기.

## 앱 도식화

![스크린샷 2023-11-23 230359.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/4f17d213-8c37-4f07-ba6f-08f3e3deca4e/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2023-11-23_230359.png)

- Java GUI 만들기
    - 모든 Client - Server 기능은 GUI를 통해서 data를 보내고, Check한다.
- Client - Server접속하여 data를 서버에서 받아오고 App을 실행시키기
    - Socket 연결. (다중 연결 지원 X)
    - DB_Server로 Jdbc이용해서 Buffer형태로 내용을 전달, Server는 jdbc형태로 온 mysql문장을 보고 필요한 data들을 client에게 판단 후 전달
        - 로그인을 할 때 Client는 DB_Server에게 본인의 ID,Password를 전달 ID,Password가 있을 경우 로그인 승인, Server에서 만약 없는 Data라고 하면 회원 가입 요청.
        - 만약 Client 즉 User가 Password 변환을 요청하면 Server는 응답을 하여 어떤 ID이니 라고 질문하기, Client는 ID, Password_change를 보낸 후 Server는 변환된 Password를 Update.
        - Following을 클릭하면
- DB_Server에서는 DataBase 만들기 Final Logical Schema를 이용하여

## GUI만들기(LoginForm)

LoginForm에서 프로그램 실행을 시킨다.

```java
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginForm extends JFrame {
	
	private UserDataSet users;

    private JLabel lblId;
    private JLabel lblPw;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    

    public LoginForm() {
    
    	users = new UserDataSet();

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    public void init() {
        // 사이즈 통합
        Dimension lblSize = new Dimension(80, 30);
        int tfSize = 10;
        Dimension btnSize = new Dimension(100, 25);

        lblId = new JLabel("ID");
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password");
        lblPw.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);

        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);

    }
    public UserDataSet getUsers() {
    	return users;
    }
    
    public String getTfId() {
		return tfId.getText();
	}

    public void setDisplay() {
        // FlowLayout 왼쪽 정렬
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        // pnlNorth(pnlId, pnlPw)
        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        pnlNorth.add(pnlId);
        pnlNorth.add(pnlPw);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnLogin);
        pnlSouth.add(btnJoin);

        pnlNorth.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(pnlNorth, BorderLayout.NORTH);
        add(pnlSouth, BorderLayout.SOUTH);

    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디칸이 비었을 경우
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "아이디를 입력하세요.",
                            "로그인폼",
                            JOptionPane.WARNING_MESSAGE);

                    // 존재하는 아이디일 경우
                } else if (users.contains(new User(tfId.getText()))) {

                    // 비밀번호칸이 비었을 경우
                    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                                LoginForm.this,
                                "비밀번호를 입력하세요.",
                                "로그인폼",
                                JOptionPane.WARNING_MESSAGE);

                        // 비밀번호가 일치하지 않을 경우
                    } else if (!users.getUser(tfId.getText()).getPw().equals(String.valueOf(tfPw.getPassword()))) {
                        JOptionPane.showMessageDialog(
                                LoginForm.this,
                                "비밀번호가 일치하지 않습니다.");

                        // 다 완료될 경우
                    } else {
                        InformationForm infoForm = new InformationForm(LoginForm.this);
                        infoForm.setTaCheck(users.getUser(tfId.getText()).toString());
                        setVisible(false);
                        infoForm.setVisible(true);
                        tfId.setText("");
                        tfPw.setText("");
                    }
                    // 존재하지 않는 Id일 경우
                } else {
                    JOptionPane.showMessageDialog(
                            LoginForm.this,
                            "존재하지 않는 Id입니다."
                         
                    );
                
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                        LoginForm.this,
                        "로그인 프로그램을 종료합니다.",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void showFrame() {
        setTitle("Twitter");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
```

JoinForm : 회원 가입을 하는 GUI

```java
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class JoinForm extends JDialog {
    private LoginForm owner;
    private UserDataSet users;

    
    private JLabel lblTitle;
    private JLabel lblId;
    private JLabel lblPw;
    private JLabel lblphone;
    private JLabel lblName;
    private JLabel lblEmail;
    private JLabel lblbirth;
    private JLabel lblwebsite;
    private JLabel lblintroduce;
    private JRadioButton rbtnMale;
    private JRadioButton rbtnFemale;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JTextField tfName;
    private JTextField tfphone;
    private JTextField tfEmail;
    private JTextField tfbirth;
    private JTextField tfwebsite;
    private JTextField tfintroduce;
    private JButton btnJoin;
    private JButton btnCancel;

    public JoinForm(LoginForm owner) {
        super(owner, "Join the Twitter", true);
        this.owner = owner;
        users = owner.getUsers(); //가지고 오는 코드
        
        init();
        setDisplay();
        addListeners();
        showFrame();
    }
    private void init() {
        // 크기 고정
        int tfSize = 10;
        Dimension lblSize = new Dimension(80, 35);
        Dimension btnSize = new Dimension(100 ,25);

        lblTitle = new JLabel("- Input your information");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        lblId = new JLabel("ID", JLabel.LEFT);
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password", JLabel.LEFT);
        lblPw.setPreferredSize(lblSize);
        
        lblName = new JLabel("Name", JLabel.LEFT);
        lblName.setPreferredSize(lblSize);
        lblphone = new JLabel("Phone", JLabel.LEFT);
        lblphone.setPreferredSize(lblSize);
        
        lblEmail = new JLabel("Email", JLabel.LEFT);
        lblEmail.setPreferredSize(lblSize);
        
        lblbirth = new JLabel("Birth", JLabel.LEFT);
        lblbirth.setPreferredSize(lblSize);
        
        lblwebsite = new JLabel("Website", JLabel.LEFT);
        lblwebsite.setPreferredSize(lblSize);
        
        lblintroduce = new JLabel("Introduce", JLabel.LEFT);
        lblintroduce.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);
        tfName = new JTextField(tfSize);
        tfphone = new JPasswordField(tfSize);
        tfEmail = new JTextField(tfSize);
        tfbirth = new JTextField(tfSize);
        tfwebsite = new JTextField(tfSize);
        tfintroduce = new JTextField(tfSize);
        
        //Gender button Group 만들기.
        rbtnMale = new JRadioButton("Male", true);
        rbtnFemale = new JRadioButton("Female");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnMale);
        group.add(rbtnFemale);

        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(btnSize);

    }
    private void setDisplay() {
        // FlowLayout 왼쪽 정렬
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        // pnlMain(pnlMNorth / pnlMCenter / pnlMSouth)
        JPanel pnlMain = new JPanel(new BorderLayout());

        // pnlMNorth(lblTitle)
        JPanel pnlMNorth = new JPanel(flowLeft);
        pnlMNorth.add(lblTitle);

        // pnlMCenter(pnlId / pnlPw / pnlName / pnlphone / pnlEmail / pnlBirth/ pnlwebsite/ pnlintroduce)
        JPanel pnlMCenter = new JPanel(new GridLayout(0, 1));
        JPanel pnlId = new JPanel(flowLeft);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        JPanel pnlName = new JPanel(flowLeft);
        pnlName.add(lblName);
        pnlName.add(tfName);

        JPanel pnlphone = new JPanel(flowLeft);
        pnlphone.add(lblphone);
        pnlphone.add(tfphone);

        JPanel pnlEmail = new JPanel(flowLeft);
        pnlEmail.add(lblEmail);
        pnlEmail.add(tfEmail);
        
        JPanel pnlbirth = new JPanel(flowLeft);
        pnlbirth.add(lblbirth);
        pnlbirth.add(tfbirth);
        
        JPanel pnlwebsite = new JPanel(flowLeft);
        pnlwebsite.add(lblwebsite);
        pnlwebsite.add(tfwebsite);
        
        JPanel pnlintroduce = new JPanel(flowLeft);
        pnlintroduce.add(lblintroduce);
        pnlintroduce.add(tfintroduce);

        pnlMCenter.add(pnlId);
        pnlMCenter.add(pnlPw);
        pnlMCenter.add(pnlName);
        pnlMCenter.add(pnlphone);
        pnlMCenter.add(pnlEmail);
        pnlMCenter.add(pnlbirth);
        pnlMCenter.add(pnlwebsite);
        pnlMCenter.add(pnlintroduce);

        // pnlMSouth(rbtnMale / rbtnFemale)
        JPanel pnlMSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMSouth.add(rbtnMale);
        pnlMSouth.add(rbtnFemale);
        pnlMSouth.setBorder(new TitledBorder("Gender"));

        // pnlMain
        pnlMain.add(pnlMNorth, BorderLayout.NORTH);
        pnlMain.add(pnlMCenter, BorderLayout.CENTER);
        pnlMain.add(pnlMSouth, BorderLayout.SOUTH);

        // pnlSouth(btnJoin / btnCancel)
        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnJoin);
        pnlSouth.add(btnCancel);

        // 화면 테두리의 간격을 주기 위해 설정 (insets 사용 가능)
        pnlMain.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(pnlMain, BorderLayout.NORTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }
    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // 정보 하나라도 비어있으면
                if(isBlank()) {
                    JOptionPane.showMessageDialog(
                            JoinForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때 -> database에서 가져온 id가 이미 중복일 때
                    if(users.isIdOverlap(tfId.getText())) {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "이미 존재하는 Id입니다."
                        );
                        tfId.requestFocus();

                        /* Pw와 Re가 일치하지 않았을 때
                    } else if(!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "Password와 Retry가 일치하지 않습니다."
                        );
                        tfPw.requestFocus();*/
                    } else {
                    	users.addUsers(new User(
                    		    tfId.getText(),
                    		    String.valueOf(tfPw.getPassword()),
                    		    tfName.getText(),
                    		    tfphone.getText(),
                    		    tfEmail.getText(),
                    		    tfbirth.getText(),
                    		    tfwebsite.getText(),
                    		    tfintroduce.getText(),
                    		    getGender()
                    		));
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "회원가입을 완료했습니다!"
                        );
                        dispose();
                        owner.setVisible(true);
                    }
                }
            }
        });
    }
    public boolean isBlank() {
        boolean result = false;
        if(tfId.getText().isEmpty()) {
            tfId.requestFocus();
            return true;
        }
        if(String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if(tfName.getText().isEmpty()) {
        	tfName.requestFocus();
        	return true;
        }
        if(tfphone.getText().isEmpty()) {
        	tfphone.requestFocus();
        	return true;
        }
        if(tfEmail.getText().isEmpty()) {
            tfEmail.requestFocus();
            return true;
        }
        if(tfbirth.getText().isEmpty()) {
            tfbirth.requestFocus();
            return true;
        }
        if(tfwebsite.getText().isEmpty()) {
            tfwebsite.requestFocus();
            return true;
        }
        if(tfintroduce.getText().isEmpty()) {
            tfintroduce.requestFocus();
            return true;
        }
        return result;
    }
    
    public String getGender() {
        if(rbtnMale.isSelected()) {
            return rbtnMale.getText();
        }
        return rbtnFemale.getText();
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
```

User : User의 정보를 가지고 있는 것 User의 Attribute들을 이용하여 정보를 불러오는 것

```java
class User {
	private String id;
    private String pw;
    private String name;
    private String phone; 
    private String Email;  
    private String birth;
    private String website;
    private String introduce;
    private String gender;

    public User(String id, String pw, String name, String phone, String Email, String birth, String website, String introduce, String gender) {
    	setId(id);
        setPw(pw);
        setName(name);
        setphone(phone);  
        setEmail(Email);  
        setbirth(birth);
        setwebite(website);
        setintroduce(introduce);
        setGender(gender);
    }
    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getphone() {
        return phone;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getbirth() {
        return birth;
    }
    public void setbirth(String birth) {
        this.birth = birth;
    }
    public String getwebsite() {
        return website;
    }
    public void setwebsite(String website) {
        this.website = website;
    }
    public String getintroduce() {
        return introduce;
    }
    public void setintroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof User)) {
            return false;
        }
        User temp = (User)o;

        return id.equals(temp.getId());
    }

    @Override
    public String toString() {
        String info = "Id: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "Name: " + name + "\n";
        info += "Phone: " + phone + "\n";
        info += "Email: " + Email + "\n";
        info += "Birth: " + birth + "\n";
        info += "Website: " + website + "\n";
        info += "Introduce: " + introduce + "\n";
        info += "gender: " + gender + "\n";
        return info;
    }
}
```

UserDataSet : ArrayList로 저장을 하고 있다. 우리는 DB_Server에서 불러와야한다.

```java
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
```

Information : 여기에는 지금 User의 정보를 나타내주지만, 우리는 여기에 Article을 Post해줘야 한다.

```java
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InformationForm extends JDialog {
    private LoginForm owner;
    private UserDataSet users;
    
    private JTextArea taCheck;
    private JButton btnLogout;
    private JButton btnWithdraw;
    private JoinForm joinForm;

    public InformationForm(LoginForm owner) {
        super(owner, "Information", true);
        this.owner = owner;
        users = owner.getUsers();

        init();
        setDisplay();	
        addListeners();
        showFrame();

    }
    private void init() {
        Dimension btnsize = new Dimension(100, 25);

        taCheck = new JTextArea(10, 30);
        taCheck.setEditable(false);

        btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(btnsize);

        btnWithdraw = new JButton("withdraw");
        btnWithdraw.setPreferredSize(btnsize);
    }
    private void setDisplay() {

        LineBorder lBorder = new LineBorder(Color.GRAY, 1);
        TitledBorder border = new TitledBorder(lBorder, "check your Information");
        taCheck.setBorder(border);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnLogout);
        pnlSouth.add(btnWithdraw);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(new JScrollPane(taCheck), BorderLayout.NORTH);
        pnlMain.add(pnlSouth, BorderLayout.CENTER);

        add(pnlMain,BorderLayout.CENTER);
    }
    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                users.withdraw(owner.getTfId());
                JOptionPane.showMessageDialog(
                        InformationForm.this,
                        "회원 정보가 삭제되었습니다. 안녕히가세요."
                );
                dispose();
                owner.setVisible(true);
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(
                        InformationForm.this,
                        "로그아웃 되었습니다."
                );
                dispose();
                owner.setVisible(true);
            }
        });
    }
    public void setTaCheck(String userInfo) {
        taCheck.setText(userInfo);
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
}
```

## GUI 초반 단계

![Login.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/15493103-f776-4171-b382-3ab059c343b5/Login.png)

![Join2.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/ea990ef9-0a53-43dc-8921-978f87dda5c9/Join2.png)

![Join.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/062d37a5-4c95-469a-99b5-22b8554ceec0/Join.png)

![아직 미완성_information.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/247af101-2d9d-4262-901b-593fd7a568b5/8985e1cf-5581-477f-bfe3-fe4a139f193a/%EC%95%84%EC%A7%81_%EB%AF%B8%EC%99%84%EC%84%B1_information.png)

- GUI information을 Post, Article 형태로 나타내야 한다.
- Swing에 article과 같이 게시물 형태를 찾아봐야 한다.
- 추가적인 디자인 버튼들 활용, 검색 엔진 만들기.

## DataBase저장

https://jindream6128.tistory.com/25 //DB저장.
