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


public class FollowForm_friends extends JDialog implements FollowFormCommon {
    private InformationForm_Follow owner;
    private Customer customer = new Customer();
    private String user_id;
    private String[] friends = new String[3];
    private String[] names = new String[3]; 
    private String[] toge_names = new String[3];
    
    private JLabel lblTitle;
    private JLabel lb1;
    private JLabel lb2;
    
    private JButton view1;
    private JButton view2;
    
    public FollowForm_friends(InformationForm_Follow owner) {
        super(owner, "Follow_friends", true);
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
    	// 예: InformationForm_Follow를 생성하고 보이게 하는 등의 동작을 추가할 수 있습니다.
    	dispose();  // Close the current dialog
        InformationForm_Follow infoFollow = new InformationForm_Follow(FollowForm_friends.this,friendId);
        infoFollow.setVisible(true);
    }

    private void init() {
        // 초기화 작업 수행
    	 Dimension lblSize = new Dimension(120, 25);
         Dimension btnSize = new Dimension(70 ,35); //Label, 버튼 크기 고정
         
         lblTitle = new JLabel("- Follow`s Friends-");
         lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
         
         //우리는 Follower, Following을 3명으로 가정을 한다.
         customer.getName(user_id, names);
         customer.getId(user_id, friends); //following 한 친구들의 id를 보낼 수 있다.
         System.out.println("User_ID = "+user_id);
         lb1 = new JLabel(names[0], JLabel.LEFT);
         lb1.setPreferredSize(lblSize);
         lb2 = new JLabel(names[1], JLabel.LEFT);
         lb2.setPreferredSize(lblSize);
         
         view1 = new JButton("View");
         view1.setPreferredSize(btnSize);
         
         view2 = new JButton("View");
         view2.setPreferredSize(btnSize);        
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
        if (lb1 != null) {
            f_1.add(lb1);
            f_1.add(view1);
        }
        
        JPanel f_2 = new JPanel(flowLeft);
        if (lb2 != null) {
            f_2.add(lb2);
            f_2.add(view2);
        }
        
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