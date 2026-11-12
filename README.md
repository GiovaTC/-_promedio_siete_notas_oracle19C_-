# -_promedio_siete_notas_oracle19C_- :.
# PromedioSieteNotasOracle19c:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/176702a7-22ee-4474-be3d-f8acb113d999" />  

<img width="2551" height="1075" alt="image" src="https://github.com/user-attachments/assets/5d4d058b-4400-4662-8533-6f23985830fe" />  
  
## Sistema de cálculo del promedio de siete notas utilizando Java 21 y Oracle Database 19c

Aplicación desarrollada en Java utilizando IntelliJ IDEA, Maven y JDBC, que permite ingresar siete notas por teclado, calcular el promedio académico, determinar si el estudiante aprobó o reprobó y almacenar los resultados en Oracle Database 19c mediante un procedimiento almacenado.

---

## 1. Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java JDK | 21 |
| IntelliJ IDEA | 2026 |
| Oracle Database | 19c |
| Maven | 3.x |
| JDBC | ojdbc11 |
| SQL Developer | Compatible con Oracle 19c |

## 2. Funcionalidades

- Ingresar siete notas por teclado.
- Validar notas entre 0.0 y 5.0.
- Calcular el promedio académico.
- Determinar el estado del estudiante.
- Mostrar los resultados en consola.
- Conectarse a Oracle Database 19c.
- Ejecutar un procedimiento almacenado.
- Registrar las siete notas en la base de datos.
- Recuperar el identificador generado por Oracle.

## 3. Fórmula matemática

El programa utiliza la siguiente fórmula:

```text
PROMEDIO = (NOTA1 + NOTA2 + NOTA3 +
            NOTA4 + NOTA5 + NOTA6 +
            NOTA7) / 7
```

Criterio académico:

```text
Promedio >= 3.0  = APROBADO

Promedio < 3.0   = REPROBADO
```

## 4. Estructura del proyecto

```text
PromedioSieteNotasOracle19c/
│
├── pom.xml
│
├── README.md
│
└── src/
    └── main/
        └── java/
            │
            ├── config/
            │   └── ConexionOracle.java
            │
            ├── modelo/
            │   └── ResultadoAcademico.java
            │
            ├── dao/
            │   └── NotasDAO.java
            │
            └── Main.java
```

---

# 5. Configuración de Oracle Database 19c

## 5.1. Crear la tabla

Ejecutar el siguiente script en Oracle SQL Developer:

```sql
CREATE TABLE NOTAS_ESTUDIANTES (

    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    NOTA1 NUMBER(4,2) NOT NULL,
    NOTA2 NUMBER(4,2) NOT NULL,
    NOTA3 NUMBER(4,2) NOT NULL,
    NOTA4 NUMBER(4,2) NOT NULL,
    NOTA5 NUMBER(4,2) NOT NULL,
    NOTA6 NUMBER(4,2) NOT NULL,
    NOTA7 NUMBER(4,2) NOT NULL,

    PROMEDIO NUMBER(5,2) NOT NULL,

    ESTADO VARCHAR2(20),

    FECHA_REGISTRO DATE DEFAULT SYSDATE,

    CONSTRAINT CK_NOTA1 CHECK (NOTA1 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA2 CHECK (NOTA2 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA3 CHECK (NOTA3 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA4 CHECK (NOTA4 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA5 CHECK (NOTA5 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA6 CHECK (NOTA6 BETWEEN 0 AND 5),
    CONSTRAINT CK_NOTA7 CHECK (NOTA7 BETWEEN 0 AND 5)

);
```

## 5.2. Crear procedimiento almacenado

Nombre del procedimiento:

`PR_REGISTRAR_NOTAS`

```sql
CREATE OR REPLACE PROCEDURE PR_REGISTRAR_NOTAS (

    P_NOTA1 IN NUMBER,
    P_NOTA2 IN NUMBER,
    P_NOTA3 IN NUMBER,
    P_NOTA4 IN NUMBER,
    P_NOTA5 IN NUMBER,
    P_NOTA6 IN NUMBER,
    P_NOTA7 IN NUMBER,

    P_ID OUT NUMBER

)
AS

    V_PROMEDIO NUMBER(5,2);
    V_ESTADO VARCHAR2(20);

BEGIN

    V_PROMEDIO := ROUND(

        (P_NOTA1 + P_NOTA2 + P_NOTA3 +
         P_NOTA4 + P_NOTA5 + P_NOTA6 +
         P_NOTA7) / 7,

        2

    );

    IF V_PROMEDIO >= 3.0 THEN

        V_ESTADO := 'APROBADO';

    ELSE

        V_ESTADO := 'REPROBADO';

    END IF;

    INSERT INTO NOTAS_ESTUDIANTES (

        NOTA1,
        NOTA2,
        NOTA3,
        NOTA4,
        NOTA5,
        NOTA6,
        NOTA7,
        PROMEDIO,
        ESTADO

    )

    VALUES (

        P_NOTA1,
        P_NOTA2,
        P_NOTA3,
        P_NOTA4,
        P_NOTA5,
        P_NOTA6,
        P_NOTA7,
        V_PROMEDIO,
        V_ESTADO

    )

    RETURNING ID INTO P_ID;

    COMMIT;

END PR_REGISTRAR_NOTAS;
/
```

## 5.3. Consultar registros

```sql
SELECT
    ID,
    NOTA1,
    NOTA2,
    NOTA3,
    NOTA4,
    NOTA5,
    NOTA6,
    NOTA7,
    PROMEDIO,
    ESTADO,
    FECHA_REGISTRO
FROM NOTAS_ESTUDIANTES
ORDER BY ID DESC;
```

---

# 6. Configuración del proyecto Maven

Archivo: `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.promedio</groupId>

    <artifactId>PromedioSieteNotasOracle19c</artifactId>

    <version>1.0-SNAPSHOT</version>

    <properties>

        <maven.compiler.source>21</maven.compiler.source>

        <maven.compiler.target>21</maven.compiler.target>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    </properties>

    <dependencies>

        <dependency>

            <groupId>com.oracle.database.jdbc</groupId>

            <artifactId>ojdbc11</artifactId>

            <version>23.6.0.24.10</version>

        </dependency>

    </dependencies>

</project>
```

---

# 7. Código fuente Java completo

## 7.1. Clase ConexionOracle.java

Ubicación: `src/main/java/config/ConexionOracle.java`

```java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@//localhost:1521/orcl";

    private static final String USUARIO = "system";

    private static final String PASSWORD = "TU_PASSWORD";

    public static Connection conectar() {

        try {

            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
            );

            System.out.println(
                    "Conexion exitosa con Oracle 19c."
            );

            return conexion;

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexion: " + e.getMessage()
            );

            return null;

        }

    }

}
```

Importante: reemplazar `TU_PASSWORD` por la contraseña correspondiente al usuario de Oracle.

## 7.2. Clase ResultadoAcademico.java

Ubicación: `src/main/java/modelo/ResultadoAcademico.java`

```java
package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResultadoAcademico {

    private final BigDecimal[] notas;

    public ResultadoAcademico(BigDecimal[] notas) {

        this.notas = notas;

    }

    public BigDecimal calcularPromedio() {

        BigDecimal suma = BigDecimal.ZERO;

        for (BigDecimal nota : notas) {

            suma = suma.add(nota);

        }

        return suma.divide(

                BigDecimal.valueOf(7),

                2,

                RoundingMode.HALF_UP

        );

    }

    public String determinarEstado() {

        if (calcularPromedio().compareTo(

                new BigDecimal("3.00")) >= 0) {

            return "APROBADO";

        } else {

            return "REPROBADO";

        }

    }

    public BigDecimal[] getNotas() {

        return notas;

    }

}
```

## 7.3. Clase NotasDAO.java

Ubicación: `src/main/java/dao/NotasDAO.java`

```java
package dao;

import config.ConexionOracle;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.math.BigDecimal;

public class NotasDAO {

    public int registrarNotas(BigDecimal[] notas) {

        String sql = "{CALL PR_REGISTRAR_NOTAS(?,?,?,?,?,?,?,?)}";

        try (Connection conexion = ConexionOracle.conectar()) {

            if (conexion == null) {

                return -1;

            }

            try (CallableStatement cs =
                         conexion.prepareCall(sql)) {

                for (int i = 0; i < 7; i++) {

                    cs.setBigDecimal(i + 1, notas[i]);

                }

                cs.registerOutParameter(8, Types.NUMERIC);

                cs.execute();

                return cs.getInt(8);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al registrar notas: " +
                    e.getMessage()
            );

            return -1;

        }

    }

}
```

## 7.4. Clase Main.java

Ubicación: `src/main/java/Main.java`

```java
import dao.NotasDAO;
import modelo.ResultadoAcademico;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        BigDecimal[] notas = new BigDecimal[7];

        System.out.println("====================================");

        System.out.println(" SISTEMA DE PROMEDIO DE 7 NOTAS");

        System.out.println(" JAVA 21 - ORACLE DATABASE 19C");

        System.out.println("====================================");

        // INGRESO DE LAS SIETE NOTAS

        for (int i = 0; i < 7; i++) {

            while (true) {

                System.out.print(
                        "Ingrese la nota " + (i + 1) + ": "
                );

                String entrada = teclado.nextLine().trim();

                try {

                    BigDecimal nota = new BigDecimal(
                            entrada.replace(',', '.')
                    );

                    if (nota.compareTo(BigDecimal.ZERO) < 0 ||
                            nota.compareTo(
                                    new BigDecimal("5.0")) > 0) {

                        System.out.println(
                                "Error: la nota debe estar entre 0 y 5."
                        );

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

        // CREAR OBJETO ACADEMICO

        ResultadoAcademico resultado =
                new ResultadoAcademico(notas);

        // MOSTRAR NOTAS

        System.out.println("\n====================================");

        System.out.println(" RESULTADOS ACADEMICOS");

        System.out.println("====================================");

        BigDecimal[] notasIngresadas =
                resultado.getNotas();

        for (int i = 0; i < 7; i++) {

            System.out.println(

                    "Nota " + (i + 1) + ": " +
                            notasIngresadas[i]

            );

        }

        // CALCULAR PROMEDIO

        BigDecimal promedio =
                resultado.calcularPromedio();

        String estado =
                resultado.determinarEstado();

        System.out.println("------------------------------------");

        System.out.println(
                "Promedio final: " + promedio
        );

        System.out.println(
                "Estado academico: " + estado
        );

        System.out.println("------------------------------------");

        // GUARDAR INFORMACION EN ORACLE

        System.out.println(
                "Procesando informacion en Oracle 19c..."
        );

        NotasDAO dao = new NotasDAO();

        int id = dao.registrarNotas(notas);

        if (id > 0) {

            System.out.println(
                    "Registro almacenado correctamente."
            );

            System.out.println(
                    "Identificador Oracle: " + id
            );

        } else {

            System.out.println(
                    "No fue posible almacenar la informacion."
            );

        }

        System.out.println("====================================");

        System.out.println(" FIN DEL PROCESAMIENTO");

        System.out.println("====================================");

        teclado.close();

    }

}
```

---

# 8. Configuración en IntelliJ IDEA

### Paso 1. Crear proyecto

1. Abrir IntelliJ IDEA.
2. Seleccionar New Project.
3. Elegir Java.
4. Seleccionar JDK 21.
5. Seleccionar Maven.
6. Asignar el nombre `PromedioSieteNotasOracle19c`.

### Paso 2. Crear paquetes

Dentro de `src/main/java`, crear:

- `config`
- `modelo`
- `dao`

Crear también la clase principal `Main.java`.

### Paso 3. Configurar Oracle

En `ConexionOracle.java`, modificar:

```java
private static final String URL =
        "jdbc:oracle:thin:@//localhost:1521/orcl";

private static final String USUARIO = "system";

private static final String PASSWORD = "TU_PASSWORD";
```

### Paso 4. Configurar Maven

Guardar el archivo `pom.xml`.

En IntelliJ IDEA, seleccionar:

**Maven → Reload All Maven Projects**

Verificar que se descargue correctamente el controlador `ojdbc11`.

### Paso 5. Ejecutar scripts SQL

En Oracle SQL Developer:

1. Crear la tabla `NOTAS_ESTUDIANTES`.
2. Crear el procedimiento `PR_REGISTRAR_NOTAS`.
3. Verificar que el procedimiento se encuentre compilado correctamente.

### Paso 6. Ejecutar Java

Abrir `Main.java`.

Seleccionar:

**Run → Run Main**

Ingresar las siete notas solicitadas.

---

# 9. Ejemplo de ejecución

Datos de prueba:

| Evaluación | Nota |
|---|---:|
| Nota 1 | 4.5 |
| Nota 2 | 3.8 |
| Nota 3 | 4.0 |
| Nota 4 | 3.5 |
| Nota 5 | 4.2 |
| Nota 6 | 4.8 |
| Nota 7 | 3.9 |

Resultado matemático:

```text
PROMEDIO = 28.7 / 7

PROMEDIO = 4.10

ESTADO = APROBADO
```

Resultado esperado en consola:

```text
====================================
 SISTEMA DE PROMEDIO DE 7 NOTAS
 JAVA 21 - ORACLE DATABASE 19C
====================================

Ingrese la nota 1: 4.5
Ingrese la nota 2: 3.8
Ingrese la nota 3: 4.0
Ingrese la nota 4: 3.5
Ingrese la nota 5: 4.2
Ingrese la nota 6: 4.8
Ingrese la nota 7: 3.9

====================================
 RESULTADOS ACADEMICOS
====================================

Nota 1: 4.5
Nota 2: 3.8
Nota 3: 4.0
Nota 4: 3.5
Nota 5: 4.2
Nota 6: 4.8
Nota 7: 3.9

------------------------------------
Promedio final: 4.10
Estado academico: APROBADO
------------------------------------

Procesando informacion en Oracle 19c...

Conexion exitosa con Oracle 19c.

Registro almacenado correctamente.

Identificador Oracle: 1

====================================
 FIN DEL PROCESAMIENTO
====================================
```

---

# 10. Verificación en Oracle Database 19c

Ejecutar:

```sql
SELECT
    ID,
    NOTA1,
    NOTA2,
    NOTA3,
    NOTA4,
    NOTA5,
    NOTA6,
    NOTA7,
    PROMEDIO,
    ESTADO,
    FECHA_REGISTRO
FROM NOTAS_ESTUDIANTES
ORDER BY ID DESC;
```

Resultado de referencia:

| ID | Promedio | Estado |
|---:|---:|---|
| 1 | 4.10 | APROBADO |

---

# 11. Consideraciones técnicas

- El proyecto utiliza JDBC para la conexión entre Java y Oracle.
- El controlador `ojdbc11` es compatible con Java 21.
- El cálculo del promedio utiliza `BigDecimal` para trabajar con precisión decimal.
- Las notas se validan antes de enviarlas a Oracle.
- Oracle ejecuta el procedimiento almacenado.
- El procedimiento determina nuevamente el promedio y el estado académico.
- El identificador generado se devuelve a Java mediante un parámetro `OUT`.
- El procedimiento realiza el `COMMIT` después de insertar el registro.

**Nota:** Para un entorno de producción, es recomendable utilizar un usuario de base de datos dedicado con permisos específicos, en lugar de utilizar `SYSTEM`. También conviene administrar las credenciales fuera del código fuente.

---

# 12. Autor

**Proyecto:** Promedio de Siete Notas

**Lenguaje:** Java 21

**Base de datos:** Oracle Database 19c

**IDE:** IntelliJ IDEA

**Arquitectura:** Java + JDBC + DAO + PL/SQL

**Tipo de aplicación:** Consola

---

## 13. Licencia

Proyecto desarrollado con fines educativos y de aprendizaje de programación Java, conexión JDBC y procesamiento de información mediante procedimientos almacenados en Oracle Database 19c.

