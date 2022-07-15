package com.example.ex3.controller;

import com.example.ex3.domain.SampleVO;
import com.example.ex3.domain.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/getText")
    public String gettext(){
        log.info("MIME Type : " + MediaType.TEXT_PLAIN_VALUE);
        return "안녕하세요.";
    }

    @GetMapping(value = "/getSample")
    public SampleVO getSample(){
        
        return new SampleVO(112, "스타","로드");
    }
    @GetMapping(value = "/getSample2")
    public SampleVO getSample2(){
        
        return new SampleVO(113, "최","창기");
    }

    @GetMapping("/getList")
    public List<SampleVO> getList(){
        return IntStream.range(1,10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last"))
                .collect(Collectors.toList());
    }

    @GetMapping("/getMap")
    public Map<String, SampleVO> getMap(){

        HashMap<String, SampleVO> map = new HashMap<>();
        map.put("First", new SampleVO(111,"그루트","주니어"));

        return map;
    }
    
    @GetMapping(value="/check")
    public ResponseEntity<SampleVO> check(Double height, Double weight){

        SampleVO vo = new SampleVO(0, "" + height, "" + weight);

        ResponseEntity<SampleVO> result = null;

        if(height < 150){
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        }else{
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }

        return result;
    }

    @GetMapping("/product/{cat}/{pid}")
    public String[] getPath(@PathVariable("cat") String cat,@PathVariable("pid")  String pid){

        return new String[] {"category: " + cat, "productid: " + pid};
    }

    @PostMapping("/ticket")
    public Ticket convert(@RequestBody Ticket ticket){

        log.info("convert.....ticket " + ticket);

        return ticket;
    }

    @PostMapping("/ticket2")
    public String Ticket_make(@RequestBody Ticket ticket){

        System.out.println("티켓 생성 ! " + ticket);

        return "티켓 생성 !";
    }

    @GetMapping("/Test")
    public String Test1(){

        return "Main";
    }

    @GetMapping("/Test2")
    public String Test2( String name , String age){

        return "이름은" + name +"나이는" + age ;
    }

}
