/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuusuario.sistemagestioninventarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Lenovo
 */
public class CaracteristicasProductosScreen extends JFrame {
    
    private JList<String> listaCaracteristicas;
    private DefaultListModel<String> modeloCaracteristicas;
    private JTextField txtNombreCaracteristica;
    private JTextArea txtDescripcionCaracteristica;
    
    
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
        JButton btnEliminarCaracteristica = new JButton("Eliminar");
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
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            agregarCaracteristica(); // Llamamos al método que guarda la característica
        }
   });
        
    }
    
    private void agregarCaracteristica() {
        JTextField nombreField = new JTextField (10);
        txtNombreCaracteristica = new JTextField(20); 
        JTextArea descripcionArea = new JTextArea (5, 20);
        txtDescripcionCaracteristica = new JTextArea(5,20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        
        
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Nombre de la caracteristica:"), BorderLayout.NORTH);
        panel.add(nombreField, BorderLayout.CENTER);
        panel.add(new JLabel("Descripcion (opcional):"), BorderLayout.SOUTH);
        panel.add(new JScrollPane(descripcionArea), BorderLayout.SOUTH);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar nueva caracteristica", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION){
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionArea.getText().trim();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la caracteristica no puede estar vacio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 
            
            for (int i = 0; i < modeloCaracteristicas.getSize(); i++) {
                String caracteristicaExistente = modeloCaracteristicas.get(i).split(" - ") [0];
                if (caracteristicaExistente.equalsIgnoreCase(nombre)) {
                    JOptionPane.showMessageDialog(this, "Ya existe una caracteristica con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        
        //Agrega la nueva caracteristica
        modeloCaracteristicas.addElement(nombre + (descripcion.isEmpty() ? "" : " - " + descripcion));
        
        guardarCaracteristicaEnArchivo(nombre, descripcion);
        }
    }
    
    private void guardarCaracteristicaEnArchivo(String nombre, String descripcion) {
        try {BufferedWriter writer = new BufferedWriter(new FileWriter("caracteristicas.txt", true));
            writer.write(nombre + "|" + descripcion);
            writer.newLine();
            writer.close ();
            
            JOptionPane.showMessageDialog(this, "Característica guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la caracteristica en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCaracteristicaUnica(String nombre) {
        try (BufferedReader reader = new BufferedReader (new FileReader ("caracteristicas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length > 0) {
                    String nombreExistente = partes[0].trim();
                    if (nombreExistente.equalsIgnoreCase(nombre.trim())){
                        
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de caracteristicas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
    
    private void cargarCaracteristicasDesdeArchivo(){
        try (BufferedReader reader = new BufferedReader (new FileReader ("caracteristicas.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\|");
            if (partes.length ==2) {
                String nombre = partes[0];
                String descripcion = partes[1];
                modeloCaracteristicas.addElement(nombre + " - " + descripcion);
            }
          } 
        }catch (IOException e) {
             JOptionPane.showMessageDialog(this, "Error al leer el archivo de características.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarCaracteristica() {
        int selectedIndex = listaCaracteristicas.getSelectedIndex();
        if (selectedIndex != -1) {
            String caracteristicaSeleccionada = listaCaracteristicas.getSelectedValue();
            String[] partes = caracteristicaSeleccionada.split(" - ");
            
            String nombreOriginal = partes[0];
            String descripcionOriginal = partes[1];
            
            String nuevoNombre = txtNombreCaracteristica.getText();
            String nuevaDescripcion = txtDescripcionCaracteristica.getText(); 
            
            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la característica no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
                
            } else if (!validarNuevoNombreUnico(nuevoNombre, nombreOriginal)){
                 JOptionPane.showMessageDialog(this, "Ya existe una característica con ese nombre.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
                
            } else {
                modificarCaracteristicaEnArchivo(nombreOriginal, nuevoNombre, nuevaDescripcion);
                cargarCaracteristicasDesdeArchivo();
                JOptionPane.showMessageDialog(this, "Característica modificada exitosamente.", "Exito", JOptionPane.WARNING_MESSAGE);
                
            }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona una característica para modificar.", "Exito", JOptionPane.WARNING_MESSAGE);  
                  
        }
    }
    
    private void modificarCaracteristicaEnArchivo(String nombreOriginal, String nuevoNombre, String nuevaDescripcion) {
         File archivoOriginal = new File("caracteristicas.txt");
         File archivoTemporal = new File("caracteristicas_temp.txt");
         
         try (BufferedReader reader = new BufferedReader(new FileReader(archivoOriginal));
              BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
             
             String linea;
             
            while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split("\\|");
            String nombreExistente = partes[0];
             
              // Si se encuentra la característica que se va a modificar
            if (nombreExistente.equalsIgnoreCase(nombreOriginal)) {
                //Escribe la linea con el nuevo nombre y descripcion
                writer.write(nuevoNombre + "|" + nuevaDescripcion);
            } else {
                
                writer.write(linea);
            }
            writer.newLine();
         }
         } catch (IOException e) {
             JOptionPane.showMessageDialog(this, "Error al modificar la característica en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         
         if (archivoOriginal.delete()) {
             archivoTemporal.renameTo(archivoOriginal);
         } else {
             JOptionPane.showMessageDialog(this, "Error al reemplazar el archivo original.", "Error", JOptionPane.ERROR_MESSAGE);
         }
    }
    
    private boolean validarNuevoNombreUnico(String nuevoNombre, String nombreOriginal) {
        try (BufferedReader reader = new BufferedReader(new FileReader("caracteristicas.txt"))) {
            String linea;
            
            while((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                
                if (partes[0].equalsIgnoreCase(nuevoNombre) && !partes[0].equalsIgnoreCase(nombreOriginal)) {
                    return false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de caractersticas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
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
