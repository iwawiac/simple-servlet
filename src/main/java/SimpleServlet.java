import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SimpleServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        this.userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/test":
                    testServlet(request, response);
                    break;
                case "/listusers":
                    listUsers(request, response);
                    break;
                case "/newuser":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    addUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void testServlet(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String message = "servlet dispatcher works";
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("test.jsp");
        dispatcher.forward(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> userList = userDAO.listAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UsersList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserForm.jsp");
        dispatcher.forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int age = Integer.parseInt(request.getParameter("age"));
        User newUser = new User(name, surname, age);
        userDAO.addUser(newUser);
        response.sendRedirect("listusers");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.getUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserForm.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int age = Integer.parseInt(request.getParameter("age"));

        User user = new User(id, name, surname, age);
        userDAO.updateUser(user);
        response.sendRedirect("listusers");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        User user = new User(id);
        userDAO.deleteUser(user);
        response.sendRedirect("listusers");

    }


}