package com.samantha.patientservice.grpc;

import com.example.billingservice.BillingAccountRequest;
import com.example.billingservice.BillingAccountResponse;
import com.example.billingservice.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String billingServiceAddress,
            @Value("${billing.service.grpc.port:9091}") int billingServicePort) {
        log.info("Initializing BillingServiceGrpcClient with address: {} and port: {}", billingServiceAddress, billingServicePort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(billingServiceAddress, billingServicePort)
                .usePlaintext()
                .build();

        billingServiceStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingAccountResponse createBillingAccount(String patientId, String name, String email) {
        log.info("Creating billing account for patient ID: {}", patientId);
        BillingAccountRequest request = BillingAccountRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();
        BillingAccountResponse response = billingServiceStub.createBillingAccount(request);
        log.info("Received billing account response: {}", response);
        return response;

    }
}
