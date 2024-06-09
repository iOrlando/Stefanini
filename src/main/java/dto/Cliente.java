package dto;

import lombok.Data;

@Data
public class Cliente {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Cliente(int id, String nombre, String apellido, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }
}
