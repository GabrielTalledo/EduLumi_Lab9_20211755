<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% String email = (String) request.getSession().getAttribute("email");%>
<% String error = (String) request.getSession().getAttribute("error");%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>EduLumi - Login</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.svg" />
    <!-- Place favicon.ico in the root directory -->

    <!-- Web Font -->
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">

    <!-- ========================= CSS here ========================= -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/LineIcons.2.0.css" />
    <link rel="stylesheet" href="assets/css/animate.css" />
    <link rel="stylesheet" href="assets/css/tiny-slider.css" />
    <link rel="stylesheet" href="assets/css/glightbox.min.css" />
    <link rel="stylesheet" href="assets/css/main.css" />

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

<!-- Start Header Area -->
<header class="header style2 navbar-area" style="height: 80px">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-12">
                <div class="nav-inner">
                    <nav class="navbar navbar-expand-lg">
                        <a class="navbar-brand pt-lg-4" href="#">
                            <img src="assets/images/logo/logo.svg" alt="Logo">
                        </a>
                        <div class="collapse navbar-collapse sub-menu-bar" id="navbarSupportedContent">
                            <ul id="nav" class="navbar-nav ms-auto">
                            </ul>
                        </div> <!-- navbar collapse -->
                    </nav> <!-- navbar -->
                </div>
            </div>
        </div> <!-- row -->
    </div> <!-- container -->
</header>
<!-- End Header Area -->

<!-- Start Breadcrumbs -->
<section class="hero-area">
    <div class="hero-slider">
        <!-- Single Slider -->
        <div class="hero-inner overlay" style="background-image: url('<%=request.getContextPath()%>/assets/images/loginBanner.jpg')">
            <div class="container">
                <div class="row ">
                    <div class="col-lg-8 offset-lg-2 col-md-12 co-12">
                        <div class="home-slider">
                            <div class="hero-text">
                                <h5 class="wow fadeInUp" data-wow-delay=".3s">Descubre la diversidad académica en un solo lugar</h5>
                                <h1 class="wow fadeInUp" data-wow-delay=".5s">Plataforma educativa<br> para el seguimiento de notas</h1>
                                <p class="wow fadeInUp" data-wow-delay=".7s">Herramienta centralizada, diseñada para brindarte información detallada sobre los cursos, docentes y evaluaciones asignadas en diversas universidades.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ End Single Slider -->
    </div>
</section>
<!-- End Breadcrumbs -->
<section class="about-us section">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-12">
                <div class="about-left">
                    <div class="about-title align-left">
                        <span class="wow fadeInDown" data-wow-delay=".2s">Sobre nosotros</span>
                        <h2 class="wow fadeInUp" data-wow-delay=".4s">Plataforma EduLumi</h2>
                        <p class="wow fadeInUp" data-wow-delay=".6s">Bienvenido a nuestra plataforma integral de gestión académica, donde la información clave de cursos, docentes y evaluaciones se encuentra al alcance de tus dedos. Diseñada para ofrecer una experiencia fácil y completa, nuestra plataforma te permite explorar y gestionar de manera eficiente los programas académicos de diversas facultades en universidades destacadas.<br>Ya sea que estés buscando información sobre cursos específicos, docentes expertos o evaluaciones asignadas, aquí encontrarás una herramienta centralizada que simplifica tu búsqueda y facilita la toma de decisiones informadas en la trayectoria académica de los estudiantes del país.</p>
                        <p class="qote wow fadeInUp" data-wow-delay=".8s"><p class="qote wow fadeInUp" data-wow-delay=".8s" style="color: #0EDC8D">¡Bienvenido a una experiencia de administración académica sin fronteras!</p></p>
                        <div class="button wow fadeInUp" data-wow-delay="1s">
                            <a href="https://bit.ly/3BlS71b"
                               class="glightbox video btn"> Reproducir bienvenida<i class="lni lni-play"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-12">
                <div class="about-right wow fadeInRight" data-wow-delay=".4s">
                    <img src="assets/images/loginWelcome.jpg" alt="#" style="box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2); border: 2px solid #ddd;border-radius: 8px; max-width: 536px; max-height:471px">
                </div>
            </div>
        </div>
    </div>
</section>
<!-- start login section -->
<section class="login section">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-12">
                <div class="form-head">
                    <h4 class="title">Iniciar sesión</h4>
                    <form method="post" action="<%=request.getContextPath()%>/">
                        <div class="form-group">
                            <label>Correo electrónico</label>
                            <input class="margin-5px-bottom" name="inputEmail" type="email" id="exampleInputEmail1" <%if(email!=null){%>value="<%=email%>"<%request.getSession().removeAttribute("email");}%> placeholder="telito@pucp.edu.pe" required>
                        </div>
                        <div class="form-group">
                            <label>Contraseña</label>
                            <input class="margin-5px-bottom" name="inputPassword" type="password" id="exampleInputPassword1"
                                   placeholder="telito" required>
                        </div>
                        <% if(error!=null){%>
                        <div class="form-group">
                            <p style="color: red"><%=error%>.</p>
                        </div>
                        <%request.getSession().removeAttribute("error");}%>
                        <div class="button">
                            <button type="submit" class="btn">Log In</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- end login section -->

<!-- Start Footer Area -->
<footer class="footer style2">
    <!-- Start Middle Top -->
    <div class="footer-middle">
        <div class="container">
            <div class="row justify-content-center align-items-center">
                <div class="col-lg-4 col-md-4 col-12">
                    <!-- Single Widget -->
                    <div class="single-footer">
                        <div class="logo">
                            <a href="index.html"><img src="assets/images/logo/logo.svg" alt="Logo"></a>
                        </div>
                        <p>Plataforma educativa implementada para facilitar el seguimiento del rendimiento de los estudiantes de las distintas universidades del país.</p>
                        <div class="footer-social">
                            <ul>
                                <li><a href="https://www.facebook.com/fibratoxica.telecom/"><i class="lni lni-facebook-original"></i></a></li>
                                <li><a href="https://www.instagram.com/fibra.toxic/?hl=es"><i class="lni lni-instagram"></i></a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- End Single Widget -->
                </div>
            </div>
        </div>
    </div>
    <!--/ End Footer Middle -->
    <!-- Start Footer Bottom -->
    <div class="footer-bottom">
        <div class="container">
            <div class="inner">
                <div class="row">
                    <div class="col-12">
                        <div class="left">
                            <p>Plataforma creada por<a href="https://www.youtube.com/watch?v=zu2Eaw6Ohxc" rel="nofollow"
                                                       target="_blank">©Gabriel™</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End Footer Middle -->
</footer>
<!--/ End Footer Area -->

<!-- ========================= scroll-top ========================= -->
<a href="#" class="scroll-top btn-hover">
    <i class="lni lni-chevron-up"></i>
</a>

<!-- ========================= JS here ========================= -->
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/count-up.min.js"></script>
<script src="assets/js/wow.min.js"></script>
<script src="assets/js/tiny-slider.js"></script>
<script src="assets/js/glightbox.min.js"></script>
<script src="assets/js/main.js"></script>
</body>

</html>
