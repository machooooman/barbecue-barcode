package com.sample.barcode.controller;


import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.barcode.entity.BarcodeType;
import com.sample.barcode.service.BarcodeUtil;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class BarcodeController {

	@Autowired
	private BarcodeUtil service;
	
	//print barcode image
	@GetMapping(value = "/image/{code}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getBarcode(@PathVariable String code) throws BarcodeException, OutputException, IOException {
		return ResponseEntity.ok(service.toByte(code, null));
	}
	
	//print barcode file
	@GetMapping(value="/file/{code}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource download(@PathVariable String code, HttpServletResponse response) throws BarcodeException, OutputException, IOException {
		File file = service.toFile(code, BarcodeType.CODE128);
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		return new FileSystemResource(file);
	}
}