package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 예외 발생 테스트 컨트롤러
 * @author Lee su hong
 *
 */
@Controller
public class TestController {

	@RequestMapping("/test")
	public String test() {
		throw new RuntimeException("예외가 발생했습니다.");
	}

	@RequestMapping("/test2")
	@ResponseBody
	public String test2() {
		throw new RuntimeException("예외가 발생했습니다. ajax");
	}
}