/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.detallesPedido;
import modelo.producto;
import vista.confiteria;
import vista.detallesVenta;
import vista.home;
import vista.pedidos;
import vista.popCombos;
import vista.popMesse;
import vista.seleccionarProducto;

/**
 *
 * @author isaac
 */
public class controladorConfiteria implements ActionListener, MouseListener {

    private confiteria confiteria;
    private home principal;
    private pedidos pedido;
    private seleccionarProducto selectProduc;
    private popCombos popcombo;
    private detallesPedido detallepedido;
    private detallesVenta detalleventa;
    private producto producto;
    private refrezcar refrez;
    private popMesse popMessen;

    conexion con = new conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    DefaultTableModel modelo2;

    public controladorConfiteria(home principal) {
        this.principal = principal;
        confiteria = new confiteria();
        pedido = new pedidos();
        selectProduc = new seleccionarProducto(principal, true);
        producto = new producto();
        popcombo = new popCombos(principal, true);
        detallepedido = new detallesPedido();
        detalleventa = new detallesVenta();
        refrez = new refrezcar(this);
        popMessen = new popMesse(principal, true);

        this.principal.btnConfiteria.addActionListener(this);
        this.principal.btnPedido.addActionListener(this);
        this.principal.btnConfiteria.addMouseListener(this);
        this.principal.btnPedido.addMouseListener(this);
        this.principal.btnDetalle.addMouseListener(this);
        this.principal.btnHome.addMouseListener(this);
        pedido.seleccionarProduc.addActionListener(this);
        selectProduc.cancelar.addActionListener(this);
        confiteria.tablaInventario.addMouseListener(this);
        selectProduc.agregar.addActionListener(this);
        selectProduc.listo.addActionListener(this);
        selectProduc.tabla1.addMouseListener(this);
        selectProduc.tabla2.addMouseListener(this);
        selectProduc.aceptar.addActionListener(this);
        pedido.boxTarjeta.addActionListener(this);
        pedido.enviarPedido.addActionListener(this);
        this.principal.btnDetalle.addActionListener(this);
        detalleventa.tablaPedidos.addMouseListener(this);
        detalleventa.marcar.addActionListener(this);
        popMessen.send.addActionListener(this);
        selectProduc.soltar.addActionListener(this);
    }

    public void listar() {
        String sql = "SELECT nombre_producto, precio, cd.categorias, descripcion, precio_tcr, precio_tcg FROM dulceria "
                + "INNER JOIN categoria_dulceria cd on categoria = cd.id_categoria ";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[6];
            modelo = (DefaultTableModel) confiteria.tablaInventario.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre_producto");
                producto[1] = rs.getFloat("precio");
                producto[2] = rs.getString("categorias");
                producto[3] = rs.getString("descripcion");
                if (rs.getFloat("precio_tcr") == 0.0) {
                    producto[4] = null;
                    producto[5] = null;
                } else {
                    producto[4] = rs.getFloat("precio_tcr");
                    producto[5] = rs.getFloat("precio_tcg");
                }
                modelo.addRow(producto);
            }
            confiteria.tablaInventario.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void listarTarjetaRoyal() {
        if (pedido.boxTarjeta.getItemCount() <= 1) {
            String sql = "SELECT id_tarjeta, tarjeta FROM tarjetas_royal \n"
                    + "ORDER by id_tarjeta ASC";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    pedido.boxTarjeta.addItem(rs.getString("tarjeta"));
                }

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    public void listarMetodoPago() {
        if (pedido.boxMetodoPago.getItemCount() <= 1) {
            String sql = "SELECT id_metodo, metodo FROM metodo_pago \n"
                    + "ORDER by id_metodo ASC";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    pedido.boxMetodoPago.addItem(rs.getString("metodo"));
                }

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    void limpiarConfiteria() {
        if (confiteria.isShowing() == true) {
            modelo.setRowCount(0);
        }
    }

    void limpiarDetalleCombo() {
        if (selectProduc.tabla2.getRowCount() != 0) {
            modelo.setRowCount(0);
        }
    }

    void limpiarDetallesProducto() {
        if (selectProduc.tabla3.getRowCount() != 0) {
            modelo2.setRowCount(0);

        }
    }

    String mensaje = "";

    public void enviarComentario() {

        if (!"".equals(popMessen.txtMensaje.getText())) {
            mensaje = popMessen.txtMensaje.getText();

            popMessen.txtMensaje.setText("");
            popMessen.dispose();
        }
    }

    public void listarProductoDulceria() {
        if (selectProduc.tabla1.getRowCount() == 0) {
            String sql = "SELECT nombre_producto, precio FROM dulceria ";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[2];
                modelo = (DefaultTableModel) selectProduc.tabla1.getModel();
                while (rs.next()) {
                    producto[0] = rs.getString("nombre_producto");
                    producto[1] = rs.getFloat("precio");
                    modelo.addRow(producto);
                }
                selectProduc.tabla1.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void listarDetallesProducto() {
        int fila = selectProduc.tabla2.getSelectedRow();
        String detalle = (String) selectProduc.tabla2.getValueAt(fila, 0);
        if (selectProduc.tabla3.getRowCount() != 0) {
            limpiarDetallesProducto();
        }

        String sql = "SELECT dd.nombre, cdd.nombre as categoria FROM detalle_dulceria dd\n"
                + "INNER JOIN categoria_detalle_dulceria cdd ON dd.categoria = cdd.id_categoria_detalle_dul WHERE cdd.nombre = \"" + detalle + "\"";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            modelo2 = (DefaultTableModel) selectProduc.tabla3.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre");
                modelo2.addRow(producto);
            }
            selectProduc.tabla3.setModel(modelo2);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public void listarDetallesProductoBebidas() {
        if (selectProduc.tabla3.getRowCount() != 0) {
            limpiarDetallesProducto();
        }

        String sql = "SELECT dd.nombre, cdd.nombre as categoria FROM detalle_dulceria dd\n"
                + "INNER JOIN categoria_detalle_dulceria cdd ON dd.categoria = cdd.id_categoria_detalle_dul WHERE cdd.nombre = \"Gaseosas\"";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            modelo2 = (DefaultTableModel) selectProduc.tabla3.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre");
                modelo2.addRow(producto);
            }
            selectProduc.tabla3.setModel(modelo2);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public void listarDetallesProductoCrispeta() {
        if (selectProduc.tabla3.getRowCount() != 0) {
            limpiarDetallesProducto();
        }

        String sql = "SELECT dd.nombre, cdd.nombre as categoria FROM detalle_dulceria dd\n"
                + "INNER JOIN categoria_detalle_dulceria cdd ON dd.categoria = cdd.id_categoria_detalle_dul WHERE cdd.nombre = \"Crispetas\"";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            modelo2 = (DefaultTableModel) selectProduc.tabla3.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre");
                modelo2.addRow(producto);
            }
            selectProduc.tabla3.setModel(modelo2);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public void listarDetallesProductoJugo() {
        if (selectProduc.tabla3.getRowCount() != 0) {
            limpiarDetallesProducto();
        }

        String sql = "SELECT dd.nombre, cdd.nombre as categoria FROM detalle_dulceria dd\n"
                + "INNER JOIN categoria_detalle_dulceria cdd ON dd.categoria = cdd.id_categoria_detalle_dul WHERE cdd.nombre = \"Jugo Hit Pet\"";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            modelo2 = (DefaultTableModel) selectProduc.tabla3.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre");
                modelo2.addRow(producto);
            }
            selectProduc.tabla3.setModel(modelo2);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public void listarDetallesProductoHatsu() {
        if (selectProduc.tabla3.getRowCount() != 0) {
            limpiarDetallesProducto();
        }

        String sql = "SELECT dd.nombre, cdd.nombre as categoria FROM detalle_dulceria dd\n"
                + "INNER JOIN categoria_detalle_dulceria cdd ON dd.categoria = cdd.id_categoria_detalle_dul WHERE cdd.nombre = \"Hatsu\"";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            modelo2 = (DefaultTableModel) selectProduc.tabla3.getModel();
            while (rs.next()) {
                producto[0] = rs.getString("nombre");
                modelo2.addRow(producto);
            }
            selectProduc.tabla3.setModel(modelo2);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }

    }

    public void obtenerProductoTabla() {
        int fila = confiteria.tablaInventario.getSelectedRow();
        String nombre = (String) confiteria.tablaInventario.getValueAt(fila, 0);
        String categoria = (String) confiteria.tablaInventario.getValueAt(fila, 2);
        String descripcion = (String) confiteria.tablaInventario.getValueAt(fila, 3);
        producto.setCategoria(categoria);
        producto.setNombre(nombre);
        producto.setContenido(descripcion);

    }

    public void popupDetallesCombos(String combo, String contenido) {
        popcombo.txtTirulo.setText(combo);
        String texto = "<html><body> <p>" + contenido + "</p></body> </html>";
        popcombo.txtMess.setText(texto);
        popcombo.setLocationRelativeTo(principal);
        popcombo.setVisible(true);
    }

    public void menu() {
        int fila = selectProduc.tabla1.getSelectedRow();
        String mensa = "";
        String mensa2 = "";
        String nombre = (String) selectProduc.tabla1.getValueAt(fila, 0);
        detallepedido.getMenu().add(nombre);
        detallepedido.setNombre(nombre);

        for (String cadena : detallepedido.getMenuDetalle()) {
            mensa += "\n     " + cadena;
        }
        detallepedido.getMenuCompleto().add(detallepedido.getNombre() + mensa);
        for (String cadena : detallepedido.getMenuCompleto()) {
            mensa2 += cadena + "\n";

        }
        selectProduc.txtMenu.setText(mensa2);
        detallepedido.getMenuDetalle().clear();

    }

    public void menuAOrden() {
        String mensa = "";

        for (String cadena : detallepedido.getMenuCompleto()) {
            mensa += cadena + "\n";

        }
        pedido.txtOrden.setText(mensa);
    }

    public void listarDetalleCombo1() {
        if (selectProduc.tabla2.getRowCount() == 0) {
            String sql = "SELECT nombre FROM categoria_detalle_dulceria";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                modelo = (DefaultTableModel) selectProduc.tabla2.getModel();
                while (rs.next()) {
                    if (rs.getString("nombre").contains("Crispeta") || rs.getString("nombre").contains("Snake") || rs.getString("nombre").contains("Gaseosa")) {
                        producto[0] = rs.getString("nombre");
                        modelo.addRow(producto);

                    }
                }
                selectProduc.tabla2.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void listarDetalleCombo2() {
        if (selectProduc.tabla2.getRowCount() == 0) {
            String sql = "SELECT nombre FROM categoria_detalle_dulceria";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                modelo = (DefaultTableModel) selectProduc.tabla2.getModel();
                while (rs.next()) {
                    if (rs.getString("nombre").contains("Crispeta") || rs.getString("nombre").contains("Comida") || rs.getString("nombre").contains("Gaseosa")) {
                        producto[0] = rs.getString("nombre");
                        modelo.addRow(producto);

                    }
                }
                selectProduc.tabla2.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void listarDetalleCombo3() {
        if (selectProduc.tabla2.getRowCount() == 0) {
            String sql = "SELECT nombre FROM categoria_detalle_dulceria";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                modelo = (DefaultTableModel) selectProduc.tabla2.getModel();
                while (rs.next()) {
                    if (rs.getString("nombre").contains("Crispeta") || rs.getString("nombre").contains("Gaseosa")) {
                        producto[0] = rs.getString("nombre");
                        modelo.addRow(producto);

                    }
                }
                selectProduc.tabla2.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void listarDetalleCombo4() {
        if (selectProduc.tabla2.getRowCount() == 0) {
            String sql = "SELECT nombre FROM categoria_detalle_dulceria";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                modelo = (DefaultTableModel) selectProduc.tabla2.getModel();
                while (rs.next()) {
                    if (rs.getString("nombre").contains("Comida") || rs.getString("nombre").contains("Gaseosa") || rs.getString("nombre").contains("Snake")) {
                        producto[0] = rs.getString("nombre");
                        modelo.addRow(producto);

                    }
                }
                selectProduc.tabla2.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void listarDetalleCombo5() {
        if (selectProduc.tabla2.getRowCount() == 0) {
            String sql = "SELECT nombre FROM categoria_detalle_dulceria";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                modelo = (DefaultTableModel) selectProduc.tabla2.getModel();
                while (rs.next()) {
                    if (rs.getString("nombre").contains("Crispeta") || rs.getString("nombre").contains("Gaseosa") || rs.getString("nombre").contains("Comida")) {
                        producto[0] = rs.getString("nombre");
                        modelo.addRow(producto);

                    }
                }
                selectProduc.tabla2.setModel(modelo);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }
   

    private int contadorCrispeta = 0;
    private int contadorGaseosa = 0;
    private int contadorSnake = 0;
    private int contadorComidaRapida = 0;

    public void limpiarContadores() {
        contadorComidaRapida = 0;
        contadorCrispeta = 0;
        contadorGaseosa = 0;
        contadorSnake = 0;
    }
    
    public void addMenuComboKids() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Crispeta")) {
            if (contadorCrispeta < 1) {
                addMenuDetalle();
                contadorCrispeta += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de crispetas para el Combo Kids");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa < 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo Kids");
            }
        } else if (detalle.contains("Snake")) {
            if (contadorSnake < 1) {
                addMenuDetalle();
                contadorSnake += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de chocolatina para el Combo Kids");
            }
        }

    }

    public void addMenuCombo1() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Crispeta")) {
            if (contadorCrispeta < 1) {
                addMenuDetalle();
                contadorCrispeta += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de crispetas para el Combo 1");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa <= 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo 1");
            }
        } else if (detalle.contains("Snake")) {
            if (contadorSnake <= 1) {
                addMenuDetalle();
                contadorSnake += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de chocolatina para el Combo 1");
            }
        }

    }

    public void addMenuCombo2() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Crispeta")) {
            if (contadorCrispeta < 1) {
                addMenuDetalle();
                contadorCrispeta += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de crispetas para el Combo 2");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa <= 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo 2");
            }
        } else if (detalle.contains("Comida")) {
            if (contadorComidaRapida <= 1) {
                addMenuDetalle();
                contadorComidaRapida += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de perros calientes para el Combo 2");
            }
        }

    }

    public void addMenuCombo3() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Crispeta")) {
            if (contadorCrispeta < 1) {
                addMenuDetalle();
                contadorCrispeta += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de crispetas para el Combo 3");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa < 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo 3");
            }
        }

    }

    public void addMenuCombo4() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Snake")) {
            if (contadorSnake < 1) {
                addMenuDetalle();
                contadorSnake += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de chocolatina para el Combo 4");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa < 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo 4");
            }
        } else if (detalle.contains("Comida")) {
            if (contadorComidaRapida < 1) {
                addMenuDetalle();
                contadorComidaRapida += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de perros calientes para el Combo 4");
            }
        }

    }

    public void addMenuCombo5() {
        int filaFn = selectProduc.tabla2.getSelectedRow();

        String detalle = (String) selectProduc.tabla2.getValueAt(filaFn, 0);
        if (detalle.contains("Crispeta")) {
            if (contadorCrispeta < 1) {
                addMenuDetalle();
                contadorCrispeta += 1;
            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de crispetas para el Combo 5");
            }
        } else if (detalle.contains("Gaseosa")) {
            if (contadorGaseosa <= 1) {
                addMenuDetalle();
                contadorGaseosa += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de gaseosa para el Combo 5");
            }
        } else if (detalle.contains("Comida")) {
            if (contadorComidaRapida < 1) {
                addMenuDetalle();
                contadorComidaRapida += 1;

            } else {
                JOptionPane.showMessageDialog(principal, "Se alcanzo el limite de perros calientes para el Combo 5");
            }
        }

    }

    public void addMenuDetalle() {
        int fila3 = selectProduc.tabla3.getSelectedRow();

        String detalle = (String) selectProduc.tabla3.getValueAt(fila3, 0);

        detallepedido.getMenuDetalle().add(detalle);
    }

    public void calcularPrecioBase() {
        float precio = 0;
        String precioFalso = "";
        for (String base : detallepedido.getMenu()) {
            String sql = "SELECT precio FROM dulceria where nombre_producto = \"" + base + "\"";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                while (rs.next()) {
                    producto[0] = rs.getString("precio");
                }
                precioFalso = producto[0].toString();
                precio += Float.parseFloat(precioFalso);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }
        detallepedido.setPrecioFull(precio);
        pedido.txtPrecio.setText("Total: " + precio);
        System.out.println(precio);
    }

    public void calcularPrecioTCR() {
        float precio = 0;
        String precioFalso = "";
        for (String base : detallepedido.getMenu()) {
            String sql = "SELECT precio_tcr FROM dulceria where nombre_producto = \"" + base + "\"";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                while (rs.next()) {
                    producto[0] = rs.getString("precio_tcr");
                }
                precioFalso = producto[0].toString();
                precio += Float.parseFloat(precioFalso);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }
        detallepedido.setPrecioFull(precio);
        pedido.txtPrecio.setText("Total: " + precio);
        System.out.println(precio);
    }

    public void calcularPrecioTCG() {
        float precio = 0;
        String precioFalso = "";
        for (String base : detallepedido.getMenu()) {
            String sql = "SELECT precio_tcg FROM dulceria where nombre_producto = \"" + base + "\"";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                Object[] producto = new Object[1];
                while (rs.next()) {
                    producto[0] = rs.getString("precio_tcg");
                }
                precioFalso = producto[0].toString();
                precio += Float.parseFloat(precioFalso);

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }
        detallepedido.setPrecioFull(precio);
        pedido.txtPrecio.setText("Total: " + precio);
        System.out.println(precio);
    }

    public void enviarPedido() {
        if ("".equals(pedido.sala.getText()) || "".equals(pedido.silla.getText())
                || "".equals(pedido.nombreCliente.getText()) || "".equals(pedido.nombreMecero.getText())
                || "".equals(pedido.txtOrden.getText()) || "".equals(pedido.txtPrecio.getText())
                || pedido.boxMetodoPago.getSelectedIndex() <= 0 || pedido.boxTarjeta.getSelectedIndex() <= 0) {

            JOptionPane.showMessageDialog(principal, "Por favor completar los campos");

        } else {

            String mensa = "";

            for (String cadena : detallepedido.getMenuCompleto()) {
                mensa += cadena + "\n";

            }
            String silla = pedido.silla.getText();
            String sala = pedido.sala.getText();
            int salaFn = Integer.parseInt(sala);
            String cliente = pedido.nombreCliente.getText();
            String mecero = pedido.nombreMecero.getText();
            int tarjeta = (int) pedido.boxTarjeta.getSelectedIndex();
            int pago = (int) pedido.boxMetodoPago.getSelectedIndex();

            String sql = "INSERT INTO `detalle_venta` (`descuento_tarjeta`, `medio_pago`, `descripcion`, `total_pagar`, `sala`, `silla`, `fecha`, "
                    + "`nombre_cliente`, `nombre_mecero`, `comentario`) VALUES ('" + tarjeta + "', '" + pago + "', '" + mensa + "', '" + detallepedido.getPrecioFull() + "'"
                    + ", '" + salaFn + "', '" + silla + "', Now(), '" + cliente + "', '" + mecero + "', '" + mensaje + "');";
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(sql);
                limpiarPedido();
                mensaje = "";
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, e);

            }
        }

    }

    public void limpiarPedido() {
        pedido.boxMetodoPago.setSelectedIndex(0);
        pedido.boxTarjeta.setSelectedIndex(0);
        pedido.nombreCliente.setText("");
        pedido.nombreMecero.setText("");
        pedido.sala.setText("");
        pedido.silla.setText("");
        pedido.txtOrden.setText("");
        pedido.txtPrecio.setText("");
        detallepedido.getMenu().clear();
        detallepedido.getMenuCompleto().clear();
        detallepedido.getMenuDetalle().clear();
    }

    int model;

    public void listarDetalleVentas() {
        String sql = "SELECT dv.id_detalle, tr.tarjeta, mp.metodo, dv.total_pagar, dv.sala, dv.silla, dv.nombre_cliente, dv.nombre_mecero, dv.fecha  \n"
                + "FROM detalle_venta dv INNER JOIN tarjetas_royal tr on dv.descuento_tarjeta = tr.id_tarjeta INNER JOIN metodo_pago mp on dv.medio_pago = mp.id_metodo where dv.estado = 1";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[9];
            modelo = (DefaultTableModel) detalleventa.tablaPedidos.getModel();
            while (rs.next()) {
                producto[0] = rs.getInt("id_detalle");
                producto[1] = rs.getString("tarjeta");
                producto[2] = rs.getString("metodo");
                producto[3] = rs.getFloat("total_pagar");
                producto[4] = rs.getInt("sala");
                producto[5] = rs.getString("silla");
                producto[6] = rs.getString("nombre_cliente");
                producto[7] = rs.getString("nombre_mecero");
                producto[8] = rs.getString("fecha");

                modelo.addRow(producto);
            }
            detalleventa.tablaPedidos.setModel(modelo);

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public boolean cambiosDetalleVentas() {

        if (model == modelo.getRowCount()) {
            return false;
        } else {
            return true;

        }
    }

    int filaOr = -1;

    public void mantenerSeleccion() {
        if (filaOr >= 0) {
            detalleventa.tablaPedidos.getSelectionModel().setSelectionInterval(filaOr, filaOr);

        }
    }

    public void marcarPedido() {
        int fila = detalleventa.tablaPedidos.getSelectedRow();

        String sql = "UPDATE `detalle_venta` SET `estado` = b'0' WHERE `detalle_venta`.`id_detalle` = " + modelo.getValueAt(fila, 0) + "";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            st.executeUpdate(sql);
            refrez.suspend();
            detalleventa.txtOrden.setText("");
            detalleventa.txtComentario.setText("");
            limpiarDetalleVentas();
            listarDetalleVentas();
            if (refrez.isAlive()) {
                refrez.resume();
            } else {
                refrez.start();

            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void mostrarOrden() {
        filaOr = detalleventa.tablaPedidos.getSelectedRow();

        int id = (int) detalleventa.tablaPedidos.getValueAt(filaOr, 0);

        String sql = "SELECT descripcion FROM `detalle_venta` WHERE id_detalle=" + id + "";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            while (rs.next()) {
                producto[0] = rs.getString("descripcion");
            }
            detalleventa.txtOrden.setText(producto[0].toString());

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void mostrarComentarios() {
        filaOr = detalleventa.tablaPedidos.getSelectedRow();

        int id = (int) detalleventa.tablaPedidos.getValueAt(filaOr, 0);

        String sql = "SELECT comentario FROM `detalle_venta` WHERE id_detalle=" + id + "";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] producto = new Object[1];
            while (rs.next()) {
                producto[0] = rs.getString("comentario");
            }
            detalleventa.txtComentario.setText(producto[0].toString());

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);

        }
    }

    void limpiarDetalleVentas() {
        if (detalleventa.isShowing()) {
            model = modelo.getRowCount();
            modelo.setRowCount(0);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (principal.btnConfiteria == e.getSource()) {
            refrez.suspend();
            if (modelo != null) {
                limpiarDetalleVentas();
            }
            principal.btnConfiteria.setEnabled(false);
            principal.btnPedido.setEnabled(true);
            principal.btnDetalle.setEnabled(true);
            principal.jPanel3.add(confiteria);
            confiteria.setVisible(true);
            principal.jPanel8.setVisible(false);
            pedido.setVisible(false);
            detalleventa.setVisible(false);
            listar();
        }
        if (principal.btnPedido == e.getSource()) {
            refrez.suspend();
            if (modelo != null) {
                limpiarConfiteria();
                limpiarDetalleVentas();
            }
            listarTarjetaRoyal();
            listarMetodoPago();
            principal.btnPedido.setEnabled(false);
            principal.btnDetalle.setEnabled(true);
            principal.btnConfiteria.setEnabled(true);
            principal.jPanel3.add(pedido);
            principal.jPanel8.setVisible(false);
            pedido.setVisible(true);
            confiteria.setVisible(false);
            detalleventa.setVisible(false);

        }
        if (pedido.seleccionarProduc == e.getSource()) {
            listarProductoDulceria();
            selectProduc.setLocationRelativeTo(principal);
            selectProduc.show();
        }
        if (selectProduc.cancelar == e.getSource()) {
            selectProduc.tabla1.clearSelection();
            selectProduc.tabla2.clearSelection();
            selectProduc.tabla3.clearSelection();
            limpiarDetalleCombo();
            limpiarDetallesProducto();
            selectProduc.tabla1.enable(true);
            detallepedido.getMenu().clear();
            detallepedido.getMenuDetalle().clear();
            detallepedido.getMenuCompleto().clear();
            selectProduc.txtMenu.setText("");
            selectProduc.dispose();
        }
        if (selectProduc.agregar == e.getSource()) {
            int fila = selectProduc.tabla1.getSelectedRow();
            String combo = (String) selectProduc.tabla1.getValueAt(fila, 0);
            if (!selectProduc.tabla1.isCellSelected(fila, 0)) {
                JOptionPane.showMessageDialog(principal, "Por favor, seleciona un producto");
            } else {
                int fila3 = selectProduc.tabla3.getSelectedRow();

                if (selectProduc.tabla3.isCellSelected(fila3, 0)) {
                    if ("Combo1".equals(combo)) {
                        addMenuCombo1();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    } else if ("Combo2".equals(combo)) {
                        addMenuCombo2();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    } else if ("Combo3".equals(combo)) {
                        addMenuCombo3();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    } else if ("Combo4".equals(combo)) {
                        addMenuCombo4();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    } else if ("Combo5".equals(combo)) {
                        addMenuCombo5();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    } else if ("Combo Kids".equals(combo)) {
                        addMenuComboKids();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    }else{
                        addMenuDetalle();
                        selectProduc.tabla2.clearSelection();
                        limpiarDetallesProducto();
                    }

                }
            }

        }
        if (selectProduc.listo == e.getSource()) {
            menu();
            limpiarDetalleCombo();
            limpiarDetallesProducto();
            limpiarContadores();
            selectProduc.tabla1.clearSelection();
            selectProduc.tabla1.enable(true);
        }
        if (selectProduc.aceptar == e.getSource()) {
            menuAOrden();
            selectProduc.dispose();
            calcularPrecioBase();

        }
        if (pedido.boxTarjeta == e.getSource()) {
            String tarjeta = "";
            tarjeta = (String) pedido.boxTarjeta.getSelectedItem();
            if ("TCR".equals(tarjeta)) {
                calcularPrecioTCR();
            } else if ("TCG".equals(tarjeta)) {
                calcularPrecioTCG();
            } else {
                calcularPrecioBase();
            }
        }
        if (pedido.enviarPedido == e.getSource()) {
            popMessen.setLocationRelativeTo(principal);
            popMessen.setVisible(true);
            enviarPedido();
            selectProduc.txtMenu.setText("");
        }
        if (principal.btnDetalle == e.getSource()) {
            if (modelo != null) {
                limpiarConfiteria();

            }
            principal.btnConfiteria.setEnabled(true);
            principal.btnDetalle.setEnabled(false);
            principal.btnPedido.setEnabled(true);
            principal.jPanel3.add(detalleventa);
            detalleventa.setVisible(true);
            principal.jPanel8.setVisible(false);
            pedido.setVisible(false);
            confiteria.setVisible(false);
            listarDetalleVentas();
            if (refrez.isAlive()) {
//                System.out.println("va");
                refrez.resume();
            } else {
                refrez.start();

            }
        }
        if (detalleventa.marcar == e.getSource()) {
            marcarPedido();
        }
        if (popMessen.send == e.getSource()) {
            enviarComentario();
        }
        if (selectProduc.soltar == e.getSource()) {
            int fila = selectProduc.tabla1.getSelectedRow();

            if (selectProduc.tabla1.isCellSelected(fila, 0)) {
                selectProduc.tabla1.enable(true);
                limpiarDetalleCombo();
                limpiarDetallesProducto();
//                detallepedido.getMenu().remove(detallepedido.getMenu().size() - 1);
            } else {
                System.out.println("Error");
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (principal.btnHome == e.getSource()) {
            filaOr = -1;
            detalleventa.txtComentario.setText("");
            if (pedido.isShowing()) {
                limpiarPedido();
                selectProduc.txtMenu.setText("");

            }
            if (modelo != null) {
                limpiarConfiteria();
                limpiarDetalleVentas();
            }
            principal.jPanel3.add(principal.jPanel8);
            principal.jPanel8.setVisible(true);
            pedido.setVisible(false);
            confiteria.setVisible(false);
            detalleventa.setVisible(false);
            principal.btnConfiteria.setEnabled(true);
            principal.btnPedido.setEnabled(true);
            principal.btnDetalle.setEnabled(true);
            refrez.suspend();

        }
        if (confiteria.tablaInventario == e.getSource()) {
            obtenerProductoTabla();

            if ("Combos".equals(producto.getCategoria())) {
                popupDetallesCombos(producto.getNombre(), producto.toString());
            }

        }
        //Arreglar
        if (selectProduc.tabla1 == e.getSource()) {
            int fila = selectProduc.tabla1.getSelectedRow();
            String nombre = (String) selectProduc.tabla1.getValueAt(fila, 0);
            if (nombre.contains("Gaseosa")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetallesProductoBebidas();
                selectProduc.tabla1.enable(false);

            } else if (nombre.contains("Combo1")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo1();
                selectProduc.tabla1.enable(false);

            } else if (nombre.contains("Combo2")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo2();
                selectProduc.tabla1.enable(false);

            }else if (nombre.contains("Combo3")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo3();
                selectProduc.tabla1.enable(false);

            }else if (nombre.contains("Combo4")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo4();
                selectProduc.tabla1.enable(false);

            }else if (nombre.contains("Combo5")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo5();
                selectProduc.tabla1.enable(false);

            }else if (nombre.contains("Combo Kids")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetalleCombo1();
                selectProduc.tabla1.enable(false);

            } else if (nombre.contains("Crispeta")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetallesProductoCrispeta();
                selectProduc.tabla1.enable(false);
            } else if (nombre.contains("Jugo Hit")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetallesProductoJugo();
                selectProduc.tabla1.enable(false);
            } else if (nombre.contains("Hatsu")) {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                listarDetallesProductoHatsu();
                selectProduc.tabla1.enable(false);
            } else {
                limpiarDetalleCombo();
                limpiarDetallesProducto();
                selectProduc.tabla1.enable(false);

            }
        }
        if (selectProduc.tabla2 == e.getSource()) {
            listarDetallesProducto();
        }
        if (detalleventa.tablaPedidos == e.getSource()) {
            mostrarOrden();
            mostrarComentarios();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (principal.btnConfiteria == e.getSource()) {
            principal.btnConfiteria.setBackground(new Color(9, 76, 123));
        }
        if (principal.btnPedido == e.getSource()) {
            principal.btnPedido.setBackground(new Color(9, 76, 123));
        }
        if (principal.btnDetalle == e.getSource()) {
            principal.btnDetalle.setBackground(new Color(9, 76, 123));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (principal.btnConfiteria == e.getSource()) {
            principal.btnConfiteria.setBackground(new Color(0, 0, 0));

        }
        if (principal.btnPedido == e.getSource()) {
            principal.btnPedido.setBackground(new Color(0, 0, 0));

        }
        if (principal.btnDetalle == e.getSource()) {
            principal.btnDetalle.setBackground(new Color(0, 0, 0));

        }
    }

}
