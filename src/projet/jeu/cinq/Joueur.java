/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.jeu.cinq;

import java.time.LocalDate;

/**
 *
 * @author laumu
 */
public class Joueur implements Comparable<Joueur> {

    //Attributs
    private int score=0;
    private String pseudo;
    private LocalDate date_jour;
    private String niveau;
    //Constructeurs
    public Joueur(String pseudo, int score, LocalDate date_jour, String niveau) {
        this.score=score;
        this.pseudo = pseudo;
        this.date_jour=date_jour;
        this.niveau=niveau;
    }

    // Getters
    public int getScore() {
        return score;
    }

    public String getPseudo() {
        return pseudo+"  ";
    }
    public LocalDate getDate(){
        return this.date_jour;
    }
    
    public String getNiveau(){
        return this.niveau;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void setNiveau(String niveau){
        this.niveau=niveau;
    }

    // Méthodes
    public void CalculScore(int points) { // calcul le score du joueur après un déplacement
        switch (points) {
            case 5:{
                this.score +=30;
                break;}
            case 6:{
                this.score +=50;
                break;}
            case 7:{
                this.score +=70;
                break;}
            case 8:{
                this.score += 90;
                break;}
            case 9:{
                 this.score +=110;
                 break;}
        }

    }
    
    public String toString(){
        return("Votre score est de: "+this.score +" "+ this.pseudo);
    }
    
     public String versFichiers() {
        return this.pseudo+"_"+ this.score+"_"+this.date_jour+"_"+this.niveau;
    }

    public boolean placerApres(Joueur joueur) {        
        return (joueur.getScore() < this.score); // return true si le score du joueur appelant...
        //...est superieur au score du joueur en argument

    }

    @Override
    public int compareTo(Joueur j2) {
        
        return (int) (j2.getScore() - this.getScore());
    }
    
}