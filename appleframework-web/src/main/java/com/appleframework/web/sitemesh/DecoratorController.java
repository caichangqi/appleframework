/**
 * 
 */
package com.appleframework.web.sitemesh;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * sitemesh主控Controller
 * 进行实时layout渲染
 * @author Cruise.Xu
 *
 */
@Controller
public class DecoratorController {
	 /**
     * 
     * Map all SiteMesh decorator requests.  Note that we use the pattern
     * \\w+ to prevent an outside source from getting access to files we might
     * not want made available.
     */
    @RequestMapping("layout/{name:\\w+}.ftl")
    public String decorator(@PathVariable String name, ModelMap map) {
           return "layout/" + name;
    }
}
