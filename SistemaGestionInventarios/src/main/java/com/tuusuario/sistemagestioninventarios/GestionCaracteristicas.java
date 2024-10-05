/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Lenovo
 */
public class GestionCaracteristicas extends JFrame{
    
    private DefaultListModel<String> modeloValores;
    private JList<String> listaValores;
    private JTextField txtNuevoValor;
    private Caracteristica caracteristica;
    
    public GestionCaracteristicas() {
        // Inicializa la característica con un ejemplo, "Color"
        caracteristica = new Caracteristica("Color");
        
        modeloValores = new DefaultListModel<>();
        listaValores = new JList<>(modeloValores);
        JScrollPane scrollPane = new JScrollPane(listaValores);
        
        txtNuevoValor = new JTextField(10);
        JButton btnAgregarValor = new JButton("Agregar Valor");
        
        btnAgregarValor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoValor = txtNuevoValor.getText().trim();
                if (!nuevoValor.isEmpty()) {
                    caracteristica.agregarValor(nuevoValor);
                    modeloValores.addElement(nuevoValor);
                    txtNuevoValor.setText("");
                }
            }
        });
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelAgregar = new JPanel();
        panelAgregar.add(txtNuevoValor);
        panelAgregar.add(btnAgregarValor);
        
        add(panelAgregar, BorderLayout.SOUTH);
        
        setTitle("Gestion de Caracteristicas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionCaracteristicas::new);
    }
}
