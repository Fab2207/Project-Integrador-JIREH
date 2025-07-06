package Vistas;

import Models.Compras;
import Models.ComprasDao;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Factura extends javax.swing.JFrame {

    Compras compra = new Compras();
    ComprasDao compra_dao = new ComprasDao();
    DefaultTableModel model = new DefaultTableModel();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Factura.class.getName());

    /**
     * Creates new form Factura
     */
    public Factura(int id) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Factura de Compra");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        txt_invoice.setText("" + id);
        listAllComprasDetalles(id);
        calcularCompra();
    }

    public void listAllComprasDetalles(int id) {
        List<Compras> list = compra_dao.listaComprasDetallesQuery(id);
        model = (DefaultTableModel) compras_detalles_tabla.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getNombre_producto();
            row[1] = list.get(i).getCompras_cantidad();
            row[2] = list.get(i).getCompras_precio();
            row[3] = list.get(i).getCompras_subtotal();
            row[4] = list.get(i).getNombre_proveedor_producto();
            row[5] = list.get(i).getCompra();
            row[6] = list.get(i).getCrear_compra();
            model.addRow(row);
        }
        compras_detalles_tabla.setModel(model);
    }

    public void calcularCompra() {
        double total = 0;
        int numRow = compras_detalles_tabla.getRowCount();
        for (int i = 0; i < numRow; i++) {
            total = total + Double.parseDouble(String.valueOf(compras_detalles_tabla.getValueAt(i, 3)));
        }
        txt_total.setText("" + total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form_print = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_invoice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        compras_detalles_tabla = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_imprimir_factura = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(620, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        form_print.setBackground(new java.awt.Color(152, 202, 63));
        form_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/face.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        form_print.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 70));

        jPanel2.setBackground(new java.awt.Color(18, 45, 61));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Farmacia JIREH");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        txt_invoice.setEditable(false);
        txt_invoice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txt_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 110, 25));

        form_print.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 70));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Detalles de la Compra");
        form_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        compras_detalles_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Precio", "Subtotal", "Proveedor", "Comprado por", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(compras_detalles_tabla);
        if (compras_detalles_tabla.getColumnModel().getColumnCount() > 0) {
            compras_detalles_tabla.getColumnModel().getColumn(0).setMinWidth(100);
            compras_detalles_tabla.getColumnModel().getColumn(5).setMinWidth(110);
            compras_detalles_tabla.getColumnModel().getColumn(6).setMinWidth(80);
        }

        form_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 560, 150));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total:");
        form_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        txt_total.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        form_print.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 120, -1));

        getContentPane().add(form_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 520));

        btn_imprimir_factura.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_imprimir_factura.setText("Imprimir");
        btn_imprimir_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimir_facturaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_imprimir_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 540, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_imprimir_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimir_facturaActionPerformed
        Toolkit tk = form_print.getToolkit();
        PrintJob pj = tk.getPrintJob(this, null, null);
        Graphics grafic = pj.getGraphics();
        form_print.print(grafic);
        grafic.dispose();
        pj.end();
    }//GEN-LAST:event_btn_imprimir_facturaActionPerformed

    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_imprimir_factura;
    public javax.swing.JTable compras_detalles_tabla;
    private javax.swing.JPanel form_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_invoice;
    public javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
