import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//공통된 동작을 가진 인터페이스 정의
interface FollowFormCommon {
 void showInformation();

void showInformation(int friendIndex);
}

//FollowForm에서 그 친구의 id를 information_follow로 넘겨줘야 한다. 
public class FollowForm extends JDialog implements FollowFormCommon {
    private InformationForm owner;
    private Customer customer = new Customer();
  //내가 누구인 지에 대한 user_id 불러오기.
    private String user_id;
    private String[] friends = new String[3];
    private String[] names = new String[3]; 
    private String[] toge_names = new String[3];
    
   
    private JLabel lblTitle;
    private JLabel lb1;
    private JLabel lb2;
    
    private JLabel t_lb1;
    private JLabel t_lb2;
    
    private JButton view1;
    private JButton delete1;
    private JButton view2;
    private JButton delete2;

    
    public FollowForm(InformationForm owner) {
        super(owner, "Follow", true);
        this.owner = owner;
        this.user_id=owner.getId();
        
        init();
        setDisplay();
        addListener();
        showFrame();
    }
    
    @Override
    public void showInformation(int friendIndex) {
        // friends 배열의 인덱스에 해당하는 친구의 id를 가져옴
        String friendId = friends[friendIndex];

        // 공통 동작 구현
        dispose();  // 현재 다이얼로그 닫기
        InformationForm_Follow info_follow = new InformationForm_Follow(FollowForm.this, friendId);
        info_follow.setVisible(true);
    }
    private void init() {
        // 초기화 작업 수행
    	 Dimension lblSize = new Dimension(120, 25);
         Dimension btnSize = new Dimension(70 ,35); //Label, 버튼 크기 고정
         
         lblTitle = new JLabel("- Your Friends-");
         lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
         
         //우리는 Follower, Following을 3명으로 가정을 한다.
         customer.getName(user_id, names);
         customer.getId(user_id, friends); //following 한 친구들의 id를 보낼 수 있다.
         System.out.println("User_ID = "+user_id);
         lb1 = new JLabel(names[0], JLabel.LEFT);
         lb1.setPreferredSize(lblSize);
         lb2 = new JLabel(names[1], JLabel.LEFT);
         lb2.setPreferredSize(lblSize);

         
         // 함께아는 친구
         customer.getfriends(user_id, toge_names);

         t_lb1 = new JLabel(toge_names[0], JLabel.LEFT);
         t_lb1.setPreferredSize(lblSize);
         t_lb2 = new JLabel(toge_names[1], JLabel.LEFT);
         t_lb2.setPreferredSize(lblSize);

         view1 = new JButton("View");
         view1.setPreferredSize(btnSize);
         delete1=new JButton("DELETE");
         delete1.setPreferredSize(btnSize);
         
         view2 = new JButton("View");
         view2.setPreferredSize(btnSize);
         delete2=new JButton("DELETE");
         delete2.setPreferredSize(btnSize);
         
    }

    private void setDisplay() {
        // 화면 구성 작업 수행
    	FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT); //왼쪽 정렬
    	
    	// pnlMain(pnlMNorth / pnlMCenter)
    	JPanel pnlMain = new JPanel(new BorderLayout());
    	
    	 // pnlMNorth(lblTitle)
        JPanel pnlNorth = new JPanel(flowLeft);
        pnlNorth.add(lblTitle);
        
        //pnlCenter(Lable view delete)
        JPanel pnlCenter = new JPanel(new GridLayout(3, 0)); // 3열의 그리드 레이아웃
        JPanel f_1 = new JPanel(flowLeft);
        f_1.add(lb1);
        f_1.add(t_lb1);
        f_1.add(view1);
        f_1.add(delete1);
        
        JPanel f_2 = new JPanel(flowLeft);
        f_2.add(lb2);
        f_2.add(t_lb2);
        f_2.add(view2);
        f_2.add(delete2);
        
        
        pnlCenter.add(f_1);
        pnlCenter.add(f_2);
        
        pnlMain.add(pnlNorth,BorderLayout.NORTH);
        pnlMain.add(pnlCenter,BorderLayout.CENTER);
        
     // 화면 테두리의 간격을 주기 위해 설정 (insets 사용 가능)
        pnlMain.setBorder(new EmptyBorder(0, 20, 0, 20));
        
        add(pnlMain, BorderLayout.CENTER);
    }

    private void addListener() {
    	
        // 리스너 추가 작업 수행
    	view1.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	showInformation(0);
    	    }
    	});

    	view2.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	showInformation(1);
    	    }
    	});
    	
    	delete1.addActionListener(new ActionListener() {
			//delete info of Friends_1
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				customer.delete(user_id, 0);
			}
		});
    	
    	delete2.addActionListener(new ActionListener() {
    		//delete info of Friends_2
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				customer.delete(user_id, 1);
				
			}
		});

    }
    
    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

	@Override
	public void showInformation() {
		// TODO Auto-generated method stub
		
	}
}
