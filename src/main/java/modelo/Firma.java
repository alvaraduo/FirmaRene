package modelo;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * Clase que crea la firma de los documentos.
 */
public class Firma {

    public void firmarDocumento(String ruta, PrivateKey clavePrivada, String rutaFirma) throws Exception {
        // Crear firma digital
        byte[] datosDocumento = leerArchivo(ruta);
        Signature firma = Signature.getInstance("SHA1withRSA");
        firma.initSign(clavePrivada);
        firma.update(datosDocumento);
        byte[] firmaDigital = firma.sign();

        // Guardar la firma en un archivo
        guardarFirma(rutaFirma, firmaDigital);
    }

    public byte[] leerArchivo(String ruta) throws Exception {
        return Files.readAllBytes(Paths.get(ruta));
    }

    public void guardarFirma(String ruta, byte[] firma) throws Exception {
        FileOutputStream fos = new FileOutputStream(ruta);
        fos.write(firma);
        fos.close();
    }
}
