/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seguridad;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Lenovo
 */
public class RegistroActividades {
    private static final String ARCHIVO_LOG = "log_accesos.txt";
    
    public static void registrarAcceso(String nombreUsuario) {
        try (FileWriter writer = new FileWriter(ARCHIVO_LOG, true)) {
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Usuario: " + nombreUsuario + "Acceso: " + fechaHora + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void registrarSalida(String nombreUsuario) {
        try (FileWriter writer = new FileWriter(ARCHIVO_LOG, true)) {
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Usuario: " + nombreUsuario + "Salida: " + fechaHora + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
