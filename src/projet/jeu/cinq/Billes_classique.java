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
public class Billes_classique extends Billes{
    
    public Billes_classique(int position_x, int position_y, String couleur) {
        super(position_x, position_y, couleur);
    }
    public boolean verifieCouleur(String couleur_ref){
        return  this.getCouleur().compareTo(couleur_ref)==0;
        }
    public boolean Vide(){
        return false;
    }
    public boolean verifMagique(){
        return true;
    }
}
