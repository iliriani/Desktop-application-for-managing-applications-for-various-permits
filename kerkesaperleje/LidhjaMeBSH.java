


import java.sql.*;

public class LidhjaMeBSH {
  public static void main(String[] args) throws Exception {
    
      LidhjaMeDb lidhja=new LidhjaMeDb();
     lidhja.valido("iliriani","123");

  }
  
  
  public void valido(String user,String pass)throws Exception{
  try{  
      Connection conn=getConnection(); 
      CallableStatement stmt = conn.prepareCall("{call valido(?,?)}");
stmt.setString(1,user);
stmt.setString(2,pass);
ResultSet rs=stmt.executeQuery();
//String sql = "select * from perdoruesit";
//ResultSet rs = stmt.executeQuery(sql);
while(rs.next()){
//System.out.println(rs.getString("emri"));
System.out.print(rs.getInt("idperdoruesi"));
System.out.print("\t");
System.out.print(rs.getInt("idroli"));
//System.out.print(rs.getString("emri"));
//System.out.print("\t");
//System.out.print(rs.getString("mbiemri"));
System.out.println();
}
  stmt.close();
    conn.close();
}
catch (ClassNotFoundException e) {e.printStackTrace();}
catch (SQLException e) {e.printStackTrace();}   
}
  
  

  private static Connection getConnection() throws Exception {
    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
    String url = "jdbc:odbc:lidhja_sql";
    String username = "";
    String password = "";
    Class.forName(driver);
    return DriverManager.getConnection(url, username, password);
  }
}