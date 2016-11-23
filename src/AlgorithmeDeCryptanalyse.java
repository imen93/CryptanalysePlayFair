import java.awt.Point;
import java.util.ArrayList;

public class AlgorithmeDeCryptanalyse {
	private static String[] bigrammesPlusFrequents = { "ES", "DE", "LE", "EN",
			"RE", "NT" };
	private static Character[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	private static String textChiffré = "IGAJLBAGRNBVYOHFGDBHYU"; // LALACTA
																	// //igajlbagrnbvyohfgdbhyu
	private static char[][] grille;
	private static Point[] positions;

	public static void main(String[] args) {

		Character[] key = { 'x', 'x', 'x', 'x', 'x' };
		String s = null;

		// Brute force
		for (int i = 0; i < alphabet.length; i++) {
			key[0] = alphabet[i];
			for (int j = 0; j < alphabet.length; j++) {
				key[1] = alphabet[j];
				for (int k = 0; k < alphabet.length; k++) {
					key[2] = alphabet[k];
					for (int m = 0; m < alphabet.length; m++) {
						key[3] = alphabet[m];
						for (int t = 0; t < alphabet.length; t++) {
							key[4] = alphabet[t];
							s = key[0] + "" + key[1] + "" + key[2] + ""
									+ key[3] + key[4];
							// cree la grille
							creerGrille(s);
							// decoder le texte Chiffré à l'aide de grille
							// obtenue
							String textClair = decode(textChiffré);
							// vérifer si les bigrammes les plus frequents ds le
							// texte clair correspond
							// aux bigrammes les plus frequents dans la langue
							// française
							if (veriferTextClair(textClair)) {
								System.out.println(textClair);
							}

						}

					}

				}

			}
		}
	}

	// vérifer si les bigrammes les plus frequents ds le texte clair correspond
	// aux bigrammes les plus frequents dans la langue française
	public static Boolean veriferTextClair(String s) {
		Boolean etat = false;
		String[] bigrammePlusFrequent = null;
		bigrammePlusFrequent = rechercheBigrammePlusFrequent(s);
		for (int i = 0; i < bigrammePlusFrequent.length; i++) {

			for (int j = 0; j < 6; j++) {
				{
					if (bigrammePlusFrequent[i]
							.equals(bigrammesPlusFrequents[j])) {
						etat = true;
						break;
					}
				}
			}
		}
		return etat;
	}

	// cree la grille

	private static void creerGrille(String cle) {
		grille = new char[5][5];
		positions = new Point[26];
		String s = cle + "ABCDEFGHIJKLMNOPRSTUVWXYZ";
		int len = s.length();
		for (int i = 0, k = 0; i < len; i++) {
			char c = s.charAt(i);
			if (positions[c - 'A'] == null) {
				grille[k / 5][k % 5] = c;
				positions[c - 'A'] = new Point(k % 5, k / 5);
				k++;
			}
		}
	}

	// decoder le texte Chiffré à l'aide de grille obtenue

	private static String decode(String s) {
		StringBuilder text = new StringBuilder(s);
		int len = text.length();
		for (int i = 0; i < len - 1; i += 2) {
			char a = text.charAt(i);
			char b = text.charAt(i + 1);

			int row1 = positions[a - 'A'].y;
			int row2 = positions[b - 'A'].y;
			int col1 = positions[a - 'A'].x;
			int col2 = positions[b - 'A'].x;

			if (row1 == row2) {
				col1 = (col1 + 4) % 5;
				col2 = (col2 + 4) % 5;

			} else if (col1 == col2) {
				row1 = (row1 + 4) % 5;
				row2 = (row2 + 4) % 5;

			} else {
				int tmp = col1;
				col1 = col2;
				col2 = tmp;
			}

			text.setCharAt(i, grille[row1][col1]);
			text.setCharAt(i + 1, grille[row2][col2]);
		}
		return text.toString();
	}

	// recherche les bigramme les plus frequents dans un texte

	public static String[] rechercheBigrammePlusFrequent(String text) {
		int n = text.length();
		ArrayList<String> bigrammes = new ArrayList<String>();
		String[] bigrammesPlusFrequentsCorrespond = new String[5]; // resultat
		ArrayList<String> bigrammesChiffréPlusFrequents = new ArrayList<String>();
		ArrayList<Integer> nbrBigrammesPlusFrequents = new ArrayList<Integer>();

		for (int i = 1; i < n - 1; i++) {
			String bigramme = text.substring(i, i + 2);
			bigrammes.add(bigramme);
		}
		for (int j = 0; j < bigrammes.size(); j++) {
			int inter = 1;
			String bi = bigrammes.get(j);
			if ((!bigrammesChiffréPlusFrequents.contains(bi))) {
				for (int k = j + 1; k < bigrammes.size(); k++) {
					if (bi.equals(bigrammes.get(k))) {
						inter++;
					}
				}
				bigrammesChiffréPlusFrequents.add(bi);
				nbrBigrammesPlusFrequents.add(inter);
			}
		}
		for (int i = 0; i < 5; i++) {
			int max = 1, id = 0;
			for (int j = 0; j < bigrammesChiffréPlusFrequents.size(); j++) {
				if (nbrBigrammesPlusFrequents.get(j) >= max) {
					max = nbrBigrammesPlusFrequents.get(j);
					id = j;
				}
			}
			bigrammesPlusFrequentsCorrespond[i] = bigrammesChiffréPlusFrequents
					.get(id);
			nbrBigrammesPlusFrequents.set(id, 0);
		}
		// System.out.println("liste des bigrammes les plus fréquents");
		for (int i = 0; i < 5; i++) {
			// System.out.println(bigrammesPlusFrequentsCorrespond[i]);
		}
		return bigrammesPlusFrequentsCorrespond;
	}

}
