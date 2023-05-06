package Schedulers;

import java.util.ArrayList;

public class PriorityScheduling {
	ArrayList<Process> processes;
	ArrayList<Integer> BurstTimes = new ArrayList<Integer>(); // Burst times of processes

	PriorityScheduling(ArrayList<Process> p) {
		processes = new ArrayList<Process>(p);
		for (int i = 0; i < p.size(); i++)
			BurstTimes.add(processes.get(i).getBurstTime());
	}

	/*
	 * 1. get minimum Priority 
	 * 2. Counter += process.get(index).BurstTime 
	 * 3. Calculate Turnaround and Waiting Turnaround = Counter-Arrival, Waiting = Turnaround - BurstTime 
	 * 4. remove process.remove(index);
	 */
	public void Schedule() {
		int FinishedProcesses = 0; // counter for number of finished processes
		int Counter = 0;
		int current_index = 0;
		int prv_index = 0;
		while (FinishedProcesses < processes.size()) {
			current_index = get_minPriority(Counter, BurstTimes);
			if (current_index != -1) {
				if (prv_index != current_index) {
					//System.out.print(processes.get(prv_index).getName() + '|');// print name of new process after switch
					// solve starvation problem
					if (processes.get(prv_index).getPriority() > 1) {
						int nPriority = processes.get(prv_index).getPriority();
						nPriority--;
						processes.get(prv_index).setPriority(nPriority);
					}
				}
				Counter++;
				// decreament BurstTime of process
				BurstTimes.set(current_index, BurstTimes.get(current_index) - 1);
				// remove if finished
				if (BurstTimes.get(current_index) == 0) {
					// calculate Trunaround and waiting
					processes.get(current_index).setTurnaroundTime(Math.abs(Counter - processes.get(current_index).getArrivalTime()));
					processes.get(current_index).setWaitingTime(processes.get(current_index).getTurnaroundTime() - processes.get(current_index).getBurstTime());
					FinishedProcesses++;
				}
			} else {
				Counter++;
				continue;
			}
			prv_index = current_index;
		}
		System.out.println("Waiting time for the processes");
		for (int i = 0; i < processes.size(); i++) {
			System.out.println(processes.get(i).getName() + " waiting name: " + processes.get(i).getWaitingTime());
		} 
		System.out.println("Average waiting time: " + getAvgWaitingTime(processes));
		System.out.println();

		System.out.println("Turn arround time for the processes");
		for (int i = 0; i < processes.size(); i++) {
			System.out.println(processes.get(i).getName() +" turn arround time : " + processes.get(i).getTurnaroundTime());
		} 
		System.out.println("Average turn arround time: " + getTaT(processes));		
	}

	public float getTaT(ArrayList<Process> R) {
		float totalTurnarround = 0.0f;
		for (int i = 0; i < R.size(); i++) {
			totalTurnarround += R.get(i).getTurnaroundTime();
		}

		float TurnaroundAvg = totalTurnarround / (float) R.size();
		return TurnaroundAvg;
	}

	public float getAvgWaitingTime(ArrayList<Process> R) {
		float totalWaiting = 0.0f;
		for (int i = 0; i < R.size(); i++) {
			totalWaiting += R.get(i).getWaitingTime();
		}

		float WaitingAvg = totalWaiting / (float) R.size();
		return WaitingAvg;
	}

	// Get minimum Priority of all processes
	int get_minPriority(int Timer, ArrayList<Integer> Bursts) {
		int min_index = 0;
		int min_Priority = Integer.MAX_VALUE;
		boolean Found = false;
		for (int i = 0; i < processes.size(); i++) {
			// if process was in interval from 0 to Timer and has the lowest priority set index to i
			if (processes.get(i).getPriority() <= min_Priority && processes.get(i).getArrivalTime() <= Timer && Bursts.get(i) > 0) {
				min_Priority = processes.get(i).getPriority();
				min_index = i;
				Found = true;
			}
		}
		if (Found)
			return min_index;
		else
			return -1;
	}
}
