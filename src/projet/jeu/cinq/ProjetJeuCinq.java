/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.jeu.cinq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;

/**
 *
 * @author laumu
 */
public class ProjetJeuCinq {

    /**
     * }
     *
     * @param args the command line arguments
     */
    /*public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Joueur Tibo = new Joueur("Laure", 0);
        Billes[][] grille = new Billes[9][9];
        Jeu jeu1 = new Jeu(Tibo);
        jeu1.Generation_grille();
        //jeu1.videGraphique();
        jeu1.depuisFichierScores();
        jeu1.ajoutJoueur(Tibo);
        while (jeu1.fin_jeu() != 0) {
            jeu1.affichageGrille();
            System.out.println("\nCoord_x: ");
            int x = sc.nextInt() - 1;
            System.out.println("Coord_y: ");
            int y = sc.nextInt() - 1;
            Billes b1 = new Billes(x, y, "");
            //jeu1.Verif_Deplacement(b1);
            jeu1.popUp();
            //jeu1.Verif_Alignement();
            System.out.println(Tibo);
            jeu1.versFichierScores(); // à mettre normalement en dehors du while, mais pour simplifier les tests...
            //...nous l'avons laissé dans le while pour ne pas être obligés de finir la partie
        }
    }*/
}
