/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fiches;

import static Fiches.Page_accueil.DOSS_IMAGES;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import projet.jeu.cinq.Billes;
import projet.jeu.cinq.Jeu;
import projet.jeu.cinq.Joueur;

/**
 *
 * @author laumu
 */
public class page_jeu extends javax.swing.JDialog {

    private int taille = 9;
    private JButton[][] tab_graph = new JButton[taille][taille];
    private Jeu monJeu;
    private Joueur joueur;
    private ArrayList<Joueur> liste_joueurs = new ArrayList<>();
    private page_regles page_regles;
    private page_meilleurs_scores meilleurs_scores;
    private int cptclic = 0; // sert à compter le nombre de clics (différencie le premier clic du deuxième clic)
    private int chgt_pos = 0; // permet de savoir si on a déjà selectionné une case vide
    private int chgt_souhait = 0; // permet de savoir si on a déjà selectionné une bille
    private Billes b = new Billes(0, 0, ""); // c'est la bille que le joueur souhaite déplacer
    private int score = 0;// initialise le score du joueur à 0

    /**
     * Creates new form page_jeu
     */
    public page_jeu(java.awt.Frame parent, boolean modal) throws IOException {
        super(parent, modal);
        monJeu = new Jeu(joueur);// on crée le jeu 
        liste_joueurs = ((Page_accueil) this.getParent()).getListeJoueurs();// on récupère la liste joueurs
        monJeu.Generation_grille();//on crée une grille de bille
        initComponents();
        page_regles = ((Page_accueil) this.getParent()).getPage_regles();
        meilleurs_scores = ((Page_accueil) this.getParent()).getMeilleurs_scores();
        initGrille();
    }

    public void initGrille() throws IOException {
        // fait le pont entre la grille partie traitement et la grille affichée dans la grille de jeu
        monJeu.depuisFichierScores(liste_joueurs);
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                tab_graph[i][j] = new JButton();
                tab_graph[i][j].setActionCommand(i + "," + j); // on récupère les coordonnées du bouton de la grille
                tab_graph[i][j].addActionListener(new ActionListener() { //on ajoute un ecouteur à chaque bouton
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (cptclic == 0) {
                                boutActionPerformed(evt);
                                infoClic.setText("Bille à déplacer");
                                cptclic = 1;
                            } else if (cptclic == 1) {
                                boutActionPerformed(evt);
                                cptclic = 0;
                                infoClic.setText("Bille déplacée");
                            }
                        } catch (IOException exe) {

                        }
                    }
                });
                grillePanel.add(tab_graph[i][j]);
                tab_graph[i][j].setBackground(new java.awt.Color(255, 255, 255));// couleur de fond des boutons
                tab_graph[i][j].setPreferredSize(new java.awt.Dimension(50, 50)); // dimension des boutons
            }
        }
        this.pack();
        monJeu.affichageGraphique(tab_graph); // associe les billes aux images de billes créées 

    }

    public void init() {
        //on crée le joueur à l'aide du pseudo rentré dans la page d'accueil
        joueur = ((Page_accueil) getParent()).getJoueur();
        pseudo_joueur.setText(joueur.getPseudo());
    }

    private void boutActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        int x = 0;
        int y = 0;
        int n = ((Page_accueil) this.getParent()).getN();
        Image img;
        Toolkit t = Toolkit.getDefaultToolkit();
        int[] position = new int[2];

        if (cptclic == 0) { // lorsque le joueur selectionne la bille qu'il souhaite déplacer
            chgt_souhait = 1;
            String cmd = evt.getActionCommand();
            int ligne = Integer.valueOf(cmd.substring(0, 1));// On récupère la ligne  
            int colonne = Integer.valueOf(cmd.substring(2, 3)); // on récupère la colonne
            b = monJeu.getGrille()[ligne][colonne]; // on récupère la bille qu'il souhaite déplacer
        }
        if (cptclic == 1) { // lorsque le joueur sélectionne la case où il souhaite déplacer la bille
            chgt_pos = 1;
            String cmd2 = evt.getActionCommand();
            x = Integer.valueOf(cmd2.substring(0, 1));// On récupère la ligne  
            y = Integer.valueOf(cmd2.substring(2, 3)); // on récupère la colonne
            // on remplie le tableau des positions souhaitées
            position[0] = x; 
            position[1] = y;
        }
        if (chgt_pos == 1 && chgt_souhait == 1) { // lorsque l'on effectue le changement
            monJeu.Verif_Deplacement(b, position); // vérifie si le déplacement est possible
            monJeu.Niveau(n); // on définit le niveau correspondant à celui choisi par le joueur
            monJeu.Verif_Alignement(joueur); // vérifie s'il y a un alignement dans la grille
            score_joueur.setText(String.valueOf(joueur.getScore())); // mise à jour du score dans le label
            // on remet les variables conditionnant le déplacement de bille à 0
            chgt_souhait = 0;
            chgt_pos = 0;
        }
        monJeu.affichageGraphique(tab_graph); // on réaffiche la grille mise à jour
        if (monJeu.fin_jeu() == 0) { // on teste si le jeu est fini 
            grillePanel.setEnabled(false); // le joueur ne peut plus modifier la grille
            // création d'une icône et assignation au label
            img = t.getImage(DOSS_IMAGES + "c-est-la-fete-emoji.gif"); 
            img = img.getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_DEFAULT);
            photo.setIcon(new ImageIcon(img));
            // on affiche deux messages de fin de jeu sur la page de jeu
            finJeu.setText("Le jeu est fini!!");
            finjeu2.setText("Vous avez un score de : " + joueur.getScore() + " points!!");
        }
    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grillePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pseudo_joueur = new javax.swing.JLabel();
        jScore = new javax.swing.JLabel();
        score_joueur = new javax.swing.JLabel();
        photo = new javax.swing.JLabel();
        l_grille = new javax.swing.JLabel();
        bQuitter = new javax.swing.JButton();
        panelClic = new javax.swing.JPanel();
        l_infoclic = new javax.swing.JLabel();
        infoClic = new javax.swing.JTextField();
        finJeu = new javax.swing.JLabel();
        finjeu2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        regleJeu = new javax.swing.JMenu();
        menuregleJeu = new javax.swing.JMenuItem();
        meilleursScores = new javax.swing.JMenu();
        menumeilleursScores = new javax.swing.JMenuItem();

        setTitle("Jeu");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        grillePanel.setBackground(new java.awt.Color(0, 0, 0));
        grillePanel.setPreferredSize(new java.awt.Dimension(500, 500));
        grillePanel.setLayout(new java.awt.GridLayout(9, 9));

        jPanel1.setBackground(new java.awt.Color(247, 247, 247));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(196, 170, 10));
        jPanel1.setOpaque(false);

        pseudo_joueur.setBackground(new java.awt.Color(255, 204, 204));
        pseudo_joueur.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        pseudo_joueur.setForeground(new java.awt.Color(233, 173, 15));
        pseudo_joueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jScore.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jScore.setForeground(new java.awt.Color(233, 173, 15));
        jScore.setText("Score:");

        score_joueur.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        score_joueur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        score_joueur.setText("0");

        photo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/crown.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScore, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(score_joueur, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(photo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pseudo_joueur, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pseudo_joueur, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(score_joueur, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScore, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        l_grille.setBackground(new java.awt.Color(255, 204, 204));
        l_grille.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 30)); // NOI18N
        l_grille.setForeground(new java.awt.Color(0, 0, 0));
        l_grille.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_grille.setText("VOTRE GRILLE");

        bQuitter.setBackground(new java.awt.Color(233, 173, 15));
        bQuitter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bQuitter.setForeground(new java.awt.Color(255, 255, 255));
        bQuitter.setText("Quitter");
        bQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bQuitterActionPerformed(evt);
            }
        });

        panelClic.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelClic.setForeground(new java.awt.Color(0, 0, 0));

        l_infoclic.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        l_infoclic.setForeground(new java.awt.Color(0, 0, 0));
        l_infoclic.setText("Information clic:");

        infoClic.setEditable(false);
        infoClic.setBackground(new java.awt.Color(255, 255, 255));
        infoClic.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        infoClic.setForeground(new java.awt.Color(0, 0, 0));
        infoClic.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout panelClicLayout = new javax.swing.GroupLayout(panelClic);
        panelClic.setLayout(panelClicLayout);
        panelClicLayout.setHorizontalGroup(
            panelClicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClicLayout.createSequentialGroup()
                .addComponent(l_infoclic)
                .addGap(0, 107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClicLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoClic)
                .addContainerGap())
        );
        panelClicLayout.setVerticalGroup(
            panelClicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClicLayout.createSequentialGroup()
                .addComponent(l_infoclic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoClic, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
        );

        finJeu.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N

        finjeu2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N

        regleJeu.setText("Règles");

        menuregleJeu.setText("Règles");
        menuregleJeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuregleJeuActionPerformed(evt);
            }
        });
        regleJeu.add(menuregleJeu);

        jMenuBar1.add(regleJeu);

        meilleursScores.setText("Meilleurs Scores");

        menumeilleursScores.setText("Meilleurs Scores");
        menumeilleursScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menumeilleursScoresActionPerformed(evt);
            }
        });
        meilleursScores.add(menumeilleursScores);

        jMenuBar1.add(meilleursScores);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(23, Short.MAX_VALUE)
                        .addComponent(grillePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(l_grille, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(bQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(37, 37, 37))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(panelClic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(finJeu, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(finjeu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(l_grille)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(grillePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(panelClic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(finJeu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(finjeu2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(bQuitter, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuregleJeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuregleJeuActionPerformed
        // TODO add your handling code here:
        this.setVisible(false); // on cache la page de jeu
        page_regles.setVisible(true); // on affiche la page des règles du jeu

    }//GEN-LAST:event_menuregleJeuActionPerformed

    private void bQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bQuitterActionPerformed
        // TODO add your handling code here:
        int rep = JOptionPane.showConfirmDialog(this, "Etes-vous sur.e de vouloir quitter votre partie?"); // on affiche une fenêtre pour vérifier si le joueur souhaite mettre fin à sa partie
        try {
            if (rep == JOptionPane.YES_OPTION) {
               // Quand le joueur quitte la partie, il faut récupérer son score et ajouter le joueur dans la liste des joueurs
                liste_joueurs.add(joueur);
                monJeu.versFichierScores(liste_joueurs);
                System.exit(0);
            }
        } catch (IOException exe) {
        }
    }//GEN-LAST:event_bQuitterActionPerformed

    private void menumeilleursScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menumeilleursScoresActionPerformed
        // TODO add your handling code here:
        this.setVisible(false); // on cache la page de jeu
        meilleurs_scores.init(); // on remplie tab_scores avec tous les joueurs 
        meilleurs_scores.setVisible(true); // on affiche la page du tableau des scores
    }//GEN-LAST:event_menumeilleursScoresActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(page_jeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(page_jeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(page_jeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(page_jeu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    page_jeu dialog = new page_jeu(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (IOException exe) {

                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bQuitter;
    private javax.swing.JLabel finJeu;
    private javax.swing.JLabel finjeu2;
    private javax.swing.JPanel grillePanel;
    private javax.swing.JTextField infoClic;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jScore;
    private javax.swing.JLabel l_grille;
    private javax.swing.JLabel l_infoclic;
    private javax.swing.JMenu meilleursScores;
    private javax.swing.JMenuItem menumeilleursScores;
    private javax.swing.JMenuItem menuregleJeu;
    private javax.swing.JPanel panelClic;
    private javax.swing.JLabel photo;
    private javax.swing.JLabel pseudo_joueur;
    private javax.swing.JMenu regleJeu;
    private javax.swing.JLabel score_joueur;
    // End of variables declaration//GEN-END:variables
}
