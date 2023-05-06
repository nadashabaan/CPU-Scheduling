package Schedulers;

import java.util.ArrayList;
import java.util.Scanner;

import AG.AGScheduling;

public class Main {

	static public void main (String[] arg) {
		int numberOfProcesses;
		int RRQuantum=0,co=0;

		ArrayList<Process> Processes = new ArrayList<Process>();
		ArrayList<Integer> Quantum = new ArrayList<Integer>();

		Scanner input = new Scanner(System.in);


		System.out.println("Select the Scheduler you want to use:"
				+ "\n[1] Preemptive Shortest- Job First (SJF)"
				+ "\n[2] Round Robin (RR)"
				+ "\n[3] Preemptive Priority Scheduling."
				+ "\n[4] AG Scheduling.");
		int option = input.nextInt();
		 if(option == 4)  {int[] bt = new int[5];
		int[] at = new int[5];
		int[] pr = new int[5];
		int[] q = new int[5];
		int[] q2 = new int[5];
		int[] ex = new int[5];
		
		bt[1]=17;
		bt[2]=6;
		bt[3]=11;
		bt[4]=4;
		
		ex[1]=17;
		ex[2]=6;
		ex[3]=11;
		ex[4]=4;
		
		at[1]=0;
		at[2]=2;
		at[3]=5;
		at[4]=15;
		
		pr[1]=4;
		pr[2]=7;
		pr[3]=3;
		pr[4]=6;
		
		q[1]=7;
		q[2]=9;
		q[3]=4;
		q[4]=6;
		
		q2[1]=7;
		q2[2]=9;
		q2[3]=4;
		q2[4]=6;
		
		AGScheduling obj= new AGScheduling(bt, at, pr, q, q2, 4,ex);
		obj.schedule();}
		 else {
		System.out.print("Enter the number of processes: ");
		numberOfProcesses = input.nextInt();
		
		System.out.println();
		
		//get rr time
		if( option==2){
			System.out.print("Enter Round Robin Time Quantum: ");
			RRQuantum = input.nextInt();
			System.out.println();
			System.out.print("Enter context: ");
			 co = input.nextInt();
			System.out.println();
		}
		
		// Read Processes
		for(int i = 0 ; i < numberOfProcesses; ++i) {
			input = new Scanner(System.in);
			Process p = new Process();

			System.out.print( "Process " + (i+1) + " name: ");
			p.setName(input.nextLine());

			System.out.print( "Process " + (i+1) + " arrival time: ");
			p.setArrivalTime(input.nextInt()) ;

			System.out.print( "Process " + (i+1) + " burst time: ");
			int burstT=input.nextInt();
			p.setBurstTime(burstT);
			p.setFixedBurstTime(burstT);

			//get queue number for multi level and priority
			if(option==3) {
				if(option == 4)
					System.out.print( "Process " + (i+1) + " queue number: ");
				else
					System.out.print( "Process " + (i+1) + " priority number: ");
				p.setPriority(input.nextInt());
			}
			System.out.println("===========================\n");
			
			Processes.add(p);
		}
		System.out.println();


		if(option == 1) {
			ShortestJobFirst SJF = new ShortestJobFirst(Processes);

		}
		else if(option == 2) {
			
			RoundRobin.FindAvg(Processes,RRQuantum,co);

		}
		else if(option == 3) {
			PriorityScheduling pps = new PriorityScheduling(Processes);
			pps.Schedule();

		}
		
		else if (option == 5) {
			System.exit(0);
		}
		else {
			System.out.println("Invalid option!");
		}}
	}
}
