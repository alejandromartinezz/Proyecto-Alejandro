import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class programa {
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_ZAPATILLAS = "zapatillas.txt";
    private static final String ARCHIVO_TRANSACCIONES = "transacciones.txt";

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Zapatillas> zapatillas = new ArrayList<>();
    private static ArrayList<Transaccion> transacciones = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        cargarUsuarios();
        cargarZapatillas();
        cargarTransacciones();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1:
                    añadirUsuario();
                    break;
                case 2:
                    añadirZapatilla();
                    break;
                case 3:
                    añadirTransaccion();
                    break;
                case 4:
                    listarUsuarios();
                    break;
                case 5:
                    listarZapatillas();
                    break;
                case 6:
                    listarTransacciones();
                    break;
                case 7:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 7);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Añadir usuario");
        System.out.println("2. Añadir zapatilla");
        System.out.println("3. Añadir transacción");
        System.out.println("4. Listar usuarios");
        System.out.println("5. Listar zapatillas");
        System.out.println("6. Listar transacciones");
        System.out.println("7. Salir");
    }

    private static int leerEntero(String mensaje) {
        int num;
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();
            try {
                num = Integer.parseInt(entrada);
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número entero.");
            }
        }
    }

    private static void añadirUsuario() {
        System.out.println("\nRegistro de usuario:");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = sc.nextLine();

        Usuario u = new Usuario(nombre, correo, contraseña);
        usuarios.add(u);
        guardarUsuarios();
        System.out.println("Usuario registrado.");
    }

    private static void añadirZapatilla() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Registra un usuario primero.");
            return;
        }

        System.out.println("\nRegistro de zapatillas:");
        System.out.print("Nombre del modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();

        double talla;
        while (true) {
            System.out.print("Talla: ");
            String entrada = sc.nextLine();
            try {
                talla = Double.parseDouble(entrada);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número para la talla.");
            }
        }

        System.out.println("Selecciona el dueño de la zapatilla:");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
        }

        int indiceDueño;
        while (true) {
            indiceDueño = leerEntero("Número del dueño: ");
            if (indiceDueño > 0 && indiceDueño <= usuarios.size()) {
                break;
            } else {
                System.out.println("Número no válido. Intenta de nuevo.");
            }
        }
        Usuario dueño = usuarios.get(indiceDueño - 1);

        Zapatillas z = new Zapatillas(modelo, marca, talla, dueño);
        zapatillas.add(z);
        guardarZapatillas();
        System.out.println("Zapatilla añadida.");
    }

    private static void añadirTransaccion() {
        if (usuarios.isEmpty() || zapatillas.isEmpty()) {
            System.out.println("Necesitas al menos un usuario y una zapatilla para registrar una transacción.");
            return;
        }

        System.out.println("\nRegistrar transacción:");

        System.out.println("Selecciona la zapatilla a comprar:");
        for (int i = 0; i < zapatillas.size(); i++) {
            System.out.println((i + 1) + ". " + zapatillas.get(i));
        }
        int indiceZapa;
        while (true) {
            indiceZapa = leerEntero("Número de la zapatilla: ");
            if (indiceZapa > 0 && indiceZapa <= zapatillas.size()) {
                break;
            } else {
                System.out.println("Número no válido. Intenta de nuevo.");
            }
        }
        Zapatillas zapa = zapatillas.get(indiceZapa - 1);

        System.out.println("Selecciona el comprador:");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
        }
        int indiceComprador;
        while (true) {
            indiceComprador = leerEntero("Número del comprador: ");
            if (indiceComprador > 0 && indiceComprador <= usuarios.size()) {
                break;
            } else {
                System.out.println("Número no válido. Intenta de nuevo.");
            }
        }
        Usuario comprador = usuarios.get(indiceComprador - 1);

        Transaccion t = new Transaccion(zapa, comprador);
        transacciones.add(t);
        guardarTransacciones();
        System.out.println("Transacción registrada:\n" + t);
    }

    private static void listarUsuarios() {
        System.out.println("\nUsuarios registrados:");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println("- " + u);
        }
    }

    private static void listarZapatillas() {
        System.out.println("\nZapatillas registradas:");
        if (zapatillas.isEmpty()) {
            System.out.println("No hay zapatillas.");
            return;
        }
        for (Zapatillas z : zapatillas) {
            System.out.println("- " + z);
        }
    }

    private static void listarTransacciones() {
        System.out.println("\nTransacciones registradas:");
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones.");
            return;
        }
        for (Transaccion t : transacciones) {
            System.out.println("- " + t);
        }
    }

    private static void guardarUsuarios() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            dos.writeInt(usuarios.size());
            for (Usuario u : usuarios) {
                dos.writeUTF(u.getNombre());
                dos.writeUTF(u.getCorreo());
                dos.writeUTF(u.getContraseña());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    private static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) return;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int cantidad = dis.readInt();
            for (int i = 0; i < cantidad; i++) {
                String nombre = dis.readUTF();
                String correo = dis.readUTF();
                String contraseña = dis.readUTF();
                usuarios.add(new Usuario(nombre, correo, contraseña));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    private static void guardarZapatillas() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_ZAPATILLAS))) {
            dos.writeInt(zapatillas.size());
            for (Zapatillas z : zapatillas) {
                dos.writeUTF(z.getNombre());
                dos.writeUTF(z.getMarca());
                dos.writeDouble(z.getTalla());
                int idxDueño = usuarios.indexOf(z.getDueño());
                dos.writeInt(idxDueño);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar zapatillas: " + e.getMessage());
        }
    }

    private static void cargarZapatillas() {
        File archivo = new File(ARCHIVO_ZAPATILLAS);
        if (!archivo.exists()) return;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int cantidad = dis.readInt();
            for (int i = 0; i < cantidad; i++) {
                String nombre = dis.readUTF();
                String marca = dis.readUTF();
                double talla = dis.readDouble();
                int idxDueño = dis.readInt();

                Usuario dueño = null;
                if (idxDueño >= 0 && idxDueño < usuarios.size()) {
                    dueño = usuarios.get(idxDueño);
                } else {
                    System.out.println("Advertencia: dueño de zapatilla no encontrado, se asigna null.");
                }

                zapatillas.add(new Zapatillas(nombre, marca, talla, dueño));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar zapatillas: " + e.getMessage());
        }
    }

    private static void guardarTransacciones() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_TRANSACCIONES))) {
            dos.writeInt(transacciones.size());
            for (Transaccion t : transacciones) {
                // Guardamos índice de zapatilla y de comprador
                int idxZapatilla = zapatillas.indexOf(t.getZapatilla());
                int idxComprador = usuarios.indexOf(t.getComprador());
                dos.writeInt(idxZapatilla);
                dos.writeInt(idxComprador);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar transacciones: " + e.getMessage());
        }
    }

    private static void cargarTransacciones() {
        File archivo = new File(ARCHIVO_TRANSACCIONES);
        if (!archivo.exists()) return;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int cantidad = dis.readInt();
            for (int i = 0; i < cantidad; i++) {
                int idxZapatilla = dis.readInt();
                int idxComprador = dis.readInt();

                Zapatillas z = null;
                Usuario c = null;

                if (idxZapatilla >= 0 && idxZapatilla < zapatillas.size()) {
                    z = zapatillas.get(idxZapatilla);
                } else {
                    System.out.println("Advertencia: zapatilla en transacción no encontrada.");
                }

                if (idxComprador >= 0 && idxComprador < usuarios.size()) {
                    c = usuarios.get(idxComprador);
                } else {
                    System.out.println("Advertencia: comprador en transacción no encontrado.");
                }

                if (z != null && c != null) {
                    transacciones.add(new Transaccion(z, c));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar transacciones: " + e.getMessage());
        }
    }
}

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
    public boolean equals(Object obj) {
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return this.correo.equals(otro.correo);
    }

    @Override
    public int hashCode() {
        return correo.hashCode();
    }

    @Override
    public String toString() {
        return nombre + " (" + correo + ")";
    }
}

class Zapatillas {
    private String nombre;
    private String marca;
    private double talla;
    private Usuario dueño;

    public Zapatillas(String nombre, String marca, double talla, Usuario dueño) {
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
        this.dueño = dueño;
    }

    public String getNombre() {
        return nombre;
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
        return nombre + " de " + marca + ", talla " + talla + ", propiedad de " + (dueño != null ? dueño.getNombre() : "Desconocido");
    }
}

class Transaccion {
    private Zapatillas zapatilla;
    private Usuario comprador;

    public Transaccion(Zapatillas zapatilla, Usuario comprador) {
        this.zapatilla = zapatilla;
        this.comprador = comprador;
    }

    public Zapatillas getZapatilla() {
        return zapatilla;
    }

    public Usuario getComprador() {
        return comprador;
    }

    @Override
    public String toString() {
        return comprador.getNombre() + " compró " + zapatilla.toString();
    }
}
