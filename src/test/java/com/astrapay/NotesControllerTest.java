package com.astrapay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllNotes() throws Exception {
        ResultActions result = mockMvc.perform(get("/notes/list"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void createNotes() throws Exception {
        ResultActions result = mockMvc.perform(post("/notes/create")
                .contentType("application/json")
                .content("{\n" +
                        "    \"title\": \"Test Title\",\n" +
                        "    \"description\": \"Test Description\",\n" +
                        "    \"content\": \"Test Content\"\n" +
                        "}"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.description").value("Test Description"))
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    public void updateNotes() throws Exception {
        ResultActions createResult = mockMvc.perform(post("/notes/create")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"title\": \"Test Title\",\n" +
                                "    \"description\": \"Test Description\",\n" +
                                "    \"content\": \"Test Content\"\n" +
                                "}"))
                .andExpect(status().isOk());

        String responseJson = createResult.andReturn().getResponse().getContentAsString();
        String id = extractIdFromResponse(responseJson);

        ResultActions updateResult = mockMvc.perform(put("/notes/update/" + id)
                .contentType("application/json")
                .content("{\n" +
                        "    \"title\": \"Updated Title\",\n" +
                        "    \"description\": \"Updated Description\",\n" +
                        "    \"content\": \"Updated Content\"\n" +
                        "}"));

        updateResult.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.description").value("Updated Description"))
                .andExpect(jsonPath("$.data.content").value("Updated Content"));
    }

    @Test
    public void deleteNotes() throws Exception {
        ResultActions createResult = mockMvc.perform(post("/notes/create")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"title\": \"Test Title\",\n" +
                                "    \"description\": \"Test Description\",\n" +
                                "    \"content\": \"Test Content\"\n" +
                                "}"))
                .andExpect(status().isOk());

        String responseJson = createResult.andReturn().getResponse().getContentAsString();
        String id = extractIdFromResponse(responseJson);

        ResultActions deleteResult = mockMvc.perform(delete("/notes/delete/" + id));

        deleteResult.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.notesId").value(id));
    }

    private String extractIdFromResponse(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"notesId\":\"") + 11;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
