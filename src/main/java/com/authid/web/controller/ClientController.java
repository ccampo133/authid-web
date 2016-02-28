package com.authid.web.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Chris Campo
 */
@RestController("/apiw")
public class ClientController {

    private final JdbcClientDetailsService clientDetailsService;

    @Autowired
    public ClientController(@NotNull final JdbcClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @RequestMapping(path = "/clients", method = RequestMethod.GET)
    public List<ClientDetails> getClients() {
        return clientDetailsService.listClientDetails();
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ClientDetails addClientDetails(@NotNull final ClientDetails clientDetails) {
        clientDetailsService.addClientDetails(clientDetails);
        return clientDetailsService.loadClientByClientId(clientDetails.getClientId());
    }
}
