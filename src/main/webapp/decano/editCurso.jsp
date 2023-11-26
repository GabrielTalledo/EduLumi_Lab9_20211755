<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page import="com.edulumi.edulumi.Dtos.Decano" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.edulumi.edulumi.Dtos.Docente" %>
<%@ page import="com.edulumi.edulumi.Dtos.CursoDocente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<% Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");%>
<% CursoDocente curso = (CursoDocente) request.getAttribute("curso");%>
<% String error = (String) request.getSession().getAttribute("error");%>

<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>EduLumi - Editar Curso</title>
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
<div class="breadcrumbs overlay" style="background-image: url('<%=request.getContextPath()%>/assets/images/cursos3.jpg')">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="breadcrumbs-content">
                    <h1 class="page-title">Editar curso</h1>
                    <p>Por favor, modifique la información que requiera de la materia elegida.</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Breadcrumbs -->

<section class="login section">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-12">
                <div class="form-head">
                    <h4 class="title">Información del curso</h4>
                    <form method="post" action="<%=request.getContextPath()%>/CursosServlet?action=edit">
                        <div class="form-group">
                            <label>Nombre</label>
                            <input class="margin-5px-bottom" name="editNombre" type="text" id="exampleInputEmail1" placeholder="" value="<%=curso.getNombre()==null?"":curso.getNombre()%>" required>
                        </div>
                        <input name="idCurso" hidden value="<%=curso.getIdCurso()%>">
                        <% if(error!=null){%>
                        <div class="form-group">
                            <p style="color: red"><%=error%>.</p>
                        </div>
                        <%request.getSession().removeAttribute("error");}%>
                        <div class="button">
                            <button type="submit" class="btn">Editar curso</button>
                        </div>
                    </form>
                    <div class="button col-12 my-3 d-flex justify-content-center">
                        <a class="btn button" href="<%=request.getContextPath()%>/CursosServlet">Regresar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

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
