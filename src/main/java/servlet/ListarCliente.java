package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListarCliente", urlPatterns = {"/ListarCliente"})
public class ListarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión (opcional)
        Object clienteSesion = request.getSession().getAttribute("clienteLogueado");
        if (clienteSesion == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Debe iniciar sesión\"}");
            return;
        }

        // Obtener clientes
        ClienteJpaController controller = new ClienteJpaController();
        List<Cliente> clientes = controller.findClienteEntities();

        // Convertir a JSON
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(clientes);
        response.getWriter().write(json);
    }
}
