package com.github.funthomas424242.sbstarter.nitrite;

/*-
 * #%L
 * rezeptsammlung
 * %%
 * Copyright (C) 2019 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.dizitart.no2.Nitrite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({Nitrite.class})
public class NitriteAutoConfiguration {

    @Value("${nitrite.dbfilePath}")
    protected String dbfilePath;

    @Value("${nitrite.username:}")
    protected String username;

    @Value("${nitrite.password:}")
    protected String password;

    @Value("${nitrite.disableautocommit:false}")
    protected Boolean disableautocommit;

    @Value("${nitrite.compressed:false}")
    protected Boolean compressed;

}
