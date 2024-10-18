/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
package informes.estadisticas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

 
public class GraficaEstadisticasScreen extends JFrame{
    setTitle("Gráficas y Estadísticas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para mostrar la gráfica
        JPanel panelGraficas = new JPanel();
        panelGraficas.setLayout(new GridLayout(1, 1)); // Una sola gráfica por ahora
        add(panelGraficas, BorderLayout.CENTER);

        // Cargar datos del inventario y generar la gráfica
        DefaultCategoryDataset dataset = generarDatosInventario();
        JFreeChart chart = ChartFactory.createBarChart(
                "Inventario Actual por Producto",  // Título
                "Producto",                       // Eje X
                "Cantidad en Stock",              // Eje Y
                dataset,                          // Datos
                PlotOrientation.VERTICAL,         // Orientación de la gráfica
                true,                             // Mostrar leyenda
                true,                             // Usar tooltips
                false                             // Generar URLs
        );

        // Crear un panel para la gráfica y agregarlo al frame
        ChartPanel chartPanel = new ChartPanel(chart);
        panelGraficas.add(chartPanel);

        // Mostrar la ventana
        setVisible(true);
    }

    // Método para generar los datos de la gráfica del inventario
    private DefaultCategoryDataset generarDatosInventario() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> inventario = cargarInventarioDesdeArchivo();

        // Añadir los datos al dataset
        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            String producto = entry.getKey();
            Integer cantidadStock = entry.getValue();
            dataset.addValue(cantidadStock, "Stock", producto);
        }

        return dataset;
    }

    // Cargar datos del inventario desde productos.txt
    private Map<String, Integer> cargarInventarioDesdeArchivo() {
        Map<String, Integer> inventario = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosProducto = linea.split("\\|");
                String nombreProducto = datosProducto[1]; // Asumiendo que la posición 1 es el nombre del producto
                int cantidadStock = Integer.parseInt(datosProducto[6]); // Asumiendo que la posición 6 es el stock actual
                inventario.put(nombreProducto, cantidadStock);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return inventario;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraficaEstadisticasScreen::new);
    }

}
* @author Lenovo
 */