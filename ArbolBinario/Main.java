public class Main {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();

        System.out.println("¿El árbol está vacío?: " + arbol.vacio());

        arbol.insertar("Netillo");
        arbol.insertar("Profe");
        arbol.insertar("Primo");
        arbol.insertar("Messi");
        arbol.insertar("Cristiano");

        System.out.println("¿El árbol está vacío?: " + arbol.vacio());

       
        System.out.print("Recorrido en orden: ");
        arbol.imprimirArbol();

        
        String buscar = "Maria";
        Nodo resultado = arbol.buscarNodo(buscar);
        if (resultado != null) {
            System.out.println("Nodo encontrado: " + resultado.nombre);
        } else {
            System.out.println("Nodo " + buscar + " no encontrado.");
        }
    }
}
