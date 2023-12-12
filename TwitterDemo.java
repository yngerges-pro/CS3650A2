import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TwitterDemo implements ActionListener {
    private JTree directoryTree;
    private JTextArea userInput;
    
    private final EntityManager entityManager;
    private UserCount userCount;
    private GroupCount groCount;
    
    private User newUser;

    public MessageStorage storage;
    private DefaultMutableTreeNode newNode;
    private String username;
    
    private Map<String, Integer> uniqueUsernames = new HashMap<>();
    
    private String time;
  
    TwitterDemo(){
        this.entityManager = EntityManager.getInstance();
        //JFrame
        JFrame frame = new JFrame("Twitter Demo");
        frame.setSize(600,600); //you can adjust the size to match the rectangles
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); //2x2 grid with 4 px gaps between each element

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
        directoryTree = new JTree(rootNode);
        DefaultTreeModel model = (DefaultTreeModel) directoryTree.getModel();
        JPanel controlPanel = new JPanel();

        controlPanel.setBackground(Color.PINK);
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        userInput = new JTextArea("User");
        controlPanel.add(userInput, c);

        c.gridx = 1;
        c.gridy = 0;
        JButton addUserBtn = new JButton("Add User");
        userCount = new UserCount(0);
        addUserBtn.addActionListener(event->{
            username = userInput.getText().trim();
            
            try{
                newUser = entityManager.createUser(username);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent(); //gets last clicked path component
                if (selectedNode == null) {
                    selectedNode = rootNode;}
                newNode = new DefaultMutableTreeNode(newUser);
                selectedNode.add(newNode);
                model.reload(selectedNode);
                userCount.Increment();
                uniqueUsernames.put(username, 1);

                 
                Timer timer = new Timer(1000, e4 -> {
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
                
                String formattedTime = String.format("%02d:%02d:%02d PT", hours, minutes, seconds);
                
                System.out.println("Created User: "+username+" at "+ formattedTime); //Prints the time in the console
            });

            timer.start();
            } catch(IllegalArgumentException e) {
                System.out.println("Error "+ e);
                 
                
            }
        });
        controlPanel.add(addUserBtn, c);
        
        c.gridx = 0;
        c.gridy = 1;
        JTextArea groupInput = new JTextArea("Group");
        controlPanel.add(groupInput, c);

        c.gridx = 1;
        c.gridy = 1;
        JButton addGroupBtn = new JButton("Add Group");
        groCount = new GroupCount(0);
        addGroupBtn.addActionListener(event->{
            String grpName = groupInput.getText().trim();
            try{
                Group newGroup = entityManager.createGroup(grpName);
                DefaultMutableTreeNode selectedNode2 = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
                 if (selectedNode2 == null) {
                    selectedNode2 = rootNode;}
                DefaultMutableTreeNode newNode2 = new DefaultMutableTreeNode(newGroup);
                selectedNode2.add(newNode2);
                groCount.Increment();
                model.reload(selectedNode2);

                Timer timer = new Timer(1000, e4 -> {
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

                String formattedTime = String.format("%02d:%02d:%02d PT", hours, minutes, seconds);
                this.time = formattedTime;
                System.out.println("Created Group: "+grpName+" at "+ time); //Prints the time in the console
            });

            timer.start();
            } catch(IllegalArgumentException e) {
               
                System.out.println("Error " + e);
            }
        });
        controlPanel.add(addGroupBtn, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        JButton viewUserBtn = new JButton("Show User View");
        viewUserBtn.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
            User user = (User) selectedNode.getUserObject();
            
            new UserView(user).setVisible(true);
        });
    

         
        controlPanel.add(viewUserBtn, c);

        frame.add(directoryTree, BorderLayout.LINE_START);
        frame.add(controlPanel, BorderLayout.CENTER);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        JLabel jlab1 = new JLabel(" ");
        controlPanel.add(jlab1, c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        JButton userCountbtn = new JButton("Count User");
        userCountbtn.addActionListener(e->{
            String info = Integer.toString(userCount.getCount());

            
        // Display a simple information message
        showNotification("User Count", "Number of Users: " + info, JOptionPane.INFORMATION_MESSAGE);
        });
        controlPanel.add(userCountbtn, c);


        c.gridx = 0;
        c.gridy = 5;
        JButton messageCountbtn = new JButton("Count Messages");
        messageCountbtn.addActionListener(e->{
            
            int MessageInfo = MessageStorage.getInstance().getNumMessages();
            showNotification("Number of messages", "Total Messages "+ MessageInfo, JOptionPane.INFORMATION_MESSAGE);
        });

        controlPanel.add(messageCountbtn, c);

        c.gridx = 1;
        c.gridy = 5;
        JButton countPositivebtn = new JButton("Count Positivity");
        countPositivebtn.addActionListener(e->{
        
            int count = MessageStorage.getInstance().getPos();
            showNotification("Number of Positive Words", "Total Positive Words: " + count, JOptionPane.INFORMATION_MESSAGE);
        });
        
        controlPanel.add(countPositivebtn, c);

       
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        JButton groupCountbtn = new JButton("Count Group");
        groupCountbtn.addActionListener(e->{
            String info2 = Integer.toString(groCount.getCount());
            // Display a simple information message
            showNotification("Group Count", "Number of Groups: "+info2, JOptionPane.INFORMATION_MESSAGE);
        });

        controlPanel.add(groupCountbtn, c);

        
    
        GridBagConstraints c2 = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        
        //Button that Validates Id based on 2 criterias (no dublicates and no spaces)
        JButton checkIDButton = new JButton("Validate ID");
        checkIDButton.addActionListener(e->{
        if(isDublicate()){
            showNotification("Unavailable", username + " Is Taken", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(username.contains(" ")){
            showNotification("Error", username + " Is Invalid", JOptionPane.INFORMATION_MESSAGE);
        }else{
            showNotification("Vaild", username + " Is Valid and Available", JOptionPane.INFORMATION_MESSAGE);
        }
        
    });

        controlPanel.add(checkIDButton, c);

        
        
    c.gridx = 1;
    c.gridy = 7;
    c.gridwidth = 1;
    //Button that shows the latest User to post
    JButton LatestPostbtn = new JButton("User with Latest Post");
    LatestPostbtn.addActionListener(e -> {
        UserView obj = new UserView(newUser);
        obj.setVisible(false);

        Map<String, String> nameTimings = obj.getLastestUser();

        int index = 0;
        String AssociatedUser = "";

        String[] Names = new String[nameTimings.size()+2]; // move the initialization outside the loop

        for (Map.Entry<String, String> entry : nameTimings.entrySet()) {
            
            String name = entry.getKey();
            Names[index] = name;
            index++;
        }

        AssociatedUser = Names[nameTimings.size()-1]; //gives the last added user in the map
        showNotification("Latest Posted User", "Latest User: " + AssociatedUser, JOptionPane.INFORMATION_MESSAGE);
    });

    controlPanel.add(LatestPostbtn, c);

        c2.gridx=0;
        c2.gridy =6;
        c2.gridwidth = 2;
        c2.anchor = GridBagConstraints.CENTER;
        JLabel text = new JLabel(" ");
        controlPanel.add(text,c2);

        //set visible
        frame.setVisible(true);
    }


    //checks for Dulicates
    public boolean isDublicate(){
        String[] keyArray = uniqueUsernames.keySet().toArray(new String[uniqueUsernames.size()]);
        int i = 0;
        while(i < keyArray.length){
            if(keyArray[i].trim().equalsIgnoreCase(username.trim())){

                if(keyArray.length == 1){ 
                    return true;
                }else if(i == keyArray.length-1){
                    return false;
                }

                return true;
            }else{
              i++;
            }
        }

        return false;
    }


    private static void showNotification(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new TwitterDemo();
            }
        });
    }

     @Override
    public void actionPerformed(ActionEvent e) {
       
      }

}


