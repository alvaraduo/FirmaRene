package interfaz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelo.Controlador;

@SuppressWarnings("serial")
public class FirmaDigitalAppInit extends JFrame {

    private Controlador controlador;

    public FirmaDigitalAppInit() {
        controlador = new Controlador();

        setTitle("Aplicación de Firma Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 490, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.GRAY);
        setContentPane(contentPane);
        setLayout(null);

        VentGClaves generarClaves = new VentGClaves(this);
        VentFirma firmador = new VentFirma(this);
        VentComprobar comprobador = new VentComprobar(this);

        JButton btnGenerador = new JButton("Generar Claves");
        btnGenerador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarClaves.setVisible(true);
                dispose();
            }
        });
        btnGenerador.setBounds(116, 51, 207, 23);
        contentPane.add(btnGenerador);

        JButton btnFirmar = new JButton("Firmar Documento");
        btnFirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firmador.setVisible(true);
                dispose();
            }
        });
        btnFirmar.setBounds(116, 111, 207, 23);
        contentPane.add(btnFirmar);

        JButton btnVerificar = new JButton("Verificar Firma");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comprobador.setVisible(true);
                dispose();
            }
        });
        btnVerificar.setBounds(116, 168, 207, 23);
        contentPane.add(btnVerificar);
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public static void main(String[] args) {
        FirmaDigitalAppInit app = new FirmaDigitalAppInit();
        app.setVisible(true);
    }
}
