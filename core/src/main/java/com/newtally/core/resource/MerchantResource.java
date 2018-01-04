package com.newtally.core.resource;

import com.newtally.core.ServiceFactory;
import com.newtally.core.model.MerchantBranch;
import com.newtally.core.model.Role;
import com.newtally.core.model.Merchant;
import com.newtally.core.service.MerchantService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Path("/merchants")
public class MerchantResource extends BaseResource {

    private final MerchantService mrctServ = ServiceFactory.getInstance().getMerchantService();

    @PermitAll
    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerMerchant(@Context HttpServletRequest req) throws IOException {

        Merchant merchant = gson.fromJson(new InputStreamReader(req.getInputStream()), Merchant.class);

        merchant = mrctServ.registerMerchant(merchant);

        return Response.ok(gson.toJson(merchant)).build();
    }

    @RolesAllowed({Role.MERCHANT})
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCurrentMerchant(@Context HttpServletRequest req) throws IOException {

        Merchant merchant = gson.fromJson(req.getReader(), Merchant.class);

        mrctServ.updateCurrentMerchant(merchant);

        return Response.ok().build();
    }

    @RolesAllowed({Role.MERCHANT})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentMerchant(@Context HttpServletRequest req) throws IOException {

        Merchant merchant = mrctServ.getCurrentMerchant();

        return Response.ok(gson.toJson(merchant)).build();
    }

    @RolesAllowed( {Role.SYSTEM, Role.USER_ADMIN})
    @GET
    @Path("/<id>")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchantById(@PathParam("id") long id) throws IOException {

        Merchant merchant = mrctServ.getMerchantById(id);

        return Response.ok(gson.toJson(merchant)).build();
    }

    @RolesAllowed( {Role.SYSTEM, Role.USER_ADMIN})
    @GET
    @Path("/inactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInActiveMerchant() throws IOException {

        Merchant merchant = mrctServ.getInActiveMerchant();

        return Response.ok(gson.toJson(merchant)).build();
    }

    @RolesAllowed( {Role.SYSTEM, Role.USER_ADMIN})
    @PUT
    @Path("/<id>/changestatus?active=<active>")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMerchantState(@PathParam("id") long id, @QueryParam("active") boolean isActive) {

        mrctServ.changeStatus(id, isActive);

        return Response.ok().build();
    }

    @RolesAllowed({Role.MERCHANT})
    @GET
    @Path("/branches")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBranches() throws IOException {

        List<MerchantBranch> branches = mrctServ.getAllBranches();

        return Response.ok(gson.toJson(branches)).build();
    }

    @RolesAllowed({Role.MERCHANT})
    @POST
    @Path("/branch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerBranch(@Context HttpServletRequest req ) throws IOException {

        MerchantBranch branch = gson.fromJson(req.getReader(), MerchantBranch.class);

        branch = mrctServ.registerBranch(branch);

        return Response.ok(gson.toJson(branch)).build();
    }

    @RolesAllowed({Role.MERCHANT})
    @PUT
    @Path("/branch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBranch(@Context HttpServletRequest req ) throws IOException {

        MerchantBranch branch = gson.fromJson(req.getReader(), MerchantBranch.class);

        mrctServ.updateBranch(branch);

        return Response.ok().build();
    }

}