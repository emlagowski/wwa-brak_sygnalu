package pl.allegro.braincode.api.prices;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.braincode.integration.OffersQuery;
import pl.allegro.braincode.messages.category.Category;
import pl.allegro.braincode.messages.price.PriceDto;
import pl.allegro.braincode.price.PricesService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PricesController {

    @Autowired
    private PricesService pricesService;

    @GetMapping
    public List<PriceDto> getPrices(@RequestParam Category category, @RequestParam String phrase) {

        OffersQuery offersQuery = new OffersQuery(pl.allegro.braincode.integration.Category.valueOf(category.name()), phrase);
        pricesService.getPrices(offersQuery);

        return Lists.newArrayList(
                new PriceDto(1,new BigDecimal("112.20")),
                new PriceDto(1,new BigDecimal("112.20"))
        );
    }
}
