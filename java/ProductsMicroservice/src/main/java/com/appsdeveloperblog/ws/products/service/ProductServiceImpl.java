package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.model.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) {
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent
                = new ProductCreatedEvent(productId, productRestModel.getTitle(),
                productRestModel.getPrice(), productRestModel.getQuantity());

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
        future.whenComplete(((result, throwable) -> {
         if(throwable != null) {
            logger.error("failed to send message ", throwable.getMessage());
         } else {
            logger.info("Message sent successfully: " + result.getRecordMetadata());
         }
        }));

//        //to block the thread here, and making send operation as synchronous
//        future.join();
        return productId;
    }
}
