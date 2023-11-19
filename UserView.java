import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.FocusEvent;
// import java.awt.event.FocusListener;
import java.util.ArrayList;


public class UserView extends JFrame implements ActionListener{
    private JButton btnFollow;
    private ArrayList<String> followers;
    // private ArrayList<String> followersListAdd;
    private String userName;
    // private JScrollPane followersScrollPane;
    private JTextField tweetTextField;
    private JTextArea newsFeedTextArea;
    // private JScrollPane followersListAdd;
    private JTextArea Follow;
    private JTextField Follow3;
    // private JScrollPane ShowFollowing;
    // private Object followersListAdd;
    private String issue;
    

    UserView(String username){
        this.userName = username;
        this.setTitle(userName);
        this.setSize(600,600); //you can adjust the size to match the rectangles
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel controlPanel = new JPanel();

        JPanel panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(panel, BorderLayout.CENTER);
        
        JTextArea Posts = new JTextArea("Message: ");
        controlPanel.add(Posts);
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        newsFeedTextArea = new JTextArea();
        newsFeedTextArea.setBorder(BorderFactory.createTitledBorder("Live News Feed")); //Where messages go
        panel.add(new JScrollPane(newsFeedTextArea), gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        tweetTextField = new JTextField();
        tweetTextField.setBorder(BorderFactory.createTitledBorder("Message here: ")); //where users inputs a message
        // tweetTextField.addFocusListener(new FocusListener() {
        //     // @Override
        //     // public void focusGained(FocusEvent e) {
        //     //     if (tweetTextField.getText().equals("Message here: ")) {
        //     //         tweetTextField.setText("");
        //     //     }
        //     // }

        //     @Override
        //     public void focusLost(FocusEvent e) {
        //         if (tweetTextField.getText().isEmpty()) {
        //             tweetTextField.setText("Show messages here ");
        //         }
        //     }
        // });
        panel.add(tweetTextField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        btnFollow = new JButton("Follow");
        btnFollow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //where users follow
                followUser(issue);
            }
        });
        panel.add(btnFollow, gbc);

        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        followers = new ArrayList<>(); //list of following users
        Follow3 = new JTextField();
        Follow3.setBorder(BorderFactory.createTitledBorder("Follow User")); //inputs users

        panel.add(Follow3, gbc);
        // panel.add(new JScrollPane(Follow3), gbc);

        
        gbc.gridy = 4;
        gbc.gridx = 0; 
        gbc.gridwidth = 6;
        gbc.weighty = 1.0;
        // followersList = new JList<>(followers.toArray(new String[0]));
        Follow = new JTextArea();
        Follow.setBorder(BorderFactory.createTitledBorder("Following")); //list of users
        // followersList = new JList(issue);
        panel.add(new JScrollPane(Follow), gbc);
        // panel.add(Follow, gbc);

        issue = Follow3.getText().trim();
        // Follow.append(issue);

    
       
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        JButton btnPostTweet = new JButton("Post Tweet");
        btnPostTweet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postTweet(userName);
            }
        });
        panel.add(btnPostTweet, gbc);

        this.setVisible(true);
    }

    private void followUser(String userName) {
        
        followers.add(userName);
        // updateFollowersList();
    if(!userName.isEmpty()){
        Follow.append(userName);
    }
    }

    private void postTweet(String userName) {
        String tweet = tweetTextField.getText().trim();
        if (!tweet.isEmpty() && !tweet.equals("Tweet message here...")) {
        
            newsFeedTextArea.append(userName + ": " + tweet + "\n");
            tweetTextField.setText("Tweet message here...");
        }
    }

    // private void updateFollowersList() {
    //     (followersListAdd).setListData(followers.toArray(new String[0]));
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new UserView(JOptionPane.showInputDialog("Enter your username: "));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }

}

