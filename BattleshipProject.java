//This is the Battleship Project

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleshipProject {
    static String[][] field1 = new String[11][11];
    static String[][]foggedField1=new String[11][11];
    static int [][][]coordinates1=new int[2][5][2];
    static int []len1={5,4,3,3,2};
    static ArrayList<Integer> al1=new ArrayList<>();
    static int totalLen1=17;
    static Integer count1= 0;
    static String[][] field2 = new String[11][11];
    static String[][]foggedField2=new String[11][11];
    static int [][][]coordinates2=new int[2][5][2];
    static int []len2={5,4,3,3,2};
    static ArrayList<Integer> al2=new ArrayList<>();
    static int totalLen2=17;
    static Integer count2= 0;
    public static void main(String[] args) {


        //Setting the first row of field
        field1[0][0] = " ";
        foggedField1[0][0]=" ";
        field2[0][0] = " ";
        foggedField2[0][0]=" ";
        for (int i = 1; i <= 10; i++) {
            field1[0][i] = i + "";
            foggedField1[0][i]=i+"";
            field2[0][i] = i + "";
            foggedField2[0][i]=i+"";
        }

        //Setting the first column of field
        int firstChar = 65;
        for (int i = 1; i <= 10; i++) {
            field1[i][0] = (char) firstChar + "";
            foggedField1[i][0]=(char)firstChar+"";
            field2[i][0] = (char) firstChar + "";
            foggedField2[i][0]=(char)firstChar+"";
            firstChar++;
        }

        //Filling the unknown area on the opponent's field with ~
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                field1[i][j] = "~";
                foggedField1[i][j]="~";
                field2[i][j] = "~";
                foggedField2[i][j]="~";
            }
        }
        gameFacilitator();
    }
    public static void gameFacilitator(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Player 1, place your ships on the game field");
        printArray(field1);
        askCoordinates(coordinates1,1,field1);
        promptEnterKey();
        System.out.println("Player 2, place your ships to the game field");
        printArray(field2);
        askCoordinates(coordinates2,2,field2);
        System.out.println("The game starts!");
        int playerCounter=1;
        while(totalLen1>0 && totalLen2>0){
            if(playerCounter%2!=0){
                System.out.println("Player 1, it's your turn:");
                printFoggedField(foggedField2);
                System.out.println("------------------");
                enterShot(foggedField2,field2,len2,coordinates2,al2,1);
            }else{
                System.out.println("Player 2, it's your turn:");
                printFoggedField(foggedField1);
                System.out.println("------------------");
                enterShot(foggedField1,field1,len1,coordinates1,al1,2);
            }
            playerCounter++;
            if(totalLen2>0 && totalLen1>0){
                promptEnterKey();
            }
        }

    }
    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("You were asked to press the enter key!");
            e.printStackTrace();
        }
    }

    public static void askCoordinates(int [][][]coordinates, int count, String[][] field) {

        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        takeInput(5,coordinates,count,field);
        printArray(field);
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        takeInput(4,coordinates,count,field);
        printArray(field);
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        takeInput(3,coordinates,count,field);
        printArray(field);
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        takeInput(3,coordinates,count,field);
        printArray(field);
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        takeInput(2,coordinates,count,field);
        printArray(field);

    }

    public static void takeInput(int len,int [][][]coordinates, int count, String[][] field) {

        Scanner sc = new Scanner(System.in);
        String loc1,loc2;
        loc1="";
        loc2="";

        loc1 = sc.next();
        loc2 = sc.next();
        int row1 = Integer.parseInt((int) loc1.charAt(0) + "") - 64;
        int col1 = Integer.parseInt(loc1.substring(1));
        int row2 = Integer.parseInt((int) loc2.charAt(0) + "") - 64;
        int col2 = Integer.parseInt(loc2.substring(1));


        if (row1 != row2 && col1 != col2) {
            checkLocation(len,coordinates,count,field);
        }
        else if (row1 >= 11 || row2 >= 11 || col1 >= 11 || col2 >= 11) {

            checkLocation(len,coordinates,count,field);
        }else{
            if(checkLength(row1, col1, row2, col2, len)){
                checkOccupation(row1, col1, row2, col2, len,coordinates,count,field);
            }else{
                takeInput(len,coordinates,count,field);
            }

        }
    }

    public static void checkLocation(int len,int [][][]coordinates, int count, String[][] field) {
        System.out.println("Error! Wrong ship location! Try again:");
        takeInput(len,coordinates,count,field);
    }

    public static boolean checkLength(int r1, int c1, int r2, int c2, int len) {
        int sameCoordinate;
        int lenCoordinate1;
        int lenCoordinate2;
        if (r1 == r2) {
            sameCoordinate = r1;
            lenCoordinate1 = c1;
            lenCoordinate2 = c2;
        } else {
            sameCoordinate = c1;
            lenCoordinate1 = r1;
            lenCoordinate2 = r2;
        }
        int diff = Math.abs(lenCoordinate1 - lenCoordinate2)+1;
        if (diff != len) {
            System.out.println("Error! Wrong length of the Submarine! Try again:");
            return false;
        }else{
            return true;
        }
    }

    public static void checkOccupation(int r1, int c1, int r2, int c2, int len,int [][][]coordinates, int count, String[][] field) {
        if (r1 == r2) {
            int i, j;
            if (c1 > c2) {
                i = c2;
                j = c1;
            } else {
                i = c1;
                j = c2;
            }
            int flag=0;
            for (int k = i; k <= j; k++) {
                if (field[r1][k].equals("O")) {

                    flag=1;
                    checkLocation(len,coordinates,count,field);
                }
            }
            if(flag==0){
                checkVicinity(r1,c1,r2,c2,len,coordinates,count,field);
            }
        } else {
            int i, j;
            if (r1 > r2) {
                i = r2;
                j = r1;
            } else {
                i = r1;
                j = r2;
            }
            int flag=0;
            for (int k = i; k <= j; k++) {
                if (field[k][c1].equals("O")) {

                    flag=1;
                    checkLocation(len,coordinates,count,field);
                }
            }
            if(flag==0){
                checkVicinity(r1,c1,r2,c2,len,coordinates,count,field);
            }
        }
    }

    public static void checkVicinity(int r1, int c1, int r2, int c2, int len,int [][][]coordinates, int count, String[][] field) {
        int row_start,col_start,row_end,col_end;
        int i, j;
        if (r1 == r2) {

            if (c1 > c2) {
                i = c2;
                j = c1;
            } else {
                i = c1;
                j = c2;
            }
            if(r1-1>=1){
                row_start=r1-1;
            }else{
                row_start=r1;
            }

            if(r1+1<=10){
                row_end=r1+1;
            }else{
                row_end=r1;
            }

            if(i-1>=1){
                col_start=i-1;
            }else{
                col_start=i;
            }

            if(j+1<=10){
                col_end=j+1;
            }else{
                col_end=j;
            }
            int flag=0;
            for(int m=row_start;m<=row_end;m++){
                for(int n=col_start;n<=col_end;n++){
                    if(field[m][n].equals("O")){
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        flag=1;
                        break;
                    }
                }
            }

            if(flag==0){
                for(int k=i;k<=j;k++){
                    field[r1][k]="O";
                }
                if(count==1){
                    coordinates[0][count1][0]=r1;
                    coordinates[0][count1][1]=i;
                    coordinates[1][count1][0]=r2;
                    coordinates[1][count1][1]=j;
                    count1++;
                }else{
                    coordinates[0][count2][0]=r1;
                    coordinates[0][count2][1]=i;
                    coordinates[1][count2][0]=r2;
                    coordinates[1][count2][1]=j;
                    count2++;
                }
            }else{
                takeInput(len,coordinates,count,field);
            }

        }else{
            if (r1 > r2) {
                i = r2;
                j = r1;
            } else {
                i = r1;
                j = r2;
            }
            if(i-1>=1){
                row_start=i-1;
            }else{
                row_start=i;
            }

            if(j+1<=10){
                row_end=j+1;
            }else{
                row_end=j;
            }

            if(c1-1>=1){
                col_start=c1-1;
            }else{
                col_start=c1;
            }

            if(c1+1<=10){
                col_end=c1+1;
            }else{
                col_end=c1;
            }
            int flag=0;
            for(int m=row_start;m<=row_end;m++){
                for(int n=col_start;n<=col_end;n++){
                    if(field[m][n].equals("O")){

                        System.out.println("Error! You placed it too close to another one. Try again:");
                        flag=1;
                        break;
                    }
                }
            }
            if(flag==0){
                for(int k=i;k<=j;k++){
                    field[k][c1]="O";
                }
                if(count==1){
                    coordinates[0][count1][0]=i;
                    coordinates[0][count1][1]=c1;
                    coordinates[1][count1][0]=j;
                    coordinates[1][count1][1]=c2;
                    count1++;
                }else{
                    coordinates[0][count2][0]=i;
                    coordinates[0][count2][1]=c1;
                    coordinates[1][count2][0]=j;
                    coordinates[1][count2][1]=c2;
                    count2++;
                }
            }else{
                takeInput(len,coordinates,count,field);
            }


        }
    }

    public static void printArray(String [][]field){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                System.out.print(field[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void printFoggedField(String [][]foggedField){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                System.out.print(foggedField[i][j]+" ");
            }
            System.out.println();
        }
    }


    public static void enterShot(String [][]foggedField,String [][]field,int []len,int [][][]coordinates, ArrayList<Integer> al,int i){
        String shot="";
        Scanner sc= new Scanner(System.in);
        shot=sc.next();
        int row = Integer.parseInt((int) shot.charAt(0) + "") - 64;
        int col = Integer.parseInt(shot.substring(1));
        if(row<1 || row>10){
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            enterShot(foggedField,field,len,coordinates,al,i);
        }
        else if(col<1 || col>10){
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            enterShot(foggedField,field,len,coordinates,al,i);
        }else{
            checkPosition(row,col,foggedField,field,len,coordinates,al,i);
        }
    }

    public static void checkPosition(int r,int c,String [][]foggedField,String [][]field,int []len,int [][][]coordinates, ArrayList<Integer> al,int i){
        if(field[r][c].equals("~")){
            field[r][c]="M";
            foggedField[r][c]="M";
            printFoggedField(foggedField);
            System.out.println("You missed.");

        }
        else if(field[r][c].equals("O")){
            field[r][c]="X";
            foggedField[r][c]="X";
            if(i==1){
                totalLen2--;
            }else{
                totalLen1--;
            }
            printFoggedField(foggedField);
            determineDisplayMessage(r,c,field,len,coordinates,al,i);
        }else{
            System.out.println("You already hit that ship!");

        }
    }

    public static void determineDisplayMessage(int r,int c,String [][]field, int []len,int [][][]coordinates,ArrayList<Integer> al,int player){
        if(r>=coordinates[0][0][0] && r<=coordinates[1][0][0] && c>=coordinates[0][0][1] && c<=coordinates[1][0][1]){
            int val=len[0];
            len[0]=--val;
        }else if(r>=coordinates[0][1][0] && r<=coordinates[1][1][0] && c>=coordinates[0][1][1] && c<=coordinates[1][1][1]){
            int val=len[1];
            len[1]=--val;
        }else if(r>=coordinates[0][2][0] && r<=coordinates[1][2][0] && c>=coordinates[0][2][1] && c<=coordinates[1][2][1]){
            int val=len[2];
            len[2]=--val;
        }else if(r>=coordinates[0][3][0] && r<=coordinates[1][3][0] && c>=coordinates[0][3][1] && c<=coordinates[1][3][1]){
            int val=len[3];
            len[3]=--val;
        }else if(r>=coordinates[0][4][0] && r<=coordinates[1][4][0] && c>=coordinates[0][4][1] && c<=coordinates[1][4][1]){
            int val=len[4];
            len[4]=--val;
        }
        int flag=0;
        for(int i=0;i<len.length;i++){
            if(len[i]==0 && !al.contains(i)){
                al.add(i);
                if(al.size()<5){
                    System.out.println("You sank a ship of opponent!");
                    flag=1;
                    break;
                }else {
                    if(player==1){
                        System.out.println("Congratulations! Player 1. You sank the last ship of Player 2.");
                    }else{
                        System.out.println("Congratulations! Player 2. You sank the last ship of Player 1.");
                    }
                    printArray(field);
                    flag=1;
                    break;
                }
            }
        }
        if(flag==0){
            System.out.println("You hit a ship!");
        }

    }
}

