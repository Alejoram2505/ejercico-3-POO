import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Estudiante {
    private String nombre;
    private String apellido;
    private int codigoUnico;
    private String fechaNacimiento;
    private String correoElectronico;
    private Map<String, Integer> resultadosExamenes;

    public Estudiante(String nombre, String apellido, int codigoUnico, String fechaNacimiento, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoUnico = codigoUnico;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.resultadosExamenes = new HashMap<>();
    }

    public void agregarResultadoExamen(String materia, int nota) {
        resultadosExamenes.put(materia, nota);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getCodigoUnico() {
        return codigoUnico;
    }

    public Map<String, Integer> getResultadosExamenes() {
        return resultadosExamenes;
    }
}

public class universidad {
    private Map<String, List<Estudiante>> sedes;

    public universidad() {
        sedes = new HashMap<>();
    }

    public void registrarEstudiante(String sede, Estudiante estudiante) {
        sedes.computeIfAbsent(sede, k -> new ArrayList<>()).add(estudiante);
    }

    public void ingresarNotasExamen(String sede, int codigoUnico, String materia, int nota) {
        List<Estudiante> estudiantes = sedes.get(sede);
        if (estudiantes != null) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getCodigoUnico() == codigoUnico) {
                    estudiante.agregarResultadoExamen(materia, nota);
                    return;
                }
            }
        }
        System.out.println("Estudiante no encontrado en la sede " + sede);
    }

    public void mostrarEstadisticas(String sede) {
        List<Estudiante> estudiantes = sedes.get(sede);
        if (estudiantes != null && !estudiantes.isEmpty()) {
            Map<String, List<Integer>> resultadosPorMateria = new HashMap<>();
            for (Estudiante estudiante : estudiantes) {
                Map<String, Integer> resultados = estudiante.getResultadosExamenes();
                for (Map.Entry<String, Integer> entry : resultados.entrySet()) {
                    String materia = entry.getKey();
                    int nota = entry.getValue();
                    resultadosPorMateria.computeIfAbsent(materia, k -> new ArrayList<>()).add(nota);
                }
            }

            for (Map.Entry<String, List<Integer>> entry : resultadosPorMateria.entrySet()) {
                String materia = entry.getKey();
                List<Integer> notas = entry.getValue();

                int cantidadEstudiantes = notas.size();
                int notaMinima = notas.stream().min(Integer::compareTo).orElse(0);
                int notaMaxima = notas.stream().max(Integer::compareTo).orElse(0);

                double promedio = notas.stream().mapToInt(Integer::intValue).average().orElse(0);
                double mediana = calcularMediana(notas);
                int moda = calcularModa(notas);
                double desviacionEstandar = calcularDesviacionEstandar(notas, promedio);

                System.out.println("Estadísticas para la materia " + materia + " en la sede " + sede);
                System.out.println("Cantidad de estudiantes: " + cantidadEstudiantes);
                System.out.println("Nota mínima: " + notaMinima);
                System.out.println("Nota máxima: " + notaMaxima);
                System.out.println("Promedio: " + promedio);
                System.out.println("Mediana: " + mediana);
                System.out.println("Moda: " + moda);
                System.out.println("Desviación estándar: " + desviacionEstandar);
                System.out.println();
            }
        } else {
            System.out.println("No hay estudiantes registrados en la sede " + sede);
        }
    }

    private double calcularMediana(List<Integer> lista) {
        lista.sort(Integer::compareTo);
        int n = lista.size();
        if (n % 2 == 0) {
            int medio1 = lista.get(n / 2 - 1);
            int medio2 = lista.get(n / 2);
            return (double) (medio1 + medio2) / 2;
        } else {
            return lista.get(n / 2);
        }
    }

    private int calcularModa(List<Integer> lista) {
        Map<Integer, Integer> conteo = new HashMap<>();
        int moda = 0;
        int maxFrecuencia = 0;

        for (int num : lista) {
            int frecuencia = conteo.getOrDefault(num, 0) + 1;
            conteo.put(num, frecuencia);

            if (frecuencia > maxFrecuencia) {
                moda = num;
                maxFrecuencia = frecuencia;
            }
        }
        return moda;
    }

    private double calcularDesviacionEstandar(List<Integer> lista, double promedio) {
        double sumatoria = 0;
        for (int num : lista) {
            sumatoria += Math.pow(num - promedio, 2);
        }
        return Math.sqrt(sumatoria / lista.size());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        universidad universidad = new universidad();

        while (true) {
            System.out.println("Ingrese una opción:");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Ingresar notas de examen");
            System.out.println("3. Mostrar estadísticas");
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del estudiante:");
                    String nombre = scanner.nextLine();

                    System.out.println("Ingrese el apellido del estudiante:");
                    String apellido = scanner.nextLine();

                    System.out.println("Ingrese el código único del estudiante:");
                    int codigoUnico = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese la fecha de nacimiento del estudiante:");
                    String fechaNacimiento = scanner.nextLine();

                    System.out.println("Ingrese el correo electrónico del estudiante:");
                    String correoElectronico = scanner.nextLine();

                    System.out.println("Ingrese la sede del estudiante:");
                    String sedeRegistro = scanner.nextLine();

                    Estudiante estudiante = new Estudiante(nombre, apellido, codigoUnico, fechaNacimiento, correoElectronico);
                    universidad.registrarEstudiante(sedeRegistro, estudiante);

                    System.out.println("Estudiante registrado con éxito.");
                    break;
                case 2:
                    System.out.println("Ingrese la sede donde desea ingresar notas:");
                    String sedeNotas = scanner.nextLine();

                    System.out.println("Ingrese el código único del estudiante:");
                    int codigoUnicoNotas = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese la materia del examen:");
                    String materia = scanner.nextLine();

                    System.out.println("Ingrese la nota del examen:");
                    int nota = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    universidad.ingresarNotasExamen(sedeNotas, codigoUnicoNotas, materia, nota);

                    System.out.println("Nota ingresada con éxito.");
                    break;
                case 3:
                    System.out.println("Ingrese la sede de la cual desea ver estadísticas:");
                    String sedeEstadisticas = scanner.nextLine();
                    universidad.mostrarEstadisticas(sedeEstadisticas);
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
}
