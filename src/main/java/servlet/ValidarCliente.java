package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import util.HashUtil; // Asegúrate de tener esta clase en tu proyecto con BCrypt

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ValidarCliente", urlPatterns = {"/ValidarCliente"})
public class ValidarCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        String dni = request.getParameter("ndniClie");
        String passIngresada = request.getParameter("passClie");

        if (dni == null || passIngresada == null) {
            response.getWriter().println("Error: Faltan parámetros (ndniClie o passClie)");
            return;
        }

        ClienteJpaController controller = new ClienteJpaController();
        Cliente cliente = controller.buscarClientePorDni(dni);

        if (cliente != null && HashUtil.checkPassword(passIngresada, cliente.getPassClie())) {
            // ✅ Guardar cliente en sesión
            request.getSession().setAttribute("clienteLogueado", cliente);

            // ✅ Redirigir a la página con tabla de clientes
            response.sendRedirect("tables.html");
        } else {
            // ❌ Mostrar mensaje de error si falla login
            response.getWriter().println("Login incorrecto: Usuario o contraseña inválidos");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para validar clientes con contraseña cifrada usando BCrypt";
    }
}
