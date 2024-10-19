/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seguridad;

/**
 *
 * @author Lenovo
 */
public class Usuario {
    private String nombreUsuario;
    private String rol;  // Ej: "Administrador", "Vendedor", "Bodega"
    
    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
    
    public String getRol() {
        return rol;
    }
}
