package com.edulumi.edulumi.Controllers;

import com.edulumi.edulumi.Beans.Evaluacion;
import com.edulumi.edulumi.Beans.Semestre;
import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Daos.*;
import com.edulumi.edulumi.Dtos.CursoDocente;
import com.edulumi.edulumi.Dtos.Decano;
import com.edulumi.edulumi.Dtos.Docente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EvaluacionesServlet", value = "/EvaluacionesServlet")
public class EvaluacionesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "list" : request.getParameter("action");

        RequestDispatcher view;
        DaoDocente daoDocente = new DaoDocente();
        DaoUsuario daoUsuario = new DaoUsuario();
        DaoCurso daoCurso = new DaoCurso();
        DaoEvaluacion daoEvaluacion = new DaoEvaluacion();
        DaoSemestre daoSemestre = new DaoSemestre();

        //Variables auxiliares:
        String idEvaluacionStr;
        int idEvaluacion = 0;
        Evaluacion evaluacionAux = new Evaluacion();

        switch (action) {
            case "list":
                ArrayList<Semestre> listaSemestres = daoSemestre.getListaSemestres();
                request.setAttribute("listaSemestres", listaSemestres);
                try{
                    int semestreFiltro = Integer.parseInt(request.getParameter("semestreFiltro"));
                    request.setAttribute("semestreFiltro",semestreFiltro);
                    request.setAttribute("listaEvaluaciones",daoEvaluacion.getListaEvaluaciones(((Docente)usuario).getCurso().getIdCurso(),semestreFiltro));
                }catch (NumberFormatException ex){
                    Integer idSemestre = 0;
                    for(Semestre sem: listaSemestres){
                        if(sem.getHabilitado()){
                            idSemestre = sem.getIdSemestre();
                        }
                    }
                    request.setAttribute("semestreFiltro",idSemestre);
                    request.setAttribute("listaEvaluaciones",daoEvaluacion.getListaEvaluaciones(((Docente)usuario).getCurso().getIdCurso(),idSemestre));
                }
                view = request.getRequestDispatcher("docente/listEvaluacion.jsp");
                view.forward(request, response);
                break;
            case "new":
                view = request.getRequestDispatcher("docente/newEvaluacion.jsp");
                view.forward(request, response);
                break;
            case "edit":
                if (request.getParameter("id") != null) {
                    idEvaluacionStr = request.getParameter("id");
                    try {
                        idEvaluacion = Integer.parseInt(idEvaluacionStr);
                        evaluacionAux = daoEvaluacion.getEvaluacionPorId(idEvaluacion);
                        if (evaluacionAux != null) {
                            request.setAttribute("evaluacion",evaluacionAux);
                            view = request.getRequestDispatcher("docente/editEvaluacion.jsp");
                            view.forward(request, response);
                        } else {
                            response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                }

                break;
            case "delete":
                if (request.getParameter("id") != null) {
                    idEvaluacionStr = request.getParameter("id");
                    try {
                        idEvaluacion = Integer.parseInt(idEvaluacionStr);
                        evaluacionAux = daoEvaluacion.getEvaluacionPorId(idEvaluacion);

                        if (evaluacionAux != null && evaluacionAux.getSemestre().getIdSemestre()==daoSemestre.getUltimoSemestreHabilitado()) {
                            daoEvaluacion.deleteEvaluacion(idEvaluacion);
                            session.setAttribute("msg","Evaluación eliminado exitosamente!!!");
                            response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                        } else {
                            session.setAttribute("msg","Evaluación no pertenece al semestre activado actual!!!");
                            response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                }

                break;

            default:
                response.sendRedirect("EvaluacionesServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "new" : request.getParameter("action");

        DaoUsuario daoUsuario = new DaoUsuario();
        DaoSemestre daoSemestre = new DaoSemestre();
        DaoEvaluacion daoEvaluacion = new DaoEvaluacion();

        // Variables auxiliares:
        int idSemestreHabilitado = daoSemestre.getUltimoSemestreHabilitado();
        String idEvaluacionStr;
        int idEvaluacion = 0;;
        int nota = 0;
        String nombre;
        String codigo;
        String email;
        String notaStr;

        switch (action) {

            case "new":
                nombre = request.getParameter("newNombre");
                codigo = request.getParameter("newCodigo");
                email = request.getParameter("newEmail");
                notaStr = request.getParameter("newNota");

                if (nombre == null || codigo == null || email == null || notaStr == null || nombre.isEmpty() || codigo.isEmpty() || email.isEmpty() || notaStr.isEmpty()) {
                    session.setAttribute("error", "Los campos no pueden estar vacíos");
                    response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet?action=new");
                } else {
                    try {
                        nota = Integer.parseInt(notaStr);
                        if (idSemestreHabilitado==0) {
                            session.setAttribute("msg", "No existe algún semestre habilitado, por lo que no se logró registrar la evaluación");
                            response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                        } else {
                            daoEvaluacion.newEvaluacion(nombre,codigo,email,nota,((Docente)usuario).getCurso().getIdCurso(),idSemestreHabilitado);
                            session.setAttribute("msg", "Curso creado exitosamente!!!");
                            response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        session.setAttribute("error", "Por favor, introduzca una nota de valor entero");
                        response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet?action=new");
                    }
                }

                break;

            case "edit":

                nombre = request.getParameter("newNombre");
                codigo = request.getParameter("newCodigo");
                email = request.getParameter("newEmail");
                notaStr = request.getParameter("newNota");
                idEvaluacionStr = request.getParameter("idEvaluacion");

                request.setAttribute("nombre",nombre);
                request.setAttribute("codigo",codigo);
                request.setAttribute("email",email);
                request.setAttribute("nota",notaStr);

                if (nombre == null || codigo == null || email == null || notaStr == null || nombre.isEmpty() || codigo.isEmpty() || email.isEmpty() || notaStr.isEmpty()) {
                    session.setAttribute("error", "Los campos no pueden estar vacíos");
                    response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet?action=new");
                } else {
                    try {
                        nota = Integer.parseInt(notaStr);
                        idEvaluacion = Integer.parseInt(idEvaluacionStr);
                        if (daoEvaluacion.getEvaluacionPorId(idEvaluacion)==null) {
                            session.setAttribute("msg", "No existe la evaluación seleccionada!");
                            response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                        } else {
                            daoEvaluacion.editEvaluacion(nombre,codigo,email,nota,idEvaluacion);
                            session.setAttribute("msg", "Evaluación editada exitosamente!!!");
                            response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        session.setAttribute("error", "Por favor, introduzca en los campos correspondientes valores enteros");
                        response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet?action=new");
                    }
                }

                break;
        }
    }
}
