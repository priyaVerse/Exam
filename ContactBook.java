import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ContactBook extends JFrame {
    JTextField nameField, phoneField, searchField;
    DefaultListModel<String> contactModel;
    JList<String> contactList;
    ArrayList<String[]> contacts = new ArrayList<>();

    ContactBook() {
        setTitle("Java Contact Book");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        phoneField = new JTextField();
        searchField = new JTextField();

        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(phoneField);

        JButton addButton = new JButton("Add Person");
        inputPanel.add(addButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Search Name:"));
        inputPanel.add(searchField);

        add(inputPanel, BorderLayout.NORTH);

        contactModel = new DefaultListModel<>();
        contactList = new JList<>(contactModel);
        add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Selected");

        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addContact());
        searchButton.addActionListener(e -> searchContacts());
        showAllButton.addActionListener(e -> showAllContacts());
        deleteButton.addActionListener(e -> deleteContact());
    }

    void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter name and phone number.");
            return;
        }

        contacts.add(new String[]{name, phone});
        showAllContacts();
        nameField.setText("");
        phoneField.setText("");
    }

    void searchContacts() {
        String query = searchField.getText().trim().toLowerCase();
        contactModel.clear();

        for (String[] contact : contacts) {
            if (contact[0].toLowerCase().contains(query)) {
                contactModel.addElement(contact[0] + " - " + contact[1]);
            }
        }

        if (contactModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No contacts found.");
        }
    }

    void showAllContacts() {
        contactModel.clear();

        for (String[] contact : contacts) {
            contactModel.addElement(contact[0] + " - " + contact[1]);
        }
    }

    void deleteContact() {
        int index = contactList.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a contact to delete.");
            return;
        }

        String selected = contactList.getSelectedValue();

        for (int i = 0; i < contacts.size(); i++) {
            String[] contact = contacts.get(i);
            if ((contact[0] + " - " + contact[1]).equals(selected)) {
                contacts.remove(i);
                break;
            }
        }

        showAllContacts();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactBook().setVisible(true));
    }
}