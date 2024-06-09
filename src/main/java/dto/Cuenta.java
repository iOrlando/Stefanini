package dto;

import lombok.Data;

@Data
public class Cuenta {

    private int id;
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldo;
    private int clienteId;

    public Cuenta(int id, String numeroCuenta, String tipoCuenta, double saldo, int clienteId) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.clienteId = clienteId;
    }
}
