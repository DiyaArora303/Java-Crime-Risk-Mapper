import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CrimeApp extends JFrame {
    private JComboBox<String> locationBox;
    private JComboBox<String> crimeBox;
    private JButton submitButton;
    private JButton viewButton;

    // Database URL
    private static final String URL = "jdbc:sqlite:crimeDB.db";

    public CrimeApp() {
        setTitle("Crime Entry System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        // ComboBox for Location
        add(new JLabel("Location:"));
        locationBox = new JComboBox<>(new String[] {
                "Downtown", "Riverside", "Market Area", "Uptown", "Suburb"
            });
        add(locationBox);

        // ComboBox for Type of Crime
        add(new JLabel("Type of Crime:"));
        crimeBox = new JComboBox<>(new String[] {
                "Theft", "Burglary", "Assault", "Vandalism", "Fraud"
            });
        add(crimeBox);

        // Buttons
        submitButton = new JButton("Submit");
        viewButton = new JButton("View Records");
        add(submitButton);
        add(viewButton);

        // Button actions
        submitButton.addActionListener(e -> {
                    String location = (String) locationBox.getSelectedItem();
                    String type = (String) crimeBox.getSelectedItem();
                    addCrime(location, type);
            });

        viewButton.addActionListener(e -> showRecords());

        setVisible(true);
    }

    // Method to insert record into DB
    private void addCrime(String location, String typeOfCrime) {
        String insertSQL = "INSERT INTO Crimes (Location, TypeOfCrime) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
        PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, location);
            pstmt.setString(2, typeOfCrime);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "✅ Record added: " + location + " - " + typeOfCrime);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error inserting data: " + e.getMessage());
        }
    }

    // Method to display all crimes in a JTable
    private void showRecords() {
        JFrame tableFrame = new JFrame("Crime Records");
        tableFrame.setSize(500, 300);

        String[] columnNames = {"Location", "Type of Crime"};

        // Use a dynamic list instead of rs.last()
        java.util.List<String[]> rows = new java.util.ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Crimes")) {

            while (rs.next()) {
                rows.add(new String[] {
                        rs.getString("Location"),
                        rs.getString("TypeOfCrime")
                    });
            }

            // Convert list to array for JTable
            String[][] data = new String[rows.size()][2];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i);
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            tableFrame.add(scrollPane);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error fetching data: " + e.getMessage());
        }

        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        createDatabase();
        new CrimeApp();
    }

    // Creates DB and table if not exists
    private static void createDatabase() {
        String createTable = "CREATE TABLE IF NOT EXISTS Crimes (" +
            "Location TEXT, " +
            "TypeOfCrime TEXT)";
        try (Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            System.out.println("Error creating DB: " + e.getMessage());
        }
    }
}
