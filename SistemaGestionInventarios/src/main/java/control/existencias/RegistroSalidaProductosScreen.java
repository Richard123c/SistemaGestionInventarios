 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.existencias;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Lenovo
 */
public class RegistroSalidaProductosScreen extends JFrame{
    
    public RegistroSalidaProductosScreen() {
    setTitle("Control de Existencias - Registro de Salida");
    setSize(400, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    
    // Componentes para registrar salida de productos
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));
    
    JLabel lblCodigoProducto = new JLabel("Codigo del Producto:");
    JTextField txtCodigoProducto = new JTextField();
    
    JLabel lblCantidad = new JLabel("Cantidad a retirar:");
    JTextField txtCantidad = new JTextField();
    
    JButton btnRegistrarSalida = new JButton("Registrar Salida");
    
    panel.add(lblCodigoProducto);
    panel.add(txtCodigoProducto);
    panel.add(lblCantidad);
    panel.add(txtCantidad);
    panel.add(new JLabel(""));
    panel.add(btnRegistrarSalida);
    
    add(panel, BorderLayout.CENTER);
    this.add(panel);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // Acción del botón Registrar Salida
    btnRegistrarSalida.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigoProducto = txtCodigoProducto.getText().trim();
                int cantidadSalida = Integer.parseInt(txtCantidad.getText().trim());
                
                if (codigoProducto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar el código del producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (cantidadSalida <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Búsqueda del producto en el archivo de texto
                boolean productoEncontrado = false;
                List<String> lineasArchivo = new ArrayList<>();
                
                try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
                    String linea;
                    
                    while ((linea = reader.readLine()) != null) {
                        String[] datosProducto = linea.split("\\|");
                        
                        if (datosProducto[0].equals(codigoProducto)) {
                            productoEncontrado = true;
                        
                            int stockActual = Integer.parseInt(datosProducto[6]);
                            
                            
                            if (stockActual < cantidadSalida) {
                                JOptionPane.showMessageDialog(null, "No hay suficiente stock para realizar la venta. Stock disponible: " + stockActual, "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            
                                // El producto tiene suficiente stock para retirar
                                int nuevoStock = stockActual - cantidadSalida;
                                datosProducto[6] = String.valueOf(nuevoStock);
                                linea = String.join("|", datosProducto);
                                
                            } 
                        lineasArchivo.add(linea);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    if (!productoEncontrado) {
                JOptionPane.showMessageDialog(null, "El producto con código " + codigoProducto + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                        
                
                // Si el producto fue encontrado y actualizado, reescribir el archivo
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
                    for (String linea : lineasArchivo) {
                        writer.write(linea);
                        writer.newLine();
                    }
                } 
                
             
            // Después de haber procesado la venta (si la cantidad de productos es válida)

        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formatter);

        String registroVenta = fechaHoraFormateada + " | Código: " + codigoProducto + " | Cantidad: " + cantidadSalida;

        // Guardar el registro de la venta en un archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ventas.txt", true))) {
        writer.write(registroVenta);
        writer.newLine();
        JOptionPane.showMessageDialog(null, "Venta registrada correctamente.");
        } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
     }
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Venta registrada correctamente para el producto con código " + codigoProducto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
            
       });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistroSalidaProductosScreen().setVisible(true);
            }
        });
    }
}

