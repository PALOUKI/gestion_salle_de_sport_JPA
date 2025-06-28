package org.example;

import gui_admin.gui_util.MyWindow1; // Importe MyWindow1

import javax.swing.SwingUtilities;
// Rend la fenêtre visible
public class MainWindow {

    public static void main(String[] args) {
        // Lance l'application Swing sur le Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MyWindow1 myAppWindow = new MyWindow1();
            myAppWindow.setVisible(true);
            // Toutes les initialisations et la gestion des vues sont faites à l'intérieur de MyWindow1
        });
    }
}