import java.io.*;
import java.util.*;

public class Genre {

	private int idGenre;
	private String libelle;
	private ArrayList<Film> films = new ArrayList<Film>();

	public Genre(){}

	public Genre(int idGenre, String libelle){
		this.idGenre = idGenre;
		this.libelle = libelle;
	}

	public int getIdGenre(){ return this.idGenre; }
	public void setIdGenre(int idGenre){ this.idGenre = idGenre; }
	public String getLibelle(){ return this.libelle; }
	public void setLibelle(String libelle){ this.libelle = libelle; }
	public ArrayList<Film> getFilms(){ return this.films; }
	public void setFilms(ArrayList<Film> films){ this.films = films; }
}