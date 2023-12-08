package com.mycompany.happymindgui;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aneta Kotas
 */
// Entry object
public class MindfulnessEntry extends MindfulnessListActivity implements Serializable {
    private String userEntry;
    private int entryIndex;
    
    public MindfulnessEntry(String userEntry) {
        this.userEntry = userEntry;
    }
    
    public MindfulnessEntry(String userEntry, int entryIndex) {
        this.userEntry = userEntry;
        this.entryIndex = entryIndex;
    }
    
    public MindfulnessEntry() {
        this.userEntry = "";
    } 

    public String getUserEntry() {
        return userEntry;
    }

    public int getEntryIndex() {
        return entryIndex;
    }
    
    public void setUserEntry(String userEntry) {
        this.userEntry = userEntry;
    }

    public void setEntryIndex(int userIndex) {
        this.entryIndex = userIndex;
    }   
}
