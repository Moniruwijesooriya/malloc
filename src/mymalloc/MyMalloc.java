/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymalloc;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author MONIRU CMY
 */
public class MyMalloc {

    /**
     * @param args the command line arguments
     */
    
    //initializing the memory size to 25000;
    int memorySize=25000;
    //declare a static variable freeMemSize to track the free memory
    static int freeMemSize = 25000;
    //static mArray to allocate and deallocate the processes
    static String mArray[] = new String[freeMemSize];
    //array to keep records of the processes
    ArrayList<String[]> processInfo = new ArrayList<String[]>();

    //filling the memory space 
    void fillArray() {
        for (int i = 0; i < memorySize; i++) {
            mArray[i] = "0x";
        }
    }

    public static void main(String[] args) {
        MyMalloc m = new MyMalloc();
        m.fillArray();
        Scanner s = new Scanner(System.in);

        while (true) {
            //commands
            System.out.println("Enter M to Allocate || Enter F to Free || Enter P to Print the Memory Allocation|| Enter E to Exit\nFree Space available : "+freeMemSize+"\n");
            String in = s.nextLine();
            if (in.equals("M")) {
                System.out.println("Enter Process ID");
                String pid = s.nextLine();
                System.out.println("Enter Process size");
                int pSize = Integer.parseInt(s.nextLine());
                m.myMalloc(pid, pSize);
                System.out.println();
            } else if (in.equals("F")) {
                System.out.println("Enter Process ID");
                String pID = s.nextLine();
                m.myFree(pID);
                System.out.println();
            } else if (in.equals("P")) {
                m.printArray();
            } else {
                System.out.println("Program Exit");
                break;
            }
        }
    }

    //print how the memory is  allocated
    void printArray() {
        System.out.println("Memory Allocation\n-------------------");
        for (int i = 0; i < processInfo.size(); i++) {
            System.out.println("Process ID : " + processInfo.get(i)[0] + "\nProcess Size : " + processInfo.get(i)[1]);
            System.out.println("Starting Index : " + processInfo.get(i)[2] + "\nEnding Index : " + processInfo.get(i)[3]);
            System.out.println("\n-------------------\n");
        }
        System.out.println("Free Memory : " + freeMemSize + "\n");
    }

    void myMalloc(String ID, int Size) {
        String pID = ID;
        int pSize = Size;
        //check whether there is free space to allocate the process
        if (freeMemSize >= pSize) {
            //array inside array to check the free space available
            int temp[][] = new int[25000][2];
            //tempcount means number of free blocks available in the memory
            int tempCount = 0;

            //starting index of the 
            int sIndex = 0;
            int eIndex;
            for (int j = 0; j < memorySize; j++) {
                if (mArray[j].equals("0x")) {
                    for (int k = j; k < memorySize; k++) {
                        if (!mArray[k].equals("0x") || k == memorySize - 1) {
                            //starting index of the free block
                            temp[tempCount][0] = j;
                            if (k == (memorySize - 1)) {
                                //ending index of the free block temp[tempCount][1]
                                temp[tempCount][1] = k + 1;
                            } else {
                                temp[tempCount][1] = k;
                            }
                            tempCount++;
                            j = k;
                            break;
                        }
                    }
                }
            }
           
            //arraylist difTemp to store the difference of the size of the free block and the size of the process
            ArrayList<Integer> difTemp = new ArrayList<>();
            for (int n = 0; n < tempCount; n++) {
                int dif = temp[n][1] - temp[n][0];

                difTemp.add(dif - pSize);

            }
            //obtain the minimun value of the differeces between the free block and the size of the process
            int minValue = difTemp.get(0);
            for (int i = 0; i < difTemp.size(); i++) {
                if (difTemp.get(i) < minValue || (difTemp.get(i) == 0)) {
                    minValue = difTemp.get(i);
                }

            }
            //using bestfit algorithm 
            int bestFitIndex = difTemp.indexOf(minValue);
            //starting address of the block which is allocated in the memory
            int bsIndex = temp[bestFitIndex][0];
            //ending address of the block which is allocated in the memory
            int beIndex = temp[bestFitIndex][1];

            //filling the memory array with the process
            for (int i = bsIndex; i < bsIndex + pSize; i++) {
                mArray[i] = pID;
            }
            //calculating the available free memory
            freeMemSize = freeMemSize - pSize;
            //storing process info ->process ID,process size,starting address of the block allocated and ending address of the block allocated
            String processArray[] = {pID, Integer.toString(pSize), Integer.toString(bsIndex), Integer.toString(bsIndex + pSize - 1)};
            processInfo.add(processArray);
            System.out.println("Starting Index : " + bsIndex + "\nEnding Index : " + (bsIndex + pSize - 1));

        } else {
            System.out.println("Not enough memory space to allocate your process !");
        }

    }

    void myFree(String p) {
        String pID = p;
        //searching process in the memory array
        for (int i = 0; i < memorySize; i++) {
            if (mArray[i].equals(p)) {
                mArray[i] = "0x";
            }
        }
        //removing the process from the arraylist which contains the processes
        for (int i = 0; i < processInfo.size(); i++) {
            if (processInfo.get(i)[0].equals(p)) {
                System.out.println("Memory Space removed :" + processInfo.get(i)[1] + "\n");
                freeMemSize += Integer.parseInt(processInfo.get(i)[1]);
                processInfo.remove(i);
            } else if (i == processInfo.size() - 1) {
                System.out.println("Process ID you entered does not exist!");

            }
        }

    }

}
