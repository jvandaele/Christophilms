import java.io.*;
import java.util.*;

public class Categorie {
	
	private int idCategorie;
	private String libelle;
	private ArrayList<Film> films = new ArrayList<Film>();

	public Categorie(){}

	public Categorie(int idCategorie, String libelle){
		this.idCategorie = idCategorie;
		this.libelle = libelle;
	}

	public int getIdCategorie(){ return this.idCategorie; }
	public void setIdCategorie(int idCategorie){ this.idCategorie = idCategorie; }
	public String getLibelle(){ return this.libelle; }
	public void setLibelle(String libelle){ this.libelle = libelle; }
	public ArrayList<Film> getFilms(){ return this.films; }
	public void setFilms(ArrayList<Film> films){ this.films = films; }
}