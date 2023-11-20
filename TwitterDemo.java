import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
// import javax.swing.tree.TreePath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Allow users to follow
//Allow posts to show across all posts
//Add stats on messages and Positive words
//Look through the patterns

public class TwitterDemo implements ActionListener {
    private JTree directoryTree;
    private JTextArea userInput;
    // private String text;
    private final EntityManager entityManager;
    private UserCount userCount;
    private GroupCount groCount;

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
        userInput = new JTextArea("User ");
        controlPanel.add(userInput, c);

        c.gridx = 1;
        c.gridy = 0;
        JButton addUserBtn = new JButton("Add User");
        userCount = new UserCount(0);
        addUserBtn.addActionListener(event->{
            String username = userInput.getText();
            // this.text = username;
            try{
                User newUser = entityManager.createUser(username);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent(); //gets last clicked path component
                if (selectedNode == null) {
                    selectedNode = rootNode;}
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newUser);
                selectedNode.add(newNode);
                model.reload(selectedNode);
                userCount.Increment();
            } catch(IllegalArgumentException e) {
                showNotification("Error", "This Name Is Taken", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        controlPanel.add(addUserBtn, c);
        
        c.gridx = 0;
        c.gridy = 1;
        JTextArea groupInput = new JTextArea("Group ");
        controlPanel.add(groupInput, c);

        c.gridx = 1;
        c.gridy = 1;
        JButton addGroupBtn = new JButton("Add Group");
        groCount = new GroupCount(0);
        addGroupBtn.addActionListener(event->{
            String grpName = groupInput.getText();
            try{
                Group newGroup = entityManager.createGroup(grpName);
                DefaultMutableTreeNode selectedNode2 = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent();
                 if (selectedNode2 == null) {
                    selectedNode2 = rootNode;}
                DefaultMutableTreeNode newNode2 = new DefaultMutableTreeNode(newGroup);
                selectedNode2.add(newNode2);
                groCount.Increment();
                model.reload(selectedNode2);
            } catch(IllegalArgumentException e) {
                 showNotification("Error", "This Group Title Is Taken", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        controlPanel.add(addGroupBtn, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        JButton viewUserBtn = new JButton("Show User View");
        viewUserBtn.addActionListener(e->{
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) directoryTree.getLastSelectedPathComponent(); //gets last clicked path component
            User user = (User) selectedNode.getUserObject();
            (new UserView(user)).setVisible(true); // will open UserView
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


        //set visible
        frame.setVisible(true);

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


