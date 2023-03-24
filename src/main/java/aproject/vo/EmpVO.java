package aproject.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmpVO {
	 private int EMPLOYEE_ID;                              
	 private String FIRST_NAME;                                         
	 private String LAST_NAME;                                
	 private String EMAIL;                                     
	 private String PHONE_NUMBER;                                      
	 private Date HIRE_DATE;                              
	 private String JOB_ID;                                    
	 private double SALARY;                                             
	 private double COMMISSION_PCT ;                                   
	 private int MANAGER_ID;                                        
	 private int DEPARTMENT_ID;
}
