/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package myart;
import utils.DataSource;
import service.UserService;
import entite.User;
import service.RoleService;
import entite.Role;
import java.time.LocalDate;
import java.util.Date;
/**
 *
 * @author rania
 */
public class MyArt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      //DataSource ds1 =DataSource.getInstance();
     //System.out.println(ds1);
     
    LocalDate ld=LocalDate.of(1999, 03, 06);
     
     

      
 Role r=new Role(2,"client");
UserService pst =new UserService();
User u2 =new User( "samir", "wachene", ld, "meriamwachene@esprit.tn", "051516", "femme", 0122425, "Img", "adresse",r);
//RoleService pst =new RoleService();

//pst.insert(u2);

//pst.insert(u1);
//pst.readById(2);
pst.readAll().forEach(System.out::println);
//pst.update(u2);
//pst.login("meriamwachene@esprit.tn", "051516");
  // RoleService r=new RoleService();
  //Role role=new Role();
  //role=r.readById(1);
    //  System.out.println(role);
    }

  
}
