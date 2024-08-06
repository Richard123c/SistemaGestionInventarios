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
public class GestionProductosScreen extends JFrame{
    private JList<String> listaCategorias;
    private DefaultListModel<String> modeloCategorias;
    
    public GestionProductosScreen(){
        //Configura el JFrame
        setTitle("Gestion de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //Panel de Categorias
        JPanel panelCategorias = new JPanel();
        panelCategorias.setLayout(new BorderLayout());
        panelCategorias.setBorder(BorderFactory.createTitledBorder("Categorías de Productos"));

        // Lista de categorías
        modeloCategorias = new DefaultListModel<>();
        listaCategorias = new JList<>(modeloCategorias);
        JScrollPane scrollCategorias = new JScrollPane(listaCategorias);
        panelCategorias.add(scrollCategorias, BorderLayout.CENTER);
        
        // Botones de categorías
        JPanel panelBotonesCategorias = new JPanel();
        panelBotonesCategorias.setLayout(new GridLayout(1, 3, 10, 0));

        JButton btnAgregarCategoria = new JButton("Agregar");
        JButton btnEliminarCategoria = new JButton("Eliminar");
        JButton btnModificarCategoria = new JButton("Modificar");
        
        panelBotonesCategorias.add(btnAgregarCategoria);
        panelBotonesCategorias.add(btnEliminarCategoria);
        panelBotonesCategorias.add(btnModificarCategoria);

        panelCategorias.add(panelBotonesCategorias, BorderLayout.SOUTH);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));

        // Aquí agregarás componentes adicionales para alta, baja, modificación, características y especificaciones

        // Añadir paneles al JFrame
        add(panelCategorias, BorderLayout.WEST);
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Agregar acciones a los botones
        btnAgregarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaCategoria = JOptionPane.showInputDialog("Ingrese el nombre de la nueva categoría:");
                if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                    modeloCategorias.addElement(nuevaCategoria.trim());
                }
            }
        });

        btnEliminarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCategorias.getSelectedIndex();
                if (selectedIndex != -1) {
                    modeloCategorias.remove(selectedIndex);
                }
            }
        });
        
        btnModificarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaCategorias.getSelectedIndex();
                if (selectedIndex != -1) {
                    String nuevaCategoria = JOptionPane.showInputDialog("Modificar categoría:", modeloCategorias.get(selectedIndex));
                    if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                        modeloCategorias.set(selectedIndex, nuevaCategoria.trim());
                    }
                }
            }
        });
    }
    
    private void agregarCategoria() {
        JTextField nombreField = new JTextField(10);
        JTextArea descripcionArea = new JTextArea(5, 20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Nombre de la categoría:"), BorderLayout.NORTH);
        panel.add(nombreField, BorderLayout.CENTER);
        panel.add(new JLabel("Descripción (opcional):"), BorderLayout.SOUTH);
        panel.add(new JScrollPane(descripcionArea), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nueva categoría", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        String nombre = nombreField.getText().trim();
        String descripcion = descripcionArea.getText().trim();

        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la categoría no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el nombre no exista ya en la lista de categorías
        for (int i = 0; i < modeloCategorias.getSize(); i++) {
            String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
            if (categoriaExistente.equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, "Ya existe una categoría con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Agregar la nueva categoría
        modeloCategorias.addElement(nombre + (descripcion.isEmpty() ? "" : " - " + descripcion));
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionProductosScreen().setVisible(true);
            }
        });
    }
    
}
