/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.jeu.cinq;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author laumu
 */
public class Bicolor extends Billes {
    // Attributs
    
    private String couleur_2;
    
    // Constructeurs

    public Bicolor(int position_x, int position_y, String couleur, String couleur_2) {
        super(position_x, position_y, couleur);
        this.couleur_2 = couleur_2;
        setFichier(couleur+"_"+this.couleur_2+".png");
        
    }

    public void setCouleur_2(String couleur_2) {
        this.couleur_2 = couleur_2;
        
    }
    // Getter
    public String getCouleur_2() {
        return couleur_2;
    }
    
    public String toString(){
        return super.toString() + " couleur2: "+ this.couleur_2;
    }
    
    public boolean verifieCouleur(String couleur_ref){
        return couleur_2.compareTo(couleur_ref)==0 || this.getCouleur().compareTo(couleur_ref)==0;
    }
    
    public boolean Vide(){
        return false;
    }
    
    public boolean verifMagique(){
         return true;
     }
}
