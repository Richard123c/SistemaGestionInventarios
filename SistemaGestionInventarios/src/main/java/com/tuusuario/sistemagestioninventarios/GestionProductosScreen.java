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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class GestionProductosScreen extends JFrame{
    private JList<String> listaCategorias;
    private DefaultListModel<String> modeloCategorias;
    private JButton btnGuardarCategoria;
    
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
        btnGuardarCategoria = new JButton("Guardar");
        
        panelBotonesCategorias.add(btnAgregarCategoria);
        panelBotonesCategorias.add(btnEliminarCategoria);
        panelBotonesCategorias.add(btnModificarCategoria);
        panelBotonesCategorias.add(btnGuardarCategoria);

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
                    eliminarCategoria();
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
        
        btnGuardarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCategoriasEnArchivo();
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
    
    private void eliminarCategoria(){
        int selectedIndex = listaCategorias.getSelectedIndex();
        if  (selectedIndex != -1) {
            //Muestra cuadro de dialogo de confirmacion
            int confirm = JOptionPane.showConfirmDialog(this, 
      "¿Estas seguro de que deseas eliminar esta categoria?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
            
            //Si el usuario confirma la eliminacion
            if (confirm == JOptionPane.YES_OPTION) {
                String categoriaAEliminar = modeloCategorias.get(selectedIndex);
                modeloCategorias.remove(selectedIndex);
                //Guardar cambios en el archivo despues de eliminar
                eliminarCategoriaDelArchivo(categoriaAEliminar);
            }
        } else {
            //Mostrar mensaje de advertencia si no hay una categoria seleccionada
            JOptionPane.showMessageDialog(this, 
      "Por favor, selecciona una categoria para eliminar.", "Eliminar categoria", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarCategoriaDelArchivo(String categoriaAEliminar){
        File archivo = new File("categorias.txt");
        File archivoTemporal = new File("categorias_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.split("\\|")[0].trim().equals(categoriaAEliminar.split(" - ")[0].trim())){
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la categoria del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        //Reemplazar el archivo original con el archivo temporal actualizado
        if (archivo.delete()) {
            archivoTemporal.renameTo(archivo);
        } else {
            JOptionPane.showMessageDialog( this, "Error al actualizar el archivo de categorias,", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarCategoria(){
        int selectedIndex = listaCategorias.getSelectedIndex();
        if (selectedIndex != -1) {
            String categoriaSeleccionada = modeloCategorias.get(selectedIndex);
            String[] partes = categoriaSeleccionada.split(" - ");
            String nombreActual = partes[0];
            String descripcionActual = partes.length > 1 ? partes[1] : "";
            
            JTextField nombreField = new JTextField(nombreActual, 10);
            JTextArea descripcionArea = new JTextArea(descripcionActual, 5, 20);
            descripcionArea.setLineWrap(true);
            descripcionArea.setWrapStyleWord(true);
            
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("Nombre de la categoria:"), BorderLayout.NORTH);
            panel.add(nombreField, BorderLayout.CENTER);
            panel.add(new JLabel("Descripcion (opcional):"), BorderLayout.SOUTH);
            panel.add(new JScrollPane(descripcionArea), BorderLayout.SOUTH);
            
            int result = JOptionPane.showConfirmDialog(null, panel, "Modificar categoria", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String nuevoNombre = nombreField.getText().trim();
                String nuevaDescripcion = descripcionArea.getText () .trim(); 
                
                //Validar que el nuevo nombre no este vacio
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre de la categoria no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que el nuevo nombre no exista ya en la lista de categorías
                for (int i = 0; i < modeloCategorias.getSize(); i++) {
                    if (i == selectedIndex) continue;
                    String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
                    if (categoriaExistente.equalsIgnoreCase(nuevoNombre)) {
                        JOptionPane.showMessageDialog(this, "Ya existe una categoría con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
            }
                
                // Modificar la categoría
                modeloCategorias.set(selectedIndex, nuevoNombre + (nuevaDescripcion.isEmpty() ? "" : " - " + nuevaDescripcion));

                // Guardar las categorías actualizadas en el archivo
                guardarCategoriasActualizadasEnArchivo();  
            }
        }
    }
            
            
    private void guardarCategoriasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt", true))) {
            for (int i = 0; i < modeloCategorias.getSize(); i++) {
                String categoria = modeloCategorias.get(i);
                String[] partes = categoria.split(" - ");
                String nombre = partes[0];
                String descripcion = partes.length > 1 ? partes[1] : "";
                writer.write(nombre + "|" + descripcion);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Categorías guardadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar las categorías en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Metodo para verificar si una categoria ya existe en el modelo
    private boolean categoriaYaExiste(String nombreCategoria) {
        for (int i = 0; i < modeloCategorias.getSize(); i++) {
            String categoriaExistente = modeloCategorias.get(i).split(" - ")[0];
            if (categoriaExistente.equalsIgnoreCase(nombreCategoria)) {
                return true;
            }
        }
        return false;
    }
    
    private void guardarCategoriasActualizadasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt",true))) {
            for (int i = 0; i < modeloCategorias.getSize(); i++) {
                String categoria = modeloCategorias.get(i);
                String[] partes = categoria.split(" - ");
                String nombre = partes[0];
                String descripcion = partes.length > 1 ? partes[1] : "";
                writer.write(nombre + "|" + descripcion);
                writer.newLine();  
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la categorias en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
           
        }
    }
    
    private void cargarCategoriasDesdeArchivo(){
        try (BufferedReader reader = new BufferedReader(new FileReader("categorias.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\|");
            String nombre = partes[0];
            String descripcion = partes.length > 1 ? partes[1] : "";
            modeloCategorias.addElement(nombre + (descripcion.isEmpty() ? "" : " - " + descripcion));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las categorias desde el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
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
