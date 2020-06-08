/*
Aziz Makda
CS 315-1 Modern Database Management  
Database includes three inserts, one delete, one search, two updates and one view.
*/

// Import: Connection, DriverManager, Statement, ResultSet, SQLException, and Scanner
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.*;

import java.util.Scanner;

public class Careplus 
{
    // Create static instance variables: Scanner, Statement, Connection, ResultSet
    public static Scanner input = new Scanner( System.in );
    public static Statement statement;
    public static Connection connection;
    public static ResultSet rs = null;

    public static void main( String[] args ) throws Exception
    {
       // Create a connection object and initialize it to null
       connection = null;
       
       // We need to use a try-catch block, which should catch an SQLException
       try
       {         
          // Initialize the connection object so that it can connect to mysql using getConnection() method in DriverManager
          connection = DriverManager.getConnection("jdbc:mysql://cs.neiu.edu:3306/s19315_azizmakda?" + "user=s19315_azizmakda&password="+ getPassword() );
          
          // Initialize the statement object with createStatement() so that it can execute SQL commands
          statement = connection.createStatement();
       
          selectMenu(); //go to Menu to Select options  
          
       }
       catch( SQLException sqle )
       {
          System.out.println("SQLException: " + sqle.getMessage() );
          System.out.println("SQLState: " + sqle.getSQLState() );
          System.out.println("VendorError: " + sqle.getErrorCode() );
       }
       catch( Exception e )
       {
          System.out.println( "Something else went wrong" );
       }
 
       finally
       {
         if( rs != null )
            rs.close();
            
          statement.close();
          connection.close();
          
          System.out.println("Thank you for using Careplus Staffing Program...Session Concluded."); 
       }
       
    } // End main()

    public static void selectMenu() throws Exception
    {
        System.out.println("Welcome to the Careplus Staffing Program");

        int choice = 1;

        while(choice >= 1 && choice <= 8)
        {
            System.out.println();
            System.out.println("1. Add a Therapist");
            System.out.println("2. Add a Patient");
            System.out.println("3. Add a Location");
            System.out.println("4. Delete a Therapist");
            System.out.println("5. Search for Therapist's current patients"); //Select, can make it view too
            System.out.println("6. Update a Therapist by Social Security Number");
            System.out.println("7. Update a Patient by Social Security Number");
            System.out.println("8. Create View for Patient and Therapist");
            System.out.println("9. Exit");
            
            System.out.println();

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            System.out.println();

            if (choice == 1) //Add a new Therapist 
            {
                addTherapist();
            }

            else if (choice == 2) //Add a new patient
            {
                addPatient();
            }

            else if (choice == 3) //Add new locations 
            {
                addLocation();
            }
            

            else if (choice == 4) //delete a therapist 
            {
                deleteTherapist();
            }

            else if (choice == 5) //Search for therapist's current patients
            {
                searchTherapistPatients(); 
            }

            else if (choice == 6) //Update therapist by social security 
            {
                updateTherapist();
            }

            else if (choice == 7) //Update patient by social security  
            {
                updatePatient();
            }

            else if (choice == 8) //Create view for patient and therapist
            {
                createView();
            }

        } //end while loop

    } //end selectMenu

    public static void addTherapist() throws Exception
   {
    
      String startInsertion = "";
      String endInsertion = "";

      System.out.println("You have selected to add a new therapist");
      System.out.println();

      String fName = "";
      String lName = "";
      String therapistType = "";
      String socialNumber = ""; //VARCHAR(11)
      String email = "";
      String phone = "";
      String address = "";
      int licenseNumber = 0;
      int zipCode = 0; 
      int bankRoutingNum = 0; //Null in SQL 

      System.out.print("Enter new therapist's first name (Enter up to 30 characters): ");
      fName = input.next();
      if (fName.length() > 30)
      {
        fName = verifyFirstName(fName);
      }
        /*while (fName.length() > 30) 
        { 
            System.out.println("Please enter the therapist's first name less than 30 characters.");
            fName = input.next();
        }
      }*/
      fName = addQuotes(fName); 
      System.out.println();

      System.out.print("Enter new therapist's last name (Enter up to 35 characters): ");
      lName = input.next();

      if (lName.length() > 35)
      {
          lName = verifyLastName(lName);
      }

      lName = addQuotes(lName);
      System.out.println();
      input.nextLine(); //dummy 

      System.out.print("Enter therapist type (Enter up to 40 characters): ");
      therapistType = input.nextLine();        
      
      while (therapistType.length() > 40)
      {
          if (therapistType.length() > 40)
          {
              System.out.print("Please enter only 40 characters: ");
              therapistType = input.nextLine(); 
          }
      }
      therapistType = addQuotes(therapistType); 
      System.out.println();

      System.out.print("Enter new therapist's license number (Enter only numbers): ");
      licenseNumber = input.nextInt();  
      System.out.println();
      input.nextLine(); //dummy
      
      System.out.print("Please enter in (###) ###-#### format. Enter new therapist's phone: "); 
      phone = input.nextLine();                  
      phone = addQuotes(phone); 
      System.out.println();

      System.out.print("Enter new therapist's email (Enter up to 40 characters): ");
      email = input.next();           
      //input.nextLine(); //dummy 
      if (email.length() > 40)
      {
          email = verifyEmail(email);
      }

      email = addQuotes(email); 
      System.out.println();
      input.nextLine(); //dummy 

      System.out.print("Enter new therapist's address (Enter up to 40 characters): ");
      address = input.nextLine(); 
      
      if (address.length() > 40)
      {
          address = verifyAddress(address);
      }

      address = addQuotes(address); 
      System.out.println();
      //input.nextLine(); //dummy value 

      System.out.print("Enter new therapist's zipcode (Enter up to 5 characters): ");
      zipCode = input.nextInt();
      System.out.println();
      
      System.out.print("Please enter in ###-##-#### format. Enter new therapist's social security number: ");
      socialNumber = input.next();   
      
      //Cannot loop any functions below due to thread depletion 

      boolean flag = ssCreate(socialNumber);
      
      if (flag == false)
      {
         System.out.print("Please enter a social security that does not exist in ###-##-#### format: ");
         socialNumber = input.next();
         flag = ssCreate(socialNumber);
      }

      boolean isValid = socialNumber.matches("^\\d{3}-\\d{2}-\\d{4}$"); 

      if (isValid == false)
      {
         System.out.print("Please enter a valid social security in the ###-##-#### format: ");
         socialNumber = input.next();
         isValid = socialNumber.matches("^\\d{3}-\\d{2}-\\d{4}$");  
      }

      else
      {
         isValid = true;
      }

      
      socialNumber = addQuotes(socialNumber);
      System.out.println();
      
      
      System.out.print("Does the new therapist have a bank routing number? (1 - YES,  2 - NO): ");
      int choice = input.nextInt();
      System.out.println();
      
      if ( choice == 1 )
      {
        System.out.print("Enter new therapist's bank routing number (Enter up to 9 characters): ");
        bankRoutingNum = input.nextInt(); 
        bankRoutingNum = verifyBank(bankRoutingNum);
        System.out.println();             
      }

      if (choice == 1)
      {
        startInsertion = "INSERT INTO Therapist (T_fName, T_lName, T_therapistType, T_licenseNumber, T_phone, T_email, T_address, L_zipCode, T_socialSecurityNumber, T_bankRoutingNumber) VALUES (";

        endInsertion = fName + "," + lName + "," + therapistType + "," + licenseNumber + "," + phone + "," + email + "," + address + "," + zipCode + "," + socialNumber + "," + bankRoutingNum + ")";
      }

      else
      {
        startInsertion = "INSERT INTO Therapist (T_fName, T_lName, T_therapistType, T_licenseNumber, T_phone, T_email, T_address, L_zipCode, T_socialSecurityNumber) VALUES (";

        endInsertion = fName + "," + lName + "," + therapistType + "," + licenseNumber + "," + phone + "," + email + "," + address + "," + zipCode + "," + socialNumber + ")";

      }
      
      String sqlInsert = startInsertion + endInsertion; 

      System.out.println("You are about to execute following command: " + sqlInsert);
      System.out.println();
      System.out.print("Are you sure? (Enter 1. Yes, 2. No): ");

      int answer = input.nextInt();
      System.out.println();

      if (answer == 1)
      {
         int numCount = statement.executeUpdate( sqlInsert );
         System.out.println(numCount + " record(s) updated.");
         System.out.println();
      }

      else 
      {
         System.out.println("\nUpdate cancelled. No Changes Made.\n");
         System.out.println();
      }

      //input.nextLine(); //to go to next line to prevent problems 


   } //end addTherapist


    public static void addPatient() throws Exception
    {
    
        String startInsertion = "";
        String endInsertion = "";

        System.out.println("You have selected to add a new patient.");
        System.out.println();

        String fName = "";
        String lName = "";
        String insuranceType = "";
        String diagnosis = "";
        String socialNumber = ""; //VARCHAR(11)
        String email = ""; //Can be Null in SQL
        String phone = "";
        String address = "";
        int zipCode = 0; 
        int totalVisits = 0;
        int attemptedVisits = 0;
        int cancelledVisits = 0;
        boolean therapyStatus = true; 

        System.out.print("Enter new patient's first name (Enter up to 30 characters): ");
        fName = input.next();

        if (fName.length() > 30)
        {
          fName = verifyFirstName(fName);
        }

        fName = addQuotes(fName); 
        System.out.println();

        System.out.print("Enter new patient's last name (Enter up to 35 characters): ");
        lName = input.next();

        if (lName.length() > 35)
        {
            lName = verifyLastName(lName);
        }

        lName = addQuotes(lName);
        System.out.println();
        input.nextLine();

        System.out.print("Enter new patient's diagnosis (Enter up to 60 characters): ");
        diagnosis = input.nextLine();  

        if (diagnosis.length() > 60)
        {
           diagnosis = verifyDiag(diagnosis);
        }

        diagnosis = addQuotes( diagnosis );
        //input.nextLine(); //dummy 
        System.out.println();

        System.out.print("Enter patient's insurance type (Enter up to 50 characters): ");
        insuranceType = input.nextLine();                  
        insuranceType = addQuotes(insuranceType); 
        //input.nextLine(); //dummy 
        System.out.println();

        System.out.print("Please enter in ###-##-#### format. Enter new patient's social security number: ");
        socialNumber = input.next();   
        
        boolean flag = ssCreate(socialNumber);
      
        if (flag == false)
        {
           System.out.print("Please enter a social security that does not exist in ###-##-#### format: ");
           socialNumber = input.next();
           flag = ssCreate(socialNumber);
        }
  
        boolean isValid = socialNumber.matches("^\\d{3}-\\d{2}-\\d{4}$"); 
  
        if (isValid == false)
        {
           System.out.print("Please enter a valid social security in the ###-##-#### format: ");
           socialNumber = input.next();
           isValid = socialNumber.matches("^\\d{3}-\\d{2}-\\d{4}$");  
        }
  
        else
        {
           isValid = true;
        }
         
        socialNumber = addQuotes(socialNumber);
        System.out.println();
        input.nextLine(); 

        System.out.print("Enter new patient's address (Enter up to 40 characters): ");
        address = input.nextLine();                  
        address = addQuotes(address); 
        //input.nextLine();
        System.out.println();

        System.out.print("Enter new patient's zipcode (Enter up to 5 characters): ");
        zipCode = input.nextInt();
        System.out.println(); 
        input.nextLine(); //dummy 
        
        System.out.print("Please enter in (###) ###-#### format. Enter new patient's phone: ");
        phone = input.nextLine();                  
        phone = addQuotes(phone); 
        //input.nextLine(); //dummy 
        System.out.println(); 
        
        System.out.print("Does the new patient have an email address? (1 - YES,  2 - NO): ");
        int choice = input.nextInt();
        System.out.println();
      
        if ( choice == 1 )
        {
            System.out.print("Enter new patient's email address (Enter up to 40 characters): ");
            email = input.next();                  
            email = addQuotes(email);  
            System.out.println();                 
        }

        System.out.print("Enter new patient's total visits: ");
        totalVisits = input.nextInt();  
        System.out.println();

        System.out.print("Enter new patient's attempted visits: ");
        attemptedVisits = input.nextInt(); 
        System.out.println(); 

        System.out.print("Enter new patient's cancelled visits: ");
        cancelledVisits = input.nextInt();  
        System.out.println();

        System.out.print("Enter new patient's therapy status: ");
        therapyStatus = input.nextBoolean(); 
        System.out.println(); 

        if (choice == 1)
        {
            startInsertion = "INSERT INTO Patient (P_fName, P_lName, P_diagnosis, P_insuranceType, P_socialSecurityNumber, P_address, L_zipCode, P_phone, P_email, P_totalVisits, P_attemptedVisits, P_cancelledVisits, P_therapyStatus) VALUES (";

            endInsertion = fName + "," + lName + "," + diagnosis + "," + insuranceType + "," + socialNumber + "," + address + "," + zipCode + "," +
            phone + "," + email + "," + totalVisits + "," + attemptedVisits + "," + cancelledVisits + "," + therapyStatus + ")";
        }

        else
        {
            startInsertion = "INSERT INTO Patient (P_fName, P_lName, P_diagnosis, P_insuranceType, P_socialSecurityNumber, P_address, L_zipCode, P_phone, P_totalVisits, P_attemptedVisits, P_cancelledVisits, P_therapyStatus) VALUES (";

            endInsertion = fName + "," + lName + "," + diagnosis + "," + insuranceType + "," + socialNumber + "," + address + "," + zipCode + "," + phone + "," + totalVisits + "," + 
            attemptedVisits + "," + cancelledVisits + "," + therapyStatus + ")";

        }
        
        String sqlInsert = startInsertion + endInsertion; 

        System.out.println("You are about to execute following command: " + sqlInsert);
        System.out.println();

        System.out.print("Are you sure? (Enter 1. Yes, 2. No): ");
        int answer = input.nextInt();

        System.out.println(); 

        if (answer == 1)
        {
            int numCount = statement.executeUpdate( sqlInsert );
            System.out.println(numCount + " record(s) updated.");
            System.out.println();
        }

        else 
        {
            System.out.println("\nUpdate cancelled. No Changes Made.\n");
            System.out.println();
        }

        //input.nextLine(); //to go to next line to prevent problems 


    } //end addPatient

    public static void addLocation() throws Exception
    {
    
        System.out.println("You have selected to add a new location");
        System.out.println();

        int zipCode = 0;
        String county = "";
        String city = "";

        System.out.print("Enter the new zipcode (Enter up to 5 characters max + Press enter twice if you have entered 5 characters): "); //Java flaw doesnt proceed forward 
        zipCode = input.nextInt();
        zipCode = verifyZip(zipCode);
        System.out.println();
        //input.nextLine(); //dummy

        
        System.out.print("Enter the new city corresponding to the new zipcode (Enter up to 35 characters): ");
        city = input.nextLine();                  
        city = addQuotes(city); 
        //input.nextLine();
        System.out.println();

        System.out.print("Enter the new county corresponding to the new zipcode (Enter up to 30 characters): ");
        county = input.nextLine();
        county = addQuotes(county);
        System.out.println();

        String startInsertion = "INSERT INTO Locations (L_zipCode, L_city, L_county) VALUES (";
        String endInsertion = zipCode + "," + city + "," + county + ")";
        
        String sqlInsert = startInsertion + endInsertion; 

        System.out.println("You are about to execute following command: " + sqlInsert);
        System.out.println();
        System.out.print("Are you sure? (Enter 1. Yes, 2. No): ");

        int answer = input.nextInt();
        System.out.println();

        if (answer == 1)
        {
            int numCount = statement.executeUpdate( sqlInsert );
            System.out.println(numCount + " record(s) updated.");
            System.out.println();
        }

        else 
            System.out.println("\nUpdate cancelled. No Changes Made.\n");
            System.out.println();

    } //end addLocations

    public static void deleteTherapist() throws Exception
    {
        int numCount = 0;

        System.out.print("Enter the social security of the therapist you would like to delete: ");
        String socialNumber = input.next();
        socialNumber = addQuotes( socialNumber );

        String sqlFind = "SELECT T_id FROM Therapist WHERE T_socialSecurityNumber=" + socialNumber;
        int t_id = 0; 
        rs = statement.executeQuery(sqlFind);

        while( rs.next() )
        {
            t_id = rs.getInt("T_id");
        }

        String sqlTP = "DELETE FROM Thera_Pat WHERE T_id=" + t_id;
        String sqlCoverage = "DELETE FROM Coverage WHERE T_id=" + t_id; 


        String sqlDelete = "DELETE FROM Therapist WHERE T_socialSecurityNumber=" + socialNumber;
        
        System.out.println();
        System.out.print("Are you sure you want to delete the therapist? (1- Yes, 2- No ): ");
        int answer = input.nextInt();

        if ( answer == 1 )
        {
            statement.executeUpdate(sqlTP);
            statement.executeUpdate(sqlCoverage);

            numCount = statement.executeUpdate( sqlDelete );
            System.out.println();
            System.out.println(numCount + " record deleted.");
            System.out.println();
        }
        else
        {
            System.out.println("No Changes");
            System.out.println();
        }

    } //end deleteTherapist()

    //ask this method in office hours 

    public static void searchTherapistPatients() throws Exception 
    {
        String socialNumber = "";
        System.out.print ("Enter the Therapist's social security number: ");
        socialNumber = input.next();
        socialNumber = addQuotes(socialNumber);

        int id = 0; 
        String sql = "SELECT T_id FROM Therapist WHERE T_socialSecurityNumber=" + socialNumber;
        
        rs = statement.executeQuery( sql ); 

        while (rs.next() )
        {
            id = rs.getInt( "T_id" );
        }
        rs.close();
        
        String select = "SELECT T_fName, T_lName, T_therapistType, T_phone, T_email, P_fName, P_lName, P_insuranceType, P_diagnosis, P_totalVisits, P_attemptedVisits, P_cancelledVisits, P_therapyStatus ";
        String from = "FROM Therapist,Thera_Pat,Patient ";
        String where = "WHERE Therapist.T_id = Thera_Pat.T_id ";
        String and = "AND Thera_Pat.P_id = Patient.P_id";
         
         
        sql = select + from + where + and;
        //sql = select + from + where + and;

        rs = statement.executeQuery( sql );
        
        String tFirst = "";   //Therapist's  first name
        String tLast = "";    //Therapist's  last name
        String tType = "";    //Therapist's  type 
        String tEmail = "";   //Therapist's  email
        String pFirst = "";   //Patient's    first name
        String pLast = "";    //Patient's    last name
        String pDiag = "";    //Patient's    diagnosis
        String pInsure = "";  //Patient's    insurance type 
        
        int pTotal = 0;       //Patient's    total visits
        int pAttempted = 0;   //Patient's    attempted visits
        int pCancelled = 0;   //Patient's    cancelled visits
        
        boolean pStatus = true; //Patient's  therapy status 

        while( rs.next() )
        {
            tFirst = rs.getString( "T_fName" );
            tLast = rs.getString ( "T_lName" );
            tType = rs.getString ( "T_therapistType" );
            tEmail = rs.getString ( "T_email" );
            pFirst = rs.getString ( "P_fName" );
            pLast = rs.getString ( "P_lName" );
            pDiag = rs.getString ( "P_diagnosis" );
            pInsure = rs.getString ( "P_insuranceType" );
            
            pTotal = rs.getInt( "P_totalVisits" );
            pAttempted = rs.getInt( "P_attemptedVisits" );
            pCancelled = rs.getInt( "P_cancelledVisits" );

            pStatus = rs.getBoolean ( "P_therapyStatus" );
       
        }
        
        System.out.println( "\nTherapist First Name: " + tFirst + "     || \tTherapist Last Name: " + tLast + "     || \tTherapist Type: " + tType + "\n");
        System.out.println("Therapist Email Address: " + tEmail + "     || \tPatient First Name: " + pFirst + "     || \tPatient Last Name: " + pLast + "     || \tPatient's Diagnosis: " + pDiag + "\n");
        System.out.println("Patient's Insurance Type: " + pInsure + "     || \tTotal visits: " + pTotal + "     || \tAttempted Visits: " + pAttempted + "     || \tCancelled Visits: " + pCancelled + 
        "     || \tPatient's Therapy Status: " + pStatus);

        System.out.println();
   
    } //end searchTherapistPatient() 
    
 
    public static void updateTherapist() throws Exception
    {
        int choice = 0; 

        System.out.println("Updating Therapist's Data, by social security");
        System.out.println();
        
        System.out.print("Please enter the therapist's social security number: ");
        String socialNumber = input.next();
        socialNumber = addQuotes ( socialNumber );
        
        System.out.println();

        System.out.print("Do you want to update therapist's address? (1- Yes,  2- No): ");
        choice = input.nextInt();
        input.nextLine(); //dummy
        System.out.println();

        if( choice == 1 ) //address
        {
            
            System.out.print("Enter the new address (Enter up to 40 characters): ");
            String newAddress = input.nextLine();
            newAddress = addQuotes ( newAddress );
            System.out.println();

            String sqlUpdate = "Update Therapist SET T_address=" + newAddress + " WHERE T_socialSecurityNumber=" + socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate); 
            
            System.out.println(); 
            
            System.out.print("Are you sure you want to update the address? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update therapist's zipcode? (1- Yes,  2- No): ");
        choice = input.nextInt();
        input.nextLine(); //dummy
        System.out.println();

        if( choice == 1 ) //zipCode
        {
            
            System.out.print("Enter the new zipcode (Enter up to 5 characters): ");
            int newZipCode = input.nextInt(); 
            System.out.println();

            String sqlUpdate = "Update Therapist SET L_zipCode=" + newZipCode + " WHERE T_socialSecurityNumber=" + socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate); 
            
            System.out.println(); 
            
            System.out.print("Are you sure you want to update the zipcode? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update therapist's phone? (1- Yes,  2- No): ");
        choice = input.nextInt();
        input.nextLine(); //dummy
        System.out.println();

        if( choice == 1 ) //phone
        {
            
            System.out.print("Enter the new phone (Please enter in (###) ###-#### format): ");
            String newPhone = input.nextLine();
            newPhone = addQuotes ( newPhone );
            System.out.println();

            String sqlUpdate = "Update Therapist SET T_phone=" + newPhone + " WHERE T_socialSecurityNumber="+ socialNumber;
            

            System.out.println("You are about to execute this command: " + sqlUpdate);  
            
            System.out.println(); 

            System.out.print("Are you sure you want to update the phone number? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            input.nextLine(); //dummy 
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update therapist's email? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();

        if( choice == 1 ) //email
        {
            
            System.out.print("Enter the new email (Enter up to 40 characters): ");
            String newEmail = input.next();
            newEmail = addQuotes ( newEmail );
            System.out.println(); 

            String sqlUpdate = "Update Therapist SET T_email=" + newEmail + " WHERE T_socialSecurityNumber="+ socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate);  
            
            System.out.println(); 

            System.out.print("Are you sure you want to update the email address? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();
            
            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println(); 
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Does the therapist have a bank routing number? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();

        if (choice == 1)
        {

            System.out.print("Do you want to update therapist's bank routing number? (1- Yes,  2- No): ");
            choice = input.nextInt();
            System.out.println();
            
            if( choice == 1 ) //Bank Routing Number
            {
                
                System.out.print("Enter the new bank routing number (Enter up to 9 characters): ");
                String newRoutingNum = input.next();
                newRoutingNum = addQuotes ( newRoutingNum );
                System.out.println();

                String sqlUpdate = "Update Therapist SET T_bankRoutingNumber=" + newRoutingNum + " WHERE T_socialSecurityNumber="+ socialNumber;

                System.out.println("You are about to execute this command: " + sqlUpdate);   
                
                System.out.println();
                
                System.out.print("Are you sure you want to update the bank routing number? (Enter 1. Yes, 2. No): ");
                int answer = input.nextInt();
                System.out.println();

                if (answer == 1)
                {
                    int numCount = statement.executeUpdate( sqlUpdate );
                    System.out.println(numCount + " record(s) updated.");
                    System.out.println();
                }

                else 
                {
                    System.out.println("\nUpdate cancelled. No Changes Made.\n");
                    System.out.println();
                }

            }

                else
                {
                    System.out.println("Update cancelled.");
                    System.out.println();
                }
        }
        

    } //end updateTherapist()

    public static void updatePatient() throws Exception
    {
        
        int choice = 0; 

        System.out.println("Updating Patient's Data, by social security");
        System.out.println();
        
        System.out.print("Please enter the patient's social security number: ");
        String socialNumber = input.next();
        socialNumber = addQuotes ( socialNumber );
        System.out.println();

        System.out.print("Do you want to update patient's insurance type? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();

        if( choice == 1 ) //insurance type
        {
            
            System.out.print("Enter the new insurance type (Enter up to 50 characters): ");
            String newInsurance = input.next();
            newInsurance = addQuotes ( newInsurance );
            input.nextLine(); //dummy
            System.out.println();

            String sqlUpdate = "Update Patient SET P_insuranceType=" + newInsurance + " WHERE P_socialSecurityNumber="+ socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate);   
            
            System.out.println();
            
            System.out.print("Are you sure you want to update the insurance type? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println(); 

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update patient's diagnosis? (1- Yes,  2- No): ");
        choice = input.nextInt();
        input.nextLine(); //dummy
        System.out.println();

        if( choice == 1 ) //diagnosis
        {
            
            System.out.print("Enter the new diagnosis for the patient (Enter up to 60 characters): ");
            String newDiagnosis = input.nextLine();
            newDiagnosis = addQuotes ( newDiagnosis );
            System.out.println(); 

            String sqlUpdate = "Update Patient SET P_diagnosis=" + newDiagnosis + " WHERE P_socialSecurityNumber="+ socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate);  
            
            System.out.println(); 
            
            System.out.print("Are you sure you want to update the diagnosis? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update patient's address? (1- Yes,  2- No): ");
        choice = input.nextInt();
        input.nextLine(); //dummy
        System.out.println();
        
        if( choice == 1 ) //address
        {
            
            System.out.print("Enter the new address (Enter up to 40 characters): ");
            String newAddress = input.nextLine();
            newAddress = addQuotes ( newAddress );
            //input.nextLine(); //dummy
            System.out.println();

            String sqlUpdate = "Update Patient SET P_address=" + newAddress + " WHERE P_socialSecurityNumber=" + socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate);
            
            System.out.println();   
            
            System.out.print("Are you sure you want to update the address? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update patient's zipcode? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();

        if( choice == 1 ) //zipcode
        {
            
            System.out.print("Enter the new zipcode (Enter up to 5 characters): ");
            int newZipCode = input.nextInt();
            //input.nextLine(); //dummy
            System.out.println();

            String sqlUpdate = "Update Patient SET L_zipCode=" + newZipCode + " WHERE P_socialSecurityNumber=" + socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate);
            
            System.out.println();   
            
            System.out.print("Are you sure you want to update the zipcode? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }

        System.out.print("Do you want to update patient's phone? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();


        if( choice == 1 ) //phone
        {
            
            System.out.print("Enter the new phone (Please enter in (###) ###-#### format): ");
            String newPhone = input.nextLine();
            newPhone = addQuotes ( newPhone );
            input.nextLine(); //dummy 
            System.out.println();

            String sqlUpdate = "Update Patient SET P_phone=" + newPhone + " WHERE P_socialSecurityNumber="+ socialNumber;

            System.out.println("You are about to execute this command: " + sqlUpdate); 
            
            System.out.println();  
            
            System.out.print("Are you sure you want to update the phone? (Enter 1. Yes, 2. No): ");
            int answer = input.nextInt();
            System.out.println();

            if (answer == 1)
            {
                int numCount = statement.executeUpdate( sqlUpdate );
                System.out.println(numCount + " record(s) updated.");
                System.out.println();
            }

            else 
            {
                System.out.println("\nUpdate cancelled. No Changes Made.\n");
                System.out.println();
            }

        }

        else
        {
            System.out.println("Update cancelled.");
            System.out.println();
        }


        System.out.print("Does the patient have an email? (1- Yes,  2- No): ");
        choice = input.nextInt();
        System.out.println();

        if (choice == 1)
        {

            System.out.print("Do you want to update patient's email? (1- Yes,  2- No): ");
            choice = input.nextInt();
            System.out.println();

            if( choice == 1 ) //Email
            {
                
                System.out.print("Enter the new email (Enter up to 40 characters): ");
                String newEmail = input.next();
                newEmail = addQuotes ( newEmail );
                System.out.println();

                String sqlUpdate = "Update Patient SET P_email=" + newEmail + " WHERE P_socialSecurityNumber="+ socialNumber;

                System.out.println("You are about to execute this command: " + sqlUpdate); 
                
                System.out.println();  
                
                System.out.print("Are you sure you want to update the email? (Enter 1. Yes, 2. No): ");
                int answer = input.nextInt();
                System.out.println();

                if (answer == 1)
                {
                    int numCount = statement.executeUpdate( sqlUpdate );
                    System.out.println(numCount + " record(s) updated.");
                    System.out.println();
                }

                else 
                {
                    System.out.println("\nUpdate cancelled. No Changes Made.\n");
                    System.out.println(); 
                }

            }

            else
            {
                System.out.println("Update cancelled.");
                System.out.println(); 
            }
        }

    } //end updatePatient()

    public static void createView() throws Exception
    {
        String sql = "CREATE OR REPLACE VIEW PatientTherapistGas AS";
        String select = " SELECT P_fName, P_lName, P_insuranceType, P_diagnosis, T_fName, T_lName, C_gasCoverage";
        String from = " FROM Patient, Therapist, Coverage, Thera_Pat";
        String where = " WHERE Patient.P_id = Thera_Pat.P_id";
        String and = " AND Thera_Pat.T_id = Therapist.T_id";
        String and2 = " AND Coverage.T_id = Therapist.T_id"; 

        String combine = sql + select + from + where + and + and2;
        statement.executeUpdate (combine); 
        
    }


   public static String addQuotes( String value ) //adds quotes to attributes 
   {
      String result = "'" + value + "'";
      return result; 
   } //end addQuotes 

   public static int verifyBank(int b)
   {
        int bank = b;
        String br = bank + "";  
        input.nextLine(); 
        
        while (br.length() < 9 || br.length() > 9)
        {

            if (br.length() < 9 || br.length() > 9)
            {
                System.out.print("Please enter only 9 characters for bank routing number: " );
                br = input.next();
                System.out.println(); 
            }
        }

        bank = Integer.parseInt(br);
        
        return bank; 

   }


   public static String verifyFirstName (String fn)
   {
       String fName = fn;
       while (fName.length() > 30)
       {
           if (fName.length() > 30)
           {
               System.out.print("Please enter the 30 characters only for the first name: ");
               fName = input.next();
               System.out.println(); 
           }
       }
       return fName; 
   }

   public static String verifyLastName (String ln)
   {
       String lName = ln;
       while (lName.length() > 30)
       {
           if (lName.length() > 30)
           {
               System.out.print("Please enter the 35 characters only for the last name: ");
               lName = input.next();
               System.out.println(); 
           }
       }
       return lName; 
   }
   
   public static String verifyPhoneNumber(String phoneN)
   {
        String newPhone = phoneN;
        while (newPhone.length() > 14)
        {

            if (phoneN.length() > 14)
            {
                System.out.print("Please enter (###) ###-#### format correctly: " );
                newPhone = input.nextLine();
                System.out.println(); 
            }
        }

        return newPhone; 
   } //end verifyPhoneNumber 

   public static String verifyAddress(String vaddress)
   {
        String newAddress = vaddress;
        while (newAddress.length() > 40)
        {

            if (newAddress.length() > 40)
            {
                System.out.print("Please enter up to 40 characters for address only: " );
                newAddress = input.nextLine();
                System.out.println(); 
            }
        }

        return newAddress; 
   }

   public static int verifyZip(int zip)
   {
        int zipCode = zip;
        String newZip = zipCode + "";  
        input.nextLine(); 
        
        while (newZip.length() < 5 || newZip.length() > 5)
        {

            if (newZip.length() < 5 || newZip.length() > 5)
            {
                System.out.print("Please enter only 5 characters for zipcode: " );
                newZip = input.next();
                System.out.println(); 
            }
        }

        zipCode = Integer.parseInt(newZip);

        input.nextLine(); //dummy
        return zipCode; 

   }

   public static String verifyEmail(String eemail)
   {
        String newEmail = eemail;
        while (newEmail.length() > 40)
        {

            if (newEmail.length() > 40)
            {
                System.out.print("Please enter 40 characters only: " );
                newEmail = input.next();
                System.out.println(); 
            }
            
        }
        //input.nextLine(); //dummy
        return newEmail; 
   }

   /*public static int verifyBank(int routing)
   {
        int newBank = routing;
        String newRouting = newBank + ""; 
        boolean flag = newRouting.matches("^\\d{9}$"); 

        while (newRouting.length() > 9 || flag == false)
        {

            if (newRouting.length() > 9)
            {
                System.out.print("Please enter only 9 characters for bank routing number: " );
                newRouting = input.nextLine();
                flag = newRouting.matches("^\\d{9}$");
                System.out.println(); 
            }
        }

    newBank = Integer.parseInt(newRouting);
    
    return newBank; 
   }*/

   public static String verifyDiag(String d)
   {
        String newDiag = d;
        while (newDiag.length() > 60)
        {

            if (newDiag.length() > 60)
            {
                System.out.print("Please enter up to 60 characters for diagnosis only: " );
                newDiag = input.nextLine();
                System.out.println(); 
            }
        }

        return newDiag; 
   }

   public static String verifyInsurance(String i)
   {
        String newInsurance = i;
        while (newInsurance.length() > 50)
        {

            if (newInsurance.length() > 50)
            {
                System.out.print("Please enter up to 50 characters for insurance only: " );
                newInsurance = input.nextLine();
                System.out.println(); 
            }
        }

        return newInsurance; 
   } //end verifyInsurance

   //method for checking is social security already exist 
   public static boolean ssCreate(String social) throws Exception
   {
       String ssn = addQuotes(social);
       String selectP = "SELECT P_fName FROM Patient WHERE P_socialSecurityNumber=" + ssn;
       String selectT = "SELECT T_fName FROM Therapist WHERE T_socialSecurityNumber=" + ssn;
       Thread.sleep(500); 

       rs = statement.executeQuery(selectP);
       

       String temp = "";

       while (rs.next() )
       {
         temp = rs.getString("P_fName");
       }
       
       if (temp.length() > 0)
       {
         return false;
       }
       
       rs = statement.executeQuery(selectT);
       
       while (rs.next() )
       {
         temp = rs.getString("T_fName");
       }
       
       if (temp.length() > 0)
       {
         return false;
       }

       return true;
       
   } //end ssCreate

    //method for checking social security format
    /*public static String isValidSS (String s) throws Exception 
    {
        String social = s;
        boolean isValid = social.matches("^\\d{3}-\\d{2}-\\d{4}$"); //need \\ slashes or else error
        //boolean flag = false;

        while (isValid == false) //      
        {
            //Thread.sleep(10000); 
            //flag = ssCreate(social);
            //Thread.sleep(550); //To give it time to process

            if (isValid == false)
            {
                System.out.print("Please enter a valid social security in the ###-##-#### format: ");
                social = input.next();
            }

            else
            {
                isValid = true;
            }
            //System.out.println();
        }

        return social;

    } //end isValidSS*/















































































































   public static String getPassword()
   {
      String p = "TempPassword9134!";
      return p;
   }



} //end class