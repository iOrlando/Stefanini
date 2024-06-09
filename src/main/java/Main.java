
import dao.DatabaseManager;
import dto.Cliente;
import dto.Cuenta;
import dto.Empleado;
import dto.Transaccion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Main {

    private final static DatabaseManager DBMANAGER = new DatabaseManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Gestión Bancaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JButton btnListarMovimientos = new JButton("Movimientos del Día");
        JButton btnListarClientes = new JButton("Clientes");
        JButton btnListarCuentas = new JButton("Cuentas");
        JButton btnListarCuentasById = new JButton("Cuentas por cliente");
        JButton btnAltaEmpleado = new JButton("Alta de Empleado");
        JButton btnListaEmpleados = new JButton("Empleados");
        JButton btnCRUDClientes = new JButton("CRUD Clientes");

        panel.add(btnListarMovimientos);
        panel.add(btnListarClientes);
        panel.add(btnListarCuentas);
        panel.add(btnListarCuentasById);
        panel.add(btnListaEmpleados);
        panel.add(btnAltaEmpleado);
        panel.add(btnCRUDClientes);

        frame.add(panel, BorderLayout.CENTER);
        // Acciones para cada botón
        btnListarMovimientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMovimientos();
            }
        });
        btnListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarClientes();
            }
        });
        btnListarCuentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCuentas();
            }
        });
        btnListarCuentasById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCuentasById();
            }
        });
        btnAltaEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                altaEmpleado();
            }
        });
        btnListaEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarEmpleados();
            }
        });
        btnCRUDClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacionesCRUDClientes();
            }
        });
        frame.setVisible(true);
    }

    private static void listarEmpleados() {
        try {
            java.util.List<Empleado> empleados = DBMANAGER.getAllEmpleados();
            JFrame frame = new JFrame("Empleados");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            JTextArea textArea = new JTextArea();
            for (Empleado t : empleados) {
                textArea.append(t.toString() + "\n");
            }
            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarMovimientos() {
        try {
            java.util.List<Transaccion> transacciones = DBMANAGER.getTransaccionesHoy();
            JFrame frame = new JFrame("Movimientos del Día");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            JTextArea textArea = new JTextArea();
            for (Transaccion t : transacciones) {
                textArea.append(t.toString() + "\n");
            }
            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarClientes() {
        try {
            java.util.List<Cliente> clientes = DBMANAGER.getAllClientes();
            JFrame frame = new JFrame("Lista de Clientes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            JTextArea textArea = new JTextArea();
            for (Cliente c : clientes) {
                textArea.append(c.toString() + "\n");
            }
            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarCuentas() {
        try {
            java.util.List<Cuenta> cuentas = DBMANAGER.getAllCuentas();
            JFrame frame = new JFrame("Lista de Cuentas");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            JTextArea textArea = new JTextArea();
            for (Cuenta c : cuentas) {
                textArea.append(c.toString() + "\n");
            }
            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarCuentasById() {
        // Pedir al usuario que ingrese el ID del cliente
        String input = JOptionPane.showInputDialog(null, "Ingrese el ID del Cliente:", "Buscar Cuentas por Cliente", JOptionPane.QUESTION_MESSAGE);
        // Validar si se ingresó un valor
        if (input != null && !input.isEmpty()) {
            try {
                int clienteId = Integer.parseInt(input.trim());
                // Obtener las cuentas del cliente por ID
                var cuentas = DBMANAGER.getCuentasById(clienteId);
                // Crear y mostrar la ventana con la lista de cuentas
                JFrame frame = new JFrame("Lista de Cuentas del Cliente " + clienteId);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 400);
                JTextArea textArea = new JTextArea();

                for (Cuenta c : cuentas) {
                    textArea.append(c.toString() + "\n");
                }
                frame.add(new JScrollPane(textArea));
                frame.setVisible(true);
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al buscar cuentas. Verifica el ID ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID de cliente válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void altaEmpleado() {
        JFrame frame = new JFrame("Alta de Empleado");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(new JLabel());
        panel.add(btnGuardar);

        frame.add(panel);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                if (!nombre.isEmpty()) {
                    try {
                        DBMANAGER.addEmpleado(new Empleado(nombre));
                        JOptionPane.showMessageDialog(frame, "Empleado agregado con éxito");
                        frame.dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error al agregar el empleado");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "El nombre no puede estar vacío");
                }
            }
        });
        frame.setVisible(true);
    }

    private static void operacionesCRUDClientes() {
        JFrame frame = new JFrame("Operaciones CRUD para Clientes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(1, 2));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 2));

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel lblTelefono = new JLabel("Telefono:");
        JTextField txtTelefono = new JTextField();

        panel1.add(lblId);
        panel1.add(txtId);
        panel1.add(lblNombre);
        panel1.add(txtNombre);
        panel1.add(lblApellido);
        panel1.add(txtApellido);
        panel1.add(lblEmail);
        panel1.add(txtEmail);
        panel1.add(lblTelefono);
        panel1.add(txtTelefono);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 1));

        JButton btnAgregar = new JButton("Agregar Cliente");
        JButton btnModificar = new JButton("Modificar Cliente");
        JButton btnEliminar = new JButton("Eliminar Cliente");
        JButton btnBuscar = new JButton("Buscar Cliente");

        panel2.add(btnAgregar);
        panel2.add(btnModificar);
        panel2.add(btnEliminar);
        panel2.add(btnBuscar);

        frame.add(panel1);
        frame.add(panel2);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente cliente = new Cliente(
                            0,
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtEmail.getText(),
                            txtTelefono.getText()
                    );
                    DBMANAGER.addCliente(cliente);
                    JOptionPane.showMessageDialog(frame, "Cliente agregado con éxito");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al agregar el cliente");
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente cliente = new Cliente(
                            Integer.parseInt(txtId.getText()),
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtEmail.getText(),
                            txtTelefono.getText()
                    );
                    DBMANAGER.updateCliente(cliente);
                    JOptionPane.showMessageDialog(frame, "Cliente modificado con éxito");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al modificar el cliente");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    DBMANAGER.deleteCliente(id);
                    JOptionPane.showMessageDialog(frame, "Cliente eliminado con éxito");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al eliminar el cliente");
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    Cliente cliente = DBMANAGER.getClienteById(id);
                    if (cliente != null) {
                        txtNombre.setText(cliente.getNombre());
                        txtApellido.setText(cliente.getApellido());
                        txtEmail.setText(cliente.getEmail());
                        txtTelefono.setText(cliente.getTelefono());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cliente no encontrado");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al buscar el cliente");
                }
            }
        });
        frame.setVisible(true);
    }
}
