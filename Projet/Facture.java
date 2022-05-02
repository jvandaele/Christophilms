import java.io.*;
import java.util.*;

public class Facture {

	private int idFacture;
	private double prix;
	private Location location;
	private MoyenDePaiement moyenDePaiement;

	public Facture(){}

	public Facture(Location location, double prix){
		this.location = location;
		this.prix = prix;
	}

	public void penalite(double montant){
		this.prix = this.prix + montant;
	}

	public int getIdFacture(){ return this.idFacture; }
	public void setIdFacture(int idFacture){ this.idFacture = idFacture; }
	public double getPrix(){ return this.prix; }
	public void setPrix(double prix){ this.prix = prix; }
	public Location getLocation(){ return this.location; }
	public void setLocation(Location location){ this.location = location; }
	public MoyenDePaiement getMoyenDePaiement() { return this.moyenDePaiement; }
	public void setMoyenDePaiement(MoyenDePaiement moyenDePaiement) { this.moyenDePaiement = moyenDePaiement; }
}