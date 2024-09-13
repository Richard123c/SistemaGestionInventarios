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
public class CaracteristicasProductosScreen extends JFrame {
    
    private JList<String> listaCaracteristicas;
    private DefaultListModel<String> modeloCaracteristicas;
    
    public CaracteristicasProductosScreen() {
        setTitle("Caracteristicas de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //Panel Principal para gestionar las caracteristicas
        JPanel panelCaracteristicas = new JPanel();
        panelCaracteristicas.setBorder(BorderFactory.createTitledBorder("Caracteristicas de Productos"));
        
        //Modelo para la lista de caracteristicas
        modeloCaracteristicas = new DefaultListModel<>();
        listaCaracteristicas = new JList<>(modeloCaracteristicas);
        JScrollPane scrollCaracteristicas = new JScrollPane(listaCaracteristicas);
        panelCaracteristicas.add(scrollCaracteristicas, BorderLayout.CENTER);
                
        //Campos de textos, listas o tablas para las caracteristicas
        add(panelCaracteristicas, BorderLayout.CENTER);
        
        //Botones para guardar o cancelar
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        //Cancela el cierre de pagina
        btnCancelar.addActionListener(e -> dispose());
        
        JPanel panelBotonesCaracteristicas = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton btnAgregarCaracteristica = new JButton("Agregar");
        JButton btnEliminarCaracteristica = new JButton("Elimnar");
        JButton btnModificarCaracteristica = new JButton("Modificar");
        
        panelBotonesCaracteristicas.add(btnAgregarCaracteristica);
        panelBotonesCaracteristicas.add(btnEliminarCaracteristica);
        panelBotonesCaracteristicas.add(btnModificarCaracteristica);
        
        panelCaracteristicas.add(panelBotonesCaracteristicas, BorderLayout.SOUTH);
        
        add(panelCaracteristicas, BorderLayout.CENTER);
        
        btnAgregarCaracteristica.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent e) {
                  JTextField nombreField = new JTextField();
                  JTextField descripcionField = new JTextField();
                  Object[] message = {"Nombre de la caracteristica:", nombreField,
                      "Descripcion (opcional):", descripcionField};
                  
                  int option = JOptionPane.showConfirmDialog(null, message, "Agregar nueva caracteristica", JOptionPane.OK_CANCEL_OPTION);
                  
                  if (option == JOptionPane.OK_OPTION) {
                      String nombre = nombreField.getText().trim();
                      String descripcion = descripcionField.getText().trim();
                      
                      if (!nombre.isEmpty()) {
                          //Si la descripcion esta vacia, solo se agrega el nombre
                          String caracteristica = nombre;
                          if (!descripcion.isEmpty()) {
                              caracteristica += " - " + descripcion;
                          }
                          modeloCaracteristicas.addElement(caracteristica);
                      }else {
                          JOptionPane.showMessageDialog(null, "El nombre de la característica es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                      }
                  }
              }
        });
        
        btnEliminarCaracteristica.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCaracteristicas.getSelectedIndex();
                if (selectedIndex != 1) {
                    modeloCaracteristicas.remove(selectedIndex);
                }else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una caracteristica para eliminar.", "Eliminar caracteristica", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        btnModificarCaracteristica.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCaracteristicas.getSelectedIndex();
                if (selectedIndex != -1) {
                    String nuevaCaracteristica = JOptionPane.showInputDialog("Modificar caracteristica:", modeloCaracteristicas.get(selectedIndex));
                    if (nuevaCaracteristica != null && !nuevaCaracteristica.trim().isEmpty()){
                    modeloCaracteristicas.set(selectedIndex, nuevaCaracteristica.trim());
                }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una caracteristica para modificar.", "Modificar caracteristica", JOptionPane.WARNING_MESSAGE);
                }
            }
        }) ;       
    }
        
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run () {
                   new CaracteristicasProductosScreen().setVisible(true);
               }
            });
        }
    
}
