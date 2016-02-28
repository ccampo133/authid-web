package com.authid.web.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Chris Campo
 */
@RestController
@RequestMapping(path = "/api")
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

    @RequestMapping(path = "/clients/{clientId}", method = RequestMethod.GET)
    public ClientDetails getClientById(@NotNull @PathVariable final String clientId) {
        return clientDetailsService.loadClientByClientId(clientId);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<ClientDetails> addClientDetails(@NotNull @RequestBody final BaseClientDetails clientDetails) {
        clientDetailsService.addClientDetails(clientDetails);
        final ClientDetails newClientDetails = clientDetailsService.loadClientByClientId(clientDetails.getClientId());
        // Set the location header to point towards our new resource URI
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/clients")
                .buildAndExpand(newClientDetails.getClientId())
                .toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(newClientDetails, headers, HttpStatus.CREATED);
    }
}
