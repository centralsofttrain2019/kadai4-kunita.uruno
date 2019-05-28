package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.EmployeeBean;
import bean.EmployeeDispKeyBean;
import dao.Dao;
import dao.EmployeesDao;

public class EmployeeService
{
	public	List<EmployeeBean> employeefindall()
	{
		List<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		try(Connection con = Dao.getConnection())
		{
			EmployeesDao dao = new EmployeesDao(con);
			empList = dao.findAll();


		}
		catch( SQLException | ClassNotFoundException e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return empList;

	}

	public EmployeeDispKeyBean employeefindkey(int userID) {
		EmployeeDispKeyBean emp = new EmployeeDispKeyBean();
		try(Connection con = Dao.getConnection())
		{
			EmployeesDao dao = new EmployeesDao(con);
			emp = dao.findByKey(userID);


		}
		catch( SQLException | ClassNotFoundException e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return emp;
	}

	public	int employeecount()
	{
		int count = 0;
		try(Connection con = Dao.getConnection())
		{
			EmployeesDao dao = new EmployeesDao(con);
			 count = dao.countRecords();


		}
		catch( SQLException | ClassNotFoundException e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);

		}
		return count;
	}


}
