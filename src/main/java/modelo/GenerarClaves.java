package modelo;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Clase encargada de generar pares de claves RSA y gestionarlas de manera segura.
 */
public class GenerarClaves {

    // HashMap que almacena los pares de claves generados con un identificador (clave).
    private HashMap<String, KeyPair> claves;

    // Generador de claves
    private KeyPairGenerator generador;

    public GenerarClaves() throws NoSuchAlgorithmException {
        generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(1024);
        claves = new HashMap<>(100);
    }

    /**
     * Genera un par de claves (clave privada y clave pública).
     *
     * @param clave Identificador para la clave.
     */
    public void generarClave(String clave) {
        claves.put(clave, generador.generateKeyPair());
    }

    /**
     * Exporta la clave pública correspondiente a una clave privada.
     *
     * @param rutaClavePublica Ruta donde se guardará la clave pública.
     * @param clavePrivada Identificador de la clave privada.
     * @throws Exception Si no se encuentra una clave privada con el identificador especificado.
     */
    public void exportarClavePublica(String rutaClavePublica, String clavePrivada) throws Exception {
        KeyPair parDeClaves = claves.get(clavePrivada);
        if (parDeClaves == null) {
            throw new Exception("No existe una clave privada guardada con el identificador especificado");
        } else {
            FileOutputStream fos = new FileOutputStream(rutaClavePublica);
            fos.write(parDeClaves.getPublic().getEncoded());
            fos.close();
        }
    }

    public HashMap<String, KeyPair> getClaves() {
        return claves;
    }

    public void setClaves(HashMap<String, KeyPair> claves) {
        this.claves = claves;
    }

    public KeyPairGenerator getGenerador() {
        return generador;
    }

    public void setGenerador(KeyPairGenerator generador) {
        this.generador = generador;
    }
}
