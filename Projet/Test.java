import java.io.*;
import java.util.*;

public class Test {

	private Agence agence;

	public void init(){
		String nomAgence = "Christophilms";
		ArrayList<Genre> genres = new ArrayList<Genre>();
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
		ArrayList<Film> films = new ArrayList<Film>();
		ArrayList<Client> clients = new ArrayList<Client>();
		int[] dureesDisponibles = new int[] {24, 48, 72};
		String[] moyensDePaiementDisponibles = new String[] {"Carte bancaire", "Chèque", "Espèces", "Prépayé"};
		genres.add(new Genre(0, "Comédie"));
		genres.add(new Genre(1, "Science-fiction"));
		genres.add(new Genre(2, "Action"));
		genres.add(new Genre(3, "Romance"));
		genres.add(new Genre(4, "Horreur"));
		categories.add(new Categorie(0, "Nouveauté"));
		categories.add(new Categorie(1, "Récent"));
		categories.add(new Categorie(2, "De l'année"));
		categories.add(new Categorie(3, "De l'an passé"));
		categories.add(new Categorie(4, "Plus ancien"));
		ArrayList<Genre> genresJeanJamy = new ArrayList<Genre>();
		genresJeanJamy.add(genres.get(0));
		genresJeanJamy.add(genres.get(2));
		ArrayList<Genre> genresMichelDrucker = new ArrayList<Genre>();
		genresMichelDrucker.add(genres.get(2));
		ArrayList<Genre> genresAventureNormale = new ArrayList<Genre>();
		genresAventureNormale.add(genres.get(4));
		ArrayList<Genre> genresDanserLaJava = new ArrayList<Genre>();
		genresDanserLaJava.add(genres.get(3));
		films.add(new Film(0, "Jean-Jamy 2 - Retour (chez lui) ", "2h00", categories.get(0), genresJeanJamy));
		films.add(new Film(1, "Michel Drucker et la quête du Graal", "1h30", categories.get(1), genresMichelDrucker));
		films.add(new Film(2, "Une aventure normale", "1h30", categories.get(2), genresAventureNormale));
		films.add(new Film(3, "Jean-Jamy - L'épopée du camion", "2h30", categories.get(3), genresJeanJamy));
		films.add(new Film(4, "Danser la Java", "2h00", categories.get(4), genresDanserLaJava));
		clients.add(new Client(0, "Christophe", "Dehe", "christophe.dehe@univ-fcomte.fr", 150.50));
		clients.add(new Client(1, "Afaf", "Nassih", "afaf.nassih@edu.univ-fcomte.fr", 25.50));
		clients.add(new Client(2, "Jason", "Vandaele", "jason.vandaele@edu.univ-fcomte.fr", 25.50));
		//On génère 2 DVDs, 2 BlueRays et 2 K7 disponibles pour chaque film.
		int id = 0;
		for(int i=0;i<films.size();i++){
			ArrayList<Support> supports = new ArrayList<Support>();
			for(int j=0;j<2;j++){
				supports.add(new Dvd(j, id, films.get(i), true));
				id++;
				supports.add(new BlueRay(j, id, films.get(i), true));
				id++;
				supports.add(new k7(j, id, films.get(i), true));
				id++;
			}
			films.get(i).setSupportsDuFilm(supports);
		}
		this.agence = new Agence(nomAgence, moyensDePaiementDisponibles, genres, categories, films, clients);
	}

	public void run(){
		init();
		agence.demo();
	}

	public static void main(String[] args) {
		Test t = new Test();
		t.run();
	}
}