/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.jeu.cinq;

/**
 *
 * @author laumu
 */
public class Fusee extends Billes {
    // Attributs
    
    // Constructeurs
 
    public Fusee(int position_x, int position_y, String couleur) {
        super(position_x, position_y, "fusee");
    }
    public boolean verifieCouleur(String couleur_ref){
       return true ;
        }
    
    public boolean Vide(){
        return false;
    }
    public boolean verifMagique(){
         return false;
     }   
}
