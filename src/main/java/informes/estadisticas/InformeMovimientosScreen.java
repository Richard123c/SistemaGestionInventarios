/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package informes.estadisticas;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Lenovo
 */
public class InformeMovimientosScreen extends JFrame{
    private JComboBox<String> comboProductos;
    private JTextField txtCodigoProducto;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JTable tablaMovimientos;
    private DefaultTableModel  modeloTabla;
    private JButton btnGenerarInforme, btnExportarCSV;
    
    public InformeMovimientosScreen() {
        setTitle("Informe de Inventario Actual");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        // Panel para seleccionar el producto y rango de fechas
        JPanel panelSeleccion = new JPanel(new GridLayout(3,2));
        
        comboProductos = new JComboBox<>();
        cargarProductos();
        
        txtFechaInicio = new JTextField("YYYY-MM-DD");
        txtFechaFin = new JTextField("YYYY-MM-DD");
        
        panelSeleccion.add(new JLabel("Producto"));
        panelSeleccion.add(comboProductos);
        panelSeleccion.add(new JLabel("Fecha Inicio"));
        panelSeleccion.add(txtFechaInicio);
        panelSeleccion.add(new JLabel("Fecha Fin"));
        panelSeleccion.add(txtFechaFin);
        
        add(panelSeleccion, BorderLayout.NORTH);
        
        // Tabla para mostrar los movimientos
        modeloTabla = new DefaultTableModel(new Object[]{"Fecha", "Operación", "Cantidad", "Usuario"}, 0);
        tablaMovimientos = new JTable(modeloTabla);
        JScrollPane scrollPaneTabla = new JScrollPane(tablaMovimientos);
        add(scrollPaneTabla, BorderLayout.CENTER);
        
        // Botones para generar informe y exportar CSV
        JPanel panelBotones = new JPanel();
        btnGenerarInforme = new JButton("Generar Informe");
        btnExportarCSV = new JButton("Exportar a CSV");
        
        panelBotones.add(btnGenerarInforme);
        panelBotones.add(btnExportarCSV);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnGenerarInforme.addActionListener(e -> generarInforme());
        btnExportarCSV.addActionListener(e -> exportarInformeCSV());
        
        setVisible(true);
    }
    
    private void cargarProductos() {
         try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datosProducto = linea.split("\\|");
            comboProductos.addItem(datosProducto[1]);
        }
        
        if (comboProductos.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos en el archivo.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar productos.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    // Genera el informe de movimientos basado en el rango de fechas
    private void generarInforme() {
    modeloTabla.setRowCount(0); // Limpia la tabla antes de cargar nuevos datos
    String productoSeleccionado = (String) comboProductos.getSelectedItem();
    
    if (productoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String fechaInicio = txtFechaInicio.getText().trim();
    String fechaFin = txtFechaFin.getText().trim();

    try (BufferedReader reader = new BufferedReader(new FileReader("movimientos_stock.txt"))) {
        String linea;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaInicioParsed = formatoFecha.parse(fechaInicio);
        Date fechaFinParsed = formatoFecha.parse(fechaFin);

        while ((linea = reader.readLine()) != null) {
            String[] datosMovimiento = linea.split("\\|");
            String fechaMovimientoStr = datosMovimiento[0].trim();
            String productoMovimiento = datosMovimiento[2].trim();

            Date fechaMovimientoParsed = formatoFecha.parse(fechaMovimientoStr.split(" ")[0]);

            if (productoMovimiento.equals(productoSeleccionado) &&
                    !fechaMovimientoParsed.before(fechaInicioParsed) &&
                    !fechaMovimientoParsed.after(fechaFinParsed)) {

                String operacion = datosMovimiento[1].trim();
                String cantidad = datosMovimiento[3].trim();
                String usuario = datosMovimiento[4].trim();

                modeloTabla.addRow(new Object[]{fechaMovimientoStr, operacion, cantidad, usuario});
            }
        }
        
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron movimientos para este producto en el rango de fechas.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al generar el informe.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

}
    
    // Exporta el informe a un archivo CSV
    private void  exportarInformeCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("informe_movimientos.csv"))) {
            writer.write("Fecha,Operacion,Cantidad,Usuario\n");
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    writer.write(modeloTabla.getValueAt(i, j).toString()+ ",");
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Informe exportado a informe_movimientos.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar el informe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Siempre usa invokeLater para crear interfaces gráficas en el EDT
        SwingUtilities.invokeLater(() -> new InformeMovimientosScreen());
    }
}
