package dto;

import lombok.Data;

@Data
public class Empleado {

    private int id;
    private String nombre;

    public Empleado(String nombre) {
        this.nombre = nombre;
    }
    
    public Empleado(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
