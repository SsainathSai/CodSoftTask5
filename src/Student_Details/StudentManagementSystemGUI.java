package Student_Details;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentManagementSystemGUI {
    private JFrame frame;
    private JPanel loginPanel;
    private JPanel actionPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton searchButton;
    private JButton displayButton;
    private JTextArea displayArea;
    private StudentManagementSystem system;

    public StudentManagementSystemGUI() {
        system = new StudentManagementSystem();

        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        
        loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

       
        actionPanel = new JPanel(new GridLayout(1, 5));
        addButton = new JButton("Add Student");
        removeButton = new JButton("Remove Student");
        searchButton = new JButton("Search Student");
        displayButton = new JButton("Display All Students");
        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(searchButton);
        actionPanel.add(displayButton);

   
        displayArea = new JTextArea(20, 50);
        displayArea.setEditable(false);

        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.CENTER);
        frame.add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login authentication here
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password)) {
                    showActionPanel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter student name:");
                if (name != null && !name.isEmpty()) {
                    String rollNumberStr = JOptionPane.showInputDialog(frame, "Enter roll number:");
                    if (rollNumberStr != null && !rollNumberStr.isEmpty()) {
                        try {
                            int rollNumber = Integer.parseInt(rollNumberStr);
                            String grade = JOptionPane.showInputDialog(frame, "Enter grade:");
                            Student student = new Student(name, rollNumber, grade);
                            system.addStudent(student);
                            displayArea.append("Student added: " + student.toString() + "\n");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid roll number", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNumberStr = JOptionPane.showInputDialog(frame, "Enter roll number of student to remove:");
                if (rollNumberStr != null && !rollNumberStr.isEmpty()) {
                    try {
                        int rollNumber = Integer.parseInt(rollNumberStr);
                        if (system.removeStudent(rollNumber)) {
                            displayArea.append("Student with roll number " + rollNumber + " removed\n");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid roll number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNumberStr = JOptionPane.showInputDialog(frame, "Enter roll number of student to search:");
                if (rollNumberStr != null && !rollNumberStr.isEmpty()) {
                    try {
                        int rollNumber = Integer.parseInt(rollNumberStr);
                        Student student = system.searchStudent(rollNumber);
                        if (student != null) {
                            displayArea.append("Student found: " + student.toString() + "\n");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid roll number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
            }
        });

        frame.setVisible(true);
    }

    private boolean authenticate(String username, String password) {
       
        return username.equals("admin") && password.equals("password");
    }

    private void showActionPanel() {
        frame.remove(loginPanel);
        frame.add(actionPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    private void displayAllStudents() {
        displayArea.setText("");
        ArrayList<Student> students = system.getAllStudents();
        if (!students.isEmpty()) {
            for (Student student : students) {
                displayArea.append(student.toString() + "\n");
            }
        } else {
            displayArea.append("No students found\n");
        }
    }
}