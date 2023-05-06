package Schedulers;

import java.util.*;


class RoundRobin{


    public static void sortRoundRobin(ArrayList<Process> processes) {
        Process temp;
        for (int i = 0; i < processes.size(); i++) {
            for (int j = 0; j < processes.size()-1; j++) {
                if (processes.get(j).getArrivalTime() > processes.get(j + 1).getArrivalTime()) {
                    temp = processes.get(j);
                    processes.set(j, processes.get(j+1));
                    processes.set(j+1, temp);
                }
            }
        }
    }


    public static void FindAvg(ArrayList<Process> processes, int quantum,int c){
        sortRoundRobin(processes);

        ArrayList<Process> tempProc=new ArrayList<>();
        Queue<Process>ready = new LinkedList<>();
        Process executing; //currently executing process
        int Time=0;

        Process temp = processes.get(0);

        ready.add(temp);

        System.out.print("Execution order :\t");

        while (true) {

            executing = ready.poll();
            boolean finished= true;

            //execute
            if (executing.getBurstTime() <= quantum) {
                Time += executing.getBurstTime()+c;
                executing.setBurstTime(0);
                processes.remove(executing);
                executing.setExitTime(Time);
                tempProc.add(executing);
                System.out.print(executing.getName()+"\t");

            } else {
                Time += quantum+c;
                executing.setBurstTime(executing.getBurstTime() - quantum);
                if (executing.getBurstTime() != 0) {
                    System.out.print(executing.getName()+"\t");


                    finished=false;
                }
                else {
                    executing.setExitTime(Time);
                    tempProc.add(executing);
                    System.out.print(executing.getName()+"\t");
                    processes.remove(executing);
                }
            }

            for (Process p : processes) {
                    if (p.getArrivalTime() <= Time && p.getBurstTime()!=0 && !existInReadyQueue(p,ready)) {
                        if(p!=executing) {
                            ready.add(p);
                        }
                    }
                }

            if(!finished){ ready.add(executing); }


            if(ready.isEmpty())break;


        }

        System.out.println("\nProcess \t"+"Turn Around Time \t"+"Waiting Time\t");
        sortRoundRobin(tempProc);
        Double totalTA=0.;
        Double totalWA=0.;

        for(Process p : tempProc){
            int TA=p.getExitTime()-p.getArrivalTime();
            totalTA+=TA;
            int WA=TA - p.getFixedBurstTime();
            totalWA+=WA;
            System.out.println(p.getName()+"\t\t\t"+TA+"\t\t\t"+WA);

        }

        totalTA=totalTA/tempProc.size();
        totalWA=totalWA/tempProc.size();
        System.out.println("\n\nAverage Turn Around Time : "+totalTA+"\ttime unit");
        System.out.println("Average Waiting Time : "+totalWA+"\ttime unit");

    }

    public static boolean existInReadyQueue(Process p,Queue<Process> q){
        for(Process P : q){
            if(P==p)return true;
        }
        return  false;
    }
}
