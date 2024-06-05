package ru.t1.solution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.exception.NotFoundException;
import ru.t1.owner.dto.OwnerDto;
import ru.t1.solution.dto.NewSolutionDto;
import ru.t1.solution.dto.SolutionDto;
import ru.t1.solution.dto.UpdateSolutionDto;
import ru.t1.solution.service.SolutionService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SolutionController.class)
class SolutionControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SolutionService solutionService;

    long solutionId = 1L;
    final String solutionName = "new Solution";
    final String version = "newSolutionVersion";
    long ownerId = 2L;
    final String ownerName = "new Owner";
    final String email = "newOwner@mail.com";
    NewSolutionDto newSolutionDto = new NewSolutionDto(solutionName, version, ownerId);

    SolutionDto solutionDto = new SolutionDto(solutionId, solutionName, version, new OwnerDto(ownerId, ownerName, email));


    @Test
    @SneakyThrows
    void addSolution() {
        when(solutionService.addSolution(any(NewSolutionDto.class))).thenReturn(solutionDto);

        mockMvc.perform(post("/solutions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(newSolutionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(solutionDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(solutionDto.getName())))
                .andExpect(jsonPath("$.version", is(solutionDto.getVersion())))
                .andExpect(jsonPath("$.ownerDto").isNotEmpty());

        verify(solutionService).addSolution(any(NewSolutionDto.class));

        mockMvc.perform(post("/solutions")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new NewSolutionDto())))
                .andExpect(status().isBadRequest());

        verify(solutionService, times(1)).addSolution(any(NewSolutionDto.class));
    }

    @Test
    @SneakyThrows
    void getSolutionById() {
        when(solutionService.getSolutionById(anyLong())).thenReturn(solutionDto);

        mockMvc.perform(get("/solutions/{id}", solutionId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(solutionDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(solutionDto.getName())))
                .andExpect(jsonPath("$.version", is(solutionDto.getVersion())))
                .andExpect(jsonPath("$.ownerDto").isNotEmpty());

        verify(solutionService).getSolutionById(solutionId);

        when(solutionService.getSolutionById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/solutions/{solutionId}", solutionId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(solutionService, times(2)).getSolutionById(solutionId);
    }

    @Test
    @SneakyThrows
    void updateSolution() {
        when(solutionService.updateSolution(eq(solutionId), any(UpdateSolutionDto.class))).thenReturn(solutionDto);

        mockMvc.perform(patch("/solutions/{id}", solutionId)
                        .accept(MediaType.ALL_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(solutionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(solutionDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(solutionDto.getName())))
                .andExpect(jsonPath("$.version", is(solutionDto.getVersion())))
                .andExpect(jsonPath("$.ownerDto").isNotEmpty());


        when(solutionService.updateSolution(eq(solutionId), any(UpdateSolutionDto.class)))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(patch("/solutions/{solutionId}", solutionId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(solutionDto)))
                .andExpect(status().isNotFound());
    }
    @Test
    @SneakyThrows
    void getAllSolutions() {
        when(solutionService.getAllSolutions()).thenReturn(List.of(solutionDto));

        mockMvc.perform(get("/solutions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(solutionDto.getId()), Long.class))
                .andExpect(jsonPath("$.[0].name", is(solutionDto.getName())))
                .andExpect(jsonPath("$.[0].version", is(solutionDto.getVersion())))
                .andExpect(jsonPath("$.[0].ownerDto").isNotEmpty());
    }

    @Test
    @SneakyThrows
    void deleteSolutionById() {
        mockMvc.perform(delete("/solutions/{solutionId}", solutionId)
                        .accept(MediaType.ALL_VALUE))
                .andExpect(status().isNoContent());

        verify(solutionService).deleteSolutionById(solutionId);

        doThrow(NotFoundException.class)
                .when(solutionService)
                .deleteSolutionById(solutionId);

        mockMvc.perform(delete("/solutions/{solutionId}", solutionId)
                        .accept(MediaType.ALL_VALUE))
                .andExpect(status().isNotFound());

        verify(solutionService, times(2)).deleteSolutionById(solutionId);
    }

}