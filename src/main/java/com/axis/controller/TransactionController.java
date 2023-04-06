package com.axis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.axis.helper.Helper;
import com.axis.model.PaymentDetails;
import com.axis.model.TransactionHeader;
import com.axis.service.TransactionHeaderService;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin("*")
public class TransactionController {
	
	@Autowired
	TransactionHeaderService transactionHeaderService;
	
	@PostMapping("/upload/file/{templateId}")
	public ResponseEntity<List<PaymentDetails>> upload(@RequestParam("file") MultipartFile file, @PathVariable int templateId){
		if(Helper.checkExcelFormat(file))
		{
			String name = file.getOriginalFilename();
        	String fileName = name.substring(0, name.indexOf("."));
			
			return new ResponseEntity<List<PaymentDetails>> (transactionHeaderService.saveTransactionHeader(file,templateId,fileName),HttpStatus.OK);
		}
		return new ResponseEntity<List<PaymentDetails>> (HttpStatus.OK);
	}
	
	@GetMapping("templatedetails/{transactionHeader}/{templateId}")
	public ResponseEntity<List<PaymentDetails>> getPaymentDetailsByHeaderTemplateId(@PathVariable int transactionHeader, @PathVariable int templateId){
		return new ResponseEntity<List<PaymentDetails>> (transactionHeaderService.getPaymentDetailsByTransactionIdTemplateId(transactionHeader, templateId), HttpStatus.OK);
	}
	
	@GetMapping("/transactionheaders")
	public ResponseEntity<List<TransactionHeader>> getAllTransactionHeaders(){
		return new ResponseEntity<List<TransactionHeader>> (transactionHeaderService.getAllTransactionHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{transactionId}")
	public ResponseEntity<String> deleteTransactionHeaderById(@PathVariable int transactionId){
		return new ResponseEntity<String> (transactionHeaderService.deleteTransactionHeader(transactionId),HttpStatus.OK);
	}
	
}
