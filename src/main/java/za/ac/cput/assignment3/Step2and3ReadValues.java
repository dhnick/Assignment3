/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Damone Hartnick 219093717
 */

//START OF STEP 2
public class Step2and3ReadValues {
    
    
    //Create  ArrayList
    private ArrayList<Customer> CustomerContent = new ArrayList<>();
    private ArrayList<Supplier> SupplierContent = new ArrayList<>();
    ObjectInputStream Input;
    
    //Write to file
    FileWriter writer;
    BufferedWriter buffWriter;
      
    //Open files
    public void openFile() {
        try{
 
             Input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
         } 
         catch(IOException e) {
             System.out.println("Error!! Could not open file!" + e.getMessage());
         }           
    }
    
     //Close  files
    public void closeFile() {
        try{
           
            Input.close();
        }
        catch(IOException e) {
            System.out.println("Error!! Could not close file!" + e.getMessage());

        }
    
    }
    
     //Read Files
    public void readFile() {
       
        try{
           while(true){
               Object line = Input.readObject();
               String x ="Customer";
               String y = "Supplier";
               String name = line.getClass().getSimpleName();
               if ( name.equals(x)){
                   CustomerContent.add((Customer)line);
               } else if ( name.equals(y)){
                   SupplierContent.add((Supplier)line);
               } else {
                   System.out.println("It didn't work");
               }
           } 
            }
        
         catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        
        catch(IOException | ClassNotFoundException e) {
            System.out.println("Error!! Could not read file!" + e.getMessage());
  
        }
        
        finally {
           closeFile();
                System.out.println("*** file has been closed ***");
        }
       
    SortCustomerContent();
    sortSupplierContents();
    
        
    }
    
    // sort customer content 
    public void SortCustomerContent () {
    
     String[] sortContent = new String[CustomerContent.size()];
        ArrayList<Customer> Array= new ArrayList<Customer>();
        int length = CustomerContent.size();
        for (int x = 0; x < length; x++) {
            sortContent[x] = CustomerContent.get(x).getStHolderId();
        }
        Arrays.sort(sortContent);
        
        for (int s = 0; s < length; s++) {
            for (int a = 0; a < length; a++) {
                if (sortContent[s].equals(CustomerContent.get(a).getStHolderId())){
                    Array.add(CustomerContent.get(a));
                }
            }
        }
        CustomerContent.clear();
        CustomerContent = Array;
    
  
    
    }
    
    
    // Determining the age of each customer
     public int getAge(String dob){
        String[] seperation = dob.split("-");
        
        LocalDate childbirth = LocalDate.of(Integer.parseInt(seperation[0]), Integer.parseInt(seperation[1]), Integer.parseInt(seperation[2]));
        LocalDate present = LocalDate.now();
        Period diff = Period.between(childbirth, present);
        int age = diff.getYears();
        return age;
    }
    
    // Format date
      public String formatDob(Customer dob){
        LocalDate dateOfBirthToFormat = LocalDate.parse(dob.getDateOfBirth());
        DateTimeFormatter changeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dateOfBirthToFormat.format(changeFormat);
    }
    
    //Write to textfile - customerOutFile.txt
       public void DisplayCustomerTextFile(){
        try{
            writer = new FileWriter("customerOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================CUSTOMERS========================================"));
            
            buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s\n", "ID","Name","Surname","Date of birth","Age"));
             buffWriter.write(String.format("%s\n","================================================================================"));
            for (int x = 0; x < CustomerContent.size(); x++) {
                buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", CustomerContent.get(x).getStHolderId(), CustomerContent.get(x).getFirstName(), CustomerContent.get(x).getSurName(), formatDob(CustomerContent.get(x)),getAge(CustomerContent.get(x).getDateOfBirth())));
            }
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n",Customerrent()));
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    
    
    // Determining the number of customers who can and can't rent
      public String Customerrent(){
        int length = CustomerContent.size();
        int canRent = 0;
        int cannotRent = 0;
        for (int x = 0; x < length; x++) {
            if (CustomerContent.get(x).getCanRent()){
                canRent++;
            }else {
                cannotRent++;
            }
        }
        String line = "Number of customers who can rent : "+ '\t' + canRent + '\n' + "Number of customers who cannot rent : "+ '\t' + cannotRent;
        return line;
    }  
    
   // END OF STEP 2
      
   //START OF STEP 3  
      
      
      
      //Sort contents of suppliers
    public void sortSupplierContents(){
        String[] sortContent = new String[SupplierContent.size()];
        ArrayList<Supplier> Array= new ArrayList<Supplier>();
        int length = SupplierContent.size();
        for (int x = 0; x < length; x++) {
            sortContent[x] = SupplierContent.get(x).getName();
        }
        Arrays.sort(sortContent);
        
        for (int s = 0; s < length; s++) {
            for (int a = 0; a < length; a++) {
                if (sortContent[s].equals(SupplierContent.get(a).getName())){
                    Array.add(SupplierContent.get(a));
                }
            }
        }
        SupplierContent.clear();
        SupplierContent = Array;
    }
    
    
 //   
  public void DisplaySupplierTextFile(){
        try{
            writer = new FileWriter("supplierOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================SUPPLIERS=========================================="));
           
            buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", "ID","Name","Prod Type","Description"));
            buffWriter.write("==============================================================================\n");
            for (int i = 0; i < SupplierContent.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", SupplierContent.get(i).getStHolderId(), SupplierContent.get(i).getName(), SupplierContent.get(i).getProductType(),SupplierContent.get(i).getProductDescription()));
            }
            System.out.println("SupplierOutFile.txt created .");
            
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }  
      
    
    // END OF STEP 3
    
    
  // Main method  
public static void main(String args[])  {
    
Step2and3ReadValues obj=new Step2and3ReadValues(); 

obj.openFile();
obj.readFile();
obj.closeFile();
obj.DisplayCustomerTextFile();
obj.DisplaySupplierTextFile();
obj.SortCustomerContent();
obj.sortSupplierContents();


     } 
    
}
