package web.converter;

import core.model.Purchase;
import org.springframework.stereotype.Component;
import web.dto.purchase.PurchaseDto;

@Component
public class PurchaseConverter extends BaseConverter<Purchase, PurchaseDto> {
    @Override
    public Purchase convertDtoToModel(PurchaseDto dto) {
        Purchase purchase = Purchase.builder()
                .clientID(dto.getClientID())
                .bookID(dto.getBookID())
                .nrBooks(dto.getNrBooks())
                .build();
        purchase.setId(dto.getId());
        return purchase;
    }

    @Override
    public PurchaseDto convertModelToDto(Purchase purchase) {
        PurchaseDto dto = PurchaseDto.builder()
                .clientID(purchase.getClientID())
                .bookID(purchase.getBookID())
                .nrBooks(purchase.getNrBooks())
                .build();
        dto.setId(purchase.getId());
        return dto;
    }
}
