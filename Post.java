package db_final;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Post extends JDialog {
    private InformationForm owner;
    private String user_id;
    private JTextArea postContent;
    private JTextField postTitle;
    private JButton postButton;
    private static ArrayList<Post> posts = new ArrayList<>();

    public Post(InformationForm owner) {
        super(owner, "Post", true);
        this.owner = owner;
        this.user_id = owner.getUserId();

        init();
        setDisplay();
        addListener();
        showFrame();
    }

    public String getUserId() {
        return user_id;
    }

    public String getTitle() {
        return postTitle.getText();
    }

    public String getContent() {
        return postContent.getText();
    }

    private void init() {
        postTitle = new JTextField(20);
        postContent = new JTextArea(10, 20);
        postButton = new JButton("Post");
    }

    private void setDisplay() {
        JPanel pnlMain = new JPanel(new BorderLayout());

        JPanel pnlNorth = new JPanel();
        pnlNorth.add(new JLabel("Title: "));
        pnlNorth.add(postTitle);

        JPanel pnlCenter = new JPanel();
        pnlCenter.add(new JLabel("Content: "));
        pnlCenter.add(new JScrollPane(postContent));

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(postButton);

        pnlMain.add(pnlNorth, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlSouth, BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void addListener() {
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = postTitle.getText();
                String content = postContent.getText();
                if (title.isEmpty() || content.isEmpty()) {
                    JOptionPane.showMessageDialog(Post.this, "Title or Content can't be empty.");
                } else {
                    Post newPost = new Post(owner);
                    posts.add(newPost);
                    System.out.println("Posted by " + user_id + ": " + title + "" + content);
                    dispose();
                }
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
