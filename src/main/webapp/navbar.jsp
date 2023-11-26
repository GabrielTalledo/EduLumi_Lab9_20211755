<%@ page import="com.edulumi.edulumi.Beans.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String currentPage = request.getParameter("currentPage"); %>
<% Usuario usuarioRol = (Usuario) request.getSession().getAttribute("usuario");%>
<!-- Start Header Area -->
<header class="header style2 navbar-area">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-12">
                <div class="nav-inner">
                    <nav class="navbar navbar-expand-lg">
                        <a class="navbar-brand" href="#">
                            <img src="assets/images/logo/logo.svg" alt="Logo">
                        </a>
                        <button class="navbar-toggler mobile-menu-btn" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                            <span class="toggler-icon"></span>
                            <span class="toggler-icon"></span>
                            <span class="toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse sub-menu-bar" id="navbarSupportedContent">
                            <div>
                                <h6>Bienvenido, <div style="color: #0EDC8D"><%=usuarioRol.getNombre()%></div></h6>
                            </div>
                            <ul id="nav" class="navbar-nav ms-auto">
                                <%if(usuarioRol.getRol().getNombre().equals("Decano")){%>
                                <li class="nav-item"><a class="<%=currentPage.equals("docentes") ? "active" : ""%>" href="<%=request.getContextPath()%>/DocentesServlet">Docentes</a></li>
                                <li class="nav-item"><a class="<%=currentPage.equals("cursos") ? "active" : ""%>" href="<%=request.getContextPath()%>/CursosServlet">Cursos</a></li>
                                <%}else{%>
                                <li class="nav-item"><a class="<%=currentPage.equals("evaluaciones") ? "active" : ""%>" href="<%=request.getContextPath()%>/EvaluacionesServlet">Evaluaciones</a></li>
                                <%}%>
                            </ul>
                            <div class="button ms-lg-5 my-2">
                                <form method="get" action="<%=request.getContextPath()%>/?action=logout">
                                    <button class="btn" type="submit">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box-arrow-right" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"/>
                                            <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                                        </svg>
                                    </button>
                                </form>
                            </div>
                        </div> <!-- navbar collapse -->
                    </nav> <!-- navbar -->
                </div>
            </div>
        </div> <!-- row -->
    </div> <!-- container -->
</header>
<!-- End Header Area -->

