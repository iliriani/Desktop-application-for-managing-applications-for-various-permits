/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kerkesaperleje;

import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Iliriani
 */
public class Raportet extends javax.swing.JFrame {

    private LidhjaMeDb lidhja;
    private int kmbidkomunat, kmbidaktiviteti, kmbidpergjigjja;
    private String porosia = "";
    private String roli;
    private Statement state;

    /**
     * Creates new form Raportet
     */
    public Raportet(String roli) {
        this.roli = roli;

        initComponents();
        validorolin();

//mbushi kombot me shenime nga DB   
        try {
            lidhja = new LidhjaMeDb();
            mbushekombon(lidhja.aktivitetet(), "aktiviteti", kmbaktiviteti);
            mbushekombon(lidhja.komunat(), "komuna", kmbkomunat);
            mbushekombon(lidhja.pergjigjjet(), "pergjigjja", kmbpergjigjja);
            
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//lidhet me Db me anen e drajverit dhe e kthen ate lidhje   
    public Statement statemt() {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            this.state = stmt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;

    }

//Nje metod per ti mbushur kombot me shenime nga DB
    public void mbushekombon(ResultSet rst, String col, javax.swing.JComboBox kmb) {

        try {
            ResultSet rs = rst;
            while (rs.next()) {
                kmb.addItem(rs.getString(col));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//e fsheh mundesin e administrimit per rolin menaxher    
    public void validorolin() {
        if (roli.equals("Menaxher")) {
            madministrimi.setVisible(false);
        }

    }

/*mbushet tabela me te dhena nga Db ne menyre dinamike te filtruara
 * sipas te dhenave te perdoruesit
 * 
 */
    private void mbushetabelen() {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            //CallableStatement stmt = conn.prepareCall("{call procedura1(?)}");
            //stmt.setInt(1, 1);
            //ResultSet rs=stmt.executeQuery();
            String sql = "SELECT kerkesat.kompania AS 'Kompania', llojet_lejes.lloji_lejes AS 'Lloji i Lejes'"
                    + " ,kerkesat.adresa AS 'Adresa', kerkesat.tel AS 'Tel',kerkesat.data_pranimit AS 'Data e Pranimit'"
                    + " ,kerkesat.nr_protokollit AS 'Numri i Protokollit', aktivitetet.aktiviteti AS 'Aktiviteti'"
                    + " ,komunat.komuna AS 'Komuna', kerkesat.taksa AS 'Taksa', kerkesat.statusi_lendes AS 'Statusi i Lendes'"
                    + " ,kerkesat.data_leshimit AS 'Data e Leshimit'"
                    + " ,pergjigjjet_komisionit.pergjigjja AS 'Pergjigjja e Komisionit'  FROM kerkesat "
                    + " JOIN llojet_lejes ON kerkesat.idlloji = llojet_lejes.idlloji "
                    + " JOIN aktivitetet ON kerkesat.idaktiviteti = aktivitetet.idaktiviteti "
                    + " JOIN pergjigjjet_komisionit ON kerkesat.idpergjigjja = pergjigjjet_komisionit.idpergjigjja "
                    + " JOIN komunat ON kerkesat.idkomuna=komunat.idkomuna "
                    + "WHERE kompania LIKE '%" + txtkompania.getText() + "%' "
                    + "AND aktiviteti like '%" + (String) kmbaktiviteti.getSelectedItem() + "%' "
                    + "AND komuna like '%" + (String) kmbkomunat.getSelectedItem() + "%' "
                    + "AND pergjigjja like '%" + (String) kmbpergjigjja.getSelectedItem() + "%' "
                    + "AND data_pranimit >= '" + new java.sql.Date(jdataprej.getDate().getTime()) + "' AND data_pranimit <= '" + new java.sql.Date(jdataderi.getDate().getTime()) + "' ";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            tblkerkesat.setModel(DBUtils.resultSetToTableModel(rs));
//boolean Zgjedhur=false;jTable1.addColumn(Zgjedhur);
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
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

        lbldata_pranimit = new javax.swing.JLabel();
        lblaktiviteti = new javax.swing.JLabel();
        kmbaktiviteti = new javax.swing.JComboBox();
        lblkomuna = new javax.swing.JLabel();
        kmbkomunat = new javax.swing.JComboBox();
        lblpergjigjja = new javax.swing.JLabel();
        kmbpergjigjja = new javax.swing.JComboBox();
        lblkerkesa = new javax.swing.JLabel();
        btnnxjerrRaportin = new javax.swing.JButton();
        btnprinto = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblkerkesat = new javax.swing.JTable();
        txtkompania = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jdataprej = new com.toedter.calendar.JDateChooser();
        jdataderi = new com.toedter.calendar.JDateChooser();
        lblprej = new javax.swing.JLabel();
        lblderi = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mkerkesat = new javax.swing.JMenu();
        mraportet = new javax.swing.JMenu();
        madministrimi = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        lbldata_pranimit.setText("Data e pranimit:");

        lblaktiviteti.setText("Aktiviteti-Veprimtaria:");

        kmbaktiviteti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbaktiviteti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbaktivitetiActionPerformed(evt);
            }
        });

        lblkomuna.setText("Komuna:");

        kmbkomunat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbkomunat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbkomunatActionPerformed(evt);
            }
        });

        lblpergjigjja.setText("Pergjigjja e Komisionit:");

        kmbpergjigjja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbpergjigjja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbpergjigjjaActionPerformed(evt);
            }
        });

        lblkerkesa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblkerkesa.setForeground(new java.awt.Color(102, 102, 0));
        lblkerkesa.setText("Raportet");

        btnnxjerrRaportin.setText("Nxjerr Raportin");
        btnnxjerrRaportin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnxjerrRaportinActionPerformed(evt);
            }
        });

        btnprinto.setText("Kerko");
        btnprinto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintoActionPerformed(evt);
            }
        });

        tblkerkesat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "idlloji", "kompania", "adresa", "tel,","data_pranimit","idaktiviteti","idkomuna","nr_protokollit","taksa","statusi_lendes","arkivimi_follder","data_leshimit","idpergjigjja"
            }
        ));
        jScrollPane1.setViewportView(tblkerkesat);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel1.setText("Kompania:");

        lblprej.setText("Prej:");

        lblderi.setText("Deri:");

        jMenuBar1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jMenuBar1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        mkerkesat.setText("Kerkesat");
        jMenuBar1.add(mkerkesat);

        mraportet.setText("Raportet");
        jMenuBar1.add(mraportet);

        madministrimi.setText("Administrimi");
        jMenuBar1.add(madministrimi);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblkerkesa)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblaktiviteti)
                                        .addComponent(lblkomuna)
                                        .addComponent(lblpergjigjja))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(kmbkomunat, 0, 88, Short.MAX_VALUE)
                                        .addComponent(kmbaktiviteti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(kmbpergjigjja, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(64, 64, 64))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnprinto)
                                    .addGap(79, 79, 79)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtkompania, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbldata_pranimit)
                            .addComponent(btnnxjerrRaportin)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblderi)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblprej)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jdataprej, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(jdataderi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblkerkesa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkompania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbldata_pranimit)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblaktiviteti)
                            .addComponent(kmbaktiviteti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblprej))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kmbkomunat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblkomuna))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(kmbpergjigjja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblpergjigjja)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jdataprej, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdataderi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblderi))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnxjerrRaportin)
                    .addComponent(btnprinto))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kmbaktivitetiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbaktivitetiActionPerformed
        try {
            String s = (String) kmbaktiviteti.getSelectedItem();//System.out.println(s);
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbaktivitetiid(s);
            kmbidaktiviteti = n;
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbaktivitetiActionPerformed

    private void kmbkomunatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbkomunatActionPerformed
        try {
            String s = (String) kmbkomunat.getSelectedItem();//System.out.println(s);
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbkomunatid(s);
            kmbidkomunat = n;
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbkomunatActionPerformed

    private void kmbpergjigjjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbpergjigjjaActionPerformed
        try {
            String s = (String) kmbpergjigjja.getSelectedItem();//System.out.println(s);
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbpergjigjjaid(s);
            kmbidpergjigjja = n;
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbpergjigjjaActionPerformed

    private void jMenuBar1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jMenuBar1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuBar1AncestorAdded

    private void btnprintoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintoActionPerformed
        if (kmbaktiviteti.getSelectedIndex() == 0 || kmbkomunat.getSelectedIndex() == 0 || kmbpergjigjja.getSelectedIndex() == 0
                || jdataprej.getDate() == null || jdataderi.getDate() == null || jdataprej.getDate().after(jdataderi.getDate())) {
            JOptionPane.showMessageDialog(this, "Ju lutem jepeni zgjidhjet e sakta!");
        } else {
            mbushetabelen();
            tblkerkesat.setVisible(true);
        }

    }//GEN-LAST:event_btnprintoActionPerformed

    private void btnnxjerrRaportinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnxjerrRaportinActionPerformed

        if (kmbaktiviteti.getSelectedIndex() == 0 || kmbkomunat.getSelectedIndex() == 0 || kmbpergjigjja.getSelectedIndex() == 0
                || jdataprej.getDate() == null || jdataderi.getDate() == null || jdataprej.getDate().after(jdataderi.getDate())) {
            JOptionPane.showMessageDialog(this, "Ju lutem jepeni zgjidhjet e sakta!");
        } else {
            try {

                Connection conn = LidhjaMeDb.merrelidhjen();
                //Statement stmt = conn.createStatement();
                String sql = "SELECT kerkesat.kompania AS 'Kompania', llojet_lejes.lloji_lejes AS 'Lloji i Lejes'"
                        + " ,kerkesat.adresa AS 'Adresa', kerkesat.tel AS 'Tel',kerkesat.data_pranimit AS 'Data e Pranimit'"
                        + " ,kerkesat.nr_protokollit AS 'Numri i Protokollit', aktivitetet.aktiviteti AS 'Aktiviteti'"
                        + " ,komunat.komuna AS 'Komuna', kerkesat.taksa AS 'Taksa', kerkesat.statusi_lendes AS 'Statusi i Lendes'"
                        + " ,kerkesat.data_leshimit AS 'Data e Leshimit'"
                        + " ,pergjigjjet_komisionit.pergjigjja AS 'Pergjigjja e Komisionit'  FROM kerkesat "
                        + " JOIN llojet_lejes ON kerkesat.idlloji = llojet_lejes.idlloji "
                        + " JOIN aktivitetet ON kerkesat.idaktiviteti = aktivitetet.idaktiviteti "
                        + " JOIN pergjigjjet_komisionit ON kerkesat.idpergjigjja = pergjigjjet_komisionit.idpergjigjja "
                        + " JOIN komunat ON kerkesat.idkomuna=komunat.idkomuna "
                        + " WHERE kompania LIKE '%" + txtkompania.getText() + "%'"
                        + " AND aktiviteti like '%" + (String) kmbaktiviteti.getSelectedItem() + "%'"
                        + " AND komuna like '%" + (String) kmbkomunat.getSelectedItem() + "%'"
                        + " AND pergjigjja like '%" + (String) kmbpergjigjja.getSelectedItem() + "%'"
                        + " AND data_pranimit > '" + new java.sql.Date(jdataprej.getDate().getTime()) + "' AND data_pranimit < '" + new java.sql.Date(jdataderi.getDate().getTime()) + "'";
                //ResultSet rs = stmt.executeQuery(sql);


                //String fileName = getClass().getClassLoader().getResource("com/foo/myproject/reports/Raporti2.jrxml").getFile();Raporti2

                String fileName = getClass().getClassLoader().getResource("RaportiIm.jrxml").getFile();
                File theFile = new File(fileName);
                JasperDesign jasperDesign = JRXmlLoader.load(theFile);

                //Build a new query
                //String theQuery = "select * from perdoruesit";

                // update the data query
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jasperDesign.setQuery(newQuery);

                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                //Connection conn = MyDatabaseClass.getConnection();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
                JasperViewer.viewReport(jasperPrint, false);
                //int n=JasperViewer.DISPOSE_ON_CLOSE;
                LidhjaMeDb.mbyllelidhjen();

            } catch (Exception ex) {
                String connectMsg = "Could not create the report " + ex.getMessage() + " " + ex.getLocalizedMessage();
                System.out.println(connectMsg);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnnxjerrRaportinActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Raportet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Raportet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Raportet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Raportet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Raportet("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnnxjerrRaportin;
    private javax.swing.JButton btnprinto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdataderi;
    private com.toedter.calendar.JDateChooser jdataprej;
    private javax.swing.JComboBox kmbaktiviteti;
    private javax.swing.JComboBox kmbkomunat;
    private javax.swing.JComboBox kmbpergjigjja;
    private javax.swing.JLabel lblaktiviteti;
    private javax.swing.JLabel lbldata_pranimit;
    private javax.swing.JLabel lblderi;
    private javax.swing.JLabel lblkerkesa;
    private javax.swing.JLabel lblkomuna;
    private javax.swing.JLabel lblpergjigjja;
    private javax.swing.JLabel lblprej;
    private javax.swing.JMenu madministrimi;
    private javax.swing.JMenu mkerkesat;
    private javax.swing.JMenu mraportet;
    private javax.swing.JTable tblkerkesat;
    private javax.swing.JTextField txtkompania;
    // End of variables declaration//GEN-END:variables
}
