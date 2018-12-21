
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
*
* @author Nischal
1. Reading the data from the txt file. File is like below
Micheal Brown 3093934948 345-45-4545 Auto abd123@abc.com
2. Saving the data(customer details) into database table
3. Getting the customer details from the database and displying the results by masking the sensitive data(ssn,phone number, email)
*/

public class MaskSensitiveData {
	
		private String customerName;
		private int phoneNumber;
		private String ssn;
		private String policy;
		private String email;
		
		public void readData(){
			try(Scanner input = new Scanner(new File("src/customer/customer_data.txt"))){
				
				while(input.hasNextLine()){
					customerName = "";
					String line;
					line = input.nextLine;
					//If the line variable has no data then re-iterratre the loop to move on the next line
					if(line.length() <= 0)
						continue;
					
					//Process line by line on the text
					try(Scanner data = new Scanner(line)){
						while(!data.hasNextInt()){
							customerName += data.next()+" ";
						}
						customerName = customerName.trim();
						//Get phone number
						if(data.hasNextInt()){
							phoneNumber = data.nextInt();
						}	
						//Get SSN
						if(data.hasNext()){
							ssn = data.next();
						}
						//Get Policy
						if(data.hasNext()){
							policy = data.next();
						}
						//Get email
						if(data.hasNext()){
							email = data.next();
						}
					}
					saveData();
				}			
			}catch(IOException e){
				System.out.println(e);
			}	
		}	
		//Save the data into the database
		private void saveData(){
			try(Connection conn = connect(); 
					PreparedStatement pstat = conn.prepareStatement("INSERT INTO customer(Customer_Id,Customer_name,Phone_Number,SSN,Policy,email) values(cust_id.nextval, ?, ?, ?, ?, ?)")){
				
				pstat.setString(1, customerName);
				pstat.setInt(2, phoneNumber);
				pstat.setString(3, ssn);
				pstat.setString(4, policy);
				pstat.setString(5, email);
				pstat.executeUpdate();
				
			}catch(SQLException e){
				System.out.println(e);
			}
			
		}
		//Disply the data from database.
		public void displayData(){
          try(Connection conn = connect();
					Statement stat = conn.createStatement()){
				boolean hasResultSet = stat.execute("select customer_id,customer_name,CONCAT('xxxxxx',RIGHT(phone_number,4) as phone_number,CONCAT('xxx-xx-',RIGHT(ssn,4)) as ssn,policy,CONCAT('xxxxx',RIGHT(email,3)) from customer");
				if(hasResultSet){
					ResultSet result = stat.getResultSet();
					ResultSetMetaData metaData = result.getMetaData();
					
					//get the number of columns
					int columnCount = metaData.getColumnCount();
					//display column labels
					for(int i=1; i<columnCount; i++){
						System.out.print(metaData.getColumnLable(i)+"\t\t");
					} 
					
					//Disply data
					while(result.next()){
						System.out.printf(result.getInt("Customer_Id"),result.getString("Customer_name"),result.getInt("Phone_Number"),result.getString("SSN"),result.getString("Policy"),result.getString("email"));
					}		
				}
					
			}catch(SQLException e){
				System.out.println(e);
			}
		}
		
		//Create a connections to the database
		private Connection connect(){
			try{
				return DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "mysql123");	
			}catch(SQLException | ClassNotFoundException e){
				System.out.println(e);
				return null;
			}
		
		}
		
}

class Evaluate{
		public static void main(String[] args){
			MaskSensitiveData data = new MaskSensitiveData();
			try{
				data.readData(); //Read data from the txt file and save the data into database
				data.displayData(); //Display the customer details by masking the sensitive data
			}catch(Exception e){
				System.out.println(e);
		}
}
}
