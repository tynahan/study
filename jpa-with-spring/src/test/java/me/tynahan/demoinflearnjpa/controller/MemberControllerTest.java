package me.tynahan.demoinflearnjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.tynahan.demoinflearnjpa.domain.Member;
import me.tynahan.demoinflearnjpa.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    MemberService memberService;

    @Test
    public void testCreateForm() throws Exception {
        mockMvc.perform(get("/members/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/createMemberForm"))
                .andExpect(model().attributeExists("memberForm"));
    }

    @Test
    public void testCreateMember() throws Exception {
        // Given
        long memberId = 1L;
        given(memberService.join(any(Member.class))).willReturn(memberId);

        // When
        MemberForm memberForm = new MemberForm();
        memberForm.setName("newbie");
        memberForm.setCity("seoul");
        memberForm.setStreet("sungbuk");
        memberForm.setZipcode("21730");

        when(modelMapper.map(any(), any())).thenReturn(new Member());

        ResultActions result = mockMvc.perform(post("/members/new")
                .content(objectMapper.writeValueAsString(memberForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }

    @Test
    public void testCreateMemberMandatoryException() throws Exception {
        // Given
        long memberId = 1L;
        given(memberService.join(any(Member.class))).willReturn(memberId);

        // When
        MemberForm memberForm = new MemberForm();
//        memberForm.setName("newbie");
        memberForm.setCity("seoul");
        memberForm.setStreet("sungbuk");
        memberForm.setZipcode("21730");

        when(modelMapper.map(any(), any())).thenReturn(new Member());

        ResultActions result = mockMvc.perform(post("/members/new")
                .content(objectMapper.writeValueAsString(memberForm))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(view().name("members/createMemberForm"))
                .andExpect(model().hasErrors())
                .andDo(print());
    }

    @Test
    public void testGetMemberList() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc.perform(get("/members"));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/memberList"))
                .andExpect(model().attributeExists("members"));
    }
}