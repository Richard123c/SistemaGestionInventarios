/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control.existencias;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
/**
 *
 * @author Lenovo
 */
public class HistorialMovimientosScreen extends JFrame{
    
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;
    private JTextArea areaHistorial;
    private JTextField txtCodigoProducto;
    private JButton btnVerHistorial;
    
    public HistorialMovimientosScreen() {
        setTitle("Historial de movimientos de Stock");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //Carga productos desde el archivo
        modeloLista = new DefaultListModel<>();
        cargarProductosDesdeArchivo();
        
        //Crea la lista de productos
        listaProductos = new JList<>(modeloLista);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaProductos);
        
        JButton btnVerHistorial = new JButton("Ver Historial");
        
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridLayout(1, 3));
        
        JLabel lblCodigoProducto = new JLabel("Código del Producto:");
        txtCodigoProducto = new JTextField();
        btnVerHistorial = new JButton("Ver Historial");
        
        panelBusqueda.add(lblCodigoProducto);
        panelBusqueda.add(txtCodigoProducto);
        panelBusqueda.add(btnVerHistorial);
        
        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setPreferredSize(new Dimension(400, 300));
        
        JScrollPane scrollPaneHistorial = new JScrollPane(areaHistorial);
        
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        btnVerHistorial.addActionListener(e -> mostrarHistorial());
        
        // Accion al presionar el boton
        btnVerHistorial.addActionListener(e -> {
            String productoSeleccionado = listaProductos.getSelectedValue();
            if (productoSeleccionado != null) {
                mostrarHistorialProducto(productoSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        add(scrollPane, BorderLayout.CENTER);
        add(btnVerHistorial, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void cargarProductosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosProducto = linea.split("\\|");
                modeloLista.addElement(datosProducto[1]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la lista de productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarHistorialProducto(String nombreProducto) {
        List<String> movimientos = new ArrayList<>();
        
        // Lee el historial del archivo de ventas o entradas
        try (BufferedReader reader = new BufferedReader(new FileReader("movimientos_stock.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.contains(nombreProducto)) {
                    movimientos.add(linea);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el historial de movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        //Muestra los movimientos en un cuadro de diálogo
        
        if(movimientos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay movimientos registrados para este producto.", "Historial de Movimientos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder historial = new StringBuilder();
            for (String movimiento : movimientos) {
                historial.append(movimiento).append("\n");
            }
            JTextArea textArea = new JTextArea(historial.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, "Historial de Movimientos - " + nombreProducto, JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    private void mostrarHistorial() {
        String codigoProducto = txtCodigoProducto.getText().trim();
        
        if (codigoProducto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un codigo de producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        areaHistorial.setText(""); // Limpia el área de texto
        
        try (BufferedReader reader = new BufferedReader(new FileReader("historial_movimientos.txt"))) {
            String linea;
            boolean productoEncontrado = false;
            
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("Codigo: " + codigoProducto)) {
                    areaHistorial.append(linea + "\n");
                    productoEncontrado = true;
                }
            }
            if (!productoEncontrado) {
                JOptionPane.showMessageDialog(null, "No se encontraron movimientos para el producto con código " + codigoProducto, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el historial de movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
          HistorialMovimientosScreen historialScreen = new HistorialMovimientosScreen();
          historialScreen.setVisible(true);
        });
    }
}
