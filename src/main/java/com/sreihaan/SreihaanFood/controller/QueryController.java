package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.QueryMailDTO;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping
    public void addQuery(@RequestBody QueryMailDTO queryMailDTO)
    {
        emailSenderService.sendQueryMail(queryMailDTO.getName(), queryMailDTO.getMail(), queryMailDTO.getMessage());
    }

}
