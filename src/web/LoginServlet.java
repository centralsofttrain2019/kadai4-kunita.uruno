package web;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.EmployeeDispKeyBean;
import bean.LoginBean;
import service.EmployeeService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }
    public enum Error
    {
    	NumberFormatException,
	    NumberOver;

    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
	protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
            )  throws ServletException, IOException
    {



        //画面から入力したデータを取得する


        String str =request.getParameter("userId");
        byte[] bi = str.getBytes("iso-8859-1");
        String userId = new String( bi, "UTF-8" );
        EmployeeService empse = new EmployeeService();
        int userID = 0;
        try
        {
        	userID = Integer.parseInt(request.getParameter("userId"));

        }
        catch(NumberFormatException e)
		{
			LoginBean error = new LoginBean();
			error.setError(Error.NumberFormatException.name());
//			System.out.println(Error.NFE.ordinal());

			request.setAttribute("error",error);
			RequestDispatcher disp = request.getRequestDispatcher("/loginError.jsp");
			disp.forward(request, response);
			return;
		}

        if(userID<1||userID>empse.employeecount())
    	{
    		LoginBean error = new LoginBean();
    		error.setCount(empse.employeecount());
			error.setError(Error.NumberOver.name());
			request.setAttribute("error",error);
			RequestDispatcher disp = request.getRequestDispatcher("/loginError.jsp");
			disp.forward(request, response);
			return;
    	}


        //bean のインスタンスを生成する
        LoginBean bean = new LoginBean();
        bean.setUserId( userId );

		EmployeeDispKeyBean emp = empse.employeefindkey(userID);
		bean.setEmployeeName(emp.getEmployeeName());
        bean.setLoginDateTime( LocalDateTime.now() );


        //セッションの今回の計算結果を保存
        //request.getSession().setAttribute("loginBean", bean);

        HttpSession ss= request.getSession(true);
        ss.setAttribute("loginBean", bean);
        LoginBean ss1 =(LoginBean)ss.getAttribute("loginBean");
		System.out.println(ss.getId());
        System.out.println(ss1.getLoginInfo());

        //beanをリクエストにセット キー名は「bean」とする
        request.setAttribute("bean", ss1);

        //JSPに遷移する
        RequestDispatcher disp = request.getRequestDispatcher("/login.jsp");
        disp.forward(request, response);
    }
}
