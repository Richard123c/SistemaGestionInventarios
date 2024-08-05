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
public class SistemaInventariosScreen extends JFrame{
         
    private JButton gestionProductosButton;
    private JButton controlExistenciasButton;
    private JButton pedidosCompraButton;
    private JButton informesEstadisticasButton;
    
    public SistemaInventariosScreen() {
        //Configura el JFRAME
        setTitle("Sistema de Gestion de Inventarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Crear un panel para los botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        
        //Crear y añadir botones al panel
        gestionProductosButton = new JButton("Gestión de Productos");
        controlExistenciasButton = new JButton("Control de Existencias");
        pedidosCompraButton = new JButton("Pedidos de Compra");
        informesEstadisticasButton = new JButton("Informes y Estadísticas");
        
        panel.add(gestionProductosButton);
        panel.add(controlExistenciasButton);
        panel.add(pedidosCompraButton);
        panel.add(informesEstadisticasButton);
        
        // Añadir el panel al JFrame
        add(panel);

        // Configurar acciones de los botones
        gestionProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para Gestión de Productos
                JOptionPane.showMessageDialog(SistemaInventariosScreen.this, "Gestión de Productos seleccionada");
                // Aquí puedes abrir una nueva ventana para Gestión de Productos
            }
        });

        controlExistenciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para Control de Existencias
                JOptionPane.showMessageDialog(SistemaInventariosScreen.this, "Control de Existencias seleccionado");
                // Aquí puedes abrir una nueva ventana para Control de Existencias
            }
        });

        pedidosCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para Pedidos de Compra
                JOptionPane.showMessageDialog(SistemaInventariosScreen.this, "Pedidos de Compra seleccionados");
                // Aquí puedes abrir una nueva ventana para Pedidos de Compra
            }
        });

        informesEstadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para Informes y Estadísticas
                JOptionPane.showMessageDialog(SistemaInventariosScreen.this, "Informes y Estadísticas seleccionados");
                // Aquí puedes abrir una nueva ventana para Informes y Estadísticas
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaInventariosScreen().setVisible(true);
            }
        });
        
    }
}
    

