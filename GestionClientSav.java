import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.DefaultTableModel;


public class GestionClientSav extends JFrame {
    private JTextField nomField, prenomField;
    private JTextField dateField;
    private JComboBox typeField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private String result;

    public GestionClientSav() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] optionsToChoose = {"réparation", "retour", "maintenant"};

        JPanel formulairePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Label et champ pour le nom
        constraints.gridx = 0;
        constraints.gridy = 0;
        formulairePanel.add(new JLabel("Titre :"), constraints);

        constraints.gridx = 1;
        nomField = new JTextField(20);
        formulairePanel.add(nomField, constraints);

        // Label et champ pour le téléphone
        constraints.gridx = 0;
        constraints.gridy = 2;
        Date date1 = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date1);  
        formulairePanel.add(new JLabel("date : "+ strDate), constraints);


        // Label et champ pour le problème
        constraints.gridx = 0;
        constraints.gridy = 3;
        formulairePanel.add(new JLabel("problème :"), constraints);

        constraints.gridx = 1;
        typeField = new JComboBox<>(optionsToChoose);
        formulairePanel.add(typeField, constraints);

        // Label et champ pour la réclamation
        constraints.gridx = 0;
        constraints.gridy = 4;
        formulairePanel.add(new JLabel("Réclamation :"), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        formulairePanel.add(new JScrollPane(reclamationArea), constraints);



// Bouton Envoyer
constraints.gridx = 0;
constraints.gridy = 6;
constraints.gridwidth = 3;
JButton envoyerButton = new JButton("Envoyer");
envoyerButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String id_client = nomField.getText();
        String descripton = reclamationArea.getText();


        // Établir la connexion à la base de données et exécuter la requête d'insertion
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sav", "root", "")) {
            String insertQuery = "INSERT INTO sav_client (id_client, descripton, date) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, id_client);
                pstmt.setString(2, descripton);
                pstmt.setString(3, strDate);
                // id_client	descripton	date	

                // Exécuter la requête d'insertion
                pstmt.executeUpdate();

                // Ajouter les données au modèle de tableau
                String[] rowData = {id_client, descripton, strDate};
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erreur lors de l'ajout des données dans la base de données.");
        }
    }
});
formulairePanel.add(envoyerButton, constraints);


        // Créer le modèle de tableau
        String[] columnNames = {"id_clients", "Réclamation", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Créer le tableau
        JTable tableau = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableau);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 3;
        formulairePanel.add(scrollPane, constraints);

      try
      {
      //étape 1: charger la classe de driver
      Class.forName("com.mysql.jdbc.Driver");

      //étape 2: créer l'objet de connexion
      Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/sav", "root", "");

      //étape 3: créer l'objet statement 
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM sav_client");

      //étape 4: exécuter la requête
      while(res.next()){
        int id = res.getInt(1);
        String id_client = res.getString(2);
        String reclamation = res.getString(3);
        String date = res.getString(4);
        // Ajouter les données au modèle de tableau
        tableModel.addRow(new Object[]{id, id_client, reclamation, date});
      }

      System.out.println("tes ok");

    }
    catch(Exception e){ 
      System.out.println(e);
      System.out.println("erreur");
    }

        add(formulairePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionClientSav();
                
            }
        });
    }
}


