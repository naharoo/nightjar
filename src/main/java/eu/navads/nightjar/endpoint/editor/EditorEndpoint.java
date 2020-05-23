package eu.navads.nightjar.endpoint.editor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Validated
@Controller
@RequestMapping("/editor")
public interface EditorEndpoint {

    @GetMapping
    ModelAndView getEditor(
            ModelAndView modelAndView,
            @RequestParam(value = "snippetId", required = false) String snippetId
    );
}
