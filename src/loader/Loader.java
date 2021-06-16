/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Loader {  
 static String TEXT="";
 static String label;
 static ArrayList<String> source = new ArrayList<String>();
  public static void main(String[] args) throws FileNotFoundException, IOException {
  Scanner input =new Scanner(System.in);
//take the program name from the user 
 String ProgName= input.next();
 //check if the program name is right or not
 if(check(ProgName)){
    //enter the new start address
 String labelv=input.next();
    //to get the differnce between the enter value to shift and the start adderss
    getlabel(labelv);
    //to get text record from file
    readTEXT();
    //to get modifiers form file
    readMOd(); 
    //to get start add ,length of each text record 
    storeSource();
    //to display the simulation of the memory after shifting
    display();
    
 
 }
 //if the program name isn't right 
 else{
     System.out.println("the name of program isn't true");  
 }
  }
  //function to check if the program name is right or not
static boolean check(String name) throws FileNotFoundException, IOException{
    //reading from the file
    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\java\\input.txt"));
    //readding the header record
  String checkName=br.readLine();
// to get the program name
  checkName=checkName.substring(1,7);
//to remove any spaces in the name
  checkName=checkName.replaceAll("\\s","");
// check if the name of program is right or not and return true or false
  if(checkName.equals(name)){
  return true;
 }

   else 
      return false;
  }
//function to get the differnce between the enter value to shift and the start adderss
static void getlabel(String labelv) throws FileNotFoundException, IOException{
    //reading the content of the file 
BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\java\\input.txt"));
//read the first line of the file (Header record) 
String Start=br.readLine();
//get the start address of the program
 Start=Start.substring(7,13);
 //get the differce between the start address and the shifting address 
int num=Integer.parseInt(labelv,16)-Integer.parseInt(Start,16);
//convert the label value to hex
 label=Integer.toHexString(num);
 
}
//function to get the modifiers 
static void readMOd() throws FileNotFoundException, IOException{
     //read the content of file
    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\java\\input.txt"));
      String a;
      //looping to get the content of file 
  while((a=br.readLine())!=null){
     //check if the first char of the line is M or not (Modifiers)
      if(a.charAt(0)=='M'){
       //Starting location of the address field to be modified  
    String m=a.substring(1,7);
    //get the half bites 
    String half=a.substring(7,9);
    //convert it to decimal from hex string
int loc= Integer.parseInt(m,16);
//convert it to integer
int halfi= Integer.parseInt(half); 
//function to change the object code of the modifcation
changeobCode(loc,halfi);
    }
}
   
}
//read the text records 
static void readTEXT() throws FileNotFoundException, IOException{
     //Reading Content from the file 
    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\java\\input.txt"));
    String l;
    //looping to get the content of file 
        while((l=br.readLine())!=null)   
        {
//get the all object code in one string 
            if(l.charAt(0)=='T'){
                TEXT=TEXT+l.substring(9);
                //delete all spaces 
                TEXT=TEXT.replaceAll("\\s","");
            }
            
        }  
 } 
//function to get change the object code
static void changeobCode(int startloc,int half){
//get the object code that will be change     
String k=TEXT.substring(2*startloc+1,(2*startloc)+half+1);
//change the object code by add the label value
String l=Integer.toHexString(Integer.parseInt(k,16)+Integer.parseInt(label,16));
//if the length is smaller than 6 bit
if(l.length()<half){
l="0"+l;
}
//replace new object code int text
TEXT=TEXT.replaceFirst(k,l);


}
//get start address and length for each text record 
static void storeSource() throws FileNotFoundException, IOException{
     //Reading Content from the file
     BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\java\\input.txt"));
     
  String Source;
  while((Source=br.readLine())!=null)
        {   if(Source.charAt(0)=='T'){
                  //add address to arraylist
                source.add(Source.substring(1,7));
                //add address to length
                source.add(Source.substring(7,9));
            }}  
  

}
//to display the simulation of the memory 
static void display(){
    
int StartAdd,length;

for (int i=0;i<source.size();i+=2) {
   //get the start address
StartAdd=Integer.parseInt(source.get(i),16)+Integer.parseInt(label,16);
 //get the length of the text record
length=Integer.parseInt(source.get(i+1),16);
  //printing the memory
 for(int k=0;k<length;k+=2){
 System.out.println(Integer.toHexString(StartAdd+k)+"   "+TEXT.substring(k,k+2));
 }
 //deleting all the object code that printed 
TEXT=TEXT.substring(length);

}

}
}




