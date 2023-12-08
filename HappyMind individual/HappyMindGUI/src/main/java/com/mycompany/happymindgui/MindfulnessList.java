package com.mycompany.happymindgui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aneta Kotas
 */

public class MindfulnessList extends MindfulnessListActivity {
    // Initial variables
    private ArrayList<MindfulnessEntry> mindfulnessList;
    private MindfulnessEntry mindfulnessEntry;
    private Connection conn;
    
    // Sets connection with the database
    public void getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HappyMind", "root", "your password!!!!");
    }
    
    private MindfulnessList() {
        mindfulnessList = new ArrayList<MindfulnessEntry>();
    }
    
    // Initializing the list instance
    private static MindfulnessList listInstance = new MindfulnessList();
    
    public static MindfulnessList getListInstance() {
        return listInstance;
    }
    
    public void addEntry(MindfulnessEntry mindfulnessEntry) {
        mindfulnessList.add(mindfulnessEntry);
    }
    
    public MindfulnessEntry getEntry(int i) {
        return mindfulnessList.get(i);
    }
    
    public int getSize() {
        return mindfulnessList.size();
    }
    
    // Updates entry in a database depending on which item is pressed, deleteSelection returns text of pressed item
    public void updateEntry(int deleteIndex, String deleteSelection) throws SQLException {
        this.getConnection();
        String editedEntry = JOptionPane.showInputDialog("Input new content:");
        MindfulnessEntry mindfulnessEntry = new MindfulnessEntry(editedEntry);
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE Entry SET entryContext = '" + editedEntry + "' WHERE entryContext = '" + deleteSelection + "'" );
        mindfulnessList.set(deleteIndex, mindfulnessEntry);
        conn.close();
    }
    public void getAllEntries() {
        for(int i = 0; i < mindfulnessList.size(); i++) {
            System.out.println("Entry " + (i + 1) + ": " + mindfulnessList.get(i));
        }
    }
    
    public int getEntryIndex(MindfulnessEntry mindfulnessEntry) {
        return mindfulnessList.indexOf(mindfulnessEntry);
    }
    
    // Makes sure entries on the list have index next to them an dthat indexing starts from 1
    public ArrayList<String> getEntries() {
        ArrayList<String> entryStrings = new ArrayList<>();
        int size = 1;
        int iter = -1;
        for(MindfulnessEntry mindfulnessEntry: mindfulnessList) {
            iter++;
            entryStrings.add((size + iter) + ". " + mindfulnessEntry.getUserEntry());
        }
        return entryStrings;
    }
    
    // Downloads data from database and saves it in a MindfulnessList
    public void readFile() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        this.getConnection();
        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM Entry");
        while(results.next()) {
            MindfulnessEntry mindfulnessEntry = null;
            String userEntry = results.getString("entryContext");
            String userIndex = String.valueOf(results.getInt("entryIndex"));
            mindfulnessEntry = new MindfulnessEntry(userEntry, Integer.parseInt(userIndex));
            mindfulnessList.add(mindfulnessEntry);  
        }
        conn.close();
    }
    
    // Loads goal data from database
    public String loadGoal() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        this.getConnection();
        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery("SELECT goalSize FROM Goal WHERE goalID = 1");
        String goal = "";
        while(results.next()) {
            goal = results.getString("goalSize");
        }
        conn.close();
        return goal;
    }
    
    // Saves entries in a database
    public void saveFile(MindfulnessEntry mindfulnessEntry) throws FileNotFoundException, IOException, SQLException {
          this.getConnection();
          Statement statement = conn.createStatement();
          String userEntry = mindfulnessEntry.getUserEntry();
          statement.executeUpdate("INSERT INTO Entry(entryContext) VALUES('" + userEntry + "');");
          conn.close();
    }
    
    // Looks for given entry and returns its position on the list
    public void searchEntry(String search) throws FileNotFoundException, IOException, SQLException {
        int inx = 0;
        for (MindfulnessEntry mindfulnessEntry : mindfulnessList) {
            inx++;
            if(mindfulnessEntry.getUserEntry().equalsIgnoreCase(search)) {
                JOptionPane.showMessageDialog(null, "Your entry's position is number: " + inx);
                break;
            } 
        }
        // Displays "not found" message if entry not on the list
        if (inx > getSize()){
            JOptionPane.showMessageDialog(null, "Entry not found.");
        }
    }
    
    // Deletes entry from a database
    public void deleteEntry(int deleteIndex, String deleteSelection) throws SQLException, IOException, FileNotFoundException, ClassNotFoundException {
          this.getConnection();
          mindfulnessList.remove(deleteIndex);
          Statement statement = conn.createStatement();
          statement.executeUpdate("DELETE FROM Entry WHERE entryContext = '" + deleteSelection + "'");
          conn.close();
    }
    
    // Changes cirrent goal to a different one and sets it in a database
    public void changeGoal(int newGoal) throws SQLException {
        this.getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE Goal SET goalSize = " + newGoal);
        conn.close();
    }
}

