package es.albarregas.controllers;

import es.albarregas.beans.CorreoElectronico;
import es.albarregas.beans.Profesor;
import es.albarregas.daofactory.DAOFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import es.albarregas.dao.IGenericoDAO;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "Controlador", urlPatterns = {"/control"})
public class Controlador extends HttpServlet {

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
            throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO pdao = daof.getGenericoDAO();

        Profesor profesor = new Profesor();
        String url = null;
        switch (request.getParameter("op")) {
            case "add":
                
                try {
                //Creo una coleccion set
                Set<CorreoElectronico> colcorreos = new HashSet();
                //Recupero las coleccion de correo
                String[] coleccion = request.getParameterValues("direccionCorreo");
                //Recorro las colecciones iterando el correo y añadiendo el nuevo objeto correo a la coleccion
                for (String p : coleccion) {
                    if (p != null && p != "") {
                        CorreoElectronico correo = new CorreoElectronico();
                        correo.setDireccionCorreo(p);
                        colcorreos.add(correo);
                    }
                }

                BeanUtils.populate(profesor, request.getParameterMap());
                BeanUtils.copyProperty(profesor, "correos", colcorreos);

            } catch (IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
            pdao.add(profesor);
            url = "index.jsp";
            break;

            case "delete":
                //Sacamos el codigo que es la pk
                String Cod = request.getParameter("registro");
                profesor = (Profesor) pdao.getOne(profesor.getClass(), Integer.parseInt(Cod));
                pdao.delete(profesor);
                url = "index.jsp";
                break;
            case "update":
                Cod = request.getParameter("registro");
                //Sacamos el profesor especifico y lo mandamos como atributo
                profesor = (Profesor) pdao.getOne(profesor.getClass(), Integer.parseInt(Cod));
                request.setAttribute("profesor", profesor);
                url = "JSP/formularioActualizar.jsp";

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
        try {
            processRequest(request, response);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
