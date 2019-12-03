package me.tynahan.demoinflearnjpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testCreateForm() throws Exception {
        mockMvc.perform(get("/members/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateMember() throws Exception {
        // Given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("newbie");
        memberForm.setCity("seoul");
        memberForm.setStreet("sungbuk");
        memberForm.setZipcode("21730");

        // When & Then
        mockMvc.perform(post("/members/new")
                .content(objectMapper.writeValueAsString(memberForm)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testCreateMemberMandatoryException() throws Exception {
        // Given
        MemberForm memberForm = new MemberForm();
        memberForm.setCity("seoul");
        memberForm.setStreet("sungbuk");
        memberForm.setZipcode("21730");

        // When & Then
        mockMvc.perform(post("/members/new")
                .content(objectMapper.writeValueAsString(memberForm)))
                .andDo(print());
    }
}