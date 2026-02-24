/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.jeu.cinq;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author laumu
 */
public class Billes {
    // Attributs

    private int position_x;
    private int position_y;
    private String couleur;
    private Image img;
    private static final String DOSS_IMAGES="src/Images/";
    private String nomFichier;

    // Constructeurs
    public Billes(int position_x, int position_y, String couleur) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.couleur = couleur;
        this.nomFichier="bille "+this.couleur +".png";
    }

    // Getters
    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getCouleur_2() {
        return null;

    }

    // Setters
    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    
    public void setFichier(String fichier){
        this.nomFichier=fichier;
        
    }
    

    // Méthodes
   
    public String toString(){
        return "position_x: "+position_x+" position_y: "+position_y+" couleur: "+couleur;
    }
    public boolean verifieCouleur(String couleur_ref){
        return false;
    }
    public boolean Vide(){
        return false;
    }
    public boolean verifMagique(){
         return true;
     }
    
    public void affichage(JButton bouton){
        Toolkit t=Toolkit.getDefaultToolkit();
        // accès à l'image
        img=t.getImage(DOSS_IMAGES+nomFichier);
        if(img!=null){
            bouton.setIcon(new ImageIcon(img));
        }
        
    }
}
