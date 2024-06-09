
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysql.cj.xdevapi.Statement;

import dao.DatabaseManager;
import dto.Cliente;
import dto.Cuenta;
import dto.Empleado;
import dto.Transaccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UtileriasTests {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @InjectMocks
    private DatabaseManager clienteService;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private Statement mockStatement;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
    }

    @Test
    void testAddCliente() throws SQLException {
        Cliente cliente = new Cliente(0, "John", "Doe", "john.doe@example.com", "123456789");
        // Simula el comportamiento de la conexión
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        // Llama al método a probar
        clienteService.addCliente(cliente);
        // Verifica que los métodos se llamaron con los parámetros correctos
        verify(mockPreparedStatement).setString(1, cliente.getNombre());
        verify(mockPreparedStatement).setString(2, cliente.getApellido());
        verify(mockPreparedStatement).setString(3, cliente.getEmail());
        verify(mockPreparedStatement).setString(4, cliente.getTelefono());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateCliente() throws SQLException {
        Cliente cliente = new Cliente(0, "John", "Doe", "john.doe@example.com", "123456789");
        cliente.setId(1);
        // Simula el comportamiento de la conexión
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        // Llama al método a probar
        clienteService.updateCliente(cliente);
        // Verifica que los métodos se llamaron con los parámetros correctos
        verify(mockPreparedStatement).setString(1, cliente.getNombre());
        verify(mockPreparedStatement).setString(2, cliente.getApellido());
        verify(mockPreparedStatement).setString(3, cliente.getEmail());
        verify(mockPreparedStatement).setString(4, cliente.getTelefono());
        verify(mockPreparedStatement).setInt(5, cliente.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteCliente() throws SQLException {
        int clienteId = 1;
        // Simula el comportamiento de la conexión
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        // Llama al método a probar
        clienteService.deleteCliente(clienteId);
        // Verifica que los métodos se llamaron con los parámetros correctos
        verify(mockPreparedStatement).setInt(1, clienteId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetClienteById() throws SQLException {
        int clienteId = 1;
        Cliente expectedCliente = new Cliente(clienteId, "John", "Doe", "john.doe@example.com", "123456789");
        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(expectedCliente.getId());
        when(mockResultSet.getString("nombre")).thenReturn(expectedCliente.getNombre());
        when(mockResultSet.getString("apellido")).thenReturn(expectedCliente.getApellido());
        when(mockResultSet.getString("email")).thenReturn(expectedCliente.getEmail());
        when(mockResultSet.getString("telefono")).thenReturn(expectedCliente.getTelefono());
        // Simula el comportamiento del PreparedStatement
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        // Llama al método a probar
        Cliente actualCliente = clienteService.getClienteById(clienteId);
        // Verifica que los métodos se llamaron con los parámetros correctos
        verify(mockPreparedStatement).setInt(1, clienteId);
        // Verifica el resultado
        assertNotNull(actualCliente);
        assertEquals(expectedCliente.getId(), actualCliente.getId());
        assertEquals(expectedCliente.getNombre(), actualCliente.getNombre());
        assertEquals(expectedCliente.getApellido(), actualCliente.getApellido());
        assertEquals(expectedCliente.getEmail(), actualCliente.getEmail());
        assertEquals(expectedCliente.getTelefono(), actualCliente.getTelefono());
    }

    @Test
    void testGetAllClientes() throws SQLException {
        List<Cliente> expectedClientes = new ArrayList<>();
        expectedClientes.add(new Cliente(1, "John", "Doe", "john.doe@example.com", "123456789"));
        expectedClientes.add(new Cliente(2, "Jane", "Smith", "jane.smith@example.com", "987654321"));

        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // Dos clientes, luego no más filas
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nombre")).thenReturn("John", "Jane");
        when(mockResultSet.getString("apellido")).thenReturn("Doe", "Smith");
        when(mockResultSet.getString("email")).thenReturn("john.doe@example.com", "jane.smith@example.com");
        when(mockResultSet.getString("telefono")).thenReturn("123456789", "987654321");

        // Simula el comportamiento del Statement
        when(((java.sql.Statement) mockStatement).executeQuery(any(String.class))).thenReturn(mockResultSet);

        // Llama al método a probar
        List<Cliente> actualClientes = clienteService.getAllClientes();

        // Verifica que el resultado sea el esperado
        assertNotNull(actualClientes);
        assertEquals(expectedClientes.size(), actualClientes.size());

        for (int i = 0; i < expectedClientes.size(); i++) {
            assertEquals(expectedClientes.get(i).getId(), actualClientes.get(i).getId());
            assertEquals(expectedClientes.get(i).getNombre(), actualClientes.get(i).getNombre());
            assertEquals(expectedClientes.get(i).getApellido(), actualClientes.get(i).getApellido());
            assertEquals(expectedClientes.get(i).getEmail(), actualClientes.get(i).getEmail());
            assertEquals(expectedClientes.get(i).getTelefono(), actualClientes.get(i).getTelefono());
        }
    }

    @Test
    void testGetAllEmpleados() throws SQLException {
        List<Empleado> expectedEmpleados = new ArrayList<>();
        expectedEmpleados.add(new Empleado(1, "Carlos"));
        expectedEmpleados.add(new Empleado(2, "Ana"));

        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // Dos empleados, luego no más filas
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("nombre")).thenReturn("Carlos", "Ana");

        // Simula el comportamiento del Statement
        when(((java.sql.Statement) mockStatement).executeQuery(any(String.class))).thenReturn(mockResultSet);

        // Llama al método a probar
        List<Empleado> actualEmpleados = clienteService.getAllEmpleados();

        // Verifica que el resultado sea el esperado
        assertNotNull(actualEmpleados);
        assertEquals(expectedEmpleados.size(), actualEmpleados.size());

        for (int i = 0; i < expectedEmpleados.size(); i++) {
            assertEquals(expectedEmpleados.get(i).getId(), actualEmpleados.get(i).getId());
            assertEquals(expectedEmpleados.get(i).getNombre(), actualEmpleados.get(i).getNombre());
        }
    }

    @Test
    void testGetAllCuentas() throws SQLException {
        List<Cuenta> expectedCuentas = new ArrayList<>();
        expectedCuentas.add(new Cuenta(1, "1234567890", "Ahorro", 1000.0, 1));
        expectedCuentas.add(new Cuenta(2, "0987654321", "Corriente", 2000.0, 2));

        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // Dos cuentas, luego no más filas
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("numeroCuenta")).thenReturn("1234567890", "0987654321");
        when(mockResultSet.getString("tipoCuenta")).thenReturn("Ahorro", "Corriente");
        when(mockResultSet.getDouble("saldo")).thenReturn(1000.0, 2000.0);
        when(mockResultSet.getInt("clienteId")).thenReturn(1, 2);

        // Simula el comportamiento del Statement
        when(((java.sql.Statement) mockStatement).executeQuery(any(String.class))).thenReturn(mockResultSet);

        // Llama al método a probar
        List<Cuenta> actualCuentas = clienteService.getAllCuentas();

        // Verifica que el resultado sea el esperado
        assertNotNull(actualCuentas);
        assertEquals(expectedCuentas.size(), actualCuentas.size());

        for (int i = 0; i < expectedCuentas.size(); i++) {
            assertEquals(expectedCuentas.get(i).getId(), actualCuentas.get(i).getId());
            assertEquals(expectedCuentas.get(i).getNumeroCuenta(), actualCuentas.get(i).getNumeroCuenta());
            assertEquals(expectedCuentas.get(i).getTipoCuenta(), actualCuentas.get(i).getTipoCuenta());
            assertEquals(expectedCuentas.get(i).getSaldo(), actualCuentas.get(i).getSaldo());
            assertEquals(expectedCuentas.get(i).getClienteId(), actualCuentas.get(i).getClienteId());
        }
    }

    @Test
    void testGetCuentasById() throws SQLException {
        int cuentaId = 1;
        List<Cuenta> expectedCuentas = new ArrayList<>();
        expectedCuentas.add(new Cuenta(cuentaId, "1234567890", "Ahorro", 1000.0, 1));

        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true, false); // Una cuenta, luego no más filas
        when(mockResultSet.getInt("id")).thenReturn(cuentaId);
        when(mockResultSet.getString("numeroCuenta")).thenReturn("1234567890");
        when(mockResultSet.getString("tipoCuenta")).thenReturn("Ahorro");
        when(mockResultSet.getDouble("saldo")).thenReturn(1000.0);
        when(mockResultSet.getInt("clienteId")).thenReturn(1);

        // Simula el comportamiento del PreparedStatement
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Llama al método a probar
        List<Cuenta> actualCuentas = clienteService.getCuentasById(cuentaId);

        // Verifica que el resultado sea el esperado
        assertNotNull(actualCuentas);
        assertEquals(expectedCuentas.size(), actualCuentas.size());

        for (int i = 0; i < expectedCuentas.size(); i++) {
            assertEquals(expectedCuentas.get(i).getId(), actualCuentas.get(i).getId());
            assertEquals(expectedCuentas.get(i).getNumeroCuenta(), actualCuentas.get(i).getNumeroCuenta());
            assertEquals(expectedCuentas.get(i).getTipoCuenta(), actualCuentas.get(i).getTipoCuenta());
            assertEquals(expectedCuentas.get(i).getSaldo(), actualCuentas.get(i).getSaldo());
            assertEquals(expectedCuentas.get(i).getClienteId(), actualCuentas.get(i).getClienteId());
        }
    }

    @Test
    void testGetTransaccionesHoy() throws SQLException {
        List<Transaccion> expectedTransacciones = new ArrayList<>();
        expectedTransacciones.add(new Transaccion(1, "Compra", 100.0, "2024-06-09", 1));
        expectedTransacciones.add(new Transaccion(2, "Venta", 150.0, "2024-06-09", 2));

        // Simula el comportamiento del ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // Dos transacciones, luego no más filas
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("tipo")).thenReturn("Compra", "Venta");
        when(mockResultSet.getDouble("monto")).thenReturn(100.0, 150.0);
        when(mockResultSet.getString("fecha")).thenReturn("2024-06-09");
        when(mockResultSet.getInt("cuentaId")).thenReturn(1, 2);

        // Simula el comportamiento del Statement
        when(((java.sql.Statement) mockStatement).executeQuery(any(String.class))).thenReturn(mockResultSet);

        // Llama al método a probar
        List<Transaccion> actualTransacciones = clienteService.getTransaccionesHoy();

        // Verifica que el resultado sea el esperado
        assertNotNull(actualTransacciones);
        assertEquals(expectedTransacciones.size(), actualTransacciones.size());

        for (int i = 0; i < expectedTransacciones.size(); i++) {
            assertEquals(expectedTransacciones.get(i).getId(), actualTransacciones.get(i).getId());
            assertEquals(expectedTransacciones.get(i).getTipo(), actualTransacciones.get(i).getTipo());
            assertEquals(expectedTransacciones.get(i).getMonto(), actualTransacciones.get(i).getMonto());
            assertEquals(expectedTransacciones.get(i).getFecha(), actualTransacciones.get(i).getFecha());
            assertEquals(expectedTransacciones.get(i).getCuentaId(), actualTransacciones.get(i).getCuentaId());
        }
    }

    @Test
    void testAddEmpleado() throws SQLException {
        Empleado empleado = new Empleado("Juan");

        // Llama al método a probar
        clienteService.addEmpleado(empleado);

        // Verifica que se haya llamado a setString con el nombre correcto
        verify(mockPreparedStatement).setString(1, "Juan");
        // Verifica que se haya llamado a executeUpdate
        verify(mockPreparedStatement).executeUpdate();
    }

}
