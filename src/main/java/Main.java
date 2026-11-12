import dao.NotasDAO;
import modelo.ResultadoAcademico;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        BigDecimal[] notas = new BigDecimal[7];

        System.out.println("====================================");
        System.out.println(" SISTEMA DE PROMEDIO DE 7 NOTAS");
        System.out.println(" JAVA 21 - ORACLE DATABASE 19C");
        System.out.println("====================================");

        // INGRESO ! DE LAS SIETE NOTAS !
        for (int i = 0; i < 7; i++) {
            while (true) {
                System.out.print(
                        "Ingrese la nota " + (i + 1) + ": "
                );

                String entrada = teclado.nextLine().trim();

                try {
                    BigDecimal nota = new BigDecimal(entrada.replace(',', '.'));

                    if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal("5.0")) > 0) {
                        System.out.println("Error: La nota debe estar entre 0 y 5!");

                        continue;
                    }

                    notas[i] = nota;

                    break;

                } catch (NumberFormatException e) {

                    System.out.println(
                            "Ingrese un valor numerico valido."
                    );
                }
            }
        }

        // CREAR objeto academico!

        ResultadoAcademico resultado = new ResultadoAcademico(notas);

        // MOSTRAR NOTAS!
        System.out.println("\n====================================");
        System.out.println(" RESULTADOS ACADEMICOS");
        System.out.println("====================================");

        BigDecimal[] notasIngresadas = resultado.getNotas();

        for (int i = 0; i < 7; i++) {
            System.out.println("Nota " + (i + 1) + ": " +
                    notasIngresadas[i]);
        }

        // CALCULAR promedio!
        BigDecimal promedio = resultado.calcularPromedio();
        String estado = resultado.determinarEstado();
        System.out.println("------------------------------------");
        System.out.println("Promedio final: " + promedio);

        System.out.println("Estado academico: " + estado);
        System.out.println("------------------------------------");

        // GUARDAR informacion en ORACLE!
        System.out.println("procesando INFORMACION en ORACLE 19c...");

        NotasDAO dao = new NotasDAO();
        int id = dao.registrarNotas(notas);

        if (id > 0)
        {
            System.out.println("REGISTRO almacenado correctamente!");

            System.out.println("IDENTIFICADOR oracle: " + id);
        } else {
            System.out.println("NO fue posible almacenar la informacion!");
        }

        System.out.println("====================================");
        System.out.println(" FIN DEL PROCESAMIENTO");
        System.out.println("====================================");

        teclado.close();
    }
}   