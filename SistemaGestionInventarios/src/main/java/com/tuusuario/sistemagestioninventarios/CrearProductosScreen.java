/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class CrearProductosScreen extends JFrame {
    
    private JTextField txtNombreProducto;
    private JComboBox<String> comboCategorias;
    private DefaultComboBoxModel<String> modeloCategorias;
    
    private JList<String> listaCaracteristicas;
    private DefaultListModel<String> modeloCaracteristicas;
    
    private JList<String> listaEspecificaciones;
    private DefaultListModel<String> modeloEspecificaciones;
    
    public CrearProductosScreen() {
        setTitle("Crear nuevo producto");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Campo para el nombre del producto
        txtNombreProducto = new JTextField(20);
        
        // JComboBox para seleccionar la categoría
        modeloCategorias = new DefaultComboBoxModel<>();
        comboCategorias = new JComboBox<>(modeloCategorias);
        
        cargarCategoriasDesdeArchivo(); // Método que carga las categorías
        cargarCaracteristicasDesdeArchivo();
        cargarEspecificacionesDesdeArchivo();
        
        modeloCaracteristicas = new DefaultListModel<>();
        listaCaracteristicas = new JList<>(modeloCaracteristicas);
        listaCaracteristicas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        modeloEspecificaciones = new DefaultListModel<>();
        listaEspecificaciones = new JList<>(modeloEspecificaciones);
        listaEspecificaciones.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JPanel panelCampos = new JPanel(new GridLayout(2,2));
        panelCampos.add(new JLabel("Nombre del Producto:"));
        panelCampos.add(txtNombreProducto);
        panelCampos.add(new JLabel("Categoria:"));
        panelCampos.add(comboCategorias);
        panelCampos.add(new JLabel("Caracteristicas:"));
        panelCampos.add(listaCaracteristicas);
        panelCampos.add(new JLabel("Especificaciones:"));
        panelCampos.add(listaEspecificaciones);
        
        JButton btnGuardarProducto = new JButton("Guardar Producto");
        btnGuardarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        
        add(panelCampos, BorderLayout.CENTER);
        add(btnGuardarProducto, BorderLayout.SOUTH);
    }
    
    // Método que carga las categorías desde un archivo de texto
    private void cargarCategoriasDesdeArchivo(){
        String archivoCategorias = "categorias.txt"; // Archivo que contiene las categorías
        File archivo = new File(archivoCategorias);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de categorias no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloCategorias.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las categorias.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }
    
    private void cargarCaracteristicasDesdeArchivo(){
        String archivoCaracteristicas = "caracteristicas.txt";
        File archivo = new File(archivoCaracteristicas);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de características no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloCaracteristicas.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las caracteristicas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }
    
    private void cargarEspecificacionesDesdeArchivo(){
        String archivoEspecificaciones = "especificaciones.txt";
        File archivo = new File(archivoEspecificaciones);
        if (!archivo.exists()) {
            JOptionPane.showConfirmDialog(this, "El archivo de especificaciones no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloEspecificaciones.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    private void guardarProducto() {
        String nombreProducto = txtNombreProducto.getText().trim();
        String categoriaSeleccionada = comboCategorias.getSelectedItem().toString();
        List<String> caracteristicasSeleccionadas = listaCaracteristicas.getSelectedValuesList();
        List<String> especificacionesSeleccionadas = listaEspecificaciones.getSelectedValuesList();
        
        if (nombreProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del producto no puede estar vacio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (caracteristicasSeleccionadas.isEmpty() || especificacionesSeleccionadas.isEmpty()) {
            JOptionPane.showConfirmDialog(this, "Debe seleccionar  al menos una caracteristica y una especificacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt", true))) {
            writer.write(nombreProducto + "|" + categoriaSeleccionada + "|" +
                    String.join(",", caracteristicasSeleccionadas) + "|" +
                    String.join(",", especificacionesSeleccionadas));
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Producto guardada exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el productos", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrearProductosScreen().setVisible(true);
            }
        });
    }
}
