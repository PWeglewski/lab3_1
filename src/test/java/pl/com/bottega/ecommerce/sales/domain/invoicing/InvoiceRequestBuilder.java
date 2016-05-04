package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 04.05.2016.
 */
public class InvoiceRequestBuilder {
    private static final String DEFAULT_CLIENT_NAME = "John";
    private final Id id = Id.generate();

    private ClientData clientData;

    private List<RequestItem> items = new ArrayList<>();

    public InvoiceRequestBuilder() {
        clientData = new ClientData(id, DEFAULT_CLIENT_NAME);
    }

    public InvoiceRequestBuilder setNewClientData(ClientData clientData){
        this.clientData = clientData;
        return this;
    }

    public InvoiceRequestBuilder addItem(RequestItem requestItem){
        this.items.add(requestItem);
        return this;
    }

    public InvoiceRequest build(){
        final InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        items.stream().forEach(invoiceRequest::add);
        return invoiceRequest;
    }
}
