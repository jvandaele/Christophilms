import java.io.*;
import java.util.*;

public class Client {

	private int idClient;
	private String prenom;
	private String nom;
	private String email;
	private double solde;
	private ArrayList<Support> supportsEmpruntes = new ArrayList<Support>();
	private ArrayList<Location> locationsEffectuees = new ArrayList<Location>();

	public Client(){}

	public Client(int idClient, String prenom, String nom, String email, double solde){
		this.idClient = idClient;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.solde = solde;
	}

	public Location louer(Date dateDebut, ArrayList<Support> supports, ArrayList<Integer> durees){
		for(int i=0;i<supports.size();i++){
			supports.get(i).setDisponible(false);
		}
		this.supportsEmpruntes.addAll(supports);
		Location loc = new Location(this, supports, dateDebut, durees , true);
		this.locationsEffectuees.add(loc);
		return loc;
	}

	public boolean rendre(Date dateRendu, Location location, ArrayList<Support> supports){
		boolean locationTerminee = true;
		boolean enCours = false;
		for(int i=0;i<supports.size();i++){
			enCours = location.verifierRendu(supports.get(i), dateRendu);
			for(int j=0;j<this.supportsEmpruntes.size();j++){
				if(supports.get(i).getIdSupport() == supportsEmpruntes.get(j).getIdSupport()){
					supportsEmpruntes.remove(j);
					break;
				}
			}
		}
		return enCours;
	}

	public int getIdClient(){ return this.idClient; }
	public void setIdClient(int idClient){ this.idClient = idClient; }
	public String getPrenom(){ return this.prenom; }
	public void setPrenom(String prenom){ this.prenom = prenom; }
	public String getNom(){ return this.nom; }
	public void setNom(String nom){ this.nom = nom; }
	public String getEmail(){ return this.email; }
	public void setEmail(String email){ this.email = email; }
	public double getSolde(){ return this.solde; }
	public void setSolde(double solde){ this.solde = solde; }
	public ArrayList<Support> getSupportsEmpruntes(){ return this.supportsEmpruntes; }
	public void setSupportsEmpruntes(ArrayList<Support> supportsEmpruntes){ this.supportsEmpruntes = supportsEmpruntes; }
	public ArrayList<Location> getLocationsEffectuees(){ return this.locationsEffectuees; }
	public void setLocationsEffectuees(ArrayList<Location> locationsEffectuees){ this.locationsEffectuees = locationsEffectuees; }
}