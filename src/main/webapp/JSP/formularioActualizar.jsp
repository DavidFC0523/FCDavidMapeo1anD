<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page pageEncoding="UTF-8" contentType="text/html" />

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar</title>
        <link rel="stylesheet" type="text/css" href="${estilo}" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    </head>
    <body>
        <h2>Actualizaci&oacute;n de datos</h2>
        <br>
        <div class="row justify-content-center" >

            <form method="post" action="conclusion">
                <input type="hidden" name="op" value="update">
                <table  class="table table-striped" style="width: 500px" id="formulario">

                    <input type="hidden" name="idProf" value="${profesor.idProf}">

                    <tr>
                        <td>Nombre Profesor</td>
                        <td><input type="text" name="nombre" value="${profesor.nombre}" required/></td>
                    </tr>
                    <c:forEach var="correo" items="${profesor.correos}">
                        <tr>
                            <td>Correo</td>
                        <input type="hidden" name="idCorreo" value="${correo.idCorreo}"">
                        <td><input type="text" name="direccionCorreo" value="${correo.direccionCorreo}"/></td>
                        </tr>
                    </c:forEach>

                </table> 
                <button type="button" name="Anadir" id="Anadir" class="boton">Añadir</button>  
                <tr>
                    <td colspan="2"><input type="submit" name="enviar" value="Enviar" class="btn-primary" /></td>
                </tr> 

            </form>
        </div>
    </body>

    <script src="./JS/scripts.js"></script> 
</html>
