
package com.freemarker;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model,
                                 HttpServletRequest request) throws Exception {
        model.put("ctx", request.getContextPath());
        super.exposeHelpers(model, request);
    }


}
