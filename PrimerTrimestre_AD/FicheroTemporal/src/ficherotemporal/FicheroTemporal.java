package ficherotemporal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FicheroTemporal {

    public static void main(String[] args) {
        File originalFile = new File("original.txt");
        File tempFile = new File("temporal.txt");

        try {
            // Crear un nuevo archivo temporal
            tempFile.createNewFile();

            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            // Leer cada línea del archivo original
            while ((line = reader.readLine()) != null) {
                // Actualizar la línea deseada
                if (line.contains("sexo")) {
                    line = line.replace("sexo", "ultraSexo");
                }

                // Escribir la línea actualizada en el archivo temporal
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Eliminar el archivo original
            originalFile.delete();

            // Renombrar el archivo temporal como el archivo original
            tempFile.renameTo(originalFile);

            System.out.println("El archivo ha sido actualizado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }
}