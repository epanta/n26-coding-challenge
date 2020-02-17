package com.n26.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    private static final String CREATE_TRANSACTIONS_URL = "/transactions";

    @Test
    public void shouldReturnStatusOkWhenCreateTransaction3() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post(CREATE_TRANSACTIONS_URL)
                .content(asJsonString(Transaction.builder().amount(BigDecimal.valueOf(23.54)).timestamp(Instant.now()).build())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenCallCreateTransaction() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(CREATE_TRANSACTIONS_URL)
                .content("{\"timestamp\":2020-02-10T01:25:39.074, \"amount\":\"48.17\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(422))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Malformed JSON in request body"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateTransaction() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post(CREATE_TRANSACTIONS_URL)
                .content(asJsonString(Transaction.builder().timestamp(Instant.now()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Malformed JSON in request body"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldReturnParserErrorWhenCreateTransaction() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(CREATE_TRANSACTIONS_URL)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(422))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not parse the request"));
    }

    @Test
    public void shouldReturnInternalServerErrortWhenCreateTransaction() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post(CREATE_TRANSACTIONS_URL)
                .content(asJsonString(Transaction.builder().amount(BigDecimal.valueOf(0.00)).timestamp(Instant.now()).build())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The server encounters an unexpected condition"));
    }

    @Test
    public void shouldReturnNoContentWhenDeleteTransaction() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(CREATE_TRANSACTIONS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
