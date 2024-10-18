/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package informes.estadisticas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class InformeComprasScreen extends JFrame{
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboProveedores;
    private JTextField txtFechaInicio, txtFechaFin;
    private JButton btnGenerarInforme, btnExportarCSV;
    
    public InformeComprasScreen() {
        setTitle("Informe de Inventario Actual");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        // Crear el panel superior para seleccionar proveedor y rango de fechas
        JPanel panelFiltro = new JPanel(new GridLayout(2, 4, 5, 5));
        JLabel lblProveedor = new JLabel("Proveedor");
        comboProveedores = new JComboBox<>();
        cargarProveedores();
        
        JLabel lblFechaInicio = new JLabel("Fecha de Inicio (YYYY-MM-DD):");
        txtFechaInicio = new JTextField();
        
        JLabel lblFechaFin = new JLabel("Fecha de Fin (YYYY-MM-DD):");
        txtFechaFin = new JTextField();
        
        btnGenerarInforme = new JButton("Generar Informe");
        
        panelFiltro.add(lblProveedor);
        panelFiltro.add(comboProveedores);
        panelFiltro.add(lblFechaInicio);
        panelFiltro.add(txtFechaInicio);
        panelFiltro.add(lblFechaFin);
        panelFiltro.add(txtFechaFin);
        panelFiltro.add(new JLabel());
        panelFiltro.add(btnGenerarInforme);
        
        add(panelFiltro, BorderLayout.NORTH);
        
        // Crear la tabla para mostrar las compras
        String[] columnas = {"Fecha", "Número de Pedido", "Producto", "Cantidad", "Precio Unitario", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaCompras = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior para exportar a CSV
        JPanel panelInferior = new JPanel(new FlowLayout());
        btnExportarCSV = new JButton("Exportar a CSV");
        panelInferior.add(btnExportarCSV);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        // Acción para generar informe
        btnGenerarInforme.addActionListener(e -> generarInformeCompras());
        
        btnExportarCSV.addActionListener(e -> exportarInformeCSV());
        
        setVisible(true);
    }
    
    // Método para cargar proveedores desde un archivo
    private void cargarProveedores() {
        comboProveedores.addItem("Seleccione un proveedor");
        try (BufferedReader reader = new BufferedReader(new FileReader("proveedores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null){
                comboProveedores.addItem(linea.trim());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para generar el informe de compras
    private void generarInformeCompras() {
        String proveedorSeleccionado = (String) comboProveedores.getSelectedItem();
        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        
        if (proveedorSeleccionado == null || proveedorSeleccionado.equals("Seleccione un proveedor")){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar, el rango de fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        File archivoCompras = new File("Compras.txt");
        if (!archivoCompras.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo de compras no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoCompras))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosCompra = linea.split("\\|");
                if (datosCompra.length == 6) {
                    String fechaCompra = datosCompra[0];
                    String proveedor = datosCompra[0];
                    
                    // Filtra por proveedor y rango de fechas
                    Date fechaCompraDate = dateFormat.parse(fechaCompra);
                    Date fechaInicioDate = dateFormat.parse(fechaInicio);
                    Date fechaFinDate = dateFormat.parse(fechaFin);
                    
                    if (proveedor.equals(proveedorSeleccionado) &&
                            (fechaCompraDate.equals(fechaInicioDate) || fechaCompraDate.after(fechaInicioDate)) &&
                            (fechaCompraDate.equals(fechaFinDate) || fechaCompraDate.before(fechaFinDate))) {
                        
                        modeloTabla.addRow(datosCompra);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el informe de compras.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para exportar informe a CSV
    private void  exportarInformeCSV() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar informe como CSV");
        int seleccion = fileChooser.showSaveDialog(this);
        if  (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo + ".csv"))) {
                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    writer.write(modeloTabla.getColumnName(i) + ",");
                }
                
                writer.newLine();
                
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                        writer.write(modeloTabla.getValueAt(i, j).toString() + ",");
            }
                    writer.newLine();
        }
                JOptionPane.showMessageDialog(this, "Informe exportado corectamente.");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error al exportar el informe.", "Error", JOptionPane.ERROR_MESSAGE);
       }
    }
  }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InformeComprasScreen::new);
    }
}
