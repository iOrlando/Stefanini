package dao;

import dto.Cliente;
import dto.Cuenta;
import dto.Empleado;
import dto.Transaccion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final String url = "jdbc:mysql://localhost:3306/banco";
    private final String user = "root";
    private final String password = "root";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Operaciones CRUD para Cliente
    public void addCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.executeUpdate();
        }
    }

    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setInt(5, cliente.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteCliente(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Cliente getClienteById(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"), rs.getString("telefono"));
            }
        }
        return null;
    }

    public List<Cliente> getAllClientes() throws SQLException {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"), rs.getString("telefono")));
            }
        }
        return clientes;
    }
    
    public List<Empleado> getAllEmpleados() throws SQLException {
        String sql = "SELECT * FROM empleados";
        List<Empleado> empleados = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(new Empleado(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return empleados;
    }

    // Operaciones CRUD para Cuenta
    public List<Cuenta> getAllCuentas() throws SQLException {
        String sql = "SELECT * FROM cuentas";
        List<Cuenta> cuentas = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cuentas.add(new Cuenta(rs.getInt("id"), rs.getString("numeroCuenta"), rs.getString("tipoCuenta"), rs.getDouble("saldo"), rs.getInt("clienteId")));
            }
        }
        return cuentas;
    }

    public List<Cuenta> getCuentasById(int id) throws SQLException {
        String sql = "SELECT * FROM cuentas WHERE id = ?";
        List<Cuenta> cuentas = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cuentas.add(new Cuenta(rs.getInt("id"), rs.getString("numeroCuenta"), rs.getString("tipoCuenta"), rs.getDouble("saldo"), rs.getInt("clienteId")));
            }
        }
        return cuentas;
    }

    // Operaciones CRUD para Transacción
    public List<Transaccion> getTransaccionesHoy() throws SQLException {
        String sql = "SELECT * FROM transacciones WHERE DATE(fecha) = CURDATE()";
        List<Transaccion> transacciones = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transacciones.add(new Transaccion(rs.getInt("id"), rs.getString("tipo"), rs.getDouble("monto"), rs.getString("fecha"), rs.getInt("cuentaId")));
            }
        }
        return transacciones;
    }
    
     // Operaciones CRUD para Empleado
    public void addEmpleado(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleados (nombre) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.executeUpdate();
        }
    }

}

