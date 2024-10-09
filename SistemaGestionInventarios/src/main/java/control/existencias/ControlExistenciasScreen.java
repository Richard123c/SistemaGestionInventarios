/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.existencias;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class ControlExistenciasScreen extends JFrame{
    
     public ControlExistenciasScreen() {
         setTitle("Control de Existencias - Registro de Entrada");
         setSize(400, 300);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setLayout(new BorderLayout());
         
         // Aquí irán los componentes para registrar entrada de productos
         JPanel panel = new JPanel();
         panel.setLayout(new GridLayout(3,2));
         
         JLabel lblCodigoProducto = new JLabel("Codigo del Producto");
         JTextField txtCodigoProducto = new JTextField();
         
         JLabel lblCantidad = new JLabel("Cantidad a ingresar:");
         JTextField txtCantidad = new JTextField();
         
         JButton btnRegistrar = new JButton("Registrar Entrada");
         
         panel.add(lblCodigoProducto);
         panel.add(txtCodigoProducto);
         panel.add(lblCantidad);
         panel.add(txtCantidad);
         panel.add(new JLabel("")); //Espacio vacio
         panel.add(btnRegistrar);
         
         add(panel, BorderLayout.CENTER);
         this.add(panel);
         this.pack();
         this.setLocationRelativeTo(null);
         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
          btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigoProducto = txtCodigoProducto.getText().trim();
                    int cantidadEntrada = Integer.parseInt(txtCantidad.getText().trim());
                    
                    if (codigoProducto.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar el codigo del producto.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (cantidadEntrada <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean productoEncontrado = false;
            List<String> lineasArchivo = new ArrayList<>();

            // Leer archivo de productos
            try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
                String linea;

                while ((linea = reader.readLine()) != null) {
                    String[] datosProducto = linea.split("\\|");

                    // Buscar producto por código
                    if (datosProducto[0].equals(codigoProducto)) {
                        int stockActual = Integer.parseInt(datosProducto[6]); // Asumiendo que el stock está en la posición 6
                        int nuevoStock = stockActual + cantidadEntrada;
                        datosProducto[6] = String.valueOf(nuevoStock); // Actualiza el stock

                        // Unir los datos actualizados en una sola línea
                        linea = String.join("|", datosProducto);
                        productoEncontrado = true;
                    }

                    lineasArchivo.add(linea); // Añade la línea, actualizada o no, a la lista temporal
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!productoEncontrado) {
                JOptionPane.showMessageDialog(null, "El producto con código " + codigoProducto + " no está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Escribir archivo actualizado
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
                for (String linea : lineasArchivo) {
                    writer.write(linea);
                    writer.newLine();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Stock actualizado exitosamente para el producto con código: " + codigoProducto);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }             
    });
         
     }
     
     private void registrarEntrada(String codigoProducto, int cantidadEntrada) {
         boolean productoEncontrado = false;
         List<String> lineasArchivo = new ArrayList<>();
         
         try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
             String linea;
             
             while ((linea = reader.readLine()) != null) {
                 String[] datosProducto = linea.split("\\|");
                 
                  // Verifica si el código del producto coincide
                  if (datosProducto.length > 6 && datosProducto[0].trim().equals(codigoProducto)) {
                      int stockActual = Integer.parseInt(datosProducto[6]);
                      int nuevoStock = stockActual + cantidadEntrada;
                      
                      // Actualiza el stock en el arreglo de datos del producto
                      datosProducto[6] = String.valueOf(nuevoStock);
                      
                      // Reconstruir la línea con los datos actualizados
                      linea = String.join("|", datosProducto);
                      productoEncontrado = true;
                  }
                  
                  // Agregar la línea (actualizada o no) a la lista
                  lineasArchivo.add(linea);
             }
         } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         
         // Si no se encontró el producto, mostrar un mensaje
         if (!productoEncontrado) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
             for (String linea : lineasArchivo) {
                 writer.write(linea);
                 writer.newLine();
             }
         } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, "Error al actualizar el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         }  
         
         JOptionPane.showMessageDialog(null, "Entrada registrada para el producto con codigo: " + codigoProducto);
     }
     
     private boolean buscarProducto(String codigoProducto) {
         boolean productoEncontrado = false;
         
         try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
             String linea;
             
             while ((linea = reader.readLine()) != null) {
                 String[] datosProducto = linea.split("\\|");
                 
                 if (datosProducto[0].equals(codigoProducto)) {
                     productoEncontrado = true;
                     
                     JOptionPane.showMessageDialog(null, "Producto encontrado:\n" + "Nombre: " + datosProducto[1] + "\n" + "Categoria: " + datosProducto[2] + "\n" + "Stock actual: " + datosProducto[6]);
                     break;
                 }
             }
             
             if (!productoEncontrado) {
                 JOptionPane.showMessageDialog(null, "El producto con codigo " + codigoProducto + "no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                 
             }
         } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
         }
         return productoEncontrado;
     }
     
     public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ControlExistenciasScreen().setVisible(true);
            }
        });
    }
    
}
         
         

