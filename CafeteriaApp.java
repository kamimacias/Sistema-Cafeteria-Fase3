import java.util.Scanner;
public class CafeteriaApp {
    // Estructura para los productos del menú
    static class Producto {
        String nombre;
        double precio;
        Producto siguiente;
        
        public Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
            this.siguiente = null;
        }
    }
    static class Pedido {
        int idPedido;
        String nombreCliente;
        String descripcion;

        public Pedido(int id, String cliente, String desc) {
            this.idPedido = id;
            this.nombreCliente = cliente;
            this.descripcion = desc;
        }
    }
    static class Nodo {
        Pedido pedido;
        Nodo siguiente;

        public Nodo(Pedido pedido) {
            this.pedido = pedido;
            this.siguiente = null;
        }
    }
    static class ColaCafeteria {
        private Nodo frente;
        private Nodo finalCola;
        // Lista enlazada para el menú dinámico
        private Producto cabezaMenu;
        public ColaCafeteria() {
            this.frente = null;
            this.finalCola = null;
            this.cabezaMenu = null;
            inicializarMenu();
        }
        // Cargar productos usando memoria dinámica
        private void inicializarMenu() {
            agregarProductoMenu("Café Americano", 2.00);
            agregarProductoMenu("Capuchino", 3.50);
            agregarProductoMenu("Sándwich de Queso", 4.00);
            agregarProductoMenu("Pastel de Chocolate", 3.00);
        }
        public void agregarProductoMenu(String nombre, double precio) {
            Producto nuevo = new Producto(nombre, precio);
            if (cabezaMenu == null) {
                cabezaMenu = nuevo;
            } else {
                Producto actual = cabezaMenu;
                while (actual.siguiente != null) {
                    actual = actual.siguiente;
                }
                actual.siguiente = nuevo;
            }
        }
        public void mostrarMenu() {
            if (cabezaMenu == null) {
                System.out.println("[!] El menú está vacío.");
                return;
            }
            System.out.println("\n========================================");
            System.out.println("          MENÚ DE LA CAFETERÍA          ");
            System.out.println("========================================");
            Producto actual = cabezaMenu;
            int i = 1;
            while (actual != null) {
                System.out.println(i + ". " + actual.nombre + " - $" + String.format("%.2f", actual.precio));
                actual = actual.siguiente;
                i++;
            }
            System.out.println("========================================");
        }
        public Producto obtenerProductoPorIndice(int indice) {
            Producto actual = cabezaMenu;
            int contador = 1;
            while (actual != null) {
                if (contador == indice) {
                    return actual;
                }
                actual = actual.siguiente;
                contador++;
            }
            return null;
        }
        public boolean estaVacia() {
            return frente == null;
        }
        public void encolar(int id, String cliente, String desc) {
            Pedido nuevoPedido = new Pedido(id, cliente, desc);
            Nodo nuevoNodo = new Nodo(nuevoPedido);
            if (estaVacia()) {
                frente = nuevoNodo;
                finalCola = nuevoNodo;
            } else {
                finalCola.siguiente = nuevoNodo;
                finalCola = nuevoNodo;
            }
            System.out.println("[+] Pedido #" + id + " de " + cliente + " agregado a la cola.");
        }
        public void desencolar() {
            if (estaVacia()) {
                System.out.println("[!] La cola de la cafetería está vacía.");
                return;
            }
            Nodo temp = frente;
            System.out.println("[✔] Atendiendo pedido #" + temp.pedido.idPedido 
                    + " a nombre de " + temp.pedido.nombreCliente 
                    + " (" + temp.pedido.descripcion + ")");
            frente = frente.siguiente;
            if (frente == null) {
                finalCola = null;
            }
        }
        public void mostrarCola() {
            if (estaVacia()) {
                System.out.println("[i] La cola está actualmente vacía.");
                return;
            }
            Nodo actual = frente;
            System.out.println("\n--- ESTADO ACTUAL DE LA COLA DE LA CAFETERÍA ---");
            while (actual != null) {
                System.out.println("-> [Pedido ID: " + actual.pedido.idPedido 
                        + " | Cliente: " + actual.pedido.nombreCliente 
                        + " | Detalle: " + actual.pedido.descripcion + "]");
                actual = actual.siguiente;
            }
            System.out.println("----------------------------------------------\n");
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColaCafeteria cafeteria = new ColaCafeteria();
        int opcion = 0;
        int contadorId = 1;
        do {
            System.out.println("\n========================================");
            System.out.println("      SISTEMA DE GESTIÓN CAFETERÍA      ");
            System.out.println("========================================");
            System.out.println("1. Ver Menú");
            System.out.println("2. Hacer Pedido");
            System.out.println("3. Ver Cola de Pedidos (Punteros)");
            System.out.println("4. Atender Siguiente Pedido");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    cafeteria.mostrarMenu();
                    break;
                case 2:
                    cafeteria.mostrarMenu();
                    System.out.print("Seleccione el número del producto: ");
                    int numProd = Integer.parseInt(scanner.nextLine());
                    Producto prodSeleccionado = cafeteria.obtenerProductoPorIndice(numProd);
                    if (prodSeleccionado != null) {
                        System.out.print("Nombre del cliente: ");
                        String cliente = scanner.nextLine();
                        cafeteria.encolar(contadorId++, cliente.toUpperCase(), prodSeleccionado.nombre);
                    } else {
                        System.out.println("[!] Producto no válido.");
                    }
                    break;
                case 3:
                    cafeteria.mostrarCola();
                    break;
                case 4:
                    cafeteria.desencolar();
                    break;
                case 5:
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("\n[!] Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
