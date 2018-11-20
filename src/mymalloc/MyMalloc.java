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
    MyMalloc() {

    }
    static int memSize = 100;
    static String mArray[] = new String [memSize];
    ArrayList<String[]> processInfo = new ArrayList<String[]>();
    

    void fillArray() {
        for (int i = 0; i < memSize; i++) {
            mArray[i] = "0x";
        }

    }

    void fillFirst(String pid, int p) {
        for (int i = 0; i < p; i++) {
            mArray[i] = pid;
        }
    }

    void fillMiddele(String pid, int p, int x) {
        for (int i = p; i < x; i++) {
            mArray[i] = pid;
        }
    }

    public static void main(String[] args) {
        MyMalloc m = new MyMalloc();
        m.fillArray();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Enter M to Allocate\nEnter F to Free\nEnter E to Exit");
            String in = s.nextLine();
            if (in.equals("M")) {
                System.out.println("Enter Process ID");
                String pid = s.nextLine();
                System.out.println("Enter Process size");
                int pSize = Integer.parseInt(s.nextLine());
                m.myMalloc(pid, pSize);
            } else if (in.equals("F")) {
                System.out.println("Enter Process ID");
                String pID =s.nextLine();
                m.free(pID);
            }
            else{
                System.out.println("Program Exit");
            break;}
        }

//        m.myMalloc(1, 10);
//        m.myMalloc(2, 20);
//        m.myMalloc(3, 30);
//        m.myMalloc(4, 30);
//        m.free(4);
    }

    void printArray() {
        System.out.println("Memory Allocation\n-------------------");
        for(int i=0;i<processInfo.size();i++){
            System.out.println("Process ID : "+processInfo.get(i)[0]+"\nProcess Size : "+processInfo.get(i)[1]);
            System.out.println("Starting Index : "+processInfo.get(i)[2]+"\nEnding Index : "+processInfo.get(i)[3]);
        }
        for (int i = 0; i < memSize; i++) {

            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
            System.out.print(mArray[i]);
        }
        System.out.println("\n----------------\n");
    }

    void myMalloc(String ID, int Size) {
        String pID = ID;
        int pSize = Size;
        System.out.println("PID " + pID + " pSize " + pSize + "\n----------------");
        int temp[][] = new int[100][2];
        int tempCount = 0;

        int sIndex = 0;
        int eIndex;
        for (int j = 0; j < memSize; j++) {
            if (mArray[j].equals("0x")) {
                for (int k = j; k < memSize; k++) {
                    if (!mArray[k].equals("0x")|| k == memSize - 1) {

                        temp[tempCount][0] = j;
                        if (k == (memSize - 1)) {
                            temp[tempCount][1] = k + 1;
                        } else {
                            temp[tempCount][1] = k;
                        }
                        System.out.println("J value " + temp[tempCount][0]);
                        System.out.println("K value " + temp[tempCount][1]);
                        tempCount++;
                        j = k;
                        break;
                    }
                }
            }
        }
        System.out.println("tmpcount " + tempCount);
        int minTemp[] = new int[tempCount];
        ArrayList<Integer> difTemp = new ArrayList<>();
        for (int n = 0; n < tempCount; n++) {
            int dif = temp[n][1] - temp[n][0];

            minTemp[n] = dif;
            System.out.println("diff " + dif);
            System.out.println("diff - pSize " +dif+"-"+pSize+"  "+ (dif - pSize));
            difTemp.add(dif - pSize);

        }

        int minValue = difTemp.get(0);
        for (int i = 0; i < difTemp.size(); i++) {
            if (difTemp.get(i) < minValue || (difTemp.get(i) == 0)) {
                minValue = difTemp.get(i);
            }
        }
        System.out.println("minval " + minValue);

        int bestFitIndex = difTemp.indexOf(minValue);
        int bsIndex = temp[bestFitIndex][0];
        int beIndex = temp[bestFitIndex][1];
        System.out.println("bsindex" + bsIndex);
        System.out.println("beindex" + beIndex);

        System.out.println("Actual S index " + bsIndex + "|| Actual E index " + (bsIndex + pSize));
        for (int i = bsIndex; i < bsIndex + pSize; i++) {
            mArray[i] = pID;

        }
        String processArray[]={pID,Integer.toString(pSize),Integer.toString(bsIndex),Integer.toString(bsIndex+pSize)};
        processInfo.add(processArray);

        printArray();
    }

    void free(String p) {
        String pID = p;
        for (int i = 0; i < memSize; i++) {
            if (mArray[i].equals(p)) {
                mArray[i] ="0x";
            }
        }
        printArray();

    }

}
