/*
MUTHELET Laure
MAITRE Thibault
D 
 */
package projet.jeu.cinq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;

/**
 *
 * @author laumu
 */
public class Jeu  {

    // Attributs
    private final String nom_fichier = "Fichier Scores";
    private final int taille = 9;
    private final int nb_color = 7;
    private Joueur joueur;
    private LocalDate date;
    private ArrayList<Joueur> liste_joueurs = new ArrayList<>();
    private Billes[][] grille = new Billes[taille][taille];
    private JButton[][] tab_graph = new JButton[taille][taille];
    //couleur pour l'affichage de la grille
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Constructeurs
    public Jeu(Joueur joueur) {
        this.joueur = joueur;
        
    }

    // Méthodes
    
    public String Couleur() { // permet de générer aléatoirement une couleur
        Random random = new Random();
        int y = random.nextInt(nb_color);
        switch (y) {
            case 0:
                return "verte";
            case 1:
                return "jaune";
            case 2:
                return "bleue";
            case 3:
                return "rose";
            case 4:
                return "rouge";
            case 5:
                return "noire";
            case 6:
                return "violette";
        }
        return "";
    }

    public Billes[][] getGrille() {
        return grille;
    }
    public Joueur getJoueur(){
        return joueur;
    }

    public Billes Generation_bille(int x, int c) { // permet de créer une bille de manière aléatoire
        Random random = new Random();
        Billes b = new Billes(0, 0, "");
        int y = random.nextInt(30);
        String couleur;
        String couleur2;
        if (y <= 15) {
            couleur = Couleur();
            b = new Billes_classique(x, c, couleur);
        } else if (y == 16) {
            couleur = Couleur();
            couleur2 = Couleur();
            while (couleur.compareTo(couleur2) == 0) { // il ne faut pas qu'une bicolor ait possède deux fois la mm couleur
                couleur2 = Couleur();
            }
            b = new Bicolor(x, c, couleur, couleur2);
        } else if (y == 17) {
            b = new ArcEnCiel(x, c, "arc-en-ciel");
        } else if (y > 18) {
            b = new Vide(x, c, "vide");
        } else if (y == 18) {
            b = new Fusee(x, c, "fusee");
        }

        return b;
    }

    public void Generation_grille() { // permet de générer une grille aléatoirement
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j] == null) {
                    this.grille[i][j] = Generation_bille(i, j);
                }
            }
        }
    }

    public void affichageGraphique(JButton[][] tabgraph) {

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j].affichage(tabgraph[i][j]);
            }
        }

    }

    public void affichageGrille() {
        // La fonction permet d'afficher la grille de l'objet appelant

        int ligne = 1;
        int[] colonne = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int i = 0; i < colonne.length; i++) {
            if (i == 0) {
                System.out.print("   " + colonne[0] + "  ");
            }
            if (i < 8 && i > 0) {
                System.out.print(colonne[i] + "  ");
            }
            if (i == 8) {
                System.out.println(colonne[8]);
            }
        }
        for (int j = 0; j < taille; j++) {
            System.out.print(ligne + "  ");
            ligne += 1;
            for (int k = 0; k < taille; k++) {
                if (this.grille[j][k] instanceof Vide) {
                    System.out.print("   ");
                }
                if (this.grille[j][k] instanceof Fusee) {
                    System.out.print("F  ");
                }
                if (this.grille[j][k] instanceof ArcEnCiel) {
                    System.out.print("A  ");
                }
                if (this.grille[j][k] instanceof Bicolor) {
                    System.out.print("I  ");
                }
                if (this.grille[j][k] instanceof Billes_classique) {

                    if (this.grille[j][k].getCouleur().compareTo("verte") == 0) {
                        System.out.print(ANSI_GREEN + "V  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("jaune") == 0) {
                        System.out.print(ANSI_YELLOW + "J  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("bleue") == 0) {
                        System.out.print(ANSI_BLUE + "B  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("rose") == 0) {
                        System.out.print(ANSI_PURPLE + "R  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("rouge") == 0) {
                        System.out.print(ANSI_RED + "B  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("noire") == 0) {
                        System.out.print(ANSI_BLACK + "N  " + ANSI_RESET);
                    }
                    if (this.grille[j][k].getCouleur().compareTo("violette") == 0) {
                        System.out.print(ANSI_WHITE + "B  " + ANSI_RESET);
                    }
                }
            }
            System.out.println(); // retour à la ligne après avoir affiché la j-ième ligne //
        }

    }

    public void ajoutJoueur(Joueur joueur) {
        liste_joueurs.add(joueur);
    }

    public int[] Souhait_Deplacement() { // permet de prendre les coordonnées de la bille que le joueur souhaite déplacer

        int[] position = new int[2];
        boolean b = true;
        while (b) {
            Scanner sc = new Scanner(System.in);
            try {
                b = false;
                System.out.println("Sur quelle case souhaitez-vous déplacer votre bille?");
                System.out.println("Sur quelle ligne?");
                position[0] = sc.nextInt() - 1;
                while (position[0] < 0 || position[0] > grille.length + 1) {
                    System.out.println("Position impossible! \nVeuillez resaisir vos données:");
                    System.out.println("Sur quelle case souhaitez-vous déplacer votre bille?");
                    System.out.println("Sur quelle ligne?");
                    position[0] = sc.nextInt() - 1;
                }
                System.out.println("Sur quelle colonne?");
                position[1] = sc.nextInt() - 1;
                while (position[1] < 0 || position[1] > grille.length + 1) {
                    System.out.println("Position impossible! \nVeuillez resaisir vos données:");
                    System.out.println("Sur quelle case souhaitez-vous déplacer votre bille?");
                    System.out.println("Sur quelle colonne?");
                    position[1] = sc.nextInt() - 1;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Position impossible! \nVeuillez resaisir vos données:");
                b = true;
            }
        }
        return position;
    }

    public void Tester(ArrayList<Integer> listcoord, int x, int y, int incr_x, int incr_y, int[][] verif_grille, Billes bille, int[] position) {
        Billes echange;
        //Teste si on peut se déplacer, à droite à gauche en bas ou en dessous
        try {
            if ((grille[listcoord.get(x) + incr_x][listcoord.get(y) + incr_y]).Vide() && verif_grille[listcoord.get(x) + incr_x][listcoord.get(y) + incr_y] == 0) {
                listcoord.add(listcoord.get(x) + incr_x);
                listcoord.add(listcoord.get(y) + incr_y);
                verif_grille[listcoord.get(x) + incr_x][listcoord.get(y) + incr_y] = 1;
                if ((listcoord.get(x) + incr_x == position[0]) && (listcoord.get(y) + incr_y == position[1])) { // Si on arrive directement à la position souhaitée 
                    echange = grille[bille.getPosition_x()][bille.getPosition_y()];
                    grille[bille.getPosition_x()][bille.getPosition_y()] = grille[position[0]][position[1]];
                    grille[position[0]][position[1]] = echange;
                    bille.setPosition_x(position[0]); // Déplacement de la bille (maj de la position)
                    bille.setPosition_y(position[1]);
                }
            }
        } catch (IndexOutOfBoundsException ex) {

        }
    }

    public boolean Verif_Deplacement(Billes b, int[] position) { // permet de vérifier si la bille peut être déplacée en appelant la méthode Tester
        int l = b.getPosition_x();
        int c = b.getPosition_y();
        ArrayList<Integer> listcoord = new ArrayList(); //Liste où l'on va stocker les cases nulles qui peuvent potentiellement faire partie de chemin
        int[][] verif_grille = new int[taille][taille]; //Copie de la grille qui permet d'éviter de rebrousser chemin
        // Vérifie si la position souhaitée est bien une bille vide et si la bille à déplacer n'est pas vide
        if (grille[position[0]][position[1]].Vide() == false || grille[l][c].Vide()) {
            return false;
        }
        //On ajoute la position initiale de la bille dans la liste
        listcoord.add(l);
        listcoord.add(c);
        //On ajoute dans la grille la position de la bille afin de ne pas revenir en arrière
        verif_grille[l][c] = 1;

        int x = 0;
        int y = 1;
        //int cpt = 0;

        while (x < listcoord.size()) { // tant qu'il y a des possibilités de déplacement
            //liste de boolean, s'il ya true dans cette liste on s'arrete 
            Tester(listcoord, x, y, 1, 0, verif_grille, b, position); // vérification du déplacement en dessous   
            Tester(listcoord, x, y, 0, 1, verif_grille, b, position); // vérification du déplacement à droite   
            Tester(listcoord, x, y, -1, 0, verif_grille, b, position); // vérification du déplacement au dessus   
            Tester(listcoord, x, y, 0, -1, verif_grille, b, position); // vérification du déplacement à gauche
            x = x + 2;
            y = y + 2;
        }
        return true;
    }

    public void Explosion(String couleur) { // permet d'exploser toutes les billes ayant la même couleur que l'alignement contenant une fusée
        //il faut que toutes les billes de la couleur de l'alignement disparaissent
        if (couleur.compareTo("vide") != 0) {
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    if (grille[i][j].verifieCouleur(couleur) && grille[i][j].getCouleur().compareTo("fusee") != 0 && grille[i][j].getCouleur().compareTo("arc-en-ciel") != 0) {
                        grille[i][j] = new Vide(i, j, "vide");
                    }
                }
            }

        }
    }

    public void popUp(int n) { // faire apparaitre des billes à chaque déplacement en fonction du niveau de difficulté n
        Random random = new Random();
        int cpt_vide = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j] instanceof Vide) {
                    cpt_vide += 1;
                }
            }
        }
        if (cpt_vide > n) {
            for (int i = 0; i < n; i++) {
                int x = random.nextInt(taille);
                int y = random.nextInt(taille);
                while(grille[x][y].Vide() == false) {
                    x = random.nextInt(taille);
                    y = random.nextInt(taille);
                }
                while (grille[x][y] instanceof Vide) { // tant que la bille crée est vide
                    grille[x][y] = Generation_bille(x, y);
                }
            }
        }
        else{
            for (int i = 0; i < cpt_vide; i++) {
                int x = random.nextInt(taille);
                int y = random.nextInt(taille);
                while(grille[x][y].Vide() == false) {
                    x = random.nextInt(taille);
                    y = random.nextInt(taille);
                }
                while (grille[x][y] instanceof Vide) { // tant que la bille crée est vide
                    grille[x][y] = Generation_bille(x, y);
                }
            }
            
        }
    }

    public void Niveau(int n){
        if (n==0){
            this.popUp(1);
        }
        if(n==1){
            this.popUp(2);
        }
        if(n==2){
            this.popUp(4);
        }
    }
    public ArrayList ajoutAlignement(ArrayList<Billes> liste_alignement, ArrayList<ArrayList> grosse_liste_alignement) { // permet d'ajouter une liste d'alignement dans la grosse liste alignement 
        ArrayList<Billes> liste_alignementbis = new ArrayList();
        for (int k = 0; k < liste_alignement.size(); k++) {
            liste_alignementbis.add(liste_alignement.get(k));
        }
        grosse_liste_alignement.add(liste_alignementbis);
        return grosse_liste_alignement;
    }

    public ArrayList colonne() { // renvoie une liste contenant autant de listes qu'il y a d'alignements sur les 9 colonnes
        // colonne
        int y;
        int x;
        ArrayList<ArrayList> grosse_liste_alignement = new ArrayList(); // liste qui contient les alignements 

        for (y = 0; y < taille; y++) {
            ArrayList<Billes> liste_alignement = new ArrayList();// on crèe une nouvelle liste à chaque fin de ligne 
            String couleur = grille[0][y].getCouleur();
            String couleur2 = "";
            for (x = 0; x < taille; x++) {
                if (grille[x][y].verifieCouleur(couleur) || grille[x][y].verifieCouleur(couleur2)) { // vérifie si la case est de la même couleur que la couleur de la bille de référence
                    liste_alignement.add(grille[x][y]); //alors on ajoute cette bille à la liste
                } else { // on y rentre que si les couleurs sont différentes
                    if (liste_alignement.size() < 5) {
                        if (liste_alignement.size() > 0) { // si l'alignement est supérieur à 0
                            int l = liste_alignement.get(0).getPosition_x();
                            int c = liste_alignement.get(0).getPosition_y();
                            liste_alignement = this.ajoutCMagique(l, c, couleur, liste_alignement); // il faut ajouter les billes magiques qui se situent 
                            //avant, s'il y en a 
                        }
                        if (liste_alignement.size() >= 5) { // on teste à nouveau la longueur de la liste au cas où des billes auraient été ajoutées
                            grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
                            return grosse_liste_alignement;
                        }
                        liste_alignement.clear();
                        couleur = grille[x][y].getCouleur();
                        if (grille[x][y] instanceof Bicolor) {
                            couleur2 = grille[x][y].getCouleur_2();
                        }
                        liste_alignement.add(grille[x][y]);
                    }
                    if (liste_alignement.size() >= 5) {// s'arrête si on a un alignement de 5, ne vérifie pas la suite
                        int l = liste_alignement.get(0).getPosition_x();
                        int c = liste_alignement.get(0).getPosition_y();
                        liste_alignement = this.ajoutCMagique(l, c, couleur, liste_alignement);
                        grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
                        return grosse_liste_alignement;
                    }
                }
            }//fin du deuxième for
            if (liste_alignement.size() < 5) { // on teste à nouveau s'il faut rajouter des billes spéciales dans l'alignement
                int l = liste_alignement.get(0).getPosition_x();
                int c = liste_alignement.get(0).getPosition_y();
                liste_alignement = this.ajoutCMagique(l, c, couleur, liste_alignement);
                if (liste_alignement.size() < 5) { // si l'alignement est tjrs < 5...
                    liste_alignement.clear();
                }// ...on retire tous les éléments de la liste car l'alignement n'est pas d'au moins 5 billes de mm couleur
            } else { // si l'alignement est supérieur à 5 on vérifie si on doit rajouter des 
                int l = liste_alignement.get(0).getPosition_x();
                int c = liste_alignement.get(0).getPosition_y();
                liste_alignement = this.ajoutCMagique(l, c, couleur, liste_alignement);
            }
            grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
        }//fin du premier for

        return grosse_liste_alignement;
    }

    public ArrayList ajoutCMagique(int x, int y, String couleur, ArrayList<Billes> liste) {
        if (x == 0) {
            return liste;
        }
        //System.out.println(couleur +"="+grille[x-1][y].getCouleur());
        while (grille[x - 1][y].verifieCouleur(couleur)) {
            //System.out.println("je vais bien la dedans");
            liste.add(grille[x - 1][y]);
            x = x - 1;
            if (x == 0) {
                return liste;
            }
        }
        return liste;
    }

    public ArrayList ajoutLMagique(int x, int y, String couleur, ArrayList<Billes> liste) {
        if (y == 0) {
            return liste;
        }
        while (grille[x][y - 1].verifieCouleur(couleur)) {
            liste.add(grille[x][y - 1]);
            y = y - 1;
            if (y == 0) {
                return liste;
            }
        }
        return liste;
    }

    public ArrayList ligne() { // renvoie une liste contenant autant de listes qu'il y a d'alignements sur les 9 lignes
        // alignement fait sur une ligne
        int y;
        int x;
        // liste qui contient toutes les billes alignées de la mm couleur
        ArrayList<ArrayList> grosse_liste_alignement = new ArrayList(); // liste qui contient les alignements 

        for (x = 0; x < taille; x++) {
            ArrayList<Billes> liste_alignement = new ArrayList();// on crèe une nouvelle liste à chaque fin de ligne 
            String couleur = grille[x][0].getCouleur();
            String couleur2 = "";
            for (y = 0; y < taille; y++) {
                if (grille[x][y].verifieCouleur(couleur) || grille[x][y].verifieCouleur(couleur2)) { // vérifie si la case est de la même couleur que la couleur de la bille de référence
                    liste_alignement.add(grille[x][y]); //alors on ajoute cette bille à la liste

                } else { // on y rentre que si les couleurs sont différentes

                    if (liste_alignement.size() < 5) {
                        if (liste_alignement.size() > 0) { // si l'alignement est supérieur à 0
                            int l = liste_alignement.get(0).getPosition_x();
                            int c = liste_alignement.get(0).getPosition_y();
                            liste_alignement = this.ajoutLMagique(l, c, couleur, liste_alignement);// il faut ajouter les billes magiques qui pourraient se situer avant 
                        }
                        if (liste_alignement.size() >= 5) {// on teste à nouveau la longueur de la liste au cas où des billes auraient été ajoutées
                            grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
                            return grosse_liste_alignement;
                        }
                        liste_alignement.clear();
                        if (grille[x][y] instanceof Bicolor) {
                            couleur2 = grille[x][y].getCouleur_2();
                        }
                        couleur = grille[x][y].getCouleur();
                        liste_alignement.add(grille[x][y]);
                    }
                    if (liste_alignement.size() >= 5) {// s'arrête si on a un alignement de 5, ne vérifie pas la suite
                        int l = liste_alignement.get(0).getPosition_x();
                        int c = liste_alignement.get(0).getPosition_y();
                        liste_alignement = this.ajoutLMagique(l, c, couleur, liste_alignement);
                        grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
                        return grosse_liste_alignement;
                    }
                }
            }//fin du deuxième for
            if (liste_alignement.size() < 5) {
                int l = liste_alignement.get(0).getPosition_x();
                int c = liste_alignement.get(0).getPosition_y();
                liste_alignement = this.ajoutLMagique(l, c, couleur, liste_alignement);
                if (liste_alignement.size() < 5) {
                    liste_alignement.clear();
                } // on retire tous les éléments de la liste car l'alignement n'est pas d'au moins 5 billes de mm couleur
            } else {
                int l = liste_alignement.get(0).getPosition_x();
                int c = liste_alignement.get(0).getPosition_y();
                liste_alignement = this.ajoutLMagique(l, c, couleur, liste_alignement);
            }
            grosse_liste_alignement = ajoutAlignement(liste_alignement, grosse_liste_alignement);
        }//fin du premier for

        return grosse_liste_alignement;
    }

    public boolean Verif_Alignement(Joueur joueur) {
        //ligne
        ArrayList<ArrayList> grosse_liste_alignement_ligne;
        ArrayList<Billes> listes;
        Boolean b = false;
        grosse_liste_alignement_ligne = ligne();
 
        for (int j = 0; j < grosse_liste_alignement_ligne.size(); j++) {
            if (grosse_liste_alignement_ligne.get(j).size() >= 5) {
                joueur.CalculScore(grosse_liste_alignement_ligne.get(j).size());
                for (int i = 0; i < grosse_liste_alignement_ligne.get(j).size(); i++) {
                    listes = grosse_liste_alignement_ligne.get(j);
                    grille[listes.get(i).getPosition_x()][listes.get(i).getPosition_y()] = new Vide(listes.get(i).getPosition_x(), listes.get(i).getPosition_y(), " ");
                    b = true;
                    if (listes.get(i) instanceof Fusee) {
                        for (int z = 0; z < listes.size(); z++) {
                            if (listes.get(z) instanceof Billes_classique) {
                                Explosion(listes.get(z).getCouleur());
                                break;
                            }
                        }

                    }
                }
            }
        }
        //colonne
        ArrayList<ArrayList> grosse_liste_alignement_colonne;
        ArrayList<Billes> listes2;
        grosse_liste_alignement_colonne = colonne();

        for (int k = 0; k < grosse_liste_alignement_colonne.size(); k++) {
            if (grosse_liste_alignement_colonne.get(k).size() >= 5) {
                joueur.CalculScore(grosse_liste_alignement_colonne.get(k).size());
                for (int l = 0; l < grosse_liste_alignement_colonne.get(k).size(); l++) {
                    listes2 = grosse_liste_alignement_colonne.get(k);
                    grille[listes2.get(l).getPosition_x()][listes2.get(l).getPosition_y()] = new Vide(listes2.get(l).getPosition_x(), listes2.get(l).getPosition_y(), " ");
                    b = true;
                    if (listes2.get(l) instanceof Fusee) {
                        for (int z = 0; z < listes2.size(); z++) {
                            if (listes2.get(z) instanceof Billes_classique) {
                                Explosion(listes2.get(z).getCouleur());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return b;
    }

    public void versFichierScores(ArrayList<Joueur> liste_joueurs) throws IOException {  // Permet d'écrire dans un fichier tous les articles présents dans le stock 
        FileWriter fich = new FileWriter(nom_fichier); // si le fichier existe déjà, on écrase le fichier et on le remplace
        trie_scores() ;
	  Collections.sort(liste_joueurs);
        for (int i = 0; i < liste_joueurs.size(); i++) {
fich.write("PSEUDO"+"                  "+"SCORE"+System.lineSeparator());
            fich.write(liste_joueurs.get(i).versFichiers() + System.lineSeparator()); // On écrit dans le fichier
        }
        fich.close();  //On ferme le fichier
    }

    public void depuisFichierScores(ArrayList<Joueur> liste_joueurs) throws FileNotFoundException, IOException { // récupère les scores des joueurs précédents
        FileReader fich = new FileReader(nom_fichier);
        BufferedReader br = new BufferedReader(fich);
        String ligne = br.readLine();
        while (ligne != null){
            String tab[] = ligne.split("_");
            String pseudo = tab[0];
            int scores = Integer.valueOf(tab[1]);
            LocalDate date=LocalDate.parse(tab[2]);
            Joueur joueur1 = new Joueur(pseudo, scores,date);
            liste_joueurs.add(joueur1);
            ligne = br.readLine();
        }
        fich.close();
    }    
      
    public int fin_jeu() {
        int cpt = 0;
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                if (this.grille[i][j] instanceof Vide) {
                    cpt += 1;
                }
            }
        }
        return cpt;
    }  
}



























/*
MUTHELET Laure
MAITRE Thibault
D
 
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
    //Constructeurs
    public Joueur(String pseudo, int score, LocalDate date_jour) {
        this.score=score;
        this.pseudo = pseudo;
        this.date_jour=date_jour; 
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

    public void setScore(int score) {
        this.score = score;
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
        return this.pseudo +"          -          "+ this.score+"          -          "+this.date_jour;
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
