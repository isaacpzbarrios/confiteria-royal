/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import rojerusan.RSTableMetro;

/**
 *
 * @author isaac
 */
public class confiteria extends javax.swing.JPanel {

    /**
     * Creates new form confiteria
     */
    public confiteria() {
        initComponents();
        orientacionColumnTabla();

    }

    public void orientacionColumnTabla() {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < 6; i++) {
            tablaInventario.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaInventario = new rojerusan.RSTableMetro();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("MADE Soulmaze", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(2, 44, 82));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Confiteria Royal");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        tablaInventario.setBackground(new java.awt.Color(255, 255, 255));
        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Precio", "Categoria", "Contenido", "Precio con TCR", "Precio con TCG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaInventario.setAltoHead(36);
        tablaInventario.setColorBackgoundHead(new java.awt.Color(9, 76, 123));
        tablaInventario.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tablaInventario.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tablaInventario.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tablaInventario.setFuenteFilas(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tablaInventario.setFuenteFilasSelect(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tablaInventario.setFuenteHead(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tablaInventario.setRowHeight(32);
        jScrollPane2.setViewportView(tablaInventario);
        tablaInventario.getAccessibleContext().setAccessibleName("");
        tablaInventario.getAccessibleContext().setAccessibleDescription("");

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    public rojerusan.RSTableMetro tablaInventario;
    // End of variables declaration//GEN-END:variables
}
