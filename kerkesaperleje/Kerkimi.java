/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kerkesaperleje;

//import com.sun.xml.internal.ws.util.ReadAllStream;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
//import javax.swing.ListSelectionModel;

import java.lang.reflect.Constructor;

/**
 *
 * @author Iliriani
 */
public class Kerkimi extends javax.swing.JFrame {

    private int id = 0;
    private String tabela_entitetit;
    private String celesi_entitetit;
    private String forma_entitetit;
    private String head_name;
    private String roli;
    private Statement state;

    /**
     * Creates new form Kerkimi
     */
    public Kerkimi(String tbl_ent, String cel_ent, String for_ent, String head_name, String roli) {

        this.tabela_entitetit = tbl_ent;
        this.celesi_entitetit = cel_ent;
        this.forma_entitetit = for_ent;
        this.head_name = head_name;
        this.roli = roli;
        //lblkerkimi.setText(head_name); 
        initComponents();
        mbusheKombon();
        mbusheTabelenEFushave();
        validorolin();
    }

//Menaxhon privilegjet ne baze te roleve  
    public void validorolin() {
        if (roli.equals("Asistent")) {
            mraportet.setVisible(false);
            madministrimi.setVisible(false);
        } else if (roli.equals("Menaxher")) {
            madministrimi.setVisible(false);
        }
    }

    //lidhjet me Bazen e Shenimeve me ane te nje drajveri dhe kthen ate lidhje   
    public Statement statemt() {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            this.state = stmt;
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;

    }

    //e mbush komboboksin me te dhena nga Baza e Shenimeve
    public void mbusheKombon() {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select PershkrimiNeNderfaqe,Fusha from FushatEKerkimit "
                    + "where TabelaKryesore='" + tabela_entitetit + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                kmbkerko.addItem(rs.getString("PershkrimiNeNderfaqe"));
            }
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //mbushet tabela me te dhena te filtruara nga Baza e Shenimeve
    public void mbushetabelen() {
        int rreshti = merreVlerenReshtit((String) kmbkerko.getSelectedItem());

        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            //nje query dinamik nga DB
            String query = "SELECT " + tabela_entitetit + "." + celesi_entitetit;
            // ndertoje pjesen e fushave dhe te join-ave ne cikel
            String fushat = "";
            String joinat = "";
            for (int i = 0; i < tblfushat.getModel().getRowCount(); i++) {
                fushat += ", " + tblfushat.getModel().getValueAt(i, 1) + "." + tblfushat.getModel().getValueAt(i, 2)
                        + " AS '" + tblfushat.getModel().getValueAt(i, 4) + "' ";

                // nese ka JOIN, shtoja
                if (tblfushat.getModel().getValueAt(i, 3) != null) {
                    joinat += " JOIN " + tblfushat.getModel().getValueAt(i, 1) + " ON "
                            + tabela_entitetit + "." + tblfushat.getModel().getValueAt(i, 3)
                            + " = " + tblfushat.getModel().getValueAt(i, 1) + "."
                            + tblfushat.getModel().getValueAt(i, 3);
                }


            }
            String where = " WHERE " + (String) tblfushat.getModel().getValueAt(rreshti, 2)
                    + " LIKE '%" + txtkerkoje.getText() + "%'";

            // ngjiti stringjet
            query += fushat + " FROM " + tabela_entitetit + joinat + where;

            // testoje kerkesen
            System.out.println(query);


            /**
             * Query static String sql = "select k.idkerkesa, k.nr_protokollit
             * as \"Numri i Protokollit\", " + "k.kompania as \"Kompania\",
             * lj.lloji_lejes as \"Lloji i Lejes\", " + "ak.aktiviteti as
             * \"Aktiviteti\", p.pergjigjja as \"Pergjigjja e Komisionit\" " +
             * "from kerkesat k " + "join llojet_lejes lj on
             * k.idlloji=lj.idlloji " + "join aktivitetet ak on
             * k.idaktiviteti=ak.idaktiviteti " + "join pergjigjjet_komisionit p
             * on k.idpergjigjja=p.idpergjigjja " + "WHERE " +
             * (String)tblfushat.getModel().getValueAt(rreshti, 2) + " LIKE '%"
             * + txtkerkoje.getText() + "%'";
             */
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(query);
            tblkerkesat.setModel(DBUtils.resultSetToTableModel(rs));
            //fshehe kolonen celesi_entitetit ne tabel
            tblkerkesat.getColumn(celesi_entitetit).setWidth(0);
            tblkerkesat.getColumn(celesi_entitetit).setMinWidth(0);
            tblkerkesat.getColumn(celesi_entitetit).setMaxWidth(0);

            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mbusheTabelenEFushave() {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();;
            String sql = "select * from FushatEKerkimit where TabelaKryesore = '"
                    + tabela_entitetit + "'";

            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            tblfushat.setModel(DBUtils.resultSetToTableModel(rs));
            tblfushat.setVisible(true);

            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int merreVlerenReshtit(String v_kombos) {
        int rreshti = 0;
        for (int i = 0; i < tblfushat.getModel().getRowCount(); i++) {
            for (int j = 0; j < tblfushat.getModel().getColumnCount(); j++) {
                String v_fushes = (String) tblfushat.getValueAt(i, j);
                //String v_kombos=(String)kmbkerko.getSelectedItem();
                if (v_fushes == null) {
                    j++;
                    v_fushes = (String) tblfushat.getValueAt(i, j);
                }

                if (v_fushes.equals(v_kombos)) {
                    rreshti = i;
                }
                //System.out.print((String)tblfushat.getValueAt(i, j)+ "|");
            }
            System.out.println();

        }
        System.out.println(rreshti);
        return rreshti;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnregjistro = new javax.swing.JButton();
        btnndrysho = new javax.swing.JButton();
        btnfshije = new javax.swing.JButton();
        btnkerko = new javax.swing.JButton();
        btnpastro = new javax.swing.JButton();
        lblkerkote = new javax.swing.JLabel();
        txtkerkoje = new javax.swing.JTextField();
        lblkerko = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblkerkesat = new javax.swing.JTable();
        lblkerkimi = new javax.swing.JLabel();
        kmbkerko = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblfushat = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mkerkesat = new javax.swing.JMenu();
        mraportet = new javax.swing.JMenu();
        madministrimi = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        btnregjistro.setText("Regjistro");
        btnregjistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregjistroActionPerformed(evt);
            }
        });

        btnndrysho.setText("Ndrysho");
        btnndrysho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnndryshoActionPerformed(evt);
            }
        });

        btnfshije.setText("Fshije");
        btnfshije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfshijeActionPerformed(evt);
            }
        });

        btnkerko.setText("Kerko");
        btnkerko.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnkerko.setSelected(true);
        btnkerko.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkerkoActionPerformed(evt);
            }
        });
        btnkerko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnkerkoKeyReleased(evt);
            }
        });

        btnpastro.setText("Pastro");
        btnpastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpastroActionPerformed(evt);
            }
        });

        lblkerkote.setText("Kerko te:");

        lblkerko.setText("Kerko:");

        tblkerkesat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Numri i Protokollit", "Kompania", "Lloji i Lejes", "Aktiviteti","Pergjigjja e Komisionit"
            }
        ));
        tblkerkesat.getTableHeader().setReorderingAllowed(false);
        tblkerkesat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblkerkesatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblkerkesat);

        jScrollPane3.setViewportView(jScrollPane2);

        lblkerkimi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblkerkimi.setForeground(new java.awt.Color(153, 153, 0));
        lblkerkimi.setText(head_name);

        kmbkerko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Zgjidh>" }));

        tblfushat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TabelaKryesore", "TabelaEFushes", "Fusha", "CelesiIJashtem","PershkrimiNeNderfaqe"
            }
        ));
        jScrollPane4.setViewportView(tblfushat);

        mkerkesat.setText("Kerkesat");
        mkerkesat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mkerkesatMouseClicked(evt);
            }
        });
        jMenuBar1.add(mkerkesat);

        mraportet.setText("Raportet");
        mraportet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mraportetMouseClicked(evt);
            }
        });
        jMenuBar1.add(mraportet);

        madministrimi.setText("Administrimi");
        madministrimi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                madministrimiMouseClicked(evt);
            }
        });
        jMenuBar1.add(madministrimi);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblkerkimi))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblkerkote)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(kmbkerko, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(lblkerko))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnregjistro)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnndrysho)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtkerkoje, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnkerko)
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnpastro)
                                            .addComponent(btnfshije)))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblkerkimi)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnregjistro)
                    .addComponent(btnndrysho)
                    .addComponent(btnfshije))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnkerko)
                    .addComponent(btnpastro)
                    .addComponent(lblkerkote)
                    .addComponent(txtkerkoje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblkerko)
                    .addComponent(kmbkerko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnkerkoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkerkoActionPerformed
        if (kmbkerko.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Ju lutem zgjidheni nje kerkese!");
        } else {
            mbushetabelen();
            tblkerkesat.setVisible(true);
        }
    }//GEN-LAST:event_btnkerkoActionPerformed

    private void btnndryshoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnndryshoActionPerformed
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "Ju lutem zgjidheni nje kerkese!");
        } else {
            try {
                Class klasa = Class.forName("kerkesaperleje." + forma_entitetit);
                Constructor konstruktori = klasa.getConstructor(new Class[]{boolean.class, int.class, String.class});
                Object objekti2 =
                        konstruktori.newInstance(new Object[]{false, id, roli});
                System.out.println("objekti2 = " + objekti2);
                ((javax.swing.JFrame) objekti2).setVisible(true);
                //r.setVisible(true);
                ((javax.swing.JFrame) objekti2).setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Flamuri_i_Kosoves.png"));
                ((javax.swing.JFrame) objekti2).setTitle("Menaxhimi i kerkesave per leje");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnndryshoActionPerformed

    private void btnregjistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregjistroActionPerformed
        try {
            Class klasa = Class.forName("kerkesaperleje." + forma_entitetit);
            Constructor konstruktori = klasa.getConstructor(new Class[]{boolean.class, int.class, String.class});
            Object objekti2 =
                    konstruktori.newInstance(new Object[]{true, 0, roli});
            System.out.println("objekti2 = " + objekti2);
            ((javax.swing.JFrame) objekti2).setVisible(true);
            //r.setVisible(true);
            ((javax.swing.JFrame) objekti2).setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Flamuri_i_Kosoves.png"));
            ((javax.swing.JFrame) objekti2).setTitle("Menaxhimi i kerkesave per leje");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnregjistroActionPerformed

    private void tblkerkesatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblkerkesatMouseClicked
        int row = tblkerkesat.getSelectedRow();
        id = (int) tblkerkesat.getModel().getValueAt(row, 0);
    }//GEN-LAST:event_tblkerkesatMouseClicked

    private void btnpastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpastroActionPerformed
        System.out.println((String) tblfushat.getModel().getValueAt(merreVlerenReshtit((String) kmbkerko.getSelectedItem()), 2));
        merreVlerenReshtit((String) kmbkerko.getSelectedItem());
        //lblkerkimi.setText("Test");
    }//GEN-LAST:event_btnpastroActionPerformed

    private void btnfshijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfshijeActionPerformed
        try {
            //int reply = JOptionPane.showConfirmDialog(this, "a deshironi ta fshini kerkesen?", "fshije", JOptionPane.YES_NO_OPTION);
            if (tblkerkesat.isVisible() && id != 0) {
                Object[] options = {"Po",
                    "Jo"};
                int zgjedhja = JOptionPane.showOptionDialog(this, "A deshironi ta fshini kerkesen e zgjedhur?", "Konfirmo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, options, options[1]);

                if (zgjedhja == JOptionPane.YES_OPTION) {
                    Statement stmt1 = LidhjaMeDb.hapelidhjen();;
                    String sql1 = "delete from kerkesat_zyret where idkerkesa=" + id;
                    int rs = stmt1.executeUpdate(sql1);
                    //CallableStatement stmt = conn.prepareCall("{call procedura1(?)}");
                    //stmt.setInt(1, 1);
                    //ResultSet rs=stmt.executeQuery();
                    Statement stmt = LidhjaMeDb.hapelidhjen();
                    String sql = "delete from " + tabela_entitetit + " where " + celesi_entitetit + "=" + id;
                    int n = stmt.executeUpdate(sql);
                    //LidhjaMeDb lidhja=new LidhjaMeDb();
                    //int n=lidhja.fshijekerkesen(id);
                    mbushetabelen();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Ju lutem zgjidheni nje kerkese!");
            }

            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            System.out.println("gabim te butoni fshije");
            JOptionPane.showMessageDialog(this, "Ju duhet ti f");
        }
    }//GEN-LAST:event_btnfshijeActionPerformed

    private void madministrimiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_madministrimiMouseClicked
        Kerkimi k = new Kerkimi("perdoruesit", "idperdoruesi", "Administro", "Administrimi", roli);
        k.setVisible(true);
        k.setTitle("Menaxhimi i kerkesave per leje");
        k.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Flamuri_i_Kosoves.png"));
        this.setVisible(false);
    }//GEN-LAST:event_madministrimiMouseClicked

    private void mraportetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mraportetMouseClicked
        Raportet r = new Raportet(roli);
        r.setVisible(true);
        r.setTitle("Menaxhimi i kerkesave per leje");
        r.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Flamuri_i_Kosoves.png"));
    }//GEN-LAST:event_mraportetMouseClicked

    private void mkerkesatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mkerkesatMouseClicked
        Kerkimi k = new Kerkimi("kerkesat", "idkerkesa", "RegjistroKerkese", "Kerkesat", roli);
        k.setVisible(true);
        k.setTitle("Menaxhimi i kerkesave per leje");
        k.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Flamuri_i_Kosoves.png"));
        this.setVisible(false);
    }//GEN-LAST:event_mkerkesatMouseClicked

    private void btnkerkoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnkerkoKeyReleased
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            System.out.println("enter");
            btnkerkoActionPerformed(null);
            //this.btnkycu.getAction().actionPerformed(java.awt.event.ActionEvent e);
        }
    }//GEN-LAST:event_btnkerkoKeyReleased

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
            java.util.logging.Logger.getLogger(Kerkimi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kerkimi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kerkimi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kerkimi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Kerkimi("", "", "", "", "").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfshije;
    private javax.swing.JButton btnkerko;
    private javax.swing.JButton btnndrysho;
    private javax.swing.JButton btnpastro;
    private javax.swing.JButton btnregjistro;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox kmbkerko;
    private javax.swing.JLabel lblkerkimi;
    private javax.swing.JLabel lblkerko;
    private javax.swing.JLabel lblkerkote;
    private javax.swing.JMenu madministrimi;
    private javax.swing.JMenu mkerkesat;
    private javax.swing.JMenu mraportet;
    private javax.swing.JTable tblfushat;
    private javax.swing.JTable tblkerkesat;
    private javax.swing.JTextField txtkerkoje;
    // End of variables declaration//GEN-END:variables
}
