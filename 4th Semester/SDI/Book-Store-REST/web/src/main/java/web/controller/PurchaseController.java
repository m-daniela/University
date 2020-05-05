package web.controller;

import core.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import web.converter.PurchaseConverter;
import web.dto.purchase.PurchaseDto;
import web.dto.purchase.PurchasesDto;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class PurchaseController {
    public static final Logger log = LoggerFactory.getLogger(PurchaseController.class);
    @Autowired
    private PurchaseConverter purchaseConverter;
    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    PurchaseDto add(@RequestBody PurchaseDto purchaseDto) throws SQLException, ParserConfigurationException, IOException, SAXException, TransformerException {
        log.trace("PurchaseController: add({})", purchaseDto);
        return purchaseConverter.convertModelToDto(purchaseService.addPurchase(purchaseConverter.convertDtoToModel(purchaseDto)));
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.GET)
    PurchasesDto getAll() throws SQLException {
        log.trace("PurchaseController: getAll()");
        return new PurchasesDto(purchaseConverter.convertModelsToDtos(purchaseService.getAllPurchases()));
    }

    @RequestMapping(value = "/purchases/sort/{filters}/{direction}", method = RequestMethod.GET)
    PurchasesDto getAll(@PathVariable String filters, @PathVariable String direction) throws SQLException {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), filters.split(" "));
        log.trace("PurchaseController: getAll({})", sort);
        return new PurchasesDto(purchaseConverter.convertModelsToDtos(purchaseService.getAllPurchases(sort)));
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.PUT)
    ResponseEntity<?> update(@RequestBody PurchaseDto purchaseDto) throws SQLException {
        log.trace("PurchaseController: update({})", purchaseDto);
        purchaseService.updatePurchase(purchaseConverter.convertDtoToModel(purchaseDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/purchases/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        log.trace("PurchaseController: delete({})", id);
        purchaseService.removePurchase(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/purchases/filter/{clientID}", method = RequestMethod.GET)
    PurchasesDto filter(@PathVariable Long clientID) throws SQLException {
        log.trace("PurchaseController: filter({})", clientID);
        return new PurchasesDto(purchaseConverter.convertModelsToDtos(purchaseService.filterPurchasesByClientID(clientID)));
    }
}
