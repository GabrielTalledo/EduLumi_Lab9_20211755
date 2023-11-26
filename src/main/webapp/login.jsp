<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% String email = (String) request.getAttribute("email");%>
<% String error = (String) request.getAttribute("error");%>
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
                        <a class="navbar-brand pt-lg-4" href="index.html">
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
<div class="breadcrumbs overlay" style="background-image: url('assets/images/loginBanner.jpg')">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-8 offset-lg-2 col-md-12 col-12">
                <div class="breadcrumbs-content">
                    <h1 class="page-title">Login</h1>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Breadcrumbs -->

<!-- start login section -->
<section class="login section">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-12">
                <div class="form-head">
                    <h4 class="title">Iniciar sesión</h4>
                    <form method="post" action="<%=request.getContextPath()%>">
                        <div class="form-group">
                            <label>Correo electrónico</label>
                            <input class="margin-5px-bottom" type="email" id="exampleInputEmail1" <%if(email!=null){%>value="<%=email%>"<%}%> placeholder="telito@pucp.edu.pe" required>
                        </div>
                        <div class="form-group">
                            <label>Contraseña</label>
                            <input class="margin-5px-bottom" type="password" id="exampleInputPassword1"
                                   placeholder="telito" required>
                        </div>
                        <% if(error!=null){%>
                        <div class="form-group">
                            <p style="color: red"><%=error%>></p>
                        </div>
                        <%}%>
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
            <div class="row">
                <div class="col-lg-4 col-md-4 col-12">
                    <!-- Single Widget -->
                    <div class="f-about single-footer">
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
                <div class="col-lg-8 col-md-8 col-12">
                    <div class="row">
                        <div class="col-lg-4 col-md-6 col-12">

                        </div>
                        <div class="col-lg-4 col-md-6 col-12">

                        </div>
                        <div class="col-lg-4 col-md-12 col-12">

                        </div>
                    </div>
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