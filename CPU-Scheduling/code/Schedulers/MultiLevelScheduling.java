package Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MultiLevelScheduling {
	ArrayList<Process> RoundRobin = new ArrayList<Process>();
	ArrayList<Process> FCFS = new ArrayList<Process>();
	Map<String, Integer> individualWaitingTime = new HashMap<String, Integer>();
	Map<String, Integer> individualTurnAroundTime = new HashMap<String, Integer>();
	double avgWaitingTime, avgTurnAroundTime;
	int quantumTime, time, size;
	
	public MultiLevelScheduling(ArrayList<Process> Processes, int quantumTime){
		size = Processes.size();
		time = 0;
		this.quantumTime = quantumTime;
		for (int i = 0; i < Processes.size(); i++) {
			Processes.get(i).setProcessingTime(Processes.get(i).getBurstTime());
			if (Processes.get(i).getPriority() == 1) {
				RoundRobin.add(Processes.get(i));
			} else {
				FCFS.add(Processes.get(i));
			}
		}
		sortRoundRobin();
		sortFCFS();
		execute();
	}
	// Sorts the processes in queue 1 by arrival time
	void sortRoundRobin() {
		Process temp;
		for (int i = 0; i < RoundRobin.size(); i++) {
			for (int j = 0; j < RoundRobin.size()-1; j++) {
				if (RoundRobin.get(j).getArrivalTime() > RoundRobin.get(j + 1).getArrivalTime()) {
					temp = RoundRobin.get(j);
					RoundRobin.set(j, RoundRobin.get(j+1));
					RoundRobin.set(j+1, temp);
				}
			}
		}
	}
	// Sorts the processes in queue 2 by arrival time
	void sortFCFS() {
		Process temp;
		for (int i = 0; i < FCFS.size(); i++) {
			for (int j = 0; j < FCFS.size()-1; j++) {
				if (FCFS.get(j).getArrivalTime() > FCFS.get(j + 1).getArrivalTime()) {
					temp = FCFS.get(j);
					FCFS.set(j, FCFS.get(j+1));
					FCFS.set(j+1, temp);
				}
			}
		}
	}
	public boolean executeProcess(Process p, int queueNum) {
		int timeSlot = 1;
		System.out.print(p.getName() + " "); // print the name of the process that's currently executing
		while (timeSlot <= quantumTime) {
			time++;
			p.setProcessingTime(p.getProcessingTime()-1);
			timeSlot++;
			if (p.getProcessingTime() == 0) {
				// Calculate waiting and turn around time for process before removing it
				int turnAroundTime = time - p.getArrivalTime();
				int waitingTime = turnAroundTime -p.getBurstTime();
				individualTurnAroundTime.put(p.getName(), turnAroundTime);
				individualWaitingTime.put(p.getName(), waitingTime);
				if (queueNum == 1) {
					RoundRobin.remove(p);
				} else {
					FCFS.remove(p);
				}
				return true;
			}
			
			if (!RoundRobin.isEmpty() && queueNum == 2) {
				if (RoundRobin.get(0).getArrivalTime() <= time) {
					return true;
				}
			}
		}
		return false;
	}
	public void execute() {
		Process p = new Process();
		int index1 = 0, index2 = 0, queueNum = 0;
		boolean processAvailable = true;
		System.out.print("Execution Order: ");
		
		while (!RoundRobin.isEmpty()) {
			queueNum = 1;
			p = RoundRobin.get(index1);
			if (p.getArrivalTime() > time) {
				if (index1 == 0) {
					queueNum = 2;
					p = FCFS.get(index2);
					if (p.getArrivalTime() > time) {
						if (index2 == 0) {
							processAvailable = false;
						} else {
							queueNum = 2;
							index2 = 0;
							p = FCFS.get(index2);
						}
					} else {
						processAvailable = true;
					}
				} else {
					queueNum = 1;
					index1 = 0;
					p = RoundRobin.get(index1);
				}
			} else {
				processAvailable = true;
			}
			
			if (processAvailable) {
				if (queueNum == 1 && !executeProcess(p, queueNum)) {
					index1++;
				} else if (queueNum == 2 && !executeProcess(p, queueNum)) {
					index2++;
				}
			} else {
				time++;
			}
			
			if (index1 == RoundRobin.size()) {
				index1 = 0;
			}
			if (index2 == FCFS.size()) {
				index2 = 0;
			}
		}
		
		index2 = 0;
		while (!FCFS.isEmpty()) {
			p = FCFS.get(index2);
			if (p.getArrivalTime() <= time) {
				if (!executeProcess(p, 2)) {
					index2++;
				}
			} else {
				time++;
			}
			
			if (index2 == FCFS.size()) {
				index2 = 0;
			}
		}
		System.out.println("\n\n");
		WaitingTime();
		TurnAroundTime();
	}
	public void WaitingTime() {
		avgWaitingTime = 0;
		System.out.println("Waiting Time for each process:");
        for (Entry<String, Integer> entry : individualWaitingTime.entrySet()) {
            System.out.println("Name: " + entry.getKey() + ", Waiting Time: " + entry.getValue());
            avgWaitingTime += entry.getValue();
        }
        avgWaitingTime /= size;
        System.out.println();
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println();
	}
	
	public void TurnAroundTime() {
		avgTurnAroundTime = 0;
		System.out.println("Turn Around Time for each process:");
        for (Entry<String, Integer> entry : individualTurnAroundTime.entrySet()) {
            System.out.println("Name: " + entry.getKey() + ", Turn Around Time: " + entry.getValue());
            avgTurnAroundTime += entry.getValue();
        }
        avgTurnAroundTime /= size;
        System.out.println();
        System.out.println("Average Turn Around Time: " + avgTurnAroundTime);
        System.out.println();
	}

}
