package interfaz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import modelo.Controlador;

@SuppressWarnings("serial")
public class VentGClaves extends JFrame implements ActionListener {

    public static final String GENERAR_CLAVES = "Generar Clave RSA";
    public static final String EXPORTAR_CLAVE_PUBLICA = "Exportar Clave Pública";

    private JPasswordField passFieldContrasena;
    private JPasswordField passFieldIngreseNuevamenteContrasena;

    private FirmaDigitalAppInit inicio;

    public VentGClaves(FirmaDigitalAppInit inicio) {
        this.inicio = inicio;

        setTitle("Generador de Claves");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 522, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labContrasena = new JLabel("Contraseña para clave privada");
        labContrasena.setBounds(30, 35, 261, 14);
        labContrasena.setForeground(Color.WHITE);
        contentPane.add(labContrasena);

        JLabel labConfirmarContrasena = new JLabel("Ingrese nuevamente la contraseña");
        labConfirmarContrasena.setBounds(30, 72, 300, 14);
        labConfirmarContrasena.setForeground(Color.WHITE);
        contentPane.add(labConfirmarContrasena);

        JButton butGenerarClaves = new JButton("Generar Clave RSA");
        butGenerarClaves.setBounds(44, 113, 162, 23);
        contentPane.add(butGenerarClaves);
        butGenerarClaves.setActionCommand(GENERAR_CLAVES);
        butGenerarClaves.addActionListener((ActionListener) this);

        passFieldContrasena = new JPasswordField();
        passFieldContrasena.setBounds(301, 32, 153, 20);
        contentPane.add(passFieldContrasena);

        passFieldIngreseNuevamenteContrasena = new JPasswordField();
        passFieldIngreseNuevamenteContrasena.setBounds(301, 69, 153, 20);
        contentPane.add(passFieldIngreseNuevamenteContrasena);

        JButton butExportarClavePublica = new JButton("Exportar Clave Pública");
        butExportarClavePublica.setBounds(44, 161, 262, 23);
        contentPane.add(butExportarClavePublica);
        butExportarClavePublica.setActionCommand(EXPORTAR_CLAVE_PUBLICA);
        butExportarClavePublica.addActionListener((ActionListener) this);

        JButton butIrAMenu = new JButton("Volver al Menú Principal");
        butIrAMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                inicio.setVisible(true);
                dispose();
            }
        });
        butIrAMenu.setBounds(160, 228, 196, 23);
        contentPane.add(butIrAMenu);
    }

    public void generarClaves() {
        Controlador controlador = inicio.getControlador();

        String pass1 = String.valueOf(passFieldContrasena.getPassword());
        String pass2 = String.valueOf(passFieldIngreseNuevamenteContrasena.getPassword());

        if (pass1.equals("") || pass2.equals("")) {
            JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                controlador.generarClave(pass1);
                JOptionPane.showMessageDialog(this, "Claves generadas exitosamente", "Respuesta", JOptionPane.INFORMATION_MESSAGE);

                passFieldContrasena.setText("");
                passFieldIngreseNuevamenteContrasena.setText("");
            }
        }
    }

    public void exportarClavePublica() {
        Controlador controlador = inicio.getControlador();

        // Paso 1: Obtener la contraseña de la clave privada
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Digite la contraseña:");
        JPasswordField password = new JPasswordField(10);
        panel.add(label);
        panel.add(password);
        String[] options = new String[] { "OK", "Cancelar" };
        int option = JOptionPane.showOptionDialog(null, panel, "Contraseña", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (option == 0) {
            String pass = new String(password.getPassword());

            JOptionPane.showMessageDialog(this, "Seleccione dónde desea guardar el archivo");

            // Paso 2: Seleccionar la ubicación para guardar la clave pública
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar archivo");

            int selection = fileChooser.showSaveDialog(this);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String publicKeyPath = selectedFile.getAbsolutePath();

                try {
                    controlador.exportarClavePublica(publicKeyPath, pass);
                    JOptionPane.showMessageDialog(this, "Clave exportada exitosamente", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(GENERAR_CLAVES)) {
            generarClaves();
        } else if (command.equals(EXPORTAR_CLAVE_PUBLICA)) {
            exportarClavePublica();
        }
    }
}
