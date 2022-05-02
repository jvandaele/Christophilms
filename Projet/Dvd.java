import java.io.*;
import java.util.*;

public class Dvd extends Support {

	private int idDVD;

	public Dvd(){}

	public Dvd(int idDVD, int idSupport, Film film, boolean disponible){
		super(idSupport, film, disponible);
		this.idDVD = idDVD;
	}

	public int getIdDVD(){ return this.idDVD; }
	public void setIdDVD(int idDVD){ this.idDVD = idDVD; }
}