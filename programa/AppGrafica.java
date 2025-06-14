// Archivo: AppSwing.java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.*;

public class AppGrafica {
    public static void main(String[] args) {
        Programa.cargarUsuarios();
        Programa.cargarZapatillas();

        JFrame frame = new JFrame("Gestor de Zapatillas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(0, 1));

        JButton btnAñadirUsuario = new JButton("Añadir Usuario");
        JButton btnVerUsuarios = new JButton("Ver Usuarios");
        JButton btnAñadirZapatilla = new JButton("Añadir Zapatilla");
        JButton btnVerZapatillas = new JButton("Ver Zapatillas");
        JButton btnSalir = new JButton("Salir");

        frame.add(btnAñadirUsuario);
        frame.add(btnVerUsuarios);
        frame.add(btnAñadirZapatilla);
        frame.add(btnVerZapatillas);
        frame.add(btnSalir);

        btnAñadirUsuario.addActionListener(e -> mostrarFormularioUsuario());
        btnAñadirZapatilla.addActionListener(e -> mostrarFormularioZapatilla());
        btnVerUsuarios.addActionListener(e -> mostrarListaUsuarios(frame));
        btnVerZapatillas.addActionListener(e -> mostrarListaZapatillas(frame));
        btnSalir.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private static void mostrarFormularioUsuario() {
        JTextField campoNombre = new JTextField();
        JTextField campoCorreo = new JTextField();
        JPasswordField campoContraseña = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Correo:"));
        panel.add(campoCorreo);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoContraseña);

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText();
            String correo = campoCorreo.getText();
            String contraseña = new String(campoContraseña.getPassword());

            Usuario nuevo = new Usuario(nombre, correo, contraseña);
            Programa.usuarios.add(nuevo);
            Programa.guardarUsuarios();
            JOptionPane.showMessageDialog(null, "Usuario añadido correctamente.");
        }
    }

    private static void mostrarFormularioZapatilla() {
        JTextField campoModelo = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoTalla = new JTextField();

        String[] nombresUsuarios = Programa.usuarios.stream().map(Usuario::getNombre).toArray(String[]::new);
        JComboBox<String> comboUsuarios = new JComboBox<>(nombresUsuarios);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Modelo:"));
        panel.add(campoModelo);
        panel.add(new JLabel("Marca:"));
        panel.add(campoMarca);
        panel.add(new JLabel("Talla:"));
        panel.add(campoTalla);
        panel.add(new JLabel("Dueño:"));
        panel.add(comboUsuarios);

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Nueva Zapatilla", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String modelo = campoModelo.getText();
                String marca = campoMarca.getText();
                double talla = Double.parseDouble(campoTalla.getText());
                int usuarioIndex = comboUsuarios.getSelectedIndex();
                Usuario dueño = Programa.usuarios.get(usuarioIndex);

                Zapatilla nueva = new Zapatilla(modelo, marca, talla, dueño);
                Programa.zapatillas.add(nueva);
                Programa.guardarZapatillas();

                JOptionPane.showMessageDialog(null, "Zapatilla añadida correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private static void mostrarListaUsuarios(Component parent) {
        StringBuilder lista = new StringBuilder();
        for (Usuario u : Programa.usuarios) {
            lista.append(u.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(parent, lista.toString(), "Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarListaZapatillas(Component parent) {
        StringBuilder lista = new StringBuilder();
        for (Zapatilla z : Programa.zapatillas) {
            lista.append(z.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(parent, lista.toString(), "Zapatillas", JOptionPane.INFORMATION_MESSAGE);
    }
}

// ----

class Programa {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Zapatilla> zapatillas = new ArrayList<>();

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_ZAPATILLAS = "zapatillas.txt";

    public static void guardarUsuarios() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            dos.writeInt(usuarios.size());
            for (Usuario u : usuarios) {
                dos.writeUTF(u.getNombre());
                dos.writeUTF(u.getCorreo());
                dos.writeUTF(u.getContraseña());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) return;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int total = dis.readInt();
            for (int i = 0; i < total; i++) {
                String nombre = dis.readUTF();
                String correo = dis.readUTF();
                String contraseña = dis.readUTF();
                usuarios.add(new Usuario(nombre, correo, contraseña));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarZapatillas() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_ZAPATILLAS))) {
            dos.writeInt(zapatillas.size());
            for (Zapatilla z : zapatillas) {
                dos.writeUTF(z.getModelo());
                dos.writeUTF(z.getMarca());
                dos.writeDouble(z.getTalla());
                dos.writeUTF(z.getDueño().getNombre()); // solo guardamos nombre
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarZapatillas() {
        File archivo = new File(ARCHIVO_ZAPATILLAS);
        if (!archivo.exists()) return;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int total = dis.readInt();
            for (int i = 0; i < total; i++) {
                String modelo = dis.readUTF();
                String marca = dis.readUTF();
                double talla = dis.readDouble();
                String nombreDueño = dis.readUTF();

                Usuario dueño = usuarios.stream().filter(u -> u.getNombre().equals(nombreDueño)).findFirst().orElse(null);
                zapatillas.add(new Zapatilla(modelo, marca, talla, dueño));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ------

class Usuario {
    private String nombre;
    private String correo;
    private String contraseña;

    public Usuario(String nombre, String correo, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    @Override
    public String toString() {
        return nombre + " (" + correo + ")";
    }
}

// ------

class Zapatilla {
    private String modelo;
    private String marca;
    private double talla;
    private Usuario dueño;

    public Zapatilla(String modelo, String marca, double talla, Usuario dueño) {
        this.modelo = modelo;
        this.marca = marca;
        this.talla = talla;
        this.dueño = dueño;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public double getTalla() {
        return talla;
    }

    public Usuario getDueño() {
        return dueño;
    }

    @Override
    public String toString() {
        return modelo + " - " + marca + " - Talla: " + talla + " - Dueño: " + (dueño != null ? dueño.getNombre() : "Sin asignar");
    }
}

