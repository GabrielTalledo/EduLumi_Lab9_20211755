<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page import="com.edulumi.edulumi.Dtos.Decano" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.edulumi.edulumi.Dtos.Docente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");%>
<% ArrayList<Usuario> listaDocentes= (ArrayList<Usuario>) request.getAttribute("listaDocentes");%>
<% String msg = (String) request.getSession().getAttribute("msg");%>

<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>EduLumi - Docentes</title>
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
    <jsp:param name="currentPage" value="docentes"/>
</jsp:include>

<!-- Start Breadcrumbs -->
<div class="breadcrumbs overlay" style="background-image: url('<%=request.getContextPath()%>/assets/images/docentes1.jpg')">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="breadcrumbs-content">
                    <h1 class="page-title wow fadeInDown" data-wow-delay=".2s">Docentes</h1>
                    <p class="wow fadeInUp" data-wow-delay=".4s">Gestiona y visualiza la información, materias y acceso a la plataforma del personal docente que conforma la facultad de <div style="color: #0EDC8D">"<%=((Decano)usuario).getFacultad().getNombre()%>".</div></p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Breadcrumbs -->

<!-- Start About Us Area -->
<section class="about-us section">
    <%if(listaDocentes.isEmpty()){%>
    <div class="container">
        <h2 class="wow fadeInUp" data-wow-delay=".4s" style="visibility: visible; animation-delay: 0.4s; animation-name: fadeInUp;">No posee docentes registrados.</h2>
    </div>
    <%}else{%>
    <div class="container">
        <div class="row mb-4">
            <h2><u>Listado:</u></h2>
        </div>

        <% if(msg!=null){%>
        <div class="row mb-3">
            <p style="color: #0EDC8D" class="wow fadeInUp" data-wow-delay=".4s"><%=msg%>.</p>
        </div>
        <%request.getSession().removeAttribute("msg");}%>
        <div class="row">
            <div class="col-12 table-responsive">
                <table class="table wow fadeInUp" data-wow-delay=".8s">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Correo</th>
                        <th scope="col">Curso</th>
                        <th scope="col">Último ingreso</th>
                        <th scope="col">Cantidad de ingresos</th>
                        <th scope="col">Fecha de registro</th>
                        <th scope="col">Fecha de edición</th>
                        <th scope="col"></th>
                        <th scope="col"></th>

                    </tr>
                    </thead>
                    <tbody>
                    <% for(Usuario d: listaDocentes){%>
                    <tr>
                        <td><%=listaDocentes.indexOf(d)+1%></td>
                        <td><%=d.getNombre()%></td>
                        <td><%=d.getCorreo()%></td>
                        <td><%=((Docente)d).getCurso().getNombre()==null?"Sin materia":((Docente)d).getCurso().getNombre()%></td>
                        <td><%=d.getUltimoIngreso()==null?"No ingresó aún":d.getUltimoIngreso()%></td>
                        <td><%=d.getCantidadIngresos()%></td>
                        <td><%=d.getFechaRegistro()%></td>
                        <td><%=d.getFechaEdicion()%></td>
                        <th scope="col">
                            <a href="<%=request.getContextPath()%>/DocentesServlet?action=edit&id=<%=d.getIdUsuario()%>" type="button" class="btn btn-dark">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                                    <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                </svg>
                            </a>
                        </th>
                        <th scope="col">
                            <%if(((Docente) d).getCurso().getIdCurso()==0){%>
                            <a onclick="return confirm('¿Estas seguro de borrar?');"
                               href="<%=request.getContextPath()%>/DocentesServlet?action=delete&id=<%=d.getIdUsuario()%>"
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
    <%}%>
</section>
<!-- /End About Us Area -->

<!-- Start Call To Action Area -->
<section class="call-action section overlay">
    <div class="container">
        <div class="row ">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="call-content">
                    <span>Gestión de cuentas</span>
                    <h2>Registrar nuevo docente en la plataforma</h2>
                    <p>Si desea ingresar un nuevo maestro que dictará en su facultad, presione el botón y registre los
                        datos de acceso del mismo.</p>
                    <div class="button">
                        <a href="<%=request.getContextPath()%>/DocentesServlet?action=new" class="btn">Continuar</a>
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
