package aproject.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.dbutil.OracleUtil;

import aproject.vo.EmpVO;

//DAO(Data Access Object) : DB업무, CRUD 
public class EmpDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst; //? 지원
	ResultSet rs;
	int resultCount; //인서트, 업데이트, 딜리트 건수
	CallableStatement cst; //SP 지원
	
	public List<EmpVO> selectAll() {
		String sql = """
				    select  EMPLOYEE_ID,
							FIRST_NAME,
							LAST_NAME,
							EMAIL,
							PHONE_NUMBER,
							HIRE_DATE,
							JOB_ID,
							SALARY,
							COMMISSION_PCT,
							MANAGER_ID,
							DEPARTMENT_ID 
					from employees
					order by employee_id
						""";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}
	
	//자신이 속한 부서의 평균 급여보다 더 작은 급여를 받는 직원들을 조회하시오.
	public List<EmpVO> selectLAB() {
		String sql ="""
				select employee_id, first_name, salary, employees.department_id
				from employees, (select department_id, avg(salary) avgsal
								 from employees
                 				 group by department_id) inlineview_emp
				where employees.department_id = inlineview_emp.department_id
				and employees.salary < inlineview_emp.avgsal
				""";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EmpVO emp = makeEmp2(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}

	//특정 직원 조회
	public EmpVO selectByid(int empid) {
		String sql ="select * from employees where employee_id = " + empid;
		EmpVO emp = null;
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				 emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emp;
	}
	
	//특정 부서 직원 조회
	public List<EmpVO> selectBydept(int deptid) {
		String sql ="select * from employees where department_id = " + deptid;
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}
	
	//특정 부서, 잡 아이디, 셀러리 이상 직원 조회
	public List<EmpVO> selectByCondition(int deptid, String jobid, double salary) {
		String sql ="select * "
				+ " from employees "
				+ " where department_id = ? "
				+ " and job_id = ? "
				+ " and salary >= ? ";
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid);
			pst.setString(2, jobid);
			pst.setDouble(3, salary);
			rs = pst.executeQuery();
			while(rs.next()) {
				EmpVO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		return emplist;
	}
	
	//신규직원을 등록하고 싶다(Insert)
	public int empInsert(EmpVO emp) {
		System.out.println(emp);
		String sql ="""
				insert into employees values(seq_employee.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		conn = OracleUtil.getConnection();
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, emp.getFIRST_NAME());
			pst.setString(2, emp.getLAST_NAME());
			pst.setString(3, emp.getEMAIL());
			pst.setString(4, emp.getPHONE_NUMBER());
			pst.setDate(5, emp.getHIRE_DATE());
			pst.setString(6, emp.getJOB_ID());
			pst.setDouble(7, emp.getSALARY());
			pst.setDouble(8, emp.getCOMMISSION_PCT());
			pst.setInt(9, emp.getMANAGER_ID());
			pst.setInt(10, emp.getDEPARTMENT_ID());
			
			resultCount =  pst.executeUpdate(); //DML 문장 실행한다
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, pst, conn);
		}
		return resultCount;
	}
	
	//직원 정보 수정
	public int empUpdate(EmpVO emp) {
		System.out.println(emp);
		String sql ="""
				update employees
				set EMAIL = ?, DEPARTMENT_ID =?, JOB_ID=?, SALARY=?, hire_date = ?, manager_id =?
				where EMPLOYEE_ID = ?
				""";
		conn = OracleUtil.getConnection();
		
		try {
			pst = conn.prepareStatement(sql);
			
			pst.setString(1, emp.getEMAIL());
			pst.setInt(2, emp.getDEPARTMENT_ID());
			pst.setString(3, emp.getJOB_ID());
			pst.setDouble(4, emp.getSALARY());
			pst.setDate(5, emp.getHIRE_DATE());
			pst.setDouble(6, emp.getMANAGER_ID());
			pst.setDouble(7, emp.getEMPLOYEE_ID());
			
			resultCount =  pst.executeUpdate(); //DML 문장 실행한다, 영향을 받은 건수가 리턴됨
			
		} catch (SQLException e) {
			resultCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, pst, conn);
		}
		System.out.println("업데이트 결과 : " + resultCount);
		return resultCount;
	}
	
	//한건의 직원을 삭제하기
		public int empDelete(int empid) {
			System.out.println(empid);
			String sql ="""
					delete from employees
					where EMPLOYEE_ID = ?
					
					""";
			conn = OracleUtil.getConnection();
			
			try {
				pst = conn.prepareStatement(sql);
				
				pst.setInt(1, empid);
				
				resultCount =  pst.executeUpdate(); //DML 문장 실행한다, 영향을 받은 건수가 리턴됨
				
			} catch (SQLException e) {
				resultCount = -1;
				e.printStackTrace();
			} finally {
				OracleUtil.dbDisconnect(null, pst, conn);
			}
			System.out.println("딜리트 결과 : " + resultCount);
			return resultCount;
		}
	
	private EmpVO makeEmp(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();
		emp.setCOMMISSION_PCT(rs.getDouble("COMMISSION_PCT"));
		emp.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
		emp.setEMAIL(rs.getString("EMAIL"));
		emp.setEMPLOYEE_ID(rs.getInt("EMPLOYEE_ID"));
		emp.setFIRST_NAME(rs.getString("FIRST_NAME"));
		emp.setHIRE_DATE(rs.getDate("HIRE_DATE"));
		emp.setJOB_ID(rs.getString("JOB_ID"));
		emp.setLAST_NAME(rs.getString("LAST_NAME"));
		emp.setMANAGER_ID(rs.getInt("MANAGER_ID"));
		emp.setPHONE_NUMBER(rs.getString("PHONE_NUMBER"));
		emp.setSALARY(rs.getDouble("SALARY"));
		
		return emp;
	}
	
	private EmpVO makeEmp2(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();
		
		emp.setDEPARTMENT_ID(rs.getInt("DEPARTMENT_ID"));
		emp.setEMPLOYEE_ID(rs.getInt("EMPLOYEE_ID"));
		emp.setFIRST_NAME(rs.getString("FIRST_NAME"));
		emp.setSALARY(rs.getDouble("SALARY"));
		
		return emp;
	}
	
	//sp 호출
	public EmpVO getSalary(int empid) {
		String sql = "{call sp_salary(?, ?, ?)}";
		conn = OracleUtil.getConnection();
		EmpVO emp = new EmpVO();
		try {
			cst = conn.prepareCall(sql);
			cst.setInt(1, empid);
			cst.registerOutParameter(2, Types.DOUBLE);
			cst.registerOutParameter(3, Types.VARCHAR);
			cst.execute(); // 리설트 셋이 있으면 트루 없으면 펄스
			emp.setSALARY(cst.getDouble(2));
			emp.setFIRST_NAME(cst.getString(3));
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}
	
}
