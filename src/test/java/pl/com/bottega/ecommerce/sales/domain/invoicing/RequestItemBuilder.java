package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

/**
 * Created by piotr on 04.05.2016.
 */
public class RequestItemBuilder {
    private static final Double DEFAULT_MONEY_VALUE = 23.45;
    private static final String DEFAULT_PRODUCT_NAME = "Default product name";
    private static final ProductType DEFAULT_PRODUCT_TYPE = ProductType.STANDARD;
    private static final int DEFAULT_PRODUCT_QUANTITY = 10;

    private final Date DEFAULT_SNAPSHOT_DATE = new Date();
    private final Id id = Id.generate();
    Money money;
    ProductData productData;
    RequestItem requestItem;
    private int quantity;

    public RequestItemBuilder() {
        money = new Money(DEFAULT_MONEY_VALUE);
        productData = new ProductData(id,
                money,
                DEFAULT_PRODUCT_NAME,
                DEFAULT_PRODUCT_TYPE,
                DEFAULT_SNAPSHOT_DATE);
        quantity = DEFAULT_PRODUCT_QUANTITY;
    }

    public RequestItemBuilder setNewProductData(ProductData productData) {
        this.productData = productData;
        return this;
    }

    public RequestItemBuilder setNewProductQuantity(int productQuantity) {
        this.quantity = productQuantity;
        return this;
    }

    public RequestItemBuilder setNewProductPrice(Money money) {
        this.money = money;
        return this;
    }

    public RequestItem build() {
        return new RequestItem(productData,
                quantity,
                money);
    }
}
