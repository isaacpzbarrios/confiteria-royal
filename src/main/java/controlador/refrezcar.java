/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.applet.AudioClip;
import javax.swing.JOptionPane;

/**
 *
 * @author isaac
 */
public class refrezcar extends Thread {

    private controladorConfiteria control;

    public refrezcar(controladorConfiteria control) {
        this.control = control;

    }

    @Override
    public void run() {

        while (1 == 1) {
            try {
                Thread.sleep(12000);
//                System.out.println("Hola mundo");
                control.limpiarDetalleVentas();
                control.listarDetalleVentas();
                if (control.cambiosDetalleVentas() == true) {
                    musica();
                }
                control.mantenerSeleccion();
            } catch (InterruptedException ex) {
                JOptionPane.showConfirmDialog(null, ex);

            }
        }

    }

    private void musica() {
        AudioClip musica;
        musica = java.applet.Applet.newAudioClip(getClass().getResource("/clic/kirby-super-star-1up.wav"));
        musica.play();

    }
}
