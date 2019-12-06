package me.tynahan.demoinflearnjpa.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.tynahan.demoinflearnjpa.service.ItemService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

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

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        Map<String, String> map = objectMapper.convertValue(bookForm, new TypeReference<Map<String, String>>() {});
        multiValueMap.setAll(map);

        ResultActions result = mockMvc.perform(post("/items/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(multiValueMap)
        );

        // Then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(print());
    }

    @Test
    public void testGetItemList() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc.perform(get("/items"));

        // Then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("items/itemList"))
                .andExpect(model().attributeExists("items"));
    }

}