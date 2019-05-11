package com.todolist.dto;

public class PerformanceDto {
	
private int doneTasksNumber;
private int inLateTasksNumber;
private long performanceIndex;

public PerformanceDto() {
		super();
}

public int getDoneTasksNumber() {
	return doneTasksNumber;
}

public void setDoneTasksNumber(int doneTasksNumber) {
	this.doneTasksNumber = doneTasksNumber;
}

public int getInLateTasksNumber() {
	return inLateTasksNumber;
}

public void setInLateTasksNumber(int inLateTasksNumber) {
	this.inLateTasksNumber = inLateTasksNumber;
}

public long getPerformanceIndex() {
	return performanceIndex;
}

public void setPerformanceIndex(long performanceIndex) {
	this.performanceIndex = performanceIndex;
}


}
