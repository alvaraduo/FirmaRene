package modelo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Clase encargada de verificar la integridad del contenido de un archivo y la integridad de la clave pública.
 */
public class Comprobar {

    /**
     * Valida la firma electrónica de un archivo.
     *
     * @param rutaArchivo Ruta del archivo a verificar.
     * @param rutaFirma Ruta del archivo de la firma electrónica.
     * @param rutaClavePublica Ruta del archivo que contiene la clave pública.
     * @return `true` si la firma es válida, `false` si no es válida.
     * @throws Exception Si ocurre un error durante la validación.
     */
    public boolean validarFirma(String rutaArchivo, String rutaFirma, String rutaClavePublica) throws Exception {
        byte[] datos = leerArchivo(rutaArchivo);
        byte[] firma = leerArchivo(rutaFirma);
        PublicKey clavePublica = leerClavePublicaDesdeArchivo(rutaClavePublica);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(clavePublica);
        signature.update(datos);

        return signature.verify(firma);
    }

    /**
     * Lee el contenido de un archivo y lo almacena en un arreglo de bytes.
     *
     * @param ruta Ruta del archivo a leer.
     * @return Arreglo de bytes con el contenido del archivo.
     * @throws Exception Si ocurre un error durante la lectura del archivo.
     */
    public byte[] leerArchivo(String ruta) throws Exception {
        return Files.readAllBytes(Paths.get(ruta));
    }

    /**
     * Lee una clave pública desde un archivo.
     *
     * @param ruta Ruta del archivo que contiene la clave pública.
     * @return Clave pública.
     * @throws Exception Si ocurre un error durante la lectura de la clave pública.
     */
    public PublicKey leerClavePublicaDesdeArchivo(String ruta) throws Exception {
        byte[] keyBytes = leerArchivo(ruta);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
