import java.io.*;
import java.util.*;

public class Location {

	private Scanner input = new Scanner(System.in);

	private int idLocation;
	private Client client;
	private ArrayList<Support> supportsLoues = new ArrayList<Support>();
	private Facture facture;
	private Date dateDebutLocation;
	private ArrayList<Date> datesRenduPrevues = new ArrayList<Date>();
	private ArrayList<Date> datesRenduReeles = new ArrayList<Date>();
	private boolean enCours;

	public Location(){}

	public Location(Client client, ArrayList<Support> supportsLoues, Date dateDebutLocation, ArrayList<Integer> durees, boolean enCours){
		this.client = client;
		this.supportsLoues = supportsLoues;
		this.dateDebutLocation = dateDebutLocation;
		this.enCours = enCours;
		calculDateRendu(durees);
		genererDatesReelesDefault(supportsLoues.size());
	}

	public void genererDatesReelesDefault(int nbSupports){
		for(int i=0;i<nbSupports;i++){
			Date dateDefault = new Date(0,0,0);
			this.datesRenduReeles.add(dateDefault);
		}
	}

	public void calculDateRendu(ArrayList<Integer> durees) {
		for(int i=0;i<durees.size();i++){
			this.datesRenduPrevues.add(addHoursToJavaUtilDate(this.dateDebutLocation, durees.get(i)));
		}
	}

	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}

	public void genererFacture(double prix){
		this.facture = new Facture(this, prix);
	}

	public boolean verifierRendu(Support support, Date dateRendu){
		int index = 0;
		support.setDisponible(true);
		for(int i=0;i<this.supportsLoues.size();i++){
			if(this.supportsLoues.get(i).getIdSupport() == support.getIdSupport()){
				index = i;
				break;
			}
		}
		this.datesRenduReeles.set(index, dateRendu);
		if(this.datesRenduPrevues.get(index).compareTo(dateRendu) < 0){
			System.out.println("Le client est en retard sur le rendu du support n°" + support.getIdSupport() + " !");
			System.out.println("Entrez un motant de pénalité : ");
			double penalite = input.nextDouble();
			input.nextLine();
			this.facture.penalite(penalite);
		}
		this.enCours = false;
		for(int i=0;i<this.datesRenduReeles.size();i++){
			if(this.datesRenduReeles.get(i).compareTo(new Date(0,0,0)) == 0){
				this.enCours = true;
				break;
			}
		}
		return this.enCours;
	}

	public int getIdLocation(){ return this.idLocation; }
	public void setIdLocation(int idLocation){ this.idLocation = idLocation; }
	public Client getClient(){ return this.client; }
	public void setClient(Client client){ this.client = client; }
	public ArrayList<Support> getSupportsLoues(){ return this.supportsLoues; }
	public void setSupportsLoues(ArrayList<Support> supportsLoues){ this.supportsLoues = supportsLoues; }
	public Facture getFacture(){ return this.facture; }
	public void setFacture(Facture facture){ this.facture = facture; }
	public Date getDateDebutLocation(){ return this.dateDebutLocation; }
	public void setDateDebutLocation(Date dateDebutLocation){ this.dateDebutLocation = dateDebutLocation; }
	public ArrayList<Date> getDatesRenduPrevues(){ return this.datesRenduPrevues; }
	public void setDatesRenduPrevues(ArrayList<Date> datesRenduPrevues){ this.datesRenduPrevues = datesRenduPrevues; }
	public ArrayList<Date> getDatesRenduReeles(){ return this.datesRenduReeles; }
	public void setDatesRenduReeles(ArrayList<Date> datesRenduReeles){ this.datesRenduReeles = datesRenduReeles; }
	public boolean getEnCours(){ return this.enCours; }
	public void setEnCours(boolean enCours){ this.enCours = enCours; }

	public Date getDateRenduPrevueDernierSupport(){
		Date lastDate = new Date(0,0,0);
		for(int i=0;i<this.datesRenduPrevues.size();i++){
			if(this.datesRenduPrevues.get(i).compareTo(lastDate) > 0){
				lastDate = this.datesRenduPrevues.get(i);
			}
		}
		return lastDate;
	}
}