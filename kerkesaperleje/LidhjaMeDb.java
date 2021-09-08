/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kerkesaperleje;

/**
 *
 * @author Iliriani
 */
import java.sql.*;
import sun.security.util.PropertyExpander;

public class LidhjaMeDb {
    private static Connection conn;
    private static Statement stmt;

//    public static Connection getConnection() throws Exception {
//        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//        String url = "jdbc:odbc:lidhja_sql";
//        String username = "";
//        String password = "";
//        Class.forName(driver);
//        return DriverManager.getConnection(url, username, password);
//    }

    public static Statement hapelidhjen() throws Exception {

                
        String url = "jdbc:sqlserver://localhost:1433;integratedSecurity=true";
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
Connection conn = DriverManager.getConnection(url);

        
        
//        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
//        String url = "jdbc:odbc:lidhja_sql";
//        String username = "";
//        String password = "";
//        Class.forName(driver);
//        Connection connct = DriverManager.getConnection(url);
//        conn=connct;
        stmt=conn.createStatement();
        return stmt;
    }

    
    public static Connection merrelidhjen() throws Exception{
        return hapelidhjen().getConnection();
    }
    
    
    public static void mbyllelidhjen() throws Exception {
        conn.close();
        stmt.close();
    }


    public ResultSet valido(String user, String pass) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call valido(?,?)}");
        stmt.setString(1, user);
        stmt.setString(2, pass);
        ResultSet rs = stmt.executeQuery();
        /**
         * ResultSetMetaData rsmd = rs.getMetaData(); int
         * NumOfCol=rsmd.getColumnCount(); System.out.println("numri i colonave
         * eshte "+NumOfCol); //String sql = "select * from perdoruesit";
         * //ResultSet rs = stmt.executeQuery(sql); int i=0; while(rs.next()){
         * i++; //System.out.println(rs.getString("emri"));
         * System.out.print(rs.getInt("idperdoruesi")); System.out.print("\t");
         * System.out.print(rs.getInt("idroli"));
         * //System.out.print(rs.getString("emri")); //System.out.print("\t");
         * //System.out.print(rs.getString("mbiemri")); System.out.println(); }
         * System.out.println("numri i rreshtave eshte "+i); //rs.last(); // int
         * rowCount = rs.getRow(); // System.out.println("Number of Rows=" +
         * rowCount); stmt.close(); conn.close();
         */
        return rs;
    }

    public ResultSet llojet_lejeve() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call llojet_lejeve}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;

    }

    public ResultSet zyret() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrizyret}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;

    }

    public ResultSet aktivitetet() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merriaktivitetet}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;

    }

    public ResultSet komunat() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrikomunat}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;

    }

    public ResultSet pergjigjjet() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merripergjigjjet}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public ResultSet kolonat_kerkeses() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call kolonat_kerkeses}");
//stmt.setString(1,user);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public int merrekbklejetid(String kmbzgj) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrekbklejetid(?) }");
        int n = 0;
        stmt.setString(1, kmbzgj);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;
    }

    public int merrekmbkomunatid(String kmbzgj) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrekmbkomunatid(?) }");
        int n = 0;
        stmt.setString(1, kmbzgj);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;
    }

    public int merrekmbaktivitetiid(String kmbzgj) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrekmbaktivitetiid(?) }");
        int n = 0;
        stmt.setString(1, kmbzgj);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;
    }

    public int merrekmbpergjigjjaid(String kmbzgj) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrekmbpergjigjjaid(?) }");
        int n = 0;
        stmt.setString(1, kmbzgj);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;
    }

    public int merrecbzyretid(String cbzgj) throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merridcb(?) }");
        int n = 0;
        stmt.setString(1, cbzgj);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;
    }

    public int ruajekerkesen(int idll, String com, String adr, String tel, java.sql.Timestamp dp, int ida, int idk, String nrp, String taksa, String sl, String af, java.sql.Timestamp dl, int idp) throws Exception {

        System.out.println(com + "~");
        Connection conn = merrelidhjen();
        String s = "{call ruajekerkesen(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, idll);
        stmt.setString(2, com);
        stmt.setString(3, adr);
        stmt.setString(4, tel);
        //stmt.setTimestamp(5,dp);

        //java.sql.Timestamp datal=new java.sql.Timestamp(dp.getYear() - 1900, dp.getMonth() - 1, dp.getDay(), 0, 0, 0, 0);

        stmt.setTimestamp(5, dp);

        System.out.println("data ne metode " + dp);
        //stmt.setDate(5, new java.sql.Timestamp(2012, 03, 15, 0, 0, 0, 0));
        // Timestamp(int year, int month, int date, int hour, int minute, int second, int nano) 

        stmt.setInt(6, ida);
        stmt.setInt(7, idk);
        stmt.setString(8, nrp);
        stmt.setString(9, taksa);
        stmt.setString(10, sl);
        stmt.setString(11, af);
        stmt.setTimestamp(12, dl);
        stmt.setInt(13, idp);

        int n = stmt.executeUpdate();
        //System.out.println(n);
        return n;
        //conn.close();stmt.close();




        /*
         * try { //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); Connection
         * conn = getConnection(); Statement st = conn.createStatement();
         * st.executeUpdate("insert into
         * kerkesat(idlloji,kompania,adresa,tel,data_pranimit,idaktiviteti,idkomuna,nr_protokollit,taksa,statusi_lendes,arkivimi_follder,data_leshimit,idpergjigjja)"+
         * "values(?,?,?,?,?,?,?,?,?,?,?,?,?) "); System.out.println("mire
         * eshte"); conn.close();
         */
//CallableStatement stmt = conn.prepareCall("{call procedura1(?)}");
//stmt.setInt(1, 1);
//ResultSet rs=stmt.executeQuery();
//String sql = "insert into kerkesat(idlloji,kompania,adresa,tel,data_pranimit,idaktiviteti,idkomuna,nr_protokollit,taksa,statusi_lendes,arkivimi_follder,data_leshimit,idpergjigjja)  values(2,'mire','','fdf','131',1,1,'erfge','ewe','erer','rege','23565',2) ";

//ResultSet rs = stmt.executeQuery(sql);}

//catch(Exception e){System.out.println("gabim ne medoden e insertimit");e.printStackTrace();}        




    }

    public int shtokerkesenezyre(int idk, int idz) throws Exception {

        Connection conn = merrelidhjen();
        String s = "{call shtokerkesnezyre(?,?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, idk);
        stmt.setInt(2, idz);
        int n = stmt.executeUpdate();
        return n;

    }

    public int merrekerkesenid() throws Exception {
        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrkerkesenid }");
        int n = 0;
        //stmt.setString(1,cbzgj);
        //stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            n = rs.getInt(1);
        }
        return n;

    }

    public ResultSet vlerat_kerkeses(int id) throws Exception {
        System.out.println("hyrja ne vk");
        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call vlerat_kerkesave(?)}");
        stmt.setInt(1, id);
//stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        System.out.println("dalja nga vk");
        return rs;

    }

    public int updatekerkesen(int id, int idll, String com, String adr, String tel, java.sql.Timestamp dp, int ida, int idk, String nrp, String taksa, String sl, String af, java.sql.Timestamp dl, int idp) throws Exception {
        System.out.println("hyrja ne updatekerkesen");
        Connection conn = merrelidhjen();
        String s = "{call updatekerkesen(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, id);
        stmt.setInt(2, idll);
        stmt.setString(3, com);
        stmt.setString(4, adr);
        stmt.setString(5, tel);
        stmt.setTimestamp(6, dp);
        stmt.setInt(7, ida);
        stmt.setInt(8, idk);
        stmt.setString(9, nrp);
        stmt.setString(10, taksa);
        stmt.setString(11, sl);
        stmt.setString(12, af);
        stmt.setTimestamp(13, dl);
        stmt.setInt(14, idp);

        int n = stmt.executeUpdate();
        System.out.println(n+" ne idne "+id);
        System.out.println("dalja ne updatekerkesen");
        System.out.println(id);

        return n;
    }

    public int fshijekerkesen(int id) throws Exception {
        System.out.println("hyrja ne fshijekerkesen");
        Connection conn = merrelidhjen();
        String s = "{call fshije_kerkesen(?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, id);

        int n = stmt.executeUpdate();
        System.out.println(n);
        System.out.println("dalja ne fshjekerkesen");
        System.out.println(id);

        return n;
    }

    public ResultSet merrirolet() throws Exception {

        Connection conn = merrelidhjen();
        CallableStatement stmt = conn.prepareCall("{call merrirolet}");
        //stmt.setString(1,user);
        //stmt.setString(2,pass);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public int fshijepunonjesin(int id) throws Exception {
        System.out.println("hyrja ne fshijepunonjesin");
        Connection conn = merrelidhjen();
        String s = "{call fshije_punonjesin(?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, id);

        int n = stmt.executeUpdate();
        System.out.println(n);
        System.out.println("dalja ne fshjekerkesen");
        System.out.println(id);

        return n;
    }

    public int ruajepunonjesin(int idrol, String per, String pass, String emri, String mbiemri) throws Exception {

        //System.out.println(com + "~");
        Connection conn = merrelidhjen();
        String s = "{call ruaje_punonjesin(?,?,?,?,?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, idrol);
        stmt.setString(2, per);
        stmt.setString(3, pass);
        stmt.setString(4, emri);
        stmt.setString(5, mbiemri);

        int n = stmt.executeUpdate();
        //System.out.println(n);
        return n;
        //conn.close();stmt.close();

    }

    public int ktheidroli(String roli) throws Exception {

        int vlera = 0;
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection conn = DriverManager.getConnection("jdbc:odbc:lidhja_sql");
        Statement stmt = conn.createStatement();
        //CallableStatement stmt = conn.prepareCall("{call procedura1(?)}");
        //stmt.setInt(1, 1);
        //ResultSet rs=stmt.executeQuery();
        String sql = "select idroli from rolet where roli='" + roli + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int n = rs.getInt("idroli");
            vlera = n;
        }
        return vlera;

    }

    public ResultSet vlerat_perdoruesit(int id) throws Exception {


        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection conn = DriverManager.getConnection("jdbc:odbc:lidhja_sql");
        Statement stmt = conn.createStatement();
        //CallableStatement stmt = conn.prepareCall("{call procedura1(?)}");
        //stmt.setInt(1, 1);
        //ResultSet rs=stmt.executeQuery();
        String sql = "select * from perdoruesit where idperdoruesi=" + id;
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public int updatepunonjesi(int idper, int idroli, String per, String pass, String emri, String mbiemri) throws Exception {
        System.out.println("hyrja ne updatekerkesen");
        Connection conn = merrelidhjen();
        String s = "{call updatepunonjesi(?,?,?,?,?,?)}";
        CallableStatement stmt = conn.prepareCall(s);
        stmt.setInt(1, idper);
        stmt.setInt(2, idroli);
        stmt.setString(3, per);
        stmt.setString(4, pass);
        stmt.setString(5, emri);
        stmt.setString(6, mbiemri);

        int n = stmt.executeUpdate();
        return n;
    }

    public static void main(String[] args) throws Exception {

        try {
            java.util.Date utilDate = new java.util.Date("28/09/2012");
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
            System.out.println("data e fillimit: " + sqlDate);

            java.util.Date utilDate1 = new java.util.Date("28/12/2012");
            java.sql.Timestamp sqlDate1 = new java.sql.Timestamp(utilDate1.getTime());
            System.out.println("data e mbarimit: " + sqlDate1);
            LidhjaMeDb lidhja = new LidhjaMeDb();//lidhja.ruajekerkesen(1, null, null, null, null, 1, 2, null, null, null, null, null, 2);
            //int n=lidhja.merrekbklejetid("biznesi");System.out.println(n);
            ResultSet rs = lidhja.merrirolet();
            int n = lidhja.ktheidroli("administrator");
            System.out.println(n);
            ///lidhja.ruajekerkesen(1,"iliriani","mitro", "kosove", "rewe", 1, 2, "rgefge", "rger", "rgee","rgere", "egere", 1);
            //int num= lidhja.ruajekerkesen(1,"testdate4", "", "", new java.sql.Timestamp(utilDate.getTime()), 1, 2, "", "", "", "",new java.sql.Timestamp(utilDate1.getTime()), 2);
            //int n=lidhja.updatekerkesen(129, 1,"iliriani","mitro", "kosove", "rewe", 1, 2, "rgefge", "rger", "rgee","rgere", "egere", 1);
            //System.out.println(n);


            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();



            while (rs.next()) {
                System.out.println(rs.getString("roli"));
            }

        } catch (Exception e) {
            System.out.println("nuk po hyne ne try ");
            e.printStackTrace();
        }

    }
}