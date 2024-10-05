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
public class GestionEspecificacionesNumericas extends JFrame{
    
    private  JTextField txtValor;
    private JComboBox<String> comboUnidades;
    private DefaultListModel<String> modeloEspecificaciones;
    private JList<String> listaEspecificaciones;
    
    public GestionEspecificacionesNumericas() {
        // Lista de unidades predefinidas
        String[] unidades = {"Kg", "g", "cm", "m", "litros"};
      
        // Panel para las especificaciones numéricas
        modeloEspecificaciones = new DefaultListModel<>();
        listaEspecificaciones = new JList<>(modeloEspecificaciones);
        JScrollPane scrollPane = new JScrollPane(listaEspecificaciones);
        
        //Campo de texto para valor numerico
        txtValor = new JTextField(10);
        
        comboUnidades = new JComboBox<>(unidades);
        
        JButton btnAgregarEspecificacion = new JButton("Agregar Especificacion");
        btnAgregarEspecificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEspecificacion = JOptionPane.showInputDialog("Ingrese el nombre de la especificacion:");
                double valor = Double.parseDouble(txtValor.getText().trim());
                String unidad = comboUnidades.getSelectedItem().toString();
                
                EspecificacionNumerica especificacion = new EspecificacionNumerica(nombreEspecificacion, valor, unidad);
                modeloEspecificaciones.addElement(especificacion.toString());
            }
        });
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelAgregar = new JPanel();
        panelAgregar.add(new JLabel("Valor"));
        panelAgregar.add(txtValor);
        panelAgregar.add(new JLabel("Unidad"));
        panelAgregar.add(comboUnidades);
        panelAgregar.add(btnAgregarEspecificacion);
        
        add(panelAgregar, BorderLayout.SOUTH);
        
        // Configuración de la ventana
        setTitle("Gestion de Especificaciones Numericas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionEspecificacionesNumericas::new);
    }
}
