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