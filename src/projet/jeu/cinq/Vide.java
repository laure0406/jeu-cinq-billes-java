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
public class Vide extends Billes {

    public Vide(int position_x, int position_y, String couleur) {
        super(position_x, position_y, "vide");
    }
    
    public boolean verifieCouleur(String couleur_ref){
       return false ;
        }
    public boolean Vide(){
        return true;
    }
}
