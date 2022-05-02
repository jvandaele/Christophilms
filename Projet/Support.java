import java.io.*;
import java.util.*;

public class Support {
	
	private int idSupport;
	private Film film;
	private boolean disponible;

	public Support(){}

	public Support(int idSupport, Film film, boolean disponible){
		this.idSupport = idSupport;
		this.film = film;
		this.disponible = disponible;
	}

	public int getIdSupport(){ return this.idSupport; }
	public void setIdSupport(int idDVD){ this.idSupport = idSupport; }
	public Film getFilm(){ return this.film; }
	public void setFilm(Film film){ this.film = film; }
	public boolean getDisponible(){ return this.disponible; }
	public void setDisponible(boolean disponible){ this.disponible = disponible; }
}