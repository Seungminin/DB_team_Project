import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/*
 * //Table 생성
    public static void createTable(){
        try{
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
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            System.out.println("Table successfully created");
        }
    } 이런 느낌으로 테이블을 만든다.
 * */
public class InformationForm extends JDialog {
	private LoginForm owner;
	private String user_id; // 사용자 아이디를 저장할 변수
    private UserDataSet users;

    private JoinForm joinForm;
    
    private Container cp;
    private JPanel p2_main;
    //Logout, 회원탈퇴를 하는 것
    private JButton btnLogout;
    private JButton btnWithdraw;
    
    //Search Button
    private JButton b3_s;
    private JTextField search;
    private JButton Back;
    private JPanel currPanel;
    private JPanel SearchPanel;
    //Follower, Following을 보는 버튼
    private JButton Follower;
    private JButton Following;
    
    //올라온 사진을 보고 댓글을 다는 기능
    private JButton comment_btn;
    private JTextField comment_tf;
    private JTextArea taCheck;
    
    //이미지를 이동하는 것, 본인의 게시글을 작성하는 것.
    private JLabel lbimage;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel Post_lb;
    private JTextField Post_tf;
    private JButton Post_bt;
    private String originalPostText;
    
    private List<Post> posts;
    
    private int index; // 현재 인덱스 저장용
    private static final String[] IMAGES = {
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple2.jpg",
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple3.jpg",
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple4.jpg"
    };
    
    //Post Class는 image마다 comment를 저장해주고, image에 Comment기능을 넣어주는 Class 
    private static class Post {
        private String imagePath;
        private String postText;
        private String originalPostText; // New field for original post text
        private List<String> comments;

        public Post(String imagePath, String postText) {
            this.imagePath = imagePath;
            this.postText = postText;
            this.originalPostText = postText; // Initialize original post text
            this.comments = new ArrayList<>();
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getPostText() {
            return postText;
        }

        public String getOriginalPostText() {
            return originalPostText;
        }

        public List<String> getComments() {
            return comments;
        }

        public void addComment(String comment) {
            comments.add(comment);
        }
    }
    
    public InformationForm(LoginForm owner) {
    	super(owner, "information", true);
        this.owner = owner;
        this.user_id = owner.getTfId(); // owner가 정상적으로 초기화된 후에 호출
        
        posts = new ArrayList<>();
        posts.add(new Post(IMAGES[0], "Post 1 Text"));
        posts.add(new Post(IMAGES[1], "Post 2 Text"));
        posts.add(new Post(IMAGES[2], "Post 3 Text"));

        init();
        addListeners();
        showFrame();
    }
    
    private void init() {
    	  
    	//
    	//Color지정
        Color c1 = new Color(170, 185, 180); // RGB
        Color c2 = new Color(210, 200, 200);
        //Button Size 지정.
        Dimension btnsize = new Dimension(100, 25);
        
        JFrame frame = new JFrame();
        this.setPreferredSize(new Dimension(600, 800));
        //frame을 정중앙에 맞춘다. 
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frm = super.getSize();
        int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
        super.setLocation(xpos, ypos);

        cp = this.getContentPane(); // Use "this" instead of creating a new JFrame

        //Search 버튼을 눌렀을 때 바뀌는 Panel 초기화, Search Panel 만들기
        SearchPanel = new JPanel();
        SearchPanel.setBounds(0, 0, 787, 700);
        SearchPanel.setVisible(false);
        SearchPanel.setLayout(null);
        SearchPanel.setBackground(c2);
        
        /* JTable table = new JTable(data, headers);
         table.setRowHeight(30);
         table.setFont(new Font("Sanserif",  Font.BOLD, 15));
         table.setAlignmentX(0);
         table.setSize(600,400);
         table.setPreferredScrollableViewportSize(new Dimension(600,400));
         JScrollPane scrollPane = new JScrollPane(table);
         scrollPane.setBounds(219, 24, 556, 305);
         SearchPanel.add(scrollPane);
         cp.add(SearchPanel);*/
        Back = new JButton("Back");
        Back.setBounds(10, 10, 100, 30);  // 적절한 위치와 크기로 조절
        SearchPanel.add(Back);
        
        search = new JTextField();
        search.setHorizontalAlignment(SwingConstants.CENTER);
        search.setText("\uAC80\uC0C9");
        search.setFont(new Font("나눔고딕", Font.BOLD, 25));
        search.setBounds(193, 650, 300, 30);
        SearchPanel.add(search);
        search.setColumns(10);
        
        cp.add(SearchPanel);
        
        JPanel p1_up = new JPanel();
        p1_up.setPreferredSize(new Dimension(250, 30));
        p1_up.setBackground(c1);

        JLabel l1 = new JLabel("My Article");
        p1_up.add(l1);
        cp.add(p1_up, BorderLayout.NORTH);

        p2_main = new JPanel();
        p2_main.setLayout(null); // null layout으로 설정 -> 개발자가 직접 크기 설정
        p2_main.setBackground(c2);
        
        //p3_search-> ()search.
        JPanel p3_search = new JPanel();
        p3_search.setPreferredSize(new Dimension(600, 50));
        p3_search.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JTextField t3_s = new JTextField(20);
        b3_s = new JButton("Search");
        
        Follower = new JButton("Follower");
        Following = new JButton("Following");
        
        p3_search.add(t3_s);
        p3_search.add(b3_s);
        p3_search.add(Follower);
        p3_search.add(Following);
        
        p3_search.setBounds(0, 0, 600, 50); //가로위치, 세로위치, 가로길이, 세로길이
        p2_main.add(p3_search,BorderLayout.NORTH);
        
        JPanel p4_article = new JPanel();
        p4_article.setPreferredSize(new Dimension(600, 400));
        p4_article.setLayout(new BorderLayout()); // 변경: BorderLayout으로 설정
        
        //이미지가 들어가는 Panel.
        JPanel PostPanel = new JPanel();
        PostPanel.setPreferredSize(new Dimension(500,300));
        PostPanel.setLayout(null);
        
        lbimage = new JLabel();
        lbimage.setBounds(128, 0, 256, 256);
        lbimage.setIcon(new ImageIcon(IMAGES[index])); // index 처음 0, IMAGES[0] 랑 결과 같음
        
        PostPanel.setBounds(0, 50, 600, 400);
        PostPanel.add(lbimage);
        
        JPanel PostPanel_type = new JPanel();
        PostPanel_type.setPreferredSize(new Dimension(100,50));
        PostPanel_type.setLayout(new FlowLayout(FlowLayout.LEFT));
        Post_lb = new JLabel();
        Post_tf = new JTextField(20);
        Post_bt = new JButton("Save");
        
        PostPanel_type.add(Post_tf);
        PostPanel_type.add(Post_bt);
        PostPanel_type.add(Post_lb);
        
        PostPanel_type.setBounds(0, 300, 600, 50);  // PostPanel의 SOUTH로 이동
        PostPanel.add(PostPanel_type, BorderLayout.SOUTH);
        //버튼
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPrev = new JButton("Prev");
        btnNext = new JButton("next");
        
        btnPrev.setPreferredSize(new Dimension(100,25));
        btnNext.setPreferredSize(new Dimension(100,25));
        
        buttonPanel.setBounds(0,0,600,200);
        buttonPanel.add(btnPrev);
        buttonPanel.add(btnNext);
        //Comment구간
        //Post구간 많이 크게 만들어야 하고 아래쪽에 댓글을 달 수 있게 Comment를 할 수 있게 하는 거 만들어야한다.
        JPanel textAreaPanel = new JPanel();
      //  textAreaPanel.setPreferredSize(new Dimension(200,400));
        comment_tf = new JTextField(20);
        comment_btn = new JButton("save");
        taCheck = new JTextArea(10, 30);
        taCheck.setEditable(true);
        
        LineBorder lBorder = new LineBorder(Color.GRAY, 1);
        TitledBorder border = new TitledBorder(lBorder, "Comment");
        textAreaPanel.setBorder(border);
        
        textAreaPanel.add(comment_tf);
        textAreaPanel.add(comment_btn);
        textAreaPanel.add(taCheck);       // Add the JTextArea to the panel

        textAreaPanel.setBounds(0, 450, 600, 200);
        p4_article.add(PostPanel, BorderLayout.CENTER); 	  //Add PostPanel
        p4_article.add(buttonPanel,BorderLayout.CENTER);
        p4_article.add(textAreaPanel, BorderLayout.SOUTH);    // Add text Panel
        p4_article.setBounds(0, 50, 600, 650);
        
        p2_main.add(p4_article,BorderLayout.CENTER);
        cp.add(p2_main, BorderLayout.CENTER);

        JPanel p5_under = new JPanel();
        p5_under.setPreferredSize(new Dimension(50, 50));
        p5_under.setBackground(c1);

        btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(btnsize);
        btnWithdraw = new JButton("withdraw");
        btnWithdraw.setPreferredSize(btnsize);

        p5_under.add(btnLogout,BorderLayout.CENTER);
        p5_under.add(btnWithdraw,BorderLayout.CENTER);

        cp.add(p5_under, BorderLayout.SOUTH);
        
        frame.pack();
		frame.setVisible(true);
    }

    private void addListeners() {
    	Customer customer = new Customer();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                customer.withdraw(owner.getTfId());
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
        
        b3_s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2_main.setVisible(!p2_main.isVisible());
                SearchPanel.setVisible(!SearchPanel.isVisible());
            }
        });

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2_main.setVisible(!p2_main.isVisible());
                SearchPanel.setVisible(!SearchPanel.isVisible());
            }
        });
        
        search.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
               /* String val = search.getText();
                TableRowSorter<TableModel> trs = new TableRowSorter<>(table.getModel());
                table.setRowSorter(trs);
                trs.setRowFilter(RowFilter.regexFilter(val));*/
            	//여기서 데이터베이스 정보를 받아와 table 형식으로 받고 그거를 다시 출력
            }
        });

        
        Follower.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FollowForm follow = new FollowForm(InformationForm.this);
				follow.setVisible(true);
			}
		});
        
        Following.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FollowForm follow = new FollowForm(InformationForm.this);
				follow.setVisible(true);
			}
		});
        
        Post_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Post_tf에 입력된 값을 가져와서 Post_lb에 표시
                String userInput = Post_tf.getText();
                Post_lb.setText(userInput);
                
                Post currentPost = posts.get(index);
                currentPost.originalPostText = userInput;
            }
        });
        
        comment_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = comment_tf.getText();
                //User_Id를 들고온다.
                String dateTime = getFormattedDateTime();
                String commentWithDateTime = comment + "   " + dateTime + "\n";
                addComment(commentWithDateTime);
            }
        });
        
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index > 0) {
                    index--;
                } else {
                    index = posts.size() - 1;
                }
                updatePost();
            }
        });
       
 
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < posts.size() - 1) {
                    index++;
                } else {
                    index = 0;
                }
                updatePost();
            }
        });
    }
    
 // InformationForm 클래스의 updatePost 메서드 수정
    private void updatePost() {
        Post currentPost = posts.get(index);
        lbimage.setIcon(new ImageIcon(currentPost.getImagePath()));

        // Set Post_tf ""
        Post_tf.setText("");

        // Set originalPostText to the current post's original text
        originalPostText = currentPost.getOriginalPostText();

        // Set Post_lb to the original post text
        Post_lb.setText(originalPostText);

        // Update comments
        updateComments(currentPost.getComments());
    }
    private void updateComments(List<String> comments) {
        taCheck.setText(""); // 기존 텍스트 지우기, 기존에 있던 댓글을 유지하기 위해서는 DB에 저장을 하고 불러오는 방법, 혹은 taCheck를 2개이상 만드는 것이다.
        for (String comment : comments) {
            taCheck.append(comment);
        }
    }
    
 // InformationForm 클래스의 addComment 메서드 수정
    private void addComment(String comment) {
        Post currentPost = posts.get(index);
        currentPost.addComment(comment);
        updateComments(currentPost.getComments());

        // Comment added, update Post_lb using the current post's original text
        // Post_lb.setText(currentPost.getOriginalPostText());
    }
    
 // 현재 날짜와 시간을 포맷팅하여 반환하는 메서드
    private String getFormattedDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }
    
 // Post에 내용 추가하는 메서드
    private void addPost(String comment) {
        Post currentPost = posts.get(index);
        currentPost.addComment(comment);
        updateComments(currentPost.getComments());
    }
    
    public String getId() {
    	return user_id; 
    }
	 public String getUserId() {
        return this.user_id;
    }
    
    public void setTaCheck(String userInfo) {
        taCheck.setText(userInfo);
         /*
          * 채팅 기록을 남기는 부분이다.
          * */
         
         SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
         Date now = new Date();
         String timeFormat = dateFormat.format(now);

         String currentText = taCheck.getText();
         taCheck.setText(currentText + "[" + timeFormat + "] " + userInfo + "\n");
         
     }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
      //  setVisible(true); // Added to make the dialog visible
    }

}
