package dbTwitter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

//import InformationForm.Post;

public class InformationForm_Follow extends JDialog {
	private FollowFormCommon owner;

    private JoinForm joinForm;

    //Follower, Following을 보는 버튼
    private JButton Follower;
    private JButton Following;
    
    //올라온 사진을 보고 댓글을 다는 기능
    private JButton comment_btn;
    private JTextField comment_tf;
    private JTextArea taCheck;
    
    //이미지를 이동하는 것
    private JLabel lbimage;
    private JButton btnPrev;
    private JButton btnNext;
    private JLabel Post_lb;
    
    
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
    
    //if FollowForm에서 정보가 왔다면 이거를 사용하고 
    public InformationForm_Follow(FollowFormCommon owner) {
    	  super(owner instanceof FollowForm ? (FollowForm) owner : null, "Article", true);
          this.owner = owner;
        
          posts = new ArrayList<>();
          posts.add(new Post(IMAGES[0], "Post 1 Text"));
          posts.add(new Post(IMAGES[1], "Post 2 Text"));
          posts.add(new Post(IMAGES[2], "Post 3 Text"));

        init();
        addListeners();
        showFrame();
    }
    //else FollowForm이 아니라 다른 데에서 상속을 요구하면 여기를 사용한다.
    //else{}
    private void init() {
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

        Container cp = this.getContentPane(); // Use "this" instead of creating a new JFrame

        JPanel p1_up = new JPanel();
        p1_up.setPreferredSize(new Dimension(250, 30));
        p1_up.setBackground(c1);

        JLabel l1 = new JLabel("Follow Article");
        p1_up.add(l1);
        cp.add(p1_up, BorderLayout.NORTH);

        JPanel p2_main = new JPanel();
        p2_main.setLayout(null); // null layout으로 설정 -> 개발자가 직접 크기 설정
        p2_main.setBackground(c2);
        
        //p3_search-> ()search.
        JPanel p3_search = new JPanel();
        p3_search.setPreferredSize(new Dimension(600, 50));
        p3_search.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        Follower = new JButton("Follower");
        Following = new JButton("Following");
        
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

        frame.pack();
		frame.setVisible(true);
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
        
        Follower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FollowForm_friends follow_f = new FollowForm_friends(InformationForm_Follow.this);
                follow_f.setVisible(true);
            }
        });

        Following.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	FollowForm_friends follow_f = new FollowForm_friends(InformationForm_Follow.this);
                follow_f.setVisible(true);
            }
        });
        
        comment_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = comment_tf.getText();
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
    
    private void updatePost() {
        Post currentPost = posts.get(index);
        lbimage.setIcon(new ImageIcon(currentPost.getImagePath()));
        updateComments(currentPost.getComments());

        // Update Post_lb with post text and comments
        String postText = currentPost.getOriginalPostText();
        StringBuilder postInfo = new StringBuilder(postText + "\nComments:\n");

        for (String comment : currentPost.getComments()) {
            postInfo.append(comment).append("\n");
        }

        Post_lb.setText(postInfo.toString());
    }
    
    private void updateComments(List<String> comments) {
        taCheck.setText(""); // 기존 텍스트 지우기, 기존에 있던 댓글을 유지하기 위해서는 DB에 저장을 하고 불러오는 방법, 혹은 taCheck를 2개이상 만드는 것이다.
        for (String comment : comments) {
            taCheck.append(comment);
        }
    }
    
    private void addComment(String comment) {
        Post currentPost = posts.get(index);
        currentPost.addComment(comment);
        updateComments(currentPost.getComments());
    }
    
 // 현재 날짜와 시간을 포맷팅하여 반환하는 메서드
    private String getFormattedDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }
    
    public void setTaCheck(String userInfo) {
       // taCheck.setText(userInfo);
        /*
         * 채팅 기록을 남기는 부분이다.
         * */
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo((Component) owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
      //  setVisible(true); // Added to make the dialog visible
    }
}