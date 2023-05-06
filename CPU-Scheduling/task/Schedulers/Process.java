package Schedulers;
import java.util.ArrayList;

public class Process implements Comparable<Process> {
	private String Name; 
	private int BurstTime;
	private int FixedBurstTime;
	private int ArrivalTime;
	private int Priority;
	private int WaitingTime;
	private int remainingTime;
	private int TurnaroundTime;
	private int ExitTime;
	// this will be used in AG
	//private int QuantumAG; 

	private int QuantumTime; // this will be used in RR Scheduling
	ArrayList<Integer> HistoryOfQuantum = new ArrayList<Integer>(); 
	// you may need it to store all updates of the quantum time.
	
	private int processingTime;
	private int StartTime; 
	private int LastTimeAged;
	private int EndTime;
	
	// empty constructor 
	public Process()
	{}
	
	// copy constructor
	public Process(Process P) {
		Name = P.getName();
		BurstTime = P.getBurstTime();
		FixedBurstTime = P.getFixedBurstTime();
		remainingTime = P.getBurstTime();
		ArrivalTime = P.getArrivalTime();
		Priority = P.getPriority();
		StartTime = -1; 
		LastTimeAged = ArrivalTime;
		// QuantumAG=P.
		setProcessingTime(BurstTime);		
	}
	
	// parameterized constructor 
	public Process(String Name, int arrivalTime, int burstTime , int queueNumber) {
		this.Name = Name;
		this.BurstTime = burstTime;
		this.FixedBurstTime=burstTime;
		remainingTime = burstTime;
		this.ArrivalTime = arrivalTime;
		this.Priority = queueNumber;	
		StartTime = -1; 
		LastTimeAged = ArrivalTime;
		setProcessingTime(BurstTime);
		
	}

	void Execute(){
		System.out.println("Process " + Name );
		processingTime--;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setBurstTime(int burstTime) {
		BurstTime = burstTime;
	}
	
	public void resetRemainingTime(){
        this.remainingTime = this.BurstTime;
    }
	
    public int getRemainingTime() {
        return remainingTime;
    }
    
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    
    public void decrementRemainingTime(){
        this.remainingTime--;
    }

	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
		LastTimeAged = ArrivalTime;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

	public void setWaitingTime(int waitingTime) {
		
		WaitingTime = waitingTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		TurnaroundTime = turnaroundTime;
	}

	public String getName() {
		return Name;
	}

	public int getBurstTime() {
		return BurstTime;
	}

	public int getArrivalTime() {
		return ArrivalTime;
	}

	public int getPriority() {
		return Priority;
	}

	public int getWaitingTime() {
		return WaitingTime;
	}

	public int getTurnaroundTime() {
		return TurnaroundTime;
	}
	public void printProcess() {
		System.out.println("Name : " + Name + "\nArrival Time : " + ArrivalTime + "\nBurstTime : " + BurstTime
				+ "\nPriority : " + Priority + "\nWaiting Time : "+ WaitingTime 
				+ "\nTurn Arround Time : " +  TurnaroundTime);
	}

	public int getQuantumTime() {
		return QuantumTime;
	}

	public void setQuantumTime(int quantumTime) {
		HistoryOfQuantum.add(QuantumTime);
		QuantumTime = quantumTime;
	}

	public int getStartTime() {
		return StartTime;
	}

	public void setStartTime(int startTime) {
		StartTime = startTime;
	}


	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}



	@Override
	public int compareTo(Process o) {
		// TODO Auto-generated method stub
		return this.getArrivalTime() - o.getArrivalTime();
	}

	public int getLastTimeAged() {
		return LastTimeAged;
	}

	public void setLastTimeAged(int lastTimeAged) {
		LastTimeAged = lastTimeAged;
	}

	public int getEndTime() {
		return EndTime;
	}

	public void setEndTime(int endTime) {
		EndTime = endTime;
	}


    public void incrementWaitingTime(){
        this.WaitingTime++;
    }
    
    public void decrementPriority(){
        --Priority;
    }


	public int getExitTime() {
		return ExitTime;
	}

	public void setExitTime(int exitTime) {
		ExitTime = exitTime;
	}

	public int getFixedBurstTime() {
		return FixedBurstTime;
	}

	public void setFixedBurstTime(int fixedBurstTime) {
		FixedBurstTime = fixedBurstTime;
	}
//	public int getQuantumAG() {
//		return QuantumAG;
//	}
//
//	public void setQuantumAG(int quantumAG) {
//		QuantumAG = quantumAG;
//	}
}
