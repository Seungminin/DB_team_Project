import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InformationForm extends JDialog {
    private LoginForm owner;
    private UserDataSet users;

    private JoinForm joinForm;
    
    //Logout, 회원탈퇴를 하는 것
    private JButton btnLogout;
    private JButton btnWithdraw;
    
    //올라온 사진을 보고 댓글을 다는 기능
    private JButton comment_btn;
    private JTextField comment_tf;
    private JTextArea taCheck;
    
    //이미지를 이동하는 것
    private JLabel lbimage;
    private JButton btnPrev;
    private JButton btnNext;
    
    private List<Post> posts;
    
    private int index; // 현재 인덱스 저장용
    private static final String[] IMAGES = {
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple2.jpg",
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple3.jpg",
    	    "C:/Users/ksmin/OneDrive/바탕 화면/사과이미지/apple4.jpg"
    };
    
    private static class Post {
        private String imagePath;
        private List<String> comments;

        public Post(String imagePath) {
            this.imagePath = imagePath;
            this.comments = new ArrayList<>();
        }

        public String getImagePath() {
            return imagePath;
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
        users = owner.getUsers();
        
        posts = new ArrayList<>();
        for (String imagePath : IMAGES) {
            posts.add(new Post(imagePath));
        }

        init();
        addListeners();
        showFrame();
    }

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

        JLabel l1 = new JLabel("My Article");
        p1_up.add(l1);
        cp.add(p1_up, BorderLayout.NORTH);

        JPanel p2_main = new JPanel();
        p2_main.setLayout(null); // null layout으로 설정 -> 개발자가 직접 크기 설정
        p2_main.setBackground(c2);
        
        //p3_search-> ()search.
        JPanel p3_search = new JPanel();
        p3_search.setPreferredSize(new Dimension(600, 50));
        p3_search.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField t3_s = new JTextField(20);
        JButton b3_s = new JButton("Search");
        p3_search.add(t3_s);
        p3_search.add(b3_s);
        
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
         
        textAreaPanel.setBorder(border);  // Set the TitledBorder for the panel
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
      //  setVisible(true); // Added to make the dialog visible
    }
}
