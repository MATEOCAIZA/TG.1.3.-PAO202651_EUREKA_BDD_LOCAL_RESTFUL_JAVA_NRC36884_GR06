package ec.edu.monster.ws;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@Path("coreBancario")
public class CoreBancarioResource {

    @Context
    private UriInfo context;

    public CoreBancarioResource() {
    }

    @GET
    @Path("/movimientos/{cuenta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovimientos(@PathParam("cuenta") String cuenta) {
        List<Movimiento> lista;
        try {
            EurekaService service = new EurekaService();
            lista = service.leerMovimientos(cuenta);
            if (lista.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"No movements found for account: " + cuenta + "\"}")
                        .build();
            }

            // Convierte los movimientos a JSON
            String movimientosJSON = service.convertirMovimientosAJSON(lista);

            return Response.ok(movimientosJSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("/deposito")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarDeposito(@FormParam("cuenta") String cuenta, @FormParam("importe") double importe) {
        int estado;
        String codEmp = "0001"; // Código del empleado, esto puede ser dinámico
        try {
            EurekaService service = new EurekaService();
            service.registrarDepósito(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok("{\"estado\":" + estado + "}").build();
    }

    @POST
    @Path("/retiro")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarRetiro(@FormParam("cuenta") String cuenta, @FormParam("importe") double importe) {
        int estado;
        String codEmp = "0001"; // Código del empleado, esto puede ser dinámico
        try {
            EurekaService service = new EurekaService();
            service.registrarRetiro(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok("{\"estado\":" + estado + "}").build();
    }

    @POST
    @Path("/transferencia")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTransferencia(
            @FormParam("cuentaOrigen") String cuentaOrigen,
            @FormParam("cuentaDestino") String cuentaDestino,
            @FormParam("importe") double importe) {
        int estado;
        String codEmp = "0001"; // Código del empleado, esto puede ser dinámico
        try {
            EurekaService service = new EurekaService();
            service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
        return Response.ok("{\"estado\":" + estado + "}").build();
    }

    @GET
    @Path("/saldo/{cuenta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarSaldo(@PathParam("cuenta") String cuenta) {
        try {
            EurekaService service = new EurekaService();
            double saldo = service.consultarSaldo(cuenta);

            return Response.ok("{\"saldo\":" + saldo + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @GET
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public Response login(
        @QueryParam("usuario") String usuario,
        @QueryParam("clave") String clave) {

    try {

        EurekaService service =
                new EurekaService();

        boolean ok =
                service.login(usuario, clave);

        return Response.ok(
                "{\"login\":" + ok + "}"
        ).build();

    } catch (Exception e) {

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(
                        "{\"error\":\""
                        + e.getMessage()
                        + "\"}"
                )
                .build();
    }
}   
}
