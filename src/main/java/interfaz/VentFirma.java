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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Controlador;

@SuppressWarnings("serial")
public class VentFirma extends JFrame implements ActionListener {

    public static final String SUBIR_ARCHIVO = "Subir Archivo";
    public static final String FIRMAR_ARCHIVO = "Firmar Archivo";

    private JPasswordField passwordField;
    private JTextField documentNameField;

    private FirmaDigitalAppInit inicio;
    private String filePath;

    public VentFirma(FirmaDigitalAppInit inicio) {
        this.inicio = inicio;
        filePath = "";

        setTitle("Firmador de Documentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 540, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.GRAY);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton uploadButton = new JButton("Cargar Documento");
        uploadButton.setActionCommand(SUBIR_ARCHIVO);
        uploadButton.addActionListener((ActionListener) this);
        uploadButton.setBounds(0, 39, 242, 23);
        contentPane.add(uploadButton);

        passwordField = new JPasswordField();
        passwordField.setBounds(252, 89, 158, 20);
        contentPane.add(passwordField);

        JLabel passwordLabel = new JLabel("Contraseña Clave Privada", SwingConstants.CENTER);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(0, 92, 242, 14);
        contentPane.add(passwordLabel);

        JButton signButton = new JButton("Firmar Documento");
        signButton.setActionCommand(FIRMAR_ARCHIVO);
        signButton.addActionListener((ActionListener) this);
        signButton.setBounds(126, 142, 176, 23);
        contentPane.add(signButton);

        documentNameField = new JTextField();
        documentNameField.setEditable(false);
        documentNameField.setBounds(252, 40, 158, 20);
        contentPane.add(documentNameField);
        documentNameField.setColumns(10);

        JButton backButton = new JButton("Volver al Menú Principal");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inicio.setVisible(true);
                dispose();
            }
        });
        backButton.setBounds(113, 228, 266, 23);
        contentPane.add(backButton);
    }

    public void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Archivo");

        int selection = fileChooser.showOpenDialog(this);

        if (selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            documentNameField.setText(selectedFile.getName());
        }
    }

    public void firmarArchivo() {
        String password = new String(passwordField.getPassword());
        if (filePath.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo y especificar la contraseña de la clave privada para firmar.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Controlador controller = inicio.getControlador();
            JOptionPane.showMessageDialog(this, "Seleccione dónde guardar la firma.");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Archivo");

            int selection = fileChooser.showSaveDialog(this);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                String signaturePath = outputFile.getAbsolutePath();

                try {
                    controller.firmarArchivo(filePath, signaturePath, password);
                    JOptionPane.showMessageDialog(this, "Firma generada exitosamente", "Respuesta", JOptionPane.INFORMATION_MESSAGE);

                    filePath = "";
                    documentNameField.setText("");
                    passwordField.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(SUBIR_ARCHIVO)) {
            cargarArchivo();
        } else if (command.equals(FIRMAR_ARCHIVO)) {
            firmarArchivo();
        }
    }
}
