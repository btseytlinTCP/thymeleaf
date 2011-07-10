/*
 * =============================================================================
 * 
 *   Copyright (c) 2011, The THYMELEAF team (http://www.thymeleaf.org)
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
package thymeleafexamples.extrathyme.dialects.score;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.Arguments;
import org.thymeleaf.processor.applicability.AttrApplicability;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.processor.value.IValueProcessor;
import org.thymeleaf.standard.processor.value.StandardValueProcessor;
import org.thymeleaf.standard.syntax.StandardSyntax;
import org.thymeleaf.standard.syntax.StandardSyntax.Value;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import thymeleafexamples.extrathyme.business.entities.Remark;
import thymeleafexamples.extrathyme.business.util.RemarkUtil;

public class AppendClassForPositionAttrProcessor 
        extends AbstractAttributeModifierAttrProcessor {

    
    public AppendClassForPositionAttrProcessor() {
        super();
    }

    
    
    public Set<AttrApplicability> getAttributeApplicabilities() {
        return AttrApplicability.createSetForAttrName("appendclassforposition");
    }

    
    
    public Integer getPrecedence() {
        return Integer.valueOf(12000);
    }


    @Override
    public Set<Class<? extends IValueProcessor>> getValueProcessorDependencies() {
        final Set<Class<? extends IValueProcessor>> dependencies = new HashSet<Class<? extends IValueProcessor>>();
        dependencies.add(StandardValueProcessor.class);
        return dependencies;
    }
    
    

    @Override
    protected Map<String, String> getNewAttributeValues(final Arguments arguments,
            final TemplateResolution templateResolution, final Document document,
            final Element element, final Attr attribute, final String attributeName,
            final String attributeValue) {

        final StandardValueProcessor valueProcessor =
            arguments.getConfiguration().getValueProcessorByClass(this,StandardValueProcessor.class);
        
        final Value positionValue = 
            StandardSyntax.parseValue(attributeValue, valueProcessor, arguments, templateResolution);
        
        final Integer position = 
            (Integer) valueProcessor.getValue(arguments, templateResolution, positionValue); 
        
        final Remark remark = RemarkUtil.getRemarkForPosition(position);
        
        final Map<String,String> values = new HashMap<String, String>();
        
        if (remark != null) {
            switch (remark) {
                case WORLD_CHAMPIONS_LEAGUE:  
                    values.put("class", "wcl");
                    break;
                case CONTINENTAL_PLAYOFFS:  
                    values.put("class", "cpo");
                    break;
                case RELEGATION:  
                    values.put("class", "rel");
                    break;
            }
        }
        
        return values;
        
    }

    
    
    @Override
    protected ModificationType getModificationType(final Arguments arguments,
            final TemplateResolution templateResolution, final Document document,
            final Element element, final Attr attribute, final String attributeName,
            final String attributeValue, final String newAttributeName) {
        
        return ModificationType.APPEND_WITH_SPACE;
        
    }

    
    
    @Override
    protected boolean removeAttributeIfEmpty(final Arguments arguments,
            final TemplateResolution templateResolution, final Document document,
            final Element element, final Attr attribute, final String attributeName,
            final String attributeValue, final String newAttributeName) {
        return false;
    }
    



}
