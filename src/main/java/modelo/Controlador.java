package modelo;

import java.security.KeyPair;

public class Controlador {

    private GenerarClaves generarClaves;
    private Firma firma;
    private Comprobar comprobar;

    public Controlador() {
        try {
            generarClaves = new GenerarClaves();
            firma = new Firma();
            comprobar = new Comprobar();
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    public void generarClave(String password) {
        generarClaves.generarClave(password);
    }

    public void firmarArchivo(String rutaArchivo, String rutaFirma, String passwordKeyPair) throws Exception {
        KeyPair parDeClaves = generarClaves.getClaves().get(passwordKeyPair);
        if (parDeClaves == null) {
            throw new Exception("No existe una clave privada guardada con el password especificado");
        } else {
            firma.firmarDocumento(rutaArchivo, parDeClaves.getPrivate(), rutaFirma);
        }
    }

    public boolean validarFirma(String rutaArchivo, String rutaFirma, String rutaClavePublica) throws Exception {
        return comprobar.validarFirma(rutaArchivo, rutaFirma, rutaClavePublica);
    }

    public void exportarClavePublica(String rutaClavePublica, String passwordKeyPair) throws Exception {
        generarClaves.exportarClavePublica(rutaClavePublica, passwordKeyPair);
    }
}
