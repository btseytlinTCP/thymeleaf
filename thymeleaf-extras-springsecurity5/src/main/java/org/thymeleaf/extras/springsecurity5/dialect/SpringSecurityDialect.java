/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2018, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.thymeleaf.extras.springsecurity5.dialect;

import java.util.LinkedHashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.extras.springsecurity5.dialect.expression.SpringSecurityExpressionObjectFactory;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthenticationAttrProcessor;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthorizeAclAttrProcessor;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthorizeAttrProcessor;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthorizeUrlAttrProcessor;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;


/**
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public class SpringSecurityDialect
        extends AbstractDialect implements IProcessorDialect, IExpressionObjectDialect {

    public static final String NAME = "SpringSecurity";
    public static final String DEFAULT_PREFIX = "sec";
    public static final int PROCESSOR_PRECEDENCE = 800;

    public static final IExpressionObjectFactory EXPRESSION_OBJECT_FACTORY = new SpringSecurityExpressionObjectFactory();
    


    public SpringSecurityDialect() {
        super(NAME);
    }

    
    
    public String getPrefix() {
        return DEFAULT_PREFIX;
    }




    public int getDialectProcessorPrecedence() {
        return PROCESSOR_PRECEDENCE;
    }




    public Set<IProcessor> getProcessors(final String dialectPrefix) {

        final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();

        final TemplateMode[] templateModes =
                new TemplateMode[] {
                        TemplateMode.HTML, TemplateMode.XML,
                        TemplateMode.TEXT, TemplateMode.JAVASCRIPT, TemplateMode.CSS };

        for (final TemplateMode templateMode : templateModes) {

            processors.add(new AuthenticationAttrProcessor(templateMode, dialectPrefix));
            // synonym (sec:authorize = sec:authorize-expr) for similarity with
            // "authorize-url" and "autorize-acl"
            processors.add(new AuthorizeAttrProcessor(templateMode, dialectPrefix, AuthorizeAttrProcessor.ATTR_NAME));
            processors.add(new AuthorizeAttrProcessor(templateMode, dialectPrefix, AuthorizeAttrProcessor.ATTR_NAME_EXPR));
            processors.add(new AuthorizeUrlAttrProcessor(templateMode, dialectPrefix));
            processors.add(new AuthorizeAclAttrProcessor(templateMode, dialectPrefix));
            processors.add(new StandardXmlNsTagProcessor(templateMode, dialectPrefix));

        }

        return processors;

    }





    public IExpressionObjectFactory getExpressionObjectFactory() {
        return EXPRESSION_OBJECT_FACTORY;
    }
    
    
}
