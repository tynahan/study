package me.tynahan.demoinflearnjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.tynahan.demoinflearnjpa.domain.Member;
import me.tynahan.demoinflearnjpa.domain.item.Book;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.service.ItemService;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    ItemService itemService;

    @Test
    public void testCreateForm() throws Exception {
        mockMvc.perform(get("/items/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("items/createItemForm"))
                .andExpect(model().attributeExists("itemForm"));
    }

    @Test
    public void testCreateItem() throws Exception {
        // When
        BookForm bookForm = new BookForm();
        bookForm.setAuthor("namja");
        bookForm.setIsbn("123124");
        bookForm.setName("new blood");
        bookForm.setPrice(100000);
        bookForm.setStockQuantity(100);

        ResultActions result = mockMvc.perform(post("/items/new")
                .content(objectMapper.writeValueAsString(bookForm))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // Then
        result.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }
}