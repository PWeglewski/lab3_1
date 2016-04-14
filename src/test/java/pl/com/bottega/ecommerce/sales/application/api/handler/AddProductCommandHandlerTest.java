package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;
import pl.com.bottega.ecommerce.system.application.SystemUser;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by piotr on 14.04.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddProductCommandHandlerTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private ProductRepository productRepository;
    @Mock private SuggestionService suggestionService;
    @Mock private ClientRepository clientRepository;
    @Mock private SystemContext systemContext;

    @InjectMocks
    private AddProductCommandHandler addProductCommandHandler;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleTest() throws Exception {
        // given
        Id orderId = Id.generate();
        Id productId = Id.generate();
        AddProductCommand addProductCommand = new AddProductCommand(orderId, productId, 1);
        when(productRepository.load(any(Id.class))).thenReturn(mock(Product.class));
        when(clientRepository.load(any(Id.class))).thenReturn(mock(Client.class));
        when(systemContext.getSystemUser()).thenReturn(mock(SystemUser.class));
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.load(any(Id.class))).thenReturn(reservation);

        // when
        addProductCommandHandler.handle(addProductCommand);

        // then
        verify(reservationRepository, times(1)).load(orderId);
        verify(reservation, times(1)).add(any(Product.class), eq(addProductCommand.getQuantity()));
        verify(reservationRepository, times(1)).save(eq(reservation));
    }

}