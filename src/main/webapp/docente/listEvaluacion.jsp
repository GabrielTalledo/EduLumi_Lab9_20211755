<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page import="com.edulumi.edulumi.Dtos.Decano" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.edulumi.edulumi.Dtos.Docente" %>
<%@ page import="com.edulumi.edulumi.Beans.Curso" %>
<%@ page import="com.edulumi.edulumi.Dtos.CursoDocente" %>
<%@ page import="com.edulumi.edulumi.Beans.Evaluacion" %>
<%@ page import="com.edulumi.edulumi.Beans.Semestre" %>
<%@ page import="com.edulumi.edulumi.Daos.DaoSemestre" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");%>
<% ArrayList<Evaluacion> listaEvaluaciones = (ArrayList<Evaluacion>) request.getAttribute("listaEvaluaciones");%>
<% String msg = (String) request.getSession().getAttribute("msg");%>
<% Integer semestreFiltro = (Integer) request.getAttribute("semestreFiltro");%>
<% ArrayList<Semestre> listaSemestres = (ArrayList<Semestre>) request.getAttribute("listaSemestres");%>

<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>EduLumi - Evaluaciones</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/favicon.svg" />
    <!-- Place favicon.ico in the root directory -->

    <!-- Web Font -->
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">


    <!-- ========================= CSS here ========================= -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/LineIcons.2.0.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/animate.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/tiny-slider.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/glightbox.min.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css" />

</head>

<body>
<!--[if lte IE 9]>
<p class="browserupgrade">
    You are using an <strong>outdated</strong> browser. Please
    <a href="https://browsehappy.com/">upgrade your browser</a> to improve
    your experience and security.
</p>
<![endif]-->

<!-- Preloader -->
<div class="preloader">
    <div class="preloader-inner">
        <div class="preloader-icon">
            <span></span>
            <span></span>
        </div>
    </div>
</div>
<!-- /End Preloader -->

<jsp:include page="/navbar.jsp">
    <jsp:param name="currentPage" value="evaluaciones"/>
</jsp:include>

<!-- Start Breadcrumbs -->
<div class="breadcrumbs overlay" style="background-image: url('<%=request.getContextPath()%>/assets/images/evaluaciones1.jpg')">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="breadcrumbs-content">
                    <h1 class="page-title">Evaluaciones</h1>
                    <p>Visualiza y gestiona las evaluaciones derivadas del curso de <div style="color: #0EDC8D">"<%=((Docente)usuario).getCurso().getCodigo()%>: <%=((Docente)usuario).getCurso().getNombre()%>"</div><div style="color:white;"> de la facultad de </div><div style="color: #0EDC8D">"<%=((Docente)usuario).getCurso().getFacultad().getNombre()%>".</div></p><p>Podrás editar la información de cada evaluación y eliminarlas si es necesario, con el fin de que se refleje con precisión el rendimiento académico de los estudiantes del curso.</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Breadcrumbs -->

<!-- Start About Us Area -->
<section class="about-us section">
    <div class="container">
        <div class="d-flex bd-highlight mb-4">
            <div class="me-auto p-2 bd-highlight"><h2><u>Listado:</u>
            </h2></div>
            <form method="get" action="<%=request.getContextPath()%>/EvaluacionesServlet">
            <div class="p-2 bd-highlight align-self-center">
                <select name="semestreFiltro" id="semestre" class="form-select form-select-sm">
                    <% for(Semestre sem: listaSemestres){%>
                    <option value=<%=sem.getIdSemestre()%> <%=sem.getIdSemestre()==semestreFiltro?"selected":""%>><%=sem.getNombre()%>
                    </option>
                    <%}%>
                </select>
            </div>

            <div class="p-2 bd-highlight button">
                <button type="submit" class="btn">Filtrar</button>
            </div>
            </form>
        </div>
        <% if(msg!=null){%>
        <div class="row mb-3">
            <p style="color: #0EDC8D"><%=msg%>.</p>
        </div>
        <%request.getSession().removeAttribute("msg");}%>
        <div class="row">
            <div class="col-12 table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nombre Estudiante</th>
                        <th scope="col">Código Estudiante</th>
                        <th scope="col">Correo Estudiante</th>
                        <th scope="col">Nota</th>
                        <th scope="col">Semestre</th>
                        <th scope="col">Fecha de registro</th>
                        <th scope="col">Fecha de edición</th>
                        <th scope="col"></th>
                        <th scope="col"></th>

                    </tr>
                    </thead>
                    <tbody>
                    <% for(Evaluacion e: listaEvaluaciones){%>
                    <tr>
                        <td><%=listaEvaluaciones.indexOf(e)+1%></td>
                        <td><%=e.getNombreEstudiantes()%></td>
                        <td><%=e.getCodigoEstudiantes()%></td>
                        <td><%=e.getCorreoEstudiantes()%></td>
                        <td><%=e.getNota()%></td>
                        <td><%=e.getSemestre().getNombre()%></td>
                        <td><%=e.getFechaRegistro()%></td>
                        <td><%=e.getFechaEdicion()%></td>
                        <th scope="col">
                            <a href="<%=request.getContextPath()%>/EvaluacionesServlet?action=edit&id=<%=e.getIdevaluaciones()%>" type="button" class="btn btn-dark">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                    <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                </svg>
                            </a>
                        </th>
                        <th scope="col">
                            <%if(e.getSemestre().getIdSemestre()==new DaoSemestre().getUltimoSemestreHabilitado()){%>
                            <a onclick="return confirm('¿Estas seguro de borrar?');"
                               href="<%=request.getContextPath()%>/EvaluacionesServlet?action=delete&id=<%=e.getIdevaluaciones()%>"
                               type="button" class="btn btn-danger"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                            </svg>
                            </a>
                            <%}%>
                        </th>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
<!-- /End About Us Area -->

<!-- Start Call To Action Area -->
<section class="call-action section overlay" style="background-image: url('<%=request.getContextPath()%>/assets/images/evaluacionesBanner.jpg')">
    <div class="container">
        <div class="row ">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="call-content">
                    <span>Gestión de Evaluaciones</span>
                    <h2>Registrar una nueva evaluación en la plataforma</h2>
                    <p>Si desea ingresar un exámen ya calificado, presione el botón y registre tanto la información del mismo como la del estudiante.</p>
                    <div class="button">
                        <a href="<%=request.getContextPath()%>/EvaluacionesServlet?action=new" class="btn">Continuar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- /End Call To Action Area -->

<jsp:include page="/footer.jsp"/>

<!-- ========================= scroll-top ========================= -->
<a href="#" class="scroll-top btn-hover">
    <i class="lni lni-chevron-up"></i>
</a>

<!-- ========================= JS here ========================= -->
<script src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/count-up.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/wow.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/tiny-slider.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/glightbox.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/main.js"></script>
<script type="text/javascript">
    //====== Clients Logo Slider
    tns({
        container: '.client-logo-carousel',
        slideBy: 'page',
        autoplay: true,
        autoplayButtonOutput: false,
        mouseDrag: true,
        gutter: 15,
        nav: false,
        controls: false,
        responsive: {
            0: {
                items: 1,
            },
            540: {
                items: 3,
            },
            768: {
                items: 4,
            },
            992: {
                items: 4,
            },
            1170: {
                items: 6,
            }
        }
    });
    //========= glightbox
    GLightbox({
        'href': 'https://www.youtube.com/watch?v=r44RKWyfcFw&fbclid=IwAR21beSJORalzmzokxDRcGfkZA1AtRTE__l5N4r09HcGS5Y6vOluyouM9EM',
        'type': 'video',
        'source': 'youtube', //vimeo, youtube or local
        'width': 900,
        'autoplayVideos': true,
    });
</script>
</body>

</html>
