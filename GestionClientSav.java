import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.table.DefaultTableModel;


public class GestionClientSav extends JFrame {
    private JTextField nomField, prenomField, telephoneField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private String result;

    public GestionClientSav() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formulairePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Label et champ pour le nom
        constraints.gridx = 0;
        constraints.gridy = 0;
        formulairePanel.add(new JLabel("Nom :"), constraints);

        constraints.gridx = 1;
        nomField = new JTextField(20);
        formulairePanel.add(nomField, constraints);

        // Label et champ pour le prénom
        constraints.gridx = 0;
        constraints.gridy = 1;
        formulairePanel.add(new JLabel("Prénom :"), constraints);

        constraints.gridx = 1;
        prenomField = new JTextField(20);
        formulairePanel.add(prenomField, constraints);

        // Label et champ pour le téléphone
        constraints.gridx = 0;
        constraints.gridy = 2;
        formulairePanel.add(new JLabel("Téléphone :"), constraints);

        constraints.gridx = 1;
        telephoneField = new JTextField(20);
        formulairePanel.add(telephoneField, constraints);

        // Label et champ pour la réclamation
        constraints.gridx = 0;
        constraints.gridy = 3;
        formulairePanel.add(new JLabel("Réclamation :"), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        formulairePanel.add(new JScrollPane(reclamationArea), constraints);

        // Bouton Envoyer
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        JButton envoyerButton = new JButton("Envoyer");
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String telephone = telephoneField.getText();
                String reclamation = reclamationArea.getText();

                // Ajouter les données au modèle de tableau
                String[] rowData = {nom, prenom, telephone, reclamation};

                tableModel.addRow(rowData);

            }
        });
        formulairePanel.add(envoyerButton, constraints);

        // Créer le modèle de tableau
        String[] columnNames = {"Nom", "Prénom", "Téléphone", "Réclamation"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Créer le tableau
        JTable tableau = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableau);

        constraints.gridx = 0;
        constraints.gridy = 5;
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
      ResultSet res = stmt.executeQuery("SELECT * FROM clients");

      //étape 4: exécuter la requête
      while(res.next()){
        int id = res.getInt(1);
        String nom = res.getString(2);
        String prenom = res.getString(3);
        String reclamation = res.getString(3);
        // Ajouter les données au modèle de tableau
        tableModel.addRow(new Object[]{id, nom, prenom, reclamation});
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


