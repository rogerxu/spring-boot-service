package com.example.demo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service/demo")
public class DemoController {

	@GetMapping("/greeting")
	public Map<String, Object> greeting(@RequestParam(value = "name", required = true) String name) {
		Map<String, Object> result = new HashMap<>();
		result.put("name", name);
		result.put("time", Instant.now().toString());

		return result;
	}
}
