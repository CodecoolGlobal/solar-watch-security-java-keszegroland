package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.CreateClientRequest;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClientController.class)
@TestPropertySource(locations = "classpath:application-integrationTest.properties")
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientServiceMock;

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @MockBean
    private JwtUtils jwtUtilsMock;

    @Test
    @WithMockUser
    public void givenCreateClientRequest_WhenCreateNewUser_ThenStatus201() throws Exception {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationManagerMock.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authenticationMock);

        when(jwtUtilsMock.generateJwtToken(authenticationMock)).thenReturn("thisIsTheGeneratedJWTToken");

        CreateClientRequest createClientRequest = new CreateClientRequest();
        createClientRequest.setUsername("user");
        createClientRequest.setPassword("user");

        String userJson = objectMapper.writeValueAsString(createClientRequest);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(csrf()))
                .andExpect(status().isCreated());


        verify(clientServiceMock).createUser(createClientRequest);
    }

}