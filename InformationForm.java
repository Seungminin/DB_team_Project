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
