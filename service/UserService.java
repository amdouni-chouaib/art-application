/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entite.Role;
import entite.User;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;


public class UserService implements InterService <User>{
       
private Connection conn;
private Statement ste;                          
private PreparedStatement pst;
private ResultSet rs;
  

    public UserService() { 
        conn = DataSource.getInstance().getConnection();
    }
    
 
  public static boolean isValidEmail(String email) {
       String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
       return email.matches(regex);
    }
  
  
    
//***********************************insert*******************************************//ok
    @Override
    public void insert(User t) {
         String requete = "insert into users (nom_user,prenom_user,date_naiss,email,pwd_user,sexe,tel_user,Img,adresse,Id_role)values (?,?,?, ?,? ,?, ?, ?,? ,?)";
        try {
   
         PreparedStatement pst = conn.prepareStatement(requete);
         
          
        pst.setString(1, t.getNom_user());
        pst.setString(2, t.getPrenom_user()); 
        pst.setDate(3,  Date.valueOf(t.getDate_naiss()));
        pst.setString(4, t.getEmail());
        pst.setString(5, t.getPwd_user());
        pst.setString(6, t.getSexe());
        pst.setInt(7, t.getTel_user());
        pst.setString(8,"az");//pst.setString(8, t.getImg())
        pst.setString(9, t.getAdresse());
        pst.setInt(10, 1);//t.getRole().getId_role()
        
        pst.executeUpdate();
          System.out.println("user ajoute");
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("user non ajoute");
    }           
        }
    

//***********************************update*******************************************//ok
    @Override
    public void update(User t) {
        try {
        pst = conn.prepareStatement("update users set nom_user= ?,prenom_user= ?,email= ?,pwd_user= ?,sexe= ?,tel_user= ?,Img= ?,adresse = ? where id_user = ?;");
          
        pst.setString(1, t.getNom_user());
        pst.setString(2, t.getPrenom_user());
      
        pst.setString(3, t.getEmail());
        pst.setString(4, t.getPwd_user());
        pst.setString(5, t.getSexe());
        pst.setInt(6, t.getTel_user());
        pst.setString(7, t.getImg());
        pst.setString(8, t.getAdresse());
         
         pst.setInt(9, t.getId_user());
        pst.executeUpdate(); 
        System.out.println("update user");
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }    
    }
 //**********************************readAll********************************************//OK
    
   @Override
   public List<User> readAll() {
       
      List<User> list = new ArrayList<>();
   String requete="select * from users";
     try {
      Statement ste = conn.createStatement();                  
    ResultSet rs=ste.executeQuery(requete);                         
    while(rs.next()){
       // RoleService sr=new RoleService();
       // Role r =new Role();
         //  r=sr.readById(rs.getInt("id_role"));
             User u = new User (
            rs.getInt("id_user"),
            rs.getString("nom_user"),
            rs.getString("prenom_user"),
           rs.getString("email"),
            rs.getString("sexe"),
rs.getInt("tel_user"),
                     rs.getString("Img")
            
             
      
          
         );
       list.add(u);
       
//    User u = new User (
//            rs.getInt("id_user"),
//            rs.getString("nom_user"),
//            rs.getString("prenom_user"),
//            rs.getDate("date_naiss").toLocalDate(),
//           rs.getString("email"),
//            rs.getString("pwd_user"),
//             rs.getString("sexe"),
//            rs.getInt("tel_user"),
//            rs.getString("Img"),
//            rs.getString("adresse"),
//           r);
//       list.add(u);
//       
     }
    
   } catch (SQLException ex) {
       Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
  }
   return list;
   
}
   
//************************************readById****************************************//ok


    @Override
    public User readById(int id_user) {
          User u = new User();
     try {
     pst = conn.prepareStatement("select id_user,nom_user,prenom_user,date_naiss,email,pwd_user,sexe,tel_user,Img,adresse,Id_role from users where id_user=?");
       
       rs=pst.executeQuery();
        while (rs.next()) {
       u.setId_user(rs.getInt("id_user"));  
      u.setNom_user(rs.getString("nom_user"));
	u.setPrenom_user(rs.getString("prenom_user"));
       u.setDate_naiss(rs.getDate("date_naiss").toLocalDate()); 
       u.setEmail(rs.getString("email")); 
      u.setPwd_user(rs.getString("pwd_user")); 
        u.setSexe(rs.getString("sexe"));       
        u.setTel_user(rs.getInt("tel_user"));
      u.setImg(rs.getString("Img"));
       u.setAdresse(rs.getString("adresse"));
       RoleService sr=new RoleService();
        Role r =new  Role();
            r=sr.readById(rs.getInt("id_role"));
      
          u.setRole(r);
         
}
   } 
     catch (SQLException ex) {
       Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return u;
    

   
        
     
         
    }
   
//***********************************delete**************************************//ok
    
    @Override
    public void delete(User t) {
           try {
      pst = conn.prepareStatement("delete from users where id_user= ?");
     pst.setInt(1,t.getId_user());
      pst.executeUpdate();
        System.out.println(" user supprimer");
    } 
     catch (SQLException ex) {
     Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
//********************************************************///
    
  
  
public User login (String email,String pwd_user  ){     
                Role r=new Role();
//ok            U
                User u=new User();
    if(email.isEmpty()||pwd_user.isEmpty())
        System.out.println("Veuillez remplir tous les champs");
    try {
        pst=conn.prepareStatement("select *  from users where email = ? and pwd_user = ? ");
        pst.setString(1, email);
        pst.setString(2, pwd_user);
       
        rs = pst.executeQuery();
        if(rs.next()){
            u.setRole(new Role(rs.getInt(2)));
            RoleService roleservice=new RoleService();
            r=roleservice.readById(rs.getInt(2));
           
            if(r.getDesc_role().equals("Admin"))
            {
                System.out.println("Bienvenue Administrateur "+email);
            
            if(r.getDesc_role().equals("Artiste"))
            {
                System.out.println("Bienvenue Artist "+email);
            
            
            
            if(r.getDesc_role().equals("Client"))
                System.out.println("Bienvenue client "+email);
            }
            }
            else {
                System.out.println("\"Who are u\"");
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return u;
}
}
