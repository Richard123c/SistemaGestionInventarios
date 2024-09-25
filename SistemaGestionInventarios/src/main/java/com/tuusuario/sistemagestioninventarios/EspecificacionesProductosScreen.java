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
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class EspecificacionesProductosScreen extends JFrame{
    
    private JList<String> listaEspecificaciones;
    private DefaultListModel<String> modeloEspecificaciones;
    private JTextField txtNombreEspecificacion;
    private JTextArea txtDescripcionEspecificacion;
    private JComboBox<String> comboTipoDato;
    
    public EspecificacionesProductosScreen() {
        txtNombreEspecificacion = new JTextField(20);
        txtDescripcionEspecificacion = new JTextArea(5, 20);
        comboTipoDato = new JComboBox<>(new String[] {"Texto", "Número", "Fecha"});
        setTitle("Especificaciones de productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible (true);
        
        JPanel panelCampos = new JPanel(new GridLayout(3, 2));
        panelCampos.add(new JLabel("Nombre de Especificación:"));
        panelCampos.add(txtNombreEspecificacion);
        panelCampos.add(new JLabel("Descripción de Especificación:"));
        panelCampos.add(new JScrollPane(txtDescripcionEspecificacion));  // Scroll para el JTextArea
        panelCampos.add(new JLabel("Tipo de Dato:"));
        panelCampos.add(comboTipoDato);
        
        JPanel panelEspecificaciones = new JPanel();
        panelEspecificaciones.setLayout(new BorderLayout());
        panelEspecificaciones.setBorder(BorderFactory.createTitledBorder("Especificaciones de Productos"));
        
        modeloEspecificaciones = new DefaultListModel<>();
        listaEspecificaciones = new JList<>(modeloEspecificaciones);
        JScrollPane scrollEspecificaciones = new JScrollPane(listaEspecificaciones);
        panelEspecificaciones.add(scrollEspecificaciones, BorderLayout.CENTER);
        
        JPanel panelBotonesEspecificaciones = new JPanel();
        panelBotonesEspecificaciones.setLayout(new GridLayout(1, 3, 10, 0));
        
        JButton btnAgregarEspecificacion = new JButton("Agregar");
        JButton btnEliminarEspecificacion = new JButton("Eliminar");
        JButton btnModificarEspecificacion = new JButton("Modificar");
        
        panelBotonesEspecificaciones.add(btnAgregarEspecificacion);
        panelBotonesEspecificaciones.add(btnEliminarEspecificacion);
        panelBotonesEspecificaciones.add(btnModificarEspecificacion);
        
        panelEspecificaciones.add(panelBotonesEspecificaciones, BorderLayout.SOUTH);
        
        add(panelEspecificaciones, BorderLayout.CENTER);
        
        cargarEspecificacionesDesdeArchivo();
        
        btnAgregarEspecificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JTextField nombreField = new JTextField();
        JTextArea descripcionField = new JTextArea(5, 20);
        JComboBox<String> tipoDatoCombo = new JComboBox<>(new String[]{"Texto", "Número", "Fecha"});

        // Agrupar los campos en un cuadro de diálogo
        Object[] message = {
            "Nombre de la Especificación:", nombreField,
            "Descripción (opcional):", new JScrollPane(descripcionField),
            "Tipo de Dato:", tipoDatoCombo
        };

        // Mostrar el cuadro de diálogo para agregar la especificación
        int option = JOptionPane.showConfirmDialog(null, message, "Agregar nueva especificación", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String tipoDato = (String) tipoDatoCombo.getSelectedItem();

            // Validar que el nombre no esté vacío
            if (!nombre.isEmpty()) {
                if (!validarEspecificacionUnica(nombre)) {
                    JOptionPane.showMessageDialog(null, "Ya existe una especificación con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Formato de especificación con tipo de dato
                String especificacion = nombre + " - " + descripcion + " - " + tipoDato;
                modeloEspecificaciones.addElement(especificacion);

                // Guardar la especificación en el archivo
                guardarEspecificacionEnArchivo(nombre, descripcion, tipoDato);
                JOptionPane.showMessageDialog(null, "Especificación agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "El nombre de la especificación es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
            }
        });
        
        btnEliminarEspecificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String especificacionSeleccionada = listaEspecificaciones.getSelectedValue();
                
                if (especificacionSeleccionada != null) {
                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            "¿Estas seguro de que deseas eliminar la especificacion " + especificacionSeleccionada + "?",
                            "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
                    
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        eliminarEspecificacionEnArchivo(especificacionSeleccionada);
                        modeloEspecificaciones.removeElement(especificacionSeleccionada);
                        JOptionPane.showMessageDialog(null, "Especificacion eliminada exitosamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona una especificacion para eliminar.");
                }
            }
        });
        
        btnModificarEspecificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaEspecificaciones.getSelectedIndex();
                
                if (selectedIndex != -1) {
            // Obtener la especificación seleccionada y dividirla en partes
            String especificacionSeleccionada = modeloEspecificaciones.get(selectedIndex);
            String[] partes = especificacionSeleccionada.split(" - ");

            // Inicializar los valores actuales
            String nombreActual = partes[0];
            String descripcionActual = partes.length > 1 ? partes[1] : "";
            String tipoDatoActual = partes.length > 2 ? partes[2] : "Texto";

            // Crear campos de entrada con los valores actuales
            JTextField nombreField = new JTextField(nombreActual);
            JTextArea descripcionField = new JTextArea(descripcionActual, 5, 20);
            JComboBox<String> tipoDatoCombo = new JComboBox<>(new String[]{"Texto", "Número", "Fecha"});
            tipoDatoCombo.setSelectedItem(tipoDatoActual);  // Seleccionar el tipo de dato actual

            // Agrupar los campos en un cuadro de diálogo
            Object[] message = {
                "Nombre de la Especificación:", nombreField,
                "Descripción (opcional):", new JScrollPane(descripcionField),
                "Tipo de Dato:", tipoDatoCombo
            };

            // Mostrar el cuadro de diálogo para modificar la especificación
            int option = JOptionPane.showConfirmDialog(null, message, "Modificar Especificación", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String nuevoNombre = nombreField.getText().trim();
                String nuevaDescripcion = descripcionField.getText().trim();
                String nuevoTipoDato = (String) tipoDatoCombo.getSelectedItem();

                // Validar que el nuevo nombre no esté vacío
                if (!nuevoNombre.isEmpty()) {
                    if (!nuevoNombre.equalsIgnoreCase(nombreActual) && !validarEspecificacionUnica(nuevoNombre)) {
                        JOptionPane.showMessageDialog(null, "Ya existe una especificación con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Actualizar la especificación en la lista visual
                    String nuevaEspecificacion = nuevoNombre + " - " + nuevaDescripcion + " - " + nuevoTipoDato;
                    modeloEspecificaciones.set(selectedIndex, nuevaEspecificacion);

                    // Actualizar la especificación en el archivo
                    modificarEspecificacionEnArchivo(nombreActual, nuevoNombre, nuevaDescripcion, nuevoTipoDato);
                    JOptionPane.showMessageDialog(null, "Especificación modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre de la especificación es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una especificación para modificar.", "Modificar Especificación", JOptionPane.WARNING_MESSAGE);
        }
            }
        });
    }
    
    private void cargarEspecificacionesDesdeArchivo() {
        String archivoEspecificaciones = "especificaciones.txt";
        String archivoTemporal = "especificaciones_temp.txt";
        File archivo = new File(archivoEspecificaciones);
        
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            }catch (IOException e) {
                JOptionPane.showMessageDialog(this, "No se pudo crear el archivo de especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } 
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    modeloEspecificaciones.addElement(linea);
                }
        }catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las especificaciones desde el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarEspecificacion() {
        String nombreEspecificacion = txtNombreEspecificacion.getText().trim();
        String descripcionEspecificacion = txtDescripcionEspecificacion.getText().trim();
        String tipoDato = comboTipoDato.getSelectedItem().toString();
        
        
        if (nombreEspecificacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error al agregar una nueva especificacion.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validarEspecificacionUnica(nombreEspecificacion)) {
            JOptionPane.showMessageDialog(this, "Ya existe una especificacion con ese nombre.", "Advertencia", JOptionPane.WARNING_MESSAGE);
              return;
        }
        
        guardarEspecificacionEnArchivo(nombreEspecificacion, descripcionEspecificacion, tipoDato);
        modeloEspecificaciones.addElement(nombreEspecificacion);  // Añadir a la lista visualmente
        JOptionPane.showMessageDialog(this, "Especificación guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }
    
    private void limpiarCampos() {
         txtNombreEspecificacion.setText("");
         txtDescripcionEspecificacion.setText("");
         comboTipoDato.setSelectedIndex(0);
    }
    
    private void guardarEspecificacionEnArchivo(String nombre, String descripcion, String tipoDato) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("especificaciones.txt", true))) {
            writer.write(nombre + "|" + descripcion + "|" + tipoDato);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la especificacion en el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarEspecificacionUnica(String nombre) {
        try (BufferedReader reader = new BufferedReader(new FileReader("especificaciones.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String nombreExistente = partes[0];
                if (nombreExistente.equalsIgnoreCase(nombre)) {
                    return false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de especificaciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
    
    private void modificarEspecificacionEnArchivo(String nombreOriginal, String nombreNuevo, String descripcionNueva, String tipoDatoNuevo) {
        File archivo = new File("especificaciones.txt");
        File archivoTemporal = new File("especificaciones_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String nombreExistente = partes[0];
                
                if (nombreExistente.equals(nombreOriginal)) {
                    writer.write(nombreNuevo + "|" + descripcionNueva + "|" + tipoDatoNuevo);
                } else {
                    writer.write(linea);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar la especificacion en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!archivo.delete()) {
            JOptionPane.showMessageDialog(this, "Error  al eliminar el archivo original.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (!archivo.delete()) {
            JOptionPane.showMessageDialog(this, "Error  al reemplazar el archivo temporal.", "Error", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    private void modificarEspecificacion() {
        String nombreNuevo = txtNombreEspecificacion.getText().trim();
        String descripcionNueva = txtDescripcionEspecificacion.getText().trim();
        String tipoDatoNuevo = comboTipoDato.getSelectedItem().toString();
        
        if (nombreNuevo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String especificacionOriginal = listaEspecificaciones.getSelectedValue();
        String[] partesOriginal = especificacionOriginal.split("\\|");
        String nombreOriginal = partesOriginal[0];
        
        if (!nombreNuevo.equals(nombreOriginal) && !validarEspecificacionUnica(nombreNuevo)) {
            JOptionPane.showMessageDialog(this, "Ya existe una especificacion con ese nombre.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modificarEspecificacionEnArchivo(nombreOriginal, nombreNuevo, descripcionNueva, tipoDatoNuevo);
        
        JOptionPane.showMessageDialog(this, "Especificacion modificada exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        
        modeloEspecificaciones.clear();
        cargarEspecificacionesDesdeArchivo();
    }
    
    private void eliminarEspecificacionEnArchivo(String nombreEspecificacion) {
        File archivo = new File("especificaciones.txt");
        File archivoTemporal = new File("especificaciones_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String nombreExistente = partes[0];
                
                if (!nombreExistente.equals(nombreEspecificacion)) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la especificacion en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (archivo.delete()) {
            archivoTemporal.renameTo(archivo);
        } else {
            JOptionPane.showMessageDialog(this, "Error al reemplazar el archivo original.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarAsociacionesDeEspecificacion(String especificacion) {
        File archivoProductos = new File("productos.txt");
        File archivoTemporal = new File("productos_temp.txt");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoProductos));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                String producto = partes[0];
                String especificaciones = partes[1];
                
                String[] listaEspecificaciones = especificaciones.split(",");
                
                List<String> nuevasEspecificaciones = new ArrayList<String>();
                for (String espec : listaEspecificaciones) {
                    if (!espec.equals(especificacion)) {
                        nuevasEspecificaciones.add(espec);
                    }
                }
                // Si la lista de especificaciones no está vacía, reescribirla
            if (!nuevasEspecificaciones.isEmpty()) {
                writer.write(producto + "|" + String.join(",", nuevasEspecificaciones));
                writer.newLine();
   
            } else {
                writer.write(producto + "|");
                writer.newLine();
            }
        } 
    } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error al eliminar las asociaciones de especificaciones", "Error", JOptionPane.ERROR_MESSAGE);
    }
        if (archivoProductos.delete()) {
        boolean exito = archivoTemporal.renameTo(archivoProductos);
        if (!exito) {
            JOptionPane.showMessageDialog(null, "Error al renombrar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Error al eliminar el archivo original", "Error", JOptionPane.ERROR_MESSAGE);
    }
        archivoProductos.delete();
        archivoTemporal.renameTo(archivoProductos);
          
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EspecificacionesProductosScreen().setVisible(true);
            }
        });
    }
}
