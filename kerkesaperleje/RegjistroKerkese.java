/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kerkesaperleje;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;

/**
 *
 * @author Iliriani
 */
public class RegjistroKerkese extends javax.swing.JFrame {

    private LidhjaMeDb lidhja;
    private int kmbidlejet, kmbidkomunat, kmbidaktiviteti, kmbidpergjigjja;
    private String porosia = "";
    private String roli;
    private ResultSet rst;
    private boolean insert;
    private int id;
    private javax.swing.table.DefaultTableModel model;
    private java.sql.Date data;
    private Statement state;
    //private Kerkesat k;
    // private Vector<Vector<String>> data; //used for data from database
    //private Vector<String> header; //used to store data header

    /**
     * Creates new form RegjistroKerkese
     */
    public RegjistroKerkese(boolean gjendja, int id, String roli) {
        this.insert = gjendja;
        this.id = id;
        this.roli = roli;
        initComponents();
        valido();

        //ketu mbushen combot
        try {
            lidhja = new LidhjaMeDb();
            mbushekombon(lidhja.llojet_lejeve(), "lloji_lejes", kmblejet);
            mbushekombon(lidhja.aktivitetet(), "aktiviteti", kmbaktiviteti);
            mbushekombon(lidhja.komunat(), "komuna", kmbkomunat);
            mbushekombon(lidhja.pergjigjjet(), "pergjigjja", kmbpergjigjja);

            lidhja.mbyllelidhjen();
        } catch (Exception e) {
            System.out.println("Gabim te mbushekombon");
            e.printStackTrace();
        }



        //metodat e klases
        kontrollogjendjen();
        mbushetabelen();
        mbusheCheckBoxListen(cbzyret, id);


    }

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

    public void valido() {
        if (roli.equals("Asistent")) {
            mraportet.setVisible(false);
            madministrimi.setVisible(false);
        } else if (roli.equals("Menaxher")) {
            madministrimi.setVisible(false);
        }
    }

    public RegjistroKerkese(int id) {
        this((id == 0), id, "");
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

    //Kthene gjitha zyret qe gjenden ne DB
    private ResultSet merrizyret() {
        ResultSet rst1 = null;
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select emri from zyret";
            ResultSet rs = stmt.executeQuery(sql);
            rst1 = rs;
            //LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst1;
    }

    //Kthen id-te e zyrave ne te cilat gjendet kerkesa me id-ne perkatese
    private ResultSet merridzyret(int idkerkesa) {
        ResultSet rst1 = null;
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select idzyra from kerkesat_zyret where idkerkesa=" + idkerkesa;
            ResultSet rs = stmt.executeQuery(sql);
            rst1 = rs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst1;
    }

    //kthen emrin e zyres te ciles i perket ajo id
    private String merrizyret(int idzyra) {
        String zyra = "";
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select emri from zyret where idzyra=" + idzyra;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                zyra = rs.getString("emri");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zyra;
    }

    //E mbush nje Chekboxlist
    public void mbusheCheckBoxListen(creator.components.CheckBoxList cb, int idkerkesa) {
        try {
            ResultSet rs = merrizyret();
            while (rs.next()) {
                idkerkesa = this.id;
                String zyret = rs.getString("emri");
                //System.out.print(zyret + " ");
                boolean vlera = false;
                ResultSet rs1 = merridzyret(idkerkesa);
                while (rs1.next()) {
                    String zyra_k = merrizyret(rs1.getInt("idzyra"));
                    //System.out.print(zyra_k + " ");
                    if (zyret.equals(zyra_k)) {
                        //checkBoxList1.add(rs.getString("emri"), true);
                        vlera = true;
                        break;
                    }
                }
                cb.add(zyret, vlera);
                //System.out.println(vlera);
                //System.out.println();
            }
            LidhjaMeDb.mbyllelidhjen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Merr vlerat nga DB per ta mbushur CheckBoxlisten zyrat
    public ResultSet getrs() {

        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select z.idzyra,z.emri from zyret as z";
            ResultSet rs = stmt.executeQuery(sql);
            this.rst = rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

//E mbushe tabelen zyret me shenime
/*
     * private void mbushetabelen(){
     *
     * JCheckBox checkBox = new javax.swing.JCheckBox();
     * tblfajllat.getColumn("Derguar").setCellEditor(new
     * DefaultCellEditor(checkBox)); try {
     * Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); Connection conn =
     * DriverManager.getConnection("jdbc:odbc:lidhja_sql"); Statement stmt =
     * conn.createStatement(); String sql = "select z.idzyra ,z.emri from zyret
     * as z"; ResultSet rs = stmt.executeQuery(sql);
     * tblfajllat.setModel(DBUtils.resultSetToTableModel(rs)); } catch
     * (ClassNotFoundException | SQLException e) { }
     *
     * }
     */
    private void mbushetabelen() {
        //...duhet ta mbushe nje table
    }

    //metoda per mbushjen e jdatave me addhock query
    public java.sql.Date merredaten(String col, int idja) {
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select " + col + " from kerkesat where idkerkesa=" + idja;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                this.data = rs.getDate(col);
            }
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }

    //shtone kerkesen ne zyret e caktuara
    public void merriZyret(int idja) {
        Vector v = new Vector();
        v = cbzyret.SelectedItems();
        Enumeration e = v.elements();
        while (e.hasMoreElements()) {

            try {
                lidhja = new LidhjaMeDb();
                int idz = lidhja.merrecbzyretid(e.nextElement().toString());//kthen id e zyres sipas emrit te saj
                //int idk=lidhja.merrekerkesenid();//kthen id-ne maksimale ne DB!
                lidhja.shtokerkesenezyre(idja, idz);
                System.out.println(idz);
                LidhjaMeDb.mbyllelidhjen();

            } catch (Exception exp) {
                exp.printStackTrace();
            }

        }


    }

    //ndryshon zyret 
    public void updateZyret() throws Exception {
        Statement stmt = LidhjaMeDb.hapelidhjen();
        String sql = "delete from kerkesat_zyret where idkerkesa=" + id;
        int rs = stmt.executeUpdate(sql);
        merriZyret(id);
        //System.out.println(idz);
        LidhjaMeDb.mbyllelidhjen();


    }

    //Kontrollon se a jemi duke e futur nje kerkese te re apo duke e ndryshuar ate 
    public boolean kontrollogjendjen() {
        try {
            if (insert == true) {
                //Nese kemi hyre per te insertuar
                System.out.println("insert");
                return true;
            } else {
                //Kur te bejmi ndonje update te kerkesave
                System.out.println("update " + id);
                mbushitxt(id);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return insert;

    }

    //tregon pathin se ku do te ruhen fajllat shtese, D:\\kerkesat
    public String merrifajllin() {

        String vlera = "";
        try {
            Statement stmt = LidhjaMeDb.hapelidhjen();
            String sql = "select vlera from konfigurimi";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(rs.getString("vlera"));
                vlera = vlera + rs.getString("vlera");
            }
            LidhjaMeDb.mbyllelidhjen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vlera;
    }

    //Metoda qe i mbushe fushat perkates kur kemi te bejme me update
    public void mbushitxt(int id) {

        System.out.println("Hyrja ne mbushitxt " + id);
        try {
            lidhja = new LidhjaMeDb();
            ResultSet rs = lidhja.vlerat_kerkeses(id);
            while (rs.next()) {
                System.out.println("Hyrja rs.next");
                kmblejet.setSelectedIndex(rs.getInt("idlloji"));
                txtkompania.setText(rs.getString("kompania"));
                txtadresa.setText(rs.getString("adresa"));
                txttel.setText(rs.getString("tel"));
                kmbaktiviteti.setSelectedIndex(rs.getInt("idaktiviteti"));
                kmbkomunat.setSelectedIndex(rs.getInt("idkomuna"));
                txtnrprotokollit.setText(rs.getString("nr_protokollit"));
                txttaksa.setText(rs.getString("taksa"));
                txtstatusi_lendes.setText(rs.getString("statusi_lendes"));
                txtarkivimi.setText(rs.getString("arkivimi_follder"));
                kmbpergjigjja.setSelectedIndex(rs.getInt("idpergjigjja"));
                jdatapranimit.setDate(merredaten("data_pranimit", id));
                jdataleshimit.setDate(merredaten("data_leshimit", id));
                System.out.println("Mbarimi rs.next");
            }
            LidhjaMeDb.mbyllelidhjen();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("gabim te metoda mbushitxt");
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

        jLabel1 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lbllloji_lejes = new javax.swing.JLabel();
        kmblejet = new javax.swing.JComboBox();
        lbladresa = new javax.swing.JLabel();
        txtadresa = new javax.swing.JTextField();
        txttel = new javax.swing.JTextField();
        lbltel = new javax.swing.JLabel();
        lblkerkesa = new javax.swing.JLabel();
        lbldata_pranimit = new javax.swing.JLabel();
        kmbaktiviteti = new javax.swing.JComboBox();
        lblaktiviteti = new javax.swing.JLabel();
        kmbkomunat = new javax.swing.JComboBox();
        lblkomuna = new javax.swing.JLabel();
        lblnrprotokollit = new javax.swing.JLabel();
        txtnrprotokollit = new javax.swing.JTextField();
        lbltaksa = new javax.swing.JLabel();
        txttaksa = new javax.swing.JTextField();
        lblstatusi_lendes = new javax.swing.JLabel();
        txtstatusi_lendes = new javax.swing.JTextField();
        lblarkivimi = new javax.swing.JLabel();
        txtarkivimi = new javax.swing.JTextField();
        lbldata_leshimit = new javax.swing.JLabel();
        lblpergjigjja = new javax.swing.JLabel();
        kmbpergjigjja = new javax.swing.JComboBox();
        btnruajkerkesen = new javax.swing.JButton();
        btnanulokerkesen = new javax.swing.JButton();
        txtkompania = new javax.swing.JTextField();
        lblkompania = new javax.swing.JLabel();
        lblporosia_kerkesa = new javax.swing.JLabel();
        cbzyret = new creator.components.CheckBoxList();
        btnshtodoc = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblfajllat = new javax.swing.JTable();
        jdatapranimit = new com.toedter.calendar.JDateChooser();
        jdataleshimit = new com.toedter.calendar.JDateChooser();
        btnfshije = new javax.swing.JButton();
        lblzyret = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mkerkesat = new javax.swing.JMenu();
        mraportet = new javax.swing.JMenu();
        madministrimi = new javax.swing.JMenu();

        jLabel1.setText("jLabel1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        lbllloji_lejes.setText("Lloji i lejes:");

        kmblejet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmblejet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmblejetActionPerformed(evt);
            }
        });

        lbladresa.setText("Adresa:");

        lbltel.setText("Telefoni:");

        lblkerkesa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblkerkesa.setForeground(new java.awt.Color(102, 102, 0));
        lblkerkesa.setText("Kerkesa per leje ");

        lbldata_pranimit.setText("Data e pranimit:");

        kmbaktiviteti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbaktiviteti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbaktivitetiActionPerformed(evt);
            }
        });

        lblaktiviteti.setText("Aktiviteti-Veprimtaria:");

        kmbkomunat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbkomunat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbkomunatActionPerformed(evt);
            }
        });

        lblkomuna.setText("Komuna:");

        lblnrprotokollit.setText("Numri i Protokollit:");

        lbltaksa.setText("Taksa:");

        lblstatusi_lendes.setText("Statusi i Lendes:");

        lblarkivimi.setText("Arkivimi ne Follder:");

        lbldata_leshimit.setText("Data e leshimit:");

        lblpergjigjja.setText("Pergjigjja e Komisionit:");

        kmbpergjigjja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<zgjedh>" }));
        kmbpergjigjja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kmbpergjigjjaActionPerformed(evt);
            }
        });

        btnruajkerkesen.setText("Ruaj");
        btnruajkerkesen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnruajkerkesenActionPerformed(evt);
            }
        });

        btnanulokerkesen.setText("Anulo");
        btnanulokerkesen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanulokerkesenActionPerformed(evt);
            }
        });

        lblkompania.setText("Kompania:");

        lblporosia_kerkesa.setForeground(new java.awt.Color(255, 0, 0));

        btnshtodoc.setText("Shto Doc");
        btnshtodoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnshtodocActionPerformed(evt);
            }
        });

        String s=merrifajllin();
        s=s+id;
        int i=0;
        File folder = new File(s);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null){
            String[] vlera=new String[listOfFiles.length];
            for (File listOfFile : listOfFiles){
                if (listOfFile.isDirectory())
                System.out.println(listOfFile.getName());
                vlera[i]=listOfFile.getName();
                i++;}
            String data[][] = {{}};
            String col[] = {"Fajlli", "Pershkrimi", "Fshije"};
            model=new javax.swing.table.DefaultTableModel(data,col);
            for(int k=0;k<vlera.length;k++){
                model.insertRow(tblfajllat.getRowCount(), new Object[]{vlera[k]});
            }
        }
        else{
            String data[][] = {{}};
            String col[] = {"Fajlli", "Pershkrimi", "Fshije"};
            model=new javax.swing.table.DefaultTableModel(data,col);}
        tblfajllat.setModel(model);
        tblfajllat.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblfajllat);

        btnfshije.setText("Fshije");
        btnfshije.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfshijeActionPerformed(evt);
            }
        });

        lblzyret.setText("Zyret:");

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
        mraportet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mraportetMouseClicked(evt);
            }
        });
        mraportet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mraportetActionPerformed(evt);
            }
        });
        jMenuBar1.add(mraportet);

        madministrimi.setText("Administrimi");
        jMenuBar1.add(madministrimi);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(232, 232, 232)
                                .addComponent(btnruajkerkesen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnanulokerkesen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblzyret)
                                    .addComponent(cbzyret, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(147, 147, 147)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblstatusi_lendes, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbltaksa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblnrprotokollit)
                                    .addGap(221, 221, 221)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnshtodoc)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnfshije)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lbldata_leshimit)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lblpergjigjja)
                                                    .addComponent(lblarkivimi, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kmbpergjigjja, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jdataleshimit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txttaksa, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtnrprotokollit, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtarkivimi, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtstatusi_lendes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))))))
                                .addGap(91, 91, 91))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblkerkesa)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblkomuna)
                                        .addGap(72, 72, 72)
                                        .addComponent(kmbkomunat, 0, 111, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblaktiviteti)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(kmbaktiviteti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbldata_pranimit)
                                            .addComponent(lblkompania)
                                            .addComponent(lbladresa)
                                            .addComponent(lbllloji_lejes)
                                            .addComponent(lbltel))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txttel, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtadresa, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtkompania, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(kmblejet, 0, 111, Short.MAX_VALUE)
                                            .addComponent(jdatapranimit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addComponent(lblporosia_kerkesa, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblkerkesa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kmblejet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbllloji_lejes))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblkompania)
                            .addComponent(txtkompania, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbladresa)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtadresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbltel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbldata_pranimit, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jdatapranimit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kmbaktiviteti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblaktiviteti))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblkomuna)
                            .addComponent(kmbkomunat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblnrprotokollit)
                                    .addComponent(txtnrprotokollit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(txttaksa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblstatusi_lendes)
                                    .addComponent(txtstatusi_lendes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblarkivimi)
                                    .addComponent(txtarkivimi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(lbltaksa)))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbldata_leshimit)
                            .addComponent(jdataleshimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kmbpergjigjja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblpergjigjja))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblporosia_kerkesa, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnshtodoc)
                        .addComponent(btnfshije))
                    .addComponent(lblzyret, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnruajkerkesen)
                            .addComponent(btnanulokerkesen)))
                    .addComponent(cbzyret, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuBar1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jMenuBar1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuBar1AncestorAdded

    private void kmblejetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmblejetActionPerformed
        try {
            String s = (String) kmblejet.getSelectedItem();//System.out.println(s);
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekbklejetid(s);
            kmbidlejet = n;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmblejetActionPerformed

    private void kmbaktivitetiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbaktivitetiActionPerformed
        try {
            String s = (String) kmbaktiviteti.getSelectedItem();
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbaktivitetiid(s);
            kmbidaktiviteti = n;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbaktivitetiActionPerformed

    private void kmbkomunatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbkomunatActionPerformed
        try {
            String s = (String) kmbkomunat.getSelectedItem();
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbkomunatid(s);
            kmbidkomunat = n;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbkomunatActionPerformed

    private void kmbpergjigjjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kmbpergjigjjaActionPerformed
        try {
            String s = (String) kmbpergjigjja.getSelectedItem();
            lidhja = new LidhjaMeDb();
            int n = lidhja.merrekmbpergjigjjaid(s);
            kmbidpergjigjja = n;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_kmbpergjigjjaActionPerformed

    private void btnruajkerkesenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnruajkerkesenActionPerformed
        try {
            lidhja = new LidhjaMeDb();
            DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String now = new String(dateformat.format(jdatapranimit.getDate()));
            //java.sql.Date datapranimit = new java.sql.Date(dateformat.parse(now).getTime());
            //System.out.println("data e pranimit "+datapranimit);
            //java.sql.Timestamp dp=new java.sql.Timestamp(jdatapranimit.getDate().getTime());
            //System.out.println("data e pranimit "+dp);

            DateFormat dateformat1 = new SimpleDateFormat("dd/MM/yyyy");
            String now1 = dateformat1.format(jdataleshimit.getDate());
            //java.sql.Date dataleshimit = new java.sql.Date(dateformat1.parse(now1).getTime());
            //System.out.println("data e leshimit "+dataleshimit);
            if (porosia != null) {
                lblporosia_kerkesa.setText("");
            }
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
//                Date convertedDate = dateFormat.parse("2012/12/12"); 
//                Calendar cal = new GregorianCalendar();
            if (insert) {
                if (jdatapranimit.getDate().before(jdataleshimit.getDate())) {
                    lidhja.ruajekerkesen(kmbidlejet, txtkompania.getText(),
                            txtadresa.getText(), txttel.getText(), new java.sql.Timestamp(jdatapranimit.getDate().getTime()),
                            kmbidaktiviteti, kmbidkomunat, txtnrprotokollit.getText(),
                            txttaksa.getText(), txtstatusi_lendes.getText(),
                            txtarkivimi.getText(), new java.sql.Timestamp(jdataleshimit.getDate().getTime()), kmbidpergjigjja);
                } else {
                    JOptionPane.showMessageDialog(this, "Data e leshimit eshte para dates se pranimit!");
                }
                int idk = lidhja.merrekerkesenid();
                merriZyret(idk);
            } else {
                if (jdatapranimit.getDate().before(jdataleshimit.getDate())) {
                    int n = lidhja.updatekerkesen(id, kmbidlejet, txtkompania.getText(),
                            txtadresa.getText(), txttel.getText(), new java.sql.Timestamp(jdatapranimit.getDate().getTime()),
                            kmbidaktiviteti, kmbidkomunat, txtnrprotokollit.getText(),
                            txttaksa.getText(), txtstatusi_lendes.getText(),
                            txtarkivimi.getText(), new java.sql.Timestamp(jdataleshimit.getDate().getTime()), kmbidpergjigjja);
                } else {
                    JOptionPane.showMessageDialog(this, "Data e leshimit eshte para dates se pranimit!");
                }
                updateZyret();
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblporosia_kerkesa.setText("Ju duhet ti zgjedheni te gjitha te dhenat");
            porosia = "kemi gabim";
        }


    }//GEN-LAST:event_btnruajkerkesenActionPerformed

    private void btnanulokerkesenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanulokerkesenActionPerformed
        //System.exit(0);
        this.dispose();
    }//GEN-LAST:event_btnanulokerkesenActionPerformed

    private void btnshtodocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnshtodocActionPerformed
        String filename = File.separator + "tmp";
        JFileChooser fc = new JFileChooser(new File(filename));
        try {
            fc.showOpenDialog(this);
            File selFile = fc.getSelectedFile();
            System.out.println(selFile.getName());
            model.insertRow(tblfajllat.getRowCount() - 1, new Object[]{selFile.getName()});
            String s = merrifajllin();
            s = s + id + "\\" + selFile.getName();
            System.out.println(s);
            File f = new File(s);
            if (f.exists() == false) {
                f.mkdirs();
                //f.mkdirs();
                System.out.println("Directory Created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(s);
    }//GEN-LAST:event_btnshtodocActionPerformed

    private void mraportetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mraportetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mraportetActionPerformed

    private void mraportetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mraportetMouseClicked
        Raportet r = new Raportet(roli);
        r.setVisible(true);
    }//GEN-LAST:event_mraportetMouseClicked

    private void btnfshijeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfshijeActionPerformed

        try {
            //int reply = JOptionPane.showConfirmDialog(this, "a deshironi ta fshini kerkesen?", "fshije", JOptionPane.YES_NO_OPTION);
            if (tblfajllat.getRowCount() != 0 && tblfajllat.getSelectedRow() != -1) {
                Object[] options = {"Po",
                    "Jo"};
                int zgjedhja = JOptionPane.showOptionDialog(this, "A deshironi ta fshini fajllin e zgjedhur?", "Konfirmo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, options, options[1]);
                if (zgjedhja == JOptionPane.YES_OPTION) {

                    String s = merrifajllin();
                    s = s + id + "\\" + (String) (tblfajllat.getValueAt(tblfajllat.getSelectedRow(), 0));
                    System.out.println(s);
                    File f = new File(s);
                    boolean fshije = f.delete();
                    if (fshije) {
                        //tblfajllat.revalidate();
                        model.removeRow(tblfajllat.getSelectedRow());
                        System.out.println("eshte fshire fajlli!");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Ju lutem zgjidheni nje fajll!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnfshijeActionPerformed

    //validon daten e pranimit
    public String validoDatenPranimit() {
        String data = "";
        //data=data+txtditap.getText()+"/"+txtmuajip.getText()+"/"+txtvitip.getText();
        return data;
    }

    //validon daten e leshimit
    public String validoDatenLeshimit() {
        String data = "";
        // data=data+txtdital.getText()+"/"+txtmuajil.getText()+"/"+txtvitil.getText();
        return data;
    }

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
            java.util.logging.Logger.getLogger(RegjistroKerkese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegjistroKerkese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegjistroKerkese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegjistroKerkese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new RegjistroKerkese(true, 0, "").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanulokerkesen;
    private javax.swing.JButton btnfshije;
    private javax.swing.JButton btnruajkerkesen;
    private javax.swing.JButton btnshtodoc;
    private creator.components.CheckBoxList cbzyret;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser jdataleshimit;
    private com.toedter.calendar.JDateChooser jdatapranimit;
    private javax.swing.JComboBox kmbaktiviteti;
    private javax.swing.JComboBox kmbkomunat;
    private javax.swing.JComboBox kmblejet;
    private javax.swing.JComboBox kmbpergjigjja;
    private javax.swing.JLabel lbladresa;
    private javax.swing.JLabel lblaktiviteti;
    private javax.swing.JLabel lblarkivimi;
    private javax.swing.JLabel lbldata_leshimit;
    private javax.swing.JLabel lbldata_pranimit;
    private javax.swing.JLabel lblkerkesa;
    private javax.swing.JLabel lblkompania;
    private javax.swing.JLabel lblkomuna;
    private javax.swing.JLabel lbllloji_lejes;
    private javax.swing.JLabel lblnrprotokollit;
    private javax.swing.JLabel lblpergjigjja;
    private javax.swing.JLabel lblporosia_kerkesa;
    private javax.swing.JLabel lblstatusi_lendes;
    private javax.swing.JLabel lbltaksa;
    private javax.swing.JLabel lbltel;
    private javax.swing.JLabel lblzyret;
    private javax.swing.JMenu madministrimi;
    private javax.swing.JMenu mkerkesat;
    private javax.swing.JMenu mraportet;
    private javax.swing.JTable tblfajllat;
    private javax.swing.JTextField txtadresa;
    private javax.swing.JTextField txtarkivimi;
    private javax.swing.JTextField txtkompania;
    private javax.swing.JTextField txtnrprotokollit;
    private javax.swing.JTextField txtstatusi_lendes;
    private javax.swing.JTextField txttaksa;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
