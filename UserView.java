// import javax.net.ssl.SSLEngineResult;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
// import java.util.Set;
import java.util.HashMap;
import java.util.Map;


public class UserView extends JFrame implements ActionListener, MessageStorage.MessageListener{
    private JButton btnFollow;
    private final User user;
    private JTextField tweetTextField;
    private JTextArea newsFeedTextArea;
    private JTextArea Follow;
    private JTextField userToFollowText;
   
    
    public String time;
    
    private Map<String,String> lastestUserPost = new HashMap<>();

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
       

        
        gbc.gridy = 4;
        gbc.gridx = 0; 
        gbc.gridwidth = 6;
        gbc.weighty = 1.0;
        
        Follow = new JTextArea();
        Follow.setBorder(BorderFactory.createTitledBorder("Following")); //list of users
       
        panel.add(new JScrollPane(Follow), gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        JButton btnPostTweet = new JButton("Post Tweet");
        

        btnPostTweet.addActionListener(new ActionListener() { //when clicking btn
    

             public void actionPerformed(ActionEvent e) { //action performed

                Timer timer = new Timer(1000, e4 -> { //Timer starts
                
                long currentTime = System.currentTimeMillis();
               
                if (currentTime >= 1000) {
                    ((Timer) e4.getSource()).stop();
                }
                
                long totalSeconds = currentTime / 1000;
                
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;
                long hours = (minutes % 3600) / 60;

                if(hours == 0){
                    hours = 12;
                }else if(hours >= 13){
                    hours = hours - 12;
                }

                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                setTime(formattedTime);//sets Time
                

                 String tweet = tweetTextField.getText().trim();
                if (!tweet.isEmpty() && !tweet.equals("Tweet message here...")) {
                    String time = getTime(); //gets Time
                    
                        postTweet(tweet, time);
                }

            });
             timer.start();
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

        public void setTime(String time2){
            this.time = time2;
        }
        public String getTime(){
            return this.time;
        }

        public void onChange() {
            displayFeed();
        }

        private void displayFeed() {
            Collection<Tweet> tweets = MessageStorage.getInstance().getLatest(this.user);
            
            StringBuilder builder = new StringBuilder();

            for(Tweet t: tweets) {
                if(t.getId().equals(user.getId())) {
                    builder.append(String.format("me: %s at time %s\n", t.getMsg(), t.getTime()));
                } else {
                    builder.append(String.format("%s: %s at time %s\n", t.getId(), t.getMsg(), t.getTime()));
                }

                lastestUserPost.put(t.getId(), t.getTime()); //Key being User and Value being Time
            }
    
            this.newsFeedTextArea.setText(builder.toString());
        }

    private void displayFollowing() {
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

    private void postTweet(String tweet, String time) {
        MessageStorage.getInstance().postTweet(this.user, tweet, time);
       
    }

   
    public Map<String,String> getLastestUser(){
        return lastestUserPost;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    }

    private static void showNotification(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    
}

