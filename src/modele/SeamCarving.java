package modele;

import java.io.*;
import java.util.*;
public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {		
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();		   
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }
		
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

	/**
	 * Méthode qui permet d'écrire un fichier pgm
	 *
	 * @param image image à écrire
	 * @param filename nom de l'image en pgm
	 */
   public static void writepgm(int[][] image, String filename){
	   try {
		   // Ouverture du flux d'écriture
		   BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
		   // Ligne de départ d'un fichier pgm
		   bf.write("P2");
		   bf.newLine();
		   // Récupération de la largeur/hauteur
		   int largeur = image.length;
		   int hauteur = image[0].length;
		   // Ecriture de la largeur et hauteur
		   bf.write(largeur+" "+hauteur);
		   bf.newLine();
		   // Ecriture de la valeur maximale
		   bf.write("255");
		   // On parcourt la matrice
		   for(int i = 0 ; i < largeur; i++){
		   		for(int j = 0 ; j < hauteur; j++){
		   			// Ecriture de la valeur d'un pixel
					bf.write(image[i][j]+" ");
				}
				// Création de la nouvelle ligne pour que le
			    // Fichier soit plus visible
				bf.newLine();
		   }
		   // Fermeture du flux
		   bf.close();
		   // Gestion de l'erreur
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
}
