package ru.t1.owner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.exception.NotFoundException;
import ru.t1.owner.dto.NewOwnerDto;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.owner.dto.UpdateOwnerDto;
import ru.t1.owner.service.OwnerService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OwnerController.class)
class OwnerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OwnerService ownerService;

    long ownerId = 2L;
    final String ownerName = "new Owner";
    final String email = "newOwner@mail.com";

    NewOwnerDto newOwnerDto = new NewOwnerDto(ownerName, email);

    OwnerDto ownerDto = new OwnerDto(ownerId, ownerName, email);


    @Test
    @SneakyThrows
    void addOwner() {
        when(ownerService.addOwner(any(NewOwnerDto.class))).thenReturn(ownerDto);

        mockMvc.perform(post("/owners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(newOwnerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ownerDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(ownerDto.getName())))
                .andExpect(jsonPath("$.email", is(ownerDto.getEmail())));

        verify(ownerService).addOwner(any(NewOwnerDto.class));

        mockMvc.perform(post("/owners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new NewOwnerDto())))
                .andExpect(status().isBadRequest());

        verify(ownerService, times(1)).addOwner(any(NewOwnerDto.class));
    }

    @Test
    @SneakyThrows
    void getOwnerById() {
        when(ownerService.getOwnerById(anyLong())).thenReturn(ownerDto);

        mockMvc.perform(get("/owners/{id}", ownerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownerDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(ownerDto.getName())))
                .andExpect(jsonPath("$.email", is(ownerDto.getEmail())));

        verify(ownerService).getOwnerById(ownerId);

        when(ownerService.getOwnerById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/owners/{ownerId}", ownerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(ownerService, times(2)).getOwnerById(ownerId);
    }

    @Test
    @SneakyThrows
    void updateOwner() {
        when(ownerService.updateOwner(eq(ownerId), any(UpdateOwnerDto.class))).thenReturn(ownerDto);

        mockMvc.perform(patch("/owners/{id}", ownerId)
                        .accept(MediaType.ALL_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownerDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(ownerDto.getName())))
                .andExpect(jsonPath("$.email", is(ownerDto.getEmail())));


        when(ownerService.updateOwner(eq(ownerId), any(UpdateOwnerDto.class)))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(patch("/owners/{ownerId}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(ownerDto)))
                .andExpect(status().isNotFound());
    }
    @Test
    @SneakyThrows
    void getAllOwners() {
        when(ownerService.getAllOwners()).thenReturn(List.of(ownerDto));

        mockMvc.perform(get("/owners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(ownerDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(ownerDto.getName())))
                .andExpect(jsonPath("$.[0].email", is(ownerDto.getEmail())));
    }

    @Test
    @SneakyThrows
    void deleteOwnerById() {
        mockMvc.perform(delete("/owners/{ownerId}", ownerId)
                        .accept(MediaType.ALL_VALUE))
                .andExpect(status().isNoContent());

        verify(ownerService).deleteOwnerById(ownerId);

        doThrow(NotFoundException.class)
                .when(ownerService)
                .deleteOwnerById(ownerId);

        mockMvc.perform(delete("/owners/{ownerId}", ownerId)
                        .accept(MediaType.ALL_VALUE))
                .andExpect(status().isNotFound());

        verify(ownerService, times(2)).deleteOwnerById(ownerId);
    }
}