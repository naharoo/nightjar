package club.thewhitewall.nightjar.endpoint.editor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping("/editor")
public interface EditorEndpoint {

}
