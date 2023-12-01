package dbTwitter;

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
}


public class FollowForm extends JDialog implements FollowFormCommon {
    private InformationForm owner;
    
    private JLabel lblTitle;
    private JLabel lb1;
    private JLabel lb2;
    private JLabel lb3;
    
    private JLabel t_lb1;
    private JLabel t_lb2;
    private JLabel t_lb3;
    
    private JButton view1;
    private JButton delete1;
    private JButton view2;
    private JButton delete2;
    private JButton view3;
    private JButton delete3;
    
    public FollowForm(InformationForm owner) {
        super(owner, "Follow", true);
        this.owner = owner;
        init();
        setDisplay();
        addListener();
        showFrame();
    }
    
    @Override
    public void showInformation() {
        // 공통 동작 구현
    	dispose();  // Close the current dialog
    	InformationForm_Follow info_follow = new InformationForm_Follow(FollowForm.this);
	    info_follow.setVisible(true);
    }

    private void init() {
        // 초기화 작업 수행
    	 Dimension lblSize = new Dimension(120, 25);
         Dimension btnSize = new Dimension(70 ,35); //Label, 버튼 크기 고정
         
         lblTitle = new JLabel("- Your Friends-");
         lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
         
         //우리는 Follower, Following을 3명으로 가정을 한다.
         lb1 = new JLabel("Name", JLabel.LEFT);
         lb1.setPreferredSize(lblSize);
         lb2 = new JLabel("Name", JLabel.LEFT);
         lb2.setPreferredSize(lblSize);
         lb3 = new JLabel("Name", JLabel.LEFT);
         lb3.setPreferredSize(lblSize);
         
         //함께아는 친구
         t_lb1 = new JLabel("together", JLabel.LEFT);
         t_lb1.setPreferredSize(lblSize);
         t_lb2 = new JLabel("together", JLabel.LEFT);
         t_lb2.setPreferredSize(lblSize);
         t_lb3 = new JLabel("together", JLabel.LEFT);
         t_lb3.setPreferredSize(lblSize);
         
         view1 = new JButton("View");
         view1.setPreferredSize(btnSize);
         delete1=new JButton("DELETE");
         delete1.setPreferredSize(btnSize);
         
         view2 = new JButton("View");
         view2.setPreferredSize(btnSize);
         delete2=new JButton("DELETE");
         delete2.setPreferredSize(btnSize);
         
         view3 = new JButton("View");
         view3.setPreferredSize(btnSize);
         delete3=new JButton("DELETE");
         delete3.setPreferredSize(btnSize);
         
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
        
        JPanel f_3 = new JPanel(flowLeft);
        f_3.add(lb3);
        f_3.add(t_lb3);
        f_3.add(view3);
        f_3.add(delete3);
        
        pnlCenter.add(f_1);
        pnlCenter.add(f_2);
        pnlCenter.add(f_3);
        
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
    	    	showInformation();
    	    }
    	});

    	view2.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	showInformation();
    	    }
    	});

    	view3.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	    	showInformation();
    	    }
    	});
    	
    	delete1.addActionListener(new ActionListener() {
			//delete info of Friends_1
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	delete2.addActionListener(new ActionListener() {
    		//delete info of Friends_2
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

    	delete3.addActionListener(new ActionListener() {
    		//delete info of Friends_3
    		@Override
    		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		
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
}
