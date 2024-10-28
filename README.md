
# Agriculture Data Processor

## Descripción

Este programa permite leer, procesar y almacenar datos provenientes de sensores agrícolas, proporcionando opciones de exportación para bases de datos SQL, NoSQL o en formato JSON.

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone <URL-del-repositorio>
   ```
2. Configurar el entorno de ejecución:
   - Requiere Java 17 o superior
   - Dependencias adicionales indicadas en `build.gradle`.

3. Configurar el archivo `config.properties` en el directorio `resources` para especificar directorios de entrada y salida (ver ejemplo más abajo).

## Uso

1. Ejecutar el programa:
   ```bash
   java -jar agriculture-data-processor.jar
   ```
2. **Flujo de trabajo**:
   - Cargar archivo de texto con datos del sensor.
   - Confirmar carga exitosa de los datos.
   - Ingresar información adicional del cultivo (tipo de cultivo, temperatura de agua, volumen de riego).
   - Seleccionar el formato de exportación: SQL, NoSQL o JSON.
   - Confirmar la exportación o continuar usando el programa.

## Estructura del Proyecto

- **Main.java**: Punto de entrada principal del programa.
- **controllers/DataProcessorController**: Controla la lógica de lectura, validación y exportación de datos.
- **models/CropData**: Representa los datos de cultivo y riego.
- **services/FileService**: Administra la lectura de archivos de texto y validación de datos.
- **services/ConfirmationService**: Gestiona los mensajes de confirmación para el usuario.
- **services/ExportService**: Exporta los datos en SQL, NoSQL o JSON.
- **utils/LoggerUtil**: Define configuraciones de logging y otros registros.
- **views/UserInterface**: Interfaz para la interacción con el usuario.

## Configuración de Logs (`LoggerUtil`)

La configuración de logs permite definir el nivel de registro en el archivo `config.properties`. Cambie `log.level` a `DEBUG` para ver mensajes detallados durante la ejecución.

## Archivo de Configuración (`config.properties`)

Ejemplo de archivo `config.properties`:

```properties
# Configuración de la aplicación
input.file.path=./data/sensors_data.txt
output.directory.path=./output
log.level=INFO
```

## Guía para Desarrolladores

### Extensiones Futuras

1. **Agregar formatos de salida**: El `ExportService` puede extenderse para incluir otros formatos de archivo.
2. **Pruebas Unitarias**: Asegurarse de que cada módulo está cubierto por pruebas unitarias.

## Ejemplos de Código

**Lectura del archivo de texto**:

```java
public class FileService {
    public List<String> readFile(String filePath) throws IOException {
        List<String> dataLines = Files.readAllLines(Paths.get(filePath));
        if (dataLines.isEmpty()) {
            throw new IOException("El archivo está vacío o no contiene datos válidos.");
        }
        return dataLines;
    }
}
```

**Confirmación de Acción**:

```java
public class ConfirmationService {
    public void confirmReadSuccess() {
        System.out.println("Datos cargados exitosamente.");
    }

    public void confirmReadError() {
        System.out.println("Error al cargar el archivo. Por favor, intente nuevamente.");
    }
}
```

**Exportar a JSON**:

```java
public class ExportService {
    public void exportToJSON(CropData data, String outputPath) throws IOException {
        String jsonString = new Gson().toJson(data);
        Files.write(Paths.get(outputPath), jsonString.getBytes());
    }
}
```

## Pruebas Unitarias

1. **FileService**: Verifica que el archivo se lea correctamente y lanza una excepción si está vacío.
2. **ConfirmationService**: Confirma que los mensajes se muestren correctamente.
3. **ExportService**: Asegura que los datos exportados tengan el formato esperado.

## Buenas Prácticas

- **Modularidad**: Cada clase debe cumplir con un único propósito para facilitar el mantenimiento.
- **Gestión de errores**: Manejar adecuadamente las excepciones, especialmente en las operaciones de E/S.
- **Documentación**: Incluir comentarios en el código y mantener el README actualizado.

