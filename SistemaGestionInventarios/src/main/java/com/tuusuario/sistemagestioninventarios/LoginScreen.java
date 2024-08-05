/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Lenovo
 */
public class LoginScreen extends JFrame{
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    
    public LoginScreen(){
        // Configurar el JFrame
        setTitle("Sistema de gestion de inventarios - Inicio de sesion");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Crea un panel de fondo
        JPanel backgroundPanel = new JPanel(){
            protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             ImageIcon icon = new ImageIcon("src/main/java/com/tuusuario/sistemagestioninventarios/DiseñoPaisaje.jpg");
             g.drawImage(icon.getImage(), 0, 0, getWidth(),getHeight(),this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Crear el panel de login
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false); // Hacer el panel transparente
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
         // Estilizar campos de texto
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        userField.setBackground(Color.WHITE);
        passField.setBackground(Color.WHITE);
        userField.setForeground(Color.BLACK);
        passField.setForeground(Color.BLACK);
        userField.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        passField.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        
        // Añadir campos de usuario y contraseña
        JLabel userLabel = new JLabel("Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);
        
        userField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(userField, gbc);
        
         JLabel passLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passLabel, gbc);
        
        passField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passField, gbc);

        // Añadir botón de inicio de sesión
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(new Color(255, 255, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Añadir botón de inicio de sesión
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(loginButton, gbc);

        // Añadir el panel de login al panel de fondo
        backgroundPanel.add(loginPanel);

        // Añadir el panel de fondo al JFrame
        add(backgroundPanel);

        // Configurar la acción del botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes añadir la lógica de autenticación
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (username.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Inicio de sesión exitoso");
                     // Abrir la pantalla del sistema de inventarios
                    new SistemaInventariosScreen().setVisible(true);
                    // Cerrar la pantalla de login actual
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Usuario o contraseña incorrectos");
                }
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
    
}
