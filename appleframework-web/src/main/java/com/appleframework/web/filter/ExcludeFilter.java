/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appleframework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.web.util.ServletUtil;


/**
 * 初始化spring容器的filter。
 *
 * @author Cruise Xu
 */
public abstract class ExcludeFilter implements Filter {
	
    private final Logger log = LoggerFactory.getLogger(getClass());

    protected RequestURIFilter excludeFilter;


    /** 设置要排除掉的URL。 */
    public void setExcludes(String excludes) {
        excludeFilter = new RequestURIFilter(excludes);
    }

    public boolean isExcluded(String path) {
        // 如果指定了excludes，并且当前requestURI匹配任何一个exclude pattern，
        // 则立即放弃控制，将控制还给servlet engine。
        // 但对于internal path，不应该被排除掉，否则internal页面会无法正常显示。
        if (excludeFilter != null && excludeFilter.matches(path)) {
        	return true;
        }
        return false;
    }


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String path = ServletUtil.getResourcePath((HttpServletRequest)request);

        if (isExcluded(path)) {
            log.debug("Excluded request: {}", path);
            chain.doFilter(request, response);
            return;
        } else {
            log.debug("Accepted and started to process request: {}", path);
        }
		
	}

	/** 初始化Filter。 */
    protected void init() throws Exception {
    }

    /** 清理filter。 */
    public void destroy() {
    }

   
}
