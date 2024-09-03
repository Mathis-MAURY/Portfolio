import java.util.Scanner;

/**
 * Rôle : Cette application a pour but d’afficher les scores finaux des différents participants ainsi que le podium des participants.
 * Pour cela, l’utilisateur doit saisir les données de la course ( Le nombre de portes total dans la course et le nombre de coureur) 
 * puis le temps de chaque coureur, le nombre de portes ratées ou touchées tout cela pour les deux manches. 
 * 
 *
 * @author LABOUTE Damien , MAURY Mathis , GABARRA Illan
 * @version 1.0
 * 
 */
public class CourseCanoe
{

    /**
     * Rôle : Programme principal permettant d'afficher les résultats et le podium d'une
     * course de canöe à partir d'une saisie des données de la courses et
     * des temps et pénalités de chaque compétiteur. 
     *
     * @author GABARRA Illan
     */
    public static void main(String[] args)
    {
        // ----------------------------
        // Déclaration des variables 
        // ---------------------------- 

        // Délaration d'une variable entière correspondant au nombre de portes 
        int nbPortes ;

        // Délaration d'une variable entière correspondant au nombre de participant 
        int nbParticipants;

        // Déclaration d'un tableau à une entrée des valeurs entières de temps de course 
        // en milisecondes de la manche 1, où tabManche1[0] correspond au temps de course du 
        // 1er coureur.
        int[] tabManche1 ;

        // Même chose mais avec la manche 2 
        int[] tabManche2 ;

        int[] tabFinal ; // Déclaration du tableau de la somme des temps
        // compensé de chaque manche pour chaque compétiteur

        String chaineResultats;// Chaîne de caractère à afficher pour afficher les résultats des deux manches
        String chainePodium; // Chaîne de caractère à afficher pour afficher le podium
        // ----------------------------
        // Saisies des données de la course 
        // ----------------------------

        nbPortes = saisieControlee(18,22,
            "Saisie du nombre de portes, >= 18 et <= 22 ");

        nbParticipants = saisieControlee(1,49,
            "Saisie du nombre de participants, il doit y avoir au moins 1 competiteur et au maximum 49");

        // Initialisation de la taille des tableaux tabManche1,tabManche2,tabFinal par 
        // rapport au nombre de partipants

        tabManche1 = new int[nbParticipants];
        tabManche2 = new int[nbParticipants];
        tabFinal = new int[nbParticipants]; 

        // --------------------------------------
        // Saisies des résultats de chaque manche 
        // ---------------------------------------
        creaTabTemps(tabManche1,tabManche2,nbParticipants,nbPortes);
        // ---------------------------------------
        // Traitements des temps en temps final 
        // ---------------------------------------
        cumulScore(tabManche1,tabManche2,nbParticipants,tabFinal);

        // ---------------------------------------
        // Création des chaîne de caractères à afficher 
        // ---------------------------------------

        chaineResultats = creaStringResultat(tabFinal,nbParticipants);
        chainePodium = podiumFinal(tabFinal,nbParticipants);

        // ---------------------------------------
        // Affichages des résultats et du podium
        // ---------------------------------------

        System.out.println(/*chaîneResultats +"\n"+*/chainePodium);
    }

    /**
     * Rôle : Permet de remplir le tableau des temps pfTabManche1, pfTabManche2 des pfNbParticipants compétiteurs 
     * des temps compensés obtenu par saisieCompétiteur 
     *
     * @author GABARRA Illan
     *
     * prec : pfNbParticipants > 0 et pfNbPortes >= 0 
     *
     * @param pfTabManche1 OUT : Tableau à une entrée d'entier correspondant au 
     *                           temps compensé de course des compétiteurs durant la manche 1
     *                          
     * @param pfTabManche2 OUT : Tableau à une entrée d'entier correspondant au 
     *                           temps compensé de course des compétiteurs durant la manche 2
     * 
     * @param pfNbParticipants IN : Entier correspondant au nombre de compétiteurs
     * 
     * @param pfNbPortes IN : Entier correspondant au nombre de portes dans la course
     * 
     * 
     */
    public static void creaTabTemps (int[] pfTabManche1, int[]pfTabManche2 ,int pfNbParticipants,int pfNbPortes){
        // Déclaration des variables

        int i ; // Indice de parcours du tableau pfTabManche1 et pfTabManche2
        Scanner clavier = new Scanner(System.in); // Déclaration du clavier

        // Saisies des temps de la première manche 
        for (i = 0 ; i < pfNbParticipants ; i ++) {

            System.out.println
            ("Le coureur avec le brassard numero "+ (i+1) +" a t-il finit la premiere manche ? 1 pour Oui , 0 pour Non ");

            if (clavier.nextInt() == 1){
                System.out.println
                ("Entrez le temps du competiteur numero  "+(i+1)+" en milisecondes");
                pfTabManche1[i] = saisieCompetiteur(pfNbPortes);
            }
            else {

                pfTabManche1[i] = -1 ;

            }
        }
        
        // Saisies des temps de la deuxième manche 
        for (i = 0 ; i < pfNbParticipants ; i ++) {

            if (pfTabManche1[i] >= 0){

                System.out.println
                ("Le coureur avec le brassard numero  "+ (i+1) +" a t-il finit la seconde manche ? 1 pour Oui , 0 pour Non ");

                if (clavier.nextInt() == 1){

                    System.out.println
                    ("Entrez le temps du competiteur numero  "+ (i+1) +" en milisecondes");
                    pfTabManche2[i] = saisieCompetiteur(pfNbPortes);

                }
                else {

                    pfTabManche2[i] = -2 ;

                }
            }
            else {

                pfTabManche2[i] = -2 ;
            }
        }
    }

    /** 
     * Rôle : Permet une saisie du temps et du nombre de portes ratées et touchées pour réaliser le parcours pour un participant
     * et renvoit le temps compensé pour cette course (temps obtenu en ajoutant les pénalités de temps lié au nombre 
     * de portes ratées et touchées dans les pfNbPortes portes existantes au temps obtenu pendant la course)
     * pour ce compétiteur
     * 
     * @author Illan GABARRA
     * 
     * @param pfNbPortes IN : Entier correspondant au nombre de portes dans le parcours
     * 
     * @return Temps compensé du compétiteur (Temps avec les pénalités) 
     * 
     * prec : pfNbPortes >= 0 
     */
    public static int saisieCompetiteur (int pfNbPortes){
        //Déclaration des variables
        Scanner clavier = new Scanner(System.in); // Déclaration du clavier
        int portesTouchees = 0 ; // Nombre de portes touchées 
        int portesRatees ; // Nombre de portes ratées 
        int tempsSimple ; // Temps de la course du compétiteur
        int tempsCompense ; // Temps compensé du compétiteur
        //Initialisation de tempsSimple par une saisie 
        tempsSimple = clavier.nextInt();

        //Contrôle de saisie pour que le tempsSimple soit strictement supérieur à 0
        while ( tempsSimple <= 0 ){
            System.out.println
            ("Le temps du competiteur doit être > 0, ressaisissez !");
            tempsSimple = clavier.nextInt();

        }
        portesRatees = saisieControlee(0,pfNbPortes,
            "Entrez le nombre de portes ratees par le competiteur cela doit inferieur ou egal au nombre de portes existantes soit "+pfNbPortes+" portes");

        // Si le nombre de portes ratées est égal au nombre de portes dans la course
        // alors ça ne sert à rien de demander si des portes ont été touchées car 
        if (pfNbPortes-portesRatees != 0){
            portesTouchees = saisieControlee(0,pfNbPortes-portesRatees,
                "Entrez le nombre de portes touchees qui doit inferieur ou egal au nombre de portes existantes moins celle que le competiteur a touche soit "+ (pfNbPortes  - portesRatees) +" portes");
        }

        //Traitement du temps simple en temps compensé
        tempsCompense = tempsSimple + (2 * 1000 *  portesTouchees) + (50*1000 * portesRatees) ; 

        // Retourne le temps compensé
        return tempsCompense ;
    }

    /**
     * 
     *  Rôle : Permet de saisir un entier supérieure à la borne inférieure et inférieure à la borne supérieure
     *  entier nombreSaisie dans pfbinf entier et pfbsup entier
     *  
     *  @author Mathis MAURY
     *  
     *  @param pfbinf IN : borne inférieure 
     *  @param pfbsup IN : borne supérieure
     *  @param String pfmsg IN : message à afficher sur l'écran par rapport à l'objet de la saisie
     *  
     *  @return le nombre saisie 
     *  prec: pfbinf <= pfbsup 
     */
    public static int saisieControlee(int pfbinf, int pfbsup, String pfmsg) 
    { 
        // Déclaration des variables
        Scanner clavier = new Scanner(System.in);
        int nombreSaisie;

        //Début de la saisie 
        System.out.println(pfmsg); 
        nombreSaisie= clavier.nextInt(); 

        // on vérifie si le nombre saisie est compris 
        // entre la borne inférieur et la borne supérieur borne comprise

        while (pfbinf > nombreSaisie || nombreSaisie > pfbsup){

            // Le nombre saisie ne respecte pas la condition du commentaire au
            // dessus on demande à l'utilisateur de ressaisir.

            System.out.println(pfmsg);
            nombreSaisie= clavier.nextInt();

        }
        // Si le nombre saisie est compris 
        // entre la borne inférieur et la borne supérieur cela retourne saisit

        return nombreSaisie;

    }

    /*
     * Rôle : permet de retourner un tableau avec pour chaque participant un numéro de brassard avec un temps compensé
     * 
     * @author: Mathis MAURY
     * 
     * @param pftabTemps IN/OUT tableau d'entier à traiter 
     * @param pfnbParticipant IN nombre de participants
     *
     *return chaineResultats qui est le nombre de cases remplies dans le tableau
     *prec: pftabTemps[i] > 0
     */
    public static String creaStringResultat(int pftabTemps[], int pfnbParticipants) {
        //Creation de la chaine de résultats 
        String chaineResultats ="---------------------------"+"\n";
        chaineResultats = chaineResultats +"|" +"  "+ "Tableau des resultats" +"  "+ "|"+"\n";
        chaineResultats = chaineResultats+"---------------------------"+"\n";
        chaineResultats = chaineResultats+"N  brassards | Temps total "+"\n";
        chaineResultats = chaineResultats+"---------------------------"+"\n";
        for(int i = 0 ; i < pfnbParticipants ; i ++){
            if ( i+1 < 10 ) {
                chaineResultats = chaineResultats +" ";
            }
            chaineResultats = chaineResultats +"     ";
            chaineResultats = chaineResultats + (i+1) ; 
            chaineResultats = chaineResultats + "      " ;
            chaineResultats = chaineResultats +"|      " ; 
            if (pftabTemps[i] > 0) {
                chaineResultats = chaineResultats + pftabTemps[i];
            }
            else {
                chaineResultats = chaineResultats + "Disqualifie";
            }
            chaineResultats = chaineResultats +"\n";
        }
        chaineResultats = chaineResultats+"-----------------------------"+"\n";
        //Affichage de la chaine de résultats
        System.out.println(chaineResultats);
        return chaineResultats; 
    }

    /**
     * Rôle : Cette fontion permettra de faire le cumul des deux manches afin d'avoir le total du score des participants 
     * 
     * @autor Damien LABOUTE
     * 
     *  @param pfTabManche1 IN : Tableau des résultats de la manche 1
     *  @param pfTabManche2 IN : Tableau des résultats de la manche 2
     *  @param pfNbParticipants IN : nombre de participants
     * 
     *  @param pfTabScore OUT : Tableau à remplir avec les scores totaux  
     */
    public static void cumulScore (int[] pfTabManche1, int[] pfTabManche2,int pfNbParticipants, int[] pfTabScore){
        //DÉCLARATION DES VARIABLES
        int i; //indice de parcours

        // PERMET DE PARCOURIR ET REMPLIR LE TABLEAU DES SCORES TOTAUX
        for (i = 0; i < pfNbParticipants; i++){
            if (pfTabManche1[i] >= 0 && pfTabManche2[i] >= 0){
                pfTabScore[i] = pfTabManche1[i] + pfTabManche2[i]; // additionne les scores des deux manches si elles ont été validées
            }
            else {
                if (pfTabManche1[i] < 0 ){
                    pfTabScore[i] = -1; // -1 correspond au fait que le participant n'a pas validé sa première manche
                }
                else{
                    pfTabScore[i] = -2; // -2 correspond au fait que le participant n'a pas validé sa deuxième manche
                }
            }
        }
    }

    /**
     * Rôle : permet de rechercher le nombre de fois que l'on retrouve un nombre dans un tableau en récupérant son indice
     * 
     * @autor Damien LABOUTE
     * 
     * @param pfNbParticipants IN : nombre de participants
     * @param pfScore IN : Tableau contenant les score des participants
     * @param pfscore IN : Valeur à rechercher dans le tableau
     * 
     * @param pfIndices OUT : Tableau des indices des positions de la valur cherchée
     * 
     * @return nboccu : nombre de fois qu ecette valeur apparaît dans le tableau
     * 
     */
    public static int scoreIdentiques(int pfscore, int pfNbParticipants, int[] pfScore, int[] pfIndices){
        //DÉCLARATION DES VARIABLES
        int j;
        int i;
        int nboccu;

        //INITIALISATION DES VARIABLES
        j = 0;
        nboccu = 0;

        //PARCOURS DU TABLEAU POUR RÉCUPÉRER LE NOMBRE DE FOIS QU'UNE VALEUR APPARAÎ DANS CELLE-CI
        for (i = 0; i < pfNbParticipants; i++){ 
            if (pfscore == pfScore[i]){
                pfIndices[j] = i;
                j++;
                nboccu++;
            }
        }
        return nboccu;
    }

    /**
     * Rôle : détermine le score minimum d'un tableau  une borne
     * 
     * @autor Damien LABOUTE
     * 
     * @param pfNbParticipants IN : nombre de participants
     * @param pfScore IN : Tableau contenant les score des participants
     * @param pfBorneInf IN : Borne inférieure  
     * 
     * @return score minimum au dessus de pfBorneInf
     * 
     */
    public static int determineScore (int[] pfScore, int pfNbParticipants, int pfBorneInf){
        //DÉCLARATION DES VARIABLES
        int score;
        int i;

        //INITIALISATUION DES VARIABLES
        score = pfScore[0];
        i = 1;

        while (score <= pfBorneInf && i <= (pfNbParticipants - 1)){ // verifie que la valeur initiale ne soit pas inferieur ou égale à la borne
            score = pfScore[i];
            i++;
        }

        //RECHERCHE DU MINIMUM A PARTIR D'UNE BORNE
        if (score > pfBorneInf){
            for (i = 1; i < pfNbParticipants; i++){ 
                if (score > pfScore[i] && pfScore[i] > pfBorneInf){
                    score = pfScore[i];
                }
            }
        }
        return score;
    }

    /**
     * Rôle : calcul du nombre de participants ayant validé les deux parcours
     * 
     * @autor Damien LABOUTE
     * 
     * @param pfNbParticipants IN : nombre de participants
     * @param pfScore IN : Tableau contenant les score des participants
     * 
     * @return le nombre de participants qui ont validé les deux parcours
     * 
     */
    public static int participantsValides(int[] pfScore, int pfNbParticipants){
        //DÉCLARATION DES VARIABLES
        int valide;
        int i;

        //INITIALISATUION DES VARIABLES
        valide = 0;

        //PARCOURS DU TABLEAU ET RECHERCHE SI LA VALEUR EST SUPÉRIEURE A 0 (parcours validé)
        for (i = 0; i < pfNbParticipants; i++){
            if ( pfScore[i] > 0 ) {
                valide ++;
            }
        }
        return valide;
    }

    /**
     * Rôle : Permet de donner le numéro des brassards des participants sur le podium ainsi que leur temps associé
     * 
     * @autor Damien LABOUTE
     * 
     * @param pfScore IN : Tableau des score des participants 
     * @param pfNbParticipants IN : nombre de participants
     * 
     * @return une chaîne de carcatères qui donne le numéro du brassard, la place et le temps des participants sur le podium 
     */
    public static String podiumFinal (int[] pfScore ,int pfNbParticipants ){
        //DÉCLARATION DES VARIABLES
        int i,j; // indices de parcours
        String podium; // chaîne de caractères retournée
        int nbPremier; // nombre de participants en première position
        int nbDeuxieme; // nombre de participants en deuxième position
        int nbTroisieme; // nombre de participants en troisième position
        int meilleurScore; // meilleur score 
        int deuxiemeScore; // Score du deuxième
        int troisiemeScore; // Score du troisième
        int[] indiceMeilleur; // tableau des indices des participants en 1ère position
        int[] indiceDeuxieme; // tableau des indices des participants en 2ème position 
        int[] indiceTroisieme; // tableau des indices des participants en 3ème position 
        int validite; // nombre de participants ayant validé les deux parcours 

        //INITIALISATUION DES VARIABLES
        j = 0;
        podium = "";
        indiceMeilleur = new int[pfNbParticipants];
        indiceDeuxieme = new int[pfNbParticipants]; 
        indiceTroisieme = new int[pfNbParticipants]; 
        validite = participantsValides( pfScore, pfNbParticipants ); 

        //RECHERCHE DES TEMPS ET DES PARTICIPANTS SUR LE PODIUM
        // détermine le meilleur score
        meilleurScore = determineScore(pfScore, pfNbParticipants, 0);
        // détermnine le deuximème score
        deuxiemeScore = determineScore(pfScore, pfNbParticipants, meilleurScore);
        // détermine le troisième score
        troisiemeScore = determineScore(pfScore, pfNbParticipants,deuxiemeScore);

        // cherche quel participant(s) a le meilleur score
        nbPremier = scoreIdentiques(meilleurScore, pfNbParticipants, pfScore, indiceMeilleur);
        // cherche quel participant(s) a le deuxième score
        nbDeuxieme = scoreIdentiques(deuxiemeScore, pfNbParticipants, pfScore, indiceDeuxieme);
        // cherche quel participant(s) a le troisième score
        nbTroisieme = scoreIdentiques(troisiemeScore, pfNbParticipants, pfScore, indiceTroisieme);

        //REMPLI LA CHAINE DE CARACTERE QUI SERA RETOURNÉE
        // contôle du nombre de participants sur le podium
        if (validite > 2){// si le podium est complet
            if (nbPremier >= 3){ // si trois participants ou plus ont gagné à égalité
                for (i = 0; i < nbPremier; i++){
                    podium = podium + "Le participant numero " + (indiceMeilleur[i] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[i]] + " millisecondes" + "\n";
                }
            }
            else{
                if(nbPremier == 2){ // si deux participants ont gagné
                    for (i = 0; i < nbPremier; i++){
                        podium = podium + "Le participant numero " + (indiceMeilleur[i] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[i]] + " millisecondes" + "\n";
                    }
                    for (i = 0; i < nbTroisieme; i++){
                        podium = podium + "Le participant numero " + (indiceDeuxieme[i] + 1) + " a fini 3eme avec un temps de " + pfScore[indiceDeuxieme[i]] + " millisecondes" + "\n";
                    }
                }
                else{
                    if(nbDeuxieme >= 2){// si deux participants ou plus ont fini 2ème
                        podium = podium + "Le participant numero " + (indiceMeilleur[0] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[0]] + " millisecondes" + "\n";
                        for (i = 0; i < nbDeuxieme; i++){
                            podium = podium + "Le participant numero " + (indiceDeuxieme[i] + 1) + " a fini 2eme avec un temps de " + pfScore[indiceDeuxieme[i]] + " millisecondes" + "\n";
                        }
                    }
                    else{// si le 1er et le deuxième sont seuls sur leur marche respective du podium
                        podium = podium + "Le participant numero " + (indiceMeilleur[0] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[0]] + " millisecondes" + "\n";
                        podium = podium + "Le participant numero " + (indiceDeuxieme[0] + 1) + " a fini 2eme avec un temps de " + pfScore[indiceDeuxieme[0]] + " millisecondes" + "\n";
                        for (i = 0; i < nbTroisieme; i++){
                            podium = podium + "Le participant numero " + (indiceTroisieme[i] + 1) + " a fini 3eme avec un temps de " + pfScore[indiceTroisieme[i]] + " millisecondes" + "\n";
                        }
                    }
                }
            }
        }
        else{// si le podium est incomplet
            if(validite == 2){// si il y a deux personnes sur le podium
                if(nbPremier == 2){//si les deux sont 1er
                    podium = podium + "Le participant numero " + (indiceMeilleur[0] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[0]] + " millisecondes" + "\n";
                    podium = podium + "Le participant numero " + (indiceMeilleur[1] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[1]] + " millisecondes" + "\n";
                    podium = podium + "Il n'y a pas de participant en 3eme position";
                }
                else{// si il y a un premier et un second
                    podium = podium + "Le participant numero " + (indiceMeilleur[0] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[0]] + " millisecondes" + "\n";
                    podium = podium + "Le participant numero " + (indiceDeuxieme[0] + 1) + " a fini 2eme avec un temps de " + pfScore[indiceDeuxieme[0]] + " millisecondes" + "\n";
                    podium = podium + "Il n'y a pas de participant en 3eme position";
                }
            }
            else{ 
                if(validite == 1){// si il n'y a qu'une seule personne sur le podium
                    podium = podium + "Le participant numero " + (indiceMeilleur[0] + 1) + " a gagne avec un temps de " + pfScore[indiceMeilleur[0]] + " millisecondes" + "\n";
                    podium = podium + "Il n'y a pas de participant en 2eme position" + "\n";
                    podium = podium + "Il n'y a pas de participant en 3eme position";
                }
                else{// si personne ne se trouve sur le poduim
                    podium = podium + "Aucun participant n'a réussi à valider ses deux parcours par conséquent  le podium est vide...";
                }
            }
        }
        return podium;
    }
}
