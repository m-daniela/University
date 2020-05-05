package web.controller;

import core.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.ClientConverter;
import web.dto.client.ClientDto;
import web.dto.client.ClientsDto;

import java.sql.SQLException;

@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientConverter clientConverter;
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto add(@RequestBody ClientDto clientDto){
        log.trace("ClientController: add({})", clientDto);
        return clientConverter.convertModelToDto(clientService.addClient(clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientsDto getAll() throws SQLException {
        log.trace("ClientController: getAll()");
        return new ClientsDto(clientConverter.convertModelsToDtos(clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients/{filters}/{direction}", method = RequestMethod.GET)
    ClientsDto getAll(@PathVariable String filters, @PathVariable String direction) throws SQLException {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), filters.split(" "));
        log.trace("ClientController: getAll({})", sort);
        return new ClientsDto(clientConverter.convertModelsToDtos(clientService.getAllClients(sort)));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    ResponseEntity<?> update(@RequestBody ClientDto clientDto) throws SQLException {
        log.trace("ClientController: update({})", clientDto);
        clientService.updateClient(clientConverter.convertDtoToModel(clientDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        log.trace("ClientController: delete({})", id);
        clientService.removeClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}")
    ClientsDto filter(@PathVariable("name") String name) throws SQLException {
        log.trace("ClientController: filter({})", name);
        return new ClientsDto(clientConverter.convertModelsToDtos(clientService.filterClientsByName(name)));
    }
}
