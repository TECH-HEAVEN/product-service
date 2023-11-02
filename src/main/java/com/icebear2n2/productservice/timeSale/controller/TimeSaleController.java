package com.icebear2n2.productservice.timeSale.controller;

import com.icebear2n2.productservice.domain.request.TimeSaleRequest;
import com.icebear2n2.productservice.domain.response.ProductResponse;
import com.icebear2n2.productservice.timeSale.service.TimeSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time")
public class TimeSaleController {
    private final TimeSaleService timeSaleService;

    @PutMapping
    public ResponseEntity<ProductResponse> startProductTimeSale(@RequestBody TimeSaleRequest timeSaleRequest) {
        ProductResponse productResponse = timeSaleService.startProductTimeSale(timeSaleRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getProductStaredTimeSale() {
        return new ResponseEntity<>(timeSaleService.getProductStaredTimeSale(), HttpStatus.OK);
    }
}
