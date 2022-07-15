package com.example.ex3;

import com.example.ex3.domain.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class SampleTest {

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void 티켓_컨버터() throws Exception { // Json으로 전달되는 데이터를 받아서 Ticket 타입으로 변환.

        Ticket ticket = Ticket.builder()
                .tno(123)
                .owner("Admin")
                .grade("AAA")
                .build();

        String jsonStr = new Gson().toJson(ticket); // Gson은 자바의 객체를 JSON 문자열로 변환하기 위해 사용. objectMapper 써도 됨.

        System.out.println(jsonStr);

        mockMvc.perform(post("/sample/ticket")
                .contentType(MediaType.APPLICATION_JSON) // 전달하는 데이터가 JSON이라는 것을 명시. 헤더에 명시
                .content(jsonStr)) // Body에 전송할 데이터를 넣음.
                .andExpect(status().is(200));
    }

    @Test
    void 티켓_만들기() throws Exception {

        Ticket ticket = Ticket.builder()
                .tno(2)
                .owner("최창기")
                .grade("GOLD")
                .build();


//         String str = new Gson().toJson(ticket);
        String str = objectMapper.writeValueAsString(ticket);

        mockMvc.perform(post("/sample/ticket2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(str))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void MockMvc_테스트1() throws Exception{

        mockMvc.perform(get("/sample/Test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Main"))
                .andDo(print());
    }

    @Test
    public void MockMvc_테스트2() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name","자바썸");
        info.add("age","10");

        mockMvc.perform(get("/sample/Test2")
                .params(info))
                .andExpect(status().isOk())
                .andExpect(content().string("이름은자바썸나이는10"))
                .andDo(print());

    }

    @Test
    public void MockMvc_연습용() throws Exception {

        String str = objectMapper.writeValueAsString(new Ticket(1, "홍길동", "VIP"));

        System.out.println(str);
    }

    @Test
    public void MockMvc_테스트3() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(view().name("Main"))
                .andDo(print());
    }



}
