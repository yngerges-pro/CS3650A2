// import javax.net.ssl.SSLEngineResult;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
// import java.util.Set;


public class UserView extends JFrame implements ActionListener, MessageStorage.MessageListener{
    private JButton btnFollow;
    private final User user;
    private JTextField tweetTextField;
    private JTextArea newsFeedTextArea;
    private JTextArea Follow;
    private JTextField userToFollowText;
    // private MessageCount countMessage = new MessageCount(0);
    // private String issue;
    

    UserView(User user){
        this.user = user;
        this.setTitle(user.getId());
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
                followUser(userToFollowText.getText().trim());
            }
        });
        panel.add(btnFollow, gbc);

        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        userToFollowText = new JTextField();
        userToFollowText.setBorder(BorderFactory.createTitledBorder("Follow User")); //inputs users

        panel.add(userToFollowText, gbc);
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

        // issue = userToFollowText.getText().trim();
        // Follow.append(issue);

    
       
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        JButton btnPostTweet = new JButton("Post Tweet");
        btnPostTweet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tweet = tweetTextField.getText().trim();
                if (!tweet.isEmpty() && !tweet.equals("Tweet message here...")) {
                        postTweet(tweet);
                }
            }
        });
        panel.add(btnPostTweet, gbc);
        displayFollowing();
        this.setVisible(true);
        MessageStorage.getInstance().addChangeListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            MessageStorage.getInstance().removeChangeListener(UserView.this);
            }
            
        });
    }

    public void onChange() {
        displayFeed();
    }

    private void displayFeed() {
        Collection<Tweet> tweets = MessageStorage.getInstance().getLatest(this.user);
        
        StringBuilder builder = new StringBuilder();

        for(Tweet t: tweets) {
            if(t.getId().equals(user.getId())) {
                builder.append(String.format("me: %s\n", t.getMsg()));
            } else {
                builder.append(String.format("%s: %s\n", t.getId(), t.getMsg()));
            }
        }
 
        this.newsFeedTextArea.setText(builder.toString());
    }

    private void displayFollowing() {
        // Set<String> following = this.user.following();
        System.out.println("display following called for " + user.getId() 
            + " following list = [" + String.join(", ", user.following()) + "]");

        this.Follow.setText(String.join("\n", user.following()));
        displayFeed();
    }

    private void followUser(String userId) {
        if(userId != null && !userId.isEmpty()){
            if(EntityManager.getInstance().userExists(userId)){
                this.user.follow(userId);
            } else {
                    showNotification("Error", "Unknown user \"" + userId + "\"", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        displayFollowing();
    }

    private void postTweet(String tweet) {
        MessageStorage.getInstance().postTweet(this.user, tweet);
        // countMessage.Increment();
    }

    // public int getMessageCount(){
    //     return countMessage.getCount();
    // }
    // private void updateFollowersList() {
    //     (followersListAdd).setListData(followers.toArray(new String[0]));
    // }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         public void run(){
    //             new UserView(JOptionPane.showInputDialog("Enter your username: "));
    //         }
    //     });
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }

    private static void showNotification(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}

