package dto;

import lombok.Data;

@Data
public class Transaccion {

    private int id;
    private String tipo;
    private double monto;
    private String fecha;
    private int cuentaId;

    public Transaccion(int id, String tipo, double monto, String fecha, int cuentaId) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.cuentaId = cuentaId;
    }

}
