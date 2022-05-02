import java.io.*;
import java.util.*;

public class Film {

	private int idFilm;
	private String titre;
	private String duree;
	private ArrayList<Support> supportsDuFilm = new ArrayList<Support>();
	private Categorie categorie;
	private ArrayList<Genre> genres = new ArrayList<Genre>();

	public Film(){}

	public Film(int idFilm, String titre, String duree, Categorie categorie, ArrayList<Genre> genres){
		this.idFilm = idFilm;
		this.titre = titre;
		this.duree = duree;
		this.categorie = categorie;
		this.genres = genres;
	}

	public Support estDisponible(int format){
		switch (format){
			case 0:
				for(int i=0;i<this.supportsDuFilm.size();i++){
					if(this.supportsDuFilm.get(i).getDisponible()){ return this.supportsDuFilm.get(i); }
				}
				break;
			case 1:
				for(int i=0;i<this.supportsDuFilm.size();i++){
					if(this.supportsDuFilm.get(i).getDisponible()){ 
						if(this.supportsDuFilm.get(i) instanceof Dvd){ return this.supportsDuFilm.get(i); }
					}
				}
				break;
			case 2:
				for(int i=0;i<this.supportsDuFilm.size();i++){
					if(this.supportsDuFilm.get(i).getDisponible()){ 
						if(this.supportsDuFilm.get(i) instanceof BlueRay){ return this.supportsDuFilm.get(i); }
					}
				}
				break;
			case 3:
				for(int i=0;i<this.supportsDuFilm.size();i++){
					if(this.supportsDuFilm.get(i).getDisponible()){ 
						if(this.supportsDuFilm.get(i) instanceof k7){ return this.supportsDuFilm.get(i); }
					}
				}
				break;
			default:
				break;
		}
		return null;
	}

	public int getIdFilm(){ return this.idFilm; }
	public void setIdFilm(int idFilm){ this.idFilm = idFilm; }
	public String getTitre(){ return this.titre; }
	public void setTitre(String titre){ this.titre = titre; }
	public String getDuree(){ return this.duree; }
	public void setDuree(String duree){ this.duree = duree; }
	public ArrayList<Support> getSupportsDuFilm(){ return this.supportsDuFilm; }
	public void setSupportsDuFilm(ArrayList<Support> supportsDuFilm){ this.supportsDuFilm = supportsDuFilm; }
	public Categorie getCategorie(){ return this.categorie; }
	public void setCategorie(Categorie categorie){ this.categorie = categorie; }
	public ArrayList<Genre> getGenres(){ return this.genres; }
	public void setGenres(ArrayList<Genre> genres){ this.genres = genres; }
}