package es.albarregas.controllers;

import es.albarregas.beans.CorreoElectronico;
import es.albarregas.beans.Profesor;
import es.albarregas.daofactory.DAOFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.CalendarConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import es.albarregas.dao.IGenericoDAO;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "Conclusion", urlPatterns = {"/conclusion"})
public class Conclusion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO pdao = daof.getGenericoDAO();

        Profesor profesor = new Profesor();

        String url = null;
        switch (request.getParameter("op")) {

            case "update":
              try {
                //Creo una coleccion de correos de tipo Set
                Set<CorreoElectronico> colcorreos = new HashSet();
               
                //Recupero los correo y los id de esos correo
                String[] coleccion = request.getParameterValues("direccionCorreo");
                String[] idcorreo = request.getParameterValues("idCorreo");
                int contador = 0;
                //Los meto en bucle para que se asocien cada id con su correo
                for (String p : coleccion) {

                    if (p != null && !p.equals("")) {   
                        CorreoElectronico correo = new CorreoElectronico();
                        if (contador < idcorreo.length) {
                            correo.setIdCorreo(Integer.parseInt(idcorreo[contador]));                           
                        }
                        correo.setDireccionCorreo(p);
                        colcorreos.add(correo);
                    }
                        contador++;
                }
                //Creo el objeto profesor con la coleccion actualizado y lo mando por update
                BeanUtils.populate(profesor, request.getParameterMap());
                BeanUtils.copyProperty(profesor, "correos", colcorreos);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
            pdao.update(profesor);
            url = "index.jsp";
            break;

            case "borrarCorreo":
                //Recupero el value del correo seleccionado para eliminar
                String todo = request.getParameter("registro");
                String[] parts = todo.split(",");
                //Hago splir para separar el id de profesor de lo que es la direccion de Correo
                //Recupero el profesor sobre el que se quiere trabajar
                profesor = (Profesor) pdao.getOne(Profesor.class, Integer.parseInt(parts[0]));
                //Recupero su coleccion de correo
                Set<CorreoElectronico> colcorreos = new HashSet();
                colcorreos = profesor.getCorreos();
                //Creo un objeto correo
                CorreoElectronico correo = new CorreoElectronico();
                correo.setDireccionCorreo(parts[1]);
                //Lo elimino de la coleccion y actualizo profesor
                colcorreos.remove(correo);
                profesor.setCorreos(colcorreos);
                pdao.update(profesor);
                url = "index.jsp";
                break;

        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
