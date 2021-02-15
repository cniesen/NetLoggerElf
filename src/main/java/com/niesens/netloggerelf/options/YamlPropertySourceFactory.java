/*
	Claus' NetLogger Elf
	Copyright (C) 2020  Claus Niesen
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.niesens.netloggerelf.options;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertySourceFactory for yaml resources.
 * This  implementation honors the {@link org.springframework.context.annotation.PropertySource#ignoreResourceNotFound()
 * ignoreResourceNotFound} flag.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String sourceName = name != null ? name : getNameForResource(resource.getResource());
        return new PropertiesPropertySource(sourceName, loadYamlProperties(resource));
    }

    /**
     * Get properties from yaml resource
     *
     * @param resource yaml resource
     * @return {@link java.util.Properties} with the properties of the resource
     * @throws FileNotFoundException when resource is not found
     */
    private Properties loadYamlProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // handle ignoreResourceNotFound
            if (e.getCause() instanceof FileNotFoundException) {
                throw (FileNotFoundException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    /**
     * Return the description for the given Resource; if the description is
     *  empty, return the class name of the resource plus its identity hash code.
     *
     * @param resource the yaml property resource
     * @return The resource description.
     *
     * @see org.springframework.core.io.Resource#getDescription()
     */
    private static String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if (!StringUtils.hasText(name)) {
            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }
        return name;
    }
}
