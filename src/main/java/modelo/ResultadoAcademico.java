package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResultadoAcademico {

    private final BigDecimal[] notas;

    public ResultadoAcademico(BigDecimal[] notas)
    {
        this.notas = notas;
    }

    public BigDecimal calcularPromedio()
    {
        BigDecimal suma = BigDecimal.ZERO;

        for (BigDecimal nota : notas)
        {
            suma = suma.add(nota);
        }

        return suma.divide(
                BigDecimal.valueOf(7),
                2,
                RoundingMode.HALF_UP
        );
    }

    public String determinarEstado()
    {
        if (calcularPromedio().compareTo(new BigDecimal("3.00")) >= 0)
        {
            return "APROBADO!";
        } else {
            return "REPROBADO!";
        }
    }

    public BigDecimal[] getNotas()
    {
        return notas;
    }
}   
