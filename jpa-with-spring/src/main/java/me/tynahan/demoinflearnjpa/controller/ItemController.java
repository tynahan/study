package me.tynahan.demoinflearnjpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tynahan.demoinflearnjpa.domain.item.Book;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm) {
        Book book = modelMapper.map(bookForm, Book.class);

        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("items")
    public String list(Model model) {
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
}


