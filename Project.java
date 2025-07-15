import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Project {

  
    public static class UserDatabase { //hashmap databases.
        private static HashMap<String, String> users = new HashMap<>(); //only id and password to easily check if this account already exist or not.
        private static HashMap<String, StudentInfo> studentInfos = new HashMap<>(); //this is where we store every info.

        public static boolean addUser(String id, String password, StudentInfo info) { //student info is a class that we created.
            if (users.containsKey(id)) {
                return false; //we will see in sign up screen this will print that account has already exists.
            }
            users.put(id, password); //if there is no account with same id, it will create new account.
            studentInfos.put(id, info);
            return true;
        }

        public static boolean checkUser(String id, String password) { //to easily check if there is a account with same id already.
            return users.containsKey(id) && users.get(id).equals(password);
        }

        public static StudentInfo getStudentInfo(String id) {
            return studentInfos.get(id);
        }
    }

    public static class StudentInfo { //our class that store students information. But be carefull this is not INFOS, INFO the infos are hashmap.
        String name,surname, gender, course, address;

        public StudentInfo(String name, String surname, String gender, String course, String address) {
            this.name = name; //used this in order to get objects easily with corresponding ones.
            this.surname=surname;
            this.gender = gender;
            this.course = course;
            this.address = address;
        }
    }
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) return false; 
        boolean hasUpper = false;
        boolean hasSpecial = false;
        boolean hasLower = false;
        String specialChars = "!@#$%^&*()-_=+[]{};:'\",.<>?/\\|`~"; //at least one special key and one uppercase letter.

        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) hasLower= true;
            if (Character.isUpperCase(ch)) hasUpper = true; //control for the password if it has upper case letter.
            if (specialChars.indexOf(ch) >= 0) hasSpecial = true;} // control for usage of special key.
        return hasUpper && hasSpecial && hasLower;}

    //The page for registering.
    public static class SignUpFrame extends JFrame { //extended this with JFrame to get rid of from creating a frame variable.
        public SignUpFrame() {
            setTitle("Sign Up");
            setSize(400, 400);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(255,245,230));
            
            
            JTextField nameField = new JTextField();
            JTextField surnameField= new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField idField = new JTextField();
            JRadioButton maleBtn = new JRadioButton("Male");
            maleBtn.setBackground(new Color(255,245,230));
            JRadioButton femaleBtn = new JRadioButton("Female");
            femaleBtn.setBackground(new Color(255,245,230));
            ButtonGroup genderGroup = new ButtonGroup();
            genderGroup.add(maleBtn);
            genderGroup.add(femaleBtn);
            JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            genderPanel.setBackground(new Color(255,245,230));
            genderPanel.add(maleBtn);
            genderPanel.add(femaleBtn);

            String[] courses = {"Computer Engineering", "Software Engineering", "Electrical Engineering", "English Teacher", "Industrial Engineering","Physics"};
            JComboBox<String> courseComboBox = new JComboBox<>(courses);
            courseComboBox.setBackground(Color.WHITE);

            JTextArea addressArea = new JTextArea(3, 20);
            addressArea.setLineWrap(true);
            addressArea.setWrapStyleWord(true); //added this for better aesthetics.

            JScrollPane addressScroll = new JScrollPane(addressArea);
           

            JButton submitButton = new JButton("Register");
            submitButton.setBackground(new Color(76,175,80));
            submitButton.setForeground(Color.WHITE);
            JButton backButton= new JButton("Back");
            backButton.setBackground(Color.lightGray);
            backButton.setForeground(Color.WHITE);
            
            panel.add(new JLabel("Name:")); panel.add(nameField);
            panel.add(new JLabel("Surname:")); panel.add(surnameField);
            panel.add(new JLabel("Password:")); panel.add(passwordField);
            panel.add(new JLabel("ID:")); panel.add(idField);
            panel.add(new JLabel("Gender:")); panel.add(genderPanel);
            panel.add(new JLabel("Course:")); panel.add(courseComboBox);
            panel.add(new JLabel("Address:")); panel.add(addressScroll);
            panel.add(backButton); ; panel.add(submitButton);

            add(panel);
            setVisible(true);

            submitButton.addActionListener(e -> {
    String name = nameField.getText().trim();
    String surname= surnameField.getText().trim();
    String password = new String(passwordField.getPassword());
    String id = idField.getText().trim();
    String gender = maleBtn.isSelected() ? "Male" : femaleBtn.isSelected() ? "Female" : "";
    String course = (String) courseComboBox.getSelectedItem();
    String address = addressArea.getText().trim();

    if (name.isEmpty() ||surname.isEmpty()|| password.isEmpty() || id.isEmpty() || gender.isEmpty() || course.isEmpty() || address.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all the blanks.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!isValidPassword(password)) {
        JOptionPane.showMessageDialog(this, "Password has to be at least 8 words length. Must have at least 1 uppercase, lowercase and 1 special key.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (UserDatabase.addUser(id, password, new StudentInfo(name,surname, gender, course, address))) {
        JOptionPane.showMessageDialog(this, "Account Created Successfuly!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new SignInFrame();
    } else {
        JOptionPane.showMessageDialog(this, "This id is already used.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});
            
           backButton.addActionListener(e ->{
               dispose();
               new SignInFrame();
           });
        }
    }


  
    public static class SignInFrame extends JFrame {
        public SignInFrame() {
            setTitle("Sign In");
            setSize(300, 150);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
            panel.setBackground(new Color(230,240,255));
            JTextField idField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JButton loginButton = new JButton("Login");
            loginButton.setBackground(new Color(76,175,80));
            loginButton.setForeground(Color.WHITE);
            JButton signupButton = new JButton("Sign Up");
            signupButton.setBackground(new Color(2,15,200));
            signupButton.setForeground(Color.WHITE);
          
            
            panel.add(new JLabel("ID:"));
            panel.add(idField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(signupButton);
            panel.add(loginButton);

            add(panel);
            setVisible(true);

            loginButton.addActionListener(e -> {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());
                if (id.equals("admin") && password.equals("123")) {
                    StudentInfo adminInfo = new StudentInfo("Admin", "Master", "Unknown", "All Courses", "Admin Office");
                    dispose();
                    new MainPage("admin", adminInfo);
                    return;
                }


                if (UserDatabase.checkUser(id, password)) {
                    StudentInfo info = UserDatabase.getStudentInfo(id);
                    dispose();
                    new MainPage(id,info);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid ID or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            });

            signupButton.addActionListener(e -> {
                dispose();
                new SignUpFrame();
            });
        }
    }
public static class MainPage extends JFrame {
    public MainPage(String id, StudentInfo info) {
        setTitle("Student Portal - Home Page");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));

        
        JLabel titleLabel = new JLabel("Welcome to Student Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        titleLabel.setForeground(new Color(25, 118, 210));

        //profile picture place but just for holding place (for aesthetics)
        JLabel profilePic = new JLabel();
        profilePic.setPreferredSize(new Dimension(100, 100));
        profilePic.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setText("Profile\nPicture");
        profilePic.setVerticalAlignment(SwingConstants.CENTER);
        profilePic.setFont(new Font("SansSerif", Font.PLAIN, 12)); 
        profilePic.setOpaque(true);
        profilePic.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.add(profilePic);

        
        JPanel infoPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        infoPanel.setBackground(new Color(240, 248, 255));

        JLabel nameLabel = new JLabel("👤 Name: " + info.name);
        JLabel surnameLabel=new JLabel("👤 Surname: "+info.surname);
        JLabel idLabel = new JLabel("🆔 ID: " + id);
        JLabel genderLabel = new JLabel("🚻 Gender: " + info.gender);
        JLabel courseLabel = new JLabel("📚 Course: " + info.course);
        JLabel addressLabel = new JLabel("🏠 Address: " + info.address);

        Font infoFont = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{nameLabel,surnameLabel, idLabel, genderLabel, courseLabel, addressLabel}) {
            label.setFont(infoFont); //we change every data's font to SansSerif
        }

        infoPanel.add(nameLabel);
        infoPanel.add(surnameLabel);
        infoPanel.add(idLabel);
        infoPanel.add(genderLabel);
        infoPanel.add(courseLabel);
        infoPanel.add(addressLabel);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        JButton logOut = new JButton("Log Out");
        logOut.setPreferredSize(new Dimension(100, 35));
        logOut.setFocusPainted(false);
        logOut.setBackground(new Color(220, 53, 69));
        logOut.setForeground(Color.WHITE);
        logOut.setFont(new Font("SansSerif", Font.BOLD, 14));
        logOut.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        buttonPanel.add(logOut);

        logOut.addActionListener(e -> {
            dispose();
            new SignInFrame();
        });

        panel.add(titleLabel, BorderLayout.NORTH); //location of label and panels
        panel.add(topPanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignInFrame();
        });
        
    }
}
