package com.todolist.dto;

public class DtoPerf {
	
private int nbreTachesEffectuees;
private int nbreTachesEnRetard;
private long indicePerformance;

public DtoPerf() {
		super();
}

public int getNbreTachesEffectuees() {
	return nbreTachesEffectuees;
}

public void setNbreTachesEffectuees(int nbreTachesEffectuees) {
	this.nbreTachesEffectuees = nbreTachesEffectuees;
}

public int getNbreTachesEnRetard() {
	return nbreTachesEnRetard;
}

public void setNbreTachesEnRetard(int nbreTachesEnRetard) {
	this.nbreTachesEnRetard = nbreTachesEnRetard;
}

public long getIndicePerformance() {
	return indicePerformance;
}

public void setIndicePerformance(long indicePerformance) {
	this.indicePerformance = indicePerformance;
}

}
