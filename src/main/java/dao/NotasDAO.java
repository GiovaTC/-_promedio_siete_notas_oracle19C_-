package dao;

import config.ConexionOracle;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.math.BigDecimal;

public class NotasDAO {

    public int registrarNotas(BigDecimal[] notas)
    {
        String sql = "{CALL PR_REGISTRAR_NOTAS(?,?,?,?,?,?,?,?)}";

        try (Connection conexion = ConexionOracle.conectar())
        {
            if (conexion == null)
            {
                return -1;
            }

            try (CallableStatement cs = conexion.prepareCall(sql))
            {
                for (int i = 0; i < 7; i++)
                {
                    cs.setBigDecimal(i + 1, notas[i]);
                }

                cs.registerOutParameter(8, Types.NUMERIC);
                cs.execute();

                return cs.getInt(8);
            }
        } catch (SQLException e)
        {
            System.out.println(
                    "Error al registrar notas: " +
                            e.getMessage()
            );

            return -1;
        }
    }
}   
