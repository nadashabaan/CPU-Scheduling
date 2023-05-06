package Schedulers;

import java.util.ArrayList;

public class ShortestJobFirst { 
	
	ShortestJobFirst (ArrayList<Process> proc){
		findavgTime(proc, proc.size());
	}
	
	// this will find the waiting time for all processes 
	static void findWaitingTime(ArrayList<Process> proc, int n, int wt[]){ 
		int rt[] = new int[n]; 
	
		// Copy the burst time into rt[] 
		for (int i = 0; i < n; i++) 
			rt[i] = proc.get(i).getBurstTime(); 
	
		int complete = 0, t = 0, minm = Integer.MAX_VALUE; 
		int shortest = 0, finish_time; 
		boolean check = false; 
	
		// Process until all processes gets 
		// completed 
		while (complete != n) { 
	
			// Find process with minimum 
			// remaining time among the 
			// processes that arrives till the 
			// current time
			for (int j = 0; j < n; j++){ 
				if ((proc.get(j).getArrivalTime() <= t) && (rt[j] < minm) && rt[j] > 0) { 
					minm = rt[j]; 
					shortest = j; 
					check = true; 
				} 
			} 
	
			if (check == false) { 
				t++; 
				continue; 
			} 
			System.out.println("Currently executing process: " + proc.get(shortest).getName());
			// Reduce remaining time by one 
			rt[shortest]--; 
	
			// Update minimum 
			minm = rt[shortest]; 
			if (minm == 0) 
				minm = Integer.MAX_VALUE; 
	
			// If a process gets completely 
			// executed 
			if (rt[shortest] == 0) { 
	
				// Increment complete 
				complete++; 
				check = false; 
	
				// Find finish time of current 
				// process 
				finish_time = t + 1; 
	
				// Calculate waiting time 
				wt[shortest] = finish_time - proc.get(shortest).getBurstTime() - proc.get(shortest).getArrivalTime(); 
				//System.out.println("shortest: " + wt[shortest]);
	
				if (wt[shortest] < 0) 
					wt[shortest] = 0; 
				
				System.out.println(proc.get(shortest).getName() + " waiting time: " + wt[shortest]);
			} 
			// Increment time 
			t++; 
		} 
	} 
	
	// Method to calculate turn around time 
	static void findTurnAroundTime(ArrayList<Process> proc, int n,  int wt[], int tat[])  { 
		// calculating turnaround time by adding 
		// bt[i] + wt[i] 
		for (int i = 0; i < n; i++) {
			tat[i] = proc.get(i).getBurstTime() + wt[i]; 
			System.out.println(proc.get(i).getName() + " turn arround time: " + tat[i]);
		}
	} 
	
	// Method to calculate average time 
	static void findavgTime(ArrayList<Process> proc, int n) { 
		int wt[] = new int[n], tat[] = new int[n]; 
		int total_wt = 0, total_tat = 0; 
	
		// Function to find waiting time of all 
		// processes 
		findWaitingTime(proc, n, wt); 
	
		// Function to find turn around time for 
		// all processes 
		findTurnAroundTime(proc, n, wt, tat); 
	
		// Display processes along with all 
		// details 
		//System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time"); 
	
		// Calculate total waiting time and 
		// total turnaround time 
		for (int i = 0; i < n; i++) { 
			total_wt = total_wt + wt[i]; 
			total_tat = total_tat + tat[i]; 
		} 
	
		System.out.println("Average waiting time = " + 
						(float)total_wt / (float)n); 
		System.out.println("Average turn around time = " + 
						(float)total_tat / (float)n); 
	} 
} 