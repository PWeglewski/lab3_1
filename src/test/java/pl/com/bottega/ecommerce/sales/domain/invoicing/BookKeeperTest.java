package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by piotr on 13.04.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {
    RequestItemBuilder requestItemBuilder = new RequestItemBuilder();

    @Mock
    InvoiceFactory invoiceFactory;

    @Mock
    TaxPolicy taxPolicy;

    @InjectMocks
    BookKeeper bookKeeper;

    ClientData clientData;
    ProductData productData;
    InvoiceRequest invoiceRequest;
    Money money;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        money = new Money(new BigDecimal(23.65));
        clientData = new ClientData(Id.generate(), "clientData");
        productData = new ProductData(Id.generate(), new Money(new BigDecimal(50)), "NAME", ProductType.DRUG, new Date());
        invoiceRequest = new InvoiceRequest(clientData);
        invoiceFactory = new InvoiceFactory();
        bookKeeper = new BookKeeper(invoiceFactory);
    }

    @Test
    public void shouldReturnInvoiceWithOneItem() throws Exception {
        // given
        invoiceRequest.add(requestItemBuilder.build());
        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(new Money(new BigDecimal(0.65)), "The tax"));

        // when
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        // then
        assertThat(invoice.getItems().size(), is(equalTo(1)));
    }

    @Test
    public void shouldReturnInvoiceWithTwoItems() throws Exception{
        // given
        invoiceRequest.add(requestItemBuilder.build());
        invoiceRequest.add(requestItemBuilder.build());
        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
                .thenReturn(new Tax(new Money(new BigDecimal(0.65)), "The tax"));

        // when
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        // then
        assertThat(invoice.getItems().size(), is(equalTo(2)));
        verify(taxPolicy, times(2)).calculateTax(any(ProductType.class), any(Money.class));
    }
}