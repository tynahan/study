package me.tynahan.demoinflearnjpa.controller;

import lombok.extern.slf4j.Slf4j;
import me.tynahan.demoinflearnjpa.domain.Address;
import me.tynahan.demoinflearnjpa.domain.Member;
import me.tynahan.demoinflearnjpa.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberService memberService;


    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = modelMapper.map(memberForm, Member.class);
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }
}
