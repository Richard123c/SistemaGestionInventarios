/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedidos.compra;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class VisualizarPedidosConFiltrosScreen extends JFrame {
    private DefaultListModel<String> modeloLista;
    private JList<String> listaPedidos;
    private JTextField txtProveedor, txtProducto;
    private JComboBox<String> comboEstado;
    private JTextField txtFecha;
    
    public VisualizarPedidosConFiltrosScreen() {
        setTitle("Lista de Pedidos de Compra con Filtros");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crea el modelo de lista para almacenar los pedidos
        modeloLista = new DefaultListModel<>();
        cargarPedidosDesdeArchivo();
        
        // Crea la lista con el modelo
        listaPedidos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaPedidos);
        
        // Crea el panel de filtros
        JPanel panelFiltros = new JPanel(new GridLayout(2,4));
        
        panelFiltros.add(new JLabel("Proveedor:"));
        txtProveedor = new JTextField();
        panelFiltros.add(txtProveedor);
        
        panelFiltros.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        panelFiltros.add(txtFecha);
        
        panelFiltros.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"Todos", "Pendiente", "En curso", "Completado"});
        panelFiltros.add(comboEstado);
        
        panelFiltros.add(new JLabel("Producto:"));
        txtProducto = new JTextField();
        panelFiltros.add(txtProducto);
        
        JButton btnFiltrar = new JButton("Filtrar");
        panelFiltros.add(btnFiltrar);
        
        JButton btnLimpiar = new JButton("Limpiar Filtros");
        panelFiltros.add(btnLimpiar);
        
        // Agrega el panel de filtros y el scrollPane a la ventana
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Acciones de los botones del filtro y limpiar
        btnFiltrar.addActionListener(e -> filtrarPedidos());
        btnLimpiar.addActionListener(e -> {
            limpiarFiltros();
            cargarPedidosDesdeArchivo();
        });
    }
    
    private void cargarPedidosDesdeArchivo() {
        modeloLista.clear(); //Limpia la lista antes de cargar
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                modeloLista.addElement(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los pedidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para filtrar los pedidos según los criterios ingresados
    private void filtrarPedidos() {
        String proveedorFiltro = txtProveedor.getText().trim();
        String fechaFiltro = txtFecha.getText().trim();
        String estadoFiltro = (String) comboEstado.getSelectedItem();
        String productoFiltro = txtProducto.getText().trim();
        
        modeloLista.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Cada pedido tiene el formato: CódigoPedido | Proveedor | Fecha | Estado | Producto | Total
                String[] datosPedido = linea.split("\\|");
                
                // Verifica que cada filtro coincida
                boolean coincide = true;
                if (!proveedorFiltro.isEmpty() && !datosPedido[1].trim().equalsIgnoreCase(proveedorFiltro)) {
                    coincide = false;
                }
                if (!fechaFiltro.isEmpty() && !datosPedido[2].trim().equals(fechaFiltro)) {
                    coincide = false;
                }
                if (!estadoFiltro.equals("Todos") && !datosPedido[3].trim().equalsIgnoreCase(estadoFiltro)) {
                    coincide = false;
                }
                if (!productoFiltro.isEmpty() && !datosPedido[4].trim().contains(productoFiltro)) {
                    coincide = false;
                }
                
                // Si el pedido coincide con todos los filtros, se añade a la lista
                if (coincide) {
                    modeloLista.addElement(linea);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al aplicar los filtros", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para limpiar los filtros
    private void limpiarFiltros() {
        txtProveedor.setText("");
        txtFecha.setText("");
        comboEstado.setSelectedIndex(0);
        txtProducto.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            VisualizarPedidosConFiltrosScreen visualizarPedidos = new VisualizarPedidosConFiltrosScreen();
            visualizarPedidos.setVisible(true);
            
        });
    }
}
