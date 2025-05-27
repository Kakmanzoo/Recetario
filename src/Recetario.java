import java.util.LinkedList;
import java.util.Scanner;

public class Recetario {

    public static void leer(LinkedList<String> receta) {
        if (receta.isEmpty()) {
            System.out.println("La receta está vacía.");
            return;
        }
        System.out.println("\n📋 Pasos de la Receta:");
        for (int i = 0; i < receta.size(); i++) {
            System.out.println((i + 1) + ". " + receta.get(i));
        }
    }

    public static void agregar(LinkedList<String> receta, String nuevoPaso) {
        receta.add(nuevoPaso);
        System.out.println("✅ Paso agregado con éxito.");
    }

    public static void actualizar(LinkedList<String> receta, int pasoNumero, String nuevoTexto) {
        if (pasoNumero <= 0 || pasoNumero > receta.size()) {
            System.out.println("❌ Paso inválido.");
            return;
        }
        receta.set(pasoNumero - 1, nuevoTexto);
        System.out.println("🔄 Paso actualizado con éxito.");
    }

    public static void eliminar(LinkedList<String> receta, int pasoNumero) {
        if (pasoNumero <= 0 || pasoNumero > receta.size()) {
            System.out.println("❌ Paso inválido.");
            return;
        }
        receta.remove(pasoNumero - 1);
        System.out.println("🗑️ Paso eliminado con éxito.");
    }

    public static void buscar(LinkedList<String> receta, String textoBusqueda) {
        boolean encontrado = false;
        System.out.println("🔍 Resultados de búsqueda:");
        for (String paso : receta) {
            if (paso.toLowerCase().contains(textoBusqueda.toLowerCase())) {
                System.out.println(paso);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró ningún paso con ese texto.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<String> pasos = new LinkedList<>();

        pasos.add("Romper 2 huevos en un recipiente.");
        pasos.add("Batir los huevos con sal y pimienta.");
        pasos.add("Calentar una sartén con mantequilla.");
        pasos.add("Verter los huevos batidos.");
        pasos.add("Revolver hasta cocer.");
        pasos.add("Servir caliente.");

        boolean salir = false;

        while (!salir) {
            System.out.println("\n📌 Menú:");
            System.out.println("1. Leer receta");
            System.out.println("2. Agregar paso");
            System.out.println("3. Actualizar paso");
            System.out.println("4. Eliminar paso");
            System.out.println("5. Buscar paso");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpia buffer

            switch (opcion) {
                case 1 -> leer(pasos);
                case 2 -> {
                    System.out.print("Escribe el nuevo paso: ");
                    String nuevo = sc.nextLine();
                    agregar(pasos, nuevo);
                }
                case 3 -> {
                    System.out.print("Número del paso a actualizar: ");
                    int num = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo texto del paso: ");
                    String nuevoTexto = sc.nextLine();
                    actualizar(pasos, num, nuevoTexto);
                }
                case 4 -> {
                    System.out.print("Número del paso a eliminar: ");
                    int num = sc.nextInt();
                    eliminar(pasos, num);
                }
                case 5 -> {
                    System.out.print("Texto a buscar: ");
                    String texto = sc.nextLine();
                    buscar(pasos, texto);
                }
                case 6 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}

